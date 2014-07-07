package com.senac.utilities;

import com.senac.night_control.controller.Consumo;
import com.senac.night_control.model.FestaConsumo;

public class DataMananger {
	
	private double gastos,qtQueroGastar,precoBebida;
	private int quantidadeQuePossoBeber,quantasBebi;
	
	
	
	
	
	
	public DataMananger() {
		super();
		this.gastos = 0;
		this.qtQueroGastar = 0;
		this.precoBebida = 0;
		this.quantidadeQuePossoBeber = 0;
		this.quantasBebi = 0;
	}


	public void CalcularQuantasAindaPossoBeber(double valorPretendido,double qtGastei,double valorUnitario)
	{
		double valorTemp=valorPretendido-qtGastei;
		int quantidadeTemp=(int) (valorTemp/valorUnitario);		
		
		setQuantidadeQuePossoBeber(quantidadeTemp);
		
	
	}
	
	
	public boolean AddBebida(FestaConsumo consumo)
	{
		if(getQuantidadeQuePossoBeber()>0)
		{		
			setQuantasBebi(getQuantasBebi()+1);
			setGastos(getGastos()+getPrecoBebida());
			
			consumo.setQuantidade_consumida(getQuantasBebi());
			
			Consumo bebendo=new Consumo();
			
			
			return true;
		}else{
		
			return false;
		}
		
				
		
		
	}
	
	
	
	
	
	public int getQuantasBebi() {
		return quantasBebi;
	}


	public void setQuantasBebi(int quantasBebi) {
		this.quantasBebi = quantasBebi;
	}


	public double getGastos() {
		return gastos;
	}
	
	
	public void setGastos(double gastos) {
		this.gastos = gastos;
	}
	public double getQtQueroGastar() {
		return qtQueroGastar;
	}
	public void setQtQueroGastar(double qtQueroGastar) {

		
		this.qtQueroGastar = qtQueroGastar;
	}
	public double getPrecoBebida() {
		return precoBebida;
	}
	public void setPrecoBebida(double precoBebida) {
		this.precoBebida = precoBebida;
	}
	public int getQuantidadeQuePossoBeber() {
		return quantidadeQuePossoBeber;
	}
	public void setQuantidadeQuePossoBeber(int quantidadeQuePossoBeber) {
		this.quantidadeQuePossoBeber = quantidadeQuePossoBeber;
	}
	
	
}
