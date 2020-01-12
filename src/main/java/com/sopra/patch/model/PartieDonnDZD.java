package com.sopra.patch.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "PartieDonnDZD")
public class PartieDonnDZD implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private long IdPartDonn;
	
	@Column(name="Nom_Rubrique")
	private String NomRubrq;
	
	@Column(name="Donnee")
	private String Donnee;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "DZD")
	  DZD dzd;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "file")
	 FileP fich;

	public PartieDonnDZD() {}
	public PartieDonnDZD(String nomRubrq, String donnee, DZD dzd,FileP f) {
		
		NomRubrq = nomRubrq;
		Donnee = donnee;
		this.dzd = dzd;
		this.fich=f;
	}
	public long getIdPartDonn() {
		return IdPartDonn;
	}
	public void setIdPartDonn(long idPartDonn) {
		IdPartDonn = idPartDonn;
	}
	public String getNomRubrq() {
		return NomRubrq;
	}
	public void setNomRubrq(String nomRubrq) {
		NomRubrq = nomRubrq;
	}
	public String getDonnee() {
		return Donnee;
	}
	public void setDonnee(String donnee) {
		Donnee = donnee;
	}
	public DZD getDzd() {
		return dzd;
	}
	public void setDzd(DZD dzd) {
		this.dzd = dzd;
	}
	public FileP getFich() {
		return fich;
	}
	public void setFich(FileP fich) {
		this.fich = fich;
	}
	
	
	
}
