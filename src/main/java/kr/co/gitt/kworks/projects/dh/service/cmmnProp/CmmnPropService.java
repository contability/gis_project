package kr.co.gitt.kworks.projects.dh.service.cmmnProp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.model.KwsFile;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.projects.dh.model.BmlAcinAs;
import kr.co.gitt.kworks.projects.dh.model.BmlBuidAs;
import kr.co.gitt.kworks.projects.dh.model.BmlLoanAs;
import kr.co.gitt.kworks.projects.dh.model.BmlOccpAs;
import kr.co.gitt.kworks.projects.dh.model.BmlPropAs;

public interface CmmnPropService {
	
	//토지재산 리스트조회
	public List<BmlPropAs> listProp(BmlPropAs bmlPropDt) throws Exception;
	
	//토지재산 단건 조회
	public BmlPropAs selectProp(Long prtIdn) throws Exception;
	
	//건물재산 리스트조회
	public List<BmlBuidAs> listBuid(BmlBuidAs bmlBuidDt) throws Exception;
	
	//건물재산 단건 조회
	public BmlBuidAs selectBuid(Long prtIdn) throws Exception;
	
	//대부내역 리스트
	public List<BmlLoanAs> listLoan(BmlLoanAs bmlLoanDt) throws Exception;
	
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
	
	// 실태조사 단건조회
	public BmlAcinAs selectAcin(Long prtIdn);
	
	public void pdfDownload(String fileKnd, String prtIdn, String agent, HttpServletResponse rs) throws UnsupportedEncodingException, FileNotFoundException, IOException;
	
	public void thumbnailImage(File file, HttpServletRequest rq, HttpServletResponse rs, String userAgent) throws IOException;
	
	public void previewFile(KwsFile kwsFile, String userAgent, HttpServletResponse rs) throws IOException;
}
