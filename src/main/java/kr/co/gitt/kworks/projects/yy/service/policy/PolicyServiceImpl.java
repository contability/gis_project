package kr.co.gitt.kworks.projects.yy.service.policy;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.ImageDTO;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.mappers.KwsDeptMapper;
import kr.co.gitt.kworks.mappers.KwsDeptSubMapper;
import kr.co.gitt.kworks.model.KwsDept;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.model.KwsImage.ImageTy;
import kr.co.gitt.kworks.projects.yy.dto.PolicyDepRegisterSearchDTO;
import kr.co.gitt.kworks.projects.yy.dto.PolicyRepoRegisterDTO;
import kr.co.gitt.kworks.projects.yy.mappers.PlcyRepoCtMapper;
import kr.co.gitt.kworks.projects.yy.mappers.PlcyRepoMaMapper;
import kr.co.gitt.kworks.projects.yy.mappers.PlcyStatAsMapper;
import kr.co.gitt.kworks.projects.yy.model.PlcyEditHi;
import kr.co.gitt.kworks.projects.yy.model.PlcyRepoCt;
import kr.co.gitt.kworks.projects.yy.model.PlcyRepoHi;
import kr.co.gitt.kworks.projects.yy.model.PlcyRepoMa;
import kr.co.gitt.kworks.projects.yy.model.PlcyStatAs;
import kr.co.gitt.kworks.service.cmmn.ImageService;
import kr.co.gitt.kworks.service.data.DataService;
import kr.co.gitt.kworks.service.dept.DeptService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;


@Service("policyService")
@Profile({"yy_dev", "yy_oper", "gds_dev", "gds_oper"})
public class PolicyServiceImpl implements PolicyService {

	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 정책지도 맵퍼
	@Resource
	PlcyStatAsMapper plcyStatAsMapper;
	
	@Resource
	PlcyRepoMaMapper plcyRepoMaMapper;
	
	@Resource
	PlcyRepoCtMapper plcyRepoCtMapper;
	
	@Resource
	KwsDeptMapper kwsDeptMapper;
	
	@Resource
	KwsDeptSubMapper kwsDeptSubMapper;

	@Resource
	EgovIdGnrService plcyRepoMaIdGnrService;
	
	@Resource
	EgovIdGnrService plcyRepoHiIdGnrService;
	
	@Resource
	PolicyRegstrHistoryService policyRegstrHistoryService;
	
	/// 부서관리 서비스
	@Autowired
	DeptService deptService;
	
	/// 데이터 서비스
	@Resource
	DataService dataService;
	
	/**
	 *  이미지 서비스
	 */
	@Resource
	ImageService imageService;
	
	//부서목록
	//조건 검색 페이지 로딩 시 담당부서 리스트  KWS_DEPT 	
	@Override
	public List<KwsDept> listAllDept() {
		// 페이징 없이 현재 사용되는 모든 부서목록 조회
		List<KwsDept> kwsDepts = deptService.listDept("DEPT_NM");
		
		// 부서목록 중 정책지도에서 의미없는 부서코드 제외
		List<KwsDept> tempDeptList = new ArrayList<KwsDept>();
		tempDeptList.addAll(kwsDepts);
		for (KwsDept kwsDept : tempDeptList) {
			if (kwsDept.getDeptNm().equals("양양군") || kwsDept.getDeptNm().equals("군수") 
					|| kwsDept.getDeptNm().equals("부군수")) {
				kwsDepts.remove(kwsDept);
			}
		}
		
		return kwsDepts;
	}

	//조건 검색시 
	//resources.projects.yy.sqlmap.mappers.PlcyStatAsMapper
	//id="listDep" resultMap="policyDepRegisterSearchResultMap" parameterType="plcyStatAs"
	@Override
	public List<PolicyDepRegisterSearchDTO> listDep(PlcyStatAs plcyStatAs) {
		List<PolicyDepRegisterSearchDTO> policyDepRegisterSearchDTO = plcyStatAsMapper.listDep(plcyStatAs);
		return policyDepRegisterSearchDTO;
	}
	
	//KWS_MENU 위치 조회
	@Override
	public List<PolicyDepRegisterSearchDTO> listSearch(String geom) {
		return plcyStatAsMapper.listSearch(geom);
	}
	
