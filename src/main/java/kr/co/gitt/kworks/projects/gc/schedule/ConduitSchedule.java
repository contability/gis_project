package kr.co.gitt.kworks.projects.gc.schedule;


import javax.annotation.Resource;

import kr.co.gitt.kworks.projects.gc.mappers.SwlPipeLmMapper;
import kr.co.gitt.kworks.projects.gc.mappers.WtlPipeLmMapper;
import kr.co.gitt.kworks.service.cmmn.ErrorService;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@EnableAsync
@EnableScheduling
@Profile({"gc_dev"})
public class ConduitSchedule {
	
	/// 에러 서비스
	@Resource
	ErrorService errorService;
	
	@Resource
	WtlPipeLmMapper wtlPipeLmMapper;
	
	@Resource
	SwlPipeLmMapper swlPipeLmMapper;
	

	//   초     |   분    |  시        |  일        |  월        | 요일    | 연도     |
	
	// 0~59 | 0~59 | 0~23 | 1~31 | 1~12 | 0~6 | 생략가능
	
	//요일 : 0(일요일) ~ 6(토요일)
	//? : 설정값 없을때 ~ 일과 요일에서만 사용가능
	// * : 모든조건

	//운영서버 반영시
	@Scheduled(cron="0 0 12 * * *")
	//@Scheduled(cron="0 0/1 * * * *")
	public void coduitSchedule() throws Exception{
		try {
//			SwlPipeLm swlPipeLm = new SwlPipeLm();
			System.out.println("스케줄러동작");
			
			System.out.println("상수폐관 데이터 업데이트 시작");
			wtlPipeLmMapper.insert();
			wtlPipeLmMapper.delete();
			System.out.println("상수폐관 데이터 업데이트 완료");
			System.out.println("---------------------------------------------------------------");
			System.out.println("하수폐관 데이터 업데이트 시작");
			swlPipeLmMapper.insert();
			swlPipeLmMapper.delete();
			System.out.println("하수폐관 데이터 업데이트 완료");
			
			System.out.println("스케쥴러 테스트 정상작동");
		} catch (Exception e) {
			// 에러 테이블에 메세지 남김
			errorService.addError(e);
		}
	}
	
}
