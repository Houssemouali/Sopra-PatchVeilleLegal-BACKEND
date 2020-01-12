package com.sopra.patch.model;

public class AxeFormation {
	private String Nudoss;
	private String LIBLON;
	private String LIBABR;
	public String getLIBLON() {
		return LIBLON;
	}
	public void setLIBLON(String lIBLON) {
		LIBLON = lIBLON;
	}
	public String getLIBABR() {
		return LIBABR;
	}
	public void setLIBABR(String lIBABR) {
		LIBABR = lIBABR;
	}
	public AxeFormation(String lIBLON, String lIBABR,String Nudoss) {
		super();
		LIBLON = lIBLON;
		LIBABR = lIBABR;
	}
	public AxeFormation() {
		super();
	}
	
	
	
}
