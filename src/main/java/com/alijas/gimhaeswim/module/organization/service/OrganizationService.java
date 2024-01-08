package com.alijas.gimhaeswim.module.organization.service;

import com.alijas.gimhaeswim.module.organization.entity.Organization;
import com.alijas.gimhaeswim.module.organization.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public List<Organization> findAll() {
        return organizationRepository.findAll();
    }
}
