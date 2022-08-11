<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="<c:url value='/webjars/ckeditor/4.5.8/full/ckeditor.js' />"></script>
<script type="text/javascript" src="<c:url value='/webjars/ckeditor/4.5.8/full/adapters/jquery.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/cmmn/ckeditorconfig.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/manage/authorGroup.js' />"></script>
 

<div id="div_authorGroup_list" class="panel cont">

	<h1>권한그룹 관리</h1>
	<hr class="hr_title" />
	
	<div class="t_a_l f_s_15 c_666 bold">총계 : ${paginationInfo.totalRecordCount}</div>
	
	<form:form commandName="searchDTO" id="frm_authorGroup_list" name="frm_authorGroup_list" method="get" >
		<table class="tbl_author_group_list">
			<colgroup>
				<col width="10%">
				<col width="*">
				<col width="20%">
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>권한그룹명</th>
					<th>작성일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="row" items="${rows}" varStatus="status">
					<tr data-authorGroup-no="${row.authorGroupNo}" style="cursor:pointer">
						<%-- <td>${row.authorGroupNo}</td> --%>
						<td>
							${paginationInfo.firstRecordIndex + status.index + 1}
						</td>
						<td>${row.authorGroupNm}</td>
						<td>${row.frstRgsde}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div id="paginate" align="center">
			<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="authorGroupObj.search" />
			<form:hidden path="pageIndex"  />
		</div>			
	</form:form>
	
	<div class="div_btn">
		<a class="a_add_authorGroup" href="#"><img src="<c:url value='/images/kworks/system/btn_regist.png' />" /></a>
	</div>

</div>

