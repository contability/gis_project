<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui"			 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			 uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		 uri="http://www.springframework.org/tags/form" %>

<form id="drngEqpCnfmPrmisnModifyform" name="drngEqpCnfmPrmisnModifyform" method="post" enctype="multipart/form-data">
	<div id="drngEqpCnfmPrmisnModify" class="window_container">
		<div class="window_article">
			<input type="hidden" id="drngEqpCnfmPrmisnModifyFtrCde" name="ftrCde" value="${result.ftrCde}" />
			<input type="hidden" id="drngEqpCnfmPrmisnModifyFtrIdn" name="ftrIdn" value="${result.ftrIdn}" />
			<input type="hidden" id="drngEqpCnfmPrmisnModifyrRstBjdcd" name="rpstBjdcd" value="${result.rpstBjdcd}" />
			<input type="hidden" id="drngEqpCnfmPrmisnModifyRpstMtlnb" name="rpstMtlnb" value="${result.rpstMtlnb}" />
			<input type="hidden" id="drngEqpCnfmPrmisnModifyPmsCde" name="pmsCde" value="${result.pmsCde}" />
			<input type="hidden" id="drngEqpCnfmPrmisnModifyMopCde" name="mopCde" value="${result.mopCde}" />
			<input type="hidden" id="drngEqpCnfmPrmisnModifyDiaCde" name="diaCde" value="${result.diaCde}" />
			<input type="hidden" id="drngEqpCnfmPrmisnModifyCnMthCde" name="cnMthCde" value="${result.cnMthCde}" />
			<input type="hidden" id="drngEqpCnfmPrmisnModifyDscss" name="dscss" value="${result.dscss}" />
			<table class="table_text">
				<tbody>
					<tr>
						<th>관리번호</th>
						<td>${result.ftrIdn}</td>
					</tr>
				</tbody>
			</table>
			<div class="tabs-1">
				<ul class="tab-list-1">
					<li class="active"><a class="tab-control" href="#list_tab1">인허가사항</a></li>
					<li><a class="tab-control" href="#list_tab2">협의내용</a></li>
					<li><a class="tab-control" href="#list_tab3">배수시설</a></li>
				</ul>
			</div>
			<div class="tab-panel-1 active" id="list_tab1" style="height:244px">
				<table class="table_text">
					<tbody>
						<tr>
							<th>신고일</th>
							<td colspan="2">
								<input type="text" name="lprYmd" id="lprYmd" class="easyui-datebox w_214" value="${result.lprYmd}" />
							</td>
							<th>건축주</th>
							<td colspan="2">
								<input type="text" name="budNam" id="budNam" class="easyui-textbox w_214" value="${result.budNam}" />
							</td>	
						</tr>
						<tr>
							<th>대표지번</th>
							<td colspan="5">
								<select class="modify_sel_bjdcd easyui-combobox w_214">
									<c:forEach items="${selBjdcd}" var="selBjdcdObj" varStatus="status">
										<option value="${selBjdcdObj.codeId}">${selBjdcdObj.codeNm}</option>
									</c:forEach>
								</select>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								산 <input type="checkbox" class="modify_chk_mtlnb easyui-checkbox" />
								&nbsp;&nbsp;&nbsp;&nbsp;
								지번
								&nbsp;
								<input type="text" class="modify_rpstMnnm easyui-textbox w_102" name="rpstMnnm" id="rpstMnnm" value="${result.rpstMnnm}" />
								&nbsp;&nbsp;-&nbsp;&nbsp;
								<input type="text" class="modify_rpstSlno easyui-textbox w_102" name="rpstSlno" value="${result.rpstSlno}"/>
							</td>
						</tr>
						<tr>
							<th>소재지</th>
							<td colspan="5">
								<input type="text" name="bildLcplc" class="easyui-textbox w_550" value="${result.bildLcplc}"/>
							</td>
						</tr>
						<tr>
							<th>도로명주소</th>
							<td colspan="5">
								<input type="text" id="bildRnadr" name="bildRnadr" class="easyui-textbox w_550" value="${result.bildRnadr}"/>
							</td>
						</tr>
						<tr>
							<th>연면적(m<sup>2</sup>)</th>
							<td>
								<input type="text" id="bldAra" name="bldAra" class="easyui-numberbox w_122" value="${result.bldAra}"/>
							</td>
							<th>신청면적(m<sup>2</sup>)</th>
							<td>
								<input type="text" id="reqstAr" name="reqstAr" class="easyui-numberbox w_100" value="${result.reqstAr}"/>
							</td>
							<th>오수량(ton)</th>
							<td>
								<input type="text" id="sdrVol" name="sdrVol" class="easyui-numberbox w_122" value="${result.sdrVol}"/>
							</td>
						</tr>
						<tr>
							<th>민원종류</th>
							<td colspan="3">
								<select class="modify_sel_pms easyui-combobox w_122">
									<c:forEach items="${selPms}" var="selPmsObj" varStatus="status">
										<option value="${selPmsObj.codeId}">${selPmsObj.codeNm}</option>
									</c:forEach>
								</select>
								&nbsp;
								<input type="text" id="pmsEtc" name="pmsEtc" class="easyui-textbox w_190" value="${result.pmsEtc}" />
							</td>
							<th>원인자부담금(원)</th>
							<td>
								<input type="text" name="csAlotm" class="easyui-numberbox w_122" value="${result.csAlotm}"/>
							</td>
						</tr>
						<tr>
							<th>주용도</th>
							<td colspan="5">
								<input type="text" id="bldUse" name="bldUse" class="easyui-textbox w_550" value="${result.bldUse}" />
							</td>
						</tr>
						<tr>
							<th>착공일</th>
							<td colspan="2">
								<input type="text" id="begYmd" name="begYmd" class="easyui-datebox w_214" value="${result.begYmd}" />
							</td>
							<th>준공일</th>
							<td colspan="2">
								<input type="text" id="cntYmd" name="cntYmd" class="easyui-datebox w_214" value="${result.cntYmd}" />
							</td>	
						</tr>
						<tr>
							<th>비고</th>
							<td colspan="5">
								<input type="text" name="rmkExp" class="easyui-textbox w_550" value="${result.rmkExp}" multiline="true" style="height:50px" />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="tab-panel-1" id="list_tab2" style="height:244px">
				<table class="table_text">
					<tbody>
						<tr>
							<th>협의내용&nbsp;
								<span class="f_r">
									<a href="#" class="a_code_list">
										<img src="<c:url value='/images/kworks/map/skin2/btn_listcall.png' />" title="협의내용코드" alt="협의내용코드" />
									</a>
								</span>
							</th>
							<td colspan="5">
								<span id="dscssContext">${result.dscss}</span>
							</td>
						</tr>
						<tr>
							<th>협의내용기타</th>
							<td colspan="5">
								<input type="text" id="dscssEtc" name="dscssEtc" class="easyui-textbox w_550" value="${result.dscssEtc}" multiline="true" style="height:110px"/>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="tab-panel-1" id="list_tab3" style="height:244px">
				<table class="table_text">
					<tbody>
						<tr>
							<th>시공자</th>
							<td colspan="5">
								<input type="text" id="makNam" name="makNam" class="easyui-textbox w_550" value="${result.makNam}" />
							</td>	
						</tr>
						<tr>
							<th>관종류</th>
							<td colspan="5">
								<select class="modify_sel_mop easyui-combobox w_214">
									<c:forEach items="${selMop}" var="selMopObj" varStatus="status">
										<option value="${selMopObj.codeId}">${selMopObj.codeNm}</option>
									</c:forEach>
								</select>
								&nbsp;
								<input type="text" id="mopEtc" name="mopEtc" class="easyui-textbox w_325" value="${result.mopEtc}" />
							</td>
						</tr>
						<tr>
							<th>관경(mm)</th>
							<td colspan="2">
								<select class="modify_sel_dia easyui-combobox w_75" >
									<c:forEach items="${selDia}" var="selDiaObj" varStatus="status">
										<option value="${selDiaObj.codeId}">${selDiaObj.codeNm}</option>
									</c:forEach>
								</select>
								&nbsp;
								<input type="text" id="diaEtc" name="diaEtc" class="easyui-numberbox w_100" value="${result.diaEtc}" />
							</td>
							<th>연장(m)</th>
							<td colspan="2">
								<input type="text" id="pipLen" name="pipLen" class="easyui-numberbox w_214" value="${result.pipLen}" />
							</td>	
						</tr>
						<tr>
							<th>접합방법</th>
							<td colspan="5">
								<select class="modify_sel_cnMth easyui-combobox w_214">
									<c:forEach items="${selCnMth}" var="selCnMthObj" varStatus="status">
										<option value="${selCnMthObj.codeId}">${selCnMthObj.codeNm}</option>
									</c:forEach>
								</select>
								&nbsp;
								<input type="text" id="cnMthEtc" name="cnMthEtc" class="easyui-textbox w_325" value="${result.mopEtc}" />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="window_footer">
				<div class="button_flat">
					<button class="btn_save_drngEqpCnfmPrmisnModify button_flat_normal btn_blue">저장</button>
					<button class="btn_close_drngEqpCnfmPrmisnModify button_flat_normal btn_black">취소</button>
				</div>
			</div>
		</div>
	</div>
</form>