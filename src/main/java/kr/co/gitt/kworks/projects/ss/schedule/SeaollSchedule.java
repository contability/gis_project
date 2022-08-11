package kr.co.gitt.kworks.projects.ss.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import kr.co.gitt.kworks.projects.ss.mappers.BmlWellPsMapper;
import kr.co.gitt.kworks.projects.ss.model.BmlWellPs;
import kr.co.gitt.kworks.saeoll.service.SoapLinkUtilService;
import kr.co.gitt.kworks.saeoll.service.SpatialObjectService;
import kr.co.gitt.kworks.service.cmmn.ErrorService;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/////////////////////////////////////////////
/// @class SeaollSchedule
/// kr.co.gitt.kworks.schedule \n
///   ㄴ SeaollSchedule.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 10. 26. 오후 9:11:25 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 새올 스케쥴러 입니다.
///   -# 
/////////////////////////////////////////////
@Configuration
@EnableAsync
@EnableScheduling
@Profile({"ss_oper"})
public class SeaollSchedule {

	//새올연계
	@Resource
	SoapLinkUtilService soapLinkUtilService;
	
	//공간객체 생성 서비스
	@Resource
	SpatialObjectService spatialObjectService;
	
	//지하수관정 
	@Resource
	BmlWellPsMapper bmlWellPsMapper;
	
	/// 에러 서비스
	@Resource
	ErrorService errorService;

//  초기시작시 로딩완료후 바로 실행하는 배치(공간객체만 생성)	
//	@Scheduled(initialDelay=1000, fixedDelay=600000000)
//	public void bmlWellpsGeomSchedule() throws Exception{
//		bmlWellpsGeomCreate();
//	}

	/////////////////////////////////////////////
	/// @fn bmlWellpsSeaollSchedule
	/// @brief 함수 간략한 설명 : 오후 11시 30분에 배치가 시작됨
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@Scheduled(cron="0 30 23 * * *")
//	@Scheduled(initialDelay=1000, fixedDelay=600000)
	public void bmlWellpsSeaollSchedule() throws Exception{
		try {
			Date today = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
			String todayStr = format.format(today);
			
//			todayStr = "19000101";
			
			//시작 카운터 (연동시 페이징 처리를 위한 값)
			Integer startCount = 1;
			//끝 카운터(연동시 페이징 처리를 위한 값)
			Integer endCount = 201;
			//전체 카운터
			Integer allCount = 0;
			
			//전체 목록 카운터 값을 구함
			Integer cnt = soapLinkUtilService.soapLink(todayStr, "1", "200", "");
			
			// cndStr 은 soapLinkUtilService.makeCndLisatStr 을 사용하여 조건을 하나씩 만들어서 추가하여야 함.
			String cndStr = "";
			
			
			while (cnt != 0) {
				//검색시 카운터값을 구함(0 이면 반복문 정지)
				cnt = soapLinkUtilService.soapLink(todayStr, String.valueOf(startCount), String.valueOf(endCount), cndStr);
				allCount += cnt;
				startCount += 200;
				endCount += 200;
			}
		
			//공간객체 생성
			if(allCount != 0){
				bmlWellpsGeomCreate();
			}
			
		} catch (Exception e) {
			// 에러 테이블에 메세지 남김
			errorService.addError(e);
		}
	}
	
	/////////////////////////////////////////////
	/// @fn bmlWellpsGeomCreate
	/// @brief 함수 간략한 설명 : 지하수관정 공간객체 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public void bmlWellpsGeomCreate() throws Exception{
		try {
			BmlWellPs bmlWellPs = new BmlWellPs();
//			List<BmlWellPs> list = bmlWellPsMapper.listAllGeomIsNull(bmlWellPs);
			List<BmlWellPs> list = bmlWellPsMapper.listAll(bmlWellPs);
			Integer size = list.size();
			
			Integer rowCnt = 0;
			for(Integer i=0; i < size; i++){
				BmlWellPs obj		= list.get(i);
				
				Integer litdDg		= obj.getLitdDg() == null ? 0 : obj.getLitdDg();
				Integer litdMint	= obj.getLitdMint() == null ? 0 : obj.getLitdMint();
				Double litdSc		= obj.getLitdSc() == null ? Double.valueOf("0") : obj.getLitdSc();
				
				Integer lttdDg		= obj.getLttdDg() == null ? 0 : obj.getLttdDg();
				Integer lttdMint	= obj.getLttdMint() == null ? 0 : obj.getLttdMint();
				Double lttdSc		= obj.getLttdSc() == null ? Double.valueOf("0") : obj.getLttdSc();
				
				if(litdDg !=0 && litdMint !=0 && litdSc != Double.valueOf("0") && lttdDg !=0 && lttdMint !=0 && lttdSc != Double.valueOf("0")){
					Double x = spatialObjectService.convertLonLatByDms(String.valueOf(litdDg), String.valueOf(litdMint), String.valueOf(litdSc));
					Double y = spatialObjectService.convertLonLatByDms(String.valueOf(lttdDg), String.valueOf(lttdMint), String.valueOf(lttdSc));
					
					String geomStr = spatialObjectService.grs80PointObject(x, y);
					System.out.println("geomStr : " +geomStr);
					
					BmlWellPs modifyBmlWellPs = new BmlWellPs();
					modifyBmlWellPs.setObjectid(obj.getObjectid());
					modifyBmlWellPs.setGeom(geomStr);
					
					rowCnt += bmlWellPsMapper.geomModify(modifyBmlWellPs);
				}else{
					System.out.println("null : objectid : " + obj.getObjectid());
				}
			}
		
		} catch (Exception e) {
			// 에러 테이블에 메세지 남김
			errorService.addError(e);
		}
	}
}
