package kr.co.gitt.kworks.projects.gn.service.ocpat;

import java.util.List;

import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.projects.gn.model.RdtOccaDt;
import kr.co.gitt.kworks.projects.gn.model.RdtOccnDt;
import kr.co.gitt.kworks.projects.gn.model.RdtOcdrDt;
import kr.co.gitt.kworks.projects.gn.model.RdtOcdsDt;
import kr.co.gitt.kworks.projects.gn.model.RdtOcpeDt;
import kr.co.gitt.kworks.projects.gn.model.RdtOcpeHt;
import kr.co.gitt.kworks.projects.gn.model.RdtOcreDt;


public interface OcpatAtachDtaService {
	
	/**
	 * 변경이력 조회
	 * @return
	 */
	public List<RdtOcpeHt> listRdtOcpeHt(RdtOcpeHt rdtOcpeHt);
	
	/**
	 * 변경이력 단건조회
	 * @return
	 */
	public RdtOcpeHt selectOneRdtOcpeHt(Long histNo);

	/**
	 * 변경이력 수정
	 * @return
	 */
	public Integer modifyRdtOcpeHt(RdtOcpeHt rdtOcpeHt) throws FdlException ;
	
	/**
	 * 변경이력 등록
	 * @return
	 */
	public Integer addRdtOcpeHt(RdtOcpeHt rdtOcpeHt) throws FdlException ;
	
	/**
	 * 변경이력 삭제
	 * @return
	 */
	public Integer removeRdtOcpeHt(Long histNo) throws FdlException ;
	
	/*/////////////////////////////////////////////////////////////*/
	/*부속자료*/
	/*/////////////////////////////////////////////////////////////*/
	
	/**
	 * 부속자료 조회
	 * @return
	 */
	//public List<RdtOcpeMa> listRdtOcpeMa(RdtOcpeMa rdtOcpeMa);
	public List<KwsImage> listKwsImage(KwsImage kwsImage);
	/**
	 * 부속자료 단건조회
	 * @return
	 */
	//public RdtOcpeMa selectOneRdtOcpeMa(Long fileNo);
	public KwsImage selectOneKwsImage(Long imageNo);
	/**
	 * 부속자료 등록
	 * @return
	 */
	//public Integer addRdtOcpeMa(RdtOcpeMa rdtOcpeMa) throws FdlException;
	public Integer addKwsImage(KwsImage kwsImage) throws FdlException;
	
	/**
	 * 부속자료 수정
	 * @return
	 */
	//public Integer modifyRdtOcpeMa(RdtOcpeMa rdtOcpeMa) throws FdlException ;
	public Integer modifyKwsImage(KwsImage kwsImage) throws FdlException;
	/**
	 * 부속자료 삭제
	 * @return
	 */
	//public Integer removeRdtOcpeMa(Long fileNo) throws FdlException ;
	public Integer removeKwsImage(Long imageNo) throws FdlException;
	
	//20200429 AM01:14
	/**
	 * 취소신청서 대장 수정
	 * @param rdtOccaDt - 대장정보
	 * @return
	 */
	public Integer modifyRdtOccaDt(RdtOccaDt rdtOccaDt) throws FdlException;
	
	/**
	 * 취소신청서 삭제 
	 * @param ftrCde - 최초대장 지형지물부호
	 * @param ftrIdn - 최초대장 관리번호
	 * @return
	 */
	public Integer removeRdtOccaDt(Long ftrIdn) throws FdlException;
	
	/**
	 * 변경대장 수정
	 * @param rdtOccnDt - 대장정보
	 * @return
	 */
	public Integer modifyRdtOccnDt(RdtOccnDt rdtOccnDt) throws FdlException;
	
	/**
	 * 변경대장삭제
	 * @param ftrCde - 지형지물부호
	 * @param ftrIdn - 관리번호
	 * @return
	 */
	public Integer removeRdtOccnDt(Long ftrIdn) throws FdlException;
	
	/**
	 * 취하원 수정
	 * @param rdtOcdrDt - 대장정보
	 * @return
	 */
	public Integer modifyRdtOcdrDt(RdtOcdrDt rdtOcdrDt) throws FdlException;
	
	/**
	 * 취하원 삭제
	 * @param rdtOcdrDt - 대장정보
	 * @return
	 */
	public Integer removeRdtOcdrDt(Long ftrIdn) throws FdlException;
	
	/**
	 * 불허가 대장 수정
	 * @param rdtOcdsDt - 대장정보
	 * @return
	 */
	public Integer modifyRdtOcdsDt(RdtOcdsDt rdtOcdsDt) throws FdlException;
	
	/**
	 * 불허가 대장 삭제
	 * @param rdtOcdsDt - 대장정보
	 * @return
	 */
	public Integer removeRdtOcdsDt(Long ftrIdn) throws FdlException;
	
	/**
	 * 점용허가 수정
	 * @param rdtOcpeDt - 대장정보
	 * @return
	 */
	public Integer modifyRdtOcpeDt(RdtOcpeDt rdtOcpeDt) throws FdlException;
	
	/**
	 * 점용허가 삭제
	 * @param rdtOcpeDt - 대장정보
	 * @return
	 */
	public Integer removeRdtOcpeDt(Long ftrIdn) throws FdlException;
	
	/**
	 * 원상회복공사 수정
	 * @param rdtOcreDt - 대장정보
	 * @return
	 */
	public Integer modifyRdtOcreDt(RdtOcreDt rdtOcreDt) throws FdlException;
	
	/**
	 * 원상회복공사 수정
	 * @param rdtOcreDt - 대장정보
	 * @return
	 */
	public Integer removeRdtOcreDt(Long ftrIdn) throws FdlException;
	
	/**
	 * 변경이력 단건조회
	 * @return
	 */
	public RdtOccaDt selectOneRdtOccaDt(Long ftrIdn);
	
	/**
	 * 변경이력 단건조회
	 * @return
	 */
	public RdtOccnDt selectOneRdtOccnDt(Long ftrIdn);
	
	/**
	 * 변경이력 단건조회
	 * @return
	 */
	public RdtOcdrDt selectOneRdtOcdrDt(Long ftrIdn);
	
	/**
	 * 변경이력 단건조회
	 * @return
	 */
	public RdtOcdsDt selectOneRdtOcdsDt(Long ftrIdn);
	
	/**
	 * 변경이력 단건조회
	 * @return
	 */
	public RdtOcpeDt selectOneRdtOcpeDt(Long ftrIdn);
	
	/**
	 * 변경이력 단건조회
	 * @return
	 */
	public RdtOcreDt selectOneRdtOcreDt(Long ftrIdn);
	

}