<div id="div_author_group_add" class="div_modal div_author_group_add display_n" title="권한 그룹 등록">
	<form id="frm_author_group_add" name="frm_author_group_add" method="post" action="<c:url value='/manage/authorGroup/add.do' />" >
    	<fieldset>
    		<!-- 권한그룹명 -->
    		<div>
    			<span class="title f_s_14 bold">
    				<label for="">권한 그룹 명 </label>
    			</span>
    			<span class="content">
      				<input type="text" name="authorGroupNm" id="txt_author_group_add_nm" maxlength="90"/>
      				
      			</span>
    		</div>
    		<!-- 서비스 접근 권한 -->
    		<div>
    			<div class="div_title bold">
    				<span class="f_l f_s_15">서비스 접근 권한</span>
	    			<span class="f_r">
	    				<a class="a_chk_system" href="#"><img src="<c:url value='/images/kworks/system/btn_select.png' />" alt="전체선택" title="전체선택" /></a>
	    				<a class="a_unchk_system" href="#"><img src="<c:url value='/images/kworks/system/btn_concellation.png' />" alt="전체해제" title="전체해제" /></a>
	    			</span>
    			</div>
    			<ul class="ul_author_group_system f_s_14">
					<c:forEach var="sysData" items="${sysData}">
						<li>
							<input type="checkbox" name="sysId" id="chk_author_group_add_sys_${sysData.sysId}" value="${sysData.sysId}" />
							<label for="chk_author_group_add_sys_${sysData.sysId}">${sysData.sysAlias}</label>
						</li>
					</c:forEach>
    			</ul>
    		</div>
    		<!-- 데이터 접근 권한 -->
    		<div>
    			<div class="div_author_group_data div_title">
    				<span class="f_l bold">데이터 접근 권한</span>
	    			<span class="f_r">
	    				<a class="a_chk_data" href="#"><img src="<c:url value='/images/kworks/system/btn_select.png' />" alt="전체선택" title="전체선택" /></a>
	    				<a class="a_unchk_data" href="#"><img src="<c:url value='/images/kworks/system/btn_concellation.png' />" alt="전체해제" title="전체해제" /></a>
	    			</span>
	    			<span class="f_r bold">
    					데이터 카테고리
    					<select class="select_author_group_data selectBox w_180">
    						<option value="all">:::전체:::</option>
    						<c:forEach var="dataCtgry" items="${dataCtgry}">
    							<option value="${dataCtgry.dataCtgryId}">${dataCtgry.dataCtgryNm}</option>
    						</c:forEach>
    						<option value="BASE_MAP">항공사진</option>
    					</select>
    				</span>
    			</div>
    			<table class="tbl_author_group_datas" class="f_s_14">
    				<colgroup>
    					<col width="300px">
    					<col width="100px">
    					<col width="100px">
    					<col width="100px">
    					<col width="100px">
    				</colgroup>
    				<thead>
						<tr>
							<th>레이어명</th>
							<th>
								<input type="checkbox" class="chk_author_all" id="div_author_group_add_indict_all" value="indictAt" />
								<label for="div_author_group_add_indict_all">표시 여부</label>
							</th>
							<th>
								<input type="checkbox" class="chk_author_all" id="div_author_group_add_edit_all" value="editAt" />
								<label for="div_author_group_add_edit_all">편집 여부</label>
							</th>
							<th>
								<input type="checkbox" class="chk_author_all" id="div_author_group_add_export_all" value="exportAt" />
								<label for="div_author_group_add_export_all">내보내기 여부</label>
							</th>
							<th>
								<input type="checkbox" class="chk_author_all" id="div_author_group_add_prntng_all" value="prntngAt" />
								<label for="div_author_group_add_prntng_all">인쇄 여부</label>
							</th>
						</tr>
					</thead>
					<tbody>
    					<c:forEach var="data" items="${data}">
    						<tr data-id="${data.dataId}" data-category="DATA" class="${data.dataCtgryId}">
								<td class="t_a_l td_layer_name" style="text-indent:10px;">
									<input type="checkbox" name="dataAlias" id="div_author_group_add_data_${data.dataId}" value="${data.dataId}" /> 
									<label for="div_author_group_add_data_${data.dataId}" >${data.dataAlias}</label>
								</td>
								<td class="td_author_item"><input type="checkbox" name="indictAt" /></td>
								<td class="td_author_item"><input type="checkbox" name="editAt" /></td>
								<td class="td_author_item"><input type="checkbox" name="exportAt" /></td>
								<td class="td_author_item"><input type="checkbox" name="prntngAt" /></td>
							</tr>
    					</c:forEach>
     					<c:forEach var="data" items="${baseMaps}">
    						<tr data-id="${data.mapNo}" data-category="BASE_MAP" class="BASE_MAP">
								<td class="t_a_l td_layer_name" style="text-indent:10px;">
									<input type="checkbox" name="dataAlias" id="div_author_group_modify_data_${data.mapNo}" value="${data.mapNo}" /> 
									<label for="div_author_group_modify_data_${data.mapNo}" >${data.title}</label>
								</td>
								<td class="td_author_item"><input type="checkbox" name="indictAt" /></td>
								<td class="td_author_item"><input type="checkbox" name="editAt" /></td>
								<td class="td_author_item"><input type="checkbox" name="exportAt" /></td>
								<td class="td_author_item"><input type="checkbox" name="prntngAt" /></td>
							</tr>
    					</c:forEach>
    				</tbody>
    			</table>
    		</div>
    	</fieldset>
	</form>
</div>

