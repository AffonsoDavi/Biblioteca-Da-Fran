
PROJETO.livros = new Object();

$(document).ready(function() {

    PROJETO.livros.trazerLivros = function(cond) {
        $.ajax({
            type: "GET",
            url: PROJETO.PATH + "livros/procurar",
            success: function(livros) {
                if (cond === 1) {
					
                    gerarTabelaRelatorioLivros(livros, cond);
                } else if (cond === 0) {
					
                    abrirModal('modalRelatorioLivros');
                    gerarTabelaRelatorioLivros(livros, cond);
                } else {
				
                    abrirModal('modalGerirLivros');
                    gerarTabelaRelatorioLivros(livros, cond);
                }

            },
            error: function(info) {
                alert(info + info.status + "-" + info.statusText);
            }
        });
    }

    PROJETO.livros.trazerLivros(1);

    function gerarTabelaRelatorioLivros(dadosLivros, cond) {
		var tabela = '<table class="tabela" border="1">';
		tabela += '<tr><th>Titulo</th><th>Autor</th><th>Paginas</th><th>Gênero</th>';
		if ((cond != 0) && (cond != 1)) {
			tabela += '<th>Ações</th>';
		}
		tabela += '</tr>';

		for (var i = 0; i < dadosLivros.length; i++) {
			tabela += '<tr>';
			tabela += '<td>' + dadosLivros[i].titulo + '</td>';
			tabela += '<td>' + dadosLivros[i].autor + '</td>';
			tabela += '<td>' + dadosLivros[i].numeroPag + '</td>';
			tabela += '<td>' + dadosLivros[i].genero + '</td>';

			if ((cond != 0) && (cond != 1)) {
				tabela += '<td><a onclick="abrirModal(\'modalEditaLivro\');PROJETO.livros.exibirEdicao(' + dadosLivros[i].id + ')"><img src=\'../../imgs/edit.png\' alt=\'Editar registro\'></a>' +
					'<a onclick="PROJETO.livros.excluir(' + dadosLivros[i].id + ')"><img src=\'../../imgs/delete.png\' alt=\'Excluir registro\'></a></td>';
			}

			tabela += '</tr>';
		}

		tabela += '</table>';

		// Exiba a tabela no id "tabelaRelatorioUsuarios"
		if (cond === 1) {
			
			$('#livrosTabela').html(tabela);
		} else if (cond != 0) {
			
			$('#tabelaGerirLivros').html(tabela);
		} else {
			
			$('#tabelaRelatorioLivros').html(tabela);
		}
	}

	PROJETO.livros.inserirLivros = function() {
		var dadosLivro = new Object();
		dadosLivro.titulo = document.frmCadastraLivro.titulo.value;
		dadosLivro.autor = document.frmCadastraLivro.autor.value;
		dadosLivro.numeroPag = document.frmCadastraLivro.paginas.value;
		dadosLivro.genero = document.frmCadastraLivro.genero.value;

		if ((dadosLivro.genero == "selecione") || (dadosLivro.AUTOR == "") || (dadosLivro.paginas == "") || (dadosLivro.titulo == "")) {
			alert("Preencha Todos os campos!");
		} else {

			$.ajax({
				type: "POST",
				url: PROJETO.PATH + "livros/cadastrar",
				data: JSON.stringify(dadosLivro),
				success: function(msg) {
					alert(msg);
					$("#CadastraLivro").trigger("reset");
					fecharModal('cadastraLivro');
				},
				error: function(info) {
					alert(info + info.status + "-" + info.statusText);
				}

			});
		}
	};

	PROJETO.livros.excluir = function(id) {
		$.ajax({
			type: "DELETE",
			url: PROJETO.PATH + "livros/excluir/" + id,
			success: function(msg) {
				alert(msg);

				PROJETO.livros.trazerLivros();
			},
			error: function(info) {
				alert("Erro ao excluir Livro: " + info.status + " - " + info.statusText);
			}
		});
	};

	PROJETO.livros.exibirEdicao = function(id) {
		$.ajax({
			type: "GET",
			url: PROJETO.PATH + "livros/buscarPorId",
			data: "id=" + id,
			success: function(dadosLivros) {
				
				document.frmEditaLivro.idLivro.value = dadosLivros.id;
				document.frmEditaLivro.tituloEdita.value = dadosLivros.titulo;
				document.frmEditaLivro.autorEdita.value = dadosLivros.autor;
				document.frmEditaLivro.paginasEdita.value = dadosLivros.numeroPag;
				document.frmEditaLivro.generoEdita.value = dadosLivros.genero;


				var tipoGenero = document.getElementById('genero');
				for (var i = 0; i < tipoGenero.length; i++) {
					if (tipoGenero.options[i].value == dadosLivros.tipoGenero) {
						tipoGenero.options[i].setAttribute("selected", "selected");
					} else {
						tipoGenero.options[i].removeAttribute("selected");
					}
				}

				$("#modalEditaLivro");

			},
			error: function(info) {
				alert(info + info.status + "-" + info.statusText);
			}

		});
	};



	PROJETO.livros.editar = function() {
		var dadosLivros = new Object();
	
		dadosLivros.id = document.frmEditaLivro.idLivro.value;
		dadosLivros.titulo = document.frmEditaLivro.tituloEdita.value;
		dadosLivros.autor = document.frmEditaLivro.autorEdita.value;
		dadosLivros.numeroPag = document.frmEditaLivro.paginasEdita.value;
		dadosLivros.genero = document.frmEditaLivro.generoEdita.value;
		
		if ((dadosLivros.titulo == "") || (dadosLivros.autor == "") || (dadosLivros.paginas == "")) {
			alert("Preencha todos os campos");
			return false;
		}
	
		$.ajax({
			type: "PUT",
			url: PROJETO.PATH + "livros/alterar",
			data: JSON.stringify(dadosLivros),
			success: function(msg) {
				alert(msg);
				PROJETO.livros.trazerLivros();
			},
			error: function(info) {
				alert(info + info.status + "-" + info.statusText);
			}
		});
	};


});