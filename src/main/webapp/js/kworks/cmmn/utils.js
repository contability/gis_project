// ie patch
var console = window.console || {log:function(){}, debug:function(){}};
// ie9 patch
if(!CanvasRenderingContext2D.prototype.setLineDash) {
	CanvasRenderingContext2D.prototype.setLineDash = function() {};
}

/*
 * jquery deferred when all
 */
if (typeof jQuery.when.all === 'undefined') {
    jQuery.when.all = function (deferreds) {
        return $.Deferred(function (def) {
            $.when.apply(jQuery, deferreds).then(
                function () {
                    def.resolveWith(this, [Array.prototype.slice.call(arguments)]);
                },
                function () {
                    def.rejectWith(this, [Array.prototype.slice.call(arguments)]);
                });
        });
    };
}

/**
 * easyui custom
 */
if($.fn && $.fn.validatebox) {
	
	/**
	 * 필수 입력 메세지 변경
	 */
	$.extend($.fn.validatebox.defaults, {
		missingMessage : "필수 항목 입니다."
	});
	$.extend($.fn.combobox.defaults, {
		missingMessage : "필수 항목 입니다."
	});
	$.extend($.fn.textbox.defaults, {
		missingMessage : "필수 항목 입니다."
	});
	$.extend($.fn.numberspinner.defaults, {
		missingMessage : "필수 항목 입니다."
	});
	$.extend($.fn.datebox.defaults, {
		missingMessage : "필수 항목 입니다."
	});

	/**
	 * 검증 정의
	 */
	$.extend($.fn.validatebox.defaults.rules, {
		// 정수 최소값 체크
		min : {
			validator : function(value, minValue) {			
				return parseInt(value) >= minValue;
			},
			message : "{0} 이상의 정수값을 입력하여 주십시오."
		},
		// 정수 최대값 체크
		max : {
			validator : function(value, maxValue) {			
				return parseInt(value) <= maxValue;
			},
			message : "{0} 이하의 정수값을 입력하여 주십시오."
		},
		// 최대 입력 길이 체크
		maxLength : {
			validator : function(value, maxLength) {
				var len = $.trim(value).length;
				return len <= maxLength;
			},
			message : "{0} 이하 길이의 값을 입력하여 주십시오."
		},
		// 입력 길이 체크 (최소, 최대 체크)
		length : {
			validator : function(value, between) {
				var len = $.trim(value).length;
				return len >= between[0] && len <= between[1];
			},
			message : "{0} 과 {1} 사이 길이의 값을 입력하여 주십시오."
		},
		// 범위 값 체크 (최소, 최대 체크)
		between : {
			validator : function(value, between) {
				return value >= between[0] && value <= between[1];
			},
			message : "{0} 과 {1} 사이 값을 입력하여 주십시오."
		},
		// 양수 체크 (0 이상의 수)
		positive : {
			validator : function(value) {
				var regExp = /^([+]?)(\d+)$/;
				return regExp.test(value);
			},
			message : "양수 값을 입력하여 주십시오."
		},
		// 정수 체크
		integer : {
			validator : function(value) {
				var regExp = /^([+-]?)(\d+)$/;
				return regExp.test(value);
			},
			message : "정수 값을 입력하여 주십시오."
		},
		// 숫자형 체크
		number : {
			validator : function(value) {
				var regExp = /^([+-]?)(?=\d|\.\d)\d*(\.\d*)?([Ee]([+-]?\d+))?$/;
				return regExp.test(value);
			},
			message : "숫자형의 값을 입력하여 주십시오."
		},
		// 한글과 숫자만 입력 허용
		koreaNumber : {
			validator : function(value) {
				var reg = /^[ㄱ-ㅎ|ㅏ-ㅣ|가-힝|0-9]*$/;
				return reg.test(value);
			},
			message : "한글과 숫자만 입력하여 주십시오."
		},
		// 한글과 숫자, 공백만 입력 허용
		koreaNumberSpace : {
			validator : function(value) {
				var reg = /^[ㄱ-ㅎ|ㅏ-ㅣ|가-힝|0-9|\s]*$/;
				return reg.test(value);
			},
			message : "한글과 숫자, 공백만 입력하여 주십시오."
		}
	});

	/**
	 * 날짜 박스 포맷 (날짜 객체를 문자열로 변환)
	 */
	$.fn.datebox.defaults.formatter = function(date){
		var y = stringUtils.lpad(date.getFullYear().toString(), 4);
		var m = stringUtils.lpad((date.getMonth()+1).toString(), 2);
		var d = stringUtils.lpad(date.getDate().toString(), 2);
		return y + m + d;
	};

	/**
	 * 날짜 박스 파서 (문자열을 날짜 객체로 변환)
	 */
	$.fn.datebox.defaults.parser = function(s){
		if(s.length == 8) {
			var y = s.substr(0, 4);
			var m = s.substr(4, 2)-1;
			var d = s.substr(6, 2);
			var t = new Date(y, m, d);
			return t;
		}
		else {
			return new Date();
		}
	};
}

/**
 * 브라우저 유틸
 * @type {Object}
 */
var browserUtils = {
	
	/**
	 * IE 체크
	 * @returns {Boolean} IE 여부 반환
	 */
	checkIe : function() {
		var agent = navigator.userAgent.toLowerCase();
		if ((agent.search("rv") != -1 && 
				agent.search("11.0") != -1 && agent.search("gecko") != -1) // IE 11
				|| (agent.search("msie") != -1 && (agent.search("8.0") != -1 // IE 8
				|| agent.search("9.0") != -1 // IE 9
				|| agent.search("10.0") != -1 // IE 10
				|| agent.search("10.6") != -1) // IE 10.6	
			)) {
			return true;
		}
		else {
			return false;
		}
	},
	
	ajaxCallXmlType : function(url) {
		var deferred = $.Deferred();
		if ('XDomainRequest' in window && window.XDomainRequest !== null) {				 
			var xdr = new XDomainRequest();
	        xdr.open('get', url);
	        xdr.onload = function () {
	        	var data = $.parseXML(this.responseText);
	        	deferred.resolve(data);
	        };
	        xdr.onerror = function() {
	        	deferred.reject();
	        };
	        xdr.send();
		}
		else {
			$.get(url,function(data){deferred.resolve(data);},"xml").fail(function() {
				deferred.reject();
			});
		}
		return deferred.promise();
	}
	
};

/**
 * Jquery 유틸
 * @type {Object}
 */
