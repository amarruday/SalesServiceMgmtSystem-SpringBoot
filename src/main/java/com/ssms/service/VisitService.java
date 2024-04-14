package com.ssms.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssms.dto.visit.SearchVisitBean;
import com.ssms.dto.visit.VisitBean;

@Service
public interface VisitService {
	public abstract Map<String, Object> addNewVisit(VisitBean visitBean);
	public abstract Map<String, Object> getVisitDetails(Long visitId);
	public abstract Map<String, Object> getAllVisits();
	public abstract Map<String, Object> searchVisits(SearchVisitBean searchVisitBean);
}
