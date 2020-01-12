package com.sopra.patch.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="compteRendu")
public class compteRendu {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idCptRendu;
	
	@Column(name="Statut")
	private String statut;
	

	@JsonIgnore
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "NudossId")
	NudossModel nud ;
	
	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	
public compteRendu(String statut, NudossModel n) {
		
		this.statut = statut;
		this.nud = n;
	}

	public compteRendu() {
		
	}
	
	
	public NudossModel getNud() {
		return nud;
	}

	public void setNud(NudossModel nud) {
		this.nud = nud;
	}
	
	

}
