<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="window_container">
	<div class="window_article">
		<input type="hidden" id="drngEqpCnfmPrmisnDetailFtrCde" value="${result.ftrCde}" />
		<input type="hidden" id="drngEqpCnfmPrmisnDetailFtrIdn" value="${result.ftrIdn}" />
		<input type="hidden" id="drngEqpCnfmPrmisnDetailPmsCde" value="${result.pmsCde}" />
		<table class="table_text">
			<tbody>
				<tr>
					<th>관리번호</th>
						<td>${result.ftrIdn}</td>
					</tr>
			</tbody>
		</table>
		<div class="tabs-1">
			<ul class="tab-list-1">
				<li class="active"><a class="tab-control" href="#list_tab1">인허가사항</a></li>
				<li><a class="tab-control" href="#list_tab2">협의내용</a></li>
				<li><a class="tab-control" href="#list_tab3">배수시설</a></li>
				<li><a class="tab-control" href="#list_tab4">첨부문서</a></li>
			</ul>
		</div>
		<div class="tab-panel-1 active" id="list_tab1" style="height:244px">
			<table class="table_text">
				<tbody>
					<tr>
						<th>신고일</th>
						<td colspan="2">
							${result.lprDate}
						</td>
						<th>건축주</th>
						<td colspan="2">
							${result.budNam}
						</td>	
					</tr>
					<tr>
						<th>대표지번</th>
						<td colspan="5">
							${result.rpst}
						</td>
					</tr>
					<tr>
						<th>소재지</th>
						<td colspan="5">
							${result.bildLcplc}
						</td>
					</tr>
					<tr>
						<th>도로명주소</th>
						<td colspan="5">
							${result.bildRnadr}
						</td>
					</tr>
					<tr>
						<th>연면적(m<sup>2</sup>)</th>
						<td>
							${result.bldAra}
						</td>
						<th>신청면적(m<sup>2</sup>)</th>
						<td>
							${result.reqstAr}
						</td>
						<th>오수량(ton)</th>
						<td>
							${result.sdrVol}
						</td>
					</tr>
					<tr>
						<th>민원종류</th>
						<td colspan="3">
							${result.pms}
						</td>
						<th>원인자부담금(원)</th>
						<td>
							<fmt:formatNumber value="${result.csAlotm}" type="currency" currencySymbol="" />
						</td>
					</tr>
					<tr>
						<th>주용도</th>
						<td colspan="5">
							${result.bldUse}
						</td>
					</tr>
					<tr>
						<th>착공일</th>
						<td colspan="2">
							${result.begDate}
						</td>
						<th>준공일</th>
						<td colspan="2">
							${result.cntDate}
						</td>	
					</tr>
					<tr>
						<th>비고</th>
						<td colspan="5">
							${result.rmkExp}
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="tab-panel-1" id="list_tab2" style="height:244px">
			<table class="table_text">
				<tbody>
					<tr>
						<th>협의내용</th>
						<td colspan="5">
							${result.dscss}
						</td>
					</tr>
					<tr>
						<th>협의내용기타</th>
						<td colspan="5">
							${result.dscssEtc}
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="tab-panel-1" id="list_tab3" style="height:244px">
			<table class="table_text">
				<tbody>
					<tr>
						<th>시공자</th>
						<td colspan="5">
							${result.makNam}
						</td>	
					</tr>
					<tr>
						<th>관종류</th>
						<td colspan="5">
							${result.mop}
						</td>
					</tr>
					<tr>
						<th>관경(mm)</th>
						<td colspan="2">
							${result.dia}
						</td>
						<th>연장(m)</th>
						<td colspan="2">
							${result.pipLen}
						</td>	
					</tr>
					<tr>
						<th>접합방법</th>
						<td colspan="5">
							${result.cnMth}
						</td>
					</tr>
				</tbody>
			</table>
			<table class="table_text tableSelector_drngEqpList">
				<tbody>
					<tr>
						<td>구분</td>
						<td>시설종류</td>
						<td>관리번호</td>
					</tr>
					<c:forEach var="drngEqpListObj" items="${drngEqpList}">
						<tr data-data-id="${drngEqpListObj.dataId}" data-objectid="${drngEqpListObj.objectid}">
							<td>${drngEqpListObj.dataAlias}</td>
							<td>${drngEqpListObj.prpos}</td>
							<td>${drngEqpListObj.ftrIdn}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="tab-panel-1" id="list_tab4" style="height:244px;">
			<table class="table_text tableSelector">
				<tbody>
					<tr>
						<td></td>
						<td>번호</td>
						<td>제목</td>
						<td>파일명</td>
					</tr>
					<c:forEach var="atchmnflListObj" items="${atchmnflList}">
						<tr data-file-no="${atchmnflListObj.fileNo}" data-sht-idn="${atchmnflListObj.shtIdn}">
							<td> <input type="checkbox"></td>
							<td>${atchmnflListObj.rownum}</td>
							<td>${atchmnflListObj.atchflSj}</td>
							<td>${atchmnflListObj.orignlFileNm}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="window_footer">
			<div class="button_flat">
				<button class="btn_spotEdit_drngEqpCnfmPrmisnDetail button_flat_normal btn_blue">물받이편집</button>
				<button class="btn_connEdit_drngEqpCnfmPrmisnDetail button_flat_normal btn_blue">연결관편집</button>
				<button class="btn_fileDownload_drngEqpCnfmPrmisnDetail button_flat_normal btn_blue">내려받기</button>
				<button class="btn_fileAdd_drngEqpCnfmPrmisnDetail button_flat_normal btn_blue">등록</button>
				<button class="btn_fileDelete_drngEqpCnfmPrmisnDetail button_flat_normal btn_blue">삭제</button>
				<button class="btn_modify_drngEqpCnfmPrmisnDetail button_flat_normal btn_blue">편집</button>
				<button class="btn_delete_drngEqpCnfmPrmisnDetail button_flat_normal btn_blue">삭제</button>
				<button class="btn_close_drngEqpCnfmPrmisnDetail button_flat_normal btn_blue">닫기</button>
			</div>
		</div>
	</div>
	<form></form>
</div>