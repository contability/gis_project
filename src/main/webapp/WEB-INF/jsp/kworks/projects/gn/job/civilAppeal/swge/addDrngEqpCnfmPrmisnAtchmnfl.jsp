<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>

<form id="add_drngEqpCnfmPrmisnAtchmnflForm" name="add_drngEqpCnfmPrmisnAtchmnflForm" method="post" enctype="multipart/form-data">
	<div id="add_drngEqpCnfmPrmisnAtchmnfl" class="window_container">
		<div class="window_article">
			<input type="hidden" id="add_drngEqpCnfmPrmisnAtchmnfl_ftr_cde" name="ftrCde" value = ""/>
			<input type="hidden" id="add_drngEqpCnfmPrmisnAtchmnfl_ftr_idn" name="ftrIdn" value = ""/>
			<table class="table_text">
				<tbody>
					<tr>
						<th>제목</th>
						<td>
							<input type="text" name="atchflSj" class="w_210 easyui-textbox" value="" />
						</td>
					</tr>
					<tr>
						<th>파일</th>
						<td>
							<p class="ma_b_10">
								<input type="file" name="file" value=""  />
							</p>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<div class="div_tool_photo_manage">
		<a class="a_save_add_drngEqpCnfmPrmisnAtchmnfl btn_register" href="#" ></a>
		<a class="a_close_add_drngEqpCnfmPrmisnAtchmnfl btn_close" href="#" ></a>
	</div>
</form>