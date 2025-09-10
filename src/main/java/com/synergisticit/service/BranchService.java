package com.synergisticit.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synergisticit.domain.Branch;


public interface BranchService {
	
	Branch findByBranchName(String branchname);
	Branch saveBranch(Branch branch);
	Branch findBranchById(Long branchId);
	Branch updateBranchById(Long branchId, Branch updates);
	Branch deleteBranchById(Long branchId);
    Page<Branch> findAll(Pageable pageable);
	List<Branch> findAll(Long branchId);
	
}
