package com.senac.night_control.model;

public class Usuario {
	
	private int id;
	private String nome;
	private String email;
	private String login;
	private String senha;
	private String ajuda_Nome;
	private String ajuda_Telefone;
	private Boolean sessao_ativa;
	
		
	
	public Usuario(String nome, String email, String login, String senha,
			String ajuda_Nome, String ajuda_Telefone, Boolean sessao_ativa) {
		super();
		this.nome = nome;
		this.email = email;
		this.login = login;
		this.senha = senha;
		this.ajuda_Nome = ajuda_Nome;
		this.ajuda_Telefone = ajuda_Telefone;
		this.sessao_ativa = sessao_ativa;
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

	public int getId() {
		return id;
	}


	public void setId(int i) {
		this.id = i;
	}


	public String getAjuda_Nome() {
		return ajuda_Nome;
	}


	public void setAjuda_Nome(String ajuda_Nome) {
		this.ajuda_Nome = ajuda_Nome;
	}


	public String getAjuda_Telefone() {
		return ajuda_Telefone;
	}


	public void setAjuda_Telefone(String ajuda_Telefone) {
		this.ajuda_Telefone = ajuda_Telefone;
	}


	public Boolean getSessao_ativa() {
		return sessao_ativa;
	}


	public void setSessao_ativa(Boolean sessao_ativa) {
		this.sessao_ativa = sessao_ativa;
	}
	
	
	
	
	
	

}
