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
import br.com.projetoindividual.jdbc.JDBCLivrosDAO;
import br.com.projetoindividual.jdbc.JDBCUsuarioDAO;
import conectar.Conexao;


@Path("livros")
public class LivrosRest extends UtilRest{
	@GET
	@Path("/procurar")
	@Produces(MediaType.APPLICATION_JSON)

	public Response buscar() {
		
		try {
			List<Livros> listaLivros = new ArrayList<Livros>();

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCLivrosDAO jdbcLivros = new JDBCLivrosDAO(conexao);
			listaLivros = jdbcLivros.buscar();
			conec.fecharConexao();
			return this.buildResponse(listaLivros);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@POST
	@Path("/cadastrar")
	@Consumes("application/*")
	public Response inserirLivros(String livroParam) {
		try {
		
			Livros livro = new Gson().fromJson(livroParam, Livros.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCLivrosDAO jdbcLivros = new JDBCLivrosDAO(conexao);
						
			
			boolean retorno = jdbcLivros.inserirLivro(livro);
			
			String msg = "";
				if(retorno) {
					msg= "Livro cadastrado com sucesso!";
				}else{
					msg = "Erro ao cadastrar livro, ou livro já cadastrado";
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
			JDBCLivrosDAO jdbcLivros = new JDBCLivrosDAO(conexao);
			
			boolean retorno = jdbcLivros.deletar(id);
			
				String msg = "";
				if(retorno) {
					msg= "Livro Excluído com sucesso!";
				}else{
					msg = "Erro ao excluir livro!";
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
			
			Livros dadosLivro = new Livros();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCLivrosDAO jdbcLivros = new JDBCLivrosDAO(conexao);
			
			dadosLivro = jdbcLivros.buscarPorId(id);
			
			conec.fecharConexao();
			return this.buildResponse(dadosLivro);
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@PUT
	@Path("/alterar")
	@Consumes("application/*")
	public Response alterar(String LivrosParam) {
		
		try {
			Livros livros = new Gson().fromJson(LivrosParam, Livros.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCLivrosDAO jdbcLivros = new JDBCLivrosDAO(conexao);
			
			boolean retorno = jdbcLivros.alterar(livros);

			String msg = "";
			if(retorno) {
				msg= "Livro alterado com sucesso!";
			}else{
				msg = "Erro ao alterar livro.";
			}
		conec.fecharConexao();
		return this.buildResponse(msg);
		
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
}
