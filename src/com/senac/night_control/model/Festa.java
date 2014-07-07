package com.senac.night_control.model;

public class Festa {
	
	private int id;
	private String FestaNome;

	public Festa(String FestaNome) {

		this.FestaNome = FestaNome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFestaNome() {
		return FestaNome;
	}

	public void setFestaNome(String festaNome) {
		FestaNome = festaNome;
	}


	
	
}
