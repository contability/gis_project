<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>
<div>
<input type="hidden" id="wttMetaHtFtrCde" value="${ftrCde }" />
<input type="hidden" id="wttMetaHtFtrIdn" value="${ftrIdn }" />

	<table id="wttMetaHtListTb" class="easyui-datagrid" title="관리이력목록" style="width:550px;height:220px"
	        data-options="singleSelect:true,collapsible:true">
	    <thead>
	        <tr>
	            <th data-options="field:'ftrCde',width:0, hidden:true"></th>
	            <th data-options="field:'ftrIdn',width:0, hidden:true"></th> 
	            <th data-options="field:'shtIdn',width:0, hidden:true"></th>
	            <th data-options="field:'gcwCde',width:0, hidden:true">교체구분</th>
	            <th data-options="field:'crsCde',width:0, hidden:true">교체사유</th>
	            <th data-options="field:'gcwCdeNm',width:135">교체구분</th>
	            <th data-options="field:'crsCdeNm',width:135">교체사유</th>
	            <th data-options="field:'chgYmd',width:135">교체일자</th>
	            <th data-options="field:'omeCnt',width:135">철거계량기 지침수</th>
	        </tr>
	    </thead>
	    <tbody>
	    	<c:forEach items="${resultList}" var="list">
	    	<tr>
		    	<td>${list.ftrCde}</td>
		    	<td>${list.ftrIdn}</td>
		    	<td>${list.shtIdn}</td>
		    	<td>${list.gcwCde}</td>
		    	<td>${list.crsCde}</td>
		    	<td>${list.gcwCdeNm}</td>
		    	<td>${list.crsCdeNm}</td>
		    	<td>${list.chgYmd}</td>
		    	<td>${list.omeCnt}</td>
	    	</tr>
	    	</c:forEach>	
	    </tbody>
	</table>
	
	<span class="f_r ma_t_20">
		<a href="#" class="btn_register" onclick="changeHistoryMng.insertView();"></a>
		<a href="#" class="btn_edit" onclick="changeHistoryMng.editView();"></a>
		<a href="#" class="btn_close" onclick="changeHistoryMng.close();"></a>
	</span>
</div>

<script>
$('#wttMetaHtListTb').datagrid({
	onDblClickCell: function(index,field,value){
		fn_tbDbClick();
	}
});

function fn_tbDbClick(){
	var param = {};
	var row = $('#wttMetaHtListTb').datagrid('getSelected');
    
	if(row){
        param.ftrCde = row.ftrCde;
        param.ftrIdn = row.ftrIdn;
        param.shtIdn = row.shtIdn;
    }
	
	changeHistoryMng.view(param);
}
</script>