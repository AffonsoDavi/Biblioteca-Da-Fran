package br.com.projetoindividual.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.com.projetoindividual.arma.*;
import br.com.projetoindividual.jdbc.JDBCUsuarioDAO;
import conectar.Conexao;

@Path("usuarios")
public class UsuarioRest extends UtilRest {

	@GET
	@Path("/procurar")
	@Produces(MediaType.APPLICATION_JSON)

	public Response buscar() {

		try {
			List<Usuario> listaUsuarios = new ArrayList<Usuario>();

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCUsuarioDAO jdbcUsuario = new JDBCUsuarioDAO(conexao);
			listaUsuarios = jdbcUsuario.buscar();
			conec.fecharConexao();
			return this.buildResponse(listaUsuarios);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	
	@POST
	@Path("/cadastrar")
	@Consumes("application/*")
	public Response inserirUsuario(String usuarioParam) {
		try {
		
			Usuario usuario = new Gson().fromJson(usuarioParam, Usuario.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCUsuarioDAO jdbcUsuario = new JDBCUsuarioDAO(conexao);
						
			
			boolean retorno = jdbcUsuario.inserirUsuario(usuario);
			
			String msg = "";
				if(retorno) {
					msg= "Usuario cadastrado com sucesso!";
				}else{
					msg = "Erro ao cadastrar usuario, ou nome de usuario já cadastrado";
				}
			conec.fecharConexao();
			return this.buildResponse(msg);
			
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@DELETE
	@Path("/excluir/{id}")
	@Consumes("application/*")
	public Response excluir(@PathParam("id")int id) {
		try {
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCUsuarioDAO jdbcUsuario = new JDBCUsuarioDAO(conexao);
			
			boolean retorno = jdbcUsuario.deletar(id);
			
				String msg = "";
				if(retorno) {
					msg= "Usuario Excluída com sucesso!";
				}else{
					msg = "Erro ao excluir usuário, ou não é possível excluir usuário administrador";
				}
				
			conec.fecharConexao();
			
			return this.buildResponse(msg);
			
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
			
		}
	}
	
	@GET
	@Path("/buscarPorId")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorId(@QueryParam("id") int id) {
		try {
			
			Usuario dadosusuario = new Usuario();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCUsuarioDAO jdbcUsuario = new JDBCUsuarioDAO(conexao);
			
			dadosusuario = jdbcUsuario.buscarPorId(id);
			
			conec.fecharConexao();
			return this.buildResponse(dadosusuario);
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@PUT
	@Path("/alterar")
	@Consumes("application/*")
	public Response alterar(String usuarioParam) {
	
		try {
			Usuario usuario = new Gson().fromJson(usuarioParam, Usuario.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCUsuarioDAO jdbcUsuario = new JDBCUsuarioDAO(conexao);
				
			boolean retorno = jdbcUsuario.alterar(usuario);

			String msg = "";
			if(retorno) {
				msg= "Usuario alterado com sucesso!";
			}else{
				msg = "Erro ao alterar usuario.";
			}
		conec.fecharConexao();
		return this.buildResponse(msg);
		
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	

}
