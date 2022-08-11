<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 		uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"	uri="http://www.springframework.org/tags/form" %>

<div id="searchBeachErosion" class="window_container">
	<div class="window_article">
		<table class="table_text">
			<tbody>
				<tr>
					<th>관측년도</th>
					<td>
						<select class="mesrYy" name="mesrYy">
						</select>
					</td>
					<th>대상지역</th>
					<td class="w_205">
						<select class="zoneNm" name="zoneNm">
						</select>
					</td>
				</tr>
				<tr>
					<th>관측시작월</th>
					<td>
						<select class="bganMt" name="bganMt">
						</select>
					</td>
					<th>관측종료월</th>
					<td>
						<select class="edanMt" name="edanMt">
						</select>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="window_footer">
			<div class="button_flat">
				<button class="a_reset button_flat_normal btn_blue">초기화</button>
				<button class="a_search button_flat_normal btn_blue">검색</button>
			</div>
		</div>
	</div>
</div>