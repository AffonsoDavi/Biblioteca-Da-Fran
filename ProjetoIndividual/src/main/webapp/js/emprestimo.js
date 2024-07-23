PROJETO.emprestimo = new Object();

$(document).ready(function() {

	/*AQUI INICIA OS ARRAYS PARA LIVROSANTIGO
		COM ELES CONTROLAREI CADA MUDANÇA FEITA NOS RESPECTIVOS CAMPOS DO FORMULARIO
		AGORA ELES SÓ TERAO UMA POSIÇÃO, A 0, COM VALORES VAZIO.*/
	livroAntigo = new Array();
	livroAntigo[0] = "";
	/*FUNCAO PARA CAPTAR OS VALORES ATUAIS DOS CAMPOS DO FORMULARIO */

	/*ELA RECEBE OS VALORES DOS CAMPOS EXISTENTES E GUARDA NOS ARRAYS CRIADOS NO INICIO DESSE ARQUIVO
	COM ISSO, PODEMOS SABER QUAL LINHA FOI ALTERADA PARA CARREGARMOS OS EMPRESTIMOS NA LINHA CORRETA*/

	PROJETO.emprestimo.guardaValores = function() {
		//recebe todas as posições dos campos sellivro[]
		var livroAtual = document.getElementsByName('selLivro[]');

		for (var i = 0; i < livroAtual.length; i++) {
				//captura e salva somente os valores atuais nos arrays criados no inicio do arquivo
				livroAntigo[i] = livroAtual[i].value;
			
		}
	}



	PROJETO.emprestimo.carregarLivros = function(id) {
		var camposLivros = document.getElementsByName('selLivro[]');

		$.ajax({
			type: "GET",
			url: PROJETO.PATH + "livros/procurar",
			success: function(livros) {

				$(camposLivros[id]).html("");
				//verifica se tem mais de 1 livro
				if (livros.length) {
					//cria um vazio para validar depois
					var option = document.createElement("option");
					option.setAttribute("value", "");
					option.innerHTML = ("Escolha");
					//coloca o campo no select correto
					$(camposLivros[id]).append(option);
					//para cada marca...
					for (var i = 0; i < livros.length; i++) {
						//verifica se o livro ja nao foi selecionado antes.

						//cria uma opcao com id e o nome do livro
						var option = document.createElement("option");
						option.setAttribute("value", livros[i].id);
						option.innerHTML = (livros[i].titulo);
						//coloca essa opcao no select
						$(camposLivros[id]).append(option);
					}
					 	 	 	 		 	 
					//caso não haja livro no BD
				} else {
					//cria uma frase dizendo para cadastrar um livro primeiro
					var option = document.createElement("option");
					option.setAttribute("value", "");
					option.innerHTML = ("Cadastre um livro primeiro");

					$(camposLivros[id]).addClass("aviso");
					$(camposLivros[id]).append(option);
				}
			},
			error: function() {
				alert("erro");

				$(camposLivros[id]).html("");
				var option = document.createElement("option");
				option.setAttribute("value", "");
				option.innerHTML = ("Erro ao carregar livros!");
				$(camposLivros[id]).addClass("aviso");
				$(camposLivros[id]).append(option);
			}
		});
	}
	PROJETO.emprestimo.carregarLivros(0);

	var conta = 1;
	$(".botaoAdd").click(function() {
		if (conta < 3) {
			conta = conta + 1;
			var novoCampo = $("div.detalhes:first").clone();

			novoCampo.find("input").val("");
			novoCampo.insertAfter("div.detalhes:last");

			//chama a função guardaValores para guardar os valores atuais dos livros
			PROJETO.emprestimo.guardaValores();


		} else {
			alert("não é possivel realizar mais que 3 emprestimos por vez!")
		}
	});

	PROJETO.emprestimo.removeCampo = function(botao) {

		if ($("div.detalhes").length > 1) {
			conta = conta - 1;
			$(botao).parent().remove();

			PROJETO.emprestimo.guardaValores();

		} else {
			alert("A última linha não pode ser removida");
		}
	}

	/*FUNCAO PARA CARREGAR OS EMPRESTIMOS CORRETOS EM CADA LINHA DO FORMULARIO */
	PROJETO.emprestimo.carregarEmprestimos = function() {
		//chama a função guardaValores para guardar os valores atuais dos livros
		PROJETO.emprestimo.guardaValores();
	}
	
	PROJETO.emprestimo.validaDetalhe = function(){
		
		var livroValidar = document.getElementsByName("selLivro[]");
		
		for(var i=0; i < livroValidar.length; i++){
			var linha = i + 1;
			
			if(livroValidar[i].value==""){
				alert("A linha "+linha+" não foi preenchida com um livro");
				return false;
			}
		}
		return true;
	}
	

	PROJETO.emprestimo.emprestar = function(){
		if(PROJETO.emprestimo.validaDetalhe()){
			
			var emprestimo = new Object();
			
			emprestimo.data = document.frmEmprestimo.dataEmprestimo.value;
			var livros = document.getElementsByName('selLivro[]');
			
			emprestimo.livros = new Array(livros.length);
			
			for(var i = 0; i < livros.length; i++){
				emprestimo.livros[i]=new Object();
				emprestimo.livros[i].idLivro = livros[i].value;
			}
			
			
			$.ajax({
				type:"POST",
				url: PROJETO.PATH + "emprestimo/inserir",
				data:JSON.stringify(emprestimo),
				success: function(msg){
					alert(msg);
				},
				error:function (info){
					alert("Erro ao cadastrar um novo emprestimo: "+info.status + info.statusText);
				}
			});
			
			
		}
	}



});