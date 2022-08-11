package kr.co.gitt.kworks.schedule;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.ExportSearchDTO;
import kr.co.gitt.kworks.model.KwsExport;
import kr.co.gitt.kworks.model.KwsExport.ProgrsSttus;
import kr.co.gitt.kworks.model.KwsExportConfm;
import kr.co.gitt.kworks.service.cmmn.ErrorService;
import kr.co.gitt.kworks.service.export.ExportService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/////////////////////////////////////////////
/// @class ExportSchedule
/// kr.co.gitt.kworks.schedule \n
///   ㄴ ExportSchedule.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 14. 오후 12:24:11 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 내보내기 스케쥴 입니다.
///   -# 
/////////////////////////////////////////////
@Configuration
@EnableAsync
@EnableScheduling
public class ExportSchedule {

	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 내보내기 서비스
	@Resource
	ExportService exportService;
	
	/// 에러 서비스
	@Resource
	ErrorService errorService;
	
	/////////////////////////////////////////////
	/// @fn export
	/// @brief 함수 간략한 설명 : 데이터 내보내기 스케쥴
	/// @remark
	/// - 함수의 상세 설명 : 
	///  
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@Scheduled(initialDelay=1000, fixedDelay=600000)
	public void export() {
		ExportSearchDTO exportSearchDTO = new ExportSearchDTO();
		exportSearchDTO.setProgrsSttus(ProgrsSttus.EXPORT_REQUEST);
		
		List<KwsExport> kwsExports = exportService.listAllExport(exportSearchDTO);
		for(KwsExport kwsExport : kwsExports) {
			try {
				exportService.exportData(kwsExport.getExportNo());
			}
			catch (Exception e) {
				exportService.modifyProgrsSttusExportFailure(kwsExport); // 진행단계 수정
				// 에러 테이블에 메세지 남김
				errorService.addError(e);
			}
		}
	}
	
	/////////////////////////////////////////////
	/// @fn periodEndExport
	/// @brief 함수 간략한 설명 : 기간 만료 스케쥴
	/// @remark
	/// - 함수의 상세 설명 : 
	///  
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@Scheduled(cron="0 0 1 * * *")
	public void periodEndExport() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		ExportSearchDTO exportSearchDTO = new ExportSearchDTO();
		exportSearchDTO.setDeletePrearngeDt(calendar.getTime());
		exportSearchDTO.setProgrsSttus(ProgrsSttus.CONSENT_COMPLETION);
		
		List<KwsExport> kwsExports = exportService.listAllExport(exportSearchDTO);
		for(KwsExport kwsExport : kwsExports) {
			exportService.periodEndExport(kwsExport);
		}
	}
	
	//@Scheduled(cron="0 10 1 * * *")
	@Scheduled(initialDelay=1000, fixedDelay=600000)
	public void periodEndConsent() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DATE, -7);
		
		ExportSearchDTO exportSearchDTO = new ExportSearchDTO();
		exportSearchDTO.setProgrsSttus(ProgrsSttus.CONSENT_WAITING);
		exportSearchDTO.setRequstDt(calendar.getTime());
		
		List<KwsExport> kwsExports = exportService.listAllExport(exportSearchDTO);
		for(KwsExport kwsExport : kwsExports) {
			KwsExportConfm kwsExportConfm = new KwsExportConfm();
			kwsExportConfm.setExportNo(kwsExport.getExportNo());
			kwsExportConfm.setConfmerId("admin");
			kwsExportConfm.setConfmDt(Calendar.getInstance().getTime());
			kwsExportConfm.setReturnPrvonsh("기간 만료");
			kwsExport.setKwsExportConfm(kwsExportConfm);
			exportService.returnExport(kwsExport);
		}
		
	}
	
}
