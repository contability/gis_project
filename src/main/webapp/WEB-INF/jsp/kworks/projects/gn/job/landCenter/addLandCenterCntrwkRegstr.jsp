<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui"			 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			 uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		 uri="http://www.springframework.org/tags/form" %>

<form id="addLandCenterCntrwkRegstrAddform" name="addLandCenterCntrwkRegstrAddform" method="post" enctype="multipart/form-data">
	<div id="landCenterCntrwkRegstrAdd" class="window_container">
		<div class="window_article" >
			<table class="table_text">
				<tbody>
					<tr>
						<th>공사명</th>
						<td colspan="3">
							<input type="text" name="cntNam" class="easyui-textbox" value="" style="width: 100%;"/>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="tabs-1">
				<ul class="tab-list-1">
					<li class="active"><a class="tab-control" href="#list_tab1">일반사항</a></li>
				</ul>
			</div>
			<div class="tab-panel-1 active" id="list_tab1" style="height:211px">
				<table class="table_text">			
					<tbody>
						<tr>
							<th>공사부서</th>
							<td colspan="3">
								<select name="cntDept" class="easyui-combobox" style="width:100%">
									<c:forEach items="${cntDeptList}" var="cntDeptList" varStatus="status">
										<option value="${cntDeptList.deptNm}">${cntDeptList.deptNm}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<th>도급자</th>
							<td>
								<input type="text" name="gcnNam" class="easyui-textbox" value=""/>
							</td>
							<th>대표자 성명</th>
							<td>
								<input type="text" name="pocNam" class="easyui-textbox" value=""/>
							</td>											
						</tr>
						<tr>
							<th>계약금액</th>
 							<td>
								<input type="text" name="totAmt" class="easyui-numberbox" value=""/>
							</td>
							<th>계약일</th>
							<td>
								<input type="text" name="cttYmd" class="easyui-datebox" value="" />
							</td>
						</tr>
						<tr>
							<th>착공일</th>
							<td>
								<input type="text" name="begYmd" class="easyui-datebox" value="" />
							</td>
							<th>준공기한</th>
							<td>
								<input type="text" name="fnsYms" class="easyui-datebox" value="" />
							</td>
						</tr>
						<tr>
							<th>준공일</th>
							<td>
								<input type="text" name="rfnYmd" class="easyui-datebox" value="" />
							</td>
							<th>준공검사</th>
							<td>
								<input type="text" name="fchYmd" class="easyui-datebox" value="" />
							</td>
						</tr>
						<tr>
							<th>비고</th>
							<td colspan="3">
								<input type="text" name="remark" class="easyui-textbox" value="" style="width:100%"/>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="window_footer">
			<div class="button_flat">
				<button class="btn_save_landCenterCntrwkRegstrAdd button_flat_normal btn_blue">저장</button>
				<button class="btn_close_landCenterCntrwkRegstrAdd button_flat_normal btn_black">취소</button>
			</div>
		</div>
	</div>
</form>