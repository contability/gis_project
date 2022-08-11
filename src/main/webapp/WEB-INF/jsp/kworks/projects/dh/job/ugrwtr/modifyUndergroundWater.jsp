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
					<th scope="row">관리일련번호</th>
					<td>
						<input type="text" name="mgNo" value="${result.mgNo}" class="inputText w100" maxlength=20" />
					</td>
					<th scope="row">사용구분</th>
					<td>
						<select name="disCde" class="selectBox w_163">
							<c:forEach items="${disCde}" var="disCde" varStatus="status">
								<option value="${disCde.codeId}" ${disCde.codeId==result.disCde ? 'selected' : ''}>${disCde.codeNm}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th scope="row">허가신고번호</th>
					<td>
						<input type="text" name="permNtNo" value="${result.permNtNo}" class="inputText w100" maxlength=10" />
					</td>
					<th scope="row">허가신고일</th>
					<td>
						<input type="text" name="permNtYm" value="${result.permNtYm}" class="inputText w100" maxlength=20" />
					</td>
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
					<th scope="row">관리자 성명</th>
					<td>
						<input type="text" name="rgtMbdNm" value="${result.rgtMbdNm}" class="inputText w100" maxlength=60" />
					</td>
					<th scope="row">연락처</th>
					<td>
						<input type="text" name="userTel" value="${result.userTel}" class="inputText w100" maxlength=20" />
					</td>
				</tr>
				<tr>
					<th scope="row">관리자 주소</th>
					<td colspan="3">
						<input type="text" name="rdnWhlAd" value="${result.rdnWhlAd}" class="inputText w100" maxlength=500" />
					</td>
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
					<th scope="row">위치 법정동</th>
					<td colspan="3">
						<select name="dvopLocN" class="selectBox w_163">
							<c:forEach items="${dvopLocN}" var="dvopLocN" varStatus="status">
								<option value="${dvopLocN.codeId}" ${dvopLocN.codeId==result.dvopLocN ? 'selected' : ''}>${dvopLocN.codeNm}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th scope="row">위치 본번</th>
					<td>
						<input type="text" name="dvopLocB" value="${result.dvopLocB}" class="inputText w100" maxlength=20" />
					</td>
					<th scope="row">위치 부번</th>
					<td>
						<input type="text" name="dvopLocH" value="${result.dvopLocH}" class="inputText w100" maxlength=4" />
					</td>
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
					<td>
						<select name="uwaterSrv" class="selectBox w_163">
							<c:forEach items="${uwaterSrv}" var="uwaterSrv" varStatus="status">
								<option value="${uwaterSrv.codeId}" ${uwaterSrv.codeId==result.uwaterSrv ? 'selected' : ''}>${uwaterSrv.codeNm}</option>
							</c:forEach>
						</select>
					</td>
					<th scope="row">세부용도</th>
					<td>
						<select name="uwaterDtl" class="selectBox w_163">
							<c:forEach items="${uwaterDtl}" var="uwaterDtl" varStatus="status">
								<option value="${uwaterDtl.codeId}" ${uwaterDtl.codeId==result.uwaterDtl ? 'selected' : ''}>${uwaterDtl.codeNm}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th scope="row">사용구분</th>
					<td colspan="3">
						<select name="uwaterPot" class="selectBox w_163">
							<c:forEach items="${uwaterPot}" var="uwaterPot" varStatus="status">
								<option value="${uwaterPot.codeId}" ${uwaterPot.codeId==result.uwaterPot ? 'selected' : ''}>${uwaterPot.codeNm}</option>
							</c:forEach>
						</select>
					</td>
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
					<th scope="row">준공일자</th>
					<td>
						<input type="text" name="jgongYmd" value="${result.jgongYmd}" class="inputText w100" maxlength=8" />
					</td>
					<th scope="row">몽리면적</th>
					<td>
						<input type="text" name="havQua" value="${result.havQua}" class="inputText w100" maxlength=16" numberOnly="true" />
					</td>
				</tr>
				<tr>
					<th scope="row">굴착깊이</th>
					<td>
						<input type="text" name="digDph" value="${result.digDph}" class="inputText w100" maxlength=12" numberOnly="true" />
					</td>
					<th scope="row">굴착지름</th>
					<td>
						<input type="text" name="digDiam" value="${result.digDiam}" class="inputText w100" maxlength=12" numberOnly="true" />
					</td>
				</tr>
				<tr>
					<th scope="row">동력장치마력(hp)</th>
					<td>
						<input type="text" name="dynEqnHr" value="${result.dynEqnHr}" class="inputText w100" maxlength=16" numberOnly="true" />
					</td>
					<th scope="row">토출관직경</th>
					<td>
						<input type="text" name="pipeDiam" value="${result.pipeDiam}" class="inputText w100" maxlength=14" numberOnly="true" />
					</td>
				</tr>
				<tr>
					<th scope="row">설치심도</th>
					<td>
						<input type="text" name="esbDph" value="${result.esbDph}" class="inputText w100" maxlength=14" numberOnly="true" />
					</td>
					<th scope="row">양수능력(m<sup>3</sup>/일)</th>
					<td>
						<input type="text" name="rwtCap" value="${result.rwtCap}" class="inputText w100" maxlength=16" numberOnly="true" />
					</td>
				</tr>
				<tr>
					<th scope="row">수질검사일</th>
					<td>
						<input type="text" name="regYmd" value="${result.regYmd}" class="inputText w100" maxlength="8" />
					</td>
					<th scope="row">적합여부</th>
					<td>
						<input type="text" name="udtPot" value="${result.udtPot}" class="inputText w100" maxlength="10" />
					</td>
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
