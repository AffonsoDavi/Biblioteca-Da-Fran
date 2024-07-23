package br.com.projetoindividual.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.projetoindividual.arma.*;
import br.com.projetoindividual.jdbcinterface.*;

public class JDBCLivrosDAO implements LivrosDAO {

	private Connection conexao;

	public JDBCLivrosDAO(Connection conexao) {
		this.conexao = conexao;
	}

	public List<Livros> buscar() {
		String comando = "SELECT * from livros";
		List<Livros> listLivros = new ArrayList<Livros>();

		Livros dados = null;

		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while (rs.next()) {
				dados = new Livros();

				int id = rs.getInt("id");
				String titulo = rs.getString("titulo");
				String autor = rs.getString("autor");
				int numeroPag = rs.getInt("numeroPag");
				String genero = rs.getString("genero");

				dados.setId(id);
				dados.setTitulo(titulo);
				dados.setAutor(autor);
				dados.setNumeroPag(numeroPag);
				dados.setGenero(genero);

				listLivros.add(dados);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listLivros;
	}

	public boolean inserirLivro(Livros livros) {
		String comando = "SELECT count(*) from livros where titulo = ?";
		PreparedStatement consulta;
		try {
			consulta = this.conexao.prepareStatement(comando);
			consulta.setString(1, livros.getTitulo());

			ResultSet resultado = consulta.executeQuery();

			if (resultado.next()) {
				int count = resultado.getInt(1);
				if (count > 0) {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		comando = "INSERT INTO livros " + "(titulo, autor, numeroPag, genero)" + " VALUES (?,?,?,?)";

		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);

			p.setString(1, livros.getTitulo());
			p.setString(2, livros.getAutor());
			p.setInt(3, livros.getNumeroPag());
			p.setString(4, livros.getGenero());

			p.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean deletar(int id) {
		String comando = "DELETE FROM livros where id = ?";
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

	public Livros buscarPorId(int id) {
		String comando = "SELECT * FROM livros WHERE id = ?";
		Livros dadosLivros = new Livros();

		try {
			PreparedStatement p = this.conexao.prepareStatement(comando);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				String titulo = rs.getString("titulo");
				String autor = rs.getString("autor");
				int numeroPag = rs.getInt("numeroPag");
				String genero = rs.getString("genero");

				dadosLivros.setId(id);
				dadosLivros.setTitulo(titulo);
				dadosLivros.setAutor(autor);
				dadosLivros.setNumeroPag(numeroPag);
				dadosLivros.setGenero(genero);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dadosLivros;
	}

	public boolean alterar(Livros livros) {

		String comando = "UPDATE livros " + "SET titulo= ?, autor= ?, numeroPag= ?, genero= ? where id = ?";

		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			p.setString(1, livros.getTitulo());
			p.setString(2, livros.getAutor());
			p.setInt(3, livros.getNumeroPag());
			p.setString(4, livros.getGenero());
			p.setInt(5, livros.getId());

			p.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}



}