var jqueryUtils = {
		
	/// 로딩 바 표시 여부
	isLoading : true,
	
	/**
	 * 로딩바 표시 여부
	 * @param isLoading 표시 여부
	 */
	setIsLoding : function(isLoading) {
		var that = this;
		that.isLoading = isLoading;
	},
	
	/**
	 * ajax 호출 설정
	 */
	configAjax : function() {
		var that = this;
		
		// 전통적인 방식으로 ajax 호출 { id[] : 1, id[] : 2...}
		$.ajaxSettings.traditional = true;
		 
		// ajax 호출 시 로딩 바 생성하고 호출 완료 시 로딩 바 삭제
		$(document).ajaxStart(function() {
			if(that.isLoading) {
				$("#div_loading").show();
			}
		}).ajaxComplete(function() {
			if(that.isLoading) {
				$("#div_loading").hide();
			}
		}).ajaxError(function() {
		}).ajaxStop(function() {
		}).ajaxSuccess(function(event, xhr, options, data) {
			that.checkAuthor(data);
		}); 
	 },
	 
	 checkAuthor : function(data) {
		 $(data).each(function() {
			var element = $(this);
			if($(element).hasClass("hid_message_code")) {
				var messageCode = $(element).val();
				
				if(messageCode == "MSG001") {
					location.href = CONTEXT_PATH + "/j_spring_security_logout";
				}
				else if(messageCode == "MSG002") {
					alert(data);
					$.messager.alert("접근거절", "접근 권한이 없습니다. 포털페이지로 이동합니다.", null, function() {
						location.href = CONTEXT_PATH + "/portal.do";
					});
				}
				else if(messageCode == "MSG003") {
					location.href = CONTEXT_PATH + "/concurrentExpired.do";
				}
				else {
					// ajax 요청의 결과로 login 페이지가 오는 경우 로그아웃 된 상태로 판별함.
					location.href = CONTEXT_PATH + "/logout.do";
				}
			} 
		 });
	 }

};

/**
 * 문자열 유틸
 * @type {Object}
 */
