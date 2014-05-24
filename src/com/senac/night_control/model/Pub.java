package com.senac.night_control.model;

public class Pub {
	
	private int id;
	private String pub_nome;
	
	public Pub(int id, String pub_nome) {
		this.id = id;
		this.pub_nome = pub_nome;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPub_nome() {
		return pub_nome;
	}

	public void setPub_nome(String pub_nome) {
		this.pub_nome = pub_nome;
	}
	
}
