package kr.co.gitt.kworks.projects.gn.service.ocpat;

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import kr.co.gitt.kworks.mappers.KwsImageMapper;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.projects.gn.mappers.RdtOccaDtMapper;
import kr.co.gitt.kworks.projects.gn.mappers.RdtOccnDtMapper;
import kr.co.gitt.kworks.projects.gn.mappers.RdtOcdrDtMapper;
import kr.co.gitt.kworks.projects.gn.mappers.RdtOcdsDtMapper;
import kr.co.gitt.kworks.projects.gn.mappers.RdtOcpeDtMapper;
import kr.co.gitt.kworks.projects.gn.mappers.RdtOcpeHtMapper;
import kr.co.gitt.kworks.projects.gn.mappers.RdtOcreDtMapper;
import kr.co.gitt.kworks.projects.gn.model.RdtOccaDt;
import kr.co.gitt.kworks.projects.gn.model.RdtOccnDt;
import kr.co.gitt.kworks.projects.gn.model.RdtOcdrDt;
import kr.co.gitt.kworks.projects.gn.model.RdtOcdsDt;
import kr.co.gitt.kworks.projects.gn.model.RdtOcpeDt;
import kr.co.gitt.kworks.projects.gn.model.RdtOcpeHt;
import kr.co.gitt.kworks.projects.gn.model.RdtOcreDt;
import kr.co.gitt.kworks.service.cmmn.ImageService;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;



import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import freemarker.template.utility.StringUtil;


@Service("ocpatAtachDtaService")
@Profile({"gn_dev", "gn_oper"})
public class OcpatAtachDtaServiceImpl implements OcpatAtachDtaService {

	/**
	 * 변경이력 맵퍼
	 */
	@Resource
	RdtOcpeHtMapper rdtOcpeHtMapper;
	
	/**
	 * 도로점용,건축허가,개발행위,사설안내 맵퍼
	 */
	@Resource
	RdtOcpeDtMapper rdtOcpeDtMapper;
	
	/*
	 * 변경허가 맵퍼
	 */
	@Resource
	RdtOccnDtMapper rdtOccnDtMapper;
	
	/*
	 * 원상회복공사 맵퍼
	 */
	@Resource
	RdtOcreDtMapper rdtOcreDtMapper;
	
	/*
	 * 불허가 맵퍼
	 */
	@Resource
	RdtOcdsDtMapper rdtOcdsDtMapper;
	
	/*
	 * 점용취소 맵퍼
	 */
	@Resource
	RdtOccaDtMapper rdtOccaDtMapper;
	
	/*
	 * 취하원 맵퍼
	 */
	@Resource
	RdtOcdrDtMapper rdtOcdrDtMapper;
	
	/**
	 * 이미지서비스 맵퍼
	 */
	@Resource
	//RdtOcpeMaMapper rdtOcpeMaMapper;
	ImageService imageService;
	
	// 이미지 맵퍼
	@Resource
	KwsImageMapper kwsImageMapper;
	
	/**
	 * 부속자료  시퀀스 서비스
	 */
	@Resource
	EgovIdGnrService kwsImageIdGnrService;
	
	/**
	 * 변경대장 시퀀스 서비스
	 */
	@Resource
	EgovIdGnrService rdtOcpeHtIdGnrService;
	
	/**
	 *  메세지 소스
	 */
	@Resource(name="messageSource")
    MessageSource messageSource;	
	
	/**
	 * 변경이력 리스트
	 * @return rdtOcpeHtMapper.list(rdtOcpeHt); 
	 */
	@Override
	public List<RdtOcpeHt> listRdtOcpeHt(RdtOcpeHt rdtOcpeHt){
		return rdtOcpeHtMapper.list(rdtOcpeHt); 
	}
	
