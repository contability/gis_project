goog.provide('kworks.Map');

goog.require('ol.Map');

/// kworks.Map 객체 
kworks.Map = function(options) {
	// 측정을 위한 벡터 소스 (측정 기능이 끝나도 객체는 유지하기 위해서 interaction 외부에서 정의)
	var measureSource = new ol.source.Vector();
	
	// 기본 controls 생성
	options.controls = goog.isDef(options.controls) ? options.controls : kworks.control.defaults();
	
	// 기본 interaction 생성 (외부에서 kworks.Map 생성 시 변경 불가)
	if(!goog.isDef(options.interactions)) {
		options.interactions = kworks.interaction.defaults({
			measureSource : measureSource
		});
	}
	goog.base(this, options);
	
	// 측정을 위한 레이어 등록  (측정 기능이 끝나도 객체는 유지하기 위해서 interaction 외부에서 정의)
	kworks.interaction.Measure.createMeasureLayer(this, measureSource);

	// 지도 이동 기능 활성화
	this.activeInteractions("drag");
	
	// 이전/다음 기능 프로퍼티 설정
	this.historyStack = new ol.Collection();
	this.historyIndex = -1;
	this.historyFlag = false;
	
	// 지도 MoveEnd 이벤트
	this.on('moveend', function(){
		// 이전/다음 stack 관리
		if(!this.historyFlag){
			var viewInfo = {
				center : this.getView().getCenter(),
				resolution : this.getView().getResolution(),
				rotation : this.getView().getRotation()
			};
			this.historyStack.push(viewInfo);
			this.historyIndex++;
		}
		else{
			this.historyFlag = false;
		}
	});
	
};
goog.inherits(kworks.Map, ol.Map);

/// interaction 활성/비활성
kworks.Map.prototype.activeInteractions = function(opt_names, opt_ids) {
	var names = new ol.Collection();
	if(goog.isDef(opt_names)) {
		if(goog.isArray(opt_names)) {
			names = new ol.Collection(opt_names);
		}
		else if(goog.isString(opt_names)) {
			names = new ol.Collection([opt_names]);
		}
	}
	
	var ids = new ol.Collection();
	if(goog.isDef(opt_ids)) {
		if(goog.isArray(opt_ids)) {
			ids = new ol.Collection(opt_ids);
		}
		else if(goog.isString(opt_ids)) {
			ids = new ol.Collection([opt_ids]);
		}
	}
	
	var interactions = this.getInteractions();
    interactions.forEach(function(interaction){
    	if(interaction.get("active")) interaction.setActive(false);
    });

    names.forEach(function(name) {
    	interactions.forEach(function(interaction) {
    		if(interaction.get("name") == name) {
    			if(!interaction.get("active")) interaction.setActive(true);
    		}
    	});
    });
    ids.forEach(function(id) {
    	interactions.forEach(function(interaction) {
    		if(interaction.get("id") == id) {
    			if(!interaction.get("active")) interaction.setActive(true);
    		}
    	});
    });
	
};

// 레이어 반환
kworks.Map.prototype.getLayer = function(id) {
	var layers = this.getLayers();
	for(var i=0, len=layers.get("length"); i < len; i++) {
		var layer = layers.item(i);
		if(layer.get("id") == id) {
			return layer;
		}
	}
	return null;
};

//인터렉션 반환
kworks.Map.prototype.getInteraction = function(id) {
	var interactions = this.getInteractions();
	for(var i=0, len=interactions.get("length"); i < len; i++) {
		var interaction = interactions.item(i);
		if(interaction.get("id") == id) return interaction;
	}
};

// 영역 반환
kworks.Map.prototype.getExtent = function() {
	return this.getView().calculateExtent(this.getSize());
};

// 중심 좌표 반환
kworks.Map.prototype.getCenter = function() {
	return this.getView().getCenter();
};

