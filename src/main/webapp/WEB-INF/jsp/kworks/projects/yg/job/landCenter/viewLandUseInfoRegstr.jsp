<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn"			uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="window_container">
	<div class="window_article">
		<input type="hidden" id="luiIdn" value="${result.luiIdn}" />
		<input type="hidden" id="cntIdn" value="${result.cntIdn}" />
		<input type="hidden" id="pnu" value="${result.pnu}" />
		<table class="table_text">
			<tbody>
				<tr>
					<th>사용정보번호</th>
					<td>
						${result.luiIdn}
					</td>
				</tr>
				<tr>	
					<th>공사번호</th>
					<td>
						${result.cntIdn}
					</td>			
				</tr>
				<tr>
					<th>주소</th>
					<td id="useInfoAddress">
					</td>
				</tr>
				<tr>
					<th>전체면적</th>
					<td>
						${result.totArea}
					</td>
				</tr>
				<tr>
					<th>편입면적</th>
					<td>
						${result.incArea}
					</td>
				</tr>
				<tr>
					<th>소유자</th>
					<td>
						${result.ownNam}
					</td>
				</tr>
				<tr>
					<th>생년월일</th>
					<td>
						${result.ownYmd}
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="window_footer">
		<div class="button_flat_l">
			<a id="a_writtenConsentOfUse" href="#">
				<button class="btn_landUseInfo_writtenConsentOfUse button_flat_normal f_l btn_blue" id="agr-file-nm">사용승낙서</button>
			</a>
			<!-- <a id="a_sealImpression" href="/">
				<button class="btn_landUseInfo_sealImpression button_flat_normal f_l btn_blue" id="reg-file-nm" value="">인감증명서</button>
			</a>	
			<a id="a_siteMap" href="/">
				<button class="btn_landUseInfo_siteMap button_flat_normal f_l btn_blue" id="sit-file-nm" value="">용지도</button>
			</a>	
			<a id="a_etc" href="/">
				<button class="btn_landUseInfo_etc button_flat_normal f_l btn_blue" id="etc-file-nm" value="">기타</button>
			</a> -->
		</div>
		<div class="button_flat">
			<button class="btn_modify_landUseInfoRegstrDetail button_flat_normal btn_blue">편집</button>
			<button class="btn_delete_landUseInfoRegstrDetail button_flat_normal btn_blue">삭제</button>
			<button class="btn_close_landUseInfoRegstrDetail button_flat_normal btn_blue">닫기</button>
		</div>
	</div>
</div>
