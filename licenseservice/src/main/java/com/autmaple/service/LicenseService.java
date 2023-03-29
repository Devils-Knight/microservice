package com.autmaple.service;

import com.autmaple.config.ServiceConfig;
import com.autmaple.model.License;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import com.autmaple.repository.LicenseRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LicenseService {
    private final LicenseRepository licenseRepository;
    private final MessageSource messages;
    private final ServiceConfig config;

    public License getLicense(String organizationId, String licenseId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        if (null == license) {
            throw new IllegalArgumentException(String.format(messages.getMessage("license.search.error.message", null, null), licenseId, organizationId));
        }
        return license.withComment(config.getProperty());
    }

    public License updateLicense(License license) {
        licenseRepository.save(license);
        return license.withComment(config.getProperty());
    }

    // todo waiting for completing
    public License getLicense(String licenseId, String organizationId, String clientType) {
        return null;
    }

    public License createLicense(License license) {
        license.setLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);
        return license.withComment(config.getProperty());
    }

    public String deleteLicense(String organizationId, String licenseId) {
        License license = new License();
        license.setLicenseId(licenseId);
        licenseRepository.delete(license);
        return String.format(messages.getMessage("license.delete.message", null, null), licenseId);
    }
}
