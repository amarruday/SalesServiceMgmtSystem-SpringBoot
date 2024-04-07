package com.yashsales.service;

import com.yashsales.dto.commons.BrandDto;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface BrandService {
    public Map<String, Object> getBrands();
    public Map<String, Object> getActiveBrands();
    public Map<String, Object> getBrand(Long brandId);
    public Map<String, Object> addBrand(BrandDto brandDto);
    public Map<String, Object> editBrand(BrandDto brandDto);
}
