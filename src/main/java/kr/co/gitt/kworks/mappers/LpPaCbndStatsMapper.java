package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.model.LpPaCbndStats;

public interface LpPaCbndStatsMapper {
	
	public List<LpPaCbndStats> list(LpPaCbndStats lpPaCbndStats);
	
	public LpPaCbndStats select(LpPaCbndStats lpPaCbndStats);
	
}