<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="addPolicyRegstr" class="window_container">
	<div class="window_article">
		<input type="hidden" name="deptSbCd" class="deptSbCd" path="deptSbCd" id="deptSbCd" value="${result.deptSbCd}" />
		<table class="table_text">
			<tbody>
				<tr>
					<th>정책명</th>
						<td colspan="5"><input type="text" id="plcyTit" class="easyui-textbox" value="${result.plcyTit}" name="plcyTit" style="width: 99%" /></td>
				</tr>
				<tr>
					<th>대표지번</th>
						<td colspan="5">
							<span>
								<select class="w_200 easyui-combobox" id="selDong" name="selDong" id="selDong"></select>
							</span>
							산 
							<span>
								<input class="easyui-switchbutton" id="checkMauntain" name="checkMauntain"/>
							</span>
							<span>
								<input type="text" class="w_100 easyui-numberspinner" name="mainNum" path="mainNum" id="mainNum"/>
							</span>
							-
							<span>
								<input type="text" class="w_100 easyui-numberspinner" name="subNum" path="subNum" id="subNum"/>
							</span>
						</td>
				</tr>
				<tr>
					<th>실과소</th>
						<td colspan="1">
						<select name="plcyDep" class="plcyDep" style="width:100%" id="plcyDep">
								<c:forEach items="${policyDeptList}" var="policyDeptList" varStatus="status">
									<option value="${policyDeptList.deptCode}" ${policyDeptList.deptNm==result.plcyDep ? 'selected' : ''}>${policyDeptList.deptNm}</option>
								</c:forEach>
						</select>
						</td>
					<th>담당부서</th>
						<td colspan="1">
							<span class="span_content"><input type="text" value="${result.deptSbNm}" class="deptSbNm" name="deptSbNm"  path="deptSbNm" id="deptSbNm" /></span>
						</td>
					<th>담당자</th>
						<td colspan="1"><input type="text" class="w_100 easyui-textbox" value="${result.plcyMng}" name="plcyMng" path="plcyMng" id="plcyMng" /></td>
				</tr>
				<tr>
					<th>계약일</th>
						<td><input type="text" class="w_100 easyui-textbox" value="${result.ctrctDt}" id="ctrctDt" name="ctrctDt" path="ctrctDt"/></td>
					<th>착공일</th>
						<td><input type="text" class="w_100 easyui-textbox" value="${result.cttBeg}" name="cttBeg" id="cttBeg" path="cttBeg"/></td>
					<th>준공일</th>
						<td><input type="text" class="w_100 easyui-textbox" value="${result.cttFrn}" name="cttFrn" id="cttFrn" path="cttFrn"/></td>
				</tr>
				<tr>
					<th>도급자 상호</th>
						<td colspan="2"><input type="text" class="easyui-textbox"value="${result.cttNam}" name="cttNam" id="cttNam" path="cttNam" style="width: 99%" /></td>
					<th>공사금액</th>
						<td colspan="2"><input type="text" class="easyui-textbox" value="${result.ctrctamt}" name="ctrctamt" id="ctrctamt" path="ctrctamt" style="width: 99%" /></td>
				</tr>
				<tr>
					<th>비고</th>
						<td colspan="5"><input type="text" class="easyui-textbox"value="${result.rmkExp}" name="rmkExp" id="rmkExp" path="rmkExp" style="width: 99%" /></td>
				</tr>
			</tbody>
		</table>
		<table class="table_text">
			<tbody>
				<tr>
					<th>보고서</th>
					<td>
						<input type="text" id="txa_repo_add_doc" name="repoDoc" path="repoDoc"/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="window_footer">
		<div class="button_flat">
			<button class="btn_save_policyRegstrAdd button_flat_normal btn_blue">저장</button>
			<button class="btn_close_policyRegstrAdd button_flat_normal btn_blue">닫기</button>
		</div>
	</div>
</div>

