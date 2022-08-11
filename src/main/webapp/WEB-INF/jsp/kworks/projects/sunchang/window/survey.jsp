<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="prjCode">
	<spring:message code='Globals.Prj'/>
</c:set>

<link type="text/css" rel="stylesheet" href='<c:url value="/css/kworks/window/survey.css"/>'/>

<div class="div_window_survey">
	<div>
		<div class="div_full_content">
			<div id="surveyTitle1">
				「순창형 뉴딜 공간정보시스템 도입」
			</div>
			<div id="surveyTitle2">
				공간정보시스템 활용현황 설문조사
			</div>
			<br>
			<div id="mainText">
				<div>
					본 설문조사는 “순창형 뉴딜 공간정보시스템 도입”사업의 일환으로 순창군 공간정보시스템의 활용현황을 파악하고 향후 GIS 적용 확대를 위한 정보화 추진방안을 마련하기 위한 것입니다. 본 설문을 통하여 순창군 공간정보시스템에 필요한 정보를 파악하고, 공간정보시스템의 수정 및 추가 정보에 관한 내용을 도출하여 업무의 편의를 제공하고자 합니다.
				</div>
				<div>
					본 설문에서는 현재 시험 운영 중인 공간정보시스템의 활용현황을 파악하고 GIS관련 요구사항을 수집하여 업무에 유용한 서비스모델을 도출하기 위한 것이므로, 바쁘시더라도 설문에 응답해 주시면 감사하겠습니다.
				</div>
				<div>
					민원과 지적담당 박윤옥
				</div>
			</div>
			<p class="qst-title">
				1. 귀하의 주요 담당업무는 무엇입니까?
			</p>
			<p>
				<input type="text" name="qst1">
			</p>
			<p class="qst-title">2. 공간정보시스템을 업무에 활용하는 이유는?</p>
			<ul>
				<li>
					<input type="radio" name="qst2" value="업무에 필요로 하는 정보들이 잘 정리되어 있다."><label>업무에 필요로 하는 정보들이 잘 정리되어 있다.</label>
				</li>
				<li>
					<input type="radio" name="qst2" value="타 시스템의 정보들보다 검색이 용이하다."><label>타 시스템의 정보들보다 검색이 용이하다.</label>
				</li>
				<li>
					<input type="radio" name="qst2" value="GIS기술을 활용하여 정보파악이 쉽다."><label>GIS기술을 활용하여 정보파악이 쉽다.</label>
				</li>
				<li>
					<input type="radio" name="qst2" value="최신의 정보를 제공하기 때문이다."><label>최신의 정보를 제공하기 때문이다.</label>
				</li>
				<li>
					<input type="radio" name="qst2" value="제공하는 정보들이 정확하기 때문이다."><label>제공하는 정보들이 정확하기 때문이다.</label>
				</li>
				<li>
					<input type="radio" name="qst2" value="시스템 접근이 용이하기 때문이다."><label>시스템 접근이 용이하기 때문이다.</label>
				</li>
				<li>
					<input type="radio" name = "qst2" value="qst2-etcetera"><label for="qst2-etcetera">기타</label>
					<input type="text" name="qst2" id="qst2-etcetera" disabled>
				</li>
			</ul>
			<p class="qst-title">
				3. 귀하의 전체 업무중에서 공간정보시스템의 활용도는?
			</p>
			<p>
				<span><input type="radio" name="qst3" value="100%"><label>100%</label></span>
				<span><input type="radio" name="qst3" value="75%"><label>75%</label></span>
				<span><input type="radio" name="qst3" value="50%"><label>50%</label></span>
				<span><input type="radio" name="qst3" value="25%"><label>25%</label></span>
				<span><input type="radio" name="qst3" value="0%"><label>0%</label></span>
			</p>
			<p class="qst-title">
				4. 공간정보시스템에서 가장 유용하게 쓰는 기능은 무엇인가?(복수선택 가능)
			</p>
			
				<ul>
					<li>
						<input type="checkbox" name="qst4" value="단순위치검색(주소/건물)"><label>단순위치검색(주소/건물)</label>
					</li>
					<li>
						<input type="checkbox" name="qst4" value="항공사진 조회"><label>항공사진 조회</label>
					</li>
					<li>
						<input type="checkbox" name="qst4" value="연속지적도 조회"><label>연속지적도 조회</label>
					</li>
					<li>
						<input type="checkbox" name="qst4" value="용도지역지구도 조회"><label>용도지역지구도 조회</label>
					</li>
					<li>
						<input type="checkbox" name="qst4" value="보고서용 이미지 저장"><label>보고서용 이미지 저장</label>
					</li>
					<li>
						<input type="checkbox" name="qst4" value="지도출력기능"><label>지도출력기능</label>
					</li>
					<li>
						<input type="checkbox" name="qst4" value="토지/건축물정보 조회"><label>토지/건축물정보 조회</label>
					</li>
					<li>
						<input type="checkbox" name="qst4" value="도로시설물 위치확인"><label>도로시설물 위치확인</label>
					</li>
					<li>
						<input type="checkbox" name="qst4" value="도로시설물 제원정보조회"><label>도로시설물 제원정보조회</label>
					</li>
					<li>
						<input type="checkbox" name="qst4" value="상수관망시설물 위치확인"><label>상수관망시설물 위치확인</label>
					</li>
					<li>
						<input type="checkbox" name="qst4" value="상수관망시설물 제원정보조회"><label>상수관망시설물 제원정보조회</label>
					</li>
					<li>
						<input type="checkbox" name="qst4" value="하수관망시설물  위치확인"><label>하수관망시설물  위치확인</label>
					</li>
					<li>
						<input type="checkbox" name="qst4" value="하수관망시설물 제원정보조회"><label>하수관망시설물 제원정보조회</label>
					</li>
					<li>
						<input type="checkbox" name="qst4" value="qst4-etcetera"><label for="qst4-etcetera">기타</label>
						<input type="text" name="qst4" id="qst4-etcetera" disabled>
					</li>
					
				</ul>
			
			<p class="qst-title">
				5. 공간정보시스템을 활용한 이후, 업무처리에 걸리는 시간이 줄었다고 판단되는가?
			</p>
			<ul>
				<li><input type="radio" name="qst5" value="상당히 줄었다"><label>상당히 줄었다</label></li>
				<li><input type="radio" name="qst5" value="조금 줄었다"><label>조금 줄었다</label></li>
				<li><input type="radio" name="qst5" value="변화가 없다"><label>변화가 없다</label></li>
				<li><input type="radio" name="qst5" value="할 일이 더 많아졌다"><label>할 일이 더 많아졌다</label></li>
				<li>
					<input type="radio" name="qst5" value="qst5-etcetera"><label for="qst5-etcetera">기타</label>
					<input type="text" name="qst5" id="qst5-etcetera" disabled>
				</li>
			</ul>
			<p class="qst-title">
				6. 공간정보시스템을 활용한 이후, 업무처리가 효율적으로 개선되었다고 판단되는가?
			</p>
			<ul>
				<li><input type="radio" name="qst6" value="상당히 개선되었다"><label>상당히 개선되었다</label></li>
				<li><input type="radio" name="qst6" value="조금 개선되었다"><label>조금 개선되었다</label></li>
				<li><input type="radio" name="qst6" value="변화가 없다"><label>변화가 없다</label></li>
				<li><input type="radio" name="qst6" value="더 복잡해졌다"><label>더 복잡해졌다</label></li>
				<li>
					<input type="radio" name="qst6" value="qst6-etcetera"><label for="qst6-etcetera">기타</label>
					<input type="text" name="qst6" id="qst6-etcetera" disabled>
				</li>
			</ul>
			<p class="qst-title">
				7. 공간정보시스템을 업무에 이용한 후의 발생한 효과가 무엇이라고 생각하십니까? 해당하는 항목에 체크하여주십시오.
			</p>
			<div class="table">
				<table border="1px solid black">
				<col width="70px">
				<col width="20px">
				<col width="20px">
				<col width="20px">
				<col width="20px">
				<col width="20px">
					<tr>
						<th height="54px">구분</th>
						<th>정말 그렇다</th>
						<th>그렇다</th>
						<th>보통</th>
						<th>그렇지 않다</th>
						<th>전혀 그렇지 않다</th>
					</tr>
					<tr>
						<td>1. 업무에 항상 최신자료를 이용할 수 있게 되었다.</td>
						<td><input type="radio" name ="qst7_1" tableQstCount ="6" class="qst7" value="1"></td>
						<td><input type="radio" name ="qst7_1" class="qst7" value="2"></td>
						<td><input type="radio" name ="qst7_1" class="qst7" value="3"></td>
						<td><input type="radio" name ="qst7_1" class="qst7" value="4"></td>
						<td><input type="radio" name ="qst7_1" class="qst7" value="5"></td>
					</tr>
					<tr>
						<td>2. 자료수집 및 분석 등 업무처리효율성이 향상되었다.</td>
						<td><input type="radio" name ="qst7_2" class="qst7" value="1"></td>
						<td><input type="radio" name ="qst7_2" class="qst7" value="2"></td>
						<td><input type="radio" name ="qst7_2" class="qst7" value="3"></td>
						<td><input type="radio" name ="qst7_2" class="qst7" value="4"></td>
						<td><input type="radio" name ="qst7_2" class="qst7" value="5"></td>
					</tr>
					<tr>
						<td>3. 지도의 활용성이 용이해졌다.</td>
						<td><input type="radio" name ="qst7_3" class="qst7" value="1"></td>
						<td><input type="radio" name ="qst7_3" class="qst7" value="2"></td>
						<td><input type="radio" name ="qst7_3" class="qst7" value="3"></td>
						<td><input type="radio" name ="qst7_3" class="qst7" value="4"></td>
						<td><input type="radio" name ="qst7_3" class="qst7" value="5"></td>
					</tr>
					<tr>
						<td>4. 비효율적인 업무절차가 개선되었다.</td>
						<td><input type="radio" name ="qst7_4" class="qst7" value="1"></td>
						<td><input type="radio" name ="qst7_4" class="qst7" value="2"></td>
						<td><input type="radio" name ="qst7_4" class="qst7" value="3"></td>
						<td><input type="radio" name ="qst7_4" class="qst7" value="4"></td>
						<td><input type="radio" name ="qst7_4" class="qst7" value="5"></td>
					</tr>
					<tr>
						<td>5. 의사결정 시간이 단축되었다.</td>
						<td><input type="radio" name ="qst7_5" class="qst7" value="1"></td>
						<td><input type="radio" name ="qst7_5" class="qst7" value="2"></td>
						<td><input type="radio" name ="qst7_5" class="qst7" value="3"></td>
						<td><input type="radio" name ="qst7_5" class="qst7" value="4"></td>
						<td><input type="radio" name ="qst7_5" class="qst7" value="5"></td>
					</tr>
					<tr>
						<td>6. 업무상의 문제를 용이하게 파악할 수 있게 되었다.</td>
						<td><input type="radio" name ="qst7_6" class="qst7" value="1"></td>
						<td><input type="radio" name ="qst7_6" class="qst7" value="2"></td>
						<td><input type="radio" name ="qst7_6" class="qst7" value="3"></td>
						<td><input type="radio" name ="qst7_6" class="qst7" value="4"></td>
						<td><input type="radio" name ="qst7_6" class="qst7" value="5"></td>
					</tr>
					<tr>
						<td colspan="6">
							<label for="qst7-etcetera">7. 기타 : </label><input type="text" id="qst7-etcetera" class="qst7" style="width:707px;">
						</td>
					</tr>
				</table>
			</div>
			<p class="qst-title">
				8. 공간정보시스템을 사용하는 것에 대한 애로사항은?
			</p>
			<ul>
				<li><input type="radio" name="qst8" value="특별히 어려운 점은 없음"><label>특별히 어려운 점은 없음</label></li>
				<li><input type="radio" name="qst8" value="공간정보시스템의 내용이 복잡함"><label>공간정보시스템의 내용이 복잡함</label></li>
				<li><input type="radio" name="qst8" value="시스템이 사용하기 어려움"><label>시스템이 사용하기 어려움</label></li>
				<li><input type="radio" name="qst8" value="qst8-concat"><label id="qst8-concat-prefix">내게 필요한 정보 (</label><input type="text" name="qst8" id="qst8-concat" disabled><label id="qst8-concat-suffix">)이/가 없음</label></li>
				<li>
					<input type="radio" name="qst8" value="qst8-etcetera"><label for="qst8-etcetera">기타</label>
					<input type="text" name="qst8" id="qst8-etcetera" disabled>
				</li>
			</ul>
			<p class="qst-title">
				9. 귀하의 업무 편의성 향상을 위해 공간정보시스템에서 우선적으로 보완 혹은 수정되어야 할 사항을 선택하여 주십시오. 
			</p>
			<ul>
				<li><input type="radio" name="qst9" value="다양한 정보의 추가 및 확충"><label>다양한 정보의 추가 및 확충</label></li>
				<li><input type="radio" name="qst9" value="원활한 정보제공"><label>원활한 정보제공</label></li>
				<li><input type="radio" name="qst9" value="분석기능 구현"><label>분석기능 구현</label></li>
				<li><input type="radio" name="qst9" value="홍보 및 교육"><label>홍보 및 교육</label></li>
				<li>
					<input type="radio" name="qst9" value="qst9-etcetera"><label for="qst9-etcetera">기타</label>
					<input type="text" name="qst9" id="qst9-etcetera" disabled>
				</li>
			</ul>
			<p class="qst-title">
				10. 귀하의 업무의 편의를 위하여 공간정보시스템에 추가되어야 할 공간정보는 무엇이라고 생각하십니까? (우선순위를 정하여 주십시오.)
			</p>
			<ul>
				<li><label for="qst10_1">1순위 : </label><input type="text" id="qst10_1" name="qst10" placeholder="ex)지하수관정 위치도"></li>
				<li><label for="qst10_2">2순위 : </label><input type="text" id="qst10_2" name="qst10"></li>
				<li><label for="qst10_3">3순위 : </label><input type="text" id="qst10_3" name="qst10"></li>
			</ul>
			<p class="qst-title">
				11. 귀하의 업무의 편의를 위하여 공간정보시스템에 추가되어야 할 기능(최신기능, 개선필요 기능, 연계 필요 기능)은 무엇이라고 생각하십니까? (우선순위를 정하여 주십시오.)
			</p>
			<ul>
				<li><label for="qst11_1">1순위 : </label><input type="text" id="qst11_1" name="qst11" placeholder="ex)소규모 공사관리 업무 기능"></li>
				<li><label for="qst11_2">2순위 : </label><input type="text" id="qst11_2" name="qst11"></li>
				<li><label for="qst11_3">3순위 : </label><input type="text" id="qst11_3" name="qst11"></li>
			</ul>
		</div>
	</div>
	<%-- <div class="div_tool">
		<a href="#" class="a_confirm">
			<img src="<c:url value='/images/kworks/map/skin2/btn_submit.png'/>" alt="등록" title="등록">
		</a>
		<a href="#" class="a_cancel">
			<img src="<c:url value='/images/kworks/map/skin2/btn_cancel2.png'/>" alt="취소" title="취소">
		</a>
	</div> --%>
</div>