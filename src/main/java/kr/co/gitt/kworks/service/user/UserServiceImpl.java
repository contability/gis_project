package kr.co.gitt.kworks.service.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.dto.UserSearchDTO;
import kr.co.gitt.kworks.mappers.KwsUserAuthorMapper;
import kr.co.gitt.kworks.mappers.KwsUserBkmkMapper;
import kr.co.gitt.kworks.mappers.KwsUserEnvrnMapper;
import kr.co.gitt.kworks.mappers.KwsUserLyrMapper;
import kr.co.gitt.kworks.mappers.KwsUserMapper;
import kr.co.gitt.kworks.mappers.KwsUserSysMapper;
import kr.co.gitt.kworks.model.KwsAuthorGroup;
import kr.co.gitt.kworks.model.KwsUser;
import kr.co.gitt.kworks.model.KwsUser.UserType;
import kr.co.gitt.kworks.model.KwsUserAuthor;
import kr.co.gitt.kworks.model.KwsUserAuthorHist;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.cryptography.EgovPasswordEncoder;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;


/////////////////////////////////////////////
/// @class UserServiceImpl
/// kr.co.gitt.kworks.service.user \n
///   ㄴ UserServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 16. 오후 12:00:02 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 사용자 관리 서비스 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("userService")
public class UserServiceImpl implements UserService {
	
	/// 사용자 관리 맵퍼
	@Resource
	KwsUserMapper kwsUserMapper;
	
	/// 사용자 권한 맵퍼
	@Resource
	KwsUserAuthorMapper kwsUserAuthorMapper;
	
	// 사용자 북마크 맵퍼
	@Resource
	KwsUserBkmkMapper kwsUserBkmkMapper;
	
	// 사용자 시스템 맵퍼
	@Resource
	KwsUserSysMapper kwsUserSysMapper;
	
	// 사용자 환경 맵퍼
	@Resource
	KwsUserEnvrnMapper kwsUserEnvrnMapper;
	
	// 사용자 레이어 맵퍼
	@Resource
	KwsUserLyrMapper kwsUserLyrMapper;
			
