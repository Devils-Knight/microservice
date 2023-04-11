package com.autmaple.controller;

import com.autmaple.model.Organization;
import com.autmaple.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@RestController
@RequestMapping("/v1/organization")
@RequiredArgsConstructor
@Slf4j
public class OrganizationController {
    private final OrganizationService organizationService;

    @GetMapping("/{organizationId}")
//    @RolesAllowed({"ADMIN", "USER", "ostock-admin"})
    @RolesAllowed({"ADMIN", "USER", "ROLE_USER"})
    public ResponseEntity<Organization> getOrganization(@PathVariable("organizationId") String organizationId, Principal principal) {
        log.error(principal.getName());
        return ResponseEntity.ok(organizationService.findById(organizationId));
    }

    @PutMapping("/{organizationId}")
    @RolesAllowed({"ADMIN", "USER"})
    public void updateOrganization(@PathVariable("organizationId") String organizationId,
                                   @RequestBody Organization organization) {
        organizationService.update(organization);
    }

    @PostMapping
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<Organization> saveOrganization(@RequestBody Organization organization) {
        return ResponseEntity.ok(organizationService.create(organization));
    }

    @DeleteMapping("/{organizationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RolesAllowed({"ADMIN"})
    public void deleteOrganization(@PathVariable("organizationId") String organizationId, @RequestBody Organization organization) {
        organizationService.delete(organization);
    }
}
