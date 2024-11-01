package com.g4AppDev.FoundIT.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g4AppDev.FoundIT.repository.AdminRepo;

import com.g4AppDev.FoundIT.entity.Admin;

@Service
public class AdminService {

    @Autowired
    private AdminRepo adminRepository;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }

    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public Admin updateAdmin(Long id, Admin adminDetails) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            admin.setEmail(adminDetails.getEmail());
            admin.setPassword(adminDetails.getPassword());
            return adminRepository.save(admin);
        } else {
            throw new RuntimeException("Admin not found with id " + id);
        }
    }

    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }
}
