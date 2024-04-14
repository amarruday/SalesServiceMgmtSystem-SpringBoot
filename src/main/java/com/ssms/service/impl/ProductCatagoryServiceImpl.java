package com.ssms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssms.constants.ApplicationConstants;
import com.ssms.entity.ProductCatagory;
import com.ssms.repository.ProductCatagoryRepository;
import com.ssms.service.ProductCatagoryService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductCatagoryServiceImpl implements ProductCatagoryService {

	private final ProductCatagoryRepository prodCatagoryRepo;
	
	@Override
	public Map<String, Object> getProductCatagories() {
		List<ProductCatagory> prodCatagories = null;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		responseMap.put("ProductCatagoryList", null);

		prodCatagories = prodCatagoryRepo.findAll();
		responseMap.put("ProductCatagoryList", prodCatagories);

		return responseMap;
	}

	@Override
	public Map<String, Object> getActiveProductCatagories() {
		List<ProductCatagory> prodCatagories = null;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		responseMap.put("ProductCatagoryList", null);

		prodCatagories = prodCatagoryRepo.findByProductCatagoryStatus();
		responseMap.put("ProductCatagoryList", prodCatagories);

		return responseMap;
	}

}
