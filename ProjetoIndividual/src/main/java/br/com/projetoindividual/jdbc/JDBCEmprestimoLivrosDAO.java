package br.com.projetoindividual.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.projetoindividual.arma.EmprestimoLivros;
import br.com.projetoindividual.jdbcinterface.EmprestimoLivrosDAO;

public class JDBCEmprestimoLivrosDAO implements EmprestimoLivrosDAO{
	
	private Connection conexao;
	
	public JDBCEmprestimoLivrosDAO(Connection conexao) {
		this.conexao = conexao;
	}
	public boolean inserir(EmprestimoLivros emprestimoLivros) throws SQLException{
		
		String comando = "INSERT INTO emprestimos_has_livros "
				+ "(emprestimos_idOperacao, livros_id) values (?,?)";
		PreparedStatement p;
		
		p= this.conexao.prepareStatement(comando);
		
		p.setInt(1, emprestimoLivros.getIdEmprestimo());
		p.setInt(2, emprestimoLivros.getIdLivro());
		
		p.execute();
		
		return true;
	}

}
