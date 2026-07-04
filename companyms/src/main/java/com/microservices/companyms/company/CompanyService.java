package com.microservices.companyms.company;

import com.microservices.companyms.company.dto.ReviewMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyService {

    List<Company> findCompanies();
    Company findCompany(Long id);
    void addCompany(Company company);
    boolean updateCompany(Company updatedCompany, Long id);
    boolean deleteCompany(Long id);
    void updateCompanyRating(ReviewMessage reviewMessage);
}