	/// 사용자 권한변경 이력 서비스
	@Resource
	UserAuthorHistService userAuthorHistService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.notice.NoticeService#listNotice(kr.co.gitt.kworks.common.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsUser> listUser(UserSearchDTO searchDTO) {
		List<KwsUser> list = kwsUserMapper.list(searchDTO);
		for(KwsUser kwsUser : list) {
			kwsUser.setKwsAuthorGroups(kwsUserAuthorMapper.listAll(kwsUser.getUserId()));
		}
		return list;
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.notice.NoticeService#listNotice(kr.co.gitt.kworks.common.dto.SearchDTO, egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo)
	/////////////////////////////////////////////
	@Override
	public List<KwsUser> listUser(UserSearchDTO searchDTO, PaginationInfo paginationInfo) {
		Integer totalRecordCount = kwsUserMapper.listCount(searchDTO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		if(totalRecordCount > 0) {
			return listUser(searchDTO);
		}
		else {
			return new ArrayList<KwsUser>();
		}
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.user.UserService#selectOneUser(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public KwsUser selectOneUser(String userId) {
		return kwsUserMapper.selectOne(userId);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.user.UserService#addUser(kr.co.gitt.kworks.model.KwsUser)
	/////////////////////////////////////////////
	@Override
	public Integer addUser(KwsUser kwsUser) {
		
		EgovPasswordEncoder encoder = new EgovPasswordEncoder();
		encoder.setAlgorithm("SHA-256");
		kwsUser.setPassword(encoder.encryptPassword(kwsUser.getPassword()));
		
		if(kwsUser.getUserType() != UserType.SEAOLL_USER) {
			kwsUser.setUserType(UserType.CRDNS_USER);
		}
		
		return kwsUserMapper.insert(kwsUser);
	}
	
	/////////////////////////////////////////////
	/// @fn addAuthorGroups
	/// @brief 함수 간략한 설명 : 사용자 권한 그룹 추가
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param userId
	/// @param authorGroups
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Integer addAuthorGroups(String userId, List<Long> authorGroups) {
		Integer rowCount = 0;
		for(Long authorGroupNo : authorGroups) {
			KwsUserAuthor kwsUserAuthor = new KwsUserAuthor();
			kwsUserAuthor.setUserId(userId);
			kwsUserAuthor.setAuthorGroupNo(authorGroupNo);
			kwsUserAuthorMapper.insert(kwsUserAuthor);
		}
		return rowCount;  
	}
	
	/// 이승재, 2021.09.23, 권한부여이력 변경에 따른 수정
	@Override
	public Integer addUser(KwsUser kwsUser, List<Long> authorGroups) {
		/// 사용자 및 권한그룹 추가
		Integer rowCount = addUser(kwsUser);
		addAuthorGroups(kwsUser.getUserId(), authorGroups);
		
		/// 권한부여이력 추가
		KwsUser newUser = selectOneUserInfo(kwsUser.getUserId());
		UserDTO gradUser = new UserDTO();
		gradUser.setUserId(kwsUser.getGradUserId());
		gradUser.setUserNm(kwsUser.getGradUserNm());
		
		// 사용자 정보 변경 추가
		addUserGradeHist(newUser, gradUser, newUser.getUserGrad().getValue(), 2);	//2 - 추가
		// 권한부여이력 추가
		addUserAuthorHist(newUser, gradUser, 2);	//2 - 추가
				
		return rowCount;  
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.user.UserService#removeUser(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public Integer removeUser(String userId){
		KwsUser oldUserInfo = selectOneUserInfo(userId);
		// 정보 및 권한 변경 사용자
		UserDTO gradUser = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		kwsUserAuthorMapper.deleteByUserId(userId);
		kwsUserBkmkMapper.deleteByUserId(userId);
		kwsUserSysMapper.deleteByUserId(userId);
		kwsUserEnvrnMapper.delete(userId);
		kwsUserLyrMapper.deleteByUserId(userId);
		Integer rowCount = kwsUserMapper.delete(userId);
		
		// 권한부여이력 추가
		addUserGradeHist(oldUserInfo, gradUser, oldUserInfo.getUserGrad().getValue(), 1); // 1- 삭제
		addUserAuthorHist(oldUserInfo, gradUser, 1);
		
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.user.UserService#modifyUser(kr.co.gitt.kworks.model.KwsUser)
	/////////////////////////////////////////////
	@Override
	public Integer modifyUser(KwsUser kwsUser, List<Long> authorGroups) throws FdlException {
		if(StringUtils.isNotBlank(kwsUser.getPassword())) {
			EgovPasswordEncoder encoder = new EgovPasswordEncoder();
			encoder.setAlgorithm("SHA-256");
			kwsUser.setPassword(encoder.encryptPassword(kwsUser.getPassword()));
			
			if(kwsUser.getUserType() == UserType.SEAOLL_USER) {
				kwsUser.setPassword(encoder.encryptPassword("NONE"));
			}
		}
		else {
			kwsUser.setPassword(null);
		}

		/// 권한부여이력 추가
		// 변경전 사용자 및 권한정보
		KwsUser oldUserInfo = selectOneUserInfo(kwsUser.getUserId());
				
		// 사용자 정보 변경
		Integer rowCount = kwsUserMapper.update(kwsUser);
		// 기존 권한 그룹 삭제
		kwsUserAuthorMapper.deleteByUserId(kwsUser.getUserId());
		// 신규 권한 그릅 추가
		addAuthorGroups(kwsUser.getUserId(), authorGroups);
		
		// 변경후 사용자 정보 및 권한정보
		KwsUser newUserInfo = selectOneUserInfo(kwsUser.getUserId());
		// 정보 및 권한 변경 사용자
		UserDTO gradUser = new UserDTO();
		gradUser.setUserId(kwsUser.getGradUserId());
		gradUser.setUserNm(kwsUser.getGradUserNm());
		
		// 사용자 정보 변경(권한부여이력) 추가
		if (!oldUserInfo.getUserGrad().getValue().equals(newUserInfo.getUserGrad().getValue())) {
			addUserGradeHist(oldUserInfo, gradUser, oldUserInfo.getUserGrad().getValue(), 1);	//1 - 삭제
			addUserGradeHist(newUserInfo, gradUser, newUserInfo.getUserGrad().getValue(), 2);	//2 - 추가
		}
		// 권한그룹 삭제 이력 추가
		addUserAuthorHist(oldUserInfo, gradUser, 1);
		// 권한그룹 추가 이력 추가
		addUserAuthorHist(newUserInfo, gradUser, 2);
		
		return rowCount;
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.user.UserService#modifyUserBySso(kr.co.gitt.kworks.model.KwsUser)
	/////////////////////////////////////////////
	@Override
	public Integer modifyUserBySso(KwsUser kwsUser) throws FdlException {
		List<Long> authorGroups = new ArrayList<Long>();
		authorGroups.add(1L);
		
		//2017.08.14 이승재
		//새올 사용자의 부서정보 업데이트 시
		//권한회수 및 공간정보활용 권한 부여, 권한회수가 이루어지지 않음에 대한 조치
		//addAuthorGroups(kwsUser.getUserId(), authorGroups);
		//return kwsUserMapper.update(kwsUser);
		return modifyUser(kwsUser, authorGroups);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.user.UserService#listUserExcel()
	/////////////////////////////////////////////
	@Override
	public List<KwsUser> listUserExcel(UserSearchDTO searchDTO) {
		return kwsUserMapper.listUserExcel(searchDTO);
	}
	
	@Override
	public List<KwsUser> listUserInfo(UserSearchDTO searchDTO) {
		List<KwsUser> list = kwsUserMapper.listInfo(searchDTO);
		for(KwsUser kwsUser : list) {
			kwsUser.setKwsAuthorGroups(kwsUserAuthorMapper.listAll(kwsUser.getUserId()));
		}
		return list;
	}
	
	@Override
	public KwsUser selectOneUserInfo(String userId) {
		return kwsUserMapper.selectOneUserInfo(userId);
	}
	
	/// 사용자에 의한 개인정보 수정
	@Override
	public Integer modifyUserInfo(KwsUser kwsUser) throws FdlException {

		if(StringUtils.isNotBlank(kwsUser.getPassword())) {
			EgovPasswordEncoder encoder = new EgovPasswordEncoder();
			encoder.setAlgorithm("SHA-256");
			kwsUser.setPassword(encoder.encryptPassword(kwsUser.getPassword()));
		} else {
			kwsUser.setPassword(null);
		}
		
		Integer rowCount = kwsUserMapper.updateUserInfo(kwsUser);
		
		return rowCount;
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 
	/// @see kr.co.gitt.kworks.service.user.UserService#addUserAuthorHist()
	/////////////////////////////////////////////
	/// 권한그룹변경이력 저장
	private void addUserAuthorHist(KwsUser kwsUser, UserDTO gradUser,
			int opperation) {
		List<KwsAuthorGroup> kwsAuthorGroups = kwsUser.getKwsAuthorGroups();
		for (KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
			addUserGradeHist(kwsUser, gradUser, kwsAuthorGroup.getAuthorGroupNm(), opperation);
		}
	}

	private Integer addUserGradeHist(KwsUser kwsUser, UserDTO gradUser,
			String authorGroupNm, int opperation) {
		String prcrCn = "";
		switch(opperation) {
			case 1:
				prcrCn = "삭제";
				break;
			case 2:
				prcrCn = "추가";
				break;
			default:
				prcrCn = "추가";
		}
		
		Calendar calendar = Calendar.getInstance();
		
		KwsUserAuthorHist kwsUserAuthorHist = new KwsUserAuthorHist();
		kwsUserAuthorHist.setUserId(kwsUser.getUserId());
		kwsUserAuthorHist.setUserNm(kwsUser.getUserNm());
		kwsUserAuthorHist.setDeptCode(kwsUser.getDeptCode());
		kwsUserAuthorHist.setDeptNm(kwsUser.getDeptNm());
		kwsUserAuthorHist.setGradUserId(gradUser.getUserId());
		kwsUserAuthorHist.setGradUserNm(gradUser.getUserNm());
		kwsUserAuthorHist.setGradAlwncDt(new Date(calendar.getTimeInMillis()));
		kwsUserAuthorHist.setAuthorGroupNm(authorGroupNm);
		kwsUserAuthorHist.setPrcrCn(prcrCn);
		
		Integer rowCount = userAuthorHistService.addUserAuthorHist(kwsUserAuthorHist);
		
		return rowCount;
	}
}
