package com.sopra.patch.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Historique")
public class Historique implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long idHistory;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	historyType type;
	
	@Column(name = "Nom_Fichier")
	String Nom_Fichier;
	
	@Temporal(TemporalType.TIMESTAMP)
	 @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@Column(name = "Date")
	Date date;
	
	@Column(name = "User_Name")
	String UserName;

	
	//public Historique() {}
	


	public Historique( String nom_Fichier, Date date, String userName,historyType type) {
		super();
		this.type = type;
		Nom_Fichier = nom_Fichier;
		this.date = date;
		UserName = userName;
	}

	public long getIdHistory() {
		return idHistory;
	}

	public void setIdHistory(long idHistory) {
		this.idHistory = idHistory;
	}

	public historyType getType() {
		return type;
	}

	public void setType(historyType type) {
		this.type = type;
	}

	public String getNom_Fichier() {
		return Nom_Fichier;
	}

	public void setNom_Fichier(String nom_Fichier) {
		Nom_Fichier = nom_Fichier;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public Historique() {
		super();
	}
	
	
	
	
}