	/**
	 * 변경이력 단건조회
	 * @return rdtOcpeHtMapper.selectOne(histNo);
	 */
	@Override
	public RdtOcpeHt selectOneRdtOcpeHt(Long histNo) {
		RdtOcpeHt result = rdtOcpeHtMapper.selectOne(histNo);
		return result;
	}
	/**
	 * 변경이력 수정
	 * @return rdtOcpeHtMapper.update(rdtOcpeHt);
	 */
	@Override
	public Integer modifyRdtOcpeHt(RdtOcpeHt rdtOcpeHt) throws FdlException {
		Integer rowCount = rdtOcpeHtMapper.update(rdtOcpeHt);
		
		return rowCount;
	}
	/**
	 * 변경이력 등록
	 * @return rdtOcpeHtMapper.insert(rdtOcpeHt);
	 */
	@Override
	public Integer addRdtOcpeHt(RdtOcpeHt rdtOcpeHt) throws FdlException {
		Long histNo = rdtOcpeHtIdGnrService.getNextLongId();
		rdtOcpeHt.setHistNo(histNo);
		
		Integer rowCount = rdtOcpeHtMapper.insert(rdtOcpeHt);
		return rowCount;
	}
	/**
	 * 변경이력 삭제
	 * @return rdtOcpeHtMapper.delete(histNo);
	 */
	@Override
	public Integer removeRdtOcpeHt(Long histNo) throws FdlException {
		RdtOcpeHt rdtOcpeHt = new RdtOcpeHt();
		rdtOcpeHt = rdtOcpeHtMapper.selectOne(histNo);
		Integer rowCount = rdtOcpeHtMapper.delete(histNo);
		return rowCount;
	}

//	/**
//	 * 부속자료 리스트
//	 * @return rdtOcpeMaMapper.list(rdtOcpeMa); 
//	 */
//	@Override
//	public List<RdtOcpeMa> listRdtOcpeMa(RdtOcpeMa rdtOcpeMa){
//		return rdtOcpeMaMapper.list(rdtOcpeMa); 
//	}
	@Override
	public List<KwsImage> listKwsImage(KwsImage kwsImage) {
		return kwsImageMapper.listAll(kwsImage);
	}
	
//	/**
//	 * 부속자료 단건조회
//	 * @return rdtOcpeMaMapper.selectOne(fileNo);
//	 */
//	@Override
//	public RdtOcpeMa selectOneRdtOcpeMa(Long fileNo) {
//		RdtOcpeMa result = rdtOcpeMaMapper.selectOne(fileNo);
//		return result;
//	}
	@Override
	public KwsImage selectOneKwsImage(Long imageNo) {
		KwsImage result = kwsImageMapper.selectOne(imageNo);
		return result;
	}
//	/**
//	 * 부속자료 등록 
//	 * @param rdtOcpeHt - 부속자료 정보
//	 * @return
//	 * @throws FdlException 
//	 */
//	@Override
//	public Integer addRdtOcpeMa(RdtOcpeMa rdtOcpeMa) throws FdlException {
//		
//		Long fileNo = rdtOcpeMaIdGnrService.getNextLongId();
//		rdtOcpeMa.setFileNo(fileNo);
//		
//		Integer rowCount = rdtOcpeMaMapper.insert(rdtOcpeMa);
//		return rowCount;
//	}

	@Override
	public Integer addKwsImage(KwsImage kwsImage) throws FdlException {
		Long imageNo = kwsImageIdGnrService.getNextLongId();
		kwsImage.setImageNo(imageNo);
		
		Integer rowCount = kwsImageMapper.insert(kwsImage);
		return rowCount;
	}
//	/**
//	 * 부속자료 수정
//	 * @param rdtOcpeHt - 부속자료 정보
//	 * @return
//	 * @throws FdlException 
//	 */
//	@Override
//	public Integer modifyRdtOcpeMa(RdtOcpeMa rdtOcpeMa) throws FdlException {
//		Integer rowCount = rdtOcpeMaMapper.update(rdtOcpeMa);
//		return rowCount;
//	}
	@Override
	public Integer modifyKwsImage(KwsImage kwsImage) throws FdlException {
		Integer rowCount = kwsImageMapper.update(kwsImage);
		return rowCount;
	}
	
//	/**
//	 * 부속자료 삭제
//	 * @param fileNo - 부속자료 정보
//	 * @return
//	 * @throws FdlException 
//	 */
//	@Override
//	public Integer removeRdtOcpeMa(Long fileNo) throws FdlException {
//		RdtOcpeMa rdtOcpeMa = new RdtOcpeMa();
//		rdtOcpeMa = rdtOcpeMaMapper.selectOne(fileNo);
//		Integer rowCount = rdtOcpeMaMapper.delete(fileNo);
//		return rowCount;
//	}
	@Override
	public Integer removeKwsImage(Long imageNo) throws FdlException {
		KwsImage kwsImage = new KwsImage();
		kwsImage = kwsImageMapper.selectOne(imageNo);
		Integer rowCount = kwsImageMapper.delete(imageNo);
		return rowCount;
	}

	//수정 삭제 부분
	@Override
	public Integer modifyRdtOccaDt(RdtOccaDt rdtOccaDt) throws FdlException {
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		
//		String dongCode = ocpatRegstr.getSelDong(); 
//		if (dongCode != null && dongCode.length() > 0) {
//			StringBuffer pnu = new StringBuffer();
//			pnu.append(StringUtil.rightPad(dongCode, 10, "0"));
//			if (ocpatRegstr.getCheckMauntain() == null)
//				pnu.append(1);
//			else
//				pnu.append(ocpatRegstr.getCheckMauntain());
//			pnu.append(StringUtil.leftPad(Integer.toString(ocpatRegstr.getMainNum()), 4, "0"));
//			pnu.append(StringUtil.leftPad(Integer.toString(ocpatRegstr.getSubNum()), 4, "0"));
//			if (pnu.length() >  0)
//				rdtOcdrDt.setPnu(pnu.toString());
//		}
		
//		StringBuffer pnu =  new StringBuffer();
//		String fourDong = rdtOccaDt.getPnu();
//		String selDong = fourDong.substring(0,10);
		
		Integer rowCount = rdtOccaDtMapper.update(rdtOccaDt, keyString);
		return rowCount;
	}

