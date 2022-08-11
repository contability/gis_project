<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<div id="searchControlPoint" class="window_container">
	<div class="window_article">
		<table class="table_text">
			<tbody>
				<tr>
					<th>기준점명</th>
					<td class="w_110">
						<input type="text" class="txt_bse_nam easyui-textbox" name="bseNam" style="width:99%"/>
					</td>
					<th>설치일자</th>
					<td>
						<input type="text" class="txt_str_ymd w_100 easyui-datebox"	name="strYmd"/>
						~					
						<input type="text" class="txt_end_ymd w_100 easyui-datebox"	name="endYmd"/>
					</td>
				</tr>
				<tr>
					<th>토지소재지</th>
					<td colspan="4">
						<input type="text" class="txt_set_loc easyui-textbox" name="setLoc" style="width:99%"/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="window_footer">
		<div class="button_flat">
			<button class="a_reset button_flat_normal btn_blue">초기화</button>
			<button class="a_search button_flat_normal btn_blue">검색</button>
		</div>
	</div>
</div>