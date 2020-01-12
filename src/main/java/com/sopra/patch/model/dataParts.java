package com.sopra.patch.model;

import java.io.Serializable;

public class dataParts implements Serializable {
	
	
	private String Rub;
	private String Donn;
	
	
	public dataParts(String rub,String donn) {
		this.Rub=rub;
		this.Donn=donn;
	}
	
	public String getRub() {
		return Rub;
	}
	public void setRub(String rub) {
		Rub = rub;
	}
	public String getDonn() {
		return Donn;
	}
	public void setDonn(String donn) {
		Donn = donn;
	}
}
