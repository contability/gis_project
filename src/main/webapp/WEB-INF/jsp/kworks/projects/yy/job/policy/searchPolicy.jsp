<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<div id="listPolicyRegstr" class="window_container">
	<div class="window_article">
		<table class="table_text">
			<tbody>
				<tr>
					<th>정책명</th>
					<td colspan="3">
						<input type="text" class="w_205 easyui-textbox" name="plcyTit" id="plcyTit" path="plcyTit" style="width:99%"/>
					</td>						
				</tr>
				<tr>
					<th>대표지번</th>
						<td colspan="5">
							<span>
								<select class="w_190 easyui-combobox" id="selDong" name="selDong"></select>
							</span>
							산 
							<span>
								<input class="easyui-switchbutton" id="checkMauntain" name="checkMauntain"/>
							</span>
							<span>
								<input type="text" class="w_110 easyui-numberspinner" name="mainNum" id="mainNum" path="mainNum"/>
							</span>
							-
							<span>
								<input type="text" class="w_110 easyui-numberspinner" name="subNum" id="subNum" path="subNum"/>
							</span>
						</td>
					</td>
				</tr>
				<tr>
					<th>담당부서</th>
					<td>
						<select name="plcyDep"  path="plcyDep" class="w_205 easyui-combobox" id="plcyDep" style="width:100%">
							<option value="">전체</option>
								<c:forEach items="${policyDeptList}" var="policyDeptList" varStatus="status">
									<option value="${policyDeptList.deptNm}">${policyDeptList.deptNm}</option>
								</c:forEach>
						</select>
					</td>
					<th>도급자</th>
					<td>
						<input type="text" class="w_230 easyui-textbox" name="cttNam" id="cttNam" path="cttNam"/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="window_footer">
		<div class="button_flat">
			<button class="a_reset_policyRegstr button_flat_normal btn_blue">초기화</button>
			<button class="a_search_policyRegstr button_flat_normal btn_blue">검색</button>
		</div>
	</div>
</div>
