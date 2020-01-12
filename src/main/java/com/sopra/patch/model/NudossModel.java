package com.sopra.patch.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Nudoss")
public class NudossModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idNudoss;
	
	@Column(name="Name")
	private String Name;
	
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "file")
	 FileP fich;
	
	
	
	public NudossModel() {
		
	}

	public NudossModel(String name,FileP fich) {
		
		Name = name;
	this.fich=fich;
	}

	public long getIdNudoss() {
		return idNudoss;
	}

	public void setIdNudoss(long idNudoss) {
		this.idNudoss = idNudoss;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public FileP getFich() {
		return fich;
	}

	public void setFich(FileP fich) {
		this.fich = fich;
	}
	
	
}
