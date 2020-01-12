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

@Entity
@Table(name = "Inf_AZD")
public class AZD implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long IdAZD;
	
	@Column(name = "Compte_Enregistrement")
	private String ComptEnreg;
	
	@Column(name = "Type_Enregistrement")
	private String TypEnreg;
	
	@Column(name = "Sequence_Zero")
	private String Seq0;
	
	@Column(name = "Nom_Structure_Donnee")
	private String NomStrcDonn;
	
	@Column(name = "Code_Segment_Accueil")
	private String CodSegAcc;
	
	@Column(name = "Num_Ordre_Colonne")
	private String NumOrdCol;
	
	@Column(name = "Nom_Information")
	private String NomInf;
	
	@Column(name = "Nom_Rubrique")
	private String NomRubq;
	
	@Column(name = "Nom_colonne")
	private String NomCol;
	
	@Column(name = "Format_colonne")
	private String FormCol;
	
	@Column(name = "Format_Rubrique")
	private String FormRubq;

	@Column(name = "Nombre_decimal")
	private String NbrDec;

	@Column(name = "Longueur_Externe_Rubrique")
	private String LongExtRubq;

	@Column(name = "Deplacement_Rubrique")
	private String DepRubriq;

	@Column(name = "Type_structure_Donnee")
	private String TypStrcDonn;
	
	@Column(name = "Temoin_Arg_Tri")
	private String ArgTri;
	
	@Column(name = "Temoin_Code_Societe")
	private String CodeSoc;
	
	@Column(name = "Temoin_Structure_Donn")
	private String StrstrDonn;
	
	@Column(name = "Temoin_mouvement_fictif")
	private String mvtFictif;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "Block")
	Block bloc;
	
	
	public AZD() {
		
	}
	public AZD(String comptEnreg, String typEnreg, String seq0, String nomStrcDonn, String codSegAcc, String numOrdCol,
			String nomInf, String nomRubq, String nomCol, String formCol, String formRubq, String nbrDec,
			String longExtRubq, String depRubriq, String typStrcDonn, String argTri, String codeSoc, String strstrDonn,
			String mvtFictif, String temoinMovFic, Block bloc) {
		
		this.ComptEnreg = comptEnreg;
		this.TypEnreg = typEnreg;
		this.Seq0 = seq0;
		this.NomStrcDonn = nomStrcDonn;
		this.CodSegAcc = codSegAcc;
		this.NumOrdCol = numOrdCol;
		this.NomInf = nomInf;
		this.NomRubq = nomRubq;
		this.NomCol = nomCol;
		this.FormCol = formCol;
		this.FormRubq = formRubq;
		this.NbrDec = nbrDec;
		this.LongExtRubq = longExtRubq;
		this.DepRubriq = depRubriq;
		this.TypStrcDonn = typStrcDonn;
		this.ArgTri = argTri;
		this.CodeSoc = codeSoc;
		this.StrstrDonn = strstrDonn;
		this.mvtFictif = mvtFictif;
		this.bloc = bloc;
	}
	public String getComptEnreg() {
		return ComptEnreg;
	}

	public void setComptEnreg(String comptEnreg) {
		ComptEnreg = comptEnreg;
	}

	public String getTypEnreg() {
		return TypEnreg;
	}

	public void setTypEnreg(String typEnreg) {
		TypEnreg = typEnreg;
	}

	public String getSeq0() {
		return Seq0;
	}

	public void setSeq0(String seq0) {
		Seq0 = seq0;
	}

	public String getNomStrcDonn() {
		return NomStrcDonn;
	}

	public void setNomStrcDonn(String nomStrcDonn) {
		NomStrcDonn = nomStrcDonn;
	}

	public String getCodSegAcc() {
		return CodSegAcc;
	}

	public void setCodSegAcc(String codSegAcc) {
		CodSegAcc = codSegAcc;
	}

	public String getNumOrdCol() {
		return NumOrdCol;
	}

	public void setNumOrdCol(String numOrdCol) {
		NumOrdCol = numOrdCol;
	}

	public String getNomInf() {
		return NomInf;
	}

	public void setNomInf(String nomInf) {
		NomInf = nomInf;
	}

	public String getNomRubq() {
		return NomRubq;
	}

	public void setNomRubq(String nomRubq) {
		NomRubq = nomRubq;
	}

	public String getNomCol() {
		return NomCol;
	}

	public void setNomCol(String nomCol) {
		NomCol = nomCol;
	}

	public String getFormCol() {
		return FormCol;
	}

	public void setFormCol(String formCol) {
		FormCol = formCol;
	}

	public String getFormRubq() {
		return FormRubq;
	}

	public void setFormRubq(String formRubq) {
		FormRubq = formRubq;
	}

	public String getNbrDec() {
		return NbrDec;
	}

	public void setNbrDec(String nbrDec) {
		NbrDec = nbrDec;
	}

	public String getLongExtRubq() {
		return LongExtRubq;
	}

	public void setLongExtRubq(String longExtRubq) {
		LongExtRubq = longExtRubq;
	}

	public String getDepRubriq() {
		return DepRubriq;
	}

	public void setDepRubriq(String depRubriq) {
		DepRubriq = depRubriq;
	}

	public String getTypStrcDonn() {
		return TypStrcDonn;
	}

	public void setTypStrcDonn(String typStrcDonn) {
		TypStrcDonn = typStrcDonn;
	}

	public Block getBloc() {
		return bloc;
	}

	public void setBloc(Block bloc) {
		this.bloc = bloc;
	}
	public long getIdAZD() {
		return IdAZD;
	}
	public void setIdAZD(long idAZD) {
		IdAZD = idAZD;
	}
	public String getArgTri() {
		return ArgTri;
	}
	public void setArgTri(String argTri) {
		ArgTri = argTri;
	}
	public String getCodeSoc() {
		return CodeSoc;
	}
	public void setCodeSoc(String codeSoc) {
		CodeSoc = codeSoc;
	}
	public String getStrstrDonn() {
		return StrstrDonn;
	}
	public void setStrstrDonn(String strstrDonn) {
		StrstrDonn = strstrDonn;
	}
	public String getMvtFictif() {
		return mvtFictif;
	}
	public void setMvtFictif(String mvtFictif) {
		this.mvtFictif = mvtFictif;
	}

	
	
		
}
