package com.indutech.gnd.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="daily_notification")
public class DailyNotification {

	@Id
	@Column(name="id", nullable=false, unique=true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="toAddress")
	private String to;
	
	@Column(name = "cc")
	private String cc;
	
	@Column(name = "bcc")
	private String bcc;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "subject")
	private String subject;
	
	@Column(name = "createdDate")
	private Date createdDate;
	
	@Column(name = "createdBy")
	private String userName;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	
	
}
