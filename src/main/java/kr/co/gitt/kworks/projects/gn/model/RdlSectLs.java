package kr.co.gitt.kworks.projects.gn.model;

public class RdlSectLs {
	
	/// objectid
	private Long objectid;
	
	/// wkt(geom)
	private String wkt;
	
	/// 관리번호
	private Long ftrIdn;
	
	/// 지형지물부호
	private String ftrCde;
	
	/// 관리기관
	private String mngCde;
	
	/// 도로종류
	private String rodTyp;
	
	/// 노선명
	private String rotNam;
	
	/// 노선번호
	private String rotIdn;
	
	/// 구간번호
	private String secIdn;
	
	/// 노선 지정(인정) 연월일
	private String rotYmd;
	
	///  도로구역 결정(변경) 연월일
	private String rodYmd;
	
	/// 접도구역 지정 연월일
	private String jdhYmd;
	
	/// 지적고시 연월일
	private String itlYmd;
	
	/// 노선시점 위치(주소)
	private String rscAdr;
	
	/// 노선종점 위치(주소)
	private String rejAdr;
	
	/// 주요한 통과지
	private String impAdr;
	
	/// 노선연장
	private Long rotLen;
	
	/// 전용연장
	private Long mixLen;
	
	/// 중용연장
	private Long perLen;
	
	/// 통행불능연장
	private Long ntrLen;
	
	/// 포장도로_전체연장
	private Long pntLen;
	
	/// 포장도로_도로연장
	private Long prdLen;
	
	/// 포장도로_터널개소(2차로)
	private Long pu2Cnt;
	
	/// 포장도로_터널개소(3차로)
	private Long pu3Cnt;
	
	/// 포장도로_터널개소(4차로)
	private Long pu4Cnt;
	
	/// 포장도로_터널개소(5차로이상)
	private Long pu5Cnt;
	
	/// 포장도로_터널개소(전체)
	private Long puaCnt;
	
	/// 포장도로_터널연장(2차로)
	private Long pu2Len;
	
	/// 포장도로_터널연장(3차로)
	private Long pu3Len;
	
	/// 포장도로_터널연장(4차로)
	private Long pu4Len;
	
	/// 포장도로_터널연장(5차로이상)
	private Long pu5Len;
	
	/// 포장도로_터널연장(전체)
	private Long puaLen;
	
	/// 포장도로_교량개소(강교)
	private Long pukCnt;
	
	/// 포장도로_교량개소(철근콘크리트교)
	private Long pucCnt;
	
	/// 포장도로_교량개소(합성교)
	private Long puhCnt;
	
	/// 포장도로_교량개소(기타)
	private Long purCnt;

	/// 포장도로_교량개소(전체)
	private Long pujCnt;
	
	/// 포장도로_교량연장(강교)
	private Long pukLen;
	
	/// 포장도로_교량연장(철근콘크리트교)
	private Long pucLen;
	
	/// 포장도로_교량연장(합성교)
	private Long puhLen;
	
	/// 포장도로_교량연장(기타)
	private Long purLen;
	
	/// 포장도로_교량연장(전체)
	private Long pujLen;
	
	/// 비포장도로연장
	private Long nprLen;
	
	/// 미개통도로연장
	private Long ntdLen;
	
	/// 폭원_전체
	private Long widAll;
	
	/// 폭원_차도
	private Long widRod;
	
	/// 폭원_중앙분리대
	private Long widCen;
	
	/// 폭원_길어깨(보도)
	private Long widGil;
	
	/// 포장두께_전체
	private Long phcAll;
	
	/// 포장두께_포장기층/포장슬래브
	private Long phcPhg;
	
	/// 포장두께_보조기층
	private Long phcSub;
	
	/// 도로연장(2차로미만)
	private Long frdLen;
	
	/// 도로연장(2차로-4차로미만)
	private Long srdLen;
	
	/// 도로연장(4차로-6차로미만)
	private Long trdLen;
	
