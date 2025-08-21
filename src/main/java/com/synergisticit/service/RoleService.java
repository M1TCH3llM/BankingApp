package com.synergisticit.service;

import java.util.List;

import com.synergisticit.domain.Role;

public interface RoleService {
	
	Role findByRoleName(String roleName);
	Role saveRole(Role role);
	Role findRoleById(int roleId);
	List<Role> findAll();
	Role updateRoleById(int roleId);
	Role deleteRoleById(int roleId);
	
}
