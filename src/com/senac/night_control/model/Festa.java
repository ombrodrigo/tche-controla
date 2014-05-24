package com.senac.night_control.model;

public class Festa {
	
	private int id;
	private Pub pub_id;
	private Usuario usuario_id;
	private String data_hora;
	private double consumo_limite;
	private int nota_festa;
	
		
	public Festa(int id, Pub pub_id, Usuario usuario_id, String data_hora, double consumo_limite, int nota_festa) {
		this.id = id;
		this.pub_id = pub_id;
		this.usuario_id = usuario_id;
		this.data_hora = data_hora;
		this.consumo_limite = consumo_limite;
		this.nota_festa = nota_festa;
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

	public String getData_hora() {
		return data_hora;
	}

	public void setData_hora(String data_hora) {
		this.data_hora = data_hora;
	}

	public double getConsumo_limite() {
		return consumo_limite;
	}

	public void setConsumo_limite(double consumo_limite) {
		this.consumo_limite = consumo_limite;
	}

	public int getNota_festa() {
		return nota_festa;
	}

	public void setNota_festa(int nota_festa) {
		this.nota_festa = nota_festa;
	}
	

}