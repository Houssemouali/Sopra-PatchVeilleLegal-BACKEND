package com.sopra.patch.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Block")
public class Block implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idBloc;
	@Column(name = "Description")
	private String Description;

	@Column(name = "codeSegAccueil")
	private String code;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)

	@JoinColumn(name = "File")
	FileP file;

	public Block() {

	}

	public Block(String description, String code, FileP file) {
		super();
		Description = description;
		this.code = code;
		this.file = file;
		
	}

	public Block(String description, FileP file,String c) {
		Description = description;
		this.file = file;
		this.code=c;
		
		}
	
		public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public Block(String description, FileP file) {
		
		Description = description;
		this.file = file;
	}

	public long getIdBloc() {
		return idBloc;
	}

	public void setIdBloc(long idBloc) {
		this.idBloc = idBloc;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public FileP getFile() {
		return file;
	}

	public void setFile(FileP file) {
		this.file = file;
	}
}
