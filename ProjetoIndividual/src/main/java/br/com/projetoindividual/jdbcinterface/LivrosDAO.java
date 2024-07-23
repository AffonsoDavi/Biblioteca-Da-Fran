package br.com.projetoindividual.jdbcinterface;

import java.util.List;

import br.com.projetoindividual.arma.*;

public interface  LivrosDAO {

	public List<Livros> buscar();
	public boolean inserirLivro(Livros livros);
	public boolean deletar(int id);
	public Livros buscarPorId(int id);
	public boolean alterar(Livros livros);

}
