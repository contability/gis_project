/**
 * 고급 출력 객체
 * @type {Object}
 */
var hghnkOutputObj = {
	
	/**
	 * 고급출력 서비스 주소
	 * @type {String}
	 */
	url : "http://127.0.0.1:8990/KMService?",
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "고급출력",
	
	/**
	 * 고급 출력 요청 파라미터
	 * @type {Object}
	 */
	defaultParams : {
		/**
		 * 서비스 명
		 * @type {String}
		 */
		SERVICE : "PRN",
		
		/**
		 * 버전
		 * @type {String}
		 */
		VERSION : "1.0.0",
		
		/**
		 * 업데이트 시퀀스
		 * @type {Number}
		 */
		UPDATE_SEQUENCE : COMMON.OUTPUT_UPDATE_SEQUENCE
	},
	
	/**
	 * 프로미스 객체
	 * @type {Object}
	 */
	promise : null,
	
	/**
	 * 고급 출력 정보
	 * @type {Object}
	 */
	data : null,
	
	/**
	 * 초기화 
	 */
	init : function() {
		var that = this;
		that.getCapabilities(false);
	},
	
	/**
	 * 프로미스 객체 반환
	 * @return {Object} 프로미스 객체
	 */
	getPromise : function() {
		var that = this;
		return that.promise;
	},
	
	/**
	 * 고급 출력 정보 반환
	 */
	getData : function() {
		var that = this;
		return that.data;
	},
	
	/**
	 * 고급 출력 정보 호출
	 */
	getCapabilities : function(isDown) {
		var that = this;
		var deferred = $.Deferred();
		
		var params = $.extend({
			REQUEST : "GetCapabilities",
			nocache : Math.random()
		}, that.defaultParams);
		
		var url = that.url + $.param(params);
		browserUtils.ajaxCallXmlType(url).done(function(data) {
			that.parseCapabilities(data);
			if(that.checkVersion(isDown)) {
				deferred.resolve();
			}
		}).fail(function() {
			if(isDown) {
				messagerUtils.alert(that.TITLE, "고급출력 프로그램을 다운로드합니다. 고급 출력을 사용하기 위해 설치가 필요합니다.");
				that.download();
			}
		});
		that.promise = deferred.promise();
	},
	
	/**
	 * 고급 출력 정보 요청 결과 분석
	 * @param {XML} res 고급 출력 정보 요청 결과 XML
	 */
	parseCapabilities : function(res) {
		var that = this;
		that.data = {
			version : null,
			printers : {},
			defaultPrint : null,
			layouts : {}
		};
		
		var capabilities = $(res).find("> PRN_Capabilities");
		that.data.version = capabilities.attr("version");
		that.data.updateSequence = parseInt(capabilities.attr("updateSequence"));
		
		var capability = capabilities.find("> Capability");
		
		var printers = capability.find("> Printers > Printer");
		printers.each(function() {
			var printer = $(this);
			var printerName = printer.find("> Name").text();
			that.data.printers[printerName] = {
				papers : []
			};
			
			var isDefault = printer.attr("default");
			if(isDefault) {
				that.data.defaultPrint = printerName;
			}
			
			var papers = printer.find("> Papers > Paper");
			papers.each(function() {
				var paper = $(this);
				that.data.printers[printerName].papers.push(paper.attr("name"));
			});
		});
		
		var layouts = capability.find("> Layouts > Layout");
		layouts.each(function() {
			var layout = $(this);
			that.data.layouts[layout.attr("id")] = {
				name : layout.attr("name"),
				type : layout.attr("type")
			};
		});
	},
	
	/**
	 * 고급 출력 프로그램 버전 확인
	 */
	checkVersion : function(isDown) {
		var that = this;
//		if(that.defaultParams.VERSION != that.data.version || that.defaultParams.UPDATE_SEQUENCE != that.data.updateSequence) {
		if(that.defaultParams.VERSION < that.data.version || that.defaultParams.UPDATE_SEQUENCE > that.data.updateSequence) {
			if(isDown) {
				that.stopService();
			}
			return false;
		}
		return true;
	},

	/**
	 * 고급 출력 프로그램 종료
	 */
	stopService : function() {
		var that = this;
		
		var params = $.extend({
			REQUEST : "StopService",
			nocache : Math.random()
		}, that.defaultParams);
		
		var url = that.url + $.param(params);
		browserUtils.ajaxCallXmlType(url).done(function() {
			messagerUtils.alert(that.TITLE, "상위 고급출력 프로그램이 있습니다. 다운로드 합니다.");
			that.download();
		}).fail(function() {
			messagerUtils.alert(that.TITLE, "고급출력 프로그램을 중지할 수 없습니다.");
		});
	},

	/**
	 * 고급 출력 프로그램 다운로드
	 */
	download : function() {
		location.href = CONTEXT_PATH+"/print/PrintServiceSetup.exe";
	},
	
	/**
	 * 레이아웃 반환
	 * @param {Long} layoutId 레이아웃 아이디
	 * @returns {Object} 레이아웃 객체
	 */
	getLayout : function(layoutId) {
		var that = this;
		return that.data.layouts[layoutId];
	},
	
	/**
	 * 레이아웃 이미지 주소 반환
	 * @param {Long} layoutId 레이아웃 아이디
	 * @returns {String} 레이아웃 이미지 주소
	 */
	getLayoutImgSrc : function(layoutId) {
		var that = this;
		var params = $.extend({
			REQUEST : "GetLayout",
			LAYOUT : that.data.layouts[layoutId].name,
			FORMAT : "image/png",
			WIDTH : 101,
			HEIGHT : 88,
			nocache : Math.random()
		}, that.defaultParams);
		return that.url + $.param(params);
	},
	
	/**
	 * 고급출력 실행
	 * @param {Object} params 파라미터 목록
	 * @returns {Object} 프로미스 객체
	 */
	execute : function(params) {
		var that = this;
		var deferred = $.Deferred();
		var url = that.url+$.param($.extend(params, that.defaultParams, { nocache : Math.random() }));
		browserUtils.ajaxCallXmlType(url).done(function(result) {
			deferred.resolve(result);
		}).fail(function() {
			deferred.fail();
		});
		return deferred.promise();
	}
		
};