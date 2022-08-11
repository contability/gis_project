<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form id="modifyRdtOccnDtModifyform" name="modifyRdtOccnDtModifyform"
	method="post" enctype="multipart/form-data">
	<div id="modifyRdtOccnDtRegstr" class="window_container">
		<div class="window_article">
			<input type="hidden" name="ocpatIdn" path="ocpatIdn" id="ocpatIdn" />
			<input type="hidden" name="pnu" path="pnu" id="pnu" />
			<input type="hidden" name="ftrCde" path="ftrCde" value="${result.ftrCde}" />
			 <input type="hidden" name="ftrIdn" path="ftrIdn" value="${result.ftrIdn}" />
			<table class="table_text">
				<tbody>
					<tr>
						<th>허가번호</th>
						<td colspan="3"><input type="text" class="easyui-textbox"
							name="pmtNum" path="pmtNum" style="width: 99%"
							value="${result.pmtNum}" /></td>
						<th>허가일</th>
						<td><input type="text" class="w_110 easyui-datebox"
							name="pmtYmd" path="pmtYmd" value="${result.pmtYmd}" /></td>

					</tr>
					<tr>
						<th>점용자 성명</th>
						<td colspan="2"><input type="text" class="easyui-textbox"
							name="prsNam" path="prsNam" style="width: 99%"
							value="${result.prsNam}" /></td>
						<th>주민등록번호</th>
						<td colspan="2"><input type="text" class="easyui-textbox"
							name="regNum" path="regNum" style="width: 99%"
							value="${result.regNum}" /></td>
					</tr>
					<tr>
						<th>점용자 주소</th>
						<td colspan="5"><input type="text" class="easyui-textbox"
							name="prsAdr" path="prsAdr" style="width: 99%"
							value="${result.prsAdr}" /></td>
					</tr>
					<tr>
						<th>점용 위치</th>
						<td colspan="5"><input type="text" class="easyui-textbox"
							name="jygLoc" path="jygLoc" style="width: 99%"
							value="${result.jygLoc}" /></td>
					</tr>
					<tr>
						<th>점용 목적</th>
						<td colspan="3"><input type="text" class="easyui-textbox"
							name="jygPur" path="jygPur" style="width: 99%"
							value="${result.jygPur}" /></td>
						<th>점용 면적</th>
						<td><input type="text" class="w_110 easyui-textbox"
							name="jygAra" path="jygAra" value="${result.jygAra}" /></td>
					</tr>
					<tr>
						<th>점용 기간</th>
						<td><select name="jygUlm" class="w_110 easyui-combobox">
								<option value="0">미분류</option>
								<option value="1">영구</option>
								<option value="2">일시</option>
								<option value="3">기간</option>
						</select></td>
						<th>점용시작일</th>
						<td><input type="text" class="w_110 easyui-datebox"
							name="jysYmd" path="jysYmd" value="${result.jysYmd}" /></td>
						<th>점용종료일</th>
						<td><input type="text" class="w_110 easyui-datebox"
							name="jyeYmd" path="jyeYmd" value="${result.jyeYmd}" /></td>
					</tr>

					<tr>
						<th>점용시설 개요</th>
						<td colspan="5"><input type="text" class="easyui-textbox"
							name="jygCmt" path="jygCmt" style="width: 99%"
							value="${result.jygCmt}" /></td>
					</tr>
					<tr>
						<th>점용료</th>
						<td colspan="2"><input type="text" class="easyui-textbox"
							name="jygPay" path="jygPay" style="width: 99%"
							value="${result.jygPay}" /></td>
						<th>연락처</th>
						<td colspan="2"><input type="text" class="easyui-textbox"
							name="jygTel" path="jygTel" style="width: 99%"
							value="${result.jygTel}" /></td>
					</tr>

					<tr>
						<th>변동사항</th>
						<td colspan="3"><input type="text" class="easyui-textbox"
							name="chnCmt" path="chnCmt" style="width: 99%"
							value="${result.chnCmt}" /></td>
						<th>발생일</th>
						<td><input type="text" class="w_110 easyui-datebox"
							name="chnYmd" path="chnYmd" value="${result.chnYmd}" /></td>
					</tr>


					<tr>
						<c:if test="${not empty oldRegCde}">
							<th>최초대장</th>
							<td colspan="2">${oldRegAlias}</td>
							<th>최초대장 관리번호</th>
							<td colspan="2">${oldRegIdn}</td>
						</c:if>
						<c:if test="${empty oldRegCde}">
							<th>최초대장</th>
							<td colspan="2"><select name="oldCde"
								class="w_110 easyui-combobox" style="width: 99%">
									<option value="OC001">도로점용허가</option>
									<option value="OC002">건축허가협의</option>
									<option value="OC003">개발행위협의</option>
									<option value="OC004">사설안내표지판</option>
							</select></td>
							<th>최초대장 관리번호</th>
							<td colspan="2"><input type="text" class="easyui-textbox"
								name="oldIdn" path="oldIdn" style="width: 99%"
								value="${result.oldIdn}" /></td>
						</c:if>
					</tr>

					<tr>
						<th>도로종류</th>
						<td><input type="text" class="w_110 easyui-textbox"
							name="rodTyp" path="rodTyp" value="${result.rodTyp}" /></td>
						<th>노선명</th>
						<td><input type="text" class="w_110 easyui-textbox"
							name="rotNam" path="rotNam" value="${result.rotNam}" /></td>
						<th>행정읍면동</th>
						<td><input type="text" class="w_110 easyui-textbox"
							name="umdNam" path="umdNam" value="${result.umdNam}" /></td>
					</tr>
					<tr>
						<th>지번주소</th>
						<td colspan="5">
							<span> 
								<select class="w_200 easyui-combobox" id="selDong" name="selDong" value="selDong">
								</select>
							</span>
							
							 산
							 <span>
							 	 <input class="easyui-switchbutton" id="checkMauntain" name="checkMauntain" />
							</span> 
							
							<span> 
								<input type="text" class="w_100 easyui-numberspinner" name="mainNum" path="mainNum" id ="mainNum"/>
							</span>
							
							-
							<span>
								<input type="text" class="w_100 easyui-numberspinner" name="subNum" path="subNum" id ="subNum"/>
							</span>
						</td>
					</tr>

					<tr>
						<th>비고</th>
						<td colspan="5"><input type="text" class="easyui-textbox"
							name="rmkExp" path="rmkExp" style="width: 99%"
							value="${result.rmkExp}" /></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="window_footer">
			<div class="button_flat">
				<button class="btn_save_RdtOcpatDtRegstr button_flat_normal btn_blue">저장</button>
				<button
					class="btn_close_RdtOcpatDtRegstr button_flat_normal btn_blue">닫기</button>
			</div>
		</div>
	</div>
</form>