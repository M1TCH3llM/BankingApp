package com.synergisticit.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.synergisticit.domain.Address;
import com.synergisticit.domain.Branch;
import com.synergisticit.service.BranchService;

@Controller
@RequestMapping("/branch")
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    // =====  JSP  =====


    @GetMapping("/page")
    public String branchPage(Model model) {
        Branch form = new Branch();
        form.setBranchAddress(new Address());
        model.addAttribute("branch", form);
        model.addAttribute("branches", branchService.findAll());
        return "branchForm"; // <-- was "branches"
    }


    @PostMapping("/page")
    public String createFromForm(@ModelAttribute("branch") Branch branch, RedirectAttributes ra) {
        branchService.saveBranch(branch);
        ra.addFlashAttribute("msg", "Branch created.");
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
}

