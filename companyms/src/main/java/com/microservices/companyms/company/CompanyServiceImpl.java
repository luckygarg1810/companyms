package com.microservices.companyms.company;

import com.microservices.companyms.company.client.ReviewClient;
import com.microservices.companyms.company.dto.ReviewMessage;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private ReviewClient reviewClient;

    public CompanyServiceImpl(CompanyRepository companyRepository, ReviewClient reviewClient) {
        this.companyRepository = companyRepository;
        this.reviewClient = reviewClient;
    }
    @Override
    public List<Company> findCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company findCompany(Long id) {
       Optional<Company> company = companyRepository.findById(id);
        return company.orElse(null);
    }

    @Override
    public void addCompany(Company company) {
        Company newCompany = new Company();
        newCompany.setName(company.getName());
        newCompany.setDescription(company.getDescription());
        companyRepository.save(newCompany);
    }

    @Override
    public boolean updateCompany(Company updatedCompany, Long id) {
        Optional<Company> company = companyRepository.findById(id);
        if(company.isPresent()){
            Company company1 = company.get();
            company1.setName(updatedCompany.getName());
            company1.setDescription(updatedCompany.getDescription());
            companyRepository.save(company1);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        Company company = companyRepository.findById(reviewMessage.getCompanyId()).
                orElseThrow(() -> new NotFoundException("Company not found"));
        double averageRating = reviewClient.getAverageRatingForCompany(reviewMessage.getCompanyId());
        company.setRating(averageRating);
        companyRepository.save(company);
    }

    @Override
    public boolean deleteCompany(Long id) {
        try{
            companyRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
