package com.yashsales.service;

import java.util.Map;

import org.springframework.stereotype.Service;
@Service
public interface ProductCatagoryService {
	
		public Map<String, Object> getProductCatagories();
		public Map<String, Object> getActiveProductCatagories();
}
