package br.com.projetoindividual.rest;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import LoginServlet.AutenticacaoServlet;
import br.com.projetoindividual.arma.Emprestimo;
import br.com.projetoindividual.arma.EmprestimoLivros;
import br.com.projetoindividual.jdbc.JDBCEmprestimoDAO;
import conectar.Conexao;

@Path("emprestimo")
public class EmprestimoRest extends UtilRest {

	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public Response inserir(String emprestimoParam, @Context HttpServletRequest request) {
        // Agora você pode usar o objeto request para acessar a sessão
        AutenticacaoServlet usuario = (AutenticacaoServlet) request.getAttribute("login");
        Long id = usuario.getId(); // Aqui você já tem o id do usuário
        
        
		try {
			Emprestimo emprestimo = new Gson().fromJson(emprestimoParam, Emprestimo.class);

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			JDBCEmprestimoDAO jdbcEmprestimo = new JDBCEmprestimoDAO(conexao);
			
			boolean retorno = jdbcEmprestimo.inserir(emprestimo);
			String msg = "Erro ao cadastrar emprestimo";
			if(retorno) {
				msg = "Emprestimo realizado com sucesso!";
			}
			return this.buildResponse(msg);

		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}

}
