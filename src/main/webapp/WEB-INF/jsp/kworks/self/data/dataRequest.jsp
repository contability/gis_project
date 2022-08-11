<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>

<c:set var="prjCode"><spring:message code='Globals.Prj' /></c:set>

<div class="panel cont">
	<div class="titleArea pa_t_20 m_auto">
		<ul>
			<li class="f_l pa_l_5 pa_b_12"><img src="<c:url value='/images/kworks/system/tit_sub14.png' />"></img></li>
			<li class="f_r pa_r_10 pa_t_10"><span class="f_s_12 color_6">Home &gt; <strong>데이터 요청 관리</strong></span></li>
		</ul>
	</div>
	<div class="content pa_l_20 pa_r_20 m_auto">
			<div class="noticeTb">
				<table>
					<colgroup>
						<col width="20%" />
						<col width="40%" />
						<col width="10%" />
						<col width="20%" />
						<col width="10%" />
					</colgroup>
					<thead>
						<tr>
							<th>요청일시</th>
							<th>파일명</th>
							<th>진행상태</th>
							<th>삭제예정일자</th>
							<th>다운로드</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="obj" items="${list}">
						<tr>
							<td class="left">
								<fmt:formatDate value="${obj.requstDt}" pattern="yyyy-MM-dd HH:mm:ss" var="requstDt" />
								${requstDt}
							</td>
							<td>${obj.requstNm}.zip</td>
							<td>
								<c:forEach var="eps" items="${epsList}">
									<c:if test="${obj.progrsSttus eq eps.cdeValue}">
										${eps.cdeNm}
									</c:if>
								</c:forEach>
							</td>
							<td>
								<fmt:formatDate value="${obj.deletePrearngeDt}" pattern="yyyy-MM-dd" var="deletePrearngeDt" />
								${deletePrearngeDt}	
							</td>
							<td>
								<c:if test="${ obj.progrsSttus eq 'EPS002' }">
									<a href="<c:url value='/my/dtaDownload.do?requstId=${obj.requstId}' />"><img src="<c:url value='/images/kworks/system/download.jpg' />" alt="다운로드" title="다운로드" /></a>
								</c:if>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
</div>