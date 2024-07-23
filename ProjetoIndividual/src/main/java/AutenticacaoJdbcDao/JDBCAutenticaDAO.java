package AutenticacaoJdbcDao;

import java.sql.Connection;
import java.sql.ResultSet;
import ArmazenaDadosUsuario.ArmaDadosUsuario;

public class JDBCAutenticaDAO {
	
	private Connection conexao;

	public JDBCAutenticaDAO(Connection conexao) {
		this.conexao = conexao;

	}
	public boolean consultar (ArmaDadosUsuario dadosautentica) {
		
		try {

			String comando = "SELECT * FROM login where usuario = '"+dadosautentica.getUsuario()+"' and senha = '"+dadosautentica.getSenha()+"'";
			java.sql.Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			if(rs.next()){
				return true;
			}else {
				return false;
			}
		}catch(Exception e) {
			return false;
		}
	}
	
}