<div id="div_author_group_modify" class="div_modal div_author_group_modify display_n" title="권한 그룹 수정">
	<form id="frm_author_group_modify" name="frm_author_group_modify" method="post">
    	<fieldset>
    		<!-- 권한그룹명 -->
    		<div>
    			<span class="title f_s_14 bold">
    				<label for="">권한 그룹 명 </label>
    			</span>
    			<span class="content">
      				<input type="text" name="authorGroupNm" id="txt_author_group_modify_nm" maxlength="90" />
      			</span>
    		</div>
    		<!-- 서비스 접근 권한 -->
    		<div>
    			<div class="div_title bold">
    				<span class="f_l f_s_15">서비스 접근 권한</span>
	    			<span class="f_r">
	    				<a class="a_chk_system" href="#"><img src="<c:url value='/images/kworks/system/btn_select.png' />" alt="전체선택" title="전체선택" /></a>
	    				<a class="a_unchk_system" href="#"><img src="<c:url value='/images/kworks/system/btn_concellation.png' />" alt="전체해제" title="전체해제" /></a>
	    			</span>
    			</div>
    			<ul class="ul_author_group_system f_s_14">
					<c:forEach var="sysData" items="${sysData}">
						<li>
							<input type="checkbox" name="sysId" id="chk_author_group_modify_sys_${sysData.sysId}" value="${sysData.sysId}" />
							<label for="chk_author_group_modify_sys_${sysData.sysId}">${sysData.sysAlias}</label>
						</li>
					</c:forEach>
    			</ul>
    		</div>
    		<!-- 데이터 접근 권한 -->
    		<div>
    			<div class="div_author_group_data div_title">
    				<span class="f_l bold">데이터 접근 권한</span>
	    			<span class="f_r">
	    				<a class="a_chk_data" href="#"><img src="<c:url value='/images/kworks/system/btn_select.png' />" alt="전체선택" title="전체선택" /></a>
	    				<a class="a_unchk_data" href="#"><img src="<c:url value='/images/kworks/system/btn_concellation.png' />" alt="전체해제" title="전체해제" /></a>
	    			</span>
	    			<span class="f_r bold">
    					데이터 카테고리
    					<select class="select_author_group_data selectBox w_180">
    						<option value="all">:::전체:::</option>
    						<c:forEach var="dataCtgry" items="${dataCtgry}">
    							<option value="${dataCtgry.dataCtgryId}">${dataCtgry.dataCtgryNm}</option>
    						</c:forEach>
    						<option value="BASE_MAP">항공사진</option>
    					</select>
    				</span>
    			</div>
    			<table class="tbl_author_group_datas" class="f_s_14">
    				<colgroup>
    					<col width="300px">
    					<col width="100px">
    					<col width="100px">
    					<col width="100px">
    					<col width="100px">
    				</colgroup>
    				<thead>
    					<tr>
							<th>레이어명</th>
							<th>
								<input type="checkbox" class="chk_author_all" id="div_author_group_modify_indict_all" value="indictAt" />
								<label for="div_author_group_modify_indict_all">표시 여부</label>
							</th>
							<th>
								<input type="checkbox" class="chk_author_all" id="div_author_group_modify_edit_all" value="editAt" />
								<label for="div_author_group_modify_edit_all">편집 여부</label>
							</th>
							<th>
								<input type="checkbox" class="chk_author_all" id="div_author_group_modify_export_all" value="exportAt" />
								<label for="div_author_group_modify_export_all">내보내기 여부</label>
							</th>
							<th>
								<input type="checkbox" class="chk_author_all" id="div_author_group_modify_prntng_all" value="prntngAt" />
								<label for="div_author_group_modify_prntng_all">인쇄 여부</label>
							</th>
						</tr>
    				</thead>
    				<tbody>
    					<c:forEach var="data" items="${data}">
    						<tr data-id="${data.dataId}" data-category="DATA" class="${data.dataCtgryId}">
								<td class="t_a_l td_layer_name" style="text-indent:10px;">
									<input type="checkbox" name="dataAlias" id="div_author_group_modify_data_${data.dataId}" value="${data.dataId}" /> 
									<label for="div_author_group_modify_data_${data.dataId}" >${data.dataAlias}</label>
								</td>
								<td class="td_author_item"><input type="checkbox" name="indictAt" /></td>
								<td class="td_author_item"><input type="checkbox" name="editAt" /></td>
								<td class="td_author_item"><input type="checkbox" name="exportAt" /></td>
								<td class="td_author_item"><input type="checkbox" name="prntngAt" /></td>
							</tr>
    					</c:forEach>
     					<c:forEach var="data" items="${baseMaps}">
    						<tr data-id="${data.mapNo}" data-category="BASE_MAP" class="BASE_MAP">
								<td class="t_a_l td_layer_name" style="text-indent:10px;">
									<input type="checkbox" name="dataAlias" id="div_author_group_modify_data_${data.mapNo}" value="${data.mapNo}" /> 
									<label for="div_author_group_modify_data_${data.mapNo}" >${data.title}</label>
								</td>
								<td class="td_author_item"><input type="checkbox" name="indictAt" /></td>
								<td class="td_author_item"><input type="checkbox" name="editAt" /></td>
								<td class="td_author_item"><input type="checkbox" name="exportAt" /></td>
								<td class="td_author_item"><input type="checkbox" name="prntngAt" /></td>
							</tr>
    					</c:forEach>
    				</tbody>
    			</table>
    		</div>
    	</fieldset>
	</form>
</div>
