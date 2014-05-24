package com.senac.night_control.model;

public class FestaConsumo {
	
	private int id;
	//ForeignKey festa_id
	private Festa festa_id;
	//ForeignKey bebida_id
	private Bebida bebida_id;
	private double valor_bebida;
	private int quantidade_consumida;
	
	
	public FestaConsumo(int id, Festa festa_id, Bebida bebida_id, double valor_bebida, int quantidade_consumida) {
		this.id = id;
		this.festa_id = festa_id;
		this.bebida_id = bebida_id;
		this.valor_bebida = valor_bebida;
		this.quantidade_consumida = quantidade_consumida;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Festa getFesta_id() {
		return festa_id;
	}
	public void setFesta_id(Festa festa_id) {
		this.festa_id = festa_id;
	}
	public Bebida getBebida_id() {
		return bebida_id;
	}
	public void setBebida_id(Bebida bebida_id) {
		this.bebida_id = bebida_id;
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

}
