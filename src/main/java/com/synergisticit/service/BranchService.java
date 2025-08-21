package com.synergisticit.service;

import java.util.List;

import com.synergisticit.domain.Branch;


public interface BranchService {
	
	Branch findByBranchName(String branchname);
	Branch saveBranch(Branch branch);
	Branch findBranchById(Long branchId);
	List<Branch> findAll();
	Branch updateBranchById(Long branchId, Branch updates);
	Branch deleteBranchById(Long branchId);
	
}
