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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "Inf_DZD")
public class DZD implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idDzd;
	
	@Column(name = "Compte_Enregistrement")
	private String CompEnreg;
	
	@Column(name = "Type_Enregistrement")
	private String TypEnreg;
	
	@Column(name = "Nom_Structure_Donnee")
	private String NomStrcDonn;
	
	@Column(name = "NuDoss")
	private String NuDoss;
	
	@Column(name = "Type_Inf")
	private String TypeInf;
	
	@Column(name = "Code_Segment_Accueil")
	private String CodSegAcc;
	
	@Column(name = "Zero")
	private String Zero;
	
	@Column(name = "Partie_Donnee")
	private String partDonn;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "Block")
	Block bloc;

	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "PartieBlockParNudoss")
		NudossModel nud;
	

	public DZD() {}

	public DZD( String compEnreg, String typEnreg, String nomStrcDonn, String nuDoss, String typeInf,
			String codSegAcc, String zero, String partDonn, Block bloc,NudossModel n) {
		
		
		CompEnreg = compEnreg;
		TypEnreg = typEnreg;
		NomStrcDonn = nomStrcDonn;
		NuDoss = nuDoss;
		TypeInf = typeInf;
		CodSegAcc = codSegAcc;
		Zero = zero;
		this.partDonn = partDonn;
		this.bloc = bloc;
		this.nud=n;
	}

	public String getCompEnreg() {
		return CompEnreg;
	}

	public void setCompEnreg(String compEnreg) {
		this.CompEnreg = compEnreg;
	}

	public String getTypEnreg() {
		return TypEnreg;
	}

	public void setTypEnreg(String typEnreg) {
		this.TypEnreg = typEnreg;
	}

	public String getZero() {
		return Zero;
	}

	public void setZero(String zero) {
		this.Zero = zero;
	}

	public String getNomStrcDonn() {
		return NomStrcDonn;
	}

	public void setNomStrcDonn(String nomStrcDonn) {
		this.NomStrcDonn = nomStrcDonn;
	}

	public NudossModel getNud() {
		return nud;
	}

	public void setNud(NudossModel nud) {
		this.nud = nud;
	}
	

	public String getCodSegAcc() {
		return CodSegAcc;
	}

	public void setCodSegAcc(String codSegAcc) {
		this.CodSegAcc = codSegAcc;
	}

	public String getPartDonn() {
		return partDonn;
	}

	public void setPartDonn(String partDonn) {
		this.partDonn = partDonn;
	}

	public Block getBloc() {
		return bloc;
	}

	public void setBloc(Block bloc) {
		this.bloc = bloc;
	}

	public long getIdDzd() {
		return idDzd;
	}

	public void setIdDzd(long idDzd) {
		this.idDzd = idDzd;
	}
	
	
	
	
}
