package com.sopra.patch.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;



@Entity
@Table(name = "Files")

public class FileP implements Serializable{

	@Id @GeneratedValue()
	private long id;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Path")
	private String Path;
	
	@Column(name="Size")
	private double size;
	
	 @Temporal(TemporalType.TIMESTAMP)
	 @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss") 
	@Column(name="date")
     Date date;
		@Column(name="NomConsultant")
	private String cons_name;

	
	public FileP() {}
	
	public FileP(String Name,String path, double size,Date dat,String cons_name) {
		this.name=Name;
		this.Path=path;
		this.size=size;
		this.date=dat;
		this.cons_name=cons_name;
	}
	
	
	public FileP(String path, double size, Date date, String cons_name) {
		super();
		Path = path;
		this.size = size;
		this.date = date;
		this.cons_name = cons_name;
	}

	public FileP(String path) {
		this.Path=path;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}


	

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return Path;
	}
	public void setPath(String path) {
		Path = path;
	}
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}

	public String getCons_name() {
		return cons_name;
	}

	public void setCons_name(String cons_name) {
		this.cons_name = cons_name;
	}
	
}
