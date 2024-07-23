package br.com.projetoindividual.jdbcinterface;

import java.sql.SQLException;

import br.com.projetoindividual.arma.EmprestimoLivros;

public interface EmprestimoLivrosDAO {
	
	public boolean inserir(EmprestimoLivros emprestimoLivros) throws SQLException;
}
