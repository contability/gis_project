<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<form id="ctrlpntAdd" name="ctrlpntAdd" method="post" enctype="multipart/form-data" >
	<div class="layerCont">
		<table class="cmmTable v2 ma_b_30" summary="기준점 정보 관련 테이블">
			<caption>기준점 정보 테이블</caption>
			<colgroup>
				<col width="22%" />
				<col width="28%" />
				<col width="20%" />
				<col width="30%" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">사업명</th>
					<td colspan="3"><input type="text" name="prjNam"class="inputText w100" maxlength="40" /></td>
				</tr>
				<tr>
					<th scope="row">고시일자</th>
					<td><input type="text" name="decYmd"class="inputText easyui-datebox" style="width:153px" maxlength="10" /></td>
					<th scope="row">고시번호</th>
					<td><input type="text" name="decNum"class="inputText w100" maxlength="10" /></td>
				</tr>
				<tr>
					<th scope="row">점의종류</th>
					<td>
						<select name="cpkCde" class="selectBox w100">
							<c:forEach items="${cpkCde}" var="cpkCde" varStatus="status">
								<option value="${cpkCde.codeId}">${cpkCde.codeNm}</option>
							</c:forEach>
						</select>
					</td>
					<th scope="row">점번호</th>
					<td><input type="text" name="bseNam" class="inputText w100" maxlength="10" /></td>
				</tr>
				<tr>
					<th scope="row">급수</th>	
					<td>
						<select name="grdCde" class="selectBox w100">
							<c:forEach items="${grdCde}" var="grdCde" varStatus="status">
								<option value="${grdCde.codeId}">${grdCde.codeNm}</option>
							</c:forEach>
						</select>
					</td>	
					<th scope="row">시행자</th>	
					<td><input type="text" name="wrkOrg" class="inputText w100" maxlength="25" /></td>
				</tr>
				<tr>
					<th scope="row">1/50,000도엽명</th>	
					<td><input type="text" name="s50Nam" class="inputText w100" maxlength="25" /></td>		
					<th scope="row">상태</th>	
					<td>
						<select name="cpsCde" class="selectBox w100">
							<c:forEach items="${cpsCde}" var="cpsCde" varStatus="status">
								<option value="${cpsCde.codeId}">${cpsCde.codeNm}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th scope="row">소재지</th>	
					<td colspan="3">
						<input type="text" name="setLoc" class="inputText" style="width:263px" maxlength="125" />
					</td>
				</tr>	
				<tr>
					<th scope="row">매설일자</th>	
					<td><input type="text" name="setYmd" class="inputText easyui-datebox" style="width:153px" /></td>		
					<th scope="row">관측일자</th>	
					<td><input type="text" name="obsYmd" class="inputText easyui-datebox" style="width:165px" /></td>
				</tr>	
				<tr>
					<th scope="row">매설자</th>	
					<td><input type="text" name="setNam" class="inputText w100" maxlength="25" /></td>		
					<th scope="row">관측자</th>	
					<td><input type="text" name="obsNam" class="inputText w100" maxlength="25" /></td>
				</tr>	
				<tr>
					<th scope="row">매설형태</th>	
					<td><input type="text" name="marSit" class="inputText w100" maxlength="25" /></td>		
					<th scope="row">관측장비</th>	
					<td><input type="text" name="obfNam" class="inputText w100" maxlength="10" /></td>
				</tr>	
				<tr>
					<th scope="row">공공기준점</th>	
					<td>신성과</td>		
					<th scope="row">공공수준점</th>	
					<td>
						<select name="lskCde" class="selectBox w100">
							<c:forEach items="${lskCde}" var="lskCde" varStatus="status">
								<option value="${lskCde.codeId}">${lskCde.codeNm}</option>
							</c:forEach>
						</select>
					</td>
				</tr>	
				<tr>
					<th scope="row">경로</th>	
					<td colspan="3"><textarea name="setAdd" class="textArea w100 h_50"></textarea></td>	
				</tr>
				<tr>
					<th scope="row">비고</th>
					<td colspan="3"><textarea name="refDes" class="textArea w100 h_50"></textarea></td>
				</tr>	
			</tbody>
		</table>
		
		<table class="cmmTable v2 ma_b_30" summary="성과 정보 관련 테이블">
			<caption>성과 정보 테이블</caption>
			<colgroup>
				<col width="" />
				<col width="" />
				<col width="" />
				<col width="" />
				<col width="" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row" rowspan="4" class="center">성과</th>
					<th scope="row">경도</th>
					<td><input type="text" name="ngwX" class="inputText w100" maxlength="10" /></td>
					<th scope="row">위도</th>
					<td><input type="text" name="ngwY" class="inputText w100" maxlength="10" /></td>
				</tr>
				<tr>
					<th scope="row" colspan="2" class="center">GRS-80 타원체</th>
					<th scope="row">좌표원점</th>
					<td><input type="text" name="pntNam" class="inputText w100" maxlength="10" /></td>
				</tr>
				<tr>
					<th scope="row">N(X)</th>	
					<td><input type="text" name="nggX" class="inputText w100" maxlength="10" required="required" /></td>	
					<th scope="row">E(Y)</th>	
					<td><input type="text" name="nggY" class="inputText w100" maxlength="10" required="required" /></td>
				</tr>
				<tr>
					<th scope="row">타원체고</th>	
					<td><input type="text" name="esdHgt" class="inputText w100" maxlength="5" /></td>		
					<th scope="row">정표고</th>	
					<td><input type="text" name="bseHgt" class="inputText w100" maxlength="10" /></td>	
				</tr>
			</tbody>
		</table>
		
		<table class="cmmTable" summary="현장사진 테이블">
			<caption>현장사진 테이블</caption>
			<colgroup>
				<col width="25%" />
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
					<td>약도</td>
					<td class="left"><input type="file" name="outlineMap" /></td>
				</tr>
				<tr>
					<td>시통도</td>
					<td class="left"><input type="file" name="visibilityMap" /></td>
				</tr>
				<tr>
					<td>근경</td>
					<td class="left"><input type="file" name="closeRangeView" /></td>
				</tr>
				<tr>
					<td>원경</td>
					<td class="left"><input type="file" name="distantView" /></td>
				</tr>
			</tbody>
		</table>
	</div>
	<p class="btnRight v2">
		<a id="a_save_ctrlpntAdd" href="#" class="btnBlue">저장</a>
	</p>
</form>
