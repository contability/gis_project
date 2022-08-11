/**
 * @license Copyright (c) 2003-2020, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function( config ) {
	config.toolbarCanCollapse = false; // 에디터 상단 툴바 접기 기능 
	
	config.language = 'ko';			//언어설정
	//config.uiColor = '#EEEEEE';		//ui 색상
	config.height = '339px';		//Editor 높이
	config.resize_minHeight = '339px';
	config.resize_maxHeight = '339px';
	//config.width = '777px';			//Editor 넓이
	//config.enterMode =CKEDITOR.ENTER_BR;		//엔터키 입력시 br 태그 변경
	//config.shiftEnterMode = CKEDITOR.ENTER_P;	//엔터키 입력시 p 태그로 변경
	//config.startupFocus = true;					// 시작시 포커스 설정
	//config.allowedContent = true;				// 기본적인 html이 필터링으로 지워지는데 필터링을 하지 않는다.
	//config.filebrowserImageUploadUrl = '/include/editor/upload/upload.asp';		//이미지 업로드 경로 (설정하면 업로드 플러그인에 탭이생김)
	//config.filebrowserFlashUploadUrl = '/include/editor/upload/upload.asp';		//플래쉬 업로드 경로 (설정하면 업로드 플러그인에 탭이생김)	
	//config.docType = "<!DOCTYPE html>";		//문서타입 설정
	config.resize_enabled = 'false';
	config.font_defaultLabel='굴림';
	config.fontSize_defaultLabel='12px';
	//한글폰트 추가
	config.font_names = '맑은 고딕/Malgun Gothic;굴림/Gulim;돋움/Dotum;바탕/Batang;궁서/Gungsuh;나눔 명조/나눔명조;휴먼 명조/휴먼명조;';
	config.allowedContent = true;
	config.startupFocus = true;  //시작시 포커스
	//버튼 창 꾸미기
	config.toolbar = [
	                	['Font','FontSize'] ,['Bold', 'Italic', 'Strike', 'Superscript', 'Subscript', 'Underline']
	                 ];
	config.fontSize_sizes = '8px/8px;9px/9px;10px/10px;11px/11px;15px/15px;16px/16px;18px/18px;20px/20px;22px/22px;/24px/24px;26px/26px;28px/28px;36px/36px;';

	//삭제할 버튼 
	config.removeButtons = 'Source,Save,Preview,Cut,Copy,Paste,PasteText,PasteFromWord,Find,Replace,Templates,Scayt,SelectAll,Form,Checkbox,Radio,TextField,Textarea,Select,Button,ImageButton,HiddenField,Blockquote,CreateDiv,Language,BidiRtl,BidiLtr,Anchor,Flash,Smiley,PageBreak,Iframe,Styles,Format,BGColor,ShowBlocks,About';

};
