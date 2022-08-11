mapToolObj.dronObj = {
	
	TITLE : "드론",
		
	toggle : function() {
		var that = this;
		if(dronObj.interaction && dronObj.interaction.get("active")) {
			mainObj.setSingleActiveObj(null);
			that.deactive();
		}
		else {
			mainObj.setSingleActiveObj(that);
			that.active();
		}
	},
		
	/**
	 * 기능 활성화
	 */
	active : function() {
		dronObj.active();
	},
	
	/**
	 * 기능 비활성화
	 */
	deactive : function() {
		dronObj.deactive();
		mainObj.defaultActive();
	}
};

var dronObj = {
	
	layer : null,
		
	source : null,
	
	interaction : null,
	
	overlay : null,
	
	isCreated : false,
		
	init : function() {
		var that = this;
		that.addLayer();
		that.addInteractions();
		that.addFeatures();
	},
	
	active : function() {
		var that = this;
		if(!that.isCreated) {
			that.init();
			that.isCreated = true;
		}
		else {
			that.layer.setVisible(true);
			highlightObj.moveFeatures(that.source.getFeatures(), {
				projection : ol.proj.get(MAP.PROJECTION)
			});
		}
		mapObj.getMap().activeInteractions("drag", "dron");
	},
	
	deactive : function() {
		var that = this;
		that.layer.setVisible(false);
		if(that.overlay) {
			that.overlay.remove();
			that.overlay = null;
		}
		dronObj.pathObj.destroy();
	},
	
	addLayer : function() {
		var that = this;
		that.source = new ol.source.Vector();
		that.layer = new ol.layer.Vector({
			source : that.source,
			style : new ol.style.Style({
				fill : new ol.style.Fill({
					color : 'rgba(255, 128, 0, 0.6)'
				}),
				stroke : new ol.style.Stroke({
					color : 'rgba(255, 128, 0, 1)',
					width : 6
				}),
				image : new ol.style.Circle({
					radius : 7,
					fill : new ol.style.Fill({
						color : 'rgba(255, 0, 0, 1)'
					})
				})
			})
		});
		mapObj.getMap().addLayer(that.layer);
	},
	
	addInteractions : function() {
		var that = this;
		
		/*
		that.interaction = new kworks.interaction.Draw({
			source : that.source,
			type : "LineString"
		});
		mapObj.getMap().addInteraction(that.interaction);
		
		$("#export_geojson").click(function() {
			var format = new ol.format.GeoJSON();
			
			var source = that.source;
			var feature = source.getFeatures()[1];
			var str = format.writeFeatures([feature]);
			debugger;
		});
		*/

		that.interaction = new kworks.interaction.Select({
			layers : [that.layer]
		});
		that.interaction.set("id", "dron");
		that.interaction.set("name", "dron");
		mapObj.getMap().addInteraction(that.interaction);
		
		that.interaction.on("select", function(event) {
			var feature = null;
			if(event.selected && event.selected.length > 0) {
				feature = event.selected[0];
			}
			if(feature) {
				
				if(that.overlay) {
					dronObj.pathObj.destroy();
					//mapObj.getMap().removeOverlay(that.overlay);
					that.overlay.remove();
					that.overlay = null;
				}
				
				/*that.overlay = new ol.Overlay({
					element : goog.dom.createDom(goog.dom.TagName.DIV, {'class' : 'dron_video'}),
					offset : [0, -25],
					positioning : 'bottom-center',
					population: 4000,
			        rainfall: 500
				});*/
				
				var tagStr = '';
				//tagStr += "<label id='lbl_index'></label>";
				tagStr += '<a href="#" class="a_close"><img src="' + CONTEXT_PATH + '/images/kworks/common/close.png" /></a>';
				tagStr += '<video width="640" height="400" autoplay="autoplay">';
				//tagStr += '<video width="320" height="200" control="control">';
				//tagStr += '<source src="/video/Dron.mp4" type="video/mp4" />';
				tagStr += '<source src="' + CONTEXT_PATH + '/video/Dron.webm" type="video/webm" />';
				tagStr += '</video>';
				
				/*$(that.overlay.getElement()).html(tagStr);
				that.overlay.setPosition(feature.getGeometry().getFirstCoordinate());
				mapObj.getMap().addOverlay(that.overlay);*/
				
				var divTag = "<div class='ol-overlay-container' style='position:absolute;right:5px;bottom:38px;z-index:9999;'>";
				divTag += "<div class='dron_video' style='position:relative;' >";
				divTag += tagStr;
				divTag += "</div>";
				divTag += "</div>";
				
				that.overlay = $(divTag);
				$("body").append(that.overlay);
				
				/*$(".a_close", that.overlay.getElement()).click(function() {
					mapObj.getMap().removeOverlay(that.overlay);
					that.interaction.removeOverlayFeatures();
					that.overlay = null;
					dronObj.pathObj.destroy();
				});*/
				
				$(".a_close", that.overlay).click(function() {
					//mapObj.getMap().removeOverlay(that.overlay);
					that.overlay.remove();
					that.interaction.removeOverlayFeatures();
					that.overlay = null;
					dronObj.pathObj.destroy();
				});
				
				that.interaction.removeOverlayFeatures();
				dronObj.pathObj.create(feature);
			}
		});
		
	},
	
	addFeatures : function() {
		var that = this;
		var features = [];
		
		//features.push(new ol.Feature(new ol.geom.Point([227912, 445720])));
		
		/*
		var line = [];
		line.push([189614.728306, 568871.967557]);
		line.push([189599.382822, 568895.233277]);
		line.push([189585.392759, 568914.931115]);
		line.push([189573.158689, 568932.441954]);
		line.push([189558.123235, 568954.209376]);
		line.push([189545.461601, 568972.450111]);
		line.push([189533.036893, 568990.439389]);
		line.push([189519.686221, 569009.642719]);
		line.push([189506.160481, 569028.894652]);
		line.push([189492.767636, 569048.230194]);
		line.push([189478.169984, 569069.335711]);
		line.push([189464.695806, 569088.966794]);
		line.push([189451.480371, 569108.056937]);
		line.push([189438.226668, 569127.257156]);
		line.push([189424.711246, 569146.828791]);
		line.push([189411.878862, 569165.462606]);
		line.push([189398.572888, 569184.594897]);
		line.push([189385.520563, 569203.296547]);
		line.push([189372.242310, 569222.909782]);
		line.push([189345.265123, 569261.762280]);
		line.push([189331.088168, 569281.652780]);
		line.push([189318.113847, 569299.944109]);
		line.push([189304.993344, 569319.061382]);
		line.push([189291.989187, 569338.003117]);
		line.push([189278.646932, 569357.368799]);
		line.push([189263.348186, 569379.369061]);
		line.push([189251.563401, 569396.545495]);
		line.push([189224.266371, 569435.120157]);
		line.push([189210.942783, 569454.497157]);
		line.push([189200.750428, 569469.566927]);
		line.push([189239.539774, 569496.436692]);
		line.push([189255.966848, 569473.707796]);
		line.push([189269.630896, 569454.074795]);
		line.push([189283.825264, 569433.235884]);
		line.push([189297.231171, 569413.766553]);
		line.push([189310.630012, 569394.694439]);
		line.push([189323.726511, 569375.987934]);
		line.push([189337.521370, 569356.203374]);
		line.push([189349.866134, 569338.406771]);
		line.push([189363.298473, 569318.772369]);
		line.push([189376.741736, 569299.510628]);
		line.push([189390.060349, 569280.428925]);
		line.push([189403.146819, 569261.219410]);
		line.push([189416.632202, 569241.782903]);
		line.push([189430.375493, 569222.339473]);
		line.push([189443.700567, 569203.368643]);
		line.push([189456.802282, 569183.953944]);
		line.push([189470.283149, 569164.484435]);
		line.push([189485.036570, 569143.578675]);
		line.push([189511.826041, 569105.002541]);
		line.push([189525.004508, 569085.860856]);
		line.push([189538.359113, 569066.375520]);
		line.push([189550.973780, 569047.879005]);
		line.push([189564.331775, 569028.333905]);
		line.push([189577.335033, 569009.349408]);
		line.push([189591.578479, 568988.757017]);
		line.push([189604.777161, 568969.798578]);
		line.push([189617.587435, 568951.033974]);
		line.push([189631.059388, 568931.154456]);
		line.push([189644.166954, 568912.428839]);
		line.push([189654.238070, 568897.278135]);
		line.push([189692.699859, 568923.941993]);
		line.push([189675.257932, 568948.633727]);
		line.push([189663.256200, 568966.336405]);
		line.push([189649.754664, 568986.330185]);
		line.push([189636.389575, 569005.698380]);
		line.push([189621.754069, 569026.678497]);
		line.push([189609.633934, 569044.006004]);
		line.push([189595.448435, 569064.497848]);
		line.push([189582.763589, 569082.526233]);
		line.push([189569.496363, 569101.746101]);
		line.push([189555.948230, 569121.209756]);
		line.push([189541.378369, 569142.384680]);
		line.push([189529.534642, 569159.504302]);
		line.push([189516.014042, 569179.031266]);
		line.push([189502.889017, 569197.995917]);
		line.push([189489.386794, 569217.598498]);
		line.push([189476.173823, 569236.747124]);
		line.push([189462.787131, 569255.648186]);
		line.push([189447.578650, 569277.459675]);
		line.push([189435.616314, 569294.681738]);
		line.push([189420.917887, 569316.077397]);
		line.push([189408.669618, 569333.880615]);
		line.push([189395.355635, 569353.431483]);
		line.push([189381.733506, 569372.927410]);
		line.push([189368.612429, 569391.800557]);
		line.push([189355.422900, 569411.103600]);
		line.push([189341.941083, 569430.714920]);
		line.push([189328.608717, 569450.084092]);
		line.push([189315.283548, 569469.453623]);
		line.push([189302.124094, 569488.611829]);
		line.push([189288.772586, 569508.030498]);
		line.push([189278.395732, 569522.869112]);
		line.push([189317.071348, 569549.887382]);
		line.push([189333.558093, 569526.395917]);
		line.push([189347.069851, 569507.035173]);
		line.push([189360.557082, 569487.630735]);
		line.push([189374.792069, 569467.326307]);
		line.push([189387.980377, 569448.040661]);
		line.push([189414.867153, 569409.037618]);
		line.push([189428.105053, 569389.682000]);
		line.push([189441.324951, 569370.360104]);
		line.push([189454.853922, 569351.050460]);
		line.push([189468.078956, 569332.216266]);
		line.push([189480.594923, 569314.235540]);
		line.push([189494.024025, 569294.645885]);
		line.push([189507.576787, 569274.993954]);
		line.push([189520.679533, 569256.287503]);
		line.push([189535.382193, 569235.266632]);
		line.push([189548.674926, 569216.043887]);
		line.push([189560.870615, 569198.391393]);
		line.push([189575.264283, 569177.456000]);
		line.push([189588.665371, 569157.776969]);
		line.push([189600.679105, 569140.306289]);
		line.push([189627.332924, 569101.847269]);
		line.push([189642.471427, 569079.954076]);
		line.push([189655.577513, 569060.818601]);
		line.push([189667.221298, 569043.858102]);
		line.push([189680.635093, 569024.637938]);
		line.push([189694.104079, 569005.276138]);
		line.push([189708.517757, 568984.520291]);
		line.push([189721.849655, 568965.210092]);
		line.push([189731.310577, 568951.102900]);
		line.push([189769.861836, 568977.448231]);
		line.push([189754.050145, 569000.017768]);
		line.push([189740.201752, 569020.089545]);
		line.push([189725.720689, 569041.332308]);
		line.push([189711.237564, 569062.249813]);
		line.push([189698.534983, 569080.643553]);
		line.push([189686.751075, 569097.619956]);
		line.push([189672.361548, 569118.060844]);
		line.push([189659.326308, 569137.282772]);
		line.push([189646.629631, 569155.602339]);
		line.push([189633.334717, 569175.054631]);
		line.push([189619.843559, 569194.521437]);
		line.push([189606.450419, 569213.786962]);
		line.push([189592.954396, 569233.291035]);
		line.push([189580.097159, 569252.018652]);
		line.push([189566.658669, 569271.172075]);
		line.push([189552.719528, 569291.193919]);
		line.push([189539.659881, 569310.084195]);
		line.push([189526.278349, 569329.587908]);
		line.push([189512.874349, 569349.044095]);
		line.push([189499.520970, 569368.370763]);
		line.push([189486.233723, 569387.531147]);
		line.push([189472.971359, 569406.949005]);
		line.push([189459.596713, 569426.565104]);
		line.push([189446.149188, 569445.847167]);
		line.push([189419.534945, 569484.206442]);
		line.push([189406.143403, 569503.479528]);
		line.push([189392.796109, 569522.893939]);
		line.push([189378.208546, 569544.174319]);
		line.push([189364.768809, 569563.589291]);
		line.push([189355.371639, 569577.352121]);
		line.push([189223.944516, 569452.861334]);
		line.push([189222.768185, 569483.082089]);
		line.push([189222.145239, 569507.201891]);
		line.push([189221.523526, 569532.530456]);
		line.push([189220.972423, 569553.842691]);
		line.push([189220.325786, 569577.668044]);
		line.push([189219.780999, 569600.819435]);
		line.push([189219.349630, 569624.200636]);
		line.push([189218.967063, 569648.015581]);
		line.push([189218.524604, 569671.844500]);
		line.push([189218.086255, 569695.134371]);
		line.push([189217.728298, 569719.236405]);
		line.push([189217.554585, 569743.746984]);
		line.push([189217.236008, 569767.760507]);
		line.push([189216.141138, 569816.361565]);
		line.push([189215.267461, 569838.089601]);
		line.push([189214.688600, 569861.246247]);
		line.push([189214.047226, 569884.300749]);
		line.push([189213.547584, 569908.485903]);
		line.push([189213.142168, 569931.879953]);
		line.push([189212.675203, 569955.582216]);
		line.push([189212.414737, 569978.552051]);
		line.push([189212.027045, 570003.049632]);
		line.push([189211.514264, 570026.699130]);
		line.push([189210.921290, 570049.468860]);
		line.push([189210.500124, 570072.923155]);
		line.push([189209.961302, 570096.949560]);
		line.push([189209.531496, 570119.974213]);
		line.push([189208.981220, 570144.229220]);
		line.push([189208.426924, 570168.165903]);
		line.push([189209.077580, 570185.250571]);
		line.push([189257.348696, 570187.389236]);
		line.push([189258.363261, 570156.685583]);
		line.push([189258.587181, 570132.091733]);
		line.push([189258.849121, 570108.714549]);
		line.push([189259.448517, 570085.078653]);
		line.push([189260.562641, 570042.834683]);
		line.push([189261.740536, 569990.747449]);
		line.push([189262.171192, 569967.647854]);
		line.push([189262.784998, 569943.594135]);
		line.push([189263.265485, 569920.347710]);
		line.push([189263.838683, 569895.263701]);
		line.push([189264.072175, 569872.659564]);
		line.push([189264.655533, 569849.363001]);
		line.push([189265.305006, 569826.767972]);
		line.push([189265.999238, 569802.106405]);
		line.push([189266.324997, 569778.910147]);
		line.push([189266.727483, 569755.277782]);
		line.push([189267.129948, 569729.786591]);
		line.push([189267.526313, 569707.989008]);
		line.push([189268.036881, 569683.629031]);
		line.push([189268.464450, 569660.800532]);
		line.push([189268.859554, 569637.704079]);
		line.push([189269.503476, 569613.975729]);
		line.push([189269.923222, 569589.072990]);
		line.push([189270.331298, 569565.367235]);
		line.push([189270.822773, 569543.866180]);
		line.push([189271.358385, 569519.446982]);
		line.push([189271.767296, 569495.405931]);
		line.push([189272.308130, 569471.993760]);
		line.push([189272.027872, 569455.374872]);
		line.push([189318.251165, 569457.007963]);
		line.push([189317.083021, 569488.279394]);
		line.push([189316.767058, 569509.227140]);
		line.push([189316.428392, 569532.416891]);
		line.push([189315.939882, 569555.892185]);
		line.push([189315.431420, 569579.239249]);
		line.push([189314.810334, 569605.432910]);
		line.push([189314.328168, 569628.271532]);
		line.push([189313.788977, 569653.079846]);
		line.push([189313.345916, 569675.696841]);
		line.push([189312.891097, 569699.141098]);
		line.push([189312.507935, 569720.900990]);
		line.push([189312.027930, 569744.504277]);
		line.push([189311.585868, 569767.588993]);
		line.push([189311.083706, 569791.772581]);
		line.push([189310.507456, 569816.956387]);
		line.push([189310.004591, 569840.294177]);
		line.push([189309.567643, 569865.627890]);
		line.push([189309.176416, 569887.492609]);
		line.push([189308.609561, 569913.685521]);
		line.push([189308.239750, 569934.775923]);
		line.push([189307.824835, 569957.056911]);
		line.push([189307.535319, 569979.643711]);
		line.push([189307.027801, 570005.776252]);
		line.push([189306.568086, 570028.805564]);
		line.push([189306.200277, 570050.157642]);
		line.push([189305.632736, 570075.516510]);
		line.push([189305.128985, 570098.913287]);
		line.push([189304.708969, 570122.742138]);
		line.push([189304.157544, 570146.800766]);
		line.push([189303.628955, 570170.177789]);
		line.push([189303.528870, 570186.621715]);
		line.push([189350.616115, 570188.293817]);
		line.push([189351.606827, 570160.127415]);
		line.push([189352.115439, 570133.437822]);
		line.push([189352.580986, 570111.829056]);
		line.push([189353.167726, 570088.401440]);
		line.push([189353.726317, 570063.632004]);
		line.push([189354.061529, 570040.918466]);
		line.push([189354.475790, 570017.502671]);
		line.push([189355.124540, 569994.253647]);
		line.push([189355.704086, 569970.543089]);
		line.push([189356.103402, 569946.465194]);
		line.push([189356.541811, 569920.321979]);
		line.push([189356.788373, 569900.010859]);
		line.push([189357.313802, 569875.073101]);
		line.push([189357.884543, 569850.432913]);
		line.push([189358.431712, 569825.753651]);
		line.push([189358.972645, 569800.360998]);
		line.push([189359.428760, 569778.941957]);
		line.push([189359.894169, 569756.332692]);
		line.push([189360.322334, 569732.004466]);
		line.push([189360.796961, 569708.584833]);
		line.push([189361.222413, 569685.277736]);
		line.push([189361.675508, 569661.781777]);
		line.push([189362.133496, 569637.852220]);
		line.push([189362.542875, 569614.487313]);
		line.push([189362.963927, 569590.671043]);
		line.push([189363.421259, 569567.275352]);
		line.push([189363.937840, 569545.204649]);
		line.push([189364.582046, 569520.086636]);
		line.push([189365.060764, 569496.809213]);
		line.push([189365.540193, 569456.920583]);
		line.push([189412.362086, 569458.258268]);
		line.push([189411.141084, 569485.757235]);
		line.push([189410.729218, 569510.315387]);
		line.push([189410.303889, 569534.259994]);
		line.push([189409.810256, 569557.515009]);
		line.push([189409.141035, 569582.534741]);
		line.push([189408.656114, 569605.902768]);
		line.push([189408.258710, 569630.068566]);
		line.push([189407.894467, 569654.084082]);
		line.push([189407.417168, 569676.729560]);
		line.push([189406.886773, 569700.334757]);
		line.push([189406.397975, 569722.424714]);
		line.push([189405.882565, 569747.525249]);
		line.push([189405.432873, 569769.921111]);
		line.push([189404.940527, 569792.855304]);
		line.push([189404.337291, 569818.074745]);
		line.push([189403.884922, 569840.627074]);
		line.push([189403.341520, 569866.126753]);
		line.push([189402.898394, 569887.218902]);
		line.push([189402.390922, 569911.002725]);
		line.push([189401.834890, 569934.527668]);
		line.push([189401.395754, 569957.991189]);
		line.push([189401.017461, 569981.477037]);
		line.push([189400.553055, 570006.252421]);
		line.push([189400.031952, 570028.918410]);
		line.push([189399.614553, 570051.704654]);
		line.push([189397.790276, 570101.687018]);
		line.push([189397.372058, 570122.719541]);
		line.push([189397.108438, 570145.799196]);
		line.push([189396.610849, 570169.991204]);
		
		var lineFeature = new ol.Feature(new ol.geom.LineString(line));
		lineFeature.getGeometry().transform("EPSG:5187", "EPSG:5181");
		features.push(lineFeature);
		*/
		
		$.get(CONTEXT_PATH + "/js/gds/dron/poi.json").done(function(geojson) {
			var format = new ol.format.GeoJSON();
			var features = format.readFeatures(geojson);
			for(var i=0, len=features.length; i < len; i++) {
				var feature = features[i];
				feature.getGeometry().transform("EPSG:5181", "EPSG:5187");
			}
			that.source.addFeatures(features);
			
			
			highlightObj.moveFeatures(features, {
				projection : ol.proj.get(MAP.PROJECTION)
			});
		});
		
		
	}
		
};

