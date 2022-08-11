package kr.co.gitt.kworks.projects.yg.service.export;


import javax.annotation.Resource;

import kr.co.gitt.kworks.mappers.KwsExportFilterImageMapper;
import kr.co.gitt.kworks.mappers.KwsExportFilterOutptMapper;
import kr.co.gitt.kworks.model.KwsExport;
import kr.co.gitt.kworks.model.KwsExport.ExportTy;
import kr.co.gitt.kworks.model.KwsUser;
import kr.co.gitt.kworks.model.KwsUser.UserGrad;
import kr.co.gitt.kworks.service.data.DataService;
import kr.co.gitt.kworks.service.export.ExportAuthorService;
import kr.co.gitt.kworks.service.user.UserService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service("exportAuthorService")
@Profile({"yg_dev", "yg_oper"})
public class ExportAuthorServiceImpl implements ExportAuthorService {

	/// 사용자 서비스
	@Resource
	UserService userService;
	
	/// 데이터 서비스
	@Resource
	DataService dataService;
	
	/// 내보내기 필터 이미지
	@Resource
	KwsExportFilterImageMapper kwsExportFilterImageMapper;
	
	/// 내보내기 필터 출력
	@Resource
	KwsExportFilterOutptMapper kwsExportFilterOutptMapper;

	@Override
	public boolean checkAutoConsent(KwsExport kwsExport) {
		KwsUser kwsUser = userService.selectOneUser(kwsExport.getRqesterId());

		// 관리자, 부서관리자인 경우 자동 승인
		if(kwsUser.getUserGrad().equals(UserGrad.ROLE_MNGR) || kwsUser.getUserGrad().equals(UserGrad.ROLE_DEPT_MNGR)) {
			return true;
		}
		// 엑셀의 경우 승인 완료 단계로 변경
		else if(kwsExport.getExportTy().equals(ExportTy.EXCEL)) {
			return true;
		}
		// Shape & Dxf 
		else if(kwsExport.getExportTy().equals(ExportTy.SHAPE) || kwsExport.getExportTy().equals(ExportTy.DXF)) {
			return false;
		}
		else {
			return true;		
		}
	}
}
// 			고급 출력 일때 항공사진이 포함되는 경우 자동 승인
//			if(kwsExport.getExportTy().equals(ExportTy.OUTPUT)) {
//				KwsExportFilterOutpt kwsExportFilterOutpt = kwsExportFilterOutptMapper.selectOne(kwsExport.getExportNo());
//				if(kwsExportFilterOutpt != null && StringUtils.isNotBlank(kwsExportFilterOutpt.getViewId()) && StringUtils.isNotBlank(kwsExportFilterOutpt.getTmsType())) {
//					return false;
//				}
//			}
//			else if(kwsExport.getExportTy().equals(ExportTy.IMAGE)) {
//				KwsExportFilterImage kwsExportFilterImage = kwsExportFilterImageMapper.selectOne(kwsExport.getExportNo());
//				if(kwsExportFilterImage != null && StringUtils.isNotBlank(kwsExportFilterImage.getViewId()) && StringUtils.isNotBlank(kwsExportFilterImage.getTmsType())) {
//					return false;
//				}
//			}
//			공간 데이터의 경우 내보내기 권한이 'Y' 인 데이터가 하나라도 포함되어 있는 경우 승인 절차 필요
//			DataSearchDTO dataSearchDTO = new DataSearchDTO();
//			dataSearchDTO.setExportAuthorAt("Y");
//			List<String> dataIds = new ArrayList<String>();
//			for(KwsExportData kwsExportData : kwsExport.getKwsExportDatas()) {
//				dataIds.add(kwsExportData.getDataId());
//			}
//			dataSearchDTO.setDataIds(dataIds);
//			
//			if(dataIds.size() > 0) {
//				List<KwsData> list = dataService.listAllData(dataSearchDTO);
//				if(list != null && list.size() > 0) {
//					return false;
//				}
//				else {
//					return true;
//				}
//			}
//			else {
//				return true;
//			}