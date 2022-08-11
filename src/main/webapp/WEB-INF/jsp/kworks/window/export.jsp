<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" 		uri="http://www.springframework.org/tags"%>
<!-- project -->
<c:set var="prjCode"><spring:message code='Globals.Prj' /></c:set>
<div class="div_window_export">
	<div>
		<span class="span_title">서약사항</span>
		<div class="div_full_content">
		
		<c:if test="${ prjCode ne 'dh' }">
			<p>
				본인은 공간정보시스템 항공정사영상 출력 및 저장권한 관리담당자에게 의뢰하여 발급(프린트 혹은 이미지) 받은 공개제한자료(항공정사영상 등)가 국가기밀사항임을 인정하고 제반 보안관련 법률, 규정, 지침 등을 성실히 준수하면, 재직 중은 물론 퇴직 후에도 알게 된 모든 기밀사항을 일체 타인에게 누설하지 아니하며, 기밀 누설 시 다음 관계법규에 따라 엄중한 처벌을 받을 것을 서약한다.
			</p>
		</c:if>
		
		<c:if test="${ prjCode eq 'dh' }">
			<p>
				본인은 동해시 공간정보시스템의 자료를 활용함에 있어 다음의 사항을 준수하고 관련 자료의 외부유출 및 관련 규정 위반 시 관계법규에 따라 엄중히 처벌받을 것을 서약합니다.
			</p>
		</c:if>
		
			<br />
			<p class="t_a_c">
				-다  음-
			</p>
			
			<c:if test="${ prjCode ne 'dh' }">
				<ul>
					<li>1. 국가보안법 제 4조 제 1항 제 2호·제5호(국가기밀 누설 등) </li>
					<li>2. 형법 제 99조(일반이적) 및 제 127호(공무상 비밀의 누설) </li>
					<li>3. 군형법 제 80조(군사기밀 누설) </li>
					<li>4. 군사기밀보호법 제12조(누설) 및 제 13조(업무상 누설) </li>
					<li>5. 국가공간정보에 관한 법률 제 30조 ~ 제34조에 관한 사항 </li>
				</ul>
			</c:if>
			
			<c:if test="${ prjCode eq 'dh' }">
				<ul>
					<li>1. 제공받은 자료(파일 출력물 또는 프린트 출력물 등)는 업무용으로만 사용하고 타용도로는 사용하지 않을 것이며, 또한 업무상 취득한 보안사항 누설 또는 위해한 행위를 하였을 때에는 관계법규에 따라 엄중한 처벌을 받을 것을 서약합니다. </li>
					<li>2. 「동해시 공간정보에 관한 조례」 및 「동해시 공간정보 보안관리 규정」 등 모든 보안관련 규정을 반드시 준수하고 제공받은 자료는 사용목적이 완료되면 반드시 자체 폐기할 것을 서약합니다. </li>
					<li>3. 제공받은 자료의 내용이 현황과 상이 하거나 오류 또는 보안위반사항 등을 발견할 경우 사용을 중지하고 공간정보 "제공부서"에 지체없이 통보하며, 제공받은 공간정보 자료의 내용을 임의로 복제·복사하여 판매 또는 제3자에게 배포하지 않겠습니다. </li>
				</ul>
			</c:if>
			
		</div>
	</div>
	<div>
		<span class="span_title">부서명</span>
		<span class="span_content deptName"></span>
		<span class="span_title">사용자명</span>
		<span class="span_content userName"></span>
	</div>
	<div>
		<span class="span_title">
			출력명
		</span>
		<span class="span_content">
			<input class="export_name" type="text" value="" />
		</span>
		<span class="span_title">
			자료구분
		</span>
		<span class="span_content">
			<select class="export_data_se">
				
				<!-- <option value="CLOSED_DOOR">비공개</option> -->
				<option value="OPEN_LIMITATION">공개제한</option>
				<option value="OPEN">공개</option>
				
			</select>
		</span>
	</div>
	<div>
		<span class="span_title">
			출력 사유 (100자 이내)
		</span>
		<span class="span_content">
			<!-- <textarea class="export_resn"></textarea> -->
			<input class="export_resn">
		</span>
	</div>
	<div>
		<input id="chk_export_security_confirm" type="checkbox" />
		<label for="chk_export_security_confirm">위 서약서의 모든 내용에 동의합니다.</label>
	</div>
	<div class="div_tool">
		<a href="#" class="a_confirm" >
			<img src="<c:url value='/images/kworks/map/skin2/request_export.png' />" alt="내보내기 요청" title="내보내기 요청" />
		</a>
		<a href="#" class="a_cancel" >
			<img src="<c:url value='/images/kworks/map/skin2/btn_cancel2.png' />" alt="취소" title="취소" />
		</a>
	</div>
</div>