	//단건검색
	@Override
	public PlcyStatAs selectOne(Long ftrIdn) {
		PlcyStatAs result = plcyStatAsMapper.selectOne(ftrIdn);
		return result;
	}
	
	@Override
	public List<KwsImage> listPhoto(String ftrCde, Long ftrIdn) throws Exception {
	
		KwsImage cql = new KwsImage();
		cql.setFtrCde(ftrCde);
		cql.setFtrIdn(ftrIdn);
		cql.setImageTy(ImageTy.POLICY_PHOTO);
		
		return imageService.listAllImage(cql);
	}
	
	@Override
	public KwsImage selectPhotoByNo(Long imageNo) throws Exception {
		
		return imageService.selectOneImage(imageNo);
	}
	
	@Override
	public KwsImage insertPhoto(ImageDTO imageDTO, MultipartFile multipartFile, int thumbnailWidth, int thumbnailHeight) throws Exception {

		return imageService.addImage(imageDTO, multipartFile, thumbnailWidth, thumbnailHeight);
	}
	
	@Override
	public KwsImage updatePhoto(ImageDTO imageDTO, MultipartFile multipartFile, int thumbnailWidth, int thumbnailHeight) throws Exception {

		return imageService.modifyImage(imageDTO, multipartFile, thumbnailWidth, thumbnailHeight);
	}

	@Override
	public Integer update(PlcyStatAs plcyStatAs) throws Exception {

		//부서코드
		//plcyStatAs.setDeptSbCd(plcyStatAs.getDeptSbNm());
		//plcyStatAs.setDeptSbCd(plcyStatAs.getDeptSbNm());
		
		//부서명
		plcyStatAs.setDeptSbNm(kwsDeptSubMapper.deptSubNmReturn(plcyStatAs.getDeptSbNm()));
		
		//실과소
		plcyStatAs.setPlcyDep(kwsDeptMapper.deptNmReturn(plcyStatAs.getPlcyDep()));
		
		Integer rowCount = plcyStatAsMapper.update(plcyStatAs);
		Long ftrIdn = plcyStatAs.getFtrIdn();
		String plcyTit = plcyStatAs.getPlcyTit();
		insertEditHistory(ftrIdn, plcyTit, "ATTR", "UPD");
		
		return rowCount;
	}
	
