<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
<title>SSO Login Test</title>
</head>
<body>
	<form action="<c:url value='/ssoLoginTestAction.do'/>" method="POST">
		<div>
			<label for="userId">사용자ID</label>
			<input type="text" id="userId" name="userId" value="TEST" />
		</div>
		<div>
			<label for="userNm">사용자명</label>
			<input type="text" id="userNm" name="userNm" value="TEST" />
		</div>
		<div>
			<label for="deptCode">부서코드</label>
			<input type="text" id="deptCode" name="deptCode" value="GDS" />
		</div>
		<input type="submit" value="전송" />
	</form>
</body>