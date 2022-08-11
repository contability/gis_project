package kr.co.gitt.kworks.projects.gn.service.cmmnProp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.node.ObjectNode;

import kr.co.gitt.kworks.cmmn.dto.ImageDTO.ImageType;
import kr.co.gitt.kworks.model.KwsFile;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.projects.gn.dto.UseAreaSearchDTO;
import kr.co.gitt.kworks.projects.gn.model.BmlAcexAs;
import kr.co.gitt.kworks.projects.gn.model.BmlAcinAs;
import kr.co.gitt.kworks.projects.gn.model.BmlBuidAs;
import kr.co.gitt.kworks.projects.gn.model.BmlLoanAs;
import kr.co.gitt.kworks.projects.gn.model.BmlLousAs;
import kr.co.gitt.kworks.projects.gn.model.BmlOccpAs;
import kr.co.gitt.kworks.projects.gn.model.BmlPropAs;

public interface CmmnPropService {
	
	//토지재산 리스트조회
	public List<BmlPropAs> listProp(BmlPropAs bmlPropDt) throws Exception;
	
	//토지재산 회계 구분별 리스트 조회
	public List<BmlPropAs> selectAccGroupList();
	
	//토지재산 단건 조회
	public BmlPropAs selectProp(Long prtIdn) throws Exception;
	
	//건물재산 리스트조회
	public List<BmlBuidAs> listBuid(BmlBuidAs bmlBuidDt) throws Exception;
	
	//건물재산 단건 조회
	public BmlBuidAs selectBuid(Long prtIdn) throws Exception;
	
	//대부내역 리스트
	public List<BmlLoanAs> listLoan(BmlLoanAs bmlLoanDt) throws Exception;
	
	//대부내역 재산 용도별 리스트
	public List<BmlLoanAs> selectPrsGroupList();
	
	public Object selectManGroupList(String dataId);
	
	//산출내역 리스트
	//public List<BmlCompDt> listComp(BmlCompDt bmlCompDt) throws Exception;
	
	//무단점유 리스트
	public List<BmlOccpAs> listOccp(BmlOccpAs bmlOccpDt) throws Exception;
	
	//대부내역 단건조회
	//public BmlLoanAs selectLoan(Long lonIdn) throws Exception;
	public BmlLoanAs selectLoan(Long lonIdn, Long prtIdn) throws Exception;
	
	//산출내역 단건조회
	//public BmlCompDt selectComp(Long cmpIdn) throws Exception;
	
	//무단점유 단건조회
	public BmlOccpAs selectOccp(Long ocpIdn, Long prtIdn) throws Exception;
	
	// 실태조사 단건 조회
	public BmlAcexAs selectAcex(Long prtIdn);
	
	// 현장조사 단건 조회
	public BmlAcinAs selectAcin(Long prtIdn);
	
	// 포털 알림
	public ObjectNode portalNotification();
	
	// pdf다운로드
	public void pdfDownload(String fileKnd, String prtIdn, String agent, HttpServletResponse rs) throws FileNotFoundException, UnsupportedEncodingException, IOException;
	
	public void thumbnailImage(File file, HttpServletRequest rq, HttpServletResponse rs, String userAgent) throws IOException;
	
	public void previewFile(KwsFile kwsFile, String userAgent, HttpServletResponse rs) throws IOException;
	
	/////////////////////////////////////////////////////////
	
	//건물재산
	public List<BmlBuidAs> useAreaBuidSearch(UseAreaSearchDTO useAreaSearchDTO);
	
	//토지재산
	public List<BmlPropAs> useAreaPropSearch(UseAreaSearchDTO useAreaSearchDTO);
	
	//대부내역
	public List<BmlLoanAs> useAreaLoanSearch(UseAreaSearchDTO useAreaSearchDTO);
	
	//실태조사
	public List<BmlAcexAs> useAreaAcexSearch(UseAreaSearchDTO useAreaSearchDTO);
	
	///////////////////////////////////////////////////////
	
	public Long maxPk();
	
	public Integer lousDelete(Long lonIdn);
	
	public Long oousMaxPk();
	
	public Integer oousDelete(Long ocpIdn);
}
