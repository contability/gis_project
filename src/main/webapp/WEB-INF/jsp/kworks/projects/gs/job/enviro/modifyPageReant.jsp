<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form id="reantModifyForm" name="reantModifyForm" method="post" enctype="multipart/form-data">
	<div id="reantModify" class="window_container">
		<input type="hidden" name="pnu" value="${result.pnu}" />
		<div class="window_article">
			<table id="tableReantModify" class="table_text">
				<tbody>
					<tr>
						<th>작업내용</th>
						<td colspan="5">
						<input type="text" class="easyui-textbox" name="jobNote" style="width:99%" value="${result.jobNote}"/>
						</td>
					<tr>
					<tr>
						<th>주소</th>
						<td colspan="5">
							<span>
								<select class="w_130 easyui-combobox" id="selDong" name="selDong" id="selDong"></select>
							</span>
							산 
							<span>
								<input class="easyui-switchbutton" id="checkMauntain" name="checkMauntain"/>
							</span>
							<span>
								<input type="text" class="w_130 easyui-numberspinner" name="mainNum" path="mainNum" id="mainNum"/>
							</span>
							-
							<span>
								<input type="text" class="w_130 easyui-numberspinner" name="subNum" path="subNum" id="subNum"/>
							</span>
						</td>
					</tr>
					<tr>
						<th>작업인원</th>
						<td colspan="2">
						<input type="text" class="easyui-numberspinner" name="jobPers" style="width:99%" value="${result.jobPers}"/>
						</td>
						<th>작업유형</th> <!-- 소수점박스 2자리 -->
						<td colspan="2">
						<input type="text" class="easyui-textbox" name="jobType" style="width:99%" value="${result.jobType}"/>
						</td>
					</tr>
					<tr>
						<th>작업일</th> <!-- 날짜박스 -->
						<td colspan="2">
						<input type="text" class="easyui-datebox" name="jobDate" style="width:99%" value="${result.jobDate}"/>
						</td>
						
						<th>간성읍<br>작업면적</th>
						<td colspan="2">
						<input type="text" class="easyui-numberbox" name="areaGs" style="width:99%" value="${result.areaGs}"/>
						</td>
					</tr>
					<tr>
						<th>거성읍<br>작업면적</th>
						<td colspan="2"> <!-- 숫자박스 -->
						<input type="text" class="easyui-numberbox" name="areaGj" style="width:99%" value="${result.areaGj}"/>
						</td>
						<th>현내면<br>작업면적</th>
						<td colspan="2"> <!-- 소수점2자리 -->
						<input type="text" class="easyui-numberbox" name="areaHn" style="width:99%" value="${result.areaHn}"/>
						</td>
					</tr>
					<tr>
						<th>죽원면<br>작업면적</th>
						<td colspan="2"> <!-- 숫자박스 -->
						<input type="text" class="easyui-numberbox" name="areaJw" style="width:99%" value="${result.areaJw}"/>
						</td>
						<th>토성면<br>작업면적</th>
						<td colspan="2"> <!-- 소수점2자리 -->
						<input type="text" class="easyui-numberbox" name="areaTs" style="width:99%" value="${result.areaTs}"/>
						</td>
					</tr>
					<tr>
						<th>특이사항</th>
						<td colspan="5">
						<input type="text" class="easyui-textbox" name="etcNote" style="width:99%" value="${result.etcNote}"/>
						</td>
					</tr>
					
				</tbody>
			</table>
		</div>
		<div class="window_footer">
			<div class="button_flat">
				<button id="btn_update" class="button_flat_normal btn_blue">저장</button>
				<button id="btn_close" class="button_flat_normal btn_black">닫기</button>
			</div>
		</div>
	</div>
</form>