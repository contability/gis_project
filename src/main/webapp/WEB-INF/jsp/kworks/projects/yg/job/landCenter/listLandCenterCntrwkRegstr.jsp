<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<div id="listLandCenterCntrwkRegstr" class="window_container">
	<div class="window_article">
		<table class="table_text">
			<tbody>
				<tr>
					<th>공사명</th>
					<td>
						<input type="text" class="easyui-textbox" name="cntNam" path="cntNam" style="width: 95%;"/>
					</td>
					<th>공사번호</th>
					<td>
						<input type="text" class="easyui-numberbox" name="cntIdn" path="cntIdn" style="width: 95%;"/>
					</td>			
				</tr>
				<tr>
					<th>계약일</th>
					<td>
						<input type="text" class="w_84 easyui-datebox" name="cttYmdMin" path="cttYmdMin" />
						<span class="space">~</span>
						<input type="text" class="w_84 easyui-datebox" name="cttYmdMax" path="cttYmdMax" />
					</td>
					<th>준공일</th>
					<td>
						<input type="text" class="w_84 easyui-datebox" name="rfnYmdMin" path="rfnYmdMin" />
						<span class="space">~</span>
						<input type="text" class="w_84 easyui-datebox" name="rfnYmdMax" path="rfnYmdMax" />	
					</td>
				</tr>
				<tr>
					<th>대표지번</th>
					<td colspan="3">
						<select name="bjdCde" id="bjdCde" class="sel_dong easyui-combobox" style="width:40%">
							<c:forEach items="${selDong}" var="selDongObj" varStatus="status">
								<option value="${selDongObj.codeId}">${selDongObj.codeNm}</option>
							</c:forEach>
						</select>
						산 <input type="checkbox" class="chk_mntn" name="sanCde" id="div_quick_search_legal_dong_mntn" />
						지번 <input type="text" class="txt_mnnm easyui-numberbox" name="facNum" path="txtMnnm" style="width:70px"/>
						- <input type="text" class="txt_slno easyui-numberbox" name="fadNum" path="txtSlno"style="width:70px"/>
					</td>
					<tr>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="window_footer">
		<div class="button_flat">
			<button class="a_reset_landCenterCntrwkRegstr button_flat_normal btn_blue">초기화</button>
			<button class="a_search_landCenterCntrwkRegstr button_flat_normal btn_blue">검색</button>
		</div>
	</div>
</div>