// 축척 반환
kworks.Map.prototype.getScale = function(resolution) {
	if(!resolution) {
		resolution = this.getView().getResolution();
	}
	
	var units = this.getView().getProjection().getUnits();
	var inchesPerUnit = {
	    'm': 39.37,
	    'km': 39370
	};
	var dotsPerInch = 96;
	return resolution * inchesPerUnit[units] * dotsPerInch;
};

// 영역으로 이동
kworks.Map.prototype.moveToExtent = function(extent) {
	this.getView().fit(extent, this.getSize());
};

// 좌표료 이동
kworks.Map.prototype.moveToCenter = function(cooridates) {
	this.getView().setCenter(cooridates);
};

kworks.Map.prototype.convertScaleToResolution = function(scale) {
	var units = this.getView().getProjection().getUnits();
	var inchesPerUnit = {
	    'm': 39.37,
	    'km': 39370
	};
	var dotsPerInch = 96;
	return scale / (inchesPerUnit[units] * dotsPerInch);
};

// 축척으로 이동
kworks.Map.prototype.moveToScale = function(scale) {
	var resolution = this.convertScaleToResolution(scale);
	this.getView().setResolution(resolution);
};

kworks.Map.prototype.moveToZoom = function(zoom) {
	this.getView().setZoom(zoom);
};

// 해상도로 이동
kworks.Map.prototype.moveToResolution = function(resolution) {
	this.getView().setResolution(this.getView().constrainResolution(resolution));
};

// geometry로 이동
kworks.Map.prototype.moveToGeometry = function(geometry, opt_options) {
	var options = opt_options || { scale : 1000, delta : -2 };
	if(geometry instanceof ol.geom.Point) {
		var coordinates = geometry.getCoordinates();
		this.moveToCenter([coordinates[0], coordinates[1]]);
		this.moveToScale(options.scale);
	}
	else {
		var view = this.getView();
		var extent = geometry.getExtent();
		var center = ol.extent.getCenter(extent);
		var resolution = view.constrainResolution(view.getResolutionForExtent(extent, this.getSize()), options.delta);
		this.moveToCenter(center);
		this.moveToResolution(resolution);
	}
};

kworks.Map.prototype.zoomIn = function() {
	var view = this.getView();
	this.beforeRender(ol.animation.zoom({
		resolution: view.getResolution(),
		duration: 250,
		easing: ol.easing.easeOut
	}));
	var resolution = view.constrainResolution(view.getResolution(), 1);
	this.getView().setResolution(resolution);
};

kworks.Map.prototype.zoomOut = function() {
	var view = this.getView();
	this.beforeRender(ol.animation.zoom({
		resolution: view.getResolution(),
		duration: 250,
		easing: ol.easing.easeOut
	}));
	var resolution = view.constrainResolution(view.getResolution(), -1);
	this.getView().setResolution(resolution);
};

//이전/다음 지도 이동
kworks.Map.prototype.moveMapHistory = function(type){
	if(type == "prev"){
		if(this.historyStack.getLength() > 0 && this.historyIndex > 0){
			this.historyIndex--;
		}
		else{
			alert("[이전] 지도화면이 더 이상 없습니다.");
			return false;
		}
	}
	else{
		if(this.historyStack.getLength() > 0 && this.historyIndex > -1 && this.historyIndex < this.historyStack.getLength()-1){
			this.historyIndex++;
		}
		else{
			alert("[다음] 지도화면이 더 이상 없습니다.");
			return false;
		}
	}
	
	var viewInfo = this.historyStack.item(this.historyIndex);
	var center = viewInfo.center;
	var angle = viewInfo.rotation * Math.PI / 180;
	var resolution = viewInfo.resolution;
	
	this.historyFlag = true;
	this.getView().setCenter(center);
	this.getView().setRotation(angle);
	this.getView().setResolution(this.getView().constrainResolution(resolution));
};

kworks.Map.prototype.getHistoryStack = function(){
	return this.historyStack;
};

kworks.Map.prototype.getHistoryIndex = function(){
	return this.historyIndex;
};

kworks.Map.prototype.getHistoryFlag = function(){
	return this.historyFlag;
};

