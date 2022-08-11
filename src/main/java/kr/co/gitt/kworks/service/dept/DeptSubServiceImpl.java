package kr.co.gitt.kworks.service.dept;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.mappers.KwsDeptSubMapper;
import kr.co.gitt.kworks.model.KwsDeptSub;

import org.springframework.stereotype.Service;

/////////////////////////////////////////////
/// @class DeptServiceImpl
/// kr.co.gitt.kworks.service.dept \n
///   ㄴ DeptServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 8. 16. 오후 4:11:19 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 부서 서비스 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("deptSubService")
public class DeptSubServiceImpl implements DeptSubService{
	
	/// 부서 맵퍼
	@Resource
	KwsDeptSubMapper kwsDeptsubMapper;

	@Override
	public List<KwsDeptSub> listDeptSub(KwsDeptSub kwsDeptSub) {
		return kwsDeptsubMapper.list(kwsDeptSub);
	}
	
}
