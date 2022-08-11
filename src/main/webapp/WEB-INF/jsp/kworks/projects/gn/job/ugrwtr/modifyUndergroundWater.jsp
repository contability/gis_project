<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>

<form id="undrWtrTubWelEditform" name="undrWtrTubWelEditform" method="post" enctype="multipart/form-data">
	<input type="hidden" id="objectid" value="${result.objectid}" />
	<input type="hidden" id="permNtNo" value="${result.permNtNo}" />
	<input type="hidden" id="ftrIdn" value="${result.ftrIdn}" />
	<div class="layerCont">
		<table id="etUV_bassInfoEdit" class="cmmTable v2 ma_b_30" summary="지하수관정 기본정보 관련 테이블" style="table-layout: fixed; word-wrap: break-word;">
			<caption>지하수관정 기본정보 테이블</caption>
			<colgroup>
				<col width="20%" />
				<col width="30%" />
				<col width="20%" />
				<col width="30%" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">관리번호</th>
					<td colspan="3">${result.mgNo}</td>
				</tr>
				<tr>
					<th scope="row">사용구분</th>
					<td>
						<select name="disCde" class="selectBox w_163">
							<c:forEach items="${kwsDisCde}" var="kwsDisCde" varStatus="status">
								<option value="${kwsDisCde.codeId}" ${kwsDisCde.codeId==result.disCde ? 'selected' : ''}>${kwsDisCde.codeNm}</option>
							</c:forEach>
						</select>
					</td>
					<th scope="row">시설구분</th>
					<td>
						<span>
							<c:forEach items="${kwsPermNtFo}" var="kwsPermNtFo">
								<c:if test="${kwsPermNtFo.codeId eq result.permNtFo}">${kwsPermNtFo.codeNm}</c:if>
							</c:forEach>
						</span>
					</td>
				</tr>
				<tr>
					<th scope="row">허가신고번호</th>
					<td>${result.permNtNo}</td>
					<th scope="row">허가신고일</th>
					<td>${result.permNtYm}</td>
				</tr>
			</tbody>
		</table>
		<table id="etUV_userInfoEdit" class="cmmTable v2 ma_b_30" summary="지하수관정 이용자 정보 관련 테이블" style="table-layout: fixed; word-wrap: break-word;">
			<caption>지하수관정 이용자 정보 테이블</caption>
			<colgroup>
				<col width="20%" />
				<col width="30%" />
				<col width="20%" />
				<col width="30%" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">이용자구분</th>
					<td>
						<span>
							<c:forEach items="${kwsRgtMbdGb}" var="kwsRgtMbdGb">
								<c:if test="${kwsRgtMbdGb.codeId eq result.rgtMbdGb}">${kwsRgtMbdGb.codeNm}</c:if>
							</c:forEach>
						</span>
					</td>
					<th scope="row">상호(성명)</th>
					<td>${result.rgtMbdNm}</td>
				</tr>
				<tr>
					<th scope="row">대표자</th>
					<td>${result.userCeo}</td>
					<th scope="row">생년월일/<br>법인/사업자번호</th>
					<td>${result.rgtMbdRe}</td>
				</tr>
				<tr>
					<th scope="row">주소</th>
					<td colspan="3">${result.rdnWhlAd}</td>
				</tr>
			</tbody>
		</table>
		<table id="etUV_devSiteInfoEdit" class="cmmTable v2 ma_b_30" summary="지하수관정 개발위치 정보 관련 테이블" style="table-layout: fixed; word-wrap: break-word;">
			<caption>지하수관정 개발위치 정보 테이블</caption>
			<colgroup>
				<col width="20%" />
				<col width="30%" />
				<col width="20%" />
				<col width="30%" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">주소</th>
					<td colspan="3">${result.dvopLocR}</td>
				</tr>
				<tr>
					<th scope="row">경도</th>
					<td>${result.litdDg}° ${result.litdMint}' ${result.litdSc}"</td>
					<th scope="row">위도</th>
					<td>${result.lttdDg}° ${result.lttdMint}' ${result.lttdSc}"</td>
				</tr>
			</tbody>
		</table>
		<table id="etUV_useInfoEdit" class="cmmTable v2 ma_b_30" summary="지하수관정 용도 정보 관련 테이블" style="table-layout: fixed; word-wrap: break-word;">
			<caption>지하수관정 용도 정보 테이블</caption>
			<colgroup>
				<col width="20%" />
				<col width="30%" />
				<col width="20%" />
				<col width="30%" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">용도구분</th>
					<td>${result.uwaterSrv}</td>
					<th scope="row">용도명칭</th>
					<td>${result.uwaterDtl}</td>
				</tr>
				<tr>
					<th scope="row">허가유효 시작일</th>
					<td>${result.permEfSt}</td>
					<th scope="row">허가유효 종료일</th>
					<td>${result.permEfEn}</td>
				</tr>
				<tr>
					<th scope="row">음용여부</th>
					<td colspan="3">${result.uwaterPot}</td>
				</tr>
			</tbody>
		</table>
		<table id="etUV_comInfoEdit" class="cmmTable v2 ma_b_30" summary="지하수관정 준공 정보 관련 테이블" style="table-layout: fixed; word-wrap: break-word;">
			<caption>지하수관정 준공 정보 테이블</caption>
			<colgroup>
				<col width="20%" />
				<col width="30%" />
				<col width="20%" />
				<col width="30%" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">준공일</th>
					<td>${result.jgongYmd}</td>
					<th scope="row">몽리면적</th>
					<td><input type="text" name="havQua" value="${result.havQua}" class="inputText w100" maxlength=10" numberOnly="true" /></td>
				</tr>
				<tr>
					<th scope="row">굴착깊이</th>
					<td>${result.digDph}</td>
					<th scope="row">굴착지름</th>
					<td>${result.digDiam}</td>
				</tr>
				<tr>
					<th scope="row">취수계획량</th>
					<td>${result.frwPlnQu}</td>
					<th scope="row">소요수량</th>
					<td>${result.ndQt}</td>
				</tr>
				<tr>
					<th scope="row">동력장치마력(hp)</th>
					<td>${result.dynEqnHr}</td>
					<th scope="row">토출관직경</th>
					<td>${result.pipeDiam}</td>
				</tr>
				<tr>
					<th scope="row">설치심도</th>
					<td>${result.esbDph}'</td>
					<th scope="row">양수능력(m<sup>3</sup>/일)</th>
					<td>${result.rwtCp}'</td>
				</tr>
				<tr>
					<th scope="row">구조물상태</th>
					<td colspan="3"><input type="text" name="wellSts" value="${result.wellSts}" class="inputText w100" maxlength="50" /></td>
				</tr>
			</tbody>
		</table>
		<table id="etUV_adWeInfoEdit" class="cmmTable v2 ma_b_30" summary="지하수관정 폐공 정보 관련 테이블" style="table-layout: fixed; word-wrap: break-word;">
			<caption>지하수관정 폐공 정보 테이블</caption>
			<colgroup>
				<col width="20%" />
				<col width="30%" />
				<col width="20%" />
				<col width="30%" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">폐공사유</th>
					<td colspan="3"><input type="text" name="disTxt" value="${result.disTxt}" class="inputText w100" maxlength="100" /></td>
				</tr>
				<tr>
					<th scope="row">폐공후상태</th>
					<td colspan="3"><input type="text" name="disSts" value="${result.disSts}" class="inputText w100" maxlength="100" /></td>
				</tr>
			</tbody>
		</table>
		<table class="cmmTable" summary="현장사진 테이블">
			<caption>현장사진 테이블</caption>
			<colgroup>
				<col width="20%" />
				<col width="*" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">현장사진</th>
					<th scope="col">파일명</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>원경</td>
					<td class="left"><input type="file" name="distantView" /></td>
				</tr>
				<tr>
					<td>근경</td>
					<td class="left"><input type="file" name="closeRangeView" /></td>
				</tr>
				<tr>
					<td>GPS관측도</td>
					<td class="left"><input type="file" name="gpsObservationMap" /></td>
				</tr>
				<tr>
					<td>폐공</td>
					<td class="left"><input type="file" name="abandonedWell" /></td>
				</tr>
				<tr>
					<td>기타</td>
					<td class="left"><input type="file" name="tempView" /></td>
				</tr>
			</tbody>
		</table>
	</div>
	<p class="btnRight v2">
		<a href="#" class="a_save_modifyUgrwtr btnBlue">저장</a>
		<a href="#" class="a_close_modifyUgrwtr btnBlue">닫기</a>
	</p>
</form>
