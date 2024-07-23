package br.com.projetoindividual.jdbcinterface;

import java.util.List;


import br.com.projetoindividual.arma.*;

public interface  UsuarioDAO {

	public List<Usuario> buscar();
	public boolean inserirUsuario(Usuario usuario);
	public boolean deletar(int id);
	public Usuario buscarPorId(int id);
	public boolean alterar(Usuario usuario);
}
