package kr.co.gitt.kworks.co.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipOutputStream;

import kr.co.gitt.kworks.co.service.CompressService;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

/////////////////////////////////////////////
/// @class CompressServiceImpl
/// kworks.co.service.impl \n
///   ㄴ CompressServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 5. 25. 오전 10:33:25 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 폴더 압축 구현입니다.
///   -# 
/////////////////////////////////////////////
@Service("compressService")
public class CompressServiceImpl implements CompressService {

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kworks.co.service.CompressService#compressFolder(java.lang.String, java.lang.String)
	/////////////////////////////////////////////
	@Override
	public void compressFolder(String folderPath, String zipPath) throws Exception {
		/*File zipFile = new File(zipPath);
		zipFile.createNewFile();
		ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
		File folder = new File(folderPath);
		for(File file : folder.listFiles()) {
			ZipEntry zipEntry = new ZipEntry(file.getName());
			zipOutputStream.putNextEntry(zipEntry);
			InputStream is = FileUtils.openInputStream(file);
			IOUtils.copy(is, zipOutputStream);
			is.close();
		}
		zipOutputStream.closeEntry();
		zipOutputStream.close();*/
		
		File zipFile = new File(zipPath);
		zipFile.createNewFile();
		ZipArchiveOutputStream zipArchiveOutputStream = new ZipArchiveOutputStream(new FileOutputStream(zipFile));
		//zipArchiveOutputStream.setEncoding("EUC-KR");
		File folder = new File(folderPath);
		for(File file : folder.listFiles()) {
			ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file.getName());
			zipArchiveOutputStream.putArchiveEntry(zipArchiveEntry);
			InputStream is = FileUtils.openInputStream(file);
			IOUtils.copy(is, zipArchiveOutputStream);
			is.close();
		}
		zipArchiveOutputStream.closeArchiveEntry();
		zipArchiveOutputStream.close();
	}

	@Override
	public void compressChild(String folderPath, String zipPath) throws Exception {
		String root = folderPath;
		if (!root.endsWith(File.separator))
			root += File.separator;
		
		File zipFile = new File(zipPath);
		zipFile.createNewFile();
		ZipArchiveOutputStream zipArchiveOutputStream = new ZipArchiveOutputStream(new FileOutputStream(zipFile));
		
		File folder = new File(folderPath);
		for(File path : folder.listFiles()) {
			childFolder(root, path, zipArchiveOutputStream);
		}
		zipArchiveOutputStream.closeArchiveEntry();
		zipArchiveOutputStream.close();
	}
	
	private void childFolder(String root, File path, ZipArchiveOutputStream zipArchiveOutputStream) {
		try
		{
			String fullPath = path.getPath();
			String entryName = fullPath.substring(root.length(), fullPath.length());
			
			if (path.isDirectory()) {
				for(File file : path.listFiles()) {
					childFolder(root, file, zipArchiveOutputStream);
				}
			}
			else {
				ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(entryName);
				zipArchiveOutputStream.putArchiveEntry(zipArchiveEntry);
				InputStream is = FileUtils.openInputStream(path);
				IOUtils.copy(is, zipArchiveOutputStream);
				is.close();
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
