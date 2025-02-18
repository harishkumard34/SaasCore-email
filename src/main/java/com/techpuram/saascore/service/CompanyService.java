package com.techpuram.saascore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.techpuram.saascore.db.CompanyRepo;
import com.techpuram.saascore.entity.Company;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service layer is where all the business logic lies
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService {

    private final CompanyRepo companyRepo;

    /**
     * Fetch all companies.
     * @return List of all companies
     */
    public List<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    /**
     * Fetch a company by ID.
     * @param id - ID of the company
     * @return Company entity if found, else null
     */
    public Company getCompanyById(Long id){
        Optional<Company> optionalCompany = companyRepo.findById(id);
        if (optionalCompany.isPresent()) {
            return optionalCompany.get();
        }
        log.info("Company with id: {} doesn't exist", id);
        return null;
    }

    /**
     * Save a company entity after validation.
     * @param company - Company entity to save
     * @return true if saved successfully, false otherwise
     */
    public boolean saveCompany(Company company) {
       
        company.setCreatedTime(System.currentTimeMillis());
       
        companyRepo.save(company);
        log.info("Company with id: {} saved successfully", company.getOrgId());
        return true;
    }
    

    /**
     * Update an existing company entity after validation.
     * @param company - Company entity to update
     * @return true if updated successfully, false otherwise
     */
    public boolean updateCompany(Company company) {
        Optional<Company> existingCompany = companyRepo.findById(company.getOrgId());
        company.setModifiedTime(System.currentTimeMillis());
        if (existingCompany.isPresent()) {
            companyRepo.save(company);
            log.info("Company with id: {} updated successfully", company.getOrgId());
            return true;
        }
        log.info("Company with id: {} not found. Update operation aborted.", company.getOrgId());
        return false;
    }

    /**
     * Restrict deletion of a company by throwing an exception.
     * @param id - ID of the company
     */
    public void deleteCompanyById(Long id) {
        log.warn("Attempted to delete company with id: {} but deletion is not allowed", id);
        throw new UnsupportedOperationException("Deleting a company is not allowed due to terms and conditions.");
    }
}
