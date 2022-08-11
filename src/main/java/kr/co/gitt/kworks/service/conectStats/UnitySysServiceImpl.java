package kr.co.gitt.kworks.service.conectStats;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.mappers.KwsConectLogMapper;
import kr.co.gitt.kworks.model.KwsConectLog;

/////////////////////////////////////////////
/// @class UnitySysServiceImpl
/// kr.co.gitt.kworks.service.conectStats \n
///   ㄴ UnitySysServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 10. 20. 오전 10:09:34 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 통합시스템 접속통계 서비스 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("unitySysService")
public class UnitySysServiceImpl implements UnitySysService{

	@Resource
	KwsConectLogMapper kwsConectLogMapper;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.conectStats.UnitySysService#list(kr.co.gitt.kworks.model.KwsConectLog)
	/////////////////////////////////////////////
	public List<KwsConectLog> list(KwsConectLog kwsConectLog){
		return kwsConectLogMapper.list(kwsConectLog);
	}
	
	/////////////////////////////////////////////
	/// @fn listTodayGroupByHourCount
	/// @brief 함수 간략한 설명 : 금일 접속현황을 시간대별로 카운터
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public HashMap<String, Integer> listTodayGroupByHourCount(){
		List <KwsConectLog> list = kwsConectLogMapper.listTodayGroupByHourCount();
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		String hStr = "";
		String tStr = "";
		Integer totalCnt = 0;
		
		//24시간을 map에 설정
		for(int i = 0; i < 24; i++){
			hStr = "";
			tStr = "";
			if(i < 10){
				hStr = "h_0" + i;
				tStr = "t_0" + i;
			}else{
				hStr = "h_" + i;
				tStr = "t_" + i;
			}
			
			map.put(hStr , 0);
			map.put(tStr , 0);
		}
		
		// 값이 있는 시간에 값 넣기
		for(int i=0; i < list.size(); i++){
			String h = "h_" + list.get(i).getHour();
			String t = "t_" + list.get(i).getHour();
			Integer cnt = list.get(i).getCnt();
			map.put(h,cnt);
			map.put(t,cnt);
		}
		
		// total 값 넣기
		for(int i = 0; i < 24; i++){
			tStr = "";
			if(i < 10){
				tStr = "t_0" + i;
			}else{
				tStr = "t_" + i;
			}
			
			totalCnt += map.get(tStr);
			map.put(tStr , totalCnt);
		}
		return map;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.conectStats.UnitySysService#nowConectCount()
	/////////////////////////////////////////////
	public Integer nowConectCount(){
		return kwsConectLogMapper.nowConectCount();
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.conectStats.UnitySysService#listGroupByDayCount(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	public ArrayList<HashMap<String,String>> listGroupByDayCount(SearchDTO searchDTO) throws ParseException{
		List<KwsConectLog> list = kwsConectLogMapper.listGroupByDay(searchDTO);
		
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
//		SimpleDateFormat formatter2 = new SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );
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
		
	    ArrayList<HashMap<String,String>> mapList = new ArrayList<HashMap<String,String>>();
	    
	    Integer listKey = 0;
	    Integer totalCnt = 0;
	    for(Integer i = 0; i <= dayCnt; i++){
	    	HashMap<String,String> dayMap = new HashMap<String, String>();
	    	
	    	String dayStr = formatter.format(cal.getTime());
	    	
	    	Integer count = 0;
	    	for(Integer j = listKey; j < list.size(); j++){
	    		String resultDay = list.get(j).getDay();
	    		if(resultDay.equals(dayStr)){
	    			count = Integer.valueOf(list.get(j).getCnt());
	    			totalCnt += count;
	    			dayMap.put("count", String.valueOf(count));
	    			dayMap.put("totalCount", String.valueOf(totalCnt));
	    			listKey = j;
	    		}
	    	}
	    	
	    	dayMap.put("day", dayStr);
	    	dayMap.put("count", String.valueOf(count));
	    	dayMap.put("totalCount", String.valueOf(totalCnt));
	    	
	    	mapList.add(dayMap);
	    	cal.add ( Calendar.DATE, 1 );
	    }
	    
		return mapList;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.conectStats.UnitySysService#listGroupByMonthCount(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	public ArrayList<HashMap<String,String>> listGroupByMonthCount(SearchDTO searchDTO) throws ParseException{
		List<KwsConectLog> list = kwsConectLogMapper.listGroupByMonth(searchDTO);
		
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
		Integer totalCnt = 0;
		Integer size = list.size();
		while(!cal1.after(cal2)){
			count++;
			
			String yearStr =  String.valueOf(cal1.get(Calendar.YEAR));
			Integer monInteger = Integer.valueOf(cal1.get(Calendar.MONTH)) +1;
			String monStr =  "0" + String.valueOf(monInteger);
			monStr = monStr.substring(monStr.length() -2);
			
			HashMap<String,String> monMap = new HashMap<String, String>();
			monMap.put("day", yearStr + "-" + monStr);
			monMap.put("count" , "0");
			monMap.put("totalCount","0");
			
			for(Integer i = 0; i < size; i++){
				String dayStr = list.get(i).getDay();
//				System.out.println("dayStr : " + dayStr);
//				System.out.println("yearStr" + "-" + "monStr : " + yearStr + "-" + monStr);
				if(dayStr.equals(yearStr + "-" + monStr)){
					Integer val = list.get(i).getCnt();
					totalCnt += val;
					monMap.put("count" , String.valueOf(val));
					monMap.put("totalCount", String.valueOf(totalCnt));
				}
			}
			mapList.add(monMap);
			
			cal1.add(Calendar.MONTH,1);
		}
		
		return mapList;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.conectStats.UnitySysService#listGroupByWeekCount(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	public ArrayList<HashMap<String,String>> listGroupByWeekCount(SearchDTO searchDTO) throws ParseException{
		List<KwsConectLog> list = kwsConectLogMapper.listGroupByWeek(searchDTO);
		ArrayList<HashMap<String,String>> mapList = new ArrayList<HashMap<String,String>>();
		
		Integer totacount = 0;
		Integer size = list.size();
		
		String[] weekArray = {"일요일","월요일","화요일","수요일","목요일","금요일","토요일"};
		//요일별 셋팅 (1:월, 2:화, 3:수, 4:목, 5:금, 6:토, 7: 일)
		for(Integer i = 1; i < 8; i++){
			HashMap<String,String> monMap = new HashMap<String, String>();
			
			System.out.println("i : " + i);
			monMap.put("week", weekArray[i-1]);
			monMap.put("count" , "0");
			monMap.put("totalCount","0");	
			
			for(Integer j = 0; j < size; j++){
				String weekStr = list.get(j).getWeek();
				Integer count = list.get(j).getCnt();
				
				if(weekStr.equals(String.valueOf(i))){
					totacount += count;
					monMap.put("count" , String.valueOf(count));
					monMap.put("totalCount", String.valueOf(totacount));
				}
			}
			
			mapList.add(monMap);
		}
		
		return mapList;
	}
}
