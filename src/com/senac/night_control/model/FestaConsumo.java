package com.senac.night_control.model;

import java.sql.Date;

public class FestaConsumo {

	private int id;
	private int festa_id;
	private double consumo_limite;
	private double valor_bebida;
	private int quantidade_consumida;
	private String data;
	private int pub_id;
	private int nota_festa;
	private int user_id;
	private int isActive;



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFesta_id() {
		return festa_id;
	}

	public void setFesta_id(int festa_id) {
		this.festa_id = festa_id;
	}

	public double getConsumo_limite() {
		return consumo_limite;
	}

	public void setConsumo_limite(double consumo_limite) {
		this.consumo_limite = consumo_limite;
	}

	public double getValor_bebida() {
		return valor_bebida;
	}

	public void setValor_bebida(double valor_bebida) {
		this.valor_bebida = valor_bebida;
	}

	public int getQuantidade_consumida() {
		return quantidade_consumida;
	}

	public void setQuantidade_consumida(int quantidade_consumida) {
		this.quantidade_consumida = quantidade_consumida;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getNota_festa() {
		return nota_festa;
	}

	public void setNota_festa(int nota_festa) {
		this.nota_festa = nota_festa;
	}

	public int getPub_id() {
		return pub_id;
	}

	public void setPub_id(int pub_id) {
		this.pub_id = pub_id;
	}

	
	public int getUser_id() {
		return user_id;
	}

	
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	
	
	


	
	
}
