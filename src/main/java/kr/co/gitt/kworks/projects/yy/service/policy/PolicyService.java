package kr.co.gitt.kworks.projects.yy.service.policy;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.co.gitt.kworks.cmmn.dto.ImageDTO;
import kr.co.gitt.kworks.cmmn.dto.LayerSearchDTO;
import kr.co.gitt.kworks.model.KwsDept;
import kr.co.gitt.kworks.model.KwsDeptSub;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.model.KwsLyrCtgry;
import kr.co.gitt.kworks.projects.yy.dto.PolicyDepRegisterSearchDTO;
import kr.co.gitt.kworks.projects.yy.dto.PolicyRepoRegisterDTO;
import kr.co.gitt.kworks.projects.yy.model.PlcyRepoCt;
import kr.co.gitt.kworks.projects.yy.model.PlcyRepoMa;
import kr.co.gitt.kworks.projects.yy.model.PlcyStatAs;

public interface PolicyService {

	
	//대장등록시 담당부서가져오기
	public List<KwsDept> listAllDept();
	
	//조건 검색시 
	//resources.projects.yy.sqlmap.mappers.PlcyStatAsMapper
	//id="listDep" resultMap="policyDepRegisterSearchResultMap" parameterType="plcyStatAs"
	public List<PolicyDepRegisterSearchDTO> listDep(PlcyStatAs plcyStatAs);

	//KWS_MENU 위치 조회
	public List<PolicyDepRegisterSearchDTO> listSearch(String geom);

	//단건검색
	public PlcyStatAs selectOne(Long ftrIdn);
	
	//단건검색
	public PlcyStatAs selectPlcyAdr(Long ftrIdn);
	
	/**
	 * 사진 목록조회
	 * @param ftrCde - 지형지물부호
	 * @param ftrIdn - 관리번호
	 * @return
	 * @throws Exception
	 */
	public List<KwsImage> listPhoto(String ftrCde, Long ftrIdn) throws Exception;	

	/**
	 * 사진 단건 조회
	 * @param imageNo
	 * @return
	 * @throws Exception
	 */
	public KwsImage selectPhotoByNo(Long imageNo) throws Exception;
	
	/**
	 * 사진 등록
	 * @param imageDTO
	 * @param multipartFile
	 * @param thumbnailWidth
	 * @param thumbnailHeight
	 * @return
	 * @throws Exception
	 */
	public KwsImage insertPhoto(ImageDTO imageDTO, MultipartFile multipartFile, int thumbnailWidth, int thumbnailHeight) throws Exception;	
	
	/**
	 * 사진 변경
	 * @param imageDTO
	 * @param multipartFile
	 * @param thumbnailWidth
	 * @param thumbnailHeight
	 * @return
	 * @throws Exception
	 */
	public KwsImage updatePhoto(ImageDTO imageDTO, MultipartFile multipartFile, int thumbnailWidth, int thumbnailHeight) throws Exception;	
	
	public Integer update(PlcyStatAs plcyStatAs)  throws Exception;
	
	//보고서 관련
	
	//보고서 대장
	public List<PlcyRepoMa> list(PlcyRepoMa plcyRepoMa);
	
	//보고서 조회
	public List<PlcyRepoCt> list(PlcyRepoCt plcyRepoCt);
	
	//보고서 대장등록 서비스
	public Integer insertRepoMaRegster(PolicyRepoRegisterDTO policyRepoRegstr) throws Exception;
	
	//보고서
	public Integer insertRepoCtRegster(PolicyRepoRegisterDTO policyRepoRegstr) throws Exception;
	
	// 단건 대장 조회
	public PolicyRepoRegisterDTO selectOneRepo(Long ftrIdn);
	
	//보고서 편집 서비스
	public Integer updateRepoMaReg(PolicyRepoRegisterDTO policyRepoRegisterDTO) throws Exception;
	
	//보고서 삭제
	public Integer deleteRepoMa(PolicyRepoRegisterDTO policyRepoReter) throws Exception;
	
	public Integer insertPlcy(Long ftrIdn, String userId, String plcyTit) throws Exception;
	
	//정책지도대장삭제
	public Integer removePlcyStatAs(Long ftrIdn) throws Exception;
	
	//정책지도대장삭제
	public Integer removeImage(Long ftrIdn) throws Exception;
	
	public List<PolicyDepRegisterSearchDTO> policyQuickSearch(String plcyAdr);

	//담당 부서
	public List<KwsDept> listAllDeptCategory();
	
	//부서코드로 검색
	public List<PlcyStatAs> deptSubList(String deptSubCode);
	
	//실과소 리턴
	public String deptNmReturn (String deptCode);
		
	//부서명 리턴
	public String deptSubNmReturn (String deptSubCode);
	
	//부서 코드 리턴
	public String forDeptCode(String deptSubCode);

}
