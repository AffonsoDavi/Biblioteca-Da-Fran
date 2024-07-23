package conectar;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

	private Connection conexao;

	public Connection abrirConexao() {
		try {
			String url = "jdbc:mysql://localhost/projeto?user=root&password=root";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexao = DriverManager.getConnection(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conexao;
	}

	public void fecharConexao() {
		try {
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
