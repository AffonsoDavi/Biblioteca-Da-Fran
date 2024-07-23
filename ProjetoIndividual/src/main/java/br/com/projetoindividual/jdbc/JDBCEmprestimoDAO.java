package br.com.projetoindividual.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.projetoindividual.arma.Emprestimo;
import br.com.projetoindividual.arma.EmprestimoLivros;

public class JDBCEmprestimoDAO {
	
	private Connection conexao;
	
	public JDBCEmprestimoDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	public boolean inserir(Emprestimo emprestimo) {
		String comando = "INSERT INTO emprestimos (idOperacao, dataEmprestimo) VALUES (?,?)";
		PreparedStatement p;
		try {
		
			
			p= this.conexao.prepareStatement(comando, PreparedStatement.RETURN_GENERATED_KEYS);
			p.setInt(1, emprestimo.getId());
			p.setString(2, emprestimo.getData());
		
			p.execute();
			
			
			
			ResultSet idGerado = p.getGeneratedKeys();
			
			while (idGerado.next()) {
				for(EmprestimoLivros infos: emprestimo.getLivros()) {		
					
					infos.setIdEmprestimo(idGerado.getInt(1));
					JDBCEmprestimoLivrosDAO jdbcEmprestimoLivros = new JDBCEmprestimoLivrosDAO(this.conexao);
					jdbcEmprestimoLivros.inserir(infos);
				}
				
			}
			
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
}
