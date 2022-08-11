<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>
<div>
<input type="hidden" id="wttRsrvHtFtrCde" value="${ftrCde }" />
<input type="hidden" id="wttRsrvHtFtrIdn" value="${ftrIdn }" />

	<table id="wttRsrvHtListTb" class="easyui-datagrid" title="관리이력목록" style="width:550px;height:220px"
	        data-options="singleSelect:true,collapsible:true">
	    <thead>
	        <tr>
	            <th data-options="field:'ftrCde',width:0, hidden:true"></th>
	            <th data-options="field:'ftrIdn',width:0, hidden:true"></th> 
	            <th data-options="field:'shtIdn',width:0, hidden:true"></th>
	            <th data-options="field:'clnYmd',width:135">청소일자</th>
	            <th data-options="field:'clnNam',width:135">청소업체명</th>
	            <th data-options="field:'clnExp',width:270">청소내용</th>
	        </tr>
	    </thead>
	    <tbody>
	    	<c:forEach items="${resultList}" var="list">
	    	<tr>
		    	<td>${list.ftrCde}</td>
		    	<td>${list.ftrIdn}</td>
		    	<td>${list.shtIdn}</td>
		    	<td>${list.clnYmd}</td>
		    	<td>${list.clnNam}</td>
		    	<td>${list.clnExp}</td>
	    	</tr>
	    	</c:forEach>	
	    </tbody>
	</table>
	
	<span class="f_r ma_t_20">
		<a href="#" class="btn_register" onclick="cleanHistoryMng.insertView();"></a>
		<a href="#" class="btn_edit" onclick="cleanHistoryMng.editView();"></a>
		<a href="#" class="btn_close" onclick="cleanHistoryMng.close();"></a>
	</span>
</div>

<script>
//$('#historyListTb').datagrid();  

$('#wttRsrvHtListTb').datagrid({
	onDblClickCell: function(index,field,value){
		fn_tbDbClick();
	}
});

function fn_tbDbClick(){
	var param = {};
	var row = $('#wttRsrvHtListTb').datagrid('getSelected');
    
	if(row){
        param.ftrCde = row.ftrCde;
        param.ftrIdn = row.ftrIdn;
        param.shtIdn = row.shtIdn;
    }
	
	cleanHistoryMng.view(param);
}
</script>