	@Override
	public Integer removeRdtOccaDt(Long ftrIdn) throws FdlException {
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		RdtOccaDt rdtOccaDt = new RdtOccaDt();
		
		rdtOccaDt = rdtOccaDtMapper.selectOne(ftrIdn, keyString);
		Integer rowCount = rdtOccaDtMapper.delete(ftrIdn);
		return rowCount;
	}

	@Override
	public Integer modifyRdtOccnDt(RdtOccnDt rdtOccnDt) throws FdlException {
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		Integer rowCount = rdtOccnDtMapper.update(rdtOccnDt, keyString);
		return rowCount;
	}

	@Override
	public Integer removeRdtOccnDt(Long ftrIdn) throws FdlException {
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		RdtOccnDt rdtOccnDt = new RdtOccnDt();
		rdtOccnDt = rdtOccnDtMapper.selectOne(ftrIdn, keyString);
		Integer rowCount = rdtOccnDtMapper.delete(ftrIdn);
		return rowCount;
	}

	@Override
	public Integer modifyRdtOcdrDt(RdtOcdrDt rdtOcdrDt) throws FdlException {
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		Integer rowCount = rdtOcdrDtMapper.update(rdtOcdrDt, keyString);
		return rowCount;
	}

	@Override
	public Integer removeRdtOcdrDt(Long ftrIdn) throws FdlException {
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		RdtOcdrDt rdtOcdrDt = new RdtOcdrDt();
		rdtOcdrDt = rdtOcdrDtMapper.selectOne(ftrIdn, keyString);
		Integer rowCount = rdtOcdrDtMapper.delete(ftrIdn);
		return rowCount;
	}

	@Override
	public Integer modifyRdtOcdsDt(RdtOcdsDt rdtOcdsDt) throws FdlException {
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		Integer rowCount = rdtOcdsDtMapper.update(rdtOcdsDt, keyString);
		return rowCount;
	}

	@Override
	public Integer removeRdtOcdsDt(Long ftrIdn) throws FdlException {
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		RdtOcdsDt rdtOcdsDt = new RdtOcdsDt();
		rdtOcdsDt = rdtOcdsDtMapper.selectOne(ftrIdn,keyString);
		Integer rowCount = rdtOcdsDtMapper.delete(ftrIdn);
		return rowCount;
	}

	@Override
	public Integer modifyRdtOcpeDt(RdtOcpeDt rdtOcpeDt) throws FdlException {
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		
		Integer rowCount = rdtOcpeDtMapper.update(rdtOcpeDt, keyString);
		return rowCount;
	}

	@Override
	public Integer removeRdtOcpeDt(Long ftrIdn) throws FdlException {
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		
		RdtOcpeDt rdtOcpeDt = new RdtOcpeDt();
		rdtOcpeDt = rdtOcpeDtMapper.selectOne(ftrIdn, keyString);
		Integer rowCount = rdtOcpeDtMapper.delete(ftrIdn);
		return rowCount;
	}

	@Override
	public Integer modifyRdtOcreDt(RdtOcreDt rdtOcreDt) throws FdlException {
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		
		Integer rowCount = rdtOcreDtMapper.update(rdtOcreDt, keyString);
		return rowCount;
	}

	@Override
	public Integer removeRdtOcreDt(Long ftrIdn) throws FdlException {
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		RdtOcreDt rdtOcreDt = new RdtOcreDt();
		rdtOcreDt = rdtOcreDtMapper.selectOne(ftrIdn, keyString);
		Integer rowCount = rdtOcreDtMapper.delete(ftrIdn);
		return rowCount;
	}
	
	@Override
	public RdtOccaDt selectOneRdtOccaDt(Long ftrIdn) {
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		RdtOccaDt result = rdtOccaDtMapper.selectOne(ftrIdn, keyString);
		return result;
	}

	@Override
	public RdtOccnDt selectOneRdtOccnDt(Long ftrIdn) {
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		RdtOccnDt result = rdtOccnDtMapper.selectOne(ftrIdn, keyString);
		return result;
	}

	@Override
	public RdtOcdrDt selectOneRdtOcdrDt(Long ftrIdn) {
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		RdtOcdrDt result = rdtOcdrDtMapper.selectOne(ftrIdn, keyString);
		return result;
	}

	@Override
	public RdtOcdsDt selectOneRdtOcdsDt(Long ftrIdn) {
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		RdtOcdsDt result = rdtOcdsDtMapper.selectOne(ftrIdn, keyString);
		return result;
	}

	@Override
	public RdtOcpeDt selectOneRdtOcpeDt(Long ftrIdn) {
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		
		RdtOcpeDt result = rdtOcpeDtMapper.selectOne(ftrIdn, keyString);
		return result;
	}

	@Override
	public RdtOcreDt selectOneRdtOcreDt(Long ftrIdn) {
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		RdtOcreDt result = rdtOcreDtMapper.selectOne(ftrIdn, keyString);
		return result;
	}
	
//	
}