<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form id="addOcpatRegstrAddform" name="addOcpatRegstrAddform" method="post" enctype="multipart/form-data">
	<div id="addOcpatRegstr" class="window_container">
		<div class="window_article">
			<input type="hidden" name="ocpatIdn" path="ocpatIdn" value="${ocpatIdn}" />
			<input type="hidden" name="ftrCde" path="ftrCde" value="${ftrCde}" />
			<input type="hidden" name="oldRegAlias" path="oldRegAlias" value="${oldRegAlias}" />
			<input type="hidden" name="oldRegCde" path="oldRegCde" value="${oldRegCde}" />
			<input type="hidden" name="oldRegIdn" path="oldRegIdn" value="${oldRegIdn}" />
			<table class="table_text">
				<tbody>
					<tr>
						<c:if test="${ocpatIdn eq '100100' or ocpatIdn eq '200100' or ocpatIdn eq '300100' or ocpatIdn eq '400100' or ocpatIdn eq '500100' or ocpatIdn eq '600100'}">
							<th>허가번호</th>
							<td colspan="3">
								<input type="text" class="easyui-textbox" name="pmtNum" path="pmtNum" style="width:99%"/>
							</td>
							<th>허가일</th>
							<td>
								<input type="text" class="w_110 easyui-datebox" name="pmtYmd" path="pmtYmd"/>
							</td>
						</c:if>
						<c:if test="${ocpatIdn eq '700100' or ocpatIdn eq '800100' or ocpatIdn eq '900100'}">
							<th>허가번호</th>
							<td colspan="5">
								<input type="text" class="easyui-textbox" name="pmtNum" path="pmtNum" style="width:99%"/>
							</td>
						</c:if>
					</tr>
					<tr>
						<th>점용자 성명</th>
						<td colspan="2">
							<input type="text" class="easyui-textbox" name="prsNam" path="prsNam" style="width:99%"/>
						</td>
						<th>주민등록번호</th>
						<td colspan="2">
							<input type="text" class="easyui-textbox" name="regNum" path="regNum" style="width:99%"/>
						</td>
					</tr>
					<tr>
						<th>점용자 주소</th>
						<td colspan="5">
							<input type="text" class="easyui-textbox" name="prsAdr" path="prsAdr" style="width:99%"/>
						</td>
					</tr> 
					<tr>
						<th>점용 위치</th>
						<td colspan="5">
							<input type="text" class="easyui-textbox" name="jygLoc" path="jygLoc" style="width:99%"/>
						</td>
					</tr>
					<tr>
						<th>점용 목적</th>
						<td colspan="3">
							<input type="text" class="easyui-textbox" name="jygPur" path="jygPur" style="width:99%"/>
						</td>
						<th>점용 면적</th>
						<td>
							<input type="text" class="w_110 easyui-textbox" name="jygAra" path="jygAra"/>
						</td>
					</tr>
					<tr>
						<th>점용 기간</th>
						<td>
							<select name="jygUlm" class="w_110 easyui-combobox">
								<option value="0">미분류</option>
								<option value="1">영구</option>
								<option value="2">일시</option>
								<option value="3">기간</option>
							</select>
						</td>
						<th>점용시작일</th>
						<td>
							<input type="text" class="w_110 easyui-datebox" name="jysYmd" path="jysYmd"/>
						</td>
						<th>점용종료일</th>
						<td>
							<input type="text" class="w_110 easyui-datebox" name="jyeYmd" path="jyeYmd"/>
						</td>
					</tr>
					<c:if test="${ocpatIdn eq '100100' or ocpatIdn eq '200100' or ocpatIdn eq '300100' or ocpatIdn eq '400100' or ocpatIdn eq '500100'}">
						<tr>
							<th>점용시설 개요</th>
							<td colspan="5">
								<input type="text" class="easyui-textbox" name="jygCmt" path="jygCmt" style="width:99%"/>
							</td>
						</tr>
						<tr>
							<th>점용료</th>
							<td colspan="2">
								<input type="text" class="easyui-textbox" name="jygPay" path="jygPay" style="width:99%"/>
							</td>
							<th>연락처</th>
							<td colspan="2">
								<input type="text" class="easyui-textbox" name="jygTel" path="jygTel" style="width:99%"/>
							</td>
						</tr>
					</c:if>
					<c:if test="${ocpatIdn eq '500100'}">
						<tr>
							<th>변동사항</th>
							<td colspan="3">
								<input type="text" class="easyui-textbox" name="chnCmt" path="chnCmt" style="width:99%"/>
							</td>
							<th>발생일</th>
							<td>
								<input type="text" class="w_110 easyui-datebox" name="chnYmd" path="chnYmd"/>
							</td>
						</tr>
					</c:if>
					<c:if test="${ocpatIdn eq '600100'}">
						<tr>
							<th>구조</th>
							<td colspan="5">
								<input type="text" class="easyui-textbox" name="jygCmt" path="jygCmt" style="width:99%"/>
							</td>
						</tr>
						<tr>
							<th>준공일자</th>
							<td colspan="2">
								<input type="text" class="easyui-textbox" name="endYmd" path="endYmd" style="width:99%"/>
							</td>
							<th>연락처</th>
							<td colspan="2">
								<input type="text" class="easyui-textbox" name="jygTel" path="jygTel" style="width:99%"/>
							</td>
						</tr>
					</c:if>
					<c:if test="${ocpatIdn eq '700100'}">
						<tr>
							<th>불허가 사유</th>
							<td colspan="5">
								<input type="text" class="easyui-textbox" name="jygCmt" path="jygCmt" style="width:99%"/>
							</td>
						</tr>
						<tr>
							<th>불허가일</th>
							<td colspan="2">
								<input type="text" class="easyui-datebox" name="pmtYmd" path="pmtYmd" style="width:99%"/>
							</td>
							<th>연락처</th>
							<td colspan="2">
								<input type="text" class="easyui-textbox" name="jygTel" path="jygTel" style="width:99%"/>
							</td>
						</tr>
					</c:if>
					<c:if test="${ocpatIdn eq '800100'}">
						<tr>
							<th>취소 사유</th>
							<td colspan="5">
								<input type="text" class="easyui-textbox" name="jygCmt" path="jygCmt" style="width:99%"/>
							</td>
						</tr>
						<tr>
							<th>취소신청일</th>
							<td colspan="2">
								<input type="text" class="easyui-datebox" name="pmtYmd" path="pmtYmd" style="width:99%"/>
							</td>
							<th>연락처</th>
							<td colspan="2">
								<input type="text" class="easyui-textbox" name="jygTel" path="jygTel" style="width:99%"/>
							</td>
						</tr>
					</c:if>
					<c:if test="${ocpatIdn eq '900100'}">
						<tr>
							<th>취하 사유</th>
							<td colspan="5">
								<input type="text" class="easyui-textbox" name="jygCmt" path="jygCmt" style="width:99%"/>
							</td>
						</tr>
						<tr>
							<th>취하신청일</th>
							<td colspan="2">
								<input type="text" class="easyui-datebox" name="pmtYmd" path="pmtYmd" style="width:99%"/>
							</td>
							<th>연락처</th>
							<td colspan="2">
								<input type="text" class="easyui-textbox" name="jygTel" path="jygTel" style="width:99%"/>
							</td>
						</tr>
					</c:if>
					<c:if test="${ocpatIdn eq '500100' or ocpatIdn eq '600100' or ocpatIdn eq '700100' or ocpatIdn eq '800100' or ocpatIdn eq '900100'}">
						<tr>
							<c:if test="${not empty oldRegCde}">
								<th>최초대장</th>
								<td colspan="2">${oldRegAlias}</td>
								<th>최초대장 관리번호</th>
								<td colspan="2">${oldRegIdn}</td>
							</c:if>
							<c:if test="${empty oldRegCde}">
								<th>최초대장</th>
								<td colspan="2">
									<select name="oldCde" class="w_110 easyui-combobox"  style="width:99%">
										<option value="OC001">도로점용허가</option>
										<option value="OC002">건축허가협의</option>
										<option value="OC003">개발행위협의</option>
										<option value="OC004">사설안내표지판</option>
									</select>
								</td>
								<th>최초대장 관리번호</th>
								<td colspan="2">
									<input type="text" class="easyui-textbox" name="oldIdn" path="oldIdn" style="width:99%"/>
								</td>
							</c:if>
						</tr>
					</c:if>
					<tr>
						<th>도로종류</th>
						<td>
							<input type="text" class="w_110 easyui-textbox" name="rodTyp" path="rodTyp"/>
						</td>
						<th>노선명</th>
						<td>
							<input type="text" class="w_110 easyui-textbox" name="rotNam" path="rotNam"/>
						</td>
						<th>행정읍면동</th>
						<td>
							<input type="text" class="w_110 easyui-textbox" name="umdNam" path="umdNam"/>
						</td>
					</tr>
					<tr>
						<th>지번주소</th>
						<td colspan="5">
							<span>
								<select class="w_200 easyui-combobox" id="selDong" name="selDong"></select>
							</span>
							산 
							<span>
								<input class="easyui-switchbutton" id="checkMauntain" name="checkMauntain"/>
							</span>
							<span>
								<input type="text" class="w_100 easyui-numberspinner" name="mainNum" path="mainNum"/>
							</span>
							-
							<span>
								<input type="text" class="w_100 easyui-numberspinner" name="subNum" path="subNum"/>
							</span>
						</td>
					</tr>
					<c:if test="${ocpatIdn eq '100100' or ocpatIdn eq '200100' or ocpatIdn eq '300100' or ocpatIdn eq '400100'}">
						<tr>
							<th>노선번호</th>
							<td colspan="2">
								<input type="text" class="easyui-textbox" name="rotIdn" path="rotIdn" style="width:99%"/>
							</td>
							<th>구간번호</th>
							<td colspan="2">
								<input type="text" class="easyui-textbox" name="secIdn" path="secIdn" style="width:99%"/>
							</td>
						</tr>
					</c:if>					
					<tr>
						<th>비고</th>
						<td colspan="5">
							<input type="text" class="easyui-textbox" name="rmkExp" path="rmkExp" style="width:99%"/>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="window_footer">
			<div class="button_flat">
				<button class="btn_save_ocpatRegstrAdd button_flat_normal btn_blue">저장</button>
				<button class="btn_close_ocpatRegstrAdd button_flat_normal btn_blue">닫기</button>
			</div>
		</div>
	</div>
</form>