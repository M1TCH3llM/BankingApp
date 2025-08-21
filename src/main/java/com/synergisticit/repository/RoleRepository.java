package com.synergisticit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synergisticit.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	Role findByRoleName(String roleName);
}
