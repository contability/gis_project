<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form id="modifyCityPlanRoadform" name="modifyCityPlanRoadform" method="post" enctype="multipart/form-data">
	<div id="modifyCityPlanRoad" class="window_container">
		<div class="window_article">
			<input type="hidden" name="ftrCde" value="${row.ftrCde}" />
			<input type="hidden" name="uprType" value="${row.uprTyp}" />
			<input type="hidden" name="uprStr" value="${row.uprStr}" />
			<input type="hidden" name="uprEnd" value="${row.uprEnd}" />
			<table id="cityPlanRoad_modify" class="table_text">
				<tbody>
					<tr>
						<th>관리번호</th>
						<td colspan="5" id="ftrIdn">${ftrIdn}</td>
					</tr>
					<tr>
						<th>국도명</th>
						<td>
							<input type="text" class="w_95 easyui-textbox" name="uprNam" value="${row.uprNam}"/>						
						</td>
						<th>규모별 구분</th>
						<td>
							<select id="uprGrd" name="uprGrd" class="w_95 easyui-combobox">
								<c:forEach items="${grdList}" var="grdCde" varStatus="status">
									<option value="${grdCde.codeId}" <c:if test="${row.uprGrd eq grdCde.codeId}">selected="selected"</c:if>>${grdCde.codeNm}</option>
								</c:forEach>
							</select>
						</td>
						<th>분류</th>
						<td>
							<select id="uprTyp" name="uprTyp" class="w_95 easyui-combobox">
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
							</select>
						</td>
					</tr>					
					<tr>
						<th>노선번호</th>
						<td>
							<input type="text" class="w_95 easyui-textbox" name="uprNum" value="${row.uprNum}"/>						
						</td>
						<th>기능별 구분</th>
						<td>
							<select id="uprFnc" name="uprFnc" class="w_95 easyui-combobox">
								<c:forEach items="${fncList}" var="fncCde" varStatus="status">
									<option value="${fncCde.codeId}" <c:if test="${row.uprFnc eq fncCde.codeId}">selected="selected"</c:if>>${fncCde.codeNm}</option>
								</c:forEach>
							</select>
						</td>
						<th>노선지정일</th>
						<td>
							<input type="text" class="w_95 easyui-datebox" name="uprYmd" value="${row.uprYmd}" />						
						</td>
					</tr>
 					<tr>
						<th>기점 소재지</th>
						<td colspan="2">
							<select class="w_200 easyui-combobox" name="strDong" id="strDong"></select>						
						</td>
						<td>
							산
							<span>
								<input class="easyui-switchbutton" name="strMauntain" id="strMauntain"/>
							</span>
						</td>
						<td>
							본번
							<span>
								<input type="text" class="w_60 easyui-numberspinner" name="strMain" id="strMain"/>
							</span>
						</td>
						<td>
							부번
							<span>
								<input type="text" class="w_60 easyui-numberspinner" name="strSub" id="strSub"/>
							</span>
						</td>
					</tr>					
  					<tr>
						<th>종점 소재지</th>
						<td colspan="2">
							<select class="w_200 easyui-combobox" name="endDong" id="endDong"></select>						
						</td>
						<td>
							산
							<span>
								<input class="easyui-switchbutton" name="endMauntain" id="endMauntain"/>
							</span>
						</td>
						<td>
							본번
							<span>
								<input type="text" class="w_60 easyui-numberspinner" name="endMain" id="endMain"/>
							</span>
						</td>
						<td>
							부번
							<span>
								<input type="text" class="w_60 easyui-numberspinner" name="endSub" id="endSub"/>
							</span>
						</td>
					</tr>
	  				<tr>
						<th colspan="2">미개통도 연장 (m)</th>
						<td>
							<input type="text" class="w_95 easyui-numberbox" name="uprUnop" value="${row.uprUnop}"/>						
						</td>
 						<th colspan="2">미개설도 연장 (m)</th>
						<td>
							<input type="text" class="w_95 easyui-numberbox" name="uprUnse" value="${row.uprUnse}"/>						
						</td>
					</tr>
 	 				<tr>
 	 					<th rowspan="3" colspan="2">포장도 연장 (m)</th>
						<th>1차선</th>
						<td>
							<input type="text" class="w_95 easyui-numberbox" name="uprPa01" value="${row.uprPa01}"/>						
						</td>
						<th>2차선</th>
						<td>
							<input type="text" class="w_95 easyui-numberbox" name="uprPa02" value="${row.uprPa02}"/>						
						</td>
					</tr>
					<tr>
						<th>4차선</th>
						<td>
							<input type="text" class="w_95 easyui-numberbox" name="uprPa04" value="${row.uprPa04}"/>						
						</td>
						<th>6차선</th>
						<td>
							<input type="text" class="w_95 easyui-numberbox" name="uprPa06" value="${row.uprPa06}"/>						
						</td>
					</tr>
					<tr>
						<th>8차선</th>
						<td>
							<input type="text" class="w_95 easyui-numberbox" name="uprPa08" value="${row.uprPa08}"/>						
						</td>
						<th>10차선 이상</th>
						<td>
							<input type="text" class="w_95 easyui-numberbox" name="uprPa10" value="${row.uprPa10}"/>						
						</td>
					</tr>
   					<tr>
						<th rowspan="3" colspan="2">미포장도 연장(m)</th>
						<th>1차선</th>
						<td>
							<input type="text" class="w_95 easyui-numberbox" name="uprUn01" value="${row.uprUn01}"/>						
						</td>
						<th>2차선</th>
						<td>
							<input type="text" class="w_95 easyui-numberbox" name="uprUn02" value="${row.uprUn02}"/>						
						</td>
					</tr>
					<tr>
						<th>4차선</th>
						<td>
							<input type="text" class="w_95 easyui-numberbox" name="uprUn04" value="${row.uprUn04}"/>						
						</td>
						<th>6차선</th>
						<td>
							<input type="text" class="w_95 easyui-numberbox" name="uprUn06" value="${row.uprUn06}"/>						
						</td>
					</tr>
					<tr>
						<th>8차선</th>
						<td>
							<input type="text" class="w_95 easyui-numberbox" name="uprUn08" value="${row.uprUn08}"/>						
						</td>
						<th>10차선 이상</th>
						<td>
							<input type="text" class="w_95 easyui-numberbox" name="uprUn10" value="${row.uprUn10}"/>						
						</td>
					</tr>
					<tr>
						<th colspan="2">보도 설치일</th>
						<td>
							<input type="text" class="w_95 easyui-datebox" name="uprWok" value="${row.uprWok}" />						
						</td>
						<th colspan="2">보도 유효폭 (m)</th>
						<td>
							<input type="text" class="w_95 easyui-numberbox" name="uprWid" value="${row.uprWid}"/>						
						</td>
					</tr>
					<tr>
						<th colspan="2">보도 횡단경사 (%)</th>
						<td>
							<input type="text" class="w_95 easyui-numberbox" name="uprSle" value="${row.uprSle}"/>						
						</td>
						<th colspan="2">보도 연석높이 (m)</th>
						<td>
							<input type="text" class="w_95 easyui-numberbox" name="uprHgt" value="${row.uprHgt}"/>						
						</td>
					</tr>
					<tr>
						<th colspan="2">보도 좌측연장 (m)</th>
						<td>
							<input type="text" class="w_95 easyui-numberbox" name="uprWkl" value="${row.uprWkl}"/>						
						</td>
						<th colspan="2">보도 우측연장 (m)</th>
						<td>
							<input type="text" class="w_95 easyui-numberbox" name="uprKwr" value="${row.uprWkr}"/>						
						</td>
					</tr>
					<tr>
						<th>비고</th>
						<td colspan="5">
							<input type="text" class="easyui-textbox" style="width:95%" name="remark" value="${row.remark}"/>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="window_footer">
			<div class="button_flat">
				<button id="btn_update" class="button_flat_normal btn_blue">저장</button>
				<button id="btn_close" class="button_flat_normal btn_black">닫기</button>
			</div>
		</div>
	</div>
</form>