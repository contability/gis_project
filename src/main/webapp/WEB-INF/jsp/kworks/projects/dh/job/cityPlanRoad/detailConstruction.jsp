<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui"			 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			 uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		 uri="http://www.springframework.org/tags/form" %>

<form id="detailCityPlanRoadConstructionForm" name="detailCityPlanRoadConstructionForm" method="post" enctype="multipart/form-data">
	<div id="detailCityPlanRoadConstruction" class="window_container">
		<div class="window_article">
			<input type="hidden" name="editAt" value="${editAt}" />
			<table class="table_text">
				<tbody>
					<c:if test="${editAt eq 'Y' or editAt eq 'y'}">
						<tr>
							<th>관리번호</th>
							<td colspan="5" id="ftrIdn">${row.ftrIdn}</td>
						</tr>
						<tr>
							<th>시작일</th>
							<td>
								<input type="text" class="w_205 easyui-datebox"	name="conStr" value="${row.conStr}" />						
							</td>
							<th>종료일</th>
							<td>
								<input type="text" class="w_205 easyui-datebox"	name="conEnd" value="${row.conEnd}" />						
							</td>
						</tr>
						<tr>
							<th>공사내용</th>
							<td colspan="5">
								<input type="text" class="easyui-textbox" name="conDoc" style="width:99%" value="${row.conDoc}"/>
							</td>
						</tr>
					</c:if>
					<c:if test="${editAt eq 'N' or editAt eq 'n'}">
						<tr>
							<th>관리번호</th>
							<td colspan="5" id="ftrIdn">${row.ftrIdn}</td>
						</tr>
						<tr>
							<th>시작일</th>
							<td colspan="2" id="conStr">${row.conStr}</td>					
							<th>종료일</th>
							<td colspan="2" id="conEnd">${row.conEnd}</td>					
						</tr>
						<tr>
							<th>공사내용</th>
							<td colspan="5" id="conDoc">${row.conDoc}"</td>
						</tr>
					</c:if>				
				</tbody>
			</table>
		</div>
		<div class="window_footer">
			<div class="button_flat">
				<c:if test="${editAt eq 'Y' or editAt eq 'y'}">
					<button id="btn_update" class="button_flat_normal btn_blue">저장</button>
					<button id="btn_delete" class="button_flat_normal btn_blue">삭제</button>
				</c:if>
				<c:if test="${editAt eq 'N' or editAt eq 'n'}">
					<button id="btn_update" class="button_flat_normal btn_grey2" title="사용되지 않는 기능입니다.">저장</button>
					<button id="btn_delete" class="button_flat_normal btn_grey2" title="사용되지 않는 기능입니다.">삭제</button>
				</c:if>
				<button id="btn_close" class="button_flat_normal btn_black">닫기</button>
			</div>
		</div>
	</div>
</form>
