var parametriJSON = {
	"map": {
		"numZoomLevels": 21,
		"projectionCode": "EPSG:32632",
		"maxExtent": [
			-10198294.6545,
			-5596920.6825,
			11389716.6914,
			15991090.6634
		],
		"initExtent": [
			353279,
			4972080,
			430854,
			5010000
		],
		"VStyle": "jquery-ui-1-10-4-agrichim",
		"mapsize_h": "550px",
		"mapsize_w": "800px"
	},
	"overview": {
		"width": "150",
		"height": "150",
		"position": [
			"right",
			"bottom"
		],
		"windowed": false,
		"maximized": false,
		"layers": [
			/*{
				"type": "csi_tms",
				"label": "",
				"visibility": true,
				"opacity": 1,
				"locked": false,
				"datasource": "http:\/\/<WEB_SERVER_HOST:PORT>\/WEBCAT\/STORICO\/TMS_32632\/SFONDI\/regp_sfondo_bdtre\/",
				"format": "png",
				"layers": "",
				"singleTile": true,
				"mapLayers": [
				]
			},*/
			{
				"type": "csi_wms",
				"label": "",
				"visibility": true,
				"opacity": 1,
				"locked": false,
				"datasource": "http:\/\/<WEB_SERVER_HOST:PORT>\/mapproxy\/service?",
				"format": "",
				"layers": "regp_sfondo_bdtre",
				"singleTile": true,
				"mapLayers": [
				]
			}/*,
			{
				"type": "csi_wmts",
				"label": "",
				"visibility": true,
				"opacity": 1,
				"locked": false,
				"datasource": "http:\/\/<WEB_SERVER_HOST:PORT>\/mapproxy\/wmts\/1.0.0\/WMTSCapabilities.xml",
				"format": "png",
				"layers": "regp_sfondo_bdtre_wmts",
				"singleTile": false,
				"mapLayers": [
				]
			}*/
		]
	},
	"toc": {
		"type": "minimal",
		"showLegend": "0",
		"hideToc": false
	},
	"mousePosition": {
		"idDiv": "",
		"labelx": "X",
		"labely": "Y",
		"precision": 0
	},/*
	"multiMap": {
		"idPrimaryMap": "map",
		"nSeconday": 1,
		"cloneLayers": false,
		"continuousMap": true,
		"divContainers": [
			"mappa2",
			"",
			"",
			""
		],
		"layers": [
			"[{\"type\":\"csi_wms\",\"label\":\"Limiti Comunali\",\"visibility\":true,\"opacity\":1,\"locked\":false,\"datasource\":\"http:\/\/<WEB_SERVER_HOST:PORT>\/ws\/taims\/rp-01\/taimslimammwms\/wms_limitiAmm?\",\"format\":\"\",\"layers\":\"LimitiComunali\",\"singleTile\":true,\"mapLayers\":[]},{\"type\":\"csi_tms\",\"label\":\"Sfondo Cartografico Piemonte\",\"visibility\":true,\"opacity\":1,\"locked\":false,\"datasource\":\"http:\/\/<WEB_SERVER_HOST:PORT>\/cataloghiradex_f\/cataloghi_TMS\/sfondi\/sfondo_europa_piemonte\/\",\"format\":\"png\",\"layers\":\"\",\"singleTile\":0,\"mapLayers\":[]},{\"type\":\"csi_tms\",\"label\":\"Ortofoto Provincia Torino 2006\",\"visibility\":false,\"opacity\":1,\"locked\":false,\"datasource\":\"http:\/\/<WEB_SERVER_HOST:PORT>\/WEBCAT\/STORICO\/TMS_32632\/ORTOIMMAGINI\/pvto_ortofoto_2006\/\",\"format\":\"png\",\"layers\":\"\",\"singleTile\":0,\"mapLayers\":[]},{\"type\":\"csi_tms\",\"label\":\"CTP Raster Trasparente\",\"visibility\":false,\"opacity\":1,\"locked\":false,\"datasource\":\"http:\/\/<WEB_SERVER_HOST:PORT>\/WEBCAT\/STORICO\/TMS_32632\/ORTOIMMAGINI\/pvto_ortofoto_2006\/\",\"format\":\"png\",\"layers\":\"\",\"singleTile\":0,\"mapLayers\":[]},{\"type\":\"csi_tms\",\"label\":\"CTP Raster\",\"visibility\":false,\"opacity\":1,\"locked\":false,\"datasource\":\"http:\/\/<WEB_SERVER_HOST:PORT>\/WEBCAT\/STORICO\/TMS_32632\/CARTOGRAFIE\/pvto_ctp_2013\/\",\"format\":\"png\",\"layers\":\"\",\"singleTile\":0,\"mapLayers\":[]},{\"type\":\"csi_tms\",\"label\":\"Sfondo Regione Piemonte\",\"visibility\":false,\"opacity\":1,\"locked\":false,\"datasource\":\"http:\/\/<WEB_SERVER_HOST:PORT>\/cataloghiradex_f\/cataloghi_TMS\/sfondi\/sfondoregione\/\",\"format\":\"png\",\"layers\":\"\",\"singleTile\":0,\"mapLayers\":[]},{\"type\":\"csi_tms\",\"label\":\"Ortofoto Regione Piemonte 2006\",\"visibility\":false,\"opacity\":1,\"locked\":false,\"datasource\":\"http:\/\/<WEB_SERVER_HOST:PORT>\/cataloghiradex_f\/cataloghi_TMS\/ortofoto\/ortoregp2010\/\",\"format\":\"png\",\"layers\":\"\",\"singleTile\":0,\"mapLayers\":[]},{\"type\":\"csi_tms\",\"label\":\"CTR Raster\",\"visibility\":false,\"opacity\":1,\"locked\":false,\"datasource\":\"http:\/\/<WEB_SERVER_HOST:PORT>\/cataloghiradex_f\/cataloghi_TMS\/sfondi\/CTR_raster\/\",\"format\":\"png\",\"layers\":\"\",\"singleTile\":0,\"mapLayers\":[]}]",
			"\"\"",
			"\"\"",
			"\"\""
		]
	},*/
	"toolbar": {
		"pan": {
			"order": 1,
			"showText": false,
			"isDefault": true,
			"icon": "ui-icon-arrow-4",
			"bbox": null,
			"iconPrev": null,
			"iconNext": null
		},
		"zoomIn": {
			"order": 2,
			"showText": false,
			"isDefault": false,
			"icon": "ui-icon-circle-zoomin",
			"bbox": null,
			"iconPrev": null,
			"iconNext": null
		},
		"zoomOut": {
			"order": 3,
			"showText": false,
			"isDefault": false,
			"icon": "ui-icon-circle-zoomout",
			"bbox": null,
			"iconPrev": null,
			"iconNext": null
		},
		"fullscreen": {
			"order": 4,
			"showText": false,
			"isDefault": false,
			"icon": "ui-icon-arrow-4-diag",
			"bbox": null,
			"iconPrev": null,
			"iconNext": null
		},
		"maxExtent": {
			"order": 5,
			"showText": false,
			"isDefault": false,
			"icon": "ui-icon-arrow-4",
			"bbox": null,
			"iconPrev": null,
			"iconNext": null
		}/*,
		"history": {
			"order": 6,
			"showText": false,
			"isDefault": false,
			"icon": null,
			"bbox": null,
			"iconPrev": "ui-icon-arrowthick-1-w",
			"iconNext": "ui-icon-arrowthick-1-e"
		},
		"infoall": {
			"order": 7,
			"showText": false,
			"isDefault": false,
			"icon": null,
			"bbox": null,
			"iconPrev": null,
			"iconNext": null
		}*/
	},
	"mapLayers": [
					
		  {
			"type": "csi_wms",
			"label": "Limiti Comunali",
			"visibility": true,
			"opacity": 1,
			"locked": true,
			"datasource": "http:\/\/<WEB_SERVER_HOST:PORT>\/ws\/taims\/rp-01\/taimslimammwms\/wms_limitiAmm?",
			"format": "",
			"layers": "LimitiComunali",
			"singleTile": true,
			"mapLayers": [
			]
		  },
		/*{
			"type": "csi_tms",
			"label": "Sfondo Cartografico Piemonte - old",
			"visibility": false,
			"opacity": 0.7,
			"locked": true,
			"datasource": "http:\/\/<WEB_SERVER_HOST:PORT>\/cataloghiradex_f\/cataloghi_TMS\/sfondi\/sfondo_europa_piemonte\/",
			"format": "png",
			"layers": "",
			"singleTile": 0,
			"mapLayers": [
			]
		},*/
		// nuovo layer che rimpiazza "Sfondo Cartografico Piemonte - old"
		/*{
			"type": "wmts",
			"label": "Sfondo Cartografico Piemonte",
			"visibility": true,
			"opacity": 0.75,
			"locked": true,
			"datasource": "http:\/\/<WEB_SERVER_HOST:PORT>\/mapproxy\/wmts\/1.0.0\/WMTSCapabilities.xml",
			"format": "png",
			"layers": "regp_sfondo_bdtre_wmts",
			"singleTile": false,
			"mapLayers": [
			]
		},*/
		{
			"type": "csi_wms",
			"label": "Sfondo Cartografico Piemonte",
			"visibility": true,
			"opacity": 0.75,
			"locked": true,
			"datasource": "http:\/\/<WEB_SERVER_HOST:PORT>\/mapproxy\/service?",
			"format": "",
			"layers": "regp_sfondo_bdtre",
			"singleTile": true,
			"mapLayers": [
			]
		},
		/*{
			"type": "csi_tms",
			"label": "Ortofoto Regione Piemonte 2010 - TMS",
			"visibility": true,
			"opacity": 0.5,
			"locked": true,
			"datasource": "http:\/\/<WEB_SERVER_HOST:PORT>\/cataloghiradex_f\/cataloghi_TMS\/ortofoto\/ortoregp2010\/",
			"format": "png",
			"layers": "",
			"singleTile": 0,
			"mapLayers": [
			]
		},*/
		  {
			"type": "csi_wms",
			"label": "Ortofoto Regione Piemonte 2010",
			"visibility": true,
			"opacity": 1,
			"locked": true,
			"datasource": "http:\/\/<WEB_SERVER_HOST:PORT>\/ws\/taims\/rp-01\/taimsortoregp\/wms_ortoregp2010?",
			"format": "",
			"layers": "OrtofotoRegione2010",
			"singleTile": true,
			"mapLayers": [
			]
		}/*,
		{
			"type": "csi_tms",
			"label": "Ortofoto Provincia Torino 2006",
			"visibility": false,
			"opacity": 1,
			"locked": true,
			"datasource": "http:\/\/<WEB_SERVER_HOST:PORT>\/WEBCAT\/STORICO\/TMS_32632\/ORTOIMMAGINI\/pvto_ortofoto_2006\/",
			"format": "png",
			"layers": "",
			"singleTile": 0,
			"mapLayers": [
			]
		},
		{
			"type": "csi_tms",
			"label": "CTP Raster trasparente",
			"visibility": false,
			"opacity": 1,
			"locked": true,
			"datasource": "http:\/\/<WEB_SERVER_HOST:PORT>\/WEBCAT\/STORICO\/TMS_32632\/CARTOGRAFIE\/pvto_ctptrasp_2013\/",
			"format": "png",
			"layers": "",
			"singleTile": 0,
			"mapLayers": [
			]
		},
		{
			"type": "csi_tms",
			"label": "CTP Raster",
			"visibility": false,
			"opacity": 1,
			"locked": true,
			"datasource": "http:\/\/<WEB_SERVER_HOST:PORT>\/WEBCAT\/STORICO\/TMS_32632\/CARTOGRAFIE\/pvto_ctp_2013\/",
			"format": "png",
			"layers": "",
			"singleTile": 0,
			"mapLayers": [
			]
		},
		{
			"type": "csi_tms",
			"label": "Sfondo Regione Piemonte",
			"visibility": true,
			"opacity": 1,
			"locked": true,
			"datasource": "http:\/\/<WEB_SERVER_HOST:PORT>\/cataloghiradex_f\/cataloghi_TMS\/sfondi\/sfondoregione\/",
			"format": "png",
			"layers": "",
			"singleTile": 0,
			"mapLayers": [
			]
		},
		{
			"type": "csi_tms",
			"label": "CTR Raster",
			"visibility": false,
			"opacity": 1,
			"locked": true,
			"datasource": "http:\/\/<WEB_SERVER_HOST:PORT>\/cataloghiradex_f\/cataloghi_TMS\/sfondi\/CTR_raster\/",
			"format": "png",
			"layers": "",
			"singleTile": 0,
			"mapLayers": [
			]
		}*/
	]
}			
;