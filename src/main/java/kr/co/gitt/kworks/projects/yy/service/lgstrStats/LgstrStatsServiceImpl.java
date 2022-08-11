package kr.co.gitt.kworks.projects.yy.service.lgstrStats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsFile;
import kr.co.gitt.kworks.projects.yy.mappers.LgstrStatsFileMapper;
import kr.co.gitt.kworks.projects.yy.mappers.LgstrStatsMapper;
import kr.co.gitt.kworks.projects.yy.model.LgstrStats;
import kr.co.gitt.kworks.projects.yy.model.LgstrStatsFile;
import kr.co.gitt.kworks.service.file.FileService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Service("lgstrStatsService")
@Profile({"yy_dev", "yy_oper"})
public class LgstrStatsServiceImpl implements LgstrStatsService {

	public static String FOLDER_NAME = "lgstrstats";

	public static String FILE_NAME_PREFIX = "LGSTRSTATS";

	@Resource
	LgstrStatsMapper lgstrStatsMapper;

	@Resource
	LgstrStatsFileMapper lgstrStatsFileMapper;

	@Resource
	EgovIdGnrService lgstrStatsIdGnrService;

	@Resource
	FileService fileService;

	@Override
	public List<LgstrStats> listLgstrStats(SearchDTO searchDTO) {
		return lgstrStatsMapper.list(searchDTO);
	}

	@Override
	public List<LgstrStats> listLgstrStats(SearchDTO searchDTO, PaginationInfo paginationInfo) {
		Integer totalRecordCount = lgstrStatsMapper.listCount(searchDTO);
		paginationInfo.setTotalRecordCount(totalRecordCount);

		if (totalRecordCount > 0) {
			return listLgstrStats(searchDTO);
		} else {
			return new ArrayList<LgstrStats>();
		}
	}

	@Override
	public LgstrStats selectOneLgstrStats(Long lgstrStatsNo) {
		LgstrStats lgstrStats = lgstrStatsMapper.selectOne(lgstrStatsNo);
		LgstrStatsFile lgstrStatsFile = lgstrStats.getLgstrStatsFile();
		KwsFile kwsFile = fileService.selectOneFile(lgstrStatsFile.getFileNo());
		
		lgstrStatsFile.setKwsFile(kwsFile);
		lgstrStats.setLgstrStatsFile(lgstrStatsFile);
		
		return lgstrStats;
	}

	@Override
	public Integer addLgstrStats(LgstrStats lgstrStats, MultipartRequest multipartRequest) throws FdlException, IllegalStateException, IOException {
		Long lgstrStatsNo = lgstrStatsIdGnrService.getNextLongId();
		lgstrStats.setLgstrStatsNo(lgstrStatsNo);

		Integer rowCount = lgstrStatsMapper.insert(lgstrStats);
		
		for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
			MultipartFile multipartFile = entry.getValue();
			if(!multipartFile.isEmpty()) {
				addLgstrStatsFile(lgstrStatsNo, multipartFile);
			}
		}
		
		return rowCount;
	}

	@Override
	public Integer modifyLgstrStats(LgstrStats lgstrStats,
			MultipartRequest multipartRequest, Long deleteFileNo) throws FdlException,
			IllegalStateException, IOException {

		Long lgstrStatsNo = lgstrStats.getLgstrStatsNo();

		Integer rowCount = lgstrStatsMapper.update(lgstrStats);
		
		if(deleteFileNo != null){
			removeLgstrStatsFile(lgstrStatsNo, deleteFileNo);
			
			for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
				MultipartFile multipartFile = entry.getValue();
				if(!multipartFile.isEmpty()) {
					addLgstrStatsFile(lgstrStatsNo, multipartFile);
				}
			}
		}
		
		
		return rowCount;
	}

	@Override
	public Integer removeLgstrStats(Long lgstrStatsNo) {
		LgstrStats lgstrStats = selectOneLgstrStats(lgstrStatsNo);
		LgstrStatsFile lgstrStatsFile = lgstrStats.getLgstrStatsFile();

		if (lgstrStatsFile != null) {
			Long fileNo = lgstrStatsFile.getFileNo();
			removeLgstrStatsFile(lgstrStatsNo, fileNo);
		}

		return lgstrStatsMapper.delete(lgstrStatsNo);
	}

	protected void addLgstrStatsFile(Long lgstrStatsNo, MultipartFile multipartFile) throws FdlException, IllegalStateException, IOException {

				KwsFile kwsFile = fileService.addFile(multipartFile,FOLDER_NAME, FILE_NAME_PREFIX);
				Long fileNo = kwsFile.getFileNo();

				LgstrStatsFile lgstrStatsFile = new LgstrStatsFile();
				lgstrStatsFile.setLgstrStatsNo(lgstrStatsNo);
				lgstrStatsFile.setFileNo(fileNo);
				lgstrStatsFileMapper.insert(lgstrStatsFile);
	}

	protected void removeLgstrStatsFile(Long lgstrStatsNo, Long fileNo) {
		LgstrStatsFile lgstrStatsFile = new LgstrStatsFile();
		lgstrStatsFile.setLgstrStatsNo(lgstrStatsNo);
		lgstrStatsFile.setFileNo(fileNo);
		lgstrStatsFileMapper.delete(lgstrStatsFile);

		fileService.removeFile(fileNo);
	}
}
