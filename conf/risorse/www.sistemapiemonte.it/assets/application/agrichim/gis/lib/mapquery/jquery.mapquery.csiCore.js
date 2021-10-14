
// estende l'elenco di layers riconosciuti
var CSITypes = $.extend(true, $.MapQuery.Layer.types,
    {
        // TMS con getURL come parametro aggiuntivo
        csi_tms: function(options)
        {
            var o = $.extend(true, {}, $.fn.mapQuery.defaults.layer.all,
                $.fn.mapQuery.defaults.layer.tms,
                options);
            var label = options.label || undefined;
            var url = options.url || undefined;
            var noDelete = options.noDelete || false;
			var cartellaToc = options.cartellaToc || "";
  			var visibility = true;
			if(!(options.visibility==undefined)) visibility = options.visibility;
            var opacity = 1;
            if(!(options.opacity==undefined)) opacity = options.opacity;

            var params = {
                layername: o.layer,
                type: o.format,
                getURL: (o.getURL || get_url_CSI) // CSI: corretto per impostare la geturlcsi di default
                ,visibility: visibility
                ,opacity: opacity
            };
            
            return {
                layer: new OpenLayers.Layer.TMS(label, url, params),
                options: o
            };
        }
        ,
        
        // TileCache
        tilecache: function(options)
        {
            var o = $.extend(true, {}, $.fn.mapQuery.defaults.layer.all,
                $.fn.mapQuery.defaults.layer.tms,
                options);
            var label = options.label || undefined;
            var url = options.url || undefined;
            var noDelete = options.noDelete || false;
			var cartellaToc = options.cartellaToc || "";
  			var visibility = true;
			if(!(options.visibility==undefined)) visibility = options.visibility;
            var opacity = 1;
            if(!(options.opacity==undefined)) opacity = options.opacity;
            
            var params = o.layers;
            return {
                layer: new OpenLayers.Layer.TileCache(label, url, params),
                options: o
            };
        }
        ,
        // TMS con getURL come parametro aggiuntivo e modifica ai colori dei tiles
        // richiede configurazione apache con proxypass
        csi_tms_rgb: function(options)
        {
            var o = $.extend(true, {}, $.fn.mapQuery.defaults.layer.all,
                $.fn.mapQuery.defaults.layer.tms,
                options);
            var label = options.label || undefined;
            var url = options.url || undefined;
            var noDelete = options.noDelete || false;
			var cartellaToc = options.cartellaToc || "";
            var visibility = true;
			if(!(options.visibility==undefined)) visibility = options.visibility;
            var params = {
                type: o.format,
                isBaseLayer: true, //(o.isBaseLayer || false),
                getURL: (o.getURL || undefined), // CSI
                visibility: visibility,
                eventListeners: {
                    tileloaded: function(evt) {
                        var ctx = evt.tile.getCanvasContext();
                        if (ctx) {
                            var imgd = ctx.getImageData(0, 0, evt.tile.size.w, evt.tile.size.h);
                            var pix = imgd.data;
                            for (var i = 0, n = pix.length; i < n; i += 4) {
                                pix[i] = pix[i + 1] = pix[i + 2] = (3 * pix[i] + 4 * pix[i + 1] + pix[i + 2]) / 8;
                            }
                            ctx.putImageData(imgd, 0, 0);
                            evt.tile.imgDiv.removeAttribute("crossorigin");
                            evt.tile.imgDiv.src = ctx.canvas.toDataURL();
                        }
                    }
                }
            };
            return {
                layer: new OpenLayers.Layer.TMS(label, url, params),
                options: o
            };
        },
        
        // WMS con SLD, maxextent, eccezioni
        csi_wms: function(options)
        {
			//dbg.log(options);
            var o = $.extend(true, {}, $.fn.mapQuery.defaults.layer.all,
                    $.fn.mapQuery.defaults.layer.raster,
                    options);
            var noDelete = options.noDelete || false;
			var cartellaToc = options.cartellaToc || "";
            var visibility = true;
			if(!(options.visibility==undefined)) visibility = options.visibility;
            var opacity = 1;
            if(!(options.opacity==undefined)) opacity = options.opacity;			
			var maxExtent = "";
			if(!(options.maxExtent==undefined)) maxExtent = options.maxExtent;
			
			var params = {
                layers: o.layers
                ,transparent: o.transparent
                ,format: o.format
                ,SLD: o.sld // CSI
				,sld_body: o.sld_body // CSI
                ,visibility: visibility
				,exceptions: "application/vnd.ogc.se_inimage"
                ,opacity: opacity
            };
            if(typeof o.wms_parameters != "undefined"){
                params = $.extend(params, o.wms_parameters);
            }
			
			//dbg.log(params);
			//dbg.log(o);
			
            return {
                layer: new OpenLayers.Layer.WMS(o.label, o.url, params, o),
                options: o
            };
        }
        
    });

