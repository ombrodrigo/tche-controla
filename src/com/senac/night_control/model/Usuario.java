package com.senac.night_control.model;

public class Usuario {
	
	private int id;
	private String nome;
	private String email;
	private String login;
	private String senha;
	private Boolean maiorDeIdade;
	
		
	
	public Usuario(String nome, String email, String login, String senha,
			Boolean maiorDeIdade) {
		super();
		this.nome = nome;
		this.email = email;
		this.login = login;
		this.senha = senha;
		this.maiorDeIdade = maiorDeIdade;
	}
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Boolean getMaiorDeIdade() {
		return maiorDeIdade;
	}
	public void setMaiorDeIdade(Boolean maiorDeIdade) {
		this.maiorDeIdade = maiorDeIdade;
	}


	public int getId() {
		return id;
	}


	public void setId(int i) {
		this.id = i;
	}
	
	
	
	

}
