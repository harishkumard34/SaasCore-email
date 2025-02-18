package com.techpuram.saascore.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techpuram.saascore.entity.Company;
import com.techpuram.saascore.service.CompanyService;

import lombok.RequiredArgsConstructor;

/**
 * Controller class is where all the user requests are handled and required/appropriate
 * responses are sent
 */
@RestController
@RequestMapping("/v1/company")
@RequiredArgsConstructor
@Validated
public class CompanyController{

    private final CompanyService companyService;

    /**
     * Fetches all companies.
     * URL: localhost:8080/v1/company/
     * @return List of Companies
     */
    @GetMapping
    public ResponseEntity<List<Company>> getAllCompany() {
        return ResponseEntity.ok().body(companyService.getAllCompanies());
    }

    /**
     * Fetches a company by ID.
     * URL: localhost:8080/v1/company/{id}
     * @param id - company ID
     * @return Company with the given ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
      	
    	return ResponseEntity.ok().body(companyService.getCompanyById(id));
        
    }
    
    @Override
    public boolean equals(Object obj) {
        return (this == obj);
    }

    /**
     * Saves a company.
     * URL: localhost:8080/v1/company/
     * @param company - Request body containing the company entity
     * @return Message indicating success or failure
     */
    @PostMapping
    public ResponseEntity<String> saveCompany(@RequestBody Company company) {
        company.setCreatedTime(System.currentTimeMillis());
        boolean isSaved = companyService.saveCompany(company);
        if (isSaved) {
            return ResponseEntity.ok("Company created successfully.");
        } else {
            return ResponseEntity.badRequest().body("Company with the same ID already exists or cannot be created.");
        }
    }

    /**
     * Updates a company.
     * URL: localhost:8080/v1/company/
     * @param company - Company entity to be updated
     * @return Message indicating success or failure
     */
    @PutMapping
    public ResponseEntity<String> updateCompany(@RequestBody Company company) {
        company.setModifiedTime(System.currentTimeMillis());
        boolean isUpdated = companyService.updateCompany(company);
        if (isUpdated) {
            return ResponseEntity.ok("Company updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Company not found for update.");
        }
    }

    /**
     * Prevents deleting a company.
     * URL: localhost:8080/v1/company/{id}
     * @param id - company ID
     * @return Message indicating deletion is not allowed
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompanyById(@PathVariable Integer id) {
        return ResponseEntity.badRequest().body("Deleting a company is not allowed due to terms and conditions.");
    }
}
