<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" 		uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- project -->
<c:set var="prjCode"><spring:message code='Globals.Prj' /></c:set>
	
<c:choose>
	<c:when test="${prjCode eq 'yy' }">
			<div class="div_window_kras" style="height:578px;">
	</c:when>
	<c:otherwise>
			<div class="div_window_kras">
	</c:otherwise>
</c:choose>

	<div class="div_loading">
		<img src="<c:url value='/images/kworks/common/contact-loading.gif' />" alt="로딩중" title="로딩중" />
	</div>

	<ul class="div_kras_tab_list tabList">
		<c:choose>
			<c:when test="${prjCode eq 'yy' }">
				<!-- 양양군 요구사항 기본정보 탭 제거 -->
				<li class="first"><a href="#" data-div-id="div_kras_land_info" >토지정보</a></li>
				<li><a href="#" data-div-id="div_kras_jiga_info" >개별공시지가</a></li>
				<li><a href="#" data-div-id="div_kras_plan_info" >토지이용계획</a></li>
				<li class="bldg_info"><a href="#" data-div-id="div_kras_buld_info" >건물정보</a></li>
				<li class="li_bildngPrmisn"><a href="#" data-div-id="div_eais_bildngPrmisn_info" >건축허가대장</a></li>
			</c:when>
			<c:when test="${prjCode eq 'gn' }">
				<!-- 강릉시 요구사항 기본정보 탭 제거 -->
				<li class="first"><a href="#" data-div-id="div_kras_land_info" >토지정보</a></li>
				<li><a href="#" data-div-id="div_kras_jiga_info" >개별공시지가</a></li>
				<li><a href="#" data-div-id="div_kras_plan_info" >토지이용계획</a></li>
				<li class="bldg_info"><a href="#" data-div-id="div_kras_buld_info" >건물정보</a></li>
				<li class="li_bildngPrmisn"><a href="#" data-div-id="div_eais_bildngPrmisn_info" >건축허가대장</a></li>
			</c:when>
			<c:otherwise>
				<li class="first" ><a href="#" data-div-id="div_kras_bass_info" >기본정보</a></li>
				<li><a href="#" data-div-id="div_kras_land_info" >토지정보</a></li>
				<li><a href="#" data-div-id="div_kras_jiga_info" >개별공시지가</a></li>
				<li><a href="#" data-div-id="div_kras_plan_info" >토지이용계획</a></li>
				<li class="bldg_info"><a href="#" data-div-id="div_kras_buld_info" >건물정보</a></li>
				<li class="li_bildngPrmisn"><a href="#" data-div-id="div_eais_bildngPrmisn_info" >건축허가대장</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
	
	<!-- 기본정보  -->
	<div id="div_kras_bass_info" class="tabCont">
		
		<table class="cmmTable v3 mod ma_b_30" summary="토지정보 테이블">
			<caption>토지정보 테이블</caption>
			<colgroup>
				<col width="15%">
				<col width="15%">
				<col width="">
				<col width="15%">
				<col width="">
				<col width="15%">
				<col width="">
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">토지소재지</th>
					<td colspan="6" class="landLoc"></td>
				</tr>
				<tr>
					<th scope="row">토지</th>
					<th scope="row">지목</th>
					<td class="jimokNm"></td>
					<th scope="row">면적(㎡)</th>
					<td class="parea"></td>
					<th scope="row">개별공시지가(원)</th>
					<td class="pannJiga"></td>
				</tr>
			</tbody>
		</table>
		
		<p class="subTitle bldg_info"><span>건축물정보</span></p>
		<table class="cmmTable v3 ma_b_30 bldg_info" summary="건물정보 테이블">
			<caption>건물정보 테이블</caption>
			<colgroup>
				<col width="" />
				<col width="" />
				<col width="" />
				<col width="" />
				<col width="" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">대장종류</th>
					<th scope="col">건물번호</th>
					<th scope="col">건축물명칭</th>
					<th scope="col">동명칭</th>
					<th scope="col">연면적(㎡)</th>
				</tr>
			</thead>
			<tbody class="bldgDongInfos">
			</tbody>
		</table>
	</div>
	
	<!-- 토지정보  -->
	<div id="div_kras_land_info" class="tabCont">
		<table class="cmmTable v4 mod" summary="토지정보 테이블">
			<caption>토지정보 테이블</caption>
			<colgroup>
				<col width="20%">
				<col width="">
				<col width="20%">
				<col width="">
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">토지소재지</th>
					<td colspan="3" class="landLoc"></td>
				</tr>
				<tr>
					<th scope="row">지목</th>
					<td class="jimokNm"></td>
					<th scope="row">면적(㎡)</th>
					<td class="parea"></td>
				</tr>
				<tr>
					<th scope="row">토지등급</th>
					<td class="grd"></td>
					<th scope="row">등급변동일자</th>
					<td class="grdYmd"></td>
				</tr>
				<tr>
					<th scope="row">이동사유</th>
					<td class="landMovRsnCdNm"></td>
					<th scope="row">이동일자</th>
					<td class="landMovYmd"></td>
				</tr>
			</tbody>
		</table>
		<table class="cmmTable v3 mod bd_n" summary="토지정보 소유자 테이블">
			<caption>토지정보 소유자 테이블</caption>
			<colgroup>
				<col width="">
				<col width="">
				<col width="">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">소유구분</th>
					<th scope="col">소유자명</th>
					<th scope="col">공유인수</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="ownGbnNm"></td>
					<td class="ownerNm"></td>
					<td class="shrCnt"></td>
				</tr>
			</tbody>
		</table>
		
		<c:if test="${ prjCode eq 'ss' or prjCode eq 'yy'}">
		<!-- 토지이동연혁 -->
		<p class="subTitle land_mov_hist"><span>토지이동연혁</span></p>
		<table class="cmmTable v3 mod ma_b_30 land_mov_hist" summary="토지이동연혁 테이블">
			<caption>토지이동연혁 테이블</caption>
			<colgroup>
				<col width="">
				<col width="">
				<col width="">
				<col width="">
				<col width="">
			</colgroup>
			<tbody class="tby_land_mov_hist_list">
				<tr>
					<th scope="row">연혁순번</th>
					<th scope="row">이동일자</th>
					<th scope="row">지목</th>
					<th scope="row">면적</th>
					<th scope="row">토지이동사유명</th>
				</tr>
			</tbody>
		</table>
		</c:if>
	</div>
	
	<!-- 개별공시지가  -->
	<div id="div_kras_jiga_info" class="tabCont">
		<table class="cmmTable v3 mod" summary="개별공시지가 테이블">
			<caption>개별공시지가 테이블</caption>
			<colgroup>
				<col width="20%">
				<col width="20%">
				<col width="20%">
				<col width="20%">
			</colgroup>
			<tbody class="tby_jiga_list">
				<tr>
					<th scope="row">토지소재지</th>
					<td colspan="3" class="left landLoc"></td>
				</tr>
				<tr>
					<th scope="row">연도</th>
					<th scope="row">기준월</th>
					<th scope="row">지가(원)</th>
					<th scope="row">공시일자</th>
				</tr>
			</tbody>
		</table>
	</div>
	
	<!-- 토지이용계획 -->
	<div id="div_kras_plan_info" class="tabCont">
		<table class="cmmTable v4 mod" summary="토지이용계획 테이블">
			<caption>토지이용계획 테이블</caption>
			<colgroup>
				<col width="15%">
				<col width="30%">
				<col width="15%">
				<col width="40%">
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">토지소재지</th>
					<td class="landLoc"></td>
					<th scope="row">면적(㎡)</th>
					<td class="plan_parea"></td>
				</tr>
				<tr>
					<th scope="row" rowspan="2">지역&#183;지구 등 지정여부</th>
					<th scope="row">「국토의 계획 및 이용에 관한 법률」<br />에 따른 지역&#183;지구 등</th>
					<td colspan="2" class="plan_uselawA" ></td>
				</tr>
				<tr>
					<th scope="row">다른 법령 등에 따른 지역&#183;지구 등</th>
					<td colspan="2" class="plan_uselawB" ></td>
				</tr>
				<tr>
					<th scope="row" colspan="2">「토지이용규제 기본법 시행령」<br />제9조제4항 각 호에 해당되는 사항</th>
					<td colspan="2"class="plan_uselawD" ></td>
				</tr>
				<tr>
					<th scope="row" colspan="2">추가기재</th>
					<td colspan="2" class="plan_uselawC" ></td>
				</tr>
				<tr>
					<th scope="row">확인도면</th>
					<td colspan="2">
						<img class="img_land_view_img" alt="도면" src="<c:url value='/images/kworks/map/blank.gif' />" />
					</td>
					<td class="va_t">
						<div class="t_a_c"><span>범례</span></div>
						<ul class="ul_land_view_legend">
						</ul>
						<div class="ma_t_8 t_a_c">
							축척 1 /
							<input class="txt_land_view_scale" type="text" value="500" />
							<a class="a_apply_land_view_scale" href="#">
								<img src="<c:url value='/images/kworks/map/common/btn_set.png' />" alt="적용" />
							</a>
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row">지역&#183;지구 등 안에서의 행위제한내용</th>
					<td colspan="3" class="ucodes">
						<a href="#" class="a_load_ucodes">행위제한 내용 불러오기</a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<!-- 건물 정보 -->
	<div id="div_kras_buld_info" class="tabCont">
		<table class="cmmTable v3 ma_b_30" summary="건물정보 테이블">
			<caption>건물정보 테이블</caption>
			<colgroup>
				<col width="" />
				<col width="" />
				<col width="" />
				<col width="" />
				<col width="" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">대장종류</th>
					<th scope="col">건물번호</th>
					<th scope="col">건축물명칭</th>
					<th scope="col">동명칭</th>
					<th scope="col">연면적(㎡)</th>
				</tr>
			</thead>
			<tbody class="tby_kras_dong_info" >
			</tbody>
		</table>
		
		<p class="subTitle"><span>건축물정보</span></p>
		<table class="tbl_kras_bldg_info cmmTable v2 ma_b_30" summary="건축물정보 테이블">
			<caption>건축물정보 테이블</caption>
			<colgroup>
				<col width="17%">
				<col width="15%">
				<col width="20%">
				<col width="15%">
				<col width="16%">
				<col width="20%">
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">대지위치</th>
					<td colspan="3" class="landLoc" ></td>
					<th scope="row">지번</th>
					<td class="jibun" ></td>
				</tr>	
				<tr>
					<th scope="row">대지면적(㎡)</th>
					<td class="larea" ></td>
					<th scope="row">연면적(㎡)</th>
					<td class="garea" ></td>
					<th scope="row">명칭 및 번호</th>
					<td class="bldgNm" ></td>
				</tr>	
				<tr>
					<th scope="row">건축면적(㎡)</th>
					<td class="barea" ></td>
					<th scope="row">용적률 산정용 연면적(㎡)</th>
					<td class="fsiCalcGarea" ></td>
					<th scope="row">건축물수</th>
					<td class="totMainBldgCnt" ></td>
				</tr>	
				<tr>
					<th scope="row">건폐율</th>
					<td class="dongBlr blr" ></td>
					<th scope="row">용적률</th>
					<td class="fsi" ></td>
					<th scope="row">총호수</th>
					<td class="hoCnt totHoCnt" ></td>
				</tr>	
				<tr>
					<th scope="row">주용도</th>
					<td class="mainUseNm" ></td>
					<th scope="row">주구조</th>
					<td class="struNm" ></td>
					<th scope="row">부속건축물</th>
					<td class="subBldgCnt" ></td>
				</tr>	
				<tr>
					<th scope="row">허가일자</th>
					<td class="permYmd" ></td>
					<th scope="row">착공일자</th>
					<td class="bgconsYmd" ></td>
					<th scope="row">사용승인일자</th>
					<td class="useAprvYmd" ></td>
				</tr>	
				<tr>
					<th scope="row">위반건축물 여부</th>
					<td class="vioBldgYn" ></td>
					<th scope="row">특이사항</th>
					<td colspan="3" class="spcItem" ></td>
				</tr>	
			</tbody>
		</table>
		
		<ul class="bldgTabList tabList">
			<li class="first" ><a href="#" data-div-id="div_kras_buld_chg" >변동현황</a></li>
			<li><a href="#" data-div-id="div_kras_buld_floor" >층별현황</a></li>
		</ul>
		
		<!-- 변동현황  -->
		<div id="div_kras_buld_chg" class="bldgTabCont on">
			<table class="cmmTable v3 ma_b_30 mod" summary="건물정보 테이블">
				<caption>건물정보 테이블</caption>
				<colgroup>
					<col width="" />
					<col width="" />
					<col width="" />
					<col width="" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col">변동원인</th>
						<th scope="col">변동내역</th>
						<th scope="col">변동일자</th>
						<th scope="col">정리일자</th>
					</tr>
				</thead>
				<tbody class="tby_kras_bldg_chg_info">
				</tbody>
			</table>
		</div>
		
		<!-- 층별현황  -->
		<div id="div_kras_buld_floor" class="bldgTabCont">
			<table class="cmmTable v3 ma_b_30 mod" summary="건물정보 테이블">
				<caption>건물정보 테이블</caption>
				<colgroup>
					<col width="" />
					<col width="" />
					<col width="" />
					<col width="" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col">층별</th>
						<th scope="col">구조</th>
						<th scope="col">용도</th>
						<th scope="col">면적(㎡)</th>
					</tr>
				</thead>
				<tbody class="tby_kras_bldg_floor_info">
				</tbody>
			</table>
		</div>
	</div>
	
	<!-- 건축허가대장 정보 -->
	<div id="div_eais_bildngPrmisn_info" class="tabCont">
		<table class="cmmTable v3 ma_b_30" summary="건축허가대장 리스트 테이블">
			<caption>건축허가대장 리스트 테이블</caption>
			<colgroup>
				<col width="" />
				<col width="" />
				<col width="" />
				<col width="" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">허가대장관리번호</th>
					<th scope="col">허가년도(연도)</th>
					<th scope="col">허가번호(기관)</th>
					<th scope="col">허가번호(구분)</th>
				</tr>
			</thead>
			<tbody class="tby_eais_bildngPrmisn_info" >
			</tbody>
		</table>
	
		<p class="subTitle"><span>건축허가대장정보</span></p>
		<table class="tbl_eais_bildngPrmisn_info cmmTable v3 mod ma_b_30" summary="건축허가대장정보 테이블">
			<caption>건축허가대장정보 테이블</caption>
			<colgroup>
				<col width="16%">
				<col width="17%">
				<col width="16%">
				<col width="17%">
				<col width="16%">
				<col width="*">
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">법정동</th>
					<td class="landLoc"></td>
					<th scope="row">지번</th>
					<td class="jibun"></td>
					<th scope="row">대지구분</th>
					<td class="platGbCdNm"></td>
				</tr>
				<tr>
					<th scope="row">허가대장관리번호</th>
					<td class="pmsrgstPk"></td>
					<th scope="row">허가번호<br>(구분)</th>
					<td class="pmsnoGbCdNm"></td>
					<th scope="row">허가신고구분</th>
					<td class="pmsDeclGbCdNm"></td>
				</tr>
				<tr>
					<th scope="row">허가번호<br>(연도)</th>
					<td class="pmsnoYear"></td>
					<th scope="row">허가번호<br>(기관)</th>
					<td class="pmsnoKikCdNm"></td>
					<th scope="row">허가번호<br>(일련번호)</th>
					<td class="pmsnoSeqno"></td>
				</tr>
				<tr>
					<th scope="row">건축구분</th>
					<td class="archGbCdNm"></td>
					<th scope="row">건축허가일</th>
					<td class="archPmsDay"></td>
					<th scope="row">설계변경차수</th>
					<td class="dsgnChangOdr"></td>
				</tr>
				<tr>
					<th scope="row">대지면적</th>
					<td class="platArea"></td>
					<th scope="row">전용면적</th>
					<td class="exuseArea"></td>
					<th scope="row">허가취소여부</th>
					<td class="pmsCnclYnNm"></td>
				</tr>
				<tr>
					<th scope="row">허가취소일</th>
					<td class="pmsCnclDay"></td>
					<th scope="row">취소사유</th>
					<td colspan="3" class="cnclCaus"></td>
				</tr>
				<tr>
					<th scope="row">착공예정일</th>
					<td class="stcnsSchedDay"></td>
					<th scope="row">착공구분</th>
					<td class="stcnsGbCdNm"></td>
					<th scope="row">착공일</th>
					<td class="realStcnsDay"></td>
				</tr>
				<tr>
					<th scope="row">착공연기일</th>
					<td class="stcnsDelayDay"></td>
					<th scope="row">착공연기사유</th>
					<td colspan="3" class="stcnsDelayCaus"></td>
				</tr>
				<tr>
					<th scope="row">사용승인구분</th>
					<td class="useaprGbCdNm"></td>
					<th scope="row">전체사용승인여부</th>
					<td class="entirUseaprYn"></td>
					<th scope="row">사용승인일</th>
					<td class="useaprDay"></td>
				</tr>
				<tr>
					<th scope="row">임시사용차수</th>
					<td class="tmpUseOdr"></td>
					<th scope="row">임시사용일련번호</th>
					<td class="tmpUseSeqno"></td>
					<th scope="row">임시사용승인만료</th>
					<td class="tmpUseaprExpDay"></td>
				</tr>
				<tr>
					<th scope="row">가설건축물<br>존치만료일</th>
					<td class="tmpbldPrsvExpDay"></td>
					<th scope="row">가설건축물<br>연장차수</th>
					<td class="tmpbldExtndOdr"></td>
					<th scope="row">가설건축물처리일</th>
					<td class="tmpbldTrsctDay"></td>
				</tr>
				<tr>
					<th scope="row">건축면적</th>
					<td class="archArea"></td>
					<th scope="row">연면적</th>
					<td class="totarea"></td>
					<th scope="row">주건축물수</th>
					<td class="mainBldCnt"></td>
				</tr>
				<tr>
					<th scope="row">부속건축물동수</th>
					<td class="atchBldDongCnt"></td>
					<th scope="row">부속건축물면적</th>
					<td class="atchBldArea"></td>
					<th scope="row">주용도</th>
					<td class="mainPurpsCdNm"></td>
				</tr>
				<tr>
					<th scope="row">세대수</th>
					<td class="hhldCnt"></td>
					<th scope="row">가구수</th>
					<td class="fmlyCnt"></td>
					<th scope="row">호수</th>
					<td class="hoCnt"></td>
				</tr>
				<tr>
					<th scope="row">기타용도</th>
					<td colspan="5" class="etcPurpsNm"></td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<p class="btnRight">
		<a class="land_print btn_print4" href="#"></a>
		<a class="a_close btn_close" href="#" ></a>
	</p>
</div>