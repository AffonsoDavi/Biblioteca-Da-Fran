package br.com.projetoindividual.arma;

import java.io.Serializable;

public class EmprestimoLivros implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int idLivro;
	private int idEmprestimo;
	
	public int getIdLivro() {
		return idLivro;
	}
	public void setIdLivro(int idLivro) {
		this.idLivro = idLivro;
	}
	public int getIdEmprestimo() {
		return idEmprestimo;
	}
	public void setIdEmprestimo(int idEmprestimo) {
		this.idEmprestimo = idEmprestimo;
	}
	
}
