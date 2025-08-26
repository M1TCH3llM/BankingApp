package com.synergisticit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.synergisticit.domain.Address;
import com.synergisticit.domain.Branch;
import com.synergisticit.domain.Role;
import com.synergisticit.service.RoleService;
import com.synergisticit.validation.RoleValidators;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/role")
public class RoleController {
	
	
	@Autowired RoleService roleService;
	
	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}
	
	 @Autowired
	    private RoleValidators roleValidator;
	
    // =====  JSP  =====

	
	@GetMapping("/page")
	public String rolePage(@RequestParam(value = "editId", required = false) Integer editId,
	                       Model model) {
	    Role form = (editId != null) ? roleService.findRoleById(editId) : new Role();
	    if (form == null) form = new Role(); // in case service returns null
	    model.addAttribute("role", form);
	    model.addAttribute("roles", roleService.findAll());
	    return "roleForm";
	}
	
	 @PostMapping("/page")
	    public String createOrUpdateFromForm(@Valid @ModelAttribute("role") Role role,
	                                         BindingResult br,
	                                         RedirectAttributes ra,
	                                         Model model) {
	        if (br.hasErrors()) {
	            model.addAttribute("roles", roleService.findAll());
	            return "roleForm";
	        }
	        roleService.saveRole(role);
	        ra.addFlashAttribute("msg", (role.getRoleId() > 0 ? "Role updated." : "Role created."));
	        return "redirect:/role/page";
	    }
	 
	 @PostMapping("/delete")
	 public String deleteRole(@RequestParam int id, RedirectAttributes ra) {
	     System.out.println(">>> DELETE role id=" + id); // TEMP
	     roleService.deleteRoleById(id);
	     ra.addFlashAttribute("msg", "Role deleted.");
	     return "redirect:/role/page";
	 }
	
    // ===== JSON I used these to test end-points and make sure data base worked with out a JSP page =====

	
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
	 
	 @InitBinder
	    protected void initBinder(WebDataBinder binder) {
	        binder.addValidators(roleValidator);
	    }
	 
	 

}
