package com.senac.night_control.model;

public class PubClassificacao {
	
	private int id;
	//ForeignKey pub_id
	private int pub_id;
	//ForeignKey usuario_id
	private int usuario_id;
	private int classificacao;
	
	public PubClassificacao(int id, int pub_id, int usuario_id, int classificacao) {
		this.id = id;
		this.pub_id = pub_id;
		this.usuario_id = usuario_id;
		this.classificacao = classificacao;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPub_id() {
		return pub_id;
	}
	public void setPub_id(int pub_id) {
		this.pub_id = pub_id;
	}
	public int getUsuario_id() {
		return usuario_id;
	}
	public void setUsuario_id(int usuario_id) {
		this.usuario_id = usuario_id;
	}
	public int getClassificacao() {
		return classificacao;
	}
	public void setClassificacao(int classificacao) {
		this.classificacao = classificacao;
	}

}
