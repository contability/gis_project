/**
 * 도메인 코드 객체
 * @type {Object}
 */
var deptSubObj = {
		
	/**
	 * 제목
	 * @Type {String}
	 */
	TITLE : "담당 부서",
		
	/**
	 * 도메인 목록
	 * @type {Object}
	 */
	data : {},
	
	/**
	 * 불러오기
	 * @param {String} deptSubCode 도메인 아이디
	 * @return {Object} Promise 객체
	 */
	load : function(deptCode) {
		var url = CONTEXT_PATH + "/rest/deptSub/"+ deptCode + "/listAll.do";
		return $.ajax({
			url : url,
			async : false
		});
	},
	
	getData : function(deptCode) {
		var that = this;
		if(!that.data[deptCode]) {
			that.load(deptCode).done(function(response) {
				if(response && response.rows) {
					that.data[deptCode] = response.rows;
				}
				else {
					$.messager.alert(that.TITLE, "실과소 정보가 없습니다");
				}
			}).fail(function() {
				$.messager.alert(that.TITLE, "실과소 정보를 불러오는데 실패하였습니다.");
			});
		}
		return that.data[deptCode];
	},
	
	forDeptCode : function(deptSubCode){
		var that = this;
		var plcyDeptCode = '';
		
		var url = CONTEXT_PATH +"/policy/" + deptSubCode + "/forDeptCode.do";
		$.ajax({
			url : url,
			type : 'get',
			datatype : 'json',
			async : false,
			success : function(rs){
				if(rs && rs.row){
					plcyDeptCode = rs.row;
				}else{
					$.messager.alert(that.TITLE, "담당 부서 정보가 없습니다.");
				}
			},
			error : function(e){
				$.messager.alert(that.TITLE, "담당 부서 정보를 불러오는데 실패하였습니다.");
			}
		});
		
		return plcyDeptCode;
	}

	
};