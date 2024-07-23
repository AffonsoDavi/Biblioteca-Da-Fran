package LoginServlet;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ArmazenaDadosUsuario.ArmaDadosUsuario;
import AutenticacaoJdbcDao.JDBCAutenticaDAO;
import conectar.Conexao;

public class AutenticacaoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ArmaDadosUsuario dadosautentica = new ArmaDadosUsuario();
		dadosautentica.setUsuario(request.getParameter("email"));
		String senmd5 = "";
		MessageDigest md = null;

		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		BigInteger hash = new BigInteger(1, md.digest(request.getParameter("password").getBytes()));
		senmd5 = hash.toString(16);
		dadosautentica.setSenha(senmd5);

		Conexao conec = new Conexao();
		Connection conexao = conec.abrirConexao();

		JDBCAutenticaDAO jdbcAutentica = new JDBCAutenticaDAO(conexao);
		boolean retorno = jdbcAutentica.consultar(dadosautentica);
		if (retorno) {
			HttpSession sessao = request.getSession();
			sessao.setAttribute("login", request.getParameter("email"));

			if (request.getParameter("email").equals("davi")) {
				response.sendRedirect("pages/admin/index.html");
			} else {
				response.sendRedirect("pages/site/index.html");
			}
		} else {
			response.sendRedirect("erro.html");

		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    process(request, response);
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

}
