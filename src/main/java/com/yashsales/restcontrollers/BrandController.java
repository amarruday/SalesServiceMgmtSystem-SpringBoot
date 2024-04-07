package com.yashsales.restcontrollers;

import com.yashsales.dto.commons.BrandDto;
import com.yashsales.entity.Brand;
import com.yashsales.service.BrandService;
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
public class BrandController {

    private final BrandService brandService;

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
