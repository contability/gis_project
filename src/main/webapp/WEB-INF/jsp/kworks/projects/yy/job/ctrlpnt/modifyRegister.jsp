<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form id="modifyControlPointForm" name="modifyControlPointForm" method="post" enctype="multipart/form-data">
	<div id="modifyControlPoint" class="window_container">
		<div class="window_article">
			<input type="hidden" name="alias" value="${alias}" />
			<input type="hidden" name="wkt" value="${row.wkt}" />
			<input type="hidden" name="ftrCde" value="${row.ftrCde}" />
			<input type="hidden" name="ftrIdn" value="${row.ftrIdn}" />
			<input type="hidden" name="dataCde" value="${row.setCde}" />
			<input type="hidden" name="bjdCde" value="${row.bjdCde}" />
			<input type="hidden" name="hjdCde" value="${row.hjdCde}" />
			<table id="tableControlPoint" class="table_text">
				<tbody>
					<tr>
						<th>기준점 종류</th>
						<td colspan="2">${alias}</td>
						<th>관리번호</th>
						<td colspan="2" id="ftrIdn">${row.ftrIdn}</td>
					</tr>
					<tr>
						<th>기준점명</th>
						<td colspan="2">
							<input type="text" class="easyui-textbox" name="bseNam" style="width:99%" value="${row.bseNam}"/>						
						</td>
						<th>원점</th>
						<td colspan="2">
							<input type="text" class="easyui-textbox" name="pntNam" style="width:99%" value="${row.pntNam}"/>						
						</td>
					</tr>
					<tr>
						<th rowspan="2">지역좌표계</th>
						<th>종선좌표(X)</th>
						<td colspan="4">
							<input type="text" class="easyui-numberbox" name="nbgX" style="width:99%" value="${row.nbgX}"/>						
						</td>
					</tr>
					<tr>
						<th>횡선좌표(Y)</th>
						<td colspan="4">
							<input type="text" class="easyui-numberbox" name="nbgY" style="width:99%" value="${row.nbgY}"/>						
						</td>
					</tr>
					<tr>
						<th rowspan="2">세계측지계</th>
						<th>종선좌표(X)</th>
						<td colspan="4">
							<input type="text" class="nggX easyui-numberbox" name="nggX" style="width:99%" value="${row.nggX}"/>						
						</td>
					</tr>
					<tr>
						<th>횡선좌표(Y)</th>
						<td colspan="4">
							<input type="text" class="nggY easyui-numberbox" name="nggY" style="width:99%" value="${row.nggY}"/>						
						</td>
					</tr>
					<tr>
						<th>위도(B)</th>
						<td colspan="2">
							<input type="text" class="easyui-textbox" name="ngwB" style="width:99%" value="${row.ngwB}"/>						
						</td>
						<th>경도(L)</th>
						<td colspan="2">
							<input type="text" class="easyui-textbox" name="ngwL" style="width:99%" value="${row.ngwL}"/>						
						</td>
					</tr>
					<tr>
						<th>표고</th>
						<td colspan="2">
							<input type="text" class="easyui-numberbox" name="bseHgt" style="width:99%" value="${row.bseHgt}"/>						
						</td>
						<th>자오선수차</th>
						<td colspan="2">
							<input type="text" class="easyui-textbox" name="merCon" style="width:99%" value="${row.merCon}"/>						
						</td>
					</tr>
					<tr>
						<th>토지소재지</th>
						<td colspan="5">
							<input type="text" class="easyui-textbox" name="setLoc" style="width:100%" value="${row.setLoc}"/>
						</td>
					</tr>
					<tr>
						<th>설치일자</th>
						<td>
							<input type="text" class="w_110 easyui-datebox" name="setYmd" value="${row.setYmd}"/>
						</td>
						<th>설치구분</th>
						<td>
							<select class="w_110 easyui-combobox" name="setCde" path="setCde">
								<c:forEach var="cdeList" items="${codeList}">
                                     <option value="${cdeList.codeId}" ${cdeList.codeId==row.setCde ? 'selected' : ''}>${cdeList.codeNm}</option>
								</c:forEach>
							</select>
						</td>
						<th>표지재질</th>
						<td>
							<input type="text" class="W_110 easyui-textbox" name="setMet" value="${row.setMet}"/>
						</td>
					</tr>
					<tr>
						<th>비고</th>
						<td colspan="5">
							<input type="text" class="easyui-textbox" name="rmkDes" style="width:99%" value="${row.rmkDes}"/>
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