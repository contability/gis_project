<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui"			 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			 uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		 uri="http://www.springframework.org/tags/form" %>

<form id="landCenterCntrwkRegstrModifyform" name="landCenterCntrwkRegstrModifyform" method="post" enctype="multipart/form-data">
	<input type="hidden" name="kwsDept" id="landCenterCntrwkRegstrModifyformCntDept" value="${result.cntDept}" />
	<input type="hidden" name="legacyPnu" id="landCenterCntrwkRegstrModifyformlegacyPnu" value="${result.pnu}" />
	<input type="hidden" name="objectid" id="landCenterCntrwkRegstrModifyformObjectid" value="${result.objectid}" />
	<input type="hidden" name="lcinfoUpdtAt" id="landCenterCntrwkRegstrModifyformlcinfoUpdtAt" value="false" />
	<div id="landCenterCntrwkRegstrModify" class="window_container">
		<div class="window_article" >
			<table class="table_text">
				<tbody>
					<tr>
						<th>공사번호</th>
						<td colspan="2">
							<input type="text" id="cntIdn" name="cntIdn" class="easyui-numberbox" value="${result.cntIdn}" style="width: 100%;" readonly="readonly">
						</td>
					</tr>
					<tr>
						<th><span style="color:red;">* </span><label for="cntNam">공사명</label></th>
						<td colspan="2">
							<input type="text" name="cntNam" id="cntNam" class="easyui-textbox" value="${result.cntNam}" style="width: 100%;" required />
						</td>
					</tr>
				</tbody>
			</table>
			<div class="tabs-1">
				<ul class="tab-list-1">
					<li class="active"><a class="tab-control tab_landCenterCntrwkRegstr_modify_1" href="#list_tab1">일반사항</a></li>
				</ul>
			</div>
			<div class="tab-panel-1 active" id="list_tab1" style="height:211px">
				<table class="table_text">			
					<tbody>
						<tr>
							<th><span style="color:red;">* </span><label for="cntDept">공사부서</label></th>
							<td colspan="2">
								<select name="cntDept" id="cntDept" class="modify_cnt_dept easyui-combobox" style="width: 100%;" required>
									<c:forEach items="${cntDeptList}" var="cntDeptObj" varStatus="status">
										<option value="${cntDeptObj.deptNm}" ${cntDeptObj.deptNm==result.cntDept ? 'select' : ''}>${cntDeptObj.deptNm}</option>
									</c:forEach>
								</select>
							</td>
							<th><span style="color:red;">* </span><label for="cntKnd">공사종류</label></th>
							<td colspan="2">
								<input type="text" name="cntKnd" id="cntKnd" class="easyui-textbox" value="${result.cntKnd}"/>
							</td>
						</tr>
						<tr>
							<th><span style="color:red;">* </span><label for="svsNam">감독자</label></th>
							<td colspan="2">
								<input type="text" name="svsNam" id="svsNam" class="easyui-textbox" value="${result.svsNam}" required />
							</td>
							<th>준공검사자</th>
							<td colspan="2">
								<input type="text" name="fchNam" class="easyui-textbox" value="${result.fchNam}"/>
							</td>											
						</tr>
						<tr>
							<th><span style="color:red;">* </span><label for="facNum">대표지번</label></th>
							<td colspan="5">
								<select name="bjdCde" id="bjdCde" class="modify_sel_dong easyui-combobox" style="width:40%">
									<c:forEach items="${selDong}" var="selDongObj" varStatus="status">
										<option value="${selDongObj.codeId}">${selDongObj.codeNm}</option>
									</c:forEach>
								</select>
								산 <input type="checkbox" class="modify_sanCde easyui-checkbox" name="modify_sanCde"/>
								<input type="hidden" name="sanCde" />
								지번 <input type="text" class="modify_facNum easyui-numberbox" name="facNum" id="facNum" style="width:70px" required />
								- <input type="text" class="modify_fadNum easyui-numberbox" name="fadNum" style="width:70px"/>
								  <input type="text" id="pnu" name="pnu" value="${result.pnu}" hidden="true"/>
							</td>
						</tr>
						<tr>
							<th>지번상세</th>
							<td colspan="5">
								<input type="text" name="locDes" class="easyui-textbox" value="${result.locDes}" style="width: 100%;"/>
							</td>
						</tr>
						<tr>
							<th><span style="color:red;">* </span><label for="gcnNam">도급자</label></th>
							<td colspan="2">
								<input type="text" name="gcnNam" id="gcnNam" class="easyui-textbox" value="${result.gcnNam}" required />
							</td>
							<th>대표자 성명</th>
							<td colspan="2">
								<input type="text" name="pocNam" class="easyui-textbox" value="${result.pocNam}"/>
							</td>											
						</tr>
						<tr>
							<th>도급자주소</th>
							<td colspan="5">
								<input type="text" name="gcnAdr" class="easyui-textbox" value="${result.gcnAdr}" style="width: 100%;"/>
							</td>
						</tr>						
						<tr>
							<th>계약금액</th>
 							<td colspan="2">
								<input type="text" name="totAmt" class="easyui-numberbox" value="${result.totAmt}"/>
							</td>
							<th><span style="color:red;">* </span><label for="cttYmd">계약일</label></th>
							<td colspan="2">
								<input type="text" name="cttYmd" id="cttYmd" class="easyui-datebox" value="${result.cttYmd}" required />
							</td>
						</tr>
						<tr>
							<th>착공일</th>
							<td colspan="2">
								<input type="text" name="begYmd" class="easyui-datebox" value="${result.begYmd}"/>
							</td>
							<th><span style="color:red;">* </span><label for="fnsYmd">준공기한</label></th>
							<td colspan="2">
								<input type="text" name="fnsYmd" id="fnsYmd" class="easyui-datebox" value="${result.fnsYmd}" required />
							</td>
						</tr>
						<tr>
							<th>준공일</th>
							<td colspan="2">
								<input type="text" name="rfnYmd" class="easyui-datebox" value="${result.rfnYmd}"/>
							</td>
							<th>준공검사</th>
							<td colspan="2">
								<input type="text" name="fchYmd" class="easyui-datebox" value="${result.fchYmd}"/>
							</td>
						</tr>
						<tr>
							<th>비고</th>
							<td colspan="5">
								<input type="text" name="remark" class="easyui-textbox" value="${result.remark}" style="width: 100%;"/>
							</td>
						</tr>
						<tr>
							<th>붙임문서</th>
							<td colspan="3">
								<input type="file" name="atcFile" value="" style="width:100%" />
							</td>
							<td>
								<input type="text" id="oldAtcFile" name="oldAtcFile" readonly="readonly" style="border:0"/>
							</td>
							<td class="atcFileModifyArea">
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="window_footer">
			<div class="button_flat">
				<button class="btn_save_landCenterCntrwkRegstrModify button_flat_normal btn_blue">저장</button>
				<button class="btn_close_landCenterCntrwkRegstrModify button_flat_normal btn_black">취소</button>
			</div>
		</div>
	</div>
</form>