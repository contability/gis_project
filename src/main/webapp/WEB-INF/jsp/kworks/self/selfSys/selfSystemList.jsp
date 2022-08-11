<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>
<script type="text/javascript" src="<c:url value='/js/kworks/self/selfSys.js' />"></script>

<div id="div_selfSys_list" class="panel cont">
	<h1>나의 시스템 목록</h1>
	<hr class="hr_title" />
	
	<table>
		<colgroup>
			<col width="10%">
			<col width="30%">
			<col width="*">
		</colgroup>
		<thead>
			<tr>
				<th>시스템 ID</th>
				<th>시스템 명</th>
				<th>시스템 설명</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${result }" var="re">
				<tr data-id="${re.sysId }">
					<td>${re.sysId }</td>
					<td>${re.sysAlias }</td>
					<td>${re.sysDc }</td>				
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="div_btn">
		<a class="a_new_selfSys" href="#"><img src="<c:url value='/images/kworks/system/btn_regist.png' />" /></a>
	</div>
</div>

<!-- 나의시스템 상세보기 팝업 -->
<div id="div_selfSys_view" class="div_modal" title="나의 시스템 상세보기">
	<div class="searchBox">
		<label for="selfSys_sysNm">시스템명</label>
		<input class="w_250" type="text" disabled="disabled" name="sysNm" id="selfSys_view_sysNm">
		<br/>
		<label for="selfSys_sysDc">설명</label>
		<input class="w_250 ma_l_30" type="text" disabled="disabled" name="sysDc" id="selfSys_view_sysDc">
	</div>
	<div class="w100">
		<div class="div_selfSys_list w_420">
			<div class="bd_gray3 pa_b_5 ma_t_10 w100 h_300">
				<div class="bg_ed h_36 w100">
					<span class="f_l pa_t_10 pa_l_20 f_s_12 color_7 bold">메뉴구성</span>
				</div>
				
				<!-- 나의 시스템 메뉴 리스트 -->
				<div class="listBox over_ys h_270">
					<ul class="pa_b_10 f_l pa_t_10 pa_l_10 t_a_l">

					</ul>
				</div>
			</div>
			
		</div>
	</div>
</div>

<!-- 나의시스템 등록 팝업 -->
<div id="div_selfSys_add" class="div_modal" title="나의 시스템 추가">
	<form:form commandName="kwsSys" method="post">
	<div class="searchBox">
		<label for="selfSys_sysNm">시스템 명</label>
		<input class="w_150 " type="text" name="sysNm" id="selfSys_sysNm">
		<label for="selfSys_sysDc" class="pa_l_20">설명</label>
		<input class="w_300" type="text" name="sysDc" id="selfSys_sysDc">
	</div>
	<div class="w100">
	
		<div class="div_basic_list f_l w_300">
			<div class="bd_gray3 pa_b_5 ma_t_10 w100 h_300">
				<div class="bg_ed h_36 w100">
					<span class="f_l pa_t_10 pa_l_5 f_s_12 color_7 bold">기능목록</span>
					<!-- 메뉴 대분류 목록 -->
					<span class="f_r f_s_12 color_6 pa_t_5">
						<select class="select_category pa_l_5 pa_r_5 h_24 w_110">
							<option value="all">==전체==</option>
							<c:forEach items="${menuList }" var="menu">
								<c:if test="${menu.upperMenuId eq '-1' && menu.sysId eq '2'}" >
									<option value="${menu.menuId }" data-sysId="${menu.sysId}">${menu.menuNm }</option>
								</c:if>
								
								<c:if test="${menu.upperMenuId eq '-1'  && menu.sysId ne '2'}" >
									<option value="${menu.menuId }" data-sysId="${menu.sysId}" style="display:none;">${menu.menuNm }</option>
								</c:if>
							</c:forEach>
						</select>
					</span>
					
					<!-- 시스템 목록 -->
					<span class="f_r f_s_12 color_6 pa_r_5 pa_l_5 pa_t_5">
						<select class="select_basic_sys pa_l_5 pa_r_5 h_24 w_110">
							<c:forEach items="${sysList }" var="sys">
								<c:if test="${sys.sysTy eq 'SYSTEM' && sys.sysId ne '1'}" >
								<option value="${sys.sysId }">${sys.sysAlias }</option>
								</c:if>
							</c:forEach>
						</select>
					</span>
				</div>
				
				<!-- 메뉴리스트 -->
				<div class="div_basic_menu listBox over_ys h_270">
					<ul class="pa_b_10 f_l pa_t_10 pa_l_20 t_a_l">
					</ul>
				</div>
			</div>
			<div class="pa_t_10 pa_b_10 f_l">
				<a class="allChecked" href="#"><img src="<c:url value='/images/kworks/system/btn_select.png' />" /></a>
				<a class="allRemove" href="#"><img class="pa_l_5" src="<c:url value='/images/kworks/system/btn_concellation.png' />" /></a>
			</div>
		</div>	
		
		
		<div class="f_l w_27 pa_l_10 pa_t_120">
			<span class="menuAdd"><img src="<c:url value='/images/kworks/system/boardLst_n.png' />" /></span><br/>
			<span class="menuRemove"><img src="<c:url value='/images/kworks/system/boardLst_p.png' />" /></span><br/>
			<span class="menuAllAdd"><img src="<c:url value='/images/kworks/system/boardLst_nn.png' />" /></span><br/>
			<span class="menuAllRemove"><img src="<c:url value='/images/kworks/system/boardLst_pp.png' />" /></span>
		</div>
		
		
		<div class="div_selfSys_list f_l pa_l_10 w_300">
			<div class="bd_gray3 pa_b_5 ma_t_10 w100 h_300">
				<div class="bg_ed h_36 w100">
					<span class="f_l pa_t_10 pa_l_20 f_s_12 color_7 bold">메뉴구성</span>
				</div>
				
				<!-- 나의 시스템 메뉴 리스트 -->
				<div class="listBox over_ys h_270">
					<ul class="pa_b_10 f_l pa_t_10 pa_l_10 t_a_l">

					</ul>
				</div>
			</div>
			<div class="f_l pa_t_10 pa_b_10 f_l">
				<a class="menuGroupAdd" href="#"><img src="<c:url value='/images/kworks/system/btn_menuAdd.png' />" /></a>
				<a class="menuGroupRemove" href="#"><img class="pa_l_5" src="<c:url value='/images/kworks/system/btn_menuDel.png' />" /></a>
			</div>
		</div>
	</div>
	</form:form>
</div>

<!-- 메뉴그룹 추가 팝업 -->
<div id="div_menuGroup_add" class="div_modal" title="메뉴그룹 추가">
	<div>
		<span class="sLabel">메뉴그룹명</span>
		<input class="txt_menuGroupNm" type="text" class="w_200" />
	</div>
</div>