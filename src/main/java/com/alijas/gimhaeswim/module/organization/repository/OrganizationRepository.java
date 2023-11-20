package com.alijas.gimhaeswim.module.organization.repository;

import com.alijas.gimhaeswim.module.organization.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
