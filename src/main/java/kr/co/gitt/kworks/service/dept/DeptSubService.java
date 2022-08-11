package kr.co.gitt.kworks.service.dept;

import java.util.List;

import kr.co.gitt.kworks.model.KwsDeptSub;
/////////////////////////////////////////////
/// @class DeptService
/// kr.co.gitt.kworks.service.dept \n
///   ㄴ DeptService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 8. 16. 오후 4:14:40 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 부서 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface DeptSubService {
	
	public List<KwsDeptSub> listDeptSub(KwsDeptSub kwsDeptSub);
}
