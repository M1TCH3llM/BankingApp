package com.synergisticit.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synergisticit.domain.Branch;
import com.synergisticit.repository.BranchRepository;

@Service
public class BranchServiceImpl implements BranchService {
	
	private final BranchRepository branchRepository;
	
	public BranchServiceImpl(BranchRepository branchRepository) {
		this.branchRepository = branchRepository;
	}

	@Override
    public Page<Branch> findAll(Pageable pageable) {
        return branchRepository.findAll(pageable);
    }
	
	@Override
	@Transactional(readOnly = true)
	public Branch findByBranchName(String branchName) {
		return branchRepository.findByBranchName(branchName);
	}

	@Override
	public Branch saveBranch(Branch branch) {
		return branchRepository.save(branch);
	}

	@Override
	@Transactional(readOnly = true)
	public Branch findBranchById(Long branchId) {
		return branchRepository.findById(branchId).orElse(null);
	}

//	@Override
//	@Transactional(readOnly = true)
//	public List<Branch> findAll() {
//		return branchRepository.findAll();
//	}

	@Override
	public Branch updateBranchById(Long branchId, Branch updates) {
		return branchRepository.findById(branchId).map(existing -> {
            existing.setBranchName(updates.getBranchName());
            existing.setBranchAddress(updates.getBranchAddress());
            return branchRepository.save(existing);
        })
        .orElse(null);
	}

	@Override
	public Branch deleteBranchById(Long branchId) {
		return branchRepository.findById(branchId)
                .map(existing -> {
                    branchRepository.delete(existing);
                    return existing;
                })
                .orElse(null);
    }

	@Override
	public List<Branch> findAll(Long branchId) {
		
		return branchRepository.findAll();
	}

	
}
