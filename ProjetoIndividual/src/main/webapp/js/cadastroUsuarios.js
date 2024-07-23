
PROJETO.usuarios = new Object();



$(document).ready(function() {

	PROJETO.usuarios.trazerUsuarios = function(cond) {
		$.ajax({
			type: "GET",
			url: PROJETO.PATH + "usuarios/procurar",
			success: function(usuarios) {
				if (cond === 0) {
					abrirModal('modalRelatorioUsuarios');
					gerarTabelaRelatorioUsuarios(usuarios, cond);
				} else {
					console.log("passo");
					abrirModal('modalGerirUsuarios');
					gerarTabelaRelatorioUsuarios(usuarios, cond);
				}

			},
			error: function(info) {
				alert(info + info.status + "-" + info.statusText);
			}
		});
	}

	function gerarTabelaRelatorioUsuarios(dadosUsuarios, cond) {
		var tabela = '<table>';
		tabela += '<tr><th>Nome</th><th>E-mail</th><th>Tipo de Usuário</th><th>Telefone</th><th>Senha</th>';
		if (cond != 0) {
			tabela += '<th>Ações</th>';
		}
		tabela += '</tr>';

		for (var i = 0; i < dadosUsuarios.length; i++) {
			tabela += '<tr>';
			tabela += '<td>' + dadosUsuarios[i].usuario + '</td>';
			tabela += '<td>' + dadosUsuarios[i].email + '</td>';
			tabela += '<td>' + dadosUsuarios[i].tipoUsuario + '</td>';
			tabela += '<td>' + dadosUsuarios[i].telefone + '</td>';
			// Substitua a senha real por "*****"
			tabela += '<td>*****</td>';
			if (cond != 0) {
				if (dadosUsuarios[i].usuario != "davi") {
					tabela += '<td><a onclick="abrirModal(\'modalEditaUsuario\');PROJETO.usuarios.exibirEdicao(' + dadosUsuarios[i].id + ')"><img src=\'../../imgs/edit.png\' alt=\'Editar registro\'></a>' +
						'<a onclick="PROJETO.usuarios.excluir(' + dadosUsuarios[i].id + ')"><img src=\'../../imgs/delete.png\' alt=\'Excluir registro\'></a></td>';
				}
			}
			tabela += '</tr>';
		}

		tabela += '</table>';

		// Exiba a tabela no id "tabelaRelatorioUsuarios"
		if (cond != 0) {
			$('#tabelaGerirUsuarios').html(tabela);
		} else {
			$('#tabelaRelatorioUsuarios').html(tabela);
		}
	}



	PROJETO.usuarios.inserirUsuario = function() {
		var dadosUsuario = new Object();
		dadosUsuario.usuario = document.frmUsuarioCad.nome.value;
		dadosUsuario.email = document.frmUsuarioCad.email.value;
		dadosUsuario.telefone = document.frmUsuarioCad.telefone.value;
		dadosUsuario.tipoUsuario = document.frmUsuarioCad.tipoUsuario.value;
		dadosUsuario.senha = document.frmUsuarioCad.senha.value;

		if (dadosUsuario.tipoUsuario == "selecione") {
			alert("Preencha com um tipo de usuario válido!");
		} else if (dadosUsuario.senha != document.frmUsuarioCad.confirmPassword.value) {
			alert("Senhas não batem");
		} else {



			$.ajax({
				
				type: "POST",
				url: PROJETO.PATH + "usuarios/cadastrar",
				data: JSON.stringify(dadosUsuario),
				success: function(msg) {
					
					alert(msg);
					$("#usuarioCad").trigger("reset");
				},
				error: function(info) {
					alert(info + info.status + "-" + info.statusText);
				}

			});
		}
	};

	PROJETO.usuarios.excluir = function(id) {
		$.ajax({
			type: "DELETE",
			url: PROJETO.PATH + "usuarios/excluir/" + id,
			success: function(msg) {
				alert(msg);
				PROJETO.usuarios.trazerUsuarios();
			},
			error: function(info) {
				alert("Erro ao excluir usuario: " + info.status + " - " + info.statusText);
			}
		});
	};


	PROJETO.usuarios.exibirEdicao = function(id) {
		$.ajax({
			type: "GET",
			url: PROJETO.PATH + "usuarios/buscarPorId",
			data: "id=" + id,
			success: function(dadosUsuarios) {
				
				document.frmEditaUsuario.idUsuario.value = dadosUsuarios.id;
				document.frmEditaUsuario.nome.value = dadosUsuarios.usuario;
				document.frmEditaUsuario.email.value = dadosUsuarios.email;
				document.frmEditaUsuario.telefone.value = dadosUsuarios.telefone;

				var tipoUsuarios = document.getElementById('tipoUsuario');
				for (var i = 0; i < tipoUsuarios.length; i++) {
					if (tipoUsuarios.options[i].value == dadosUsuarios.tipoUsuario) {
						tipoUsuarios.options[i].setAttribute("selected", "selected");
					} else {
						tipoUsuarios.options[i].removeAttribute("selected");
					}
				}

				$("#modalEditaUsuario");

			},
			error: function(info) {
				alert(info + info.status + "-" + info.statusText);
			}

		});
	};

	PROJETO.usuarios.editar = function() {
		var dadosUsuario = new Object();
		dadosUsuario.id = document.frmEditaUsuario.idUsuario.value;
		dadosUsuario.usuario = document.frmEditaUsuario.nome.value;
		dadosUsuario.email = document.frmEditaUsuario.email.value;
		dadosUsuario.telefone = document.frmEditaUsuario.telefone.value;
		dadosUsuario.tipoUsuario = document.frmEditaUsuario.tipoUsuario.value;
		dadosUsuario.senha = document.frmEditaUsuario.senha.value;

		if ((dadosUsuario.usuario == "") || (dadosUsuario.email == "") || (dadosUsuario.telefone == "")) {
			alert("Preencha todos os campos");
			return false;
		}

		$.ajax({
			type: "PUT",
			url: PROJETO.PATH + "usuarios/alterar",
			data: JSON.stringify(dadosUsuario),
			success: function(msg) {
				alert(msg);
				PROJETO.usuarios.trazerUsuarios();
			},
			error: function(info) {
				alert(info + info.status + "-" + info.statusText);
			}
		});
	};





























});

