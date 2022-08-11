<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form id="addCityPlanRoadform" name="addCityPlanRoadform" method="post" enctype="multipart/form-data">
	<div id="addCityPlanRoad" class="window_container">
		<div class="window_article">
			<input type="hidden" name="uprIdn" value="${uprIdn}" />
			<table id="tableCityPlanRoad" class="table_text">
				<tbody>
					<tr>
						<th>관리번호</th>
						<td colspan="5">${uprIdn}</td>
					</tr>
					<tr>
						<th>도시</th>
						<td colspan="2">
							<select id="uprBjd" class="w_200 easyui-combobox" name="uprBjd">
								<option value="42150">강릉시</option>
							</select>						
						</td>
						<th>구역명</th>
						<td colspan="2">
							<input type="text" class="easyui-textbox" name="uprNam" style="width:99%" value=""/>						
						</td>
					</tr>					
					<tr>
						<th>등급</th>
						<td>
							<select id="uprGrd" class="w_100 easyui-combobox" name="uprGrd">
								<option value="UPR001">광로</option>
								<option value="UPR002">대로</option>
								<option value="UPR003">중로</option>
								<option value="UPR004">소로</option>
							</select>
						</td>
						<th>류별</th>
						<td>
							<select id="uprTyp" class="w_100 easyui-combobox" name="uprTyp">
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
							</select>
						</td>
						<th>번호</th>
						<td>
							<input type="text" class="w_100 easyui-textbox" name="uprNum"/>						
						</td>
					</tr>
					<tr>
						<th>폭원(m)</th>
						<td colspan="2">
							<input type="text" class="easyui-textbox" name="uprWid" style="width:99%" value=""/>						
						</td>
						<th>연장(m)</th>
						<td colspan="2">
							<input type="text" class="easyui-textbox" name="uprLen" style="width:99%" value=""/>						
						</td>
					</tr>
					<tr>
						<th>기점</th>
						<td colspan="2">
							<input type="text" class="easyui-textbox" name="uprStr" style="width:99%" value=""/>						
						</td>
						<th>종점</th>
						<td colspan="2">
							<input type="text" class="easyui-textbox" name="uprEnd" style="width:99%" value=""/>						
						</td>
					</tr>
					<tr>
						<th>기능</th>
						<td colspan="2">
							<input type="text" class="easyui-textbox" name="uprFcn" style="width:99%" value=""/>						
						</td>
						<th>사용형태</th>
						<td colspan="2">
							<input type="text" class="easyui-textbox" name="uprUty" style="width:99%" value=""/>						
						</td>
					</tr>
					<tr>
						<th>최초고시</th>
						<td colspan="3">
							<input type="text" class="easyui-textbox" name="strNtc" style="width:99%" value=""/>						
						</td>
						<th>최초고시일</th>
						<td>
							<input type="text" class="w_110 easyui-datebox"	name="strYmd" value="" />						
						</td>
					</tr>
					<tr>
						<th>최종변경고시</th>
						<td colspan="3">
							<input type="text" class="easyui-textbox" name="lstNtc" style="width:99%" value=""/>						
						</td>
						<th>최종변경고시일</th>
						<td>
							<input type="text" class="w_110 easyui-datebox"	name="lstYmd" value="" />						
						</td>
					</tr>
					<tr>
						<th>인가고시</th>
						<td colspan="3">
							<input type="text" class="easyui-textbox" name="pmtNtc" style="width:99%" value=""/>						
						</td>
						<th>인가고시일</th>
						<td>
							<input type="text" class="w_110 easyui-datebox"	name="pmtYmd" value="" />						
						</td>
					</tr>
					<tr>
						<th>최종변경고시</th>
						<td colspan="3">
							<input type="text" class="easyui-textbox" name="lstNtc" style="width:99%" value=""/>						
						</td>
						<th>최종변경고시일</th>
						<td>
							<input type="text" class="w_110 easyui-datebox"	name="lstYmd" value="" />						
						</td>
					</tr>
					<tr>
						<th>폐지고시</th>
						<td colspan="3">
							<input type="text" class="easyui-textbox" name="disNtc" style="width:99%" value=""/>						
						</td>
						<th>폐지고시일</th>
						<td>
							<input type="text" class="w_110 easyui-datebox"	name="disYmd" value="" />						
						</td>
					</tr>
					<tr>
						<th>비고</th>
						<td colspan="5">
							<input type="text" class="easyui-textbox" name="rmkExp" style="width:99%" value=""/>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="window_footer">
			<div class="button_flat">
				<button id="btn_insert" class="button_flat_normal btn_grey">등록</button>
				<button id="btn_close" class="button_flat_normal btn_black">닫기</button>
			</div>
		</div>
	</div>
</form>