var COMMON = {
		
	/// 출력 프로그램 업데이트 시퀀스
	OUTPUT_UPDATE_SEQUENCE : 59,
		
	/// 공간 PK 컬럼 아이디 (Lower CamelCase)
	PK : "objectid",
		
	/// 지번 테이블 명
	LAND : "LP_PA_CBND",
	
	/// 소유자별 통계 연속지적 테이블 명
	STATS : "LP_PA_CBND_STATS",
	
	/// 도로명건물 테이블 명
	BULD : "TL_SPBD_BULD",
	
	/// 도로명 목록 (퀵 메뉴 도로명에서 사용)
	RNS : ["TL_SPBD_BULD", "TL_SPRD_MANAGE"],
	
	/// 동 테이블 명
	DONG : "BML_BADM_AS",
	
	/// 동 테이블에 동 코드 컬럼명
	DONG_CODE_FIELD : "BJD_CDE",
	
	/// 동 테이블에 동명 컬럼명
	DONG_NAME_FIELD : "BJD_NAM",
	
	/// 행정동 테이블 명
	HDONG : '',
	
	/// 행정동 테이블에 동 코드 컬럼명
	HDONG_CODE_FIELD : '',
	
	///  1/1000색인도 테이블명
	INDEX1000 : '',
	
	/// 1/1000색인도 테이블 도엽번호 필드명
	INDEX1000_NUM_FIELD : '',
	
	/// 도로명 테이블 명
	ROAD : "RDT_RNMG_DT",
	
	/// 도로구간 테이블 명
	ROAD_ROUTE : "TL_SPRD_MANAGE",
	
	/// 출력영역 미리보기 레이어
	PRINT_AREA_PREVIEW_LAYER : "BML_BADM_AS,TL_SPRD_RW",
	
	/// 심볼 목록
	SYMBOLS : [
   	    "AA002.png", "AAB001.png", "AD010.png", "AD020.png", "AD030.png", "AE131.png", "AE132.png", "AE133.png",
	    "AE160.png", "AE200.png", "AE210.png", "AE211.png", "AE260.png", "AE310.png", "AE325.png", "AE330.png",
	    "AE360.png", "AE542.png", "AE640.png", "AE911.png", "AE952.png", "AEE000.png", "AEE001.png", "AEE002.png",
	    "AEE003.png", "AEE004.png", "AEE010.png", "AEE999.png", "AET000.png", "AET008.png", "AET009.png", 
	    "AET010.png", "AET011.png", "AET012.png", "AET013.png", "AET999.png", "AF030.png", "AI010.png", "AZ901.png",
	    "AZ902.png", "AZ903.png", "AZ904.png", "AZ936.png", "BB199.png", "BB991.png", "CA002.png", "GRD02.png", 
	    "GRD04.png", "GRD14.png", "GT000.png", "GT001.png", "GT002.png", "GT003.png", "GT004.png", "GT005.png", 
	    "GT006.png", "GT007.png", "GT008.png", "GT009.png", "GT010.png", "GT011.png", "GT012.png", "GT013.png", 
	    "GT014.png", "GT015.png", "GT016.png", "GT017.png", "GT018.png", "GT019.png", "GT020.png", "GT022.png", 
	    "GT023.png", "GT911.png", "GT912.png", "GT921.png", "GT922.png", "GT923.png", "GT924.png", "GT925.png", 
	    "GT926.png", "GT999.png", "IT002.png", "IT005.png", "SA003.png", "SA100.png", "SA110.png", "SA112.png", 
	    "SA114.png", "SA117.png", "SA118.png", "SA119.png", "SA120.png", "SA121.png", "SA122.png", "SA200.png", 
	    "SA201.png", "SA202.png", "SA203.png", "SA204.png", "SA205.png", "SA206.png", "SA209.png", "SA300.png", 
	    "SA900.png", "SA902.png", "SB100.png", "SB101.png", "SB102.png", "SB103.png", "SB104.png", "SB200.png", 
	    "SB210.png", "SB220.png", "SB410.png", "SB900.png", "SB907.png", "SC110.png", "SC210.png", "SC212.png", 
	    "SC233.png", "SC242.png", "SC510.png", "SC540.png", "SC900.png", "SD100.png", "SD300.png", "SD900.png", 
	    "SD991.png", "SD993.png", "SE100.png", "SE111.png", "SE112.png", "SE114.png", "SE170.png", "SE900.png", 
	    "SF110.png", "SF200.png", "SF300.png", "SF400.png", "SF600.png", "SF900.png", "SG010.png", "SG040.png", 
	    "SG060.png", "SG100.png", "SG101.png", "SG900.png", "XX01.png", "XX02.png", "XX03.png", "ZA001.png", 
	    "ZA002.png", "ZA003.png", "ZA010.png", "ZA011.png", "ZA020.png", "ZA090.png", "ZA099.png", "ZE360.png"
	]
		
};

var MAP_TOOLS = [
    {
    	id : "bass",
    	title : "기본",
    	type : "gisbutton",
    	width : 31
    },
    {
    	id : "expansion",
    	title : "확대",
    	type : "gisbutton",
    	width : 31
    },
    {
    	id : "reduction",
    	title : "축소",
    	type : "gisbutton",
    	width : 31
    },
    {
    	id : "line_1",
    	type : "line",
    	width : 10
    },
	{
    	id : "eraser",
    	title : "화면정리",
    	type : "button"
	},
    {
    	id : "line_2",
    	type : "line",
    	width : 10
    },
	{
    	id : "magnifier",
    	title : "돋보기",
    	type : "button"
	},
	{
    	id : "xray",
    	title : "엑스레이",
    	type : "button"
	},
	{
		id : "measurement",
    	title : "측정",
    	type : "button"
	},
	{
		id : "draw",
    	title : "그리기",
    	type : "button"
	},
    {
    	id : "line_3",
    	type : "line",
    	width : 10
    },
	{
		id : "spatial_info",
    	title : "공간확인",
    	type : "gisbutton",
    	width : 31
	},
	{
		id : "spatial_search",
    	title : "공간검색",
    	type : "button"
	},
    {
    	id : "line_4",
    	type : "line",
    	width : 10
    },
//	{
//		id : "multi",
//    	title : "다분면",
//    	type : "button",
//    	width : 31
//	},
	{
		id : "roadview",
    	title : "로드뷰",
    	type : "button"
	},
	{
		id : "moveAerialMap",
		title : "항공사진 위치조절",
		type : "button"
	}
];

var QUICK_TOOLS = [ 
    {
		id : "lot_number",
		title : "지번",
		type : "button"
	}, {
		id : "road_name",
		title : "도로명",
		type : "button"
	},
	{
		id : "kras",
		title : "토지/건물",
		type : "gisbutton"
	},
//	{
//		id : "landConsInfo",
//		title : "토지공사대장",
//		type : "gisbutton"
//	}
];

///방향성 전환 시 swap 필드 정의
var FIELD_CONVERSION = {};
