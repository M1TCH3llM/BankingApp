package com.synergisticit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.synergisticit.domain.Role;
import com.synergisticit.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService{

	private final RoleRepository roleRepository;
	
	public RoleServiceImpl(RoleRepository roleRepository) {
	        this.roleRepository = roleRepository;
	    }
	
	@Override
	public Role findByRoleName(String roleName) {
		return roleRepository.findByRoleName(roleName) ;
	}

	@Override
	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public Role findRoleById(int roleId) {
		 return roleRepository.findById(roleId).orElse(null);
    }

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public Role updateRoleById(int roleId) {
		return roleRepository.findById(roleId).orElse(null);
	}
	
	@Override
    public void deleteRoleById(int roleId) {
        roleRepository.deleteById(roleId);
    }
	
	
}
