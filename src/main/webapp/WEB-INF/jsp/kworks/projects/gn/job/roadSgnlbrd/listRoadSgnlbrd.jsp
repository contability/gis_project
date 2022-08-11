<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<div id="listRoadSgnlbrd" class="window_container">
	<div class="window_article">
		<table class="table_text">
			<tbody>
				<tr>
					<th>검색방법</th>
					<td>
						&nbsp;<input type="radio" id="listRoadSgnlbrd_searchKind_sgnal_cn" name="listRoadSgnlbrd_searchKind" checked="checked" value="SGNAL_CN" />&nbsp;<label for="listRoadSgnlbrd_searchKind_sgnal_cn">표지내용 검색</label>
						&nbsp;&nbsp;&nbsp;<input type="radio" id="listRoadSgnlbrd_searchKind_bbox" name="listRoadSgnlbrd_searchKind" value="BBOX" />&nbsp;<label for="listRoadSgnlbrd_searchKind_bbox">현재화면 검색</label>
					</td>
				</tr>
				<tr>
					<th>표지내용</th>
					<td><input type="text" class="easyui-textbox signalDetails" name="listRoadSgnlbrd_signalDetails" style="width: 95%;"/></td>
				</tr>
			</tbody>
		</table>
		<div style="width:100%; height:10px">
			<span style="font-size:12px; color:gray; float:right;">※ 표지내용을 ','로 구분하여 OR검색을 할 수 있습니다.</span>
		</div>
	</div>
	<div class="window_footer">
		<div class="button_flat">
			<button class="a_search_roadSgnlbrd button_flat_normal btn_blue">검색</button>
		</div>
	</div>
</div>