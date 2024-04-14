package com.ssms.service.impl;

import com.ssms.constants.ApplicationConstants;
import com.ssms.dto.commons.BrandDto;
import com.ssms.entity.Brand;
import com.ssms.repository.BrandsRepository;
import com.ssms.service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandsRepository brandRepo;

    @Override
    public Map<String, Object> addBrand(BrandDto brandDto) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
        List<Brand> brandsList = brandRepo.findAll();

        Optional<Brand> duplicate = brandsList.stream()
                .filter(brd -> brd.getBrandName().trim().equalsIgnoreCase(brandDto.getBrandName().trim())).findFirst();

        if (duplicate.isPresent()) {
            responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
            responseMap.put("message", "This Brand is already exists!");
            responseMap.put("errorCode", "WSEM0004");
        } else {
            Brand brand = new Brand();
            brand.setBrandName(brandDto.getBrandName());
            brand.setStatus(true);
            brandRepo.save(brand);
            responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
            responseMap.put("message", "Brand is added successfully!");
            responseMap.put("errorCode", "");
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> getActiveBrands() {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
        responseMap.put("BrandsList", null);

        List<Brand> brandsList = brandRepo.findByStatus();
        responseMap.put("BrandsList", brandsList);
        return responseMap;
    }

    @Override
    public Map<String, Object> editBrand(BrandDto brandDto) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
        List<Brand> brandsList = brandRepo.findAll();

        Brand theLocalBrand = brandRepo.getById(brandDto.getBrandId());

        Optional<Brand> duplicate = brandsList.stream()
                .filter(brd -> brd.getBrandName().trim().equalsIgnoreCase(brandDto.getBrandName().trim())).findFirst();

        if (duplicate.isPresent()) {
            if (duplicate.get().getBrandId().equals(brandDto.getBrandId())) {
                theLocalBrand.setStatus(brandDto.isStatus());
                brandRepo.save(theLocalBrand);
                responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
                responseMap.put("message", "Brand is updated successfully!");
                responseMap.put("errorCode", "");
            } else {
                responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
                responseMap.put("message", "This Brand is already exists!");
                responseMap.put("errorCode", "WSEM0004");
            }
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> getBrands() {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
        responseMap.put("BrandsList", null);

        List<Brand> brandsList = brandRepo.findAll();
        if (!brandsList.isEmpty()) {
            responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
            responseMap.put("BrandsList", brandsList);
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> getBrand(Long brandId) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
        responseMap.put("Brand", null);

        Optional<Brand> brand = brandRepo.findById(brandId);
        if (brand.isPresent()) {
            responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
            responseMap.put("Brand", brand.get());
        }
        return responseMap;
    }
}
