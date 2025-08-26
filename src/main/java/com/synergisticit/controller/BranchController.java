package com.synergisticit.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.synergisticit.domain.Address;
import com.synergisticit.domain.Branch;
import com.synergisticit.service.BranchService;
import com.synergisticit.validation.BranchValidators;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/branch")
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }
    
    @Autowired
    private BranchValidators branchValidator;


    // =====  JSP  =====


    @GetMapping("/page")
    public String branchPage(@RequestParam(value = "editId", required = false) Long editId,
                             Model model) {
        Branch form;

        if (editId != null) {
            form = branchService.findBranchById(editId);
            if (form == null) {
                form = new Branch();
            }
        } else {
            form = new Branch();
        }

        if (form.getBranchAddress() == null) {
            form.setBranchAddress(new Address());
        }

        model.addAttribute("branch", form);
        model.addAttribute("branches", branchService.findAll());
        return "branchForm";
    }

    @PostMapping("/page")
    public String createFromForm(@Valid @ModelAttribute("branch") Branch branch,
                                 BindingResult result,
                                 RedirectAttributes ra,
                                 Model model) {
        if (result.hasErrors()) {
            model.addAttribute("branches", branchService.findAll());
            return "branchForm"; // JSP shows <form:errors/>
        }
        boolean isUpdate = (branch.getBranchId() != null);
        branchService.saveBranch(branch);
        ra.addFlashAttribute("msg", isUpdate ? "Branch updated." : "Branch created.");
        return "redirect:/branch/page";
    }
  
    @PostMapping("/delete/{id:\\d+}")
    public String deleteBranchPost(@PathVariable("id") Long id, RedirectAttributes ra) {
        branchService.deleteBranchById(id); 
        ra.addFlashAttribute("msg", "Branch deleted.");
        return "redirect:/branch/page";
    }
 

    // ===== JSON I used these to test end-points and make sure data base worked with out a JSP page =====

    @GetMapping
    @ResponseBody
    public List<Branch> findAll() {
        return branchService.findAll();
    }

    @GetMapping(params = "name")
    @ResponseBody
    public ResponseEntity<Branch> findByBranchName(@RequestParam("name") String branchname) {
        Branch b = branchService.findByBranchName(branchname);
        return (b == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(b);
    }

    @GetMapping("/{id:\\d+}")
    @ResponseBody
    public ResponseEntity<Branch> findBranchById(@PathVariable("id") Long branchId) {
        Branch b = branchService.findBranchById(branchId);
        return (b == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(b);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Branch> saveBranch(@RequestBody Branch branch) {
        Branch saved = branchService.saveBranch(branch);
        return ResponseEntity.created(URI.create("/branch/" + saved.getBranchId())).body(saved);
    }

    @PutMapping(path = "/{id:\\d+}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Branch> updateBranchById(@PathVariable("id") Long branchId,
                                                   @RequestBody Branch updates) {
        Branch updated = branchService.updateBranchById(branchId, updates);
        return (updated == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id:\\d+}")
    @ResponseBody
    public ResponseEntity<Branch> deleteBranchById(@PathVariable("id") Long branchId) {
        Branch deleted = branchService.deleteBranchById(branchId);
        return (deleted == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(deleted);
    }
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(branchValidator);
    }
}


