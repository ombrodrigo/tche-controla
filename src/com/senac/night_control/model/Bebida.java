package com.senac.night_control.model;

public class Bebida {
	
	private int id;
	private String bebida_nome;
	
	public Bebida(int id, String bebida_nome) {
		this.id = id;
		this.bebida_nome = bebida_nome;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBebida_nome() {
		return bebida_nome;
	}
	public void setBebida_nome(String bebida_nome) {
		this.bebida_nome = bebida_nome;
	}
	

}