	/// 도로연장(6차로이상)
	private Long urdLen;
	
	/// 차도 연장_전체(상행)
	private Long rduLen;
	
	/// 차도 연장_전체(하행)
	private Long rddLen;
	
	/// 아스팔트 차도 연장(상행)
	private Long asuLen;
	
	/// 아스팔트 차도 연장(하행)
	private Long asdLen;
	
	/// 콘크리트 차도 연장(상행)
	private Long cnuLen;
	
	/// 콘크리트 차도 연장(하행)
	private Long cndLen;
	
	/// 비포장 차도 연장(상행)
	private Long npuLen;
	
	/// 비포장 차도 연장(하행)
	private Long npdLen;
	
	/// 길어깨(보도) 포장(좌)
	private Long pmyLft;
	
	/// 길어깨(보도) 포장(우)
	private Long pmyRit;
	
	/// 길어깨(보도) 비포장(좌)
	private Long pmnLft;
	
	/// 길어깨(보도) 비포장(우)
	private Long pmnRit;
	
	/// 자전거도로(좌)
	private Long bycLit;
	
	/// 자전거도로(우)
	private Long bycRit;
	
	/// 도로부지면적 계
	private Long areAll;
	
	/// 도로부지면적 국유지
	private Long areNat;
	
	/// 도로부지면적 공유지
	private Long areLoc;
	
	/// 도로부지면적 사유지
	private Long arePri;
	
	/// 곡선반경(개소) 100m미만
	private Long radF10;
	
	/// 곡선반경(개소) 200m미만
	private Long radF20;
	
	/// 곡선반경(개소) 300m미만
	private Long radF30;
	
	/// 곡선반경(개소) 460m미만
	private Long radF46;
	
	/// 곡선반경(개소) 700m미만
	private Long radF70;
	
	/// 곡선반경(개소) 700m이상
	private Long radU70;
	
	/// 교차(개소) 육교
	private Long rtrObg;
	
	/// 교차(개소) 지하도
	private Long rtrSub;
	
	/// 교차(개소) 철도 과선
	private Long rtrCgu;
	
	/// 교차(개소) 철도 가도
	private Long rtrCga;
	
	/// 교차(개소) 도로 평면
	private Long rtrR2d;
	
	/// 교차(개소) 도로 입체
	private Long rtrR3d;
	
	/// 종단경사(3%미만)_개소
	private Long fgbCnt;
	
	/// 종단경사(3%미만)_연장
	private Long fgbLen;
	
	/// 종단경사(3-5%미만)_개소
	private Long sgbCnt;
	
	/// 종단경사(3-5%미만)_연장
	private Long sgbLen;
	
	/// 종단경사(5-10%미만)_개소
	private Long tgbCnt;
	
	/// 종단경사(5-10%미만)_연장
	private Long tgbLen;
	
	/// 종단경사(10%이상)_개소
	private Long ugbCnt;
	
	/// 종단경사(10%이상)_연장
	private Long ugbLen;
	
	/// 유료도로_관리자
	private String tolMan;
	
	/// 유료도로_요금징수_시작
	private String tosSta;
	
	/// 유료도로_요금징수_종료
	private String todEnd;
	
	/// 유료도로_요금징수 시설수
	private Long tolCnt;
	
	/// 유료도로_요금징수근거
	private String tolRec;
	
	/// 유료도로 전체 연장
	private Long toaLen;
	
	/// 유료도로 도로_연장
	private Long torLen;
	
	/// 유료도로 터널_개소
	private Long totCnt;
	
	/// 유료도로 터널_연장
	private Long totLen;
	
	/// 유료도로 교량_개소
	private Long tobCnt;
	
	/// 유료도로 교량_연장
	private Long tobLen;
	
	/// 비고
	private String rmkExp;

