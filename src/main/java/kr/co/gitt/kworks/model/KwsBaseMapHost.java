package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsBaseMapHost
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsBaseMapHost.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 2. 1. 오전 11:18:43 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 기본 지도 호스트 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsBaseMapHost {

	/// 호스트 번호
	private Long hostNo;
	
	/// 지도 번호
	private Long mapNo;
	
	/// 포트
	private Long port;
	
	/// 주소
	private String url;

	public Long getHostNo() {
		return hostNo;
	}

	public void setHostNo(Long hostNo) {
		this.hostNo = hostNo;
	}

	public Long getMapNo() {
		return mapNo;
	}

	public void setMapNo(Long mapNo) {
		this.mapNo = mapNo;
	}

	public Long getPort() {
		return port;
	}

	public void setPort(Long port) {
		this.port = port;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
