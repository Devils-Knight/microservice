package com.autmaple.service;

import com.autmaple.model.Organization;
import com.autmaple.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    public Organization findById(String organizationId) {
        Optional<Organization> nullableOrganization = organizationRepository.findById(organizationId);
        return nullableOrganization.orElse(null);
    }

    public Organization create(Organization organization) {
        organization.setId(UUID.randomUUID().toString());
        return organizationRepository.save(organization);
    }

    public void update(Organization organization) {
        organizationRepository.save(organization);
    }

    public void delete(Organization organization) {
        organizationRepository.deleteById(organization.getId());
    }
}