	public Long getObjectid() {
		return objectid;
	}

	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}

	public String getWkt() {
		return wkt;
	}

	public void setWkt(String wkt) {
		this.wkt = wkt;
	}

	public Long getFtrIdn() {
		return ftrIdn;
	}

	public void setFtrIdn(Long ftrIdn) {
		this.ftrIdn = ftrIdn;
	}

	public String getFtrCde() {
		return ftrCde;
	}

	public void setFtrCde(String ftrCde) {
		this.ftrCde = ftrCde;
	}

	public String getMngCde() {
		return mngCde;
	}

	public void setMngCde(String mngCde) {
		this.mngCde = mngCde;
	}

	public String getRodTyp() {
		return rodTyp;
	}

	public void setRodTyp(String rodTyp) {
		this.rodTyp = rodTyp;
	}

	public String getRotNam() {
		return rotNam;
	}

	public void setRotNam(String rotNam) {
		this.rotNam = rotNam;
	}

	public String getRotIdn() {
		return rotIdn;
	}

	public void setRotIdn(String rotIdn) {
		this.rotIdn = rotIdn;
	}

	public String getSecIdn() {
		return secIdn;
	}

	public void setSecIdn(String secIdn) {
		this.secIdn = secIdn;
	}

	public String getRotYmd() {
		return rotYmd;
	}

	public void setRotYmd(String rotYmd) {
		this.rotYmd = rotYmd;
	}

	public String getRodYmd() {
		return rodYmd;
	}

	public void setRodYmd(String rodYmd) {
		this.rodYmd = rodYmd;
	}

	public String getJdhYmd() {
		return jdhYmd;
	}

	public void setJdhYmd(String jdhYmd) {
		this.jdhYmd = jdhYmd;
	}

	public String getItlYmd() {
		return itlYmd;
	}

	public void setItlYmd(String itlYmd) {
		this.itlYmd = itlYmd;
	}

	public String getRscAdr() {
		return rscAdr;
	}

	public void setRscAdr(String rscAdr) {
		this.rscAdr = rscAdr;
	}

	public String getRejAdr() {
		return rejAdr;
	}

	public void setRejAdr(String rejAdr) {
		this.rejAdr = rejAdr;
	}

	public String getImpAdr() {
		return impAdr;
	}

	public void setImpAdr(String impAdr) {
		this.impAdr = impAdr;
	}

	public Long getRotLen() {
		return rotLen;
	}

	public void setRotLen(Long rotLen) {
		this.rotLen = rotLen;
	}

	public Long getMixLen() {
		return mixLen;
	}

	public void setMixLen(Long mixLen) {
		this.mixLen = mixLen;
	}

	public Long getPerLen() {
		return perLen;
	}

	public void setPerLen(Long perLen) {
		this.perLen = perLen;
	}

	public Long getNtrLen() {
		return ntrLen;
	}

	public void setNtrLen(Long ntrLen) {
		this.ntrLen = ntrLen;
	}

	public Long getPntLen() {
		return pntLen;
	}

	public void setPntLen(Long pntLen) {
		this.pntLen = pntLen;
	}

	public Long getPrdLen() {
		return prdLen;
	}

	public void setPrdLen(Long prdLen) {
		this.prdLen = prdLen;
	}

	public Long getPu2Cnt() {
		return pu2Cnt;
	}

	public void setPu2Cnt(Long pu2Cnt) {
		this.pu2Cnt = pu2Cnt;
	}

	public Long getPu3Cnt() {
		return pu3Cnt;
	}

	public void setPu3Cnt(Long pu3Cnt) {
		this.pu3Cnt = pu3Cnt;
	}

	public Long getPu4Cnt() {
		return pu4Cnt;
	}

	public void setPu4Cnt(Long pu4Cnt) {
		this.pu4Cnt = pu4Cnt;
	}

	public Long getPu5Cnt() {
		return pu5Cnt;
	}

	public void setPu5Cnt(Long pu5Cnt) {
		this.pu5Cnt = pu5Cnt;
	}

	public Long getPuaCnt() {
		return puaCnt;
	}

	public void setPuaCnt(Long puaCnt) {
		this.puaCnt = puaCnt;
	}

	public Long getPu2Len() {
		return pu2Len;
	}

	public void setPu2Len(Long pu2Len) {
		this.pu2Len = pu2Len;
	}

	public Long getPu3Len() {
		return pu3Len;
	}

	public void setPu3Len(Long pu3Len) {
		this.pu3Len = pu3Len;
	}

	public Long getPu4Len() {
		return pu4Len;
	}

	public void setPu4Len(Long pu4Len) {
		this.pu4Len = pu4Len;
	}

	public Long getPu5Len() {
		return pu5Len;
	}

	public void setPu5Len(Long pu5Len) {
		this.pu5Len = pu5Len;
	}

	public Long getPuaLen() {
		return puaLen;
	}

	public void setPuaLen(Long puaLen) {
		this.puaLen = puaLen;
	}

	public Long getPukCnt() {
		return pukCnt;
	}

	public void setPukCnt(Long pukCnt) {
		this.pukCnt = pukCnt;
	}

	public Long getPucCnt() {
		return pucCnt;
	}

	public void setPucCnt(Long pucCnt) {
		this.pucCnt = pucCnt;
	}

	public Long getPuhCnt() {
		return puhCnt;
	}

	public void setPuhCnt(Long puhCnt) {
		this.puhCnt = puhCnt;
	}

	public Long getPurCnt() {
		return purCnt;
	}

	public void setPurCnt(Long purCnt) {
		this.purCnt = purCnt;
	}

	public Long getPujCnt() {
		return pujCnt;
	}

	public void setPujCnt(Long pujCnt) {
		this.pujCnt = pujCnt;
	}

	public Long getPukLen() {
		return pukLen;
	}

	public void setPukLen(Long pukLen) {
		this.pukLen = pukLen;
	}

	public Long getPucLen() {
		return pucLen;
	}

	public void setPucLen(Long pucLen) {
		this.pucLen = pucLen;
	}

	public Long getPuhLen() {
		return puhLen;
	}

	public void setPuhLen(Long puhLen) {
		this.puhLen = puhLen;
	}

	public Long getPurLen() {
		return purLen;
	}

	public void setPurLen(Long purLen) {
		this.purLen = purLen;
	}

	public Long getPujLen() {
		return pujLen;
	}

	public void setPujLen(Long pujLen) {
		this.pujLen = pujLen;
	}

	public Long getNprLen() {
		return nprLen;
	}

	public void setNprLen(Long nprLen) {
		this.nprLen = nprLen;
	}

	public Long getNtdLen() {
		return ntdLen;
	}

	public void setNtdLen(Long ntdLen) {
		this.ntdLen = ntdLen;
	}

	public Long getWidAll() {
		return widAll;
	}

	public void setWidAll(Long widAll) {
		this.widAll = widAll;
	}

	public Long getWidRod() {
		return widRod;
	}

	public void setWidRod(Long widRod) {
		this.widRod = widRod;
	}

	public Long getWidCen() {
		return widCen;
	}

	public void setWidCen(Long widCen) {
		this.widCen = widCen;
	}

	public Long getWidGil() {
		return widGil;
	}

	public void setWidGil(Long widGil) {
		this.widGil = widGil;
	}

	public Long getPhcAll() {
		return phcAll;
	}

	public void setPhcAll(Long phcAll) {
		this.phcAll = phcAll;
	}

	public Long getPhcPhg() {
		return phcPhg;
	}

	public void setPhcPhg(Long phcPhg) {
		this.phcPhg = phcPhg;
	}

	public Long getPhcSub() {
		return phcSub;
	}

	public void setPhcSub(Long phcSub) {
		this.phcSub = phcSub;
	}

	public Long getFrdLen() {
		return frdLen;
	}

	public void setFrdLen(Long frdLen) {
		this.frdLen = frdLen;
	}

	public Long getSrdLen() {
		return srdLen;
	}

	public void setSrdLen(Long srdLen) {
		this.srdLen = srdLen;
	}

	public Long getTrdLen() {
		return trdLen;
	}

	public void setTrdLen(Long trdLen) {
		this.trdLen = trdLen;
	}

	public Long getUrdLen() {
		return urdLen;
	}

	public void setUrdLen(Long urdLen) {
		this.urdLen = urdLen;
	}

	public Long getRduLen() {
		return rduLen;
	}

	public void setRduLen(Long rduLen) {
		this.rduLen = rduLen;
	}

	public Long getRddLen() {
		return rddLen;
	}

	public void setRddLen(Long rddLen) {
		this.rddLen = rddLen;
	}

	public Long getAsuLen() {
		return asuLen;
	}

	public void setAsuLen(Long asuLen) {
		this.asuLen = asuLen;
	}

	public Long getAsdLen() {
		return asdLen;
	}

	public void setAsdLen(Long asdLen) {
		this.asdLen = asdLen;
	}

	public Long getCnuLen() {
		return cnuLen;
	}

	public void setCnuLen(Long cnuLen) {
		this.cnuLen = cnuLen;
	}

	public Long getCndLen() {
		return cndLen;
	}

	public void setCndLen(Long cndLen) {
		this.cndLen = cndLen;
	}

	public Long getNpuLen() {
		return npuLen;
	}

	public void setNpuLen(Long npuLen) {
		this.npuLen = npuLen;
	}

	public Long getNpdLen() {
		return npdLen;
	}

	public void setNpdLen(Long npdLen) {
		this.npdLen = npdLen;
	}

	public Long getPmyLft() {
		return pmyLft;
	}

	public void setPmyLft(Long pmyLft) {
		this.pmyLft = pmyLft;
	}

	public Long getPmyRit() {
		return pmyRit;
	}

	public void setPmyRit(Long pmyRit) {
		this.pmyRit = pmyRit;
	}

	public Long getPmnLft() {
		return pmnLft;
	}

	public void setPmnLft(Long pmnLft) {
		this.pmnLft = pmnLft;
	}

	public Long getPmnRit() {
		return pmnRit;
	}

	public void setPmnRit(Long pmnRit) {
		this.pmnRit = pmnRit;
	}

	public Long getBycLit() {
		return bycLit;
	}

	public void setBycLit(Long bycLit) {
		this.bycLit = bycLit;
	}

	public Long getBycRit() {
		return bycRit;
	}

	public void setBycRit(Long bycRit) {
		this.bycRit = bycRit;
	}

	public Long getAreAll() {
		return areAll;
	}

	public void setAreAll(Long areAll) {
		this.areAll = areAll;
	}

	public Long getAreNat() {
		return areNat;
	}

	public void setAreNat(Long areNat) {
		this.areNat = areNat;
	}

	public Long getAreLoc() {
		return areLoc;
	}

	public void setAreLoc(Long areLoc) {
		this.areLoc = areLoc;
	}

	public Long getArePri() {
		return arePri;
	}

	public void setArePri(Long arePri) {
		this.arePri = arePri;
	}

	public Long getRadF10() {
		return radF10;
	}

	public void setRadF10(Long radF10) {
		this.radF10 = radF10;
	}

	public Long getRadF20() {
		return radF20;
	}

	public void setRadF20(Long radF20) {
		this.radF20 = radF20;
	}

	public Long getRadF30() {
		return radF30;
	}

	public void setRadF30(Long radF30) {
		this.radF30 = radF30;
	}

	public Long getRadF46() {
		return radF46;
	}

	public void setRadF46(Long radF46) {
		this.radF46 = radF46;
	}

	public Long getRadF70() {
		return radF70;
	}

	public void setRadF70(Long radF70) {
		this.radF70 = radF70;
	}

	public Long getRadU70() {
		return radU70;
	}

	public void setRadU70(Long radU70) {
		this.radU70 = radU70;
	}

	public Long getRtrObg() {
		return rtrObg;
	}

	public void setRtrObg(Long rtrObg) {
		this.rtrObg = rtrObg;
	}

	public Long getRtrSub() {
		return rtrSub;
	}

	public void setRtrSub(Long rtrSub) {
		this.rtrSub = rtrSub;
	}

	public Long getRtrCgu() {
		return rtrCgu;
	}

	public void setRtrCgu(Long rtrCgu) {
		this.rtrCgu = rtrCgu;
	}

	public Long getRtrCga() {
		return rtrCga;
	}

	public void setRtrCga(Long rtrCga) {
		this.rtrCga = rtrCga;
	}

	public Long getRtrR2d() {
		return rtrR2d;
	}

	public void setRtrR2d(Long rtrR2d) {
		this.rtrR2d = rtrR2d;
	}

	public Long getRtrR3d() {
		return rtrR3d;
	}

	public void setRtrR3d(Long rtrR3d) {
		this.rtrR3d = rtrR3d;
	}

	public Long getFgbCnt() {
		return fgbCnt;
	}

	public void setFgbCnt(Long fgbCnt) {
		this.fgbCnt = fgbCnt;
	}

	public Long getFgbLen() {
		return fgbLen;
	}

	public void setFgbLen(Long fgbLen) {
		this.fgbLen = fgbLen;
	}

	public Long getSgbCnt() {
		return sgbCnt;
	}

	public void setSgbCnt(Long sgbCnt) {
		this.sgbCnt = sgbCnt;
	}

	public Long getSgbLen() {
		return sgbLen;
	}

	public void setSgbLen(Long sgbLen) {
		this.sgbLen = sgbLen;
	}

	public Long getTgbCnt() {
		return tgbCnt;
	}

	public void setTgbCnt(Long tgbCnt) {
		this.tgbCnt = tgbCnt;
	}

	public Long getTgbLen() {
		return tgbLen;
	}

	public void setTgbLen(Long tgbLen) {
		this.tgbLen = tgbLen;
	}

	public Long getUgbCnt() {
		return ugbCnt;
	}

	public void setUgbCnt(Long ugbCnt) {
		this.ugbCnt = ugbCnt;
	}

	public Long getUgbLen() {
		return ugbLen;
	}

	public void setUgbLen(Long ugbLen) {
		this.ugbLen = ugbLen;
	}

	public String getTolMan() {
		return tolMan;
	}

	public void setTolMan(String tolMan) {
		this.tolMan = tolMan;
	}

	public String getTosSta() {
		return tosSta;
	}

	public void setTosSta(String tosSta) {
		this.tosSta = tosSta;
	}

	public String getTodEnd() {
		return todEnd;
	}

	public void setTodEnd(String todEnd) {
		this.todEnd = todEnd;
	}

	public Long getTolCnt() {
		return tolCnt;
	}

	public void setTolCnt(Long tolCnt) {
		this.tolCnt = tolCnt;
	}

	public String getTolRec() {
		return tolRec;
	}

	public void setTolRec(String tolRec) {
		this.tolRec = tolRec;
	}

	public Long getToaLen() {
		return toaLen;
	}

	public void setToaLen(Long toaLen) {
		this.toaLen = toaLen;
	}

	public Long getTorLen() {
		return torLen;
	}

	public void setTorLen(Long torLen) {
		this.torLen = torLen;
	}

	public Long getTotCnt() {
		return totCnt;
	}

	public void setTotCnt(Long totCnt) {
		this.totCnt = totCnt;
	}

	public Long getTotLen() {
		return totLen;
	}

	public void setTotLen(Long totLen) {
		this.totLen = totLen;
	}

	public Long getTobCnt() {
		return tobCnt;
	}

	public void setTobCnt(Long tobCnt) {
		this.tobCnt = tobCnt;
	}

	public Long getTobLen() {
		return tobLen;
	}

	public void setTobLen(Long tobLen) {
		this.tobLen = tobLen;
	}

	public String getRmkExp() {
		return rmkExp;
	}

	public void setRmkExp(String rmkExp) {
		this.rmkExp = rmkExp;
	}

}