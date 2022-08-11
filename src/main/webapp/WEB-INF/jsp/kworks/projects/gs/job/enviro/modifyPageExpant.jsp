<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form id="enviroModifyForm" name="enviroModifyForm" method="post" enctype="multipart/form-data">
	<div id="enviroModify" class="window_container">
		<input type="hidden" name="pnu" value="${result.pnu}" />
		<div class="window_article">
			<table id="tableEnviroModify" class="table_text">
				<tbody>
					<tr>
						<th>조사대상</th>
						<td colspan="2">
						<input type="text" class="easyui-textbox" name="expType" style="width:99%" value="${result.expType}"/>
						</td>
						<th>조사자</th>
						<td colspan="2">
						<input type="text" class="easyui-textbox" name="invNam" style="width:99%" value="${result.invNam}"/>
						</td>
					<tr>
					<tr>
						<th>조사장소</th>
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
						<th>분포지 유형</th>
						<td colspan="2">
						<input type="text" class="easyui-textbox" name="disType" style="width:99%" value="${result.disType}"/>
						</td>
						<th>분포 면적</th> <!-- 소수점박스 2자리 -->
						<td colspan="2">
						<input type="text" class="easyui-numberbox" name="disArea" style="width:99%" value="${result.disArea}"/>
						</td>
					</tr>
					<tr>
						<th>조사일</th> <!-- 날짜박스 -->
						<td colspan="2">
						<input type="text" class="easyui-datebox" name="invDate" style="width:99%" value="${result.invDate}"/>
						</td>
						
						<th>생육단계</th>
						<td colspan="2">
						<input type="text" class="easyui-textbox" name="expStep" style="width:99%" value="${result.expStep}"/>
						</td>
					</tr>
					<tr>
						<th>분포 밀도</th>
						<td colspan="2"> <!-- 숫자박스 -->
						<input type="text" class="easyui-numberspinner" name="disDens" style="width:99%" value="${result.disDens}"/>
						</td>
						<th>평균 키</th>
						<td colspan="2"> <!-- 소수점2자리 -->
						<input type="text" class="easyui-numberbox" name="expSize" style="width:99%" value="${result.expSize}"/>
						</td>
					</tr>
					<tr>
						<th>특이사항</th>
						<td colspan="5">
						<input type="text" class="easyui-textbox" name="invNote" style="width:99%" value="${result.invNote}"/>
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