<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>

<script>

	$('#historyListTb').datagrid({
		onDblClickCell: function(index,field,value){
			fn_tbDbClick();
		}
	});
	
	function fn_tbDbClick(){
		var param = {};
		var row = $('#historyListTb').datagrid('getSelected');
	    
		if (row){
	        param.ftrCde = row.ftrCde;
	        param.ftrIdn = row.ftrIdn;
	        param.shtIdn = row.shtIdn;
	    }
		
		
		historyMng.view(param);
	}
	
	function consMaView(){
		var ftrCde = $("#historyViewFtrCde").val();
		var ftrIdn = $("#historyViewFtrIdn").val();
		bisDocuObj.consMaViewOpen( ftrCde, ftrIdn, true );
	}
	
</script>

<div>
<input type="hidden" id="historyViewFtrCde" value="${ftrCde }" />
<input type="hidden" id="historyViewFtrIdn" value="${ftrIdn }" />

	<table id="historyListTb" class="easyui-datagrid" title="관리이력목록" style="width:550px;height:220px"
	        data-options="singleSelect:true,collapsible:true">
	    <thead>
	        <tr>
	            <th data-options="field:'shtIdn',width:0, hidden:true"></th>
	            <th data-options="field:'ftrCde',width:0, hidden:true"></th>
	            <th data-options="field:'ftrIdn',width:0, hidden:true"></th> 
	            <th data-options="field:'cntIdn',width:0, hidden:true"></th>
	            <th data-options="field:'mnhCde',width:0, hidden:true">관리이력코드 </th>
	            <th data-options="field:'mnhNm' ,width:135">관리이력구분</th>
	            <th data-options="field:'sbjCde',width:135">보수사유</th>
	            <th data-options="field:'sreYmd',width:135">보수시작일</th>
	            <th data-options="field:'ereYmd',width:135">보수종료일</th>
	        </tr>
	    </thead>
	    <tbody>
	    	<c:forEach items="${resultList}" var="list">
	    	<tr>
		    	<td>${list.shtIdn}</td>
		    	<td>${list.ftrCde}</td>
		    	<td>${list.ftrIdn}</td>
		    	<td>${list.cntIdn}</td>
		    	<td>${list.mnhCde}</td>
		    	<td>${list.mnhNm}</td>
		    	<td>${list.sbjCde}</td>
		    	<td>${list.sreYmd}</td>
		    	<td>${list.ereYmd}</td>
	    	</tr>
	    	</c:forEach>	
	    </tbody>
	</table>
	
	<span class="f_l ma_t_20">
		<c:if test="${!empty list.shtIdn}">
			<a href="#" id="consMaView" onclick="consMaView();"><img src="<c:url value='/images/kworks/map/skin2/btn_constru2.png' />" alt="공사대장" /></a>
		</c:if>
	</span>
	<span class="f_r ma_t_20">
		<a href="#" class="btn_register" onclick="historyMng.insertView();"></a>
		<a href="#" class="btn_edit" onclick="historyMng.editView();"></a>
		<!-- <a href="#" class="btn_del" onclick="historyMng.remove();"></a> -->
		<a href="#" class="btn_close" onclick="historyMng.close();"></a>
	</span>
</div>
