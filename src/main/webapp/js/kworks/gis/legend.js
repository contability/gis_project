var legendObj = {
		
	/**
	 * 범례 그리기 (심볼형, 선형, 면형)
	 * @param {Element} canvas Canvas 요소
	 * @param {Object} rule SLD Rule 정보
	 * @param {Number} width 너비
	 * @param {Number} height 높이
	 */
	draw : function(canvas, rule, width, height) {
		var that = this;
		
		if(!width) width = 16;
		if(!height) height = 16;
		
		var ctx = canvas.getContext("2d");
		ctx.clearRect(0, 0, width, height);
		
		if(rule.line) {
			that.drawLine(ctx, rule.line, width, height);
		}
		else if(rule.point) {
			that.drawSymbol(ctx, rule.point, width, height);
		}
		else if(rule.polygon) {
			that.drawPolygon(ctx, rule.polygon, width, height);
		}
	},
	
	/**
	 * 텍스트 범례 그리기
	 * @param {Object} ctx Canvas context
	 * @param {Object} text Text Symbolizer 정보
	 * @param {Number} width 너비
	 * @param {Number} height 높이
	 */
	drawText : function(ctx, text, width, height) {
		if(!width) width = 16;
		if(!height) height = 16;
		ctx.clearRect(0, 0, width, height);
		ctx.strokeRect(0, 0, width, height);
		ctx.strokeStyle = "#000000";
		ctx.textAlign = "center";
		ctx.textBaseline = "middle";
		ctx.font = "bold 12px Dotum";
		ctx.fillStyle = text.fill;
		ctx.globalAlpha = text.fillOpacity;
		ctx.fillText("T", width/2, height/2);
	},
	
	drawLabel : function(ctx, text, width, height) {
		ctx.clearRect(0, 0, width, height);

		ctx.strokeStyle = "#000000";
		
		ctx.lineWidth = 1;
		ctx.setLineDash([1, 1]);
		ctx.globalAlpha = 0.3;
		ctx.beginPath();
		ctx.moveTo(0, (height / 2));
		ctx.lineTo(width, (height / 2));
		ctx.moveTo((width / 2), 0);
		ctx.lineTo((width / 2), height);
		ctx.closePath();
		ctx.stroke();
		ctx.strokeRect(0, 0, width, height);
		
		ctx.textAlign = canvasUtils.toLabelAlign(text.anchor.charAt(0));
		ctx.textBaseline = canvasUtils.toTextBaseline(text.anchor.charAt(1));
		ctx.font = canvasUtils.toFont(text);
		
		if (text.haloRadius > 0 && text.haloOpacity > 0) {
			ctx.setLineDash([1, 0]);
			ctx.globalAlpha = text.haloOpacity;
			ctx.strokeStyle = text.halo;
			ctx.lineWidth = text.haloRadius;
			ctx.strokeText("T", width/2, height/2);
		}
		
		ctx.fillStyle = text.fill;
		ctx.globalAlpha = text.fillOpacity;
		ctx.fillText("T", width/2, height/2);
	},
		
	/**
	 * 심볼 범례 그리기
	 * @param {Object} ctx Canvas context
	 * @param {Object} text Point Symbolizer 정보
	 * @param {Number} width 너비
	 * @param {Number} height 높이
	 */
	drawSymbol : function(ctx, point, width, height) {
		ctx.clearRect(0, 0, width, height);
		
		var img = new Image();
		if(point.type == "base64") {
			img.src = "data:image/png;base64," + point.resource;
			var imgWidth = img.width;
			var imgHeight = img.height;
			var x = (width / 2) - (imgWidth / 2);
			var y = (height / 2) - (imgHeight / 2);
			ctx.drawImage(img, x, y, imgWidth, imgHeight);
		}
		else {
			img.src = MAP.SYMBOL_URL + "/" + point.resource;
			img.onload = function() {
				var imgWidth = img.width;
				var imgHeight = img.height;
				var x = (width / 2) - (imgWidth / 2);
				var y = (height / 2) - (imgHeight / 2);
				ctx.drawImage(img, x, y, imgWidth, imgHeight);
			};
		}
	},
	
	/**
	 * 심볼 범례 그리기
	 * @param {Object} ctx Canvas context
	 * @param {Object} point Point Symbolizer 정보
	 * @param {Number} width 너비
	 * @param {Number} height 높이
	 */
	previewSymbol : function(ctx, point, width, height) {
		ctx.clearRect(0, 0, width, height);
		
		// Lks : 가이드 라인
		ctx.strokeStyle = "#000000";
		ctx.lineWidth = 1;
		ctx.setLineDash([1, 1]);
		ctx.globalAlpha = 0.3;
		ctx.beginPath();
		ctx.moveTo(0, (height / 2));
		ctx.lineTo(width, (height / 2));
		ctx.moveTo((width / 2), 0);
		ctx.lineTo((width / 2), height);
		ctx.closePath();
		ctx.stroke();
		ctx.strokeRect(0, 0, width, height);
		
		// Lks : 심볼에 투명도 속성이 없음.
		ctx.globalAlpha = 1.0;
		
		// Lks : 기준위치에 그리기
		var anchor = canvasUtils.toAnchor(point.anchor);
		var img = new Image();
		if(point.type == "base64") {
			img.src = "data:image/png;base64," + point.resource;
			var imgWidth = img.width;
			var imgHeight = img.height;
			var x = (width / 2) - (imgWidth * (1 - anchor[0]));
			var y = (height / 2) - (imgHeight * (1 - anchor[1]));
			ctx.drawImage(img, x, y, imgWidth, imgHeight);
		}
		else {
			img.src = MAP.SYMBOL_URL + "/" + point.resource;
			img.onload = function() {
				var imgWidth = img.width;
				var imgHeight = img.height;
				var x = (width / 2) - (imgWidth * (1 - anchor[0]));
				var y = (height / 2) - (imgHeight * (1 - anchor[1]));
				ctx.drawImage(img, x, y, imgWidth, imgHeight);
			};
		}
	},
	
	/**
	 * 라인 범례 그리기
	 * @param {Object} ctx Canvas context
	 * @param {Object} text Line Symbolizer 정보
	 * @param {Number} width 너비
	 * @param {Number} height 높이
	 */
	drawLine : function(ctx, line, width, height) {
		ctx.clearRect(0, 0, width, height);
		ctx.beginPath();
		ctx.moveTo(0, height);
		ctx.lineTo(width*0.40, 0);
		ctx.lineTo(width*0.60, height);
		ctx.lineTo(width, 0);
		ctx.globalAlpha = line.strokeOpacity;
		ctx.setLineDash(canvasUtils.toLineDash(line.strokeDasharray));
		ctx.lineWidth = line.strokeWidth;
		ctx.strokeStyle = line.stroke;
		ctx.stroke();
	},
	
	/**
	 * 폴리곤 범례 그리기
	 * @param {Object} ctx Canvas context
	 * @param {Object} text Polygon Symbolizer 정보
	 * @param {Number} width 너비
	 * @param {Number} height 높이
	 */
	drawPolygon : function(ctx, polygon, width, height) {
		ctx.clearRect(0, 0, width, height);
		if(polygon.fill) {
			ctx.globalAlpha = polygon.fillOpacity;
			ctx.fillStyle = polygon.fill;
			ctx.fillRect(0, 0, width, height);
		}
		if(polygon.stroke) {
			ctx.setLineDash(canvasUtils.toLineDash(polygon.strokeDasharray));
			ctx.globalAlpha = polygon.strokeOpacity;
			ctx.lineWidth = polygon.strokeWidth;
			ctx.strokeStyle = polygon.stroke;
			ctx.strokeRect(0, 0, width, height);
		}
	}	
	
};