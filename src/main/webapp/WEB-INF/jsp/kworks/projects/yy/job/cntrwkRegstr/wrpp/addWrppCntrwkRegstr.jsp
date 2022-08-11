<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui"			 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			 uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		 uri="http://www.springframework.org/tags/form" %>

<form id="addWrppCntrwkRegstrAddform" name="addWrppCntrwkRegstrAddform" method="post" enctype="multipart/form-data">
	<div id="wrppCntrwkRegstrAdd" class="window_container">
		<div class="window_article">
			<table class="table_text">
				<tbody>
					<tr>
						<th>공사번호</th>
						<td colspan="2"></td>
					</tr>
				</tbody>
			</table>
			<div class="tabs-1">
				<ul class="tab-list-1">
					<li class="active"><a class="tab-control" href="#list_tab1">일반사항</a></li>
					<li><a class="tab-control" href="#list_tab2">공사관련</a></li>
					<li><a class="tab-control" href="#list_tab3">계약사항</a></li>
					<li><a class="tab-control" href="#list_tab4">도급자정보</a></li>
				</ul>
			</div>
			<div class="tab-panel-1 active" id="list_tab1">
				<table class="table_text">			
					<tbody>
						<tr>
							<th>공사명</th>
							<td colspan="5">
								<input type="text" name="cntNam" class="input_text" value="" />
							</td>
						</tr>
						<tr>
							<th>공사구분</th>
							<td colspan="2">
								<select name="wrkCde" class="input_text">
									<c:forEach items="${wrkCdeList}" var="wrkCdeList" varStatus="status">
										<option value="${wrkCdeList.codeId}">${wrkCdeList.codeNm}</option>
									</c:forEach>
								</select>
							</td>
							<th>설계자</th>
							<td colspan="2">
								<input type="text" name="dsnNam" class="input_text" value="" />
							</td>											
						</tr>
						<tr>
							<th>공사위치</th>
							<td colspan="5">
								<input type="text" name="cntLoc" class="input_text" value="" />
							</td>
						</tr>
						<tr>
							<th>설계총액</th>
							<td colspan="2">
								<input type="text" name="dsnAmt" class="input_text numOnly" value="" />
							</td>
							<th>관급금액</th>
							<td colspan="2">
								<input type="text" name="dgcAmt" class="input_text numOnly" value="" />
							</td>
						</tr>
						<tr>
							<th>순공사비</th>
							<td colspan="2">
								<input type="text" name="dpcAmt" class="input_text numOnly" value="" />
							</td>
							<th>기타잡비</th>
							<td colspan="2">
								<input type="text" name="detAmt" class="input_text numOnly" value="" />
							</td>
						</tr>
						<tr>
							<th>공사개요</th>
							<td colspan="5">
								<input type="text" name="cntDes" class="input_text" value="" />
							</td>
						</tr>
						<tr>
							<th>국비</th>
							<td>
								<input type="text" name="natAmt" class="input_text numOnly" value="" />
							</td>
							<th>도비</th>
							<td>
								<input type="text" name="couAmt" class="input_text numOnly" value="" />
							</td>
							<th>시군비</th>
							<td>
								<input type="text" name="citAmt" class="input_text numOnly" value="" />
							</td>
						</tr>
						<tr >
							<th>기채</th>
							<td colspan="2">
								<input type="text" name="bndAmt" class="input_text numOnly" value="" />
							</td>
							<th>잉여금</th>
							<td colspan="2">
								<input type="text" name="cssAmt" class="input_text numOnly" value="" />
							</td>
						</tr>
						<tr>
							<th>관</th>
							<td colspan="2">
								<input type="text" name="kwnExp" class="input_text numOnly" value="" />
							</td>
							<th>항</th>
							<td colspan="2">
								<input type="text" name="hngExp" class="input_text numOnly" value="" />
							</td>
						</tr>
						<tr>
							<th>세</th>
							<td colspan="2">
								<input type="text" name="shnExp" class="input_text numOnly" value="" />
							</td>
							<th>목</th>
							<td colspan="2">
								<input type="text" name="mokExp" class="input_text numOnly" value="" />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="tab-panel-1" id="list_tab2">
					<table class="table_text">
						<tbody>
							<tr>
								<th>감독자</th>
								<td>
									<input type="text" name="svsNam" class="input_text" value="" />
								</td>
								<th>착공일자</th>
								<td>
									<input type="text" name="begYmd" class="input_text easyui-datebox" value="" />
								</td>
							</tr>
							<tr>
								<th>준공검사자</th>
								<td>
									<input type="text" name="fchNam" class="input_text" value="" />
								</td>
								<th>준공예정일</th>
								<td>
									<input type="text" name="fnsYmd" class="input_text easyui-datebox" value="" />
								</td>
							</tr>
							<tr>
								<th>준공검사일</th>
								<td>
									<input type="text" name="fchYmd" class="input_text easyui-datebox" value="" />
								</td>
								<th>실준공일</th>
								<td>
									<input type="text" name="rfnYmd" class="input_text easyui-datebox" value="" />
								</td>
							</tr>
							<tr>
								<th>관급물량</th>
								<td colspan="3">
									<input type="text" name="gvrDes" class="input_text" value="" />
								</td>											
							</tr>
						</tbody>
					</table>
				</div>
				<div class="tab-panel-1" id="list_tab3">
				<table class="table_text">
					<tbody>
						<tr>
							<th>입찰일자</th>
							<td>
								<input type="text" name="bidYmd" class="input_text easyui-datebox" value="" />
							</td>
							<th>계약일자</th>
							<td>
								<input type="text" name="cttYmd" class="input_text easyui-datebox" value="" />
							</td>
						</tr>
						<tr>
							<th>계약방법</th>
							<td>
								<select name="cttCde" class="input_text">
									<c:forEach items="${cttCdeList}" var="cttCdeList" varStatus="status">
										<option value="${cttCdeList.codeId}">${cttCdeList.codeNm}</option>
									</c:forEach>
								</select>
							</td>
							<th>예정금액</th>
							<td>
								<input type="text" name="estAmt" class="input_text numOnly" value="" />
							</td>
						</tr>
						<tr>
							<th>계약총액</th>
							<td>
								<input type="text" name="tctAmt" class="input_text numOnly" value="" />
							</td>
							<th>순공사비</th>
							<td>
								<input type="text" name="cpcAmt" class="input_text numOnly" value="" />
							</td>
						</tr>
						<tr>
							<th>관급금액</th>
							<td>
								<input type="text" name="cgvAmt" class="input_text numOnly" value="" />
							</td>
							<th>기타잡비</th>
							<td>
								<input type="text" name="cetAmt" class="input_text numOnly" value="" />
							</td>
						</tr>					
					</tbody>
				</table>
			</div>
			<div class="tab-panel-1" id="list_tab1">
				<table class="table_text">
					<tbody>
						<tr>
							<th>상호</th>
							<td>
								<input type="text" name="gcnNam" class="input_text" value="" />
							</td>
							<th>대표자</th>
							<td>
								<input type="text" name="pocNam" class="input_text" value="" />
							</td>
						</tr>
						<tr>
							<th>주소</th>
							<td colspan="5">
								<input type="text" name="gcnAdr" class="input_text" value="" />
							</td>
						</tr>
						<tr>
							<th>전화번호</th>
							<td colspan="5">
								<input type="text" name="gcnTel" class="input_text" value="" />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="window_footer">
			<div class="button_flat">
				<button class="btn_save_wrppCntrwkRegstrAdd button_flat_normal btn_blue">저장</button>
			</div>
		</div>
	</div>
</form>