function retrieveWMSparamsAndAddToMap(idMap, options) {

	format = new OpenLayers.Format.WMSCapabilities();

    OpenLayers.Request.GET({
        url: options.url,
        params: {
            SERVICE: "WMS",
            //VERSION: "1.1.0",
            REQUEST: "GetCapabilities"
        },
        success: function(request) {
			
			//dbg.log(options)
			
            var doc = request.responseXML;
            if (!doc || !doc.documentElement) {
                doc = request.responseText;
            }
            var capabilities = format.read(doc);
			//dbg.log(capabilities)
			
			// livelli da caircare
			var lay2load = options.layers.split(",");
			
			// livelli del WMS
			var GC_layers = capabilities.capability.layers;
			
			for (var i=0; i<GC_layers.length; i++) {
				for (var k=0; k<lay2load.length; k++) {
					
					if (lay2load[k]==GC_layers[i].name) {
						
						//dbg.log(lay2load[k]+" == "+GC_layers[i].name);
						//dbg.log(GC_layers[i].llbbox);
						
						// MASSIMA ESTENSIONE
						if (typeof(GC_layers[i].llbbox)!="undefined") {
							options.maxExtent = GC_layers[i].llbbox;
							//debuga = GC_layers[i].llbbox;
							// conversione da LatLong al sistema di riferimento della mappa
							var mapProjection = mappa.getProjection();
							var p1 = new OpenLayers.LonLat(options.maxExtent[0] , options.maxExtent[1])
								.transform(new OpenLayers.Projection("EPSG:4326"),new OpenLayers.Projection(mapProjection));
							var p2 = new OpenLayers.LonLat(options.maxExtent[2] , options.maxExtent[3])
								.transform(new OpenLayers.Projection("EPSG:4326"),new OpenLayers.Projection(mapProjection));
							options.maxExtent = [p1.lon, p1.lat, p2.lon, p2.lat];
						}
						//dbg.log(options.maxExtent);
						
						//dbg.log(GC_layers[i])
						
						// URL LEGENDA
						if (typeof(GC_layers[i].styles)!="undefined") {
							if (GC_layers[i].styles.length>0) {
								//dbg.log(GC_layers[i].styles[0].legend.href);
								options.legend = {url:GC_layers[i].styles[0].legend.href};
								//options.legend = {url:"http://vixra.files.wordpress.com/2012/02/stop1.png"};
								//{url:'legendimage.png'}
							}
						}
					}
					
				}				
			}
			//dbg.log(options)
			jQuery(idMap).data("mapQuery").layers(options);
        },
        failure: function() {
            alert("Trouble getting capabilities doc");
            OpenLayers.Console.error.apply(OpenLayers.Console, arguments);
        }
    });
	
	
	
}

    //jQuery('#map').data("mapQuery").layers({
    //        type:'wms',
    //        label:'Motore Semantico',
    //        url:'http://osgis.csi.it/ws/motore_semantico/wms_motore_semantico?',
    //        layers: 'prod_prgc,aree_produttive_50000mq,centri_ricerca_scientifica,areali_strategici',
    //        });


