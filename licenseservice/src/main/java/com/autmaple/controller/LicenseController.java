package com.autmaple.controller;

import com.autmaple.model.License;
import com.autmaple.service.LicenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("v1/organization/{organizationId}/license")
@RequiredArgsConstructor
@Slf4j
public class LicenseController {
    private final LicenseService licenseService;

    @GetMapping("/{licenseId}")
    public ResponseEntity<?> getLicense(@PathVariable("organizationId") String organizationId,
                                        @PathVariable("licenseId") String licenseId) {
        License license = licenseService.getLicense(organizationId, licenseId);
        license.add(
                linkTo(methodOn(LicenseController.class).getLicense(organizationId, licenseId)).withSelfRel(),
                linkTo(methodOn(LicenseController.class).createLicense(license)).withSelfRel(),
                linkTo(methodOn(LicenseController.class).updateLicense(license)).withSelfRel(),
                linkTo(methodOn(LicenseController.class).deleteLicense(organizationId, licenseId)).withSelfRel()
        );
        return ResponseEntity.ok(license);
    }


    @GetMapping("/{licenseId}/{clientType}")
    public License getLicenseWithClient(@PathVariable("organizationId") String organizationId,
                                        @PathVariable("licenseId") String licenseId,
                                        @PathVariable("clientType") String clientType) {
        return licenseService.getLicense(licenseId, organizationId, clientType);
    }

    @PutMapping
    public ResponseEntity<License> updateLicense(@RequestBody License license) {
        return ResponseEntity.ok(licenseService.updateLicense(license));
    }

    @PostMapping
    public ResponseEntity<License> createLicense(@RequestBody License license) {
        return ResponseEntity.ok(licenseService.createLicense(license));
    }

    @DeleteMapping("/{licenseId}")
    public ResponseEntity<String> deleteLicense(@PathVariable("organizationId") String organizationId,
                                                @PathVariable("licenseId") String licenseId) {
        return ResponseEntity.ok(licenseService.deleteLicense(organizationId, licenseId));
    }
}
