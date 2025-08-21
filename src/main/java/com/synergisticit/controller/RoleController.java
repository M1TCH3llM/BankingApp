package com.synergisticit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.synergisticit.domain.Role;
import com.synergisticit.service.RoleService;


@RestController
@RequestMapping("/roles")
public class RoleController {
	
	
	@Autowired RoleService roleService;
	
	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}
	
	 @GetMapping
	 public List<Role> getAllRoles() {
	        return roleService.findAll();
	    }
	 
	 @GetMapping("/{id:\\\\d+}") //Regex prevents path crossing with non numeric 
	 public Role getRoleById(@PathVariable int id) {
	        return roleService.findRoleById(id);
	    }
	
	 @PostMapping("/create")
	    public Role createRole(@RequestBody Role role) {
	        return roleService.saveRole(role);
	    }
	 

}
