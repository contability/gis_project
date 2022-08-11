package kr.co.gitt.kworks.service.conectStats;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.mappers.KwsSysConectLogMapper;
import kr.co.gitt.kworks.model.KwsSys;
import kr.co.gitt.kworks.model.KwsSysConectLog;
import kr.co.gitt.kworks.model.KwsSys.SysTy;
import kr.co.gitt.kworks.service.sys.SysService;

import org.springframework.stereotype.Service;

/////////////////////////////////////////////
/// @class IndvdlzSysServiceImpl
/// kr.co.gitt.kworks.service.conectStats \n
///   ㄴ IndvdlzSysServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 12. 28. 오후 5:25:27 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 개별시스템 접속 통계 서비스 구현체 입니다
///   -# 
/////////////////////////////////////////////
@Service("indvdlzSysService")
public class IndvdlzSysServiceImpl implements IndvdlzSysService{
	
	//시스템접속로그
	@Resource
	KwsSysConectLogMapper kwsSysConectLogMapper;
	
	@Resource
	SysService sysService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.conectStats.IndvdlzSysService#list(kr.co.gitt.kworks.model.KwsSysConectLog)
	/////////////////////////////////////////////
	public List<KwsSysConectLog> list(KwsSysConectLog KwsSysConectLog){
		return kwsSysConectLogMapper.list(KwsSysConectLog);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.conectStats.IndvdlzSysService#listTodayGroupByHourCount()
	/////////////////////////////////////////////
//	public ArrayList<HashMap<String,String>> listTodayGroupByHourCount(int sysListLength){
	public Map<String, Object> listTodayGroupByHourCount(){
		List <KwsSysConectLog> list = kwsSysConectLogMapper.listTodayGroupByHourCount();
		ArrayList<HashMap<String,String>> mapList = new ArrayList<HashMap<String,String>>();
		
		Map<String, Integer> sysTotalCntMap = new HashMap<String, Integer>();
		
		KwsSys kwsSys = new KwsSys();
		kwsSys.setSysTy(SysTy.SYSTEM);
		
		List<KwsSys> sysList = sysService.listAllSys(kwsSys);
		
		//24시간을 map에 설정
		for(int i = 0; i < 24; i++){
			String hStr = "";
			String hEndStr = "";
			
			Map<String, Integer> sysCntMap = new HashMap<String, Integer>();
			
			//각 시스템별 카운터 수
			Integer totalCnt  = 0;
			HashMap<String, String> map = new HashMap<String, String>();
			if(i < 10){
				hStr = "0" + i;
			}else{
				hStr = String.valueOf(i);
			}
			
			if(i < 9){
				hEndStr = "0" + (i + 1);
			}else{
				hEndStr = String.valueOf(i + 1);
			}
			
			for(Integer j=0; j < list.size(); j++){
				String sysId = String.valueOf(list.get(j).getSysId());
				String day = list.get(j).getDay();
				Integer count = list.get(j).getCnt();
				
				if(hStr.equals(day)){
					sysCntMap.put("sys" + sysId + "Count", count); 
					totalCnt += count;
				}
			}
			
			map.put("time", hStr + " ~ " + hEndStr);
			
			for(int j = 0; j < sysList.size(); j++){
				Long sysId = sysList.get(j).getSysId();
				map.put("sys" + sysId + "Count", sysCntMap.get("sys" + sysId + "Count") != null ? String.valueOf(sysCntMap.get("sys" + sysId + "Count")) : "0");
				
				sysTotalCntMap.put("sys" + sysId + "Total", sysTotalCntMap.get("sys" + sysId + "Total") != null ? sysTotalCntMap.get("sys" + sysId + "Total") + Integer.parseInt(map.get("sys" + sysId + "Count") != null ? map.get("sys" + sysId + "Count") : "0") : Integer.parseInt(map.get("sys" + sysId + "Count") != null ? map.get("sys" + sysId + "Count") : "0"));
			}
			
			map.put("totalCount", String.valueOf(totalCnt));
			sysTotalCntMap.put("sysTotalCnt", sysTotalCntMap.get("sysTotalCnt") != null ? sysTotalCntMap.get("sysTotalCnt") + totalCnt : totalCnt);
			
			System.out.println("time : " + map.get("time"));
			mapList.add(map);
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("mapList", mapList);
		resultMap.put("sysTotalCnt", sysTotalCntMap);
		resultMap.put("sysList", sysList);
		
		return resultMap;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.conectStats.IndvdlzSysService#listGroupByDayCount(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	public Map<String, Object> listGroupByDayCount(SearchDTO searchDTO) throws ParseException{
		List<KwsSysConectLog> list = kwsSysConectLogMapper.listGroupByDay(searchDTO);
		
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
		SimpleDateFormat yearFormatter = new SimpleDateFormat ( "yyyy", Locale.KOREA );
		SimpleDateFormat monthFormatter = new SimpleDateFormat ( "MM", Locale.KOREA );
		SimpleDateFormat dayFormatter = new SimpleDateFormat ( "dd", Locale.KOREA );
		
		Date beginDate = formatter.parse(searchDTO.getSearchStartDt());
		Date endDate = formatter.parse(searchDTO.getSearchEndDt());
		
		long diff = endDate.getTime() - beginDate.getTime();
	    long diffDays = diff / (24 * 60 * 60 * 1000);
	    
	    Integer dayCnt = Integer.valueOf(String.valueOf(diffDays));
	    Integer year = Integer.valueOf(yearFormatter.format(beginDate));
	    Integer month = Integer.valueOf(monthFormatter.format(beginDate)) -1;
	    Integer day = Integer.valueOf(dayFormatter.format(beginDate));
	    
	    Calendar cal = Calendar.getInstance();
		cal.set(year,month,day);
		
		KwsSys kwsSys = new KwsSys();
		kwsSys.setSysTy(SysTy.SYSTEM);
		
		List<KwsSys> sysList = sysService.listAllSys(kwsSys);
		
		Map<String, Integer> sysTotalCntMap = new HashMap<String, Integer>();
		
		sysTotalCntMap.put("allTotal", 0);
		
		for(int j = 0; j < sysList.size(); j++){
			sysTotalCntMap.put("sys" + sysList.get(j).getSysId() + "Total", 0);
		}
		
	    ArrayList<HashMap<String,String>> mapList = new ArrayList<HashMap<String,String>>();
	    
	    for(Integer i = 0; i <= dayCnt; i++){
	    	HashMap<String,String> dayMap = new HashMap<String, String>();
	    	
	    	String dayStr = formatter.format(cal.getTime());
	    	
	    	Map<String, Integer> sysCntMap = new HashMap<String, Integer>();
	    	
	    	for(int j = 0; j < sysList.size(); j++){
				sysCntMap.put("sys" + sysList.get(j).getSysId() + "Count", 0);
			}
    		
	    	Integer totalCnt = 0;
	    	for(Integer j = 0; j < list.size(); j++){
	    		String resultDay = list.get(j).getDay();
	    		String sysId = String.valueOf(list.get(j).getSysId());
	    		
	    		if(resultDay.equals(dayStr)){
	    			int count = Integer.valueOf(list.get(j).getCnt());
	    			
	    			for(int k = 0; k < sysList.size(); k++){
	    				if(String.valueOf(sysList.get(k).getSysId()).equals(sysId)){
	    					sysCntMap.put("sys" + sysId + "Count", sysCntMap.get("sys" + sysId + "Count") != null ? sysCntMap.get("sys" + sysId + "Count") + count : sysCntMap.get("sys" + sysId + "Count"));
	    					
	    					sysTotalCntMap.put("sys" + sysId + "Total", sysTotalCntMap.get("sys" + sysId + "Total") != null ? sysTotalCntMap.get("sys" + sysId + "Total") + count : count);
	    					totalCnt += count;
	    				}
	    			}
	    			
	    			sysTotalCntMap.put("allTotal", sysTotalCntMap.get("allTotal") + count);
	    			
	    		}
	    	}
	    	
	    	dayMap.put("day", dayStr);
	    	for(int j = 0; j < sysList.size(); j++){
	    		dayMap.put("sys" + sysList.get(j).getSysId() + "Count", String.valueOf(sysCntMap.get("sys" + sysList.get(j).getSysId() + "Count")));
			}
	    	dayMap.put("totalCount", String.valueOf(totalCnt));
	    	mapList.add(dayMap);
	    	cal.add ( Calendar.DATE, 1 );
	    }
	    
	    Map<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("mapList", mapList);
	    resultMap.put("sysList", sysList);
	    resultMap.put("sysTotalCntMap", sysTotalCntMap);
	    
		return resultMap;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.conectStats.IndvdlzSysService#listGroupByMonthCount(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	public Map<String, Object> listGroupByMonthCount(SearchDTO searchDTO) throws ParseException{
		List<KwsSysConectLog> list = kwsSysConectLogMapper.listGroupByMonth(searchDTO);
		
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
		SimpleDateFormat yearFormatter = new SimpleDateFormat ( "yyyy", Locale.KOREA );
		SimpleDateFormat monthFormatter = new SimpleDateFormat ( "MM", Locale.KOREA );
		
		ArrayList<HashMap<String,String>> mapList = new ArrayList<HashMap<String,String>>();
		
		Date beginDate = formatter.parse(searchDTO.getSearchStartDt());
		Date endDate = formatter.parse(searchDTO.getSearchEndDt());
		
		Integer beginYear = Integer.valueOf(yearFormatter.format(beginDate));
		Integer beginMonth = Integer.valueOf(monthFormatter.format(beginDate)) -1;
		
		Integer endYear = Integer.valueOf(yearFormatter.format(endDate));
		Integer endMonth = Integer.valueOf(monthFormatter.format(endDate)) -1;
		    
		Calendar cal1 = Calendar.getInstance();
		cal1.set(beginYear,beginMonth, 1);
		
		Calendar cal2 = Calendar.getInstance();
		cal2.set(endYear,endMonth, 1);
		
		Integer count = 0;
		Integer size = list.size();
		
		KwsSys kwsSys = new KwsSys();
		kwsSys.setSysTy(SysTy.SYSTEM);
		
		List<KwsSys> sysList = sysService.listAllSys(kwsSys);
		
		Map<String, Integer> sysTotalCntMap = new HashMap<String, Integer>(); 
		
		for(int i = 0; i < sysList.size(); i++){
			sysTotalCntMap.put("sys" + sysList.get(i).getSysId() + "Total", 0);
		}
		
		sysTotalCntMap.put("allTotal", 0);
		
		while(!cal1.after(cal2)){
			Integer totalCnt = 0;
			
			count++;
			
			String yearStr =  String.valueOf(cal1.get(Calendar.YEAR));
			Integer monInteger = Integer.valueOf(cal1.get(Calendar.MONTH)) +1;
			String monStr =  "0" + String.valueOf(monInteger);
			monStr = monStr.substring(monStr.length() -2);
			
			HashMap<String,String> monMap = new HashMap<String, String>();
			monMap.put("day", yearStr + "-" + monStr);
			
			Map<String, Integer> sysCntMap = new HashMap<String, Integer>();
			
			
			for(int i = 0; i < sysList.size(); i++){
				sysCntMap.put("sys" + sysList.get(i).getSysId() + "Count", 0);
			}
			
			for(Integer i = 0; i < size; i++){
				String dayStr = list.get(i).getDay();
				String sysId = String.valueOf(list.get(i).getSysId());
				
				count = Integer.valueOf(list.get(i).getCnt());
				
				if(dayStr.equals(yearStr + "-" + monStr)){
					
					sysTotalCntMap.put("allTotal", sysTotalCntMap.get("allTotal") + count );
					
	    			
    				sysCntMap.put("sys" + sysId + "Count", sysCntMap.get("sys" + sysId + "Count") != null ? sysCntMap.get("sys" + sysId + "Count") + count : count);
    				totalCnt += count;
    				sysTotalCntMap.put("sys" + sysId + "Total", sysTotalCntMap.get("sys" + sysId + "Total") + count);
				}
			}
			for(int i = 0; i < sysList.size(); i++){
				monMap.put("sys" + sysList.get(i).getSysId() + "Count", String.valueOf(sysCntMap.get("sys" + sysList.get(i).getSysId() + "Count")));
			}
	    	monMap.put("totalCount", String.valueOf(totalCnt));
			mapList.add(monMap);
			
			cal1.add(Calendar.MONTH,1);
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("mapList", mapList);
		resultMap.put("sysList", sysList);
		resultMap.put("sysTotalCntMap", sysTotalCntMap);
		
		return resultMap;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.conectStats.IndvdlzSysService#listGroupByWeekCount(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	public Map<String, Object> listGroupByWeekCount(SearchDTO searchDTO) throws ParseException{
		List<KwsSysConectLog> list = kwsSysConectLogMapper.listGroupByWeek(searchDTO);
		ArrayList<HashMap<String,String>> mapList = new ArrayList<HashMap<String,String>>();
		
		Integer size = list.size();
		
		String[] weekArray = {"일요일","월요일","화요일","수요일","목요일","금요일","토요일"};
		
		KwsSys kwsSys = new KwsSys();
		kwsSys.setSysTy(SysTy.SYSTEM);
		
		List<KwsSys> sysList = sysService.listAllSys(kwsSys);
		
		Map<String, Integer> sysTotalCntMap = new HashMap<String, Integer>();
		
		for(int i = 0; i < sysList.size(); i++){
			sysTotalCntMap.put("sys"+sysList.get(i).getSysId() + "Total", 0);
		}
		sysTotalCntMap.put("allTotal", 0);
		
		//요일별 셋팅 (1:월, 2:화, 3:수, 4:목, 5:금, 6:토, 7: 일)
		for(Integer i = 1; i < 8; i++){
			HashMap<String,String> monMap = new HashMap<String, String>();
			
			System.out.println("i : " + i);
			monMap.put("week", weekArray[i-1]);
			
			Map<String, Integer> sysCntMap = new HashMap<String, Integer>();
			
			for(int j = 0; j < sysList.size(); j++){
				sysCntMap.put("sys"+sysList.get(j).getSysId() + "Count", 0);
			}
			
    		Integer totalCnt = 0; 
    		
			for(Integer j = 0; j < size; j++){
				String weekStr = String.valueOf(list.get(j).getWeek());
				String sysId = String.valueOf(list.get(j).getSysId());
				Integer count = list.get(j).getCnt();
				if(weekStr.equals(String.valueOf(i))){
					sysTotalCntMap.put("allTotal", sysTotalCntMap.get("allTotal") + count);
					
					sysCntMap.put("sys"+ sysId + "Count", sysCntMap.get("sys"+ sysId + "Count") + count);
					sysTotalCntMap.put("sys"+ sysId + "Total", sysTotalCntMap.get("sys"+ sysId + "Total") + count);
					totalCnt += count;
				}
			}
			
			for(int j = 0; j < sysList.size(); j++){
				monMap.put("sys"+sysList.get(j).getSysId() + "Count", String.valueOf(sysCntMap.get("sys"+sysList.get(j).getSysId() + "Count")));
			}
			
	    	monMap.put("totalCount", String.valueOf(totalCnt));
			mapList.add(monMap);
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("mapList", mapList);
		resultMap.put("sysList", sysList);
		resultMap.put("sysTotalCntMap", sysTotalCntMap);
		
		return resultMap;
	}
}
