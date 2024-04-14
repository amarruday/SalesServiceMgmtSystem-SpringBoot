package com.ssms.restcontrollers;

import com.ssms.dto.commons.BrandDto;
import com.ssms.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("api/brand/")
@CrossOrigin("*")
@Tag(name="brand-api", description ="Brand Controller")
public class BrandController {

    private final BrandService brandService;

    @Operation(summary = "Get All Brands", description = "Returns a brands")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
    @GetMapping("brand/")
    public ResponseEntity<Map<String, Object>> getBrands() {
        return new ResponseEntity<>(brandService.getBrands(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
    @GetMapping("brand/Active")
    public ResponseEntity<Map<String, Object>> getActiveBrands() {
        return new ResponseEntity<>(brandService.getActiveBrands(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
    @GetMapping("brand/{brandId}")
    public ResponseEntity<Map<String, Object>> getBrandById(@PathVariable Long brandId) {
        return new ResponseEntity<>(brandService.getBrand(brandId), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("brand/")
    public ResponseEntity<Map<String, Object>> addBrand(@RequestBody BrandDto brandDto) {
        return new ResponseEntity<>(brandService.addBrand(brandDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("brand/")
    public ResponseEntity<Map<String, Object>> updateBrand(@RequestBody BrandDto brandDto) {
        return new ResponseEntity<>(brandService.editBrand(brandDto), HttpStatus.OK);
    }
}
