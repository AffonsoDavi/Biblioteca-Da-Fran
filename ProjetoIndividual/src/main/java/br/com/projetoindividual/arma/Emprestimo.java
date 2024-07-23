package br.com.projetoindividual.arma;

import java.io.Serializable;
import java.util.ArrayList;

public class Emprestimo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String data;
	private ArrayList<EmprestimoLivros> livros = new ArrayList<EmprestimoLivros>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public ArrayList<EmprestimoLivros> getLivros() {
		return livros;
	}
	public void setLivros(ArrayList<EmprestimoLivros> livros) {
		this.livros = livros;
	}
	
	
	
}
