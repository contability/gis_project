package kr.co.gitt.kworks.service.feature;

import java.io.File;

import kr.co.gitt.kworks.dto.feature.FeatureKmlResultDTO;
import kr.co.gitt.kworks.dto.feature.FeatureResultDTO;

import org.springframework.web.multipart.MultipartFile;

public interface FeatureService {

	public FeatureResultDTO getFeaturesFromExcelFile(MultipartFile multipartFile, Integer startLine, Integer xIndex, Integer yIndex) throws Exception;
	
	public FeatureKmlResultDTO getFeaturesFromKmlFile(File file) throws Exception;
	
}
