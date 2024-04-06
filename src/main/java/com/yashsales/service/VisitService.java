package com.yashsales.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.yashsales.dto.visit.SearchVisitBean;
import com.yashsales.dto.visit.VisitBean;

@Service
public interface VisitService {
	public abstract Map<String, Object> addNewVisit(VisitBean visitBean);
	public abstract Map<String, Object> getVisitDetails(Long visitId);
	public abstract Map<String, Object> getAllVisits();
	public abstract Map<String, Object> searchVisits(SearchVisitBean searchVisitBean);
}