dronObj.pathObj = {
		
	feature : null,
		
	coordinates : null,
	
	timeoutFn : null,
	
	timeout : [
		6, 2, 3, 1, 1, 1, 1, 3, 2, 2,
		2, 2, 2, 3, 2, 1, 1, 1, 1, 1,
		1, 1, 1, 1, 1, 1, 1, 1, 13, 1,
		2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
		2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
		2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
		2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
		2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
		2, 2, 2, 2, 2, 2, 2, 2, 2, 2
	],
	
	index : 0,
		
	create : function(feature) {
		var that = this;
		that.coordinates = feature.getGeometry().getCoordinates();
		
		that.createFeature();
		that.move();
	},
	
	destroy : function() {
		var that = this;
		clearTimeout(that.timeoutFn);
		
		that.index = 0;
		that.coordinates = [];
		if(that.feature) {
			dronObj.source.removeFeature(that.feature);
			that.feature = null;
		}
	},
	
	move : function() {
		var that = this;
		if(that.index < that.coordinates.length-1) {
		//if(that.index < 70) {
			that.timeoutFn = setTimeout(function() {
				that.index++;
				that.createFeature();
				
				that.move();
			}, that.timeout[that.index] * 1000);
		}
	},
	
	createFeature : function() {
		var that = this;
		//$("#lbl_index").text(that.index);
		
		if(that.feature) {
			dronObj.source.removeFeature(that.feature);
			that.feature = null;
		}
		
		if(that.coordinates && that.coordinates.length > 0) {
			that.feature = new ol.Feature(new ol.geom.Point(that.coordinates[that.index]));
			dronObj.source.addFeatures([that.feature]);
		}
	}
		
};