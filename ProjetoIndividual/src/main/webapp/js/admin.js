// Função genérica para abrir uma modal
function abrirModal(modalId) {
	var modal = document.getElementById(modalId);
	modal.style.display = 'block';
}

// Função genérica para fechar uma modal
function fecharModal(modalId) {
	var modal = document.getElementById(modalId);
	modal.style.display = 'none';
}

PROJETO = new Object();
PROJETO.PATH = "/ProjetoIndividual/rest/";


function abreRelatorios(tipo) {
	if (tipo.value === "usuarios") {
		var cond = 0;
		PROJETO.usuarios.trazerUsuarios(cond);
	}else if(tipo.value === "livros"){
		var cond = 0;
		PROJETO.livros.trazerLivros(cond)
	}
}

function redireciona(){
	window.location.href = "http://localhost:8080/ProjetoIndividual/index.html";
}

function gerarTabelaRelatorioLivros() {

	//NO FUTURO SUBSTITUIR POR DADOS DO BANCO
	var dadosLivros = [
		{ titulo: 'a arte do front', autor: 'uchoin', genero: 'Chatisse', emprestado: 'SIM' },
		{ titulo: 'a arte do back', autor: 'silvin', genero: 'Ação', emprestado: 'NAO' }

	];

	var tabela = '<table>';
	tabela += '<tr><th>Titulo</th><th>Autor</th><th>Gênero</th><th>Emprestado?</th></tr>';

	for (var i = 0; i < dadosLivros.length; i++) {
		tabela += '<tr>';
		tabela += '<td>' + dadosLivros[i].titulo + '</td>';
		tabela += '<td>' + dadosLivros[i].autor + '</td>';
		tabela += '<td>' + dadosLivros[i].genero + '</td>';
		tabela += '<td>' + dadosLivros[i].emprestado + '</td>';
		tabela += '</tr>';
	}

	tabela += '</table>';

	// Exiba a tabela no id "tabelaRelatorioLivros"
	document.getElementById('tabelaRelatorioLivros').innerHTML = tabela;
}




//NO FUTURO SUBSTITUIR POR DADOS DO BANCO
var livrosGeral = {
	total: 100,
	emprestados: 75,
	emEstoque: 25,
	livros: [
		{ titulo: 'a arte do front', autor: 'uchoin', genero: 'Chatisse', emprestado: 'SIM' },
		{ titulo: 'a arte do back', autor: 'silvin', genero: 'Ação', emprestado: 'NAO' },
	]
};

// preenche a modal com os dados dos usuários
function preencherModalLivros() {
	document.getElementById('quantidadeLivros').innerHTML = livrosGeral.total; /*INFO É EXIBIDA NO ID "quantidadeUsuarios" */
	document.getElementById('livrosEmprestados').innerHTML = livrosGeral.emprestados; /*INFO É EXIBIDA NO ID "usuariosAtivos" */
	document.getElementById('livrosEmEstoque').innerHTML = livrosGeral.emEstoque;/*INFO É EXIBIDA NO ID "usuariosInativos" */

	var tabelaLivros = '<table>';
	tabelaLivros += '<tr><th>Nome</th><th>genero</th><th>Autor</th><th>Emprestado?</th></tr>';

	for (var i = 0; i < livrosGeral.livros.length; i++) {
		tabelaLivros += '<tr>';
		tabelaLivros += '<td>' + livrosGeral.livros[i].titulo + '</td>';
		tabelaLivros += '<td>' + livrosGeral.livros[i].genero + '</td>';
		tabelaLivros += '<td>' + livrosGeral.livros[i].autor + '</td>';
		tabelaLivros += '<td>' + livrosGeral.livros[i].emprestado + '</td>';

		tabelaLivros += '</tr>';
	}

	tabelaLivros += '</table>';
	// Exiba a tabela no id "tabelaGerirLivros"
	document.getElementById('tabelaGerirLivros').innerHTML = tabelaLivros;
}