	//보고서 관련
	//////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public List<PlcyRepoMa> list(PlcyRepoMa plcyRepoMa) {
		return plcyRepoMaMapper.list(plcyRepoMa);
	}

	@Override
	public Integer insertRepoMaRegster(PolicyRepoRegisterDTO policyRepoRegstr) throws Exception {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		Integer repoVer = 1;
		Long ftrIdn =  policyRepoRegstr.getFtrIdn();

		//보고서저장
		PlcyRepoMa plcyRepoMa =  new PlcyRepoMa();
		String userId = userDTO.getUserId(); //사용자아이디
		Long repoIdn = policyRepoRegstr.getRepoIdn();
		String repoTit = policyRepoRegstr.getRepoTit();
		String plcyTit = policyRepoRegstr.getRepoTit();
		
		plcyRepoMa.setRepoMak(userId); //사용자아이디
		plcyRepoMa.setFtrIdn(ftrIdn); //관리번호
		plcyRepoMa.setFtrCde(policyRepoRegstr.getFtrCde()); //지형지물부호
		plcyRepoMa.setRepoIdn(repoIdn); //보고서 관리번호
		plcyRepoMa.setRepoTit(repoTit); //보고서 제목
		plcyRepoMa.setRepoVer(repoVer); // 보고서 버전
		
		Integer result = plcyRepoMaMapper.insert(plcyRepoMa);
		
		insertRepoHi(ftrIdn, userId, repoIdn, plcyTit, repoTit, repoVer, "INS");
		
		return result;
	}
	
	@Override
	public Integer insertRepoCtRegster(PolicyRepoRegisterDTO policyRepoRegstr) throws Exception {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		Integer repoVer = 1;

		//보고서 내역저장
		PlcyRepoCt plcyRepoCt = new PlcyRepoCt();
		plcyRepoCt.setRepoIdn(policyRepoRegstr.getRepoIdn()); // 보고서 관리번호
		plcyRepoCt.setRepoVer(repoVer); // 보고서 번호
		plcyRepoCt.setRepoDoc(policyRepoRegstr.getRepoDoc()); //보고서 내용
		//plcyRepoCt.setRepoPrt(policyRepoRegstr.getRepoPrt()); //보고서 내용
		plcyRepoCt.setRepoLst(userDTO.getUserId()); //수정자
		String repoDoc = policyRepoRegstr.getRepoDoc(); //보고서내용
		String repoPar = repoDoc;
		
		String prtPdel = repoPar.replaceAll("<p>",""); //패널태그삭제
		String prtPdel2 = prtPdel.replaceAll("</p>","<br>"); //패널태그삭제
		
		String prtSdel = prtPdel2.replaceAll("<strong>","<B>"); //진하게 태그 변경
		String prtSdel2 = prtSdel.replaceAll("</strong>","</B>"); //진하게 태그변경
		
		String prtEdel = prtSdel2.replaceAll("<em>","<I>"); //기울기 태그 변경
		String prtEdel2 = prtEdel.replaceAll("</em>","</I>"); //기울기 태그변경
		
		String prtSUdel = prtEdel2.replaceAll("<sup>","<P>"); //아랫첨자 태그 변경
		String prtSUdel2 = prtSUdel.replaceAll("</sup>","</P>"); //아랫첨자 태그변경
		
		String prtSUBdel = prtSUdel2.replaceAll("<sub>","<D>"); //아랫첨자 태그 변경
		String prtSUBdel2 = prtSUBdel.replaceAll("</sub>","</D>"); //아랫첨자 태그변경

		String prtUteg = prtSUBdel2.replaceAll("<u>","<U>"); //폰트 태그변경
		String prtUteg1 = prtUteg.replaceAll("</u>","</U>"); //폰트 태그변경
		
		String prtSteg = prtUteg1.replaceAll("<s>","<S>"); //폰트 태그변경
		String prtSteg1 = prtSteg.replaceAll("</s>","</S>"); //폰트 태그변경
		
		//&nbsp;
		String prtnbsp = prtSteg1.replaceAll("&nbsp;",""); //폰트 태그변경
		
		Document doc = Jsoup.parse(prtnbsp);
		Element body = doc.body();
		List<Node> nodes = paserReport(body.childNodesCopy());
		Element newReport = new Element("body");
		for (Node node : nodes)
			newReport.appendChild(node);

		String repoPrtPar = newReport.html();
		
		//사이즈태그 파싱
		String FontPrt8 = repoPrtPar.replaceAll("</Size.8>","</Size>");
		String FontPrt9 = FontPrt8.replaceAll("</Size.9>","</Size>");
		String FontPrt10 = FontPrt9.replaceAll("</Size.10>","</Size>");
		String FontPrt11 = FontPrt10.replaceAll("</Size.11>","</Size>");
		String FontPrt12 = FontPrt11.replaceAll("</Size.12>","</Size>");
		String FontPrt14 = FontPrt12.replaceAll("</Size.14>","</Size>");
		String FontPrt15 = FontPrt14.replaceAll("</Size.15>","</Size>");
		String FontPrt16 = FontPrt15.replaceAll("</Size.16>","</Size>");
		String FontPrt18 = FontPrt16.replaceAll("</Size.18>","</Size>");
		String FontPrt20 = FontPrt18.replaceAll("</Size.20>","</Size>");
		String FontPrt22 = FontPrt20.replaceAll("</Size.22>","</Size>");
		String FontPrt24 = FontPrt22.replaceAll("</Size.24>","</Size>");
		String FontPrt26 = FontPrt24.replaceAll("</Size.26>","</Size>");
		String FontPrt28 = FontPrt26.replaceAll("</Size.28>","</Size>");
		String FontPrt36 = FontPrt28.replaceAll("</Size.36>","</Size>");
		String FontPrt48 = FontPrt36.replaceAll("</Size.48>","</Size>");
		String FontPrt72 = FontPrt48.replaceAll("</Size.72>","</Size>");

		//폰트 파싱
		String FontPrtOffFM = FontPrt72.replaceAll("</Font.malgun gothic>", "</Font>");
		String FontPrtOffFG = FontPrtOffFM.replaceAll("</Font.gulim>", "</Font>");
		String FontPrtOffFD = FontPrtOffFG.replaceAll("</Font.dotum>", "</Font>");
		String FontPrtOffFB = FontPrtOffFD.replaceAll("</Font.batang>", "</Font>");
		String FontPrtOffFGu = FontPrtOffFB.replaceAll("</Font.gungsuh>", "</Font>");
		String FontPrtOffFGuD = FontPrtOffFGu.replaceAll("</Font.나눔명조>", "</Font>");
		String FontPrtOffFGua = FontPrtOffFGuD.replaceAll("</Font.휴먼명조>", "</Font>");
		
		String FontPrtOnFM = FontPrtOffFGua.replaceAll("<Font.malgun gothic>", "<Font.맑은 고딕>");
		String FontPrtOnFG = FontPrtOnFM.replaceAll("<Font.gulim>", "<Font.굴림>");
		String FontPrtOnFD = FontPrtOnFG.replaceAll("<Font.dotum>", "<Font.돋움>");
		String FontPrtOnFB = FontPrtOnFD.replaceAll("<Font.batang>", "<Font.바탕>");
		String FontPrtOnFGu = FontPrtOnFB.replaceAll("<Font.gungsuh>", "<Font.궁서>");
		String FontPrtOnFGuD = FontPrtOnFGu.replaceAll("<Font.나눔명조>", "<Font.나눔명조>");
		String FontPrtOnFGuDs = FontPrtOnFGuD.replaceAll("<Font.휴먼명조>", "<Font.휴먼명조>");
		String FontPrtEnter = FontPrtOnFGuDs.replaceAll("\n", "");
		
		String repoPrt = FontPrtEnter;
		//System.out.println("repoPrt = " + repoPrt);
		
		plcyRepoCt.setRepoPrt(repoPrt); //수정자
		Integer result = plcyRepoCtMapper.insert(plcyRepoCt);
		
		return result;
	}


	@Override
	public List<PlcyRepoCt> list(PlcyRepoCt plcyRepoCt) {
		return plcyRepoCtMapper.list(plcyRepoCt);
	}

	@Override
	public PolicyRepoRegisterDTO selectOneRepo(Long ftrIdn){
		return plcyRepoCtMapper.selectOne(ftrIdn);
	}
	
	public void insertRepoHi(Long ftrIdn, String userId, Long repoIdn, String plcyTit, String repoTit, Integer repoVer, String editType) throws Exception {
		PlcyRepoHi plcyRepoHi = new PlcyRepoHi(); 
		Long histNo = plcyRepoHiIdGnrService.getNextLongId();  // 이력 순번
		plcyRepoHi.setEditType(editType);
		plcyRepoHi.setFtrIdn(ftrIdn);
		plcyRepoHi.setHistNo(histNo);
		plcyRepoHi.setPlcyTit(plcyTit);
		plcyRepoHi.setRepoIdn(repoIdn);
		plcyRepoHi.setRepoVer(repoVer);
		plcyRepoHi.setUserId(userId);
		plcyRepoHi.setRepoTit(repoTit);
		
		policyRegstrHistoryService.insertRepoHi(plcyRepoHi);
	}

	@Override
	public Integer updateRepoMaReg(PolicyRepoRegisterDTO policyRepoRegisterDTO) throws Exception {
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser(); // userDTO
		Long ftrIdn = policyRepoRegisterDTO.getFtrIdn(); //관리번호
		Long repoIdn = plcyRepoMaMapper.selectRepoIdn(ftrIdn); //보고서관리번호
		String repoTit = policyRepoRegisterDTO.getRepoTit(); //보고서 제목 
		String repoDoc = policyRepoRegisterDTO.getRepoDoc(); //보고서내용
		String repoPar = repoDoc;
		
		String prtPdel = repoPar.replaceAll("<p>",""); //패널태그삭제
		String prtPdel2 = prtPdel.replaceAll("</p>","<br>"); //패널태그삭제
		
		String prtSdel = prtPdel2.replaceAll("<strong>","<B>"); //진하게 태그 변경
		String prtSdel2 = prtSdel.replaceAll("</strong>","</B>"); //진하게 태그변경
		
		String prtEdel = prtSdel2.replaceAll("<em>","<I>"); //기울기 태그 변경
		String prtEdel2 = prtEdel.replaceAll("</em>","</I>"); //기울기 태그변경
		
		String prtSUdel = prtEdel2.replaceAll("<sup>","<P>"); //아랫첨자 태그 변경
		String prtSUdel2 = prtSUdel.replaceAll("</sup>","</P>"); //아랫첨자 태그변경
		
		String prtSUBdel = prtSUdel2.replaceAll("<sub>","<D>"); //아랫첨자 태그 변경
		String prtSUBdel2 = prtSUBdel.replaceAll("</sub>","</D>"); //아랫첨자 태그변경

		String prtUteg = prtSUBdel2.replaceAll("<u>","<U>"); //폰트 태그변경
		String prtUteg1 = prtUteg.replaceAll("</u>","</U>"); //폰트 태그변경
		
		String prtSteg = prtUteg1.replaceAll("<s>","<S>"); //폰트 태그변경
		String prtSteg1 = prtSteg.replaceAll("</s>","</S>"); //폰트 태그변경
		
		//&nbsp;
		String prtnbsp = prtSteg1.replaceAll("&nbsp;",""); //폰트 태그변경
		
		Document doc = Jsoup.parse(prtnbsp);
		Element body = doc.body();
		List<Node> nodes = paserReport(body.childNodesCopy());
		Element newReport = new Element("body");
		for (Node node : nodes)
			newReport.appendChild(node);

		String repoPrtPar = newReport.html();
		
		//사이즈태그 파싱
		String FontPrt8 = repoPrtPar.replaceAll("</Size.8>","</Size>");
		String FontPrt9 = FontPrt8.replaceAll("</Size.9>","</Size>");
		String FontPrt10 = FontPrt9.replaceAll("</Size.10>","</Size>");
		String FontPrt11 = FontPrt10.replaceAll("</Size.11>","</Size>");
		String FontPrt12 = FontPrt11.replaceAll("</Size.12>","</Size>");
		String FontPrt14 = FontPrt12.replaceAll("</Size.14>","</Size>");
		String FontPrt15 = FontPrt14.replaceAll("</Size.15>","</Size>");
		String FontPrt16 = FontPrt15.replaceAll("</Size.16>","</Size>");
		String FontPrt18 = FontPrt16.replaceAll("</Size.18>","</Size>");
		String FontPrt20 = FontPrt18.replaceAll("</Size.20>","</Size>");
		String FontPrt22 = FontPrt20.replaceAll("</Size.22>","</Size>");
		String FontPrt24 = FontPrt22.replaceAll("</Size.24>","</Size>");
		String FontPrt26 = FontPrt24.replaceAll("</Size.26>","</Size>");
		String FontPrt28 = FontPrt26.replaceAll("</Size.28>","</Size>");
		String FontPrt36 = FontPrt28.replaceAll("</Size.36>","</Size>");
		String FontPrt48 = FontPrt36.replaceAll("</Size.48>","</Size>");
		String FontPrt72 = FontPrt48.replaceAll("</Size.72>","</Size>");

		//폰트 파싱
		String FontPrtOffFM = FontPrt72.replaceAll("</Font.맑은 고딕>", "</Font>");
		String FontPrtOffFG = FontPrtOffFM.replaceAll("</굴림>", "</Font>");
		String FontPrtOffFD = FontPrtOffFG.replaceAll("</Font.돋움>", "</Font>");
		String FontPrtOffFB = FontPrtOffFD.replaceAll("</Font.바탕>", "</Font>");
		String FontPrtOffFGu = FontPrtOffFB.replaceAll("</Font.궁서>", "</Font>");
		String FontPrtOffFGuD = FontPrtOffFGu.replaceAll("</Font.나눔명조>", "</Font>");
		String FontPrtOffFGuDs = FontPrtOffFGuD.replaceAll("</Font.휴먼명조>", "</Font>");
//		String FontPrtOnFM = FontPrtOffFGu.replaceAll("<Font.malgun gothic>", "<Font.맑은 고딕>");
//		String FontPrtOnFG = FontPrtOnFM.replaceAll("<Font.gulim>", "<Font.굴림>");
//		String FontPrtOnFD = FontPrtOnFG.replaceAll("<Font.dotum>", "<Font.돋움>");
//		String FontPrtOnFB = FontPrtOnFD.replaceAll("<Font.batang>", "<Font.바탕>");
//		String FontPrtOnFGu = FontPrtOnFB.replaceAll("<Font.gungsuh>", "<Font.궁서>");
		String FontPrtEnterK = FontPrtOffFGuDs.replaceAll("\n", "");
		String FontPrtOnB = FontPrtEnterK.replaceAll("<b>","<B>"); //진하게 태그 변경
		String FontPrtOffB = FontPrtOnB.replaceAll("</b>","</B>"); //진하게 태그변경
		
		String repoPrt = FontPrtOffB;
		
		
		//System.out.println("repoPrt = " + repoPrt);
		String userId = userDTO.getUserId(); //USER ID
		
		String plcyTit = plcyStatAsMapper.selectPlcyTit(ftrIdn);
		
		Integer reVer = plcyRepoMaMapper.repoVerSeq(ftrIdn, repoIdn) + 1; //보고서 버전 
		
		//보고서 업데이트 
		PlcyRepoMa plcyRepoMa = new PlcyRepoMa();
		plcyRepoMa.setRepoVer(reVer); // 보고서 버전
		plcyRepoMa.setFtrIdn(ftrIdn); // 관리번호
		plcyRepoMa.setRepoIdn(repoIdn); // 보고서번호
		plcyRepoMa.setRepoTit(repoTit); // 보고서제목
		plcyRepoMa.setRepoLst(userId); // userId
		plcyRepoMa.setFtrCde("PA000"); // ftrCde
		plcyRepoMaMapper.update(plcyRepoMa);
		
		//보고서내역 INS
		PlcyRepoCt plcyRepoCt = new PlcyRepoCt();
		plcyRepoCt.setRepoIdn(repoIdn); // 보고서 관리번호 
		plcyRepoCt.setRepoVer(reVer); // 보고서 버전
		plcyRepoCt.setRepoDoc(repoDoc); // 보고서 내용
		plcyRepoCt.setRepoPrt(repoPrt); // 보고서 내용
		plcyRepoCt.setRepoLst(userId); //userID
		plcyRepoCtMapper.insert(plcyRepoCt);
		
		insertRepoHi(ftrIdn, userId, repoIdn, plcyTit, repoTit, reVer, "UPD"); // 이력저장
		
		return 1;
	}

	private List<Node> paserReport(List<Node> nodes) {
		List<Node> newNodes = new ArrayList<Node>();
		
		if (nodes == null || nodes.size() <= 0)
			return newNodes;
		
		for (Node node : nodes) {
			if (node instanceof TextNode ) {
				newNodes.add(node.clone());
				continue;
			}
			
			Element ele = (Element) node.clone();
			String type = ele.tagName();
			if (type.equalsIgnoreCase("span")) {
				newNodes.add(replaceTag(ele));
			}
			else {
				List<Node> oldChilds = ele.childNodesCopy();
				List<Node> newChilds = paserReport(oldChilds);
				ele = new Element(type);
				for (Node child : newChilds) {
					ele.appendChild(child.clone());
				}
				newNodes.add(ele);
			}
		}
		return newNodes;
	}
	
	private Element replaceTag(Element element){
		Element newEle = null;
		if (!element.hasAttr("style")){
			newEle = element;
		}//<span style="font-size: 14px;"> □ 양양군의 지하시설물 조사 및 탐사를 통한 정확한 DB구축</span>
		else {
			String style = element.attr("style");
			if (style.contains("font-size:8px")){
				newEle = new Element("Size.8");
			}
			else if (style.contains("font-size:9px")){
				newEle = new Element("Size.9");
			}
			else if (style.contains("font-size:10px")){
				newEle = new Element("Size.10");
			}
			else if (style.contains("font-size:11px")){
				newEle = new Element("Size.11");
			}
			else if (style.contains("font-size:12px")){
				newEle = new Element("Size.12");
			}
			else if (style.contains("font-size:14px")){
				newEle = new Element("Size.14");
			}
			else if (style.contains("font-size:15px")){
				newEle = new Element("Size.15");
			}
			else if (style.contains("font-size:16px")){
				newEle = new Element("Size.16");
			}
			else if (style.contains("font-size:18px")){
				newEle = new Element("Size.18");
			}
			else if (style.contains("font-size:20px")){
				newEle = new Element("Size.20");
			}
			else if (style.contains("font-size:22px")){
				newEle = new Element("Size.22");
			}
			else if (style.contains("font-size:24px")){
				newEle = new Element("Size.24");
			}
			else if (style.contains("font-size:26px")){
				newEle = new Element("Size.26");
			}
			else if (style.contains("font-size:28px")){
				newEle = new Element("Size.28");
			}
			else if (style.contains("font-size:36px")){
				newEle = new Element("Size.36");
			}
			else if (style.contains("font-size:48px")){
				newEle = new Element("Size.48");
			}
			else if (style.contains("font-size:72px")){
				newEle = new Element("Size.72");
			}
			else if (style.contains("font-size: 8px")){
				newEle = new Element("Size.8");
			}
			else if (style.contains("font-size: 9px")){
				newEle = new Element("Size.9");
			}
			else if (style.contains("font-size: 10px")){
				newEle = new Element("Size.10");
			}
			else if (style.contains("font-size: 11px")){
				newEle = new Element("Size.11");
			}
			else if (style.contains("font-size: 12px")){
				newEle = new Element("Size.12");
			}
			else if (style.contains("font-size: 14px")){
				newEle = new Element("Size.14");
			}
			else if (style.contains("font-size: 15px")){
				newEle = new Element("Size.15");
			}
			else if (style.contains("font-size: 16px")){
				newEle = new Element("Size.16");
			}
			else if (style.contains("font-size: 18px")){
				newEle = new Element("Size.18");
			}
			else if (style.contains("font-size: 20px")){
				newEle = new Element("Size.20");
			}
			else if (style.contains("font-size: 22px")){
				newEle = new Element("Size.22");
			}
			else if (style.contains("font-size: 24px")){
				newEle = new Element("Size.24");
			}
			else if (style.contains("font-size: 26px")){
				newEle = new Element("Size.26");
			}
			else if (style.contains("font-size: 28px")){
				newEle = new Element("Size.28");
			}
			else if (style.contains("font-size: 36px")){
				newEle = new Element("Size.36");
			}
			else if (style.contains("font-size: 48px")){
				newEle = new Element("Size.48");
			}
			else if (style.contains("font-size: 72px")){
				newEle = new Element("Size.72");
			}
			else if (style.contains("font-family:malgun gothic")){
				newEle = new Element("Font.맑은 고딕");
			}
			else if (style.contains("font-family:gulim")){
				newEle = new Element("Font.굴림");
			}
			else if (style.contains("font-family:dotum")){
				newEle = new Element("Font.돋움");
			}
			else if (style.contains("font-family:batang")){
				newEle = new Element("Font.바탕");
			}
			else if (style.contains("font-family:gungsuh")){
				newEle = new Element("Font.궁서");
			}
			else if (style.contains("font-family:나눔명조")){
				newEle = new Element("Font.나눔명조");
			}
			else if (style.contains("font-family: 휴먼명조")){
				newEle = new Element("Font.휴먼명조");
			}
			else if (style.contains("font-family:휴먼명조")){
				newEle = new Element("Font.휴먼명조");
			}
		}
			
		List<Node> nodes = paserReport(element.childNodesCopy());
		newEle.empty();
		for (Node node : nodes){
			newEle.appendChild(node.clone());
		}
		
		return newEle;
	}
	
	@Override
	public Integer deleteRepoMa(PolicyRepoRegisterDTO policyRepoReter) throws Exception {
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser(); // userDTO
		Long ftrIdn = policyRepoReter.getFtrIdn(); //관리번호
		Long repoIdn = policyRepoReter.getRepoIdn(); //보고서관리번호
		//String repoTit = policyRepoReter.getRepoTit(); //보고서 제목 
		String userId = userDTO.getUserId(); //USER ID
		
		Integer reVer = plcyRepoMaMapper.repoVerSeq(ftrIdn, repoIdn);
		//정책명
		String plcyTit = plcyStatAsMapper.selectPlcyTit(ftrIdn);
		
		String repoTit = plcyRepoMaMapper.selectRepoTit(ftrIdn, repoIdn);
		
		insertRepoHi(ftrIdn, userId, repoIdn, plcyTit, repoTit, reVer, "DEL"); // 이력저장
		
		plcyRepoCtMapper.delete(repoIdn);
		
		Integer rowCount = plcyRepoMaMapper.delete(ftrIdn);
		
		return rowCount;
	}
	
	@Override
	public Integer insertPlcy(Long ftrIdn, String userId, String plcyTit) throws Exception {
		
		// 대장 편집이력
		PlcyEditHi plcyEditHi = new PlcyEditHi();
		plcyEditHi.setUserId(userId);
		plcyEditHi.setFtrIdn(ftrIdn);
		plcyEditHi.setEditData("GEOM");
		plcyEditHi.setEditType("INS");
		plcyEditHi.setPlcyTit(plcyTit);
		
		policyRegstrHistoryService.insert(plcyEditHi);
		
		return 0;
	}

	private void insertEditHistory(Long ftrIdn, String plcyTit, String editData, String editType) throws Exception {
		
		// 대장 편집이력
		PlcyEditHi plcyEditHi = new PlcyEditHi(); 
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		plcyEditHi.setUserId(userDTO.getUserId());
		plcyEditHi.setFtrIdn(ftrIdn);
		plcyEditHi.setEditData(editData);
		plcyEditHi.setEditType(editType);
		plcyEditHi.setPlcyTit(plcyTit);
		
		policyRegstrHistoryService.insert(plcyEditHi);
	}
	
	//정책지도 대장삭제
	@Override
	public Integer removePlcyStatAs(Long ftrIdn) throws Exception {
		PlcyStatAs plcyStatAs = new PlcyStatAs();
		
		plcyStatAs = plcyStatAsMapper.selectPlcyStatAs(ftrIdn);
		Integer rowCount = plcyStatAsMapper.delete(ftrIdn);
		String plcyTit = plcyStatAs.getPlcyTit();
		insertEditHistory(ftrIdn, plcyTit, "ATTR", "DEL");
		
		return rowCount;
	}

	@Override
	public Integer removeImage(Long ftrIdn) throws Exception {
		PlcyStatAs plcyStatAs = new PlcyStatAs();
		
		plcyStatAs = plcyStatAsMapper.selectPlcyStatAs(ftrIdn);
		Integer rowCount = plcyStatAsMapper.deleteImage(ftrIdn);
		String plcyTit = plcyStatAs.getPlcyTit();
		insertEditHistory(ftrIdn, plcyTit, "ATTR", "DEL");
		
		return rowCount;
	}

	@Override
	public PlcyStatAs selectPlcyAdr(Long ftrIdn) {
		return plcyStatAsMapper.selectPlcyAdr(ftrIdn);
	}
	
	@Override
	public List<PolicyDepRegisterSearchDTO> policyQuickSearch(String plcyAdr) {
		return plcyStatAsMapper.quickSearch(plcyAdr);
	}

	@Override
	public List<KwsDept> listAllDeptCategory() {
		return kwsDeptMapper.listDeptSub();
	}

	@Override
	public List<PlcyStatAs> deptSubList(String deptSbCd) {
		return plcyStatAsMapper.deptSubList(deptSbCd);
	}

	@Override
	public String deptNmReturn(String deptCode) {
		return kwsDeptMapper.deptNmReturn(deptCode);
	}

	@Override
	public String deptSubNmReturn(String deptSbCd) {
		return kwsDeptSubMapper.deptSubNmReturn(deptSbCd);
	}

	@Override
	public String forDeptCode(String deptSbCd) {
		return kwsDeptSubMapper.forDeptCode(deptSbCd);
	}
	
//	//부서코드
//	plcyStatAs.setDeptSubCode(plcyStatAs.getDeptSubNm());
//	
//	//부서명
//	plcyStatAs.setDeptSubNm(kwsDeptSubMapper.deptSubNmReturn(plcyStatAs.getDeptSubNm()));
//	
//	//실과소
//	plcyStatAs.setPlcyDep(kwsDeptMapper.deptNmReturn(plcyStatAs.getPlcyDep()));
//	

}
