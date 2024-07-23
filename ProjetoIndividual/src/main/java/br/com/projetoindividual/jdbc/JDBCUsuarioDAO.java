package br.com.projetoindividual.jdbc;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.projetoindividual.arma.Usuario;
import br.com.projetoindividual.jdbcinterface.*;

public class JDBCUsuarioDAO implements UsuarioDAO {

	private Connection conexao;

	public JDBCUsuarioDAO(Connection conexao) {
		this.conexao = conexao;
	}

	public List<Usuario> buscar() {
		String comando = "SELECT * FROM login";
		List<Usuario> listUsuarios = new ArrayList<Usuario>();

		Usuario dados = null;

		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while (rs.next()) {
				dados = new Usuario();

				int id = rs.getInt("id");
				String usuarios = rs.getString("usuario");
				String email = rs.getString("email");
				String telefone = rs.getString("telefone");
				String tipoUsuario = rs.getString("tipoUsuario");

				dados.setId(id);
				dados.setUsuario(usuarios);
				dados.setEmail(email);
				dados.setTelefone(telefone);
				dados.setTipoUsuario(tipoUsuario);
				
				listUsuarios.add(dados);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return listUsuarios;
	}

	public boolean inserirUsuario(Usuario usuarioss) {
		
		String senmd5 = "";
		MessageDigest md = null;

		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		BigInteger hash = new BigInteger(1, md.digest(usuarioss.getSenha().getBytes()));
		senmd5 = hash.toString(16);
		usuarioss.setSenha(senmd5);

		String comando = "SELECT COUNT(*) from login WHERE usuario = ?";
		PreparedStatement consulta;
		try {
			consulta = this.conexao.prepareStatement(comando);
			consulta.setString(1, usuarioss.getUsuario());

			// Executa a consulta
			ResultSet resultado = consulta.executeQuery();

			// Se o resultado contiver alguma linha, isso significa que o usuario com o mesmo
			// nome já existe
			if (resultado.next()) {
				int count = resultado.getInt(1);
				if (count > 0) {
					// usuario com o mesmo nome já existe, não é possível inserir novamente
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		comando = "INSERT INTO login " + "(usuario, email, telefone, tipoUsuario, senha)" + " VALUES (?,?,?,?,?)";

		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);

			p.setString(1, usuarioss.getUsuario());
			p.setString(2, usuarioss.getEmail());
			p.setString(3, usuarioss.getTelefone());
			p.setString(4, usuarioss.getTipoUsuario());
			p.setString(5, usuarioss.getSenha());

			p.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public boolean deletar(int id) {
		if (id == 1) {
			return false;
		} else {
			String comando = "DELETE FROM login WHERE id = ?";
			PreparedStatement p;
			try {
				p = this.conexao.prepareStatement(comando);
				p.setInt(1, id);
				p.execute();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
	}

	public Usuario buscarPorId(int id) {
		String comando = "SELECT * FROM login WHERE id = ?";
		Usuario dadosUsuario = new Usuario();

		try {
			PreparedStatement p = this.conexao.prepareStatement(comando);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				String nome = rs.getString("usuario");
				String email = rs.getString("email");
				String telefone = rs.getString("telefone");
				String tipoUsuario = rs.getString("tipoUsuario");

				dadosUsuario.setId(id);
				dadosUsuario.setUsuario(nome);
				dadosUsuario.setEmail(email);
				dadosUsuario.setTelefone(telefone);
				dadosUsuario.setTipoUsuario(tipoUsuario);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dadosUsuario;
	}

	public boolean alterar(Usuario usuario) {
		String senmd5 = "";
		MessageDigest md = null;

		String comando = "UPDATE login " + "SET usuario= ?, email= ?, telefone= ?, tipoUsuario= ? ";

		if (!usuario.getSenha().isEmpty()) {
			try {
				md = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			BigInteger hash = new BigInteger(1, md.digest(usuario.getSenha().getBytes()));
			senmd5 = hash.toString(16);
			usuario.setSenha(senmd5);

			comando += "senha= ? ";
		}
		comando += " WHERE id= ? ";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			p.setString(1, usuario.getUsuario());
			p.setString(2, usuario.getEmail());
			p.setString(3, usuario.getTelefone());
			p.setString(4, usuario.getTipoUsuario());

			if (!usuario.getSenha().isEmpty()) {
				p.setString(5, usuario.getSenha());
				p.setInt(6, usuario.getId());
			} else {
				p.setInt(5, usuario.getId());

			}

			p.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}
}