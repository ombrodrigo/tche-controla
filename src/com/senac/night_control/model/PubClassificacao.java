package com.senac.night_control.model;

public class PubClassificacao {
	
	private int id;
	//ForeignKey pub_id
	private Pub pub_id;
	//ForeignKey usuario_id
	private Usuario usuario_id;
	private int classificacao;
	
	public PubClassificacao(int id, Pub pub_id, Usuario usuario_id, int classificacao) {
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
	public Pub getPub_id() {
		return pub_id;
	}
	public void setPub_id(Pub pub_id) {
		this.pub_id = pub_id;
	}
	public Usuario getUsuario_id() {
		return usuario_id;
	}
	public void setUsuario_id(Usuario usuario_id) {
		this.usuario_id = usuario_id;
	}
	public int getClassificacao() {
		return classificacao;
	}
	public void setClassificacao(int classificacao) {
		this.classificacao = classificacao;
	}

}