var stringUtils = {
	
	/**
	 * 지정된 길이만큼 좌측에 특정문자열 채움
	 * @param {String} str 문자열
	 * @param {Number} len 길이
	 * @param {String} ch 채울 문자
	 * @return {String} 문자열
	 */
	lpad : function(str, len, ch) {
		var retVal = '';
		for(var i=str.length; i < len; i++) {
			if(ch) retVal += ch;
			else retVal += "0";
		}
		retVal += str;
		return retVal;
	},
	
	/**
	 * 지정된 길이만큼 우측에 특정문자열 채움
	 * @param {String} str 문자열
	 * @param {Number} len 길이
	 * @param {String} ch 채울 문자
	 * @return {String} 문자열
	 */
	rpad : function(str, len, ch) {
		var retVal = '';
		retVal += str;
		for(var i=str.length; i < len; i++) {
			if(ch) retVal += ch;
			else retVal += "0";
		}
		return retVal;
	},
	
	unescapeHtml : function(text) {
		if (text == null) {
			return '';
		}
		
		return text
		.replace(/&amp;/g, '&')
		.replace(/&lt;/g, '<')
		.replace(/&gt;/g, '>')
		.replace(/&quot;/g, '"')
		.replace(/&#039;/g, "'")
		.replace(/&#39;/g, "'");
	}
		
};

/**
 * 날짜 유틸
 * @type {Object}
 */
var dateUtils = {
	
	/**
	 * 날짜/시간 문자열 표시
	 * @param {Date} date 날짜 객체
	 * @returns {String} 날짜/시간 문자열
	 */
	toDateTime : function(date) {
		var that = this;
		var h = stringUtils.lpad(date.getHours().toString(), 2);
		var m = stringUtils.lpad(date.getMinutes().toString(), 2);
		var s = stringUtils.lpad(date.getSeconds().toString(), 2);
		return that.toDate(date) + " " + h + ":" + m + ":" + s;
	},
	
	/**
	 * 날짜 표시
	 * @param {Date} date 날짜 객체
	 * @param {String} ch 연결 문자
	 * @returns {String} 날짜 문자열
	 */
	toDate : function(date, ch) {
		if(ch == null) {
			ch = "-";
		}
		var y = stringUtils.lpad(date.getFullYear().toString(), 4);
		var m = stringUtils.lpad((date.getMonth()+1).toString(), 2);
		var d = stringUtils.lpad(date.getDate().toString(), 2);
		return y + ch + m + ch + d;
	}
		
};


/**
 * Rest 유틸
 * @type {Object}
 */
var restUtils = {
		
	/**
	 * Rest Url 반환
	 */
	getResUrl : function() {
		var offset = location.href.lastIndexOf("/");
		var restUrl = location.href.substring(0, offset+1);
		return restUrl;
	},
	
	search : function(pageIndex) {
		var that = this;
		if(pageIndex) {
			$("#pageIndex").val(pageIndex);
		}
		$("form", that.selector).submit();
	}

};

/**
 * Popup 유틸
 * @type {Object}
 */
var popupUtils = {
	
	/**
	 * 열기
	 * @params {String} url URL
	 * @params {String} name 이름
	 * @params {Object} options 옵션
	 */
	open : function(url, name, options) {
		//url Check
		if(!url) {
			console.debug("Url(주소)는 필수 입력 대상 입니다.");
			return;
		}
		
		//default 항상 새 창으로
		if(!name) {
			name = "_blank";
		}
		
		//default options
		var optObj = {
		    'width'  : 300,
		    'height' : 300,
		    'scrollbars' : 'yes',
		    'toolbar' : 'no',
		    'menubars' : 'no',
		    'locationbar' : 'no',
		    'statusbar'   : 'no',
		    'resizable'   : 'no',
		    'titlebar'    : 'no',
		    'left'    : 0,
		    'top'     : 0,
		    'message' : '팝업차단을 해제해주세요.'
		};
		
		var opts = $.extend({}, optObj, options);
		var optStr = convertUtils.convertObjToString(opts, ",");
		var popup = window.open(url, name, optStr);
		
		if(!popup) {
			$.messager.alert("팝업", opts.message);
			console.debug(opts.message);
			return false;
		}
		else {
			popup.focus();	
		}
		return popup;
	}

};

/**
 * 변환 유틸
 */
var convertUtils = {
	
	/**
	 * 객체를 문자열로 변환
	 * @param {Object} obj 객체
	 * @param {String} ch 연결 문자
	 */
	convertObjToString : function(obj, ch) {
		var retVal = "";
		
		if(!ch) {
			ch = "&";
		}
		
		for(var i in obj) {
			retVal += ch;
			retVal += i;
			retVal += "=";
			retVal += obj[i];
		}
		
		return retVal.substr(1);
	}
};

var caseUtils = {
	
	getText : function(text) {
		return COM.DEFAULT_CASE == "lower" ? text.toLowerCase() : text.toUpperCase();
	}
};

/**
 * Camelcase 유틸
 * @type {Object}
 */
var camelcaseUtils = {
	
	/**
	 * Camelcase 형태의 문자열을 언더바 형태의 문자열로 변환
	 * @param {String} value 변환할 문자열
	 * @return {String} 변환된 문자열
	 */
	camelcaseToUnderbar : function(value) {
		var result = '';
		if(value) {
			result = value.replace( /([a-z])([A-Z])/g, '$1_$2' ).toUpperCase();
		}
		return result;
	},
	
	/**
	 * 언더바 형태의 문자열을 Camelcase 형태의 문자열로 변환
	 * @param {String} value 변환할 문자열
	 * @return {String} 변환된 문자열
	 */
	underbarToCamelcase : function(value) {
		var result = "";
		if(value) {
			value = value.toLowerCase();
			result = value.replace(/[-_]([a-z])/g, function (g) { return g[1].toUpperCase(); });
		}
		return result;
	}
		
};

/**
 * 메세지 유틸
 * @type {Object}
 */
var messagerUtils = {
	
	/**
	 * 알림
	 * @param {String} title 제목
	 * @param {String} message 내용
	 */
	alert : function(title, message) {
		if($.messager) {
			$.messager.alert(title, message);
		}
		else {
			alert("[" + title + "] " + message);
		}
	}
		
};

/**
 * 이미지 유틸
 * @type {Object}
 */
var imageUtils = {
		
	isOn : function(element) {
		if(element.hasClass("node-selected")) {
			return true;
		}
		else {
			return false;
		}
	},
	
	/**
	 * 이미지 요소
	 * @param {Element} element 이미지 
	 * @returns {Boolean} 선택 여부
	 */
	toggle : function(element) {
		var that = this;
		
		if(element.hasClass("node-selected")) {
			that.off(element);
			return false;
		}
		else {
			that.on(element);
			return true;
		}
	},
	
	/**
	 * 켜기
	 * @param {Element} element 이미지 
	 */
	on : function(element) {
		if(element.length > 0) {
			element.attr("src", $(element).attr("src").replace("_ov", "_on").replace("_off", "_on"));
			if(!element.hasClass("node-selected")) {
				element.addClass("node-selected");
			}
		}
	},
	
	/**
	 * 끄기
	 * @param {Element} element 이미지 
	 */
	off : function(element) {
		if(element.length > 0) {
			element.each(function() {
				var img = $(this);
				img.attr("src", $(img).attr("src").replace("_ov", "_off").replace("_on", "_off"));
				if(img.hasClass("node-selected")) {
					img.removeClass("node-selected");
				}
			});
		}
	}
		
};

/**
 * 페이징 유틸
 * @type {Object}
 */
var pagingUtils = {
	
	/**
	 * 첫 번째 인덱스 반환
	 * @param {Number} pageIndex 현재 페이지 인덱스
	 * @param {Number} pageSize 한 페이지에 나타낼 목록 수
	 * @return 첫 번째 인덱스
	 */
	getFirstIndex : function(pageIndex, pageSize) {
		var firstIndex = (pageIndex - 1) * pageSize;
		return firstIndex;
	},
	
	/**
	 * 두 번째 인덱스 반환
	 * @param {Number} pageIndex 현재 페이지 인덱스
	 * @param {Number} pageSize 한 페이지에 나타낼 목록 수
	 * @return 두 번째 인덱스
	 */
	getLastIndex : function(pageIndex, pageSize) {
		var lastIndex = pageIndex * pageSize;
		return lastIndex;
	}

};

/**
 * 공간 유틸
 * @type {Object}
 */
var spatialUtils = {
		
	toOlGeometry : function(geom) {
		var parser = new jsts.io.GeoJSONParser();
		var format = new ol.format.GeoJSON();
		
		var json = parser.write(geom);
		return format.readGeometry(json);
	},
	
	toJstsGeometry : function(geom) {
		var parser = new jsts.io.GeoJSONParser();
		var format = new ol.format.GeoJSON();
		
		var json = format.writeGeometry(geom);
		return parser.read(json);
	},
		
	/**
	 * 버퍼
	 * @param {ol.geom.Geometry} geometry 공간 정보
	 * @param {Number} distance 버퍼 거리
	 * @return {ol.geom.Geometry} 버퍼된 공간 정보
	 */
	buffer : function(geometry, distance) {
		var that = this;
		if(distance) {
			distance = parseInt(distance);
		}
		
		var geom = geometry.clone();
		if(geom instanceof ol.geom.Circle) {
			var center = geom.getCenter();
			var radius = geom.getRadius();
			geom = new ol.geom.Point(center);
			distance += radius;
		}

		if(distance) {
			var jstsGeometry = that.toJstsGeometry(geom).buffer(distance);
			return that.toOlGeometry(jstsGeometry);
		}
		else {
			return geometry;
		}
	},
	
	/**
	 * 공간 겹침 여부 
	 * @param {ol.geom.Geometry} g1 공간정보
	 * @param {ol.geom.Geometry} g2 공간정보
	 * @returns {Boolean} 공간 겹침 여부
	 */
	intersects : function(g1, g2) {
		var that = this;
		try {
			var jstsGeom1 = that.toJstsGeometry(g1.clone());
			var jstsGeom2 = that.toJstsGeometry(g2.clone());
			return jstsGeom1.intersects(jstsGeom2);
		}
		catch(err) {
			console.log(err.name);
			return null;
		}
	},
	
	/**
	 * 공간 터치 (외곽선만 겹침) 여부
	 * @param {ol.geom.Geometry} g1 공간정보
	 * @param {ol.geom.Geometry} g2 공간정보
	 * @returns {Boolean} 공간 터치 여부
	 */
	touches : function(g1, g2) {
		var that = this;
		try {
			var jstsGeom1 = that.toJstsGeometry(g1.clone());
			var jstsGeom2 = that.toJstsGeometry(g2.clone());
			return jstsGeom1.touches(jstsGeom2);
		}
		catch(err) {
			console.log(err.name);
			return null;
		}
	},
	
	/**
	 * 겹치는 공간 반환
	 * @param {ol.geom.Geometry} g1 공간정보
	 * @param {ol.geom.Geometry} g2 공간정보
	 * @returns {ol.geom.Geometry} 겹침 공간정보
	 */
	intersection : function(g1, g2) {
		var that = this;
		try {
			var jstsGeom1 = that.toJstsGeometry(g1.clone());
			var jstsGeom2 = that.toJstsGeometry(g2.clone());
			var intersectionGeom = jstsGeom1.intersection(jstsGeom2);
			return that.toOlGeometry(intersectionGeom);
		}
		catch(err) {
			console.log(err.name);
			return null;
		}
	},
	
	/**
	 * 공간 차이 반환 (g1 에서 g2와 겹치는 부분을 제외한 나머지 공간 정보)
	 * @param {ol.geom.Geometry} g1 공간정보
	 * @param {ol.geom.Geometry} g2 공간정보
	 * @returns {ol.geom.Geometry} 차이 공간정보
	 */
	difference : function(g1, g2) {
		var that = this;
		try {
			var jstsGeom1 = that.toJstsGeometry(g1.clone());
			var jstsGeom2 = that.toJstsGeometry(g2.clone());
			var differenceGeom = jstsGeom1.difference(jstsGeom2);
			return that.toOlGeometry(differenceGeom);
		}
		catch(err) {
			console.log(err.name);
			return null;
		}
	},
	
	/**
	 * 선 2개 병합
	 * @param {ol.geom.Geometry} g1 공간정보
	 * @param {ol.geom.Geometry} g2 공간정보
	 * @returns {ol.geom.Geometry} 병합된 공간정보
	 */
	mergeLineString : function(g1, g2) {
		var that = this;
		try {
			var mergeCoordinates = null;
			
			var jstsGeom1 = that.toJstsGeometry(g1.clone());
			var jstsGeom2 = that.toJstsGeometry(g2.clone());
			
			var dist1 = jstsGeom1.getStartPoint().distance(jstsGeom2.getStartPoint());
			var dist2 = jstsGeom1.getStartPoint().distance(jstsGeom2.getEndPoint());
			var dist3 = jstsGeom1.getEndPoint().distance(jstsGeom2.getStartPoint());
			var dist4 = jstsGeom1.getEndPoint().distance(jstsGeom2.getEndPoint());
			
			var min = Math.min(dist1, dist2, dist3, dist4);
			if(dist1 == min) {
				if(jstsGeom1.getStartPoint().equals(jstsGeom2.getStartPoint())) {
					jstsGeom1.getCoordinates().shift();
				}
				mergeCoordinates = jstsGeom2.getCoordinates().reverse().concat(jstsGeom1.getCoordinates());
			}
			else if(dist2 == min) {
				if(jstsGeom1.getStartPoint().equals(jstsGeom2.getEndPoint())) {
					jstsGeom1.getCoordinates().shift();
				}
				mergeCoordinates = jstsGeom2.getCoordinates().concat(jstsGeom1.getCoordinates());
			}
			else if(dist3 == min) {
				if(jstsGeom1.getEndPoint().equals(jstsGeom2.getStartPoint())) {
					jstsGeom2.getCoordinates().shift();
				}
				mergeCoordinates = jstsGeom1.getCoordinates().concat(jstsGeom2.getCoordinates());
			}
			else if(dist4 == min) {
				if(jstsGeom1.getEndPoint().equals(jstsGeom2.getEndPoint())) {
					jstsGeom2.getCoordinates().pop();
				}
				mergeCoordinates = jstsGeom1.getCoordinates().concat(jstsGeom2.getCoordinates().reverse());
			}
			else {
				console.log("최소 거리가 없습니다.");
			}
			return that.toOlGeometry(new jsts.geom.GeometryFactory().createLineString(mergeCoordinates));
		}
		catch(err) {
			console.log(err.name);
			return null;
		}
	},
	
	/**
	 * 도형 목록 병합
	 * @param {Array.<ol.Feature>} features 도형 목록
	 * @returns {ol.geom.Geometry} 병환된 공간 정보
	 */
	union : function(features) {
		var that = this;
		var multi = null; 
		for(var i=0, len=features.length; i < len; i++) {
			var feature = features[i];
			var geometry = feature.getGeometry().clone();
			var geom = that.toJstsGeometry(geometry);
			if(i==0) {
				multi = geom;
			}
			else {
				multi = multi.union(geom);
			}
		}
		return that.toOlGeometry(multi);
	},
	
	/**
	 * 좌표 변환
	 * @param {ol.geom.Geometry} geometry 공간 정보
	 * @param {ol.proj.Projection} from 도형의 좌표계 정보
	 * @param {ol.proj.Projection} to 변환할 좌표계 정보
	 * @return {ol.geom.Geometry} 좌표 변환된 geometry 공간 정보
	 */
	transform : function(geometry, from, to) {
		if(from == to) {
			return geometry;
		}
		else {
			return geometry.clone().transform(from, to);
		}
	},
	
	/**
	 * 영역 변환
	 * @param {ol.Extent} extent 영역
	 * @param from 변환 전 좌표계
	 * @param to 변환 후 좌표계
	 * @returns 변환된 영역
	 */
	transformExtent : function(extent, from , to) {
		var that = this;
		if(from == to) {
			return extent;
		}
		else {
			var geometry = ol.geom.Polygon.fromExtent(extent);
			return that.transform(geometry, from, to).getExtent();
		}
	},
	
	/**
	 * 좌표 변환
	 * @param coordinate 좌표
	 * @param from 변환 전 좌표계
	 * @param to 변환 후 좌표계
	 * @returns 변환된 좌표
	 */
	transformCooridate : function(coordinate, from , to) {
		var that = this;
		if(from == to) {
			return coordinate;
		}
		else {
			var geometry = new ol.geom.Point(coordinate);
			return that.transform(geometry, from, to).getCoordinates();
		}
	},
	
	/**
	 * 원형을 점형과 버퍼로 변환
	 * @param feature
	 * @returns {ol.Feature}
	 */
	convertCircleToPoint : function(feature) {
		var circle = feature.getGeometry();
		var point = new ol.geom.Point(circle.getCenter());
		var pointFeature = new ol.Feature();
		pointFeature.setId(feature.getId());
		pointFeature.setProperties($.extend(feature.getProperties(), { circleRadius : circle.getRadius() }));
		pointFeature.setGeometry(point);
		return pointFeature;
	},
	
	/**
	 * 점형과 버퍼를 원형으로 변환
	 * @param feature
	 * @returns {ol.Feature}
	 */
	convertPointToCircle : function(feature) {
		var point = feature.getGeometry();
		var circle = new ol.geom.Circle(point.getCoordinates(), feature.getProperties().circleRadius);
		var circleFeature = new ol.Feature();
		var properties = feature.getProperties();
		delete properties.circleRadius;
		circleFeature.setId(feature.getId());
		circleFeature.setProperties(properties);
		circleFeature.setGeometry(circle);
		return circleFeature;
	},
	
	/**
	 * 다각선에 상월을 생성.
	 * @param {Array} linestring
	 * @param {Array} ceneter
	 * @param radius;
	 * @return {Array<Array>}
	 */
	createUpwidLine : function (linestring, center, radius) {
		if (!linestring)
			return null;
		
		// 상월 호를 생성할 중심점을 기준으로 라인을 분할.
		var splitLineString = this.cutByPoint(linestring, center);
		if (splitLineString == null || splitLineString.length < 2)
			return null;
		
		// 분할된 라인을 반지름 길이 만큼 절단
		var cutDist1 = this.calculateLength(splitLineString[0]) - radius;
		var cutLine1 = null;
		if (cutDist1 > 0)
			cutLine1 = this.cutByDistance(splitLineString[0], cutDist1);
		
		var cutDist2 = this.calculateLength(splitLineString[1]) - radius;
		var cutLine2 = null;
		if (cutDist2 > 0) {
			var reverseLine = splitLineString[1].reverse();
			cutLine2 = this.cutByDistance(reverseLine, cutDist2).reverse();
		}
		
		// 상월 생성
		var sPt = cutLine1[cutLine1.length - 1];
		var ePt = cutLine2[0];
		var sAngle = this.calculatePolarAngle(center, sPt);
		var eAngle = this.calculatePolarAngle(center, ePt);
		var lineAngle = this.calculatePolarAngle(sPt, ePt);
		var isCCW = false;
		switch(this.checkCCW(sPt, center, ePt))
		{
		case 0:	// 일직선
			if (sPt[0] == ePt[0]) {	// 수직선 
				if (sPt[1] < ePt[1])
					isCCW = true;
				else 
					isCCW = false;
			}
			else {
				if (sPt[0] < ePt[0])
					isCCW = false;					
				else
					isCCW = true;
			}
			break;
			
		default:
			if (lineAngle < 90 && lineAngle >= -90) 
				isCCW = false;
			else
				isCCW = true;
			break;
		}
		
		var upwidArc = this.createArcPoints(center, radius, sAngle, eAngle, 8, isCCW);
		
		var result = [];
		if (cutLine1) {
			result = result.concat(cutLine1);
		}
		if (upwidArc) {
			result = result.concat(upwidArc.slice(1, upwidArc.length-1));
		}
		if (cutLine2) {
			result = result.concat(cutLine2);
		}
		
		return result;
	},

	/**
	 * 호를 구성하는 정점목록을 생성
	 * @param {Array} center 
	 * @param radius
	 * @param start
	 * @param end
	 * @param split - 분할 수
	 * @param isCCW - 호의 회전방향
	 * @returns {Array}
	 */
	createArcPoints : function (center, radius, start, end, split, isCCW) {
		var result = [];

		var angle = end - start;
		if (isCCW && angle < 0)
			angle += 360;
		else if (!isCCW && angle > 0)
			angle -= 360;
		
		var unit = angle / (split - 1);
		var ua = unit * (Math.PI / 180.0);
		
		var startAngle = start * (Math.PI / 180.0);
		for (var i = 0; i < split; i++) {
			var rotateAngle = startAngle + (i * ua);
			var x = radius * Math.cos(rotateAngle);
			var y = radius * Math.sin(rotateAngle);
			
			var point = [];
			point.push(center[0] + x);
			point.push(center[1] + y);
			
			result.push(point);
		}
		
		return result;
	},
	
	/**
	 * 다각선에 하월을 생성.
	 * @param {Array} linestring
	 * @param {Array} ceneter
	 * @param radius;
	 * @return {Array<Array>}
	 */
	createDownwidLine : function (linestring, center, radius) {
		if (!linestring)
			return null;
		
		// 하월 호를 생성할 중심점을 기준으로 라인을 분할.
		var splitLineString = this.cutByPoint(linestring, center);
		if (splitLineString == null || splitLineString.length < 2)
			return null;
		
		// 분할된 라인을 반지름 길이 만큼 절단
		var cutDist1 = this.calculateLength(splitLineString[0]) - radius;
		var cutLine1 = null;
		if (cutDist1 > 0)
			cutLine1 = this.cutByDistance(splitLineString[0], cutDist1);
		
		var cutDist2 = this.calculateLength(splitLineString[1]) - radius;
		var cutLine2 = null;
		if (cutDist2 > 0) {
			var reverseLine = splitLineString[1].reverse();
			cutLine2 = this.cutByDistance(reverseLine, cutDist2).reverse();
		}
		
		// 하월 생성
		var sPt = cutLine1[cutLine1.length - 1];
		var ePt = cutLine2[0];
		var sAngle = this.calculatePolarAngle(center, sPt);
		var eAngle = this.calculatePolarAngle(center, ePt);
		var lineAngle = this.calculatePolarAngle(sPt, ePt);
		var isCCW = false;
		switch(this.checkCCW(sPt, center, ePt))
		{
		case 0:	// 일직선
			if (sPt[0] == ePt[0]) {	// 수직선 
				if (sPt[1] < ePt[1])
					isCCW = false;
				else 
					isCCW = true;
			}
			else {
				if (sPt[0] < ePt[0])
					isCCW = true;					
				else
					isCCW = false;
			}
			break;
			
		default:
			if (lineAngle < 90 && lineAngle >= -90) 
				isCCW = true;
			else
				isCCW = false;
			break;			
		}
		
		var downwidArc = this.createArcPoints(center, radius, sAngle, eAngle, 8, isCCW);
				
		var result = [];
		if (cutLine1) {
			result = result.concat(cutLine1);
		}
		if (downwidArc) {
			result = result.concat(downwidArc.slice(1, downwidArc.length-1));
		}
		if (cutLine2) {
			result = result.concat(cutLine2);
		}
		
		return result;
	},
	
	/**
	 * 세점의 방향성을 판단
	 * @param coord1
	 * @param coord2
	 * @param coord3
	 * @returns {Number}
	 */
	checkCCW : function (coord1, coord2, coord3) {
		var d = coord1[0]*coord2[1] + coord2[0]*coord3[1] + coord3[0]*coord1[1];
		d -= coord1[1]*coord2[0] + coord2[1]*coord3[0] + coord3[1]*coord1[0];
		
		if (d > 0)
			return 1; 	// 반시계 방향
		else if (d < 0)
			return -1; 	// 시계 방향
		else
			return 0;	// 일직선
	},
	
	/**
	 * 두 정점을 비교합니다.
	 * @param {Array} coord1
	 * @param {Array} coord2
	 * @returns {Boolean}
	 */
	comparePoint : function (coord1, coord2) {
		if (coord1[0] == coord2[0] && coord1[1] == coord2[1])
			return true;
		
		return false;
	},
	
	/**
	 * 직선의 시작점을 기준으로 직선의 기울기를 계산
	 * @param start
	 * @param end
	 * @return {Double}
	 */
	calculatePolarAngle : function(start, end) {
		var vx = end[0] - start[0];
		var vy = end[1] - start[1];
		
		return Math.atan2(vy, vx) * (180 / Math.PI);
	},
	
	/**
	 * 다각선의 전체 길이를 계산
	 * @param {Array} linestring
	 * @return {Double}
	 */
	calculateLength : function (linestring) {
		var length = 0.0;
		var count = linestring.length;

		for (var i = 1; i < count; i++) {
			var partLength = this.calculateDistance(linestring[i], linestring[i-1]);
			
			length += partLength;
		}
		
		return length;
	},
	
	/**
	 * 두 정점간의 거리를 계산
	 * @param {Array} point
	 * @param {Array} other
	 * @return {Double}
	 */
	calculateDistance : function (point, other) {
		var dx = point[0] - other[0];
		var dy = point[1] - other[1];
		
		return Math.sqrt((dx*dx) + (dy*dy));
	},
	
	/**
	 * 두 정점 사이에 위치하는 포인트를 계산 
	 */
	calculatePoint : function (start, end, rate) {
		var point = [];
		point.push(start[0] + (end[0] - start[0]) * rate);
		point.push(start[1] + (end[1] - start[1]) * rate);
		
		return point;
	},
	
	/**
	 * 다각선을 주어진 길이 만큼의 위치 
	 */
	calculateDistancePoint : function (linestring, distance) {
		var count = linestring.length;
		if (count < 2)
			return null;
		
		var length = 0.0;
		for (var i = 1; i < count; i++) {
			var partLength = this.calculateDistance(linestring[i], linestring[i-1]);
			if (distance <= length + partLength) {
				var rate = (distance - length) / partLength;
				return this.calculatePoint(linestring[i-1], linestring[i], rate);
			}
			
			length += partLength;
		}
		
		return null;
	},

	/**
	 * 다각선을 주어진 길이 만큼 절단
	 * @param {Array<Array>} linestring
	 * @param {Double} dustance 
	 * @return {Array};
	 */
	cutByDistance : function (linestring, distance) {
		var count = linestring.length;
		if (count < 2)
			return null;

		var length = 0.0;
		var result = [linestring[0]];
		for (var i = 1; i < count; i++) {
			var partLength = this.calculateDistance(linestring[i], linestring[i-1]);
			if (distance <= length + partLength) {
				var rate = (distance - length) / partLength;
				var end = this.calculatePoint(linestring[i-1], linestring[i], rate);
				if (end)
					result.push(end);
				
				return result;
			}
			
			length += partLength;
			result.push(linestring[i]);
		}
		
		return null;
	},
	
	/**
	 * 다각선을 정점으로 절단
	 * @param {Attay} linestring
	 * @param {Array} cutter 
	 */
	cutByPoint : function (linestring, cutter) {
		var result = [];
		var length = linestring.length;
		if (this.comparePoint(linestring[0], cutter) || this.comparePoint(linestring[length-1], cutter)) {
			result.push(linestring);
			return result;
		} 
		
		var check = false;
		var cutting = new Array();
		for (var i = 0; i < length-1; i++) {
			var start = linestring[i];
			var end = linestring[i+1];
			if (check && this.comparePoint(cutter, start))
				continue;
			
			cutting.push(start);
			
			if (!check) {
				var dist = this.shortestDistance(cutter, start, end);
				if (dist == 0) {
					check = true;
					cutting.push(cutter);
					result.push(cutting);
					
					cutting = new Array();
					cutting.push(cutter);
				}
			}
		}
		
		cutting.push(linestring[length-1]);
		result.push(cutting);
		
		if (check) {
			return result;
		}
		else {
			return null;
		}
	},
	
	/**
	 * 선분과 임의의 점과의 최단거리
	 * @param {Array} point
	 * @param {Array} start
	 * @param {Array} end
	 */
	shortestDistance : function (point, start, end) {
		
		if ((point[0] == start[0] && point[1] == start[1]) || (point[0] == end[0] && point[1] == end[1])) {
			return 0;
		}
		
		var deltaX = end[0] - start[0]; 
		var deltaY = end[1] - start[1]; 
		
		if (deltaX == 0 && deltaY == 0)
			return -1;
		
		var u = ((point[0] - start[0]) * deltaX + (point[1] - start[1]) * deltaY) / (deltaX*deltaX + deltaY*deltaY);
		
		var closestX = 0;
		var closestY = 0;
		if (u < 0) {
			closestX = start[0];
			closestY = start[1];
		}
		else if (u > 1) {
			closestX = end[0];
			closestY = end[1];
		}
		else {
			closestX = start[0] + u * deltaX;
			closestY = start[1] + u * deltaY;
		}
		
		var dx = closestX - point[0];
		var dy = closestY - point[1];
		
		var dist = Math.sqrt(dx*dx + dy*dy);
		return dist;
	},
	
	/**
	 * 선분위의 인접점을 계산 
	 * @param {ol.geom.LineString} linestring
	 * @param {Array} point
	 */
	calculateNearPoint : function(linestring, point) {
		var nearPoint = [];
		
		var dist = linestring.getLength();
		var coordinates = linestring.getCoordinates();
		
		var length = coordinates.length;
		for (var i = 0; i < length-1; i++) {
			var result = this.segmentNearPoint(coordinates[i], coordinates[i+1], point);
			if (result[0] != -1 && result[0] < dist) {
				dist = result[0];
				nearPoint = result[1];
			}
		}
		
		return nearPoint;
	},
	
	/**
	 * 선분위의 인접점을 계산 
	 * @param {Array} start
	 * @param {Array} end
	 * @param {Array} point 
	 */
	segmentNearPoint : function(start, end, point) {
		var result = [];
		
		if (point[0] == start[0] && point[1] == start[1]) {
			result.push(0);
			result.push(start);
		}

		if (point[0] == end[0] && point[1] == end[1]) {
			result.push(0);
			result.push(end);
		}
		
		var deltaX = end[0] - start[0]; 
		var deltaY = end[1] - start[1]; 
		
		if (deltaX == 0 && deltaY == 0) {
			result.push(-1);
			result.push(start);
		}
		
		var u = ((point[0] - start[0]) * deltaX + (point[1] - start[1]) * deltaY) / (deltaX*deltaX + deltaY*deltaY);
		
		var nearPoint = null;
		if (u < 0) {
			nearPoint = start;
		}
		else if (u > 1) {
			nearPoint = end;
		}
		else {
			nearPoint = new Array();
			nearPoint.push(start[0] + u * deltaX);
			nearPoint.push(start[1] + u * deltaY);
		}
		
		var dx = nearPoint[0] - point[0];
		var dy = nearPoint[1] - point[1];
		var dist = Math.sqrt(dx*dx + dy*dy);
		
		result.push(dist);
		result.push(nearPoint);
		
		return result;
	},
	
	calculateCenter : function(geometry) {
		var extent = geometry.getExtent();
		var x = (extent[0] + extent[2]) / 2;
		var y = (extent[1] + extent[3]) / 2;
		
		return new ol.geom.Point([x,y]);
	},
	
	/**
	 * 소스 좌표계의 좌표로 원의 면적을 계산
	 * @param {ol.geom.Geometry} geometry 공간객체
	 */
	calculateArea : function(geometry) {
		var geom = geometry.clone();
//		var proj = configObj.getProjection(); 
		var proj = ol.proj.get(MAP.PROJECTION);
		var mapProj = mapObj.getMap().getView().getProjection();
		if(proj != mapProj) {
			geom = geom.transform(mapProj, proj);
		}
		
		var radius = geom.getRadius();
		var area = radius * radius * Math.PI;

		return area;
	},
	
	formatArea : function(area) {
		var n = 4;
		var muliplier = Math.pow(10, n);
		
		var output;
	    if (area > 1000000) {
	        output = (Math.round((area / 1000000) * muliplier) / muliplier) + ' km<sup>2</sup>';
	    } else {
	        output = (Math.round(area * muliplier) / muliplier) + ' m<sup>2</sup>';
	    }
		
	    return output;
	}
};

/**
 * 공간 검색 유틸
 * @type {Object}
 */
var spatialSearchUtils = {
		
	/**
	 * 요약 검색
	 * PK값만 select
	 * @param {String} title 제목
	 * @param {Array.<String>} dataIds 데이터 아이디 목록
	 * @param {Object} filter 필터 목록
	 * @param {Function} callback 요청 성공 시 수행할 함수 
	 */
	searchSummaries : function(title, dataIds, filter, callback, options) {
		var params = $.extend({
			dataIds : dataIds
		}, filter);
		
		if(dataIds && dataIds.length > 0) {
			var url = CONTEXT_PATH + "/rest/spatial/searchSummaries.do";
			// 조회는 GET 을 사용해야 되지만 검색의 길이가 너무 커서 POST로 요청
			return $.post(url,params).done(function(response) {
				if(response && response.rows) {
					if(response.rows.length > 0) {
						if(callback) {
							callback(response.rows);
						}
					}
					else {
						// 지도 이동에 따른 법정동 위치 바인딩 시에는 메세지 처리 하지 않음
						if(!options || !options.isNoMessage) {
							$.messager.alert(title, "검색 결과가 없습니다.");
						}
					}
				}
				else {
					$.messager.alert(title, "공간 정보를 불러오는데 실패 했습니다.");
				}
			}).fail(function() {
				$.messager.alert(title, "공간 정보를 불러오는데 실패 했습니다.");
			});
		}
		else {
			$.messager.alert(title, "공간 정보를 확인할 데이터명 목록이 없습니다.");
		}
	},
	
	/**
	 * 검색
	 * @param {String} title 제목
	 * @param {Array.<String>} dataIds 데이터 아이디 목록
	 * @param {Object} filter 필터 목록
	 * @param {Function} callback 요청 성공 시 수행할 함수 
	 * @param {Object} options 옵션
	 */
	search : function(title, dataIds, filter, callback, options) {
		var params = $.extend({
			dataIds : dataIds
		}, filter);
		
		if(dataIds && dataIds.length > 0) {
			var url = CONTEXT_PATH + "/rest/spatial/search.do";
			// 조회는 GET 을 사용해야 되지만 검색의 길이가 너무 커서 POST로 요청
			return $.post(url,params).done(function(response) {
				if(response && response.rows) {
					if(response.rows.length > 0) {
						if(callback) {
							callback(response.rows);
						}
					}
					else {
						// 지도 이동에 따른 법정동 위치 바인딩 시에는 메세지 처리 하지 않음
						if(!options || !options.isNoMessage) {
							$.messager.alert(title, "검색 결과가 없습니다.");
						}
					}
				}
				else {
					$.messager.alert(title, "공간 정보를 불러오는데 실패 했습니다.");
				}
			}).fail(function() {
				$.messager.alert(title, "공간 정보를 불러오는데 실패 했습니다.");
			});
		}
		else {
			$.messager.alert(title, "공간 정보를 확인할 데이터명 목록이 없습니다.");
		}
	},
	
	/**
	 * 한 개의 데이터 요약 검색
	 * @param {String} title 제목
	 * @param {String} dataId 데이터 아이디
	 * @param {Object} filter 필터 목록
	 * @param {Function} callback 요청 성공 시 수행할 함수 
	 * @param {Object} options 옵션
	 */
	listAllSummary : function(title, dataId, filter, callback, options) {
		if(dataId) {
			var url = CONTEXT_PATH + "/rest/spatial/" + dataId + "/listAllSummary.do";
			// 조회는 GET 을 사용해야 되지만 검색의 길이가 너무 커서 POST로 요청
			return $.post(url,filter).done(function(response) {
				if(response) {
					if(response.data) {
						if(callback) {
							callback(response.data);
						}
					}
					else {
						// 지도 이동에 따른 법정동 위치 바인딩 시에는 메세지 처리 하지 않음
						if(!options || !options.isNoMessage) {
							$.messager.alert(title, "검색 결과가 없습니다.");
						}
						else {
							// 메세지 처리 하지 않을 경우 콜백
							if(callback) {
								callback(response.data);
							}
						}
					}
				}
				else {
					$.messager.alert(title, "공간 정보를 불러오는데 실패 했습니다.");
				}
			}).fail(function() {
				$.messager.alert(title, "공간 정보를 불러오는데 실패 했습니다.");
			});
		}
		else {
			$.messager.alert(title, "공간 정보를 확인할 데이터명이 없습니다.");
		}
	},
	
	/**
	 * 한 개의 데이터 목록 검색 (페이징 X)
	 * @param {String} title 제목
	 * @param {Array.<String>} dataIds 데이터 아이디 목록
	 * @param {Object} filter 필터 목록
	 * @param {Function} callback 요청 성공 시 수행할 함수 
	 */
	listAll : function(title, dataId, filter, callback, options) {
		if(dataId) {
			var url = CONTEXT_PATH + "/rest/spatial/" + dataId + "/listAll.do";
			// 조회는 GET 을 사용해야 되지만 검색의 길이가 너무 커서 POST로 요청
			return $.post(url,filter).done(function(response) {
				if(response && response.rows) {
					if(response.rows.length <= 0) {
						// 지도 이동에 따른 법정동 위치 바인딩 시에는 메세지 처리 하지 않음
						if(!options || !options.isNoMessage) {
							$.messager.alert(title, "검색 결과가 없습니다.");
						}
					}
					if(callback) {
						callback(response.rows, dataId);
					}
				}
				else {
					$.messager.alert(title, "공간 정보를 불러오는데 실패 했습니다.");
				}
			}).fail(function() {
				$.messager.alert(title, "공간 정보를 불러오는데 실패 했습니다.");
			});
		}
		else {
			$.messager.alert(title, "공간 정보를 확인할 데이터명이 없습니다.");
		}
	},
	
	/**
	 * 한 개의 데이터 목록 검색 (페이징 O)
	 * @param {String} title 제목
	 * @param {Array.<String>} dataIds 데이터 아이디 목록
	 * @param {Object} filter 필터 목록
	 * @param {Function} callback 요청 성공 시 수행할 함수 
	 */
	list : function(title, dataId, filter, callback, options) {
		if(dataId) {
			var url = CONTEXT_PATH + "/rest/spatial/" + dataId + "/list.do";
			// 조회는 GET 을 사용해야 되지만 검색의 길이가 너무 커서 POST로 요청
			return $.post(url,filter).done(function(response) {
				if(response && response.rows) {
					if(response.rows.length <= 0) {
						if(!options || !options.isNoMessage) {
							$.messager.alert(title, "검색 결과가 없습니다.");
						}
					}
					if(callback) {
						callback(response.rows, response.paginationInfo);
					}
				}
				else {
					$.messager.alert(title, "공간 정보를 불러오는데 실패 했습니다.");
				}
			}).fail(function() {
				$.messager.alert(title, "공간 정보를 불러오는데 실패 했습니다.");
			});
		}
		else {
			$.messager.alert(title, "공간 정보를 확인할 데이터명이 없습니다.");
		}
	},
	
	/**
	 * 단 건 조회
	 * @param {String} title 제목
	 * @param {String} dataId 데이터 아이디
	 * @param {Object} fid 객체 아이디
	 * @param {Function} callback 요청 성공 시 수행할 함수 
	 */
	selectOne : function(title, dataId, fid, params, callback) {
		if(dataId) {
			var url = CONTEXT_PATH + "/rest/spatial/" + dataId + "/" + fid + "/select.do";
			return $.get(url, params).done(function(response) {
				if(response && response.data) {
					if(callback) {
						callback(response.data);
					}
				}
				else {
					$.messager.alert(title, "공간 정보를 불러오는데 실패 했습니다.");
				}
			}).fail(function() {
				$.messager.alert(title, "공간 정보를 불러오는데 실패 했습니다.");
			});
		}
		else {
			$.messager.alert(title, "공간 정보를 확인할 데이터명이 없습니다.");
		}
	}
		
};

/**
 * 색상 유틸
 * @type {Object}
 */
var colorUtils = {
		
	/**
	 * 색상 객체 반환
	 * @param {String} hex 색상 헥사코드 (ex : #ff0000)
	 * @param {Number} opacity 투명도
	 * @returns {ol.Color} 색상 객체 반환
	 */
	toColorFromHex : function(hex, opacity) {
		var color = ol.color.asArray(hex).slice();
		if(opacity >= 0) {// Lks : 투명도가 0인 경우 처리가 안되서 수정 
			color[3] = opacity;
		}
		return color;
	},
		
	/**
	 * 색상 객체를 색상 헥사코드 및 투명도로 변환 
	 * @param color - 색상 객체
	 * @returns {hex, opacity} 헥사코드 및 투명도
	 */
	convertColor : function(color) {
		
		var hexColor = "";
		var opacity = 1;
		
		if(goog.color.isValidHexColor_(color)) {
			hexColor = color;
		} 
		else {
			if (color.indexOf('rgb') == 0) {
				var rgba = ol.color.fromString(color);
				hexColor = goog.color.rgbArrayToHex(rgba);
				opacity = rgba[3];
			} else {
				hexColor = goog.color.rgbArrayToHex(color);
				opacity = color[3];
			}
		}
	
		return {
			"hex" :  hexColor,
			"opacity" : opacity
		};
	}
};

/**
 * Casvas 유틸
 * @type {Object}
 */
var canvasUtils = {
	
	/**
	 * 선 스타일
	 * @param {String} str 선 스타일 (문자열형)
	 * @param width 선 두께
	 * @returns {Array.<Number>} 선 스타일 (Canvas 스타일)
	 */
	toLineDash : function(str, width) {
		var factor = 1;
		if(width) {
			factor = width;
		}
		
		var lineDash = null;
		if(str == "dot") {
			lineDash = [2*factor, 2*factor];
		}
		else if(str == "dash") {
			lineDash = [7*factor, 3*factor];
		}
		else if(str == "dashDot") {
			lineDash = [10*factor, 2*factor, 2*factor, 2*factor];
		}
		else if(str == "dashDotDot") {
			lineDash = [10*factor, 2*factor, 2*factor, 2*factor, 2*factor, 2*factor];
		}
		else {
			lineDash = [1*factor, 0];
		}
		return lineDash;
	},
	
	/**
	 * 위치
	 * @param {String} str 위치 (문자열형)
	 * @returns {Array.<Number>} 위치 (Canvas 스타일)
	 */
	toAnchor : function(str) {
		var anchor = null;
		switch(str) {
			case "lt" :
				anchor = [0, 0];
				break;
			case "ct" :
				anchor = [0.5, 0];
				break;
			case "rt" :
				anchor = [1, 0];
				break;
			case "lm" :
				anchor = [0, 0.5];
				break;
			case "cm" :
				anchor = [0.5, 0.5];
				break;
			case "rm" :
				anchor = [1, 0.5];
				break;
			case "lb" :
				anchor = [0, 1];
				break;
			case "cb" :
				anchor = [0.5, 1];
				break;
			case "rb" :
				anchor = [1, 1];
				break;
			default :
				anchor = [0.5, 0.5];
				break;
		}
		return anchor;
	},
	
	/**
	 * 폰트
	 * @param {Object} options 폰트 옵션
	 * @returns {String} 폰트 (Canvas 스타일)
	 */
	toFont : function(options) {
		var font = "";
		// 굵게 설정
		if(options.fontWeight && options.fontWeight == "bold") {
			font += "bold ";
		}
		// 기울기 설정
		if(options.fontStyle && options.fontStyle == "italic") {
			font += "italic ";
		}
		font += options.fontSize + "px " +  options.fontFamily;
		return font;
	},
	
	/**
	 * 텍스트 정렬
	 * @param {String} str 텍스트 정렬
	 * @returns {String} 텍스트 정렬 (Canvas 스타일)
	 */
	toTextAlign : function(str) {
		var textAlign = null;
		switch(str) {
			case "l" :
				textAlign = "left";
				break;
			case "c" :
				textAlign = "center";
				break;
			case "r" :
				textAlign = "right";
				break;
		}
		return textAlign;
	},

	/**
	 * 라벨 정렬
	 * @param {String} str 텍스트 정렬
	 * @returns {String} 텍스트 정렬 (Canvas 스타일)
	 */
	toLabelAlign : function(str) {
		var textAlign = null;
		switch(str) {
			case "l" :
				textAlign = "right";
				break;
			case "c" :
				textAlign = "center";
				break;
			case "r" :
				textAlign = "left";
				break;
		}
		return textAlign;
	},

	/**
	 * 텍스트 기준 선
	 * @param {String} str 텍스트 기준 선
	 * @returns {String} 텍스트 기준 선 (Canvas 스타일)
	 */
	toTextBaseline : function(str) {
		var textBaseline = null;
		switch(str) {
			case "t" :
				textBaseline = "bottom";
				break;
			case "m" :
				textBaseline = "middle";
				break;
			case "b" :
				textBaseline = "top";
				break;
		}
		return textBaseline;
	}
		
};

/**
 * 숫자 유틸
 * @type {Object}
 */
var numberUtils = {
	
	/**
	 * number type 의 값에 currency separator 추가
	 * @param {number} 값  
	 * @return {String} 1000 단위 comma(,) 로 구분된 문자열
	 */
	formatCurrency : function(value) {
		var result = '';
		if(value) {
			// 타입 체크
			if(typeof value == "number") {
				result = value.toString();
			}
			else {
				result = value;
			}
			// 정수부 3자리마다 ',' 표시
			result = result.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		}
		return result; 
	}

};


/**
 * 지형지도 검색 유틸
 * @type {Object}
 */
var topographicUtils = {
		
	/**
	 * 검색
	 * @param {String} title 제목
	 * @param {Function} callback 요청 성공 시 수행할 함수 
	 * @param {Object} options 옵션
	 */
	search : function(title, group, srs, extent, position, width, height, callback) {
		var params = {
			groupName : group,
			srs : srs,
			minX : extent[0],
			minY : extent[1],
			maxX : extent[2],
			maxY : extent[3],
			x : position[0],
			y : position[1],
			width : width,
			height : height
		};
		
		if(group && group.length > 0) {
			var url = CONTEXT_PATH + "/topographic/search.do";
			$.post(url, params).done(function(response) {
				if(response && response.gridData) {
					if(callback) {
						callback(response.gridData);
					}
				}
				else {
//					$.messager.alert(title, "지형데이터를 불러오는데 실패 했습니다.");
					if(callback) {
						callback(null);
					}
				}
			}).fail(function() {
//				$.messager.alert(title, "지형데이터를 불러오는데 실패 했습니다.");
				if(callback) {
					callback(null);
				}
			});
		}
		else {
//			$.messager.alert(title, "확인할 지형데이터를 지정하지 않았습니다.");
			if(callback) {
				callback(null);
			}
		}
		
		return false;
	},
			
	profile : function(title, group, layer, interval, srs, extent, start, end, width, height, callback) {
		var params = {
			group : group, 
			layer : layer,
			startX : parseInt(start[0]),
			startY : parseInt(start[1]),
			endX : parseInt(end[0]),
			endY : parseInt(end[1]),
			interval : interval,
			srs : srs,
			minX : extent[0],
			minY : extent[1],
			maxX : extent[2],
			maxY : extent[3],
			width : width,
			height : height
		};

		if(layer && layer.length > 0) {
			var url = CONTEXT_PATH + "/topographic/profile.do";
			$.post(url, params).done(function(response) {
				if(response) {
					if(callback) {
						callback(response.profileData);
					}
				}
				else {
					$.messager.alert(title, "지형단면 데이터를 불러오는데 실패 했습니다.");
				}
			}).fail(function() {
				$.messager.alert(title, "분석에 실패 했습니다.");
			});
		}
		else {
			$.messager.alert(title, "분석할 지형데이터를 지정하지 않았습니다.");
		}
		return false;
	}
};

/**
 * 도시계획도로 검색 유틸
 * @type {Object}
 */
var cityPlanRoadUtils = {
	
	/**
	 * 도시계획도로 목록 검색 (페이징 O)
	 * @param {String} title 제목
	 * @param {Object} params 검색 파라메터
	 * @param {Function} callback 요청 성공 시 수행할 함수 
	 */
	list : function(title, params, callback, options) {
		
		var url = CONTEXT_PATH + "/rest/cityPlanRoad/list.do";
		return $.post(url,params).done(function(response) {
			if(response && response.rows) {
				if(response.rows.length <= 0) {
					if(!options || !options.isNoMessage) {
						$.messager.alert(title, "검색 결과가 없습니다.");
					}
				}
				if(callback) {
					callback(response.rows, response.paginationInfo);
				}
			}
			else {
				$.messager.alert(title, "검색을 실패 했습니다.");
			}
		}).fail(function() {
			$.messager.alert(title, "검색을 실패 했습니다.");
		});
	},
};
