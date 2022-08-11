<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<div id="searchExplant" class="window_container">
	<div class="window_article">
		<table class="table_text">
			<tbody>
				<tr>
					<th>대표지번</th>
						<td colspan="5">
							<span>
								<select class="w_190 easyui-combobox" id="selDong" name="selDong"></select>
							</span>
							산 
							<span>
								<input class="easyui-switchbutton" id="checkMauntain" name="checkMauntain"/>
							</span>
							<span>
								<input type="text" class="w_110 easyui-numberspinner" name="mainNum" id="mainNum" path="mainNum"/>
							</span>
							-
							<span>
								<input type="text" class="w_110 easyui-numberspinner" name="subNum" id="subNum" path="subNum"/>
							</span>
						</td>
					</td>
				</tr>	
				
			</tbody>
		</table>
	</div>
	<div class="window_footer">
		<div class="button_flat">
			<button class="a_search button_flat_normal btn_blue">검색</button>
			<button class="a_close button_flat_normal btn_blue">닫기</button>
		</div>
	</div>
</div>
