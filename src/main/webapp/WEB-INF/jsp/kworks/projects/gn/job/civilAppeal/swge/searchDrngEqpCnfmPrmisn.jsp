<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 		uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"	uri="http://www.springframework.org/tags/form" %>

<div id="searchDrngEqpCnfmPrmisn" class="window_container">
	<div class="window_article">
		<table class="table_text">
			<tbody>
				<tr>
					<th>소재지</th>
					<td colspan="3">
						<form:select class="sel_dong easyui-combobox w_160" path="rpstBjdcd" >
							<form:options items="${rpstBjdcd}" itemValue="codeId" itemLabel="codeNm" />
						</form:select>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						산 <input type="checkbox" class="chk_mntn"  />
						&nbsp;&nbsp;&nbsp;&nbsp;
						지번
						&nbsp;
						<input type="text" class="txt_mnnm easyui-numberbox w_70"  />
						&nbsp;-&nbsp;
						<input type="text" class="txt_slno easyui-numberbox w_70"  />
					</td>
				</tr>
				<tr>
					<th>민원종류</th>
					<td>
						<form:select class="pmsCde easyui-combobox w_160"  path="pmsCde" >
							<form:option value="" label="전체"/>
							<form:options items="${pmsCde}" itemValue="codeId" itemLabel="codeNm" />
						</form:select>
					</td>
					<th>건축주</th>
					<td>
						<input type="text" class="budNam easyui-textbox w_160" " />
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="window_footer">
		<div class="button_flat">
			<button class="a_reset_DrngEqpCnfmPrmisnSearch button_flat_normal btn_blue">초기화</button>
			<button class="a_search_DrngEqpCnfmPrmisnSearch button_flat_normal btn_blue">검색</button>
		</div>
	</div>
</div>