kworks.Map.prototype.clone = function(options) {
	var target = goog.isDef(options.target) ?  options.target : null;
	
	var layers = [];
	for(var i=0, len=this.getLayers().get("length"); i < len; i++) {
		layers.push(this.getLayers().item(i));
	}
	var view = new ol.View({
		projection : this.getView().getProjection(),
		maxResolution : this.getView().maxResolution_,
		minResolution : this.getView().minResolution_
	});
	var controls = new ol.Collection();

	return new kworks.Map({
		target : target,
		layers : layers,
		controls : controls,
		view : view
	});
};


ol.renderer.canvas.TileLayer.prototype.prepareFrame = function(frameState,
		layerState) {

	//
	// Warning! You're entering a dangerous zone!
	//
	// The canvas tile layer renderering is highly optimized, hence
	// the complexity of this function. For best performance we try
	// to minimize the number of pixels to update on the canvas. This
	// includes:
	//
	// - Only drawing pixels that will be visible.
	// - Not re-drawing pixels/tiles that are already correct.
	// - Minimizing calls to clearRect.
	// - Never shrink the canvas. Just make it bigger when necessary.
	//   Re-sizing the canvas also clears it, which further means
	//   re-creating it (expensive).
	//
	// The various steps performed by this functions:
	//
	// - Create a canvas element if none has been created yet.
	//
	// - Make the canvas bigger if it's too small. Note that we never shrink
	//   the canvas, we just make it bigger when necessary, when rotating for
	//   example. Note also that the canvas always contains a whole number
	//   of tiles.
	//
	// - Invalidate the canvas tile range (renderedCanvasTileRange_ = null)
	//   if (1) the canvas has been enlarged, or (2) the zoom level changes,
	//   or (3) the canvas tile range doesn't contain the required tile
	//   range. This canvas tile range invalidation thing is related to
	//   an optimization where we attempt to redraw as few pixels as
	//   possible on each prepareFrame call.
	//
	// - If the canvas tile range has been invalidated we reset
	//   renderedCanvasTileRange_ and reset the renderedTiles_ array.
	//   The renderedTiles_ array is the structure used to determine
	//   the canvas pixels that need not be redrawn from one prepareFrame
	//   call to another. It records while tile has been rendered at
	//   which position in the canvas.
	//
	// - We then determine the tiles to draw on the canvas. Tiles for
	//   the target resolution may not be loaded yet. In that case we
	//   use low-resolution/interim tiles if loaded already. And, if
	//   for a non-yet-loaded tile we haven't found a corresponding
	//   low-resolution tile we indicate that the pixels for that
	//   tile must be cleared on the canvas. Note: determining the
	//   interim tiles is based on tile extents instead of tile
	//   coords, this is to be able to handler irregular tile grids.
	//
	// - We're now ready to render. We start by calling clearRect
	//   for the tiles that aren't loaded yet and are not fully covered
	//   by a low-resolution tile (if they're loaded, we'll draw them;
	//   if they're fully covered by a low-resolution tile then there's
	//   no need to clear). We then render the tiles "back to front",
	//   i.e. starting with the low-resolution tiles.
	//
	// - After rendering some bookkeeping is performed (updateUsedTiles,
	//   etc.). manageTilePyramid is what enqueue tiles in the tile
	//   queue for loading.
	//
	// - The last step involves updating the image transform matrix,
	//   which will be used by the map renderer for the final
	//   composition and positioning.
	//

	var pixelRatio = frameState.pixelRatio;
	var viewState = frameState.viewState;
	var projection = viewState.projection;

	var tileLayer = this.getLayer();
	goog.asserts.assertInstanceof(tileLayer, ol.layer.Tile,
			'layer is an instance of ol.layer.Tile');
	var tileSource = tileLayer.getSource();
	var tileGrid = tileSource.getTileGridForProjection(projection);
	var tileGutter = tileSource.getGutter();
	var z = tileGrid.getZForResolution(viewState.resolution);
	var tilePixelSize = tileSource.getTilePixelSize(z, frameState.pixelRatio,
			projection);
	var tilePixelRatio = tilePixelSize[0]
			/ ol.size.toSize(tileGrid.getTileSize(z), this.tmpSize_)[0];
	var tileResolution = tileGrid.getResolution(z);
	var tilePixelResolution = tileResolution / tilePixelRatio;
	var center = viewState.center;
	var extent;
	if (tileResolution == viewState.resolution) {
		center = this
				.snapCenterToPixel(center, tileResolution, frameState.size);
		extent = ol.extent.getForViewAndSize(center, tileResolution,
				viewState.rotation, frameState.size);
	} else {
		extent = frameState.extent;
	}

	if (layerState.extent !== undefined) {
		extent = ol.extent.getIntersection(extent, layerState.extent);
	}
	if (ol.extent.isEmpty(extent)) {
		// Return false to prevent the rendering of the layer.
		return false;
	}

	var tileRange = tileGrid.getTileRangeForExtentAndResolution(extent,
			tileResolution);

	var canvasWidth = tilePixelSize[0] * tileRange.getWidth();
	var canvasHeight = tilePixelSize[1] * tileRange.getHeight();

	var canvas, context;
	if (!this.canvas_) {
		goog.asserts.assert(!this.canvasSize_,
				'canvasSize is null (because canvas is null)');
		goog.asserts.assert(!this.context_,
				'context is null (because canvas is null)');
		goog.asserts.assert(!this.renderedCanvasTileRange_,
				'renderedCanvasTileRange is null (because canvas is null)');
		context = ol.dom.createCanvasContext2D(canvasWidth, canvasHeight);
		this.canvas_ = context.canvas;
		this.canvasSize_ = [ canvasWidth, canvasHeight ];
		this.context_ = context;
		this.canvasTooBig_ = !ol.renderer.canvas.Layer
				.testCanvasSize(this.canvasSize_);
	} else {
		goog.asserts.assert(this.canvasSize_,
				'non-null canvasSize (because canvas is not null)');
		goog.asserts.assert(this.context_,
				'non-null context (because canvas is not null)');
		canvas = this.canvas_;
		context = this.context_;
		if (this.canvasSize_[0] < canvasWidth
				|| this.canvasSize_[1] < canvasHeight
				|| this.renderedTileWidth_ !== tilePixelSize[0]
				|| this.renderedTileHeight_ !== tilePixelSize[1]
				|| (this.canvasTooBig_ && (this.canvasSize_[0] > canvasWidth || this.canvasSize_[1] > canvasHeight))) {
			// Canvas is too small or tileSize has changed, resize it.
			// We never shrink the canvas, unless
			// we know that the current canvas size exceeds the maximum size
			canvas.width = canvasWidth;
			canvas.height = canvasHeight;
			this.canvasSize_ = [ canvasWidth, canvasHeight ];
			this.canvasTooBig_ = !ol.renderer.canvas.Layer
					.testCanvasSize(this.canvasSize_);
			this.renderedCanvasTileRange_ = null;
		} else {
			canvasWidth = this.canvasSize_[0];
			canvasHeight = this.canvasSize_[1];
			if (z != this.renderedCanvasZ_
					|| !this.renderedCanvasTileRange_
							.containsTileRange(tileRange)) {
				this.renderedCanvasTileRange_ = null;
			}
		}
	}

	var canvasTileRange, canvasTileRangeWidth, minX, minY;
	if (!this.renderedCanvasTileRange_) {
		canvasTileRangeWidth = canvasWidth / tilePixelSize[0];
		var canvasTileRangeHeight = canvasHeight / tilePixelSize[1];
		minX = tileRange.minX
				- Math.floor((canvasTileRangeWidth - tileRange.getWidth()) / 2);
		minY = tileRange.minY
				- Math
						.floor((canvasTileRangeHeight - tileRange.getHeight()) / 2);
		this.renderedCanvasZ_ = z;
		this.renderedTileWidth_ = tilePixelSize[0];
		this.renderedTileHeight_ = tilePixelSize[1];
		this.renderedCanvasTileRange_ = new ol.TileRange(minX, minX
				+ canvasTileRangeWidth - 1, minY, minY + canvasTileRangeHeight
				- 1);
		this.renderedTiles_ = new Array(canvasTileRangeWidth
				* canvasTileRangeHeight);
		canvasTileRange = this.renderedCanvasTileRange_;
	} else {
		canvasTileRange = this.renderedCanvasTileRange_;
		canvasTileRangeWidth = canvasTileRange.getWidth();
	}

	goog.asserts.assert(canvasTileRange.containsTileRange(tileRange),
			'tileRange is contained in canvasTileRange');

	/**
	 * @type {Object.<number, Object.<string, ol.Tile>>}
	 */
	var tilesToDrawByZ = {};
	tilesToDrawByZ[z] = {};
	/** @type {Array.<ol.Tile>} */
	var tilesToClear = [];

	var findLoadedTiles = this.createLoadedTileFinder(tileSource, projection,
			tilesToDrawByZ);

	var useInterimTilesOnError = tileLayer.getUseInterimTilesOnError();

	var tmpExtent = ol.extent.createEmpty();
	var tmpTileRange = new ol.TileRange(0, 0, 0, 0);
	var childTileRange, fullyLoaded, tile, x, y;
	var drawableTile = (
	/**
	 * @param {!ol.Tile} tile Tile.
	 * @return {boolean} Tile is selected.
	 */
	function(tile) {
		var tileState = tile.getState();
		return tileState == ol.TileState.LOADED
				|| tileState == ol.TileState.EMPTY
				|| tileState == ol.TileState.ERROR && !useInterimTilesOnError;
	});
	for (x = tileRange.minX; x <= tileRange.maxX; ++x) {
		for (y = tileRange.minY; y <= tileRange.maxY; ++y) {
			tile = tileSource.getTile(z, x, y, pixelRatio, projection);
			if (!drawableTile(tile) && tile.interimTile) {
				tile = tile.interimTile;
			}
			goog.asserts.assert(tile);
			if (drawableTile(tile)) {
				tilesToDrawByZ[z][tile.tileCoord.toString()] = tile;
				continue;
			}
			fullyLoaded = tileGrid.forEachTileCoordParentTileRange(
					tile.tileCoord, findLoadedTiles, null, tmpTileRange,
					tmpExtent);
			if (!fullyLoaded) {
				// FIXME we do not need to clear the tile if it is fully covered by its
				//       children
				tilesToClear.push(tile);
				childTileRange = tileGrid.getTileCoordChildTileRange(
						tile.tileCoord, tmpTileRange, tmpExtent);
				if (childTileRange) {
					findLoadedTiles(z + 1, childTileRange);
				}
			}

		}
	}

	var i, ii;
	for (i = 0, ii = tilesToClear.length; i < ii; ++i) {
		tile = tilesToClear[i];
		x = tilePixelSize[0] * (tile.tileCoord[1] - canvasTileRange.minX);
		y = tilePixelSize[1] * (canvasTileRange.maxY - tile.tileCoord[2]);
		context.clearRect(x, y, tilePixelSize[0], tilePixelSize[1]);
	}

	/** @type {Array.<number>} */
	var zs = Object.keys(tilesToDrawByZ).map(Number);
	zs.sort(ol.array.numberSafeCompareFunction);
	var opaque = tileSource.getOpaque(projection);
	var origin = ol.extent.getTopLeft(tileGrid.getTileCoordExtent([ z,
			canvasTileRange.minX, canvasTileRange.maxY ], tmpExtent));
	var currentZ, index, scale, tileCoordKey, tileExtent, tileState, tilesToDraw;
	var ix, iy, interimTileRange, maxX, maxY;
	var height, width;
	for (i = 0, ii = zs.length; i < ii; ++i) {
		currentZ = zs[i];
		tilePixelSize = tileSource.getTilePixelSize(currentZ, pixelRatio,
				projection);
		tilesToDraw = tilesToDrawByZ[currentZ];
		if (currentZ == z) {
			for (tileCoordKey in tilesToDraw) {
				tile = tilesToDraw[tileCoordKey];
				index = (tile.tileCoord[2] - canvasTileRange.minY)
						* canvasTileRangeWidth
						+ (tile.tileCoord[1] - canvasTileRange.minX);
				if (this.renderedTiles_[index] != tile) {
					x = tilePixelSize[0]
							* (tile.tileCoord[1] - canvasTileRange.minX);
					y = tilePixelSize[1]
							* (canvasTileRange.maxY - tile.tileCoord[2]);
					tileState = tile.getState();
					if (tileState == ol.TileState.EMPTY
							|| (tileState == ol.TileState.ERROR && !useInterimTilesOnError)
							|| !opaque) {
						context.clearRect(x, y, tilePixelSize[0],
								tilePixelSize[1]);
					}
					if (tileState == ol.TileState.LOADED) {
						if(tile.getImage().width == 1 && tile.getImage().height == 1) {
							context.clearRect(x, y, tilePixelSize[0],
									tilePixelSize[1]);
						}
						else {
							context.drawImage(tile.getImage(), tileGutter,
									tileGutter, tilePixelSize[0], tilePixelSize[1],
									x, y, tilePixelSize[0], tilePixelSize[1]);
						}
					}
					this.renderedTiles_[index] = tile;
				}
			}
		} else {
			scale = tileGrid.getResolution(currentZ) / tileResolution;
			for (tileCoordKey in tilesToDraw) {
				tile = tilesToDraw[tileCoordKey];
				tileExtent = tileGrid.getTileCoordExtent(tile.tileCoord,
						tmpExtent);
				x = (tileExtent[0] - origin[0]) / tilePixelResolution;
				y = (origin[1] - tileExtent[3]) / tilePixelResolution;
				width = scale * tilePixelSize[0];
				height = scale * tilePixelSize[1];
				tileState = tile.getState();
				if (tileState == ol.TileState.EMPTY || !opaque) {
					context.clearRect(x, y, width, height);
				}
				if (tileState == ol.TileState.LOADED) {
					if(tile.getImage().width == 1 && tile.getImage().height == 1) {
						context.clearRect(x, y, tilePixelSize[0],
								tilePixelSize[1]);
					}
					else {
						context.drawImage(tile.getImage(), tileGutter, tileGutter,
								tilePixelSize[0], tilePixelSize[1], x, y, width,
								height);
					}
				}
				interimTileRange = tileGrid.getTileRangeForExtentAndZ(
						tileExtent, z, tmpTileRange);
				minX = Math.max(interimTileRange.minX, canvasTileRange.minX);
				maxX = Math.min(interimTileRange.maxX, canvasTileRange.maxX);
				minY = Math.max(interimTileRange.minY, canvasTileRange.minY);
				maxY = Math.min(interimTileRange.maxY, canvasTileRange.maxY);
				for (ix = minX; ix <= maxX; ++ix) {
					for (iy = minY; iy <= maxY; ++iy) {
						index = (iy - canvasTileRange.minY)
								* canvasTileRangeWidth
								+ (ix - canvasTileRange.minX);
						this.renderedTiles_[index] = undefined;
					}
				}
			}
		}
	}

	this.updateUsedTiles(frameState.usedTiles, tileSource, z, tileRange);
	this.manageTilePyramid(frameState, tileSource, tileGrid, pixelRatio,
			projection, extent, z, tileLayer.getPreload());
	this.scheduleExpireCache(frameState, tileSource);
	this.updateLogos(frameState, tileSource);

	ol.vec.Mat4.makeTransform2D(this.imageTransform_, pixelRatio
			* frameState.size[0] / 2, pixelRatio * frameState.size[1] / 2,
			pixelRatio * tilePixelResolution / viewState.resolution, pixelRatio
					* tilePixelResolution / viewState.resolution,
			viewState.rotation, (origin[0] - center[0]) / tilePixelResolution,
			(center[1] - origin[1]) / tilePixelResolution);
	this.imageTransformInv_ = null;

	return true;
};
