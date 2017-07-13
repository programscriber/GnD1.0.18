package com.indutech.gnd.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="LCPC_BRANCH_ORDER")
public class LCPCBranchOrder {

	@Id
	@Column(name="LCPC_BRANCH", nullable=false, unique=true)
	private Long lcpcBranchCode;
	
	@Column(name="seq")
	private int seq;
	
	@Column(name = "securityPrinters")
	private String securityPrinters;

	public Long getLcpcBranchCode() {
		return lcpcBranchCode;
	}

	public void setLcpcBranchCode(Long lcpcBranchCode) {
		this.lcpcBranchCode = lcpcBranchCode;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getSecurityPrinters() {
		return securityPrinters;
	}

	public void setSecurityPrinters(String securityPrinters) {
		this.securityPrinters = securityPrinters;
	}
	
	
	
}
