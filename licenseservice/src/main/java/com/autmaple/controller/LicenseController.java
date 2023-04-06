package com.autmaple.controller;

import com.autmaple.model.License;
import com.autmaple.service.LicenseService;
import com.autmaple.utils.UserContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LicenseController {
    private final LicenseService licenseService;

    @GetMapping("/v1/organization/{organizationId}/license/{licenseId}")
    public ResponseEntity<License> getLicense(@PathVariable("organizationId") String organizationId,
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

    @PutMapping("v1/organization/{organizationId}/license")
    public ResponseEntity<License> updateLicense(@RequestBody License license) {
        return ResponseEntity.ok(licenseService.updateLicense(license));
    }

    @PostMapping
    public ResponseEntity<License> createLicense(@RequestBody License license) {
        return ResponseEntity.ok(licenseService.createLicense(license));
    }

    @DeleteMapping("/v1/organization/{organizationId}/license/{licenseId}")
    public ResponseEntity<String> deleteLicense(@PathVariable("organizationId") String organizationId,
                                                @PathVariable("licenseId") String licenseId) {
        return ResponseEntity.ok(licenseService.deleteLicense(organizationId, licenseId));
    }

    @GetMapping("/v1/organization/{organizationId}/license")
//    @SneakyThrows
    public List<License> getLicenses(@PathVariable("organizationId") String organizationId) throws TimeoutException, ExecutionException, InterruptedException {
        log.debug("LicenseServiceController Correlation id {}", UserContextHolder.getUserContext().getCorrelationId());
        CompletableFuture<List<License>> licenseByOrganization = licenseService.getLicenseByOrganization(organizationId);
        return licenseByOrganization.get();
    }
}
