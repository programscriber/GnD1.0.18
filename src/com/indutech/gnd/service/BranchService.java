package com.indutech.gnd.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.indutech.gnd.dto.Branch;

public interface BranchService {
	
	public static final Integer NON_CARD_ISSUE_BRANCH_ACTIVE = 0;
	public static final Integer NON_CARD_ISSUE_BRANCH_INACTIVE = 1;
	public static final String NON_CARD_ISSUE_BRANCH_REJECT="Both Home branch and issue branch are not active";
	
	String importBranchXls(MultipartFile file);
	List<Branch> getBankBranchDetails(Long bankId);
	List<Branch> branchNameDetails(Long branchNameCode);
}
