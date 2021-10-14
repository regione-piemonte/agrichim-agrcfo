OpenLayers.ProxyHost = "proxy.php?csurl=";
var UrlAutocomplete = "autocomplete.php";

var mappa = null;
var mq = null;

var vlayer = null;

var highlightLayer = null;

var cartelleTocUtente = ["utente 1", "utente 2"];

// variabile globale per la memorizzazione dei tematismi caricati in TOC
var tematismi = null;
var TOC = null;

var toolbar = null;


//-----------------------------------------------------------------------------
$(document).ready(function() {
	
	//if (pathJoomla != undefined)
	if (typeof(pathJoomla) != "undefined")
	{
		UrlAutocomplete = pathJoomla +"tmpl/"+UrlAutocomplete;
		OpenLayers.ProxyHost = pathJoomla +"tmpl/"+OpenLayers.ProxyHost;
	}

	if (typeof(parametriJSON) == "undefined")
	{
		alert("Parametri JSON non definiti!");
		$("#div_mappa").html("Error!");
		return false;
	}
		
	
	loadExtraCssJs();
	
});


//-----------------------------------------------------------------------------
// carica le risorse aggiuntive per definire lo stile del template
function loadExtraCssJs(){
	
	var vstyle = "ui-lightness";
	var urlCssStile = "";
	
	if (parametriJSON.map.VStyle != undefined)
		if(parametriJSON.map.VStyle != "") 
			vstyle=parametriJSON.map.VStyle;
	
	if (typeof(pathJoomla) != "undefined")
		urlCssStile = pathJoomla + 'tmpl/lib/jquery/jquery-ui-1.10.0/css/'+ vstyle +'/jquery-ui-1.10.4.custom.css';
	else
		urlCssStile = '/assets/application/agrichim/gis/lib/jquery/jquery-ui-1.10.0/css/'+ vstyle +'/jquery-ui-1.10.4.custom.css';
	

	
	$.rloader([ 
		  {src: urlCssStile},
		  {
			  event:'onready', 
			  func:'startAll' 
			  //arg:<parameters>
		  }
		]);
}

var dimensioneMappaX = "100%";
var dimensioneMappaY = "100%";
//-----------------------------------------------------------------------------
function startAll(){
    dbg.activate(true);
    //dbg.log("prova");
    
    // avvio delle funzioni specifiche del progetto
	projectOnStart();
	
    // dimensione della mappa
    if (parametriJSON.map.mapsize_h != undefined && parametriJSON.map.mapsize_w != undefined)
    	if(parametriJSON.map.mapsize_h != "" && parametriJSON.map.mapsize_w != "")
    	{	
        /*
        dimensioneMappaX = parametriJSON.map.mapsize_w+"px";
        dimensioneMappaY = parametriJSON.map.mapsize_h+"px";
        dimensioneMappaX = parametriJSON.map.mapsize_w+"%";
        dimensioneMappaY = parametriJSON.map.mapsize_h+"%";
        */
        dimensioneMappaX = parametriJSON.map.mapsize_w;
        dimensioneMappaY = parametriJSON.map.mapsize_h;
    		$("#div_mappa").css("width",dimensioneMappaX);
    		$("#div_mappa").css("height",dimensioneMappaY);  
    	}
    
    
    
	// autocomplete con ricerca su pagina php
	
	// $("#nome_comune").autocomplete({
	//	source: UrlAutocomplete,
	//	minLength: 2,
	//	position: { my : "left top", at: "left bottom" },
	//	select: function( event, ui ) {
	//		if (ui.item) {
	//			//alert(ui.item.value);
	//			//showComune($("#nome_comune").val())
	//			showComune(ui.item.value);
	//		}
	//	},
	//	appendTo: '#div_find'
	//});
	/*
	listaRicerche = sortByKey(listaRicerche, "label");
	
	$( "#nome_comune" ).autocomplete({
		delay: 0,
		sortResults:true,
		source: listaRicerche,
		position: { my : "left bottom", at: "left top" },
		appendTo: '#div_find',
		focus: function( event, ui ) {
			$( "#nome_comune" ).val( ui.item.label );
			return false;
		},
		select: function( event, ui ) {
			$( "#nome_comune" ).val( ui.item.label );
			if (ui.item) {
				showComuneDaLista(ui.item.value);
			}
			return false;
		}
	});
	*/
	
	
	
	
	
	//
	//$("#dialogGraf").dialog({
	//	autoOpen: false,
	//	height: 350,
	//	width: 400
	//});
	
	//idSezioniSelezionate = idSezioniSelezionate.split(",");
	//
	//	sld_body = '<?xml version="1.0" encoding="ISO-8859-1" standalone="yes"?>';
	//	sld_body += '<sld:StyledLayerDescriptor version="1.0.0" xmlns:sld="http://www.opengis.net/sld" xmlns:ogc="http://www.opengis.net/ogc" xmlns:xlink="http://www.w3.org/1999/xlink">';
	//	sld_body += '  <sld:NamedLayer>';
	//	
	//	sld_body += '	<sld:Name>Sezioni</sld:Name>'; // nome livello del WMS
	//	
	//	sld_body += '	<sld:UserStyle>';
	//	
	//	
	//	
	//	sld_body += '	  <sld:Name>stile_sezione</sld:Name>'; 
	//	sld_body += '	  <sld:FeatureTypeStyle>';
	//	sld_body += '		<sld:Rule>';
	//				
	//				
	//	sld_body += '			<ogc:Filter>';
	//
	//	
	//	/*
	//	sld_body += '				<ogc:PropertyIsEqualTo>';
	//	sld_body += '					<ogc:PropertyName>id_sezione_geom</ogc:PropertyName>';
	//	//sld_body += '					<ogc:Literal>SEZ 9B</ogc:Literal>';
	//	
	//	//sld_body += '					<ogc:PropertyName>id_sezione</ogc:PropertyName>';
	//	sld_body += '					<ogc:Literal>'+idSezioniSelezionate+'</ogc:Literal>';
	//	
	//	sld_body += '				</ogc:PropertyIsEqualTo>';
	//
	//	
	//	*/
	//	if (idSezioniSelezionate.length>1)
	//		sld_body += '				<ogc:Or>';
	//
	//	for (var idss=0; idss<idSezioniSelezionate.length; idss++) {
	//	sld_body += '				<ogc:PropertyIsEqualTo>';
	//	sld_body += '					<ogc:PropertyName>id_sezione_topo</ogc:PropertyName>';
	//	sld_body += '					<ogc:Literal>'+idSezioniSelezionate[idss]+'</ogc:Literal>';
	//	sld_body += '				</ogc:PropertyIsEqualTo>';
	//}
	//	
	//	if (idSezioniSelezionate.length>1)
	//		sld_body += '				</ogc:Or>';
	//
	//
	//	sld_body += '			</ogc:Filter>';
	//	
	//	
	//	
	//	
	//	//sld_body += '		  <sld:Name>LOC_NM_POS</sld:Name>';
	//	//sld_body += '		  <sld:Title>LOC_NM_POS</sld:Title>';
	//	
	//	//sld_body += '		  <sld:TextSymbolizer>';
	//	//sld_body += '			<sld:Label>';
	//	//sld_body += '			  <ogc:PropertyName>loc_nm_top</ogc:PropertyName>';
	//	//sld_body += '			</sld:Label>';
	//	//sld_body += '			<sld:Font>';
	//	//sld_body += '			  <sld:CssParameter name="font-family">Arial</sld:CssParameter>';
	//	//sld_body += '			  <sld:CssParameter name="font-family">Sans-Serif</sld:CssParameter>';
	//	//sld_body += '			  <sld:CssParameter name="font-size">6</sld:CssParameter>';
	//	//sld_body += '			  <sld:CssParameter name="font-style">normal</sld:CssParameter>';
	//	//sld_body += '			  <sld:CssParameter name="font-weight">bold</sld:CssParameter>';
	//	//sld_body += '			</sld:Font>';
	//	//sld_body += '			<sld:Fill>';
	//	//sld_body += '			  <sld:CssParameter name="fill">#000000</sld:CssParameter>';
	//	//sld_body += '			  <sld:CssParameter name="fill-opacity">1.0</sld:CssParameter>';
	//	//sld_body += '			</sld:Fill>';
	//	//sld_body += '		  </sld:TextSymbolizer>';
	//	
	//	sld_body += '		  <sld:LineSymbolizer>';
	//	sld_body += '		 	 <sld:Stroke>';
	//	sld_body += '		 		 <sld:CssParameter name="stroke">#ffee00</sld:CssParameter>';
	//	sld_body += '		 		 <sld:CssParameter name="stroke-opacity">1</sld:CssParameter>';
	//	sld_body += '		 		 <sld:CssParameter name="stroke-width">3</sld:CssParameter>';
	//	sld_body += '		 		 <sld:CssParameter name="stroke-linejoin">mitre</sld:CssParameter>';
	//	sld_body += '		 		 <sld:CssParameter name="stroke-linecap">square</sld:CssParameter>';
	//	sld_body += '		 	 </sld:Stroke>';
	//	sld_body += '		  </sld:LineSymbolizer>';
	//	
	//	sld_body += '		</sld:Rule>';
	//	sld_body += '	  </sld:FeatureTypeStyle>';
	//	sld_body += '	</sld:UserStyle>';
	//	sld_body += '  </sld:NamedLayer>';
	//	
	//	sld_body += '</sld:StyledLayerDescriptor>	';
	//
	//dbg.log("--- sld_body ---");
	//dbg.log(sld_body);
	//dbg.log("----------------");
	//   
    // seleziona il linguaggio leggendo il parametro "lang" da GET
    lng.setLanguage(getURLParameter('lang'));
    //$('#langSelector').csiLangSelector();

    // require layout.js
    
    
    // prima di creare il layout cancella le parti che non servono

    // se mappa singola nasconde il pannello ovest e toglie la seconda toolbar
    // dbg.log(parametriJSON.multiMap);
    if (parametriJSON.multiMap != undefined )
    	if(parametriJSON.multiMap.nSeconday == 0)
    	{	
    		$(".ui-layout-west").remove();
    		$("#maintoolbar2").hide();
    	}
    
    init_layout();
    
	// valori di default
	var p_numZoomLevels = 21;
	var p_projectionCode = "EPSG:32632";
	var p_maxExtent = [-10198294.6545,-5596920.6825,11389716.6914,15991090.6634];
	
	// lettura da parametri
	if (parametriJSON.map.numZoomLevels != undefined)
		p_numZoomLevels = parametriJSON.map.numZoomLevels;
	
	if (parametriJSON.map.projectionCode != undefined)http:
		p_projectionCode = parametriJSON.map.projectionCode;
	
	if (parametriJSON.map.maxExtent != undefined)
		p_maxExtent = parametriJSON.map.maxExtent;
	
    var map = $('#map').mapQuery({
        projection: new OpenLayers.Projection(p_projectionCode)
        ,maxExtent: p_maxExtent
		,numZoomLevels: p_numZoomLevels
    });
	    
	//tematismi = getCookie("tematismi");
	//tematismi = eval('(' + tematismi + ')');
	//dbg.log(tematismi);
	//debuggaTematismi = tematismi;
	//if (tematismi == null) {
		
		
		/*
		 * QUI VENGONO DEFINITI I LIVELLI PER LA PRIMA VOLTA
		 * IN SEGUITO SONO LETTI NEI COOKIES
		*/
				
		//tematismi = tematismiDefault;
		
		
		// PER TESTARE L'SLD
//		tematismi[tematismi.length] = {
//            type:'csi_wms'
//            ,label:'Sezioni topografiche'
//            //,url:'http://geomap.reteunitaria.piemonte.it/ws/siti/aipo-01/sitiwms/wms_aipo_rilievi_topografici?'
//			//,url:'http://tst-geomap.reteunitaria.piemonte.it/ws/siti/aipo-01/sitiwms/wms_aipo_rilievi_topografici?'
//			
//			,url:'http://<WEB_SERVER_HOST:PORT>/ws/siti/aipo-01/sitiwms/wms_aipo_rilievi_topografici?'
//            
//			//,url:'http://<WEB_SERVER_HOST:PORT>/ws/siti/aipo-01/sitiwms/wms_aipo_sezioni?'
//			
//			,layers: 'Sezioni'
//			,cartellaToc: "Ricerche"
//			,sld_body: sld_body
//			}
//		;

		
		
		
		//// per debug
		//tematismi =[            
		//	{         
		//		type:'csi_tms'
		//		,getURL: get_url_CSI
		//		,format: 'png'
		//		,label:'Sfondo Cartografico Piemonte'
		//		,url:'http://<WEB_SERVER_HOST:PORT>/cataloghiradex_f/cataloghi_TMS/sfondi/sfondo_europa_piemonte/'
		//		,numZoomLevels: 11
		//		,projection:         new OpenLayers.Projection("EPSG:32632")
		//		//restrictedExtent:   new OpenLayers.Bounds(313263.0,4879724.0,517049.0,5145994.0),
		//		,isBaseLayer: false
		//		,noDelete: true
		//		//,visibility: false
		//		,cartellaToc: "la mia cartela"
		//	}
		//	//,
		//	//{
		//	//	type:'csi_wms'
		//	//	,label:'Idrografia def'
		//	//	,url:'http://<WEB_SERVER_HOST:PORT>/ws/aipo/wms_aipo_dati_base?'
		//	//	,layers: 'Idrografia,IdrografiaLabel'
		//	//	//,legend: {url: "http://<WEB_SERVER_HOST:PORT>/ws/aipo/wms_aipo_dati_base?layer=Idrografia,IdrografiaLabel"}
		//	//	,cartellaToc: "wms"
		//	//}
		//]


		
//		tematismiDefault
//		[            
//		{         
//			type:'csi_tms'
//			,getURL: get_url_CSI
//			,format: 'png'
//			,label:'Sfondo Cartografico Piemonte'
//			,url:'http://<WEB_SERVER_HOST:PORT>/cataloghiradex_f/cataloghi_TMS/sfondi/sfondo_europa_piemonte/'
//			,numZoomLevels: 11
//			//projection:         new OpenLayers.Projection("EPSG:32632"),
//			//restrictedExtent:   new OpenLayers.Bounds(313263.0,4879724.0,517049.0,5145994.0),
//			,isBaseLayer: false
//			,noDelete: true
//			//,visibility: false
//			//,cartellaToc: "la mia cartela"
//		},
//		{
//			type:'csi_tms',
//			getURL: get_url_CSI,
//			format: 'png',
//			label: 'Ortofoto Piemonte 2010',
//			url: 'http://<WEB_SERVER_HOST:PORT>:80/cataloghiradex_f/cataloghi_TMS/ortofoto/ortoregp2010/',
//			numZoomLevels: 18,
//			isBaseLayer: false,
//			noDelete: true
//			//,cartellaToc: "prova2"
//				//visibility : 		true,
//				//projection:         new OpenLayers.Projection("EPSG:32632"),
//				//restrictedExtent:   new OpenLayers.Bounds(-10198294.6545,-5596920.6825,11389716.6914,15991090.6634),
//				//serverResolutions:  [84328.169319921875, 42164.084659960937, 21082.042329980468, 10541.021164990234, 5270.510582495117, 2635.255291247558, 1317.627645623779, 658.813822811889, 329.406911405944, 164.703455702972, 82.351727851486, 41.175863925743, 20.587931962871, 10.293965981435, 5.146982990717, 2.573491495358, 1.286745747679, 0.643372873839, 0.321686436919, 0.160843218459],
//				//resolutions:        [658.813822811889, 329.406911405944, 164.703455702972, 82.351727851486, 41.175863925743, 20.587931962871, 10.293965981435, 5.146982990717, 2.573491495358, 1.286745747679, 0.643372873839, 0.321686436919, 0.160843218459],
//				//transitionEffect:   'resize'
//		}
//		,
//		{
//            type:'csi_wms',
//            label:'Idrografia',
//            url:'http://<WEB_SERVER_HOST:PORT>/ws/aipo/wms_aipo_dati_base?',
//            layers: 'Idrografia,IdrografiaLabel',
//            legend: {url: "http://<WEB_SERVER_HOST:PORT>/ws/aipo/wms_aipo_dati_base?layer=Idrografia,IdrografiaLabel"}
//			,cartellaToc: "wms"
//        }
//		,
//		{
//            type:'csi_wms',
//            label:'Limiti amministrativi',
//            url:'http://<WEB_SERVER_HOST:PORT>/ws/aipo/wms_aipo_dati_base?',
//            layers: 'Regioni,Province,Comuni,ComuniLabel',
//            legend: {url: "http://<WEB_SERVER_HOST:PORT>/ws/aipo/wms_aipo_dati_base?layer=Regioni,Province,Comuni,ComuniLabel"}
//			,cartellaToc: "wms"
//			,visibility: false
//        }
//		,
//		{
//            type:'csi_wms',
//            label:'Bacini idrografici',
//            url:'http://<WEB_SERVER_HOST:PORT>/ws/aipo/wms_aipo_dati_base?',
//            layers: 'Bacino,BacinoLabel',
//            legend: {url: "http://<WEB_SERVER_HOST:PORT>/ws/aipo/wms_aipo_dati_base?layer=Bacino,BacinoLabel"}
//			,cartellaToc: "wms"
//			,visibility: false
//        },
//		{         
//          type:'osm'
//          ,label:'OpenStreetMap'
//          ,legend:{url:'http://www.openstreetmap.org/images/osm_logo.png'}
//		  ,visibility: false // per spegnere il livello al caricamento (default true)
//        },{
//          type:'google'
//		  ,label:'Google - terreno'
//		  ,view:'terrain'
//		  ,visibility: false
//		},{
//          type:'google'
//		  ,label:'Google - strade'
//		  ,view:'road'
//		  ,visibility: false
//		},{
//          type:'google'
//		  ,label:'Google - ibrido'
//		  ,view:'hybrid'
//		  ,visibility: false
//		},{
//          type:'google'
//		  ,label:'Google - satellite'
//		  ,view:'satellite'
//		  ,visibility: false
//		}		
//		
//		
////wms[li++] = new OpenLayers.Layer.WMS("Carta fiume Po",
////		"http://<WEB_SERVER_HOST:PORT>/ws/aipo/wms_aipo_cartafiumepo?",
////		{
////			layers: '2008_CT10_Po,2011_CT50_Po',
////			transparent: true,
////		},
////		{
////			isBaseLayer: true,
////			singleTile: true,
////			visibility: true,
////		}
////);
////
////
//
////);
////wms[li++] = new OpenLayers.Layer.WMS("CT Po 10000",
////		"http://<WEB_SERVER_HOST:PORT>/ws/aipo/wms_aipo_cartafiumepo?", 
////		{layers: '2008_CT10_Po', transparent: true},
////		{isBaseLayer: false, singleTile: true, visibility: false}
////		);
////wms[li++] = new OpenLayers.Layer.WMS("CT Po 50000",
////		"http://<WEB_SERVER_HOST:PORT>/ws/aipo/wms_aipo_cartafiumepo?", 
////		{layers: '2011_CT50_Po', transparent: true},
////		{isBaseLayer: false, singleTile: true, visibility: false}
////		);
////wms[li++] = new OpenLayers.Layer.WMS("Competenza AIPo",
////		"http://<WEB_SERVER_HOST:PORT>/ws/aipo/wms_aipo_competenza_aipo?", 
////		{layers: 'AIPO-Competenza', transparent: true},
////		{isBaseLayer: false, singleTile: true, queryLayers: 'Competenza_AIPo_pol,Competenza_AIPo_lin', visibility: false}
////		);
////wms2q[qli++] = wms[li-1];
////		
////wms[li++] = new OpenLayers.Layer.WMS("Curve di navigazione",
////		"http://<WEB_SERVER_HOST:PORT>/ws/aipo/wms_aipo_curve_navigazione?", 
////		{layers: 'CurveNavigazione', transparent: true},
////		{isBaseLayer: false, singleTile: true, visibility: false, queryLayers: 'CurveNavigazione'}
////		);
////		
////wms2q[qli++] = wms[li-1]; // Aggiungo il layer negli interrogabili
////wms[li++] = new OpenLayers.Layer.WMS("Sezioni fluviali",
////		"http://<WEB_SERVER_HOST:PORT>/ws/aipo/wms_aipo_rilievi_topografici?", 
////		{layers: 'AIPO-RilieviTopografici', transparent: true},
////		{isBaseLayer: false, singleTile: true, queryLayers: 'Sezioni,Capisaldo', visibility: false}
////		);
////wms2q[qli++] = wms[li-1]; // Aggiungo il layer negli interrogabili
////wms[li++] = new OpenLayers.Layer.WMS(
////		//"Rete teleidrometrica",
////		"Rete fiduciaria",
////		//"http://<WEB_SERVER_HOST:PORT>/ws/aipo/wms_aipo_stazioni_di_misura?",
////		"http://<WEB_SERVER_HOST:PORT>/ws/aipo/wms_aipo_rete_fiduciaria?",
////		{layers: 'ReteFiduciaria,ReteFiduciaria-label', transparent: true},
////		{isBaseLayer: false, singleTile: true, queryLayers: 'Staz_Misura', visibility: false}
////		);
////wms2q[qli++] = wms[li-1]; // Aggiungo il layer negli interrogabili
////wms[li++] = new OpenLayers.Layer.WMS("Fasce fluviali",
////		"http://<WEB_SERVER_HOST:PORT>/ws/aipo/wms_aipo_fasce?", 
////		{layers: 'AreeInondabili,AreeDiLam-Outline,FasceFluvialiLineari,ImmobiliRiloc', transparent: true},
////		{isBaseLayer: false, singleTile: true, queryLayers: 'AreeInondab,AreeDiLam,FasceFluvialiLineari,ImmobiliRiloc', visibility: false}
////		);
////wms2q[qli++] = wms[li-1]; // Aggiungo il layer negli interrogabili
////wms[li++] = new OpenLayers.Layer.WMS("Fasce fluviali areali",
////		"http://<WEB_SERVER_HOST:PORT>/ws/aipo/wms_aipo_fasce?", 
////		{layers: 'FasceFluvialiAreali', transparent: true},
////		{isBaseLayer: false, singleTile: true, queryLayers: 'FasceArealiC,FasceArealiB,FasceArealiA', visibility: false, opacity: 0.45}
////		);
////wms2q[qli++] = wms[li-1]; // Aggiungo il layer negli interrogabili
////wms[li++] = new OpenLayers.Layer.WMS("Aree protette",
////		"http://<WEB_SERVER_HOST:PORT>/ws/aipo/wms_aipo_aree_protette?", 
////		{layers: 'AIPO-AreeProtette', transparent: true},
////		{isBaseLayer: false, singleTile: true, queryLayers: 'AreeProtette', visibility: false}
////		);
////wms2q[qli++] = wms[li-1]; // Aggiungo il layer negli interrogabili
////wms[li++] = new OpenLayers.Layer.WMS("Rete natura 2000",
////		"http://<WEB_SERVER_HOST:PORT>/ws/aipo/wms_aipo_retenatura2000?", 
////		{layers: 'ZPS,SIC,ZPS-LAbel,SIC-Label', transparent: true},
////		{isBaseLayer: false, singleTile: true, queryLayers: 'ZPS,SIC', visibility: false}
////		);
//		
//		];
	//}
	
	//setCookie("tematismi",JSON.stringify(tematismi));
	//var layersDaCaricare = tematismi;
	
				

	// -------------------------------------------------------------------------
	// AGGIUNTA DELL'OVERVIEW
	if (parametriJSON.overview != undefined) {
		
		var OVlays = null;
		var OVh = 100;
		var OVw = 100;
		var OVposition = ['right','bottom'];
		var OVwindowed = true;
		var OVmaximized = false;
		
		if (parametriJSON.overview.layers != undefined)
			OVlays = parametriJSON.overview.layers;
			
		if (parametriJSON.overview.width != undefined)
			OVw = parametriJSON.overview.width;
		
		if (parametriJSON.overview.height != undefined)
			OVh = parametriJSON.overview.height;
		
		if (parametriJSON.overview.position != undefined)
			OVposition = parametriJSON.overview.position;
			
		if (parametriJSON.overview.windowed != undefined)
			OVwindowed = parametriJSON.overview.windowed;
		
		if (parametriJSON.overview.maximized != undefined)
			OVmaximized = parametriJSON.overview.maximized;
		
		
		$('#overview').mqOverviewMap({
			map: '#map',
			position: OVposition,
			width: OVw,
			height: OVh,
			layers: OVlays,
			windowed: OVwindowed,
			maximized: OVmaximized
		});
	}
	
	// toc di default
	
	
	switch (parametriJSON.toc.type) {
		case "tree":
			TOC = $('#layermanagergn').csiLayerManagerSimpleTree({map:'#map'});
			// crea le cartelle (per avere anche le cartelle vuote nella TOC)
			$('#layermanagergn').csiLayerManagerSimpleTree("initTree",parametriJSON.mapLayers);
			
		break;
		
		default:
			TOC = $('#layermanagergn').csiLayerManagerMinimal({map:'#map'});
	}		
		
	// non usare la variabile tematismi perchè viene modificata durante il processo di caricamento
	//$('#map').data("mapQuery").layers(layersDaCaricare);
	
	if (parametriJSON.mapLayers != undefined) {
		var appJSON = parametriJSON.mapLayers;
		parseJSONLayers(appJSON);
	}	
	
    /*
        var UnitaAmministrative = new OpenLayers.Layer.WMS(
            "UnitaAmministrative",
            "http://geomap.reteunitaria.piemonte.it/ws/taims/rp-01/taimslimammwms/wms_limitiAmm?", 
            {
                layers: 'UnitaAmministrative',
                transparent: true,
                format: 'image/gif'
            },
            {
                isBaseLayer: false
            }
        );
        */
    mq = map.data('mapQuery');
        
    mappa = $('#map').data('mapQuery').olMap;
	
		
    
    /*
    retrieveWMSparamsAndAddToMap('#map',{
            type:'csi_wms',
            label:'prova motore semantico',
            url:'http://<WEB_SERVER_HOST:PORT>/ws/motore_semantico/wms_motore_semantico?',
            layers: 'areali_strategici',
            //legend: {url: "http://<WEB_SERVER_HOST:PORT>/ws/motore_semantico/wms_motore_semantico?layer=areali_strategici"}
            //legend: {url: "http://vixra.files.wordpress.com/2012/02/stop1.png"}
            });
    /*
    $('#map').data("mapQuery").layers({
            type:'csi_wms',
            label:'diretto',
            url:'http://<WEB_SERVER_HOST:PORT>/ws/motore_semantico/wms_motore_semantico?',
            layers: 'prod_prgc,aree_produttive_50000mq,centri_ricerca_scientifica,areali_strategici',
            legend: {url: "http://<WEB_SERVER_HOST:PORT>/ws/motore_semantico/wms_motore_semantico?layer=prod_prgc"}
            //legend: {url: "http://vixra.files.wordpress.com/2012/02/stop1.png"}
        });
    $('#map').data("mapQuery").layers({
        type:'csi_wms',
        minZoom:2,
        label:'Population density 2010',
        legend:{url:'http://mapserver.edugis.nl/cgi-bin/mapserv?map=maps/edugis/cache/population.map&version=1.1.1&service=WMS&request=GetLegendGraphic&layer=Bevolkingsdichtheid_2010&format=image/png'},
        url:'http://t1.edugis.nl/tiles/tilecache.py?map=maps/edugis/cache/population.map', //note: this is actually a tileservice and only works with the spherical mercator tileschema
        layers:'Bevolkingsdichtheid_2010'
        });
    
    /*
    $('#map').data("mapQuery").layers({
            type:'wms',
            label:'Motore Semantico',
            url:'http://<WEB_SERVER_HOST:PORT>/ws/motore_semantico/wms_motore_semantico?',
            layers: 'prod_prgc,aree_produttive_50000mq,centri_ricerca_scientifica,areali_strategici',
            });
    
    /*
    $('#map').data("mapQuery").layers({
            type:'wms',
            label:'UnitaAmministrative',
            url:'http://geomap.reteunitaria.piemonte.it/ws/taims/rp-01/taimslimammwms/wms_limitiAmm?',
            layers: 'UnitaAmministrative',
            });
    
    $('#map').data("mapQuery").layers({
            type:'wms',
            label:'Aree Protette Rete Natura 2000',
            url:'http://geomap.reteunitaria.piemonte.it/ws/gsareprot/rp-01/areeprotwms/wms_gsareprot_1?',
            layers: 'AreeProtetteReteNatura2000',
            });

/*    
    $('#map').data("mapQuery").layers({
            type:'csi_wms',
            label:'motore semantico',
            url:'http://<WEB_SERVER_HOST:PORT>/ws/motore_semantico/wms_motore_semantico?',
            layers: 'relazioni',
            sld: 'http://<WEB_SERVER_HOST:PORT>/motoresemantico/gwm/tmp/file_relazioni_73a.sld'
            });    
  */  
    
    //mappa.addLayers([UnitaAmministrative]);
    
    // TOC
    // semplice
    //$('#layermanager').csiLayerManager({map:'#map'});
    // con gestione livelli wms
    //$('#layermanager').csiLayerManagerTree({map:'#map'});
    /*
    
        $('#map').data("mapQuery").layers({
            type:'wms',
            minZoom:2,
            label:'Population density 2010',
            legend:{url:'http://mapserver.edugis.nl/cgi-bin/mapserv?map=maps/edugis/cache/population.map&version=1.1.1&service=WMS&request=GetLegendGraphic&layer=Bevolkingsdichtheid_2010&format=image/png'},
            url:'http://t1.edugis.nl/tiles/tilecache.py?map=maps/edugis/cache/population.map', //note: this is actually a tileservice and only works with the spherical mercator tileschema
            layers:'Bevolkingsdichtheid_2010'
            });
    */
    
    


    //init_edit(mappa)

	// METADATA EXPLORER
    //$('body').csiMetadataExplorer();
	
	// RICERCA INDIRIZZI
    //$('body').csiAddress({map:'#map'});
    
	
	// -------------------------------------------------------------------------
	// CREAZIONE DELLA TOOLBAR
	
	$( "#radio" ).buttonset(
		{
			icons: { primary: "ui-icon-locked" },
			text: false
		}
	);
	
	$('#radio :radio').change(function () {
	    cambiaModalitaMappa($('#radio :radio:checked').val());
	});
	
	var defaultTool = "ctrl_DragPan"; // strumento di default
	avToolsPositional = new Array();
	var newTool = null;
	counterOut = 100;
	if (parametriJSON.toolbar != undefined) {
		
		// pan
		// showText: false
		// icon: "ui-icon-arrow-4"
		if (parametriJSON.toolbar.pan != undefined)
		{
			if (parametriJSON.toolbar.pan.isDefault != undefined)
				if (parametriJSON.toolbar.pan.isDefault) defaultTool = "ctrl_DragPan";
			
			var shTxt = false;
			if (parametriJSON.toolbar.pan.showText != undefined)
				shTxt = parametriJSON.toolbar.pan.showText;
			var ico = "ui-icon-arrow-4";
			if (parametriJSON.toolbar.pan.icon != undefined)
				icon = parametriJSON.toolbar.pan.icon;
			
			addToolPositional(parametriJSON.toolbar.pan,
			{
				name: "ctrl_DragPan",
				icon: ico,
				showText: shTxt                
			});
		}
		
		// zoomin
		// showText: false
		// icon: "ui-icon-circle-zoomin"
		if (parametriJSON.toolbar.zoomIn != undefined)
		{
			if (parametriJSON.toolbar.zoomIn.isDefault != undefined)
				if (parametriJSON.toolbar.zoomIn.isDefault) defaultTool = "ctrl_ZoomInbox";
				
			var shTxt = false;
			if (parametriJSON.toolbar.zoomIn.showText != undefined)
				shTxt = parametriJSON.toolbar.zoomIn.showText;
			var ico = "ui-icon-circle-zoomin";
			if (parametriJSON.toolbar.zoomIn.icon != undefined)
				icon = parametriJSON.toolbar.zoomIn.icon;
			
			addToolPositional(parametriJSON.toolbar.zoomIn,
			{
				name: "ctrl_ZoomInbox",
				icon: ico,
				showText: shTxt
			});	
		}
		
		
		// zoomout
		// showText: false
		// icon: "ui-icon-circle-zoomout"
		if (parametriJSON.toolbar.zoomOut != undefined)
		{
			if (parametriJSON.toolbar.zoomOut.isDefault != undefined)
				if (parametriJSON.toolbar.zoomOut.isDefault) defaultTool = "ctrl_ZoomOutbox";
			
			var shTxt = false;
			if (parametriJSON.toolbar.zoomOut.showText != undefined)
				shTxt = parametriJSON.toolbar.zoomOut.showText;
			var ico = "ui-icon-circle-zoomout";
			if (parametriJSON.toolbar.pan.icon != undefined)
				icon = parametriJSON.toolbar.zoomOut.icon;
				
			addToolPositional(parametriJSON.toolbar.zoomOut,
			{
				name: "ctrl_ZoomOutbox",
				icon: icon,
				showText: shTxt                 
			});
		}
		
		// fullScreen
		// showText: false
		// icon: "ui-icon-arrow-4-diag"
		if (parametriJSON.toolbar.fullscreen != undefined)
		{
			var shTxt = false;
			if (parametriJSON.toolbar.fullscreen.showText != undefined)
				shTxt = parametriJSON.toolbar.fullscreen.showText;
			var icon = "ui-icon-arrow-4-diag";
			if (parametriJSON.toolbar.fullscreen.icon != undefined)
				icon = parametriJSON.toolbar.fullscreen.icon;
			
			
			addToolPositional(parametriJSON.toolbar.fullscreen,
			{
				name: "ctrl_CustomFunction",
				icon: icon, //"ui-icon-extlink",
				title: "Schermo intero", // lng.get("GNOSREG_SHERMOINTERO"),
				text: "Fullscreen", // lng.get("GNOSREG_SHERMOINTERO"),
				showText: shTxt,
				customFunction: function(){toggleFullScreenMode();}				
			});
		}
	
		// metaExp (Metadata Explorer)
		// showText: false
		// icon: "ui-icon-cart"
		if (parametriJSON.toolbar.metaExp != undefined)
		{
			var shTxt = false;
			if (parametriJSON.toolbar.metaExp.showText != undefined)
				shTxt = parametriJSON.toolbar.metaExp.showText;
			var ico = "ui-icon-cart";
			if (parametriJSON.toolbar.metaExp.icon != undefined)
				icon = parametriJSON.toolbar.metaExp.icon;
				
			addToolPositional(parametriJSON.toolbar.metaExp,
			{
				name: "ctrl_CustomFunction",
				icon: "ui-icon-cart",
				title: lng.get("TB_METADATAEXP_TITLE"),
				text: lng.get("TB_METADATAEXP"),
				showText: shTxt,
				customFunction: function(){$("#dia_metadataexplorer").dialog("open");}
			});
		}
	
		// fullExt (zoom alla massima estensione o parametrizzata)
		// showText: false
		// icon: "ui-icon-extlink"
		if (parametriJSON.toolbar.fullExt != undefined)
		{
			var bbExt = [313263.0,4879724.0,517049.0,5145994.0];
			if (parametriJSON.toolbar.fullExt.bbox != undefined)
				bbExt = parametriJSON.toolbar.fullExt.bbox;
			var shTxt = false;
			if (parametriJSON.toolbar.fullExt.showText != undefined)
				shTxt = parametriJSON.toolbar.fullExt.showText;
			var ico = "ui-icon-arrow-4-diag";
			if (parametriJSON.toolbar.fullExt.icon != undefined)
				icon = parametriJSON.toolbar.fullExt.icon;
			
			addToolPositional(parametriJSON.toolbar.fullExt,
			{
				name: "ctrl_CustomFunction",
				icon: ico,
				title: lng.get("TB_MAXEXT_TITLE"),
				text: lng.get("TB_MAXEXT"),
				showText: shTxt,
				customFunction: function(){
					$('#map').data("mapQuery").center({box:bbExt});
				}
			});
		}
		
		// history (cronologia di navigazione della mappa)
		// showText: false
		// iconPrev: "ui-icon-arrowthick-1-w"
		// iconNext: "ui-icon-arrowthick-1-e"
		if (parametriJSON.toolbar.history != undefined)
		{
			var shTxt = false;
			if (parametriJSON.toolbar.history.showText != undefined)
				shTxt = parametriJSON.toolbar.history.showText;
			var iconPrev = "ui-icon-arrow-4-diag";
			if (parametriJSON.toolbar.history.iconPrev != undefined)
				iconPrev = parametriJSON.toolbar.history.iconPrev;
			var iconNext = "ui-icon-arrow-4-diag";
			if (parametriJSON.toolbar.history.iconNext != undefined)
				iconNext = parametriJSON.toolbar.history.iconNext;
				
			addToolPositional(parametriJSON.toolbar.history,
			{
				name: "ctrl_nav", // aggiunge una coppia di tasti
				iconPrev: iconPrev,
				iconNext: iconNext,
				showText: shTxt
			});
		}
		
		// infoall (identify a carotaggio)
		// showText: false
		// iconPrev: "ui-icon-arrow-4-diag"
		// TODO: strumento di default
		if (parametriJSON.toolbar.infoall != undefined)
		{
			if (parametriJSON.toolbar.infoall.isDefault != undefined)
				if (parametriJSON.toolbar.infoall.isDefault) defaultTool = "ctrl_ZoomOutbox";
				
			var shTxt = false;
			if (parametriJSON.toolbar.infoall.showText != undefined)
				shTxt = parametriJSON.toolbar.infoall.showText;
			
			addToolPositional(parametriJSON.toolbar.infoall,
		    {
				name: "custom",   // controllo definito in modo personalizzato
				customControl: ctrl_infoAll,  // includere js
                showText: shTxt
			});
		}
		
		
		// identifica sui dati della casella di ricerca
		if (parametriJSON.toolbar.infoRicerca != undefined)
		{
			var shTxt = false;
			if (parametriJSON.toolbar.infoRicerca.showText != undefined)
				shTxt = parametriJSON.toolbar.infoRicerca.showText;
			
			addToolPositional(parametriJSON.toolbar.infoRicerca,
		    {
				name: "custom",   // controllo definito in modo personalizzato
				customControl: ctrl_infoRicerca,  // includere js
                showText: false
			});
		}	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// Mostra mappa secondaria
		// showText: false
		// iconPrev: "ui-icon-arrow-4-diag"
		// TODO: strumento di default
		if (parametriJSON.toolbar.show2Map != undefined)
		{
			addToolPositional(parametriJSON.toolbar.infoall,
            {
                name: "ctrl_CustomFunction", // FULLSCREEN
                icon: "ui-icon-arrow-4-diag",
                title: "mappe affiancate",
                text: "",
                showText: false,
                customFunction: mostraMappaAffiancata
            });
		}
		
		
	}
	
	// eventuali tools definiti da codice li inserisco da qui
//	avToolsPositional[counterOut++] = {
//        name: "custom", 
//        customControl: ctrl_infoAllPopup 
//    }
	// --- 

	var avTools = new Array();
	for(var i=0; i<avToolsPositional.length; i++)
		if (avToolsPositional[i]!=undefined) avTools.push(avToolsPositional[i]);
    
	
    toolbar = $('#maintoolbar').mqToolbar({
        map:'#map',
        defaultControl: defaultTool,
        activeControls: avTools
		
//      [	


//            {
//            },
//            /*
//            {
//                name: "custom",   // controllo definito in modo personalizzato
//                customControl: ctrl_infoAllPopup  // includere js
//            },

//            {
//                name: "ctrl_CustomFunction", // esegue una funzione al click
//                icon: "ui-icon-suitcase",
//                title: lng.get("TB_ADDRESS_TITLE"),
//                text: lng.get("TB_ADDRESS"),
//                showText: true,
//                customFunction: function(){$("#dia_address").dialog("open");}
//            }*/
//			,
//            {
//                name: "custom",   // controllo definito in modo personalizzato
//                customControl: ctrl_MeasureLine,  // includere js
//                showText: false
//            },
//            {
//                name: "custom",   // controllo definito in modo personalizzato
//                customControl: ctrl_MeasurePolygon  // includere js
//            },
//            {
//                name: "ctrl_CustomFunction", // STAMPA AL CLICK
//                icon: "ui-icon-print",
//                title: "stampa",
//                text: "",
//                showText: false,
//                customFunction: function(){window.print();}
//            },
//            {
//                name: "ctrl_CustomFunction", // FULLSCREEN
//                icon: "ui-icon-arrow-4-diag",
//                title: "schermo intero",
//                text: "",
//                showText: false,
//                customFunction: toggleFullScreenMode
//            }			
//        ]
    });

    //toolbar.mqToolbar("addControl",{ control: "ctrl_DragPan"}); 
    //var toolbar2 = $('#toolbar2').mqToolbar({
    //    map:'#map',
    //    activeControls:
    //    [
    //        {
    //            name: "custom",   // controllo definito in modo personalizzato
    //            customControl: ctrl_EditingPoint  // includere js
    //        },
    //        {
    //            name: "custom",   // controllo definito in modo personalizzato
    //            customControl: ctrl_EditingLine  // includere js
    //        },
    //        {
    //            name: "custom",   // controllo definito in modo personalizzato
    //            customControl: ctrl_EditingPolygon  // includere js
    //        },
    //        {
    //            name: "custom",   // controllo definito in modo personalizzato
    //            customControl: ctrl_EditingModify  // includere js
    //        },
    //        {
    //            name: "custom",   // controllo definito in modo personalizzato
    //            customControl: ctrl_RemoveAllFeatures  // includere js
    //        },
    //        {
    //            name: "custom",   // controllo definito in modo personalizzato
    //            customControl: ctrl_MeasureLine  // includere js
    //        },
    //        {
    //            name: "custom",   // controllo definito in modo personalizzato
    //            customControl: ctrl_MeasurePolygon  // includere js
    //        }
    //    ]
    //});
    
	// -------------------------------------------------------------------------
    // COORDINATE DEL MOUSE
	
	if (parametriJSON.mousePosition != undefined)
	{
		var mp_idDiv = 'map';
		if (parametriJSON.mousePosition.idDiv != undefined && parametriJSON.mousePosition.idDiv!="")
			mp_idDiv = parametriJSON.mousePosition.idDiv
		
		var mp_labelx = "X";
		if (parametriJSON.mousePosition.labelx != undefined)
			mp_labelx = parametriJSON.mousePosition.labelx

		var mp_labely = "Y";
		if (parametriJSON.mousePosition.labely != undefined)
			mp_labely = parametriJSON.mousePosition.labely
			
		var mp_precision = 0;
		if (parametriJSON.mousePosition.precision != undefined)
			mp_precision = parametriJSON.mousePosition.precision
		
	    $('#mouseposition').mqMousePosition({
	        map:'#'+mp_idDiv,
	        x: mp_labelx,
	        y: mp_labely,
	        precision: mp_precision
	    });
	}
    
	// ZOOM INIZIALE
	if (parametriJSON.map.initExtent != undefined)
		$('#map').data("mapQuery").center({box:parametriJSON.map.initExtent});
	else
		$('#map').data("mapQuery").center({box:[313263.0,4879724.0,517049.0,5145994.0]});
    
	
	
	
    //$('#map').data("mapQuery").center({box:[5,44,10,46]});
    //$('#map').data("mapQuery").center({box:[313263.0,4879724.0,517049.0,5145994.0]});
	
	// REGIONE
	//$('#map').data("mapQuery").center({box:[313263.0,4879724.0,517049.0,5145994.0]});
    
	// PROVINCIA
	//$('#map').data("mapQuery").center({box:[310000.0,4950000.0,435700.0,5051000.0]});
	//$('#map').data("mapQuery").center({box:[313280.454784371,4952971.20566506,433350.247364818,5050507.08644679]});
	
	
    
    $('#zoomslider').mqZoomSlider({ map: '#map' });
    
    // TODO: trasformare in widget??
    //addPanPanel(mappa);
    addScaleLine(mappa);

    
    // DIALOG: per il risultato dell'identify
    $("#dia_info").dialog(
    {
        title: "Identify",
        resizable: false,
        height:350,
        width:500,
        autoOpen: false,
        
        buttons: {
            "Ok": function() {
                $( this ).dialog( "close" );
            }
        }            
    });
    /*
    //create add layer options
    $("#addOsm").click(function() {
        $('#map').data("mapQuery").layers({
            type:'osm',
            label:'OpenStreetMap',
            legend:{url:'http://www.openstreetmap.org/images/osm_logo.png'}
            });
        });
    $("#addPop").click(function() {
        $('#map').data("mapQuery").layers({
            type:'wms',
            minZoom:2,
            label:'Population density 2010',
            legend:{url:'http://mapserver.edugis.nl/cgi-bin/mapserv?map=maps/edugis/cache/population.map&version=1.1.1&service=WMS&request=GetLegendGraphic&layer=Bevolkingsdichtheid_2010&format=image/png'},
            url:'http://t1.edugis.nl/tiles/tilecache.py?map=maps/edugis/cache/population.map', //note: this is actually a tileservice and only works with the spherical mercator tileschema
            layers:'Bevolkingsdichtheid_2010'
            });
        });
    $("#addBing").click(function() {
        $('#map').data("mapQuery").layers({
            type:'bing',
            label: 'Bing Aerial',
            view:'satellite',
            key:'ArAGGPJ16xm0RXRbw27PvYc9Tfuj1k1dUr_gfA5j8QBD6yAYMlsAtF6YkVyiiLGn'
            });
        });
    */
    
    
    aggiornaPosizionamentoOL();
    
	//statoMappaPrecedente = getStatoMappaFromCookies();
	//if (statoMappaPrecedente.extent) {
	//	$('#map').data("mapQuery").center({box:statoMappaPrecedente.extent});
	//}else{
	//	$('#map').data("mapQuery").center({box:[313263.0,4879724.0,517049.0,5145994.0]});
	//}
	//attivaRegistrazioneStato(mq.olMap);
    
    //DA RIMETTERE COME TOOL ATTIVO DEL PANNEL
    //setActiveTool("dragpan");
	
	// AGGIUNTA DEL LIVELLO PER EVIDENZIARE LE GEOMETRIE SELEZIONATE ALL'IDENTIFY
	highlightLayer = new OpenLayers.Layer.Vector(
		"selezioni all'info",
		{
	        displayInLayerSwitcher: false, 
		    isBaseLayer: false 
		}
    );
	mq.olMap.addLayer(highlightLayer);
	
	
	
	//// SE RICEVE IN COME PARAMETRO IL RIFERIMENTO AD UN WMS APRE LA FINESTRA PER AGGIUNGERLO ALLA TOC
	//if(WMSdaAggiungere != null)
	//{
	//	addSourceAndShowLayers(WMSdaAggiungere.urlwms, WMSdaAggiungere.titlewms);
	//}
	
	
	
	
	

	aggiungiMultimappa()




    // aggiunge livelli alle mappe secondarie
    //$('body').csiMappeSecondarie("addLayer2Map",
    //    {
    //        idMap: 1,
    //        layers: [
    //            {
    //                type:'wms',
    //                label:'UnitaAmministrative',
    //                url:'http://geomap.reteunitaria.piemonte.it/ws/taims/rp-01/taimslimammwms/wms_limitiAmm?',
    //                layers: 'UnitaAmministrative',
    //            },
    //            {
    //                type:'wms',
    //                label:'Aree Protette Rete Natura 2000',
    //                url:'http://geomap.reteunitaria.piemonte.it/ws/gsareprot/rp-01/areeprotwms/wms_gsareprot_1?',
    //                layers: 'AreeProtetteReteNatura2000',
    //            }]
    //    });
    //	
	
	
	// nasconde il tasto per aprire la toc
	if (parametriJSON.toc.hideToc != undefined)
		if (parametriJSON.toc.hideToc){
			$("#buttonToc").hide();
			$(".btnTocSec").hide();		
		}

	
	//WMSLogin();
	projectAfterCreationMap();
	
	markers = new OpenLayers.Layer.Markers("Cities");   			
	mq.olMap.addLayer(markers); 
}

function aggiungiMarkatore(lat, lon)
{
    markers.clearMarkers();                
    var location = new OpenLayers.LonLat(lat,lon); 			
    var size = new OpenLayers.Size(30,30);
    var offset = new OpenLayers.Pixel(-(size.w/2), -size.h);
    var icon = new OpenLayers.Icon('/assets/application/agrichim/gis/img/pushpin_50.png',size,offset);
    markers.addMarker(new OpenLayers.Marker(location,icon.clone()));               			
}

function aggiungiMultimappa(){
	// ---- MULTIMAPPA
	if (parametriJSON.multiMap != undefined )
	if (parametriJSON.multiMap.nSeconday > 0)
	{
		var mp_idPrimaryMap = 'map';
		if (parametriJSON.multiMap.idPrimaryMap != undefined && parametriJSON.multiMap.idPrimaryMap!="")
			mp_idPrimaryMap = parametriJSON.multiMap.idPrimaryMap;

		var mp_nSeconday = 1;
		if (parametriJSON.multiMap.nSeconday != undefined && parametriJSON.multiMap.nSeconday!="")
			mp_nSeconday = parametriJSON.multiMap.nSeconday;
			
		var mp_cloneLayers= false;
		if (parametriJSON.multiMap.cloneLayers != undefined && parametriJSON.multiMap.cloneLayers!="")
			mp_cloneLayers = parametriJSON.multiMap.cloneLayers;
		
		var mp_continuousMap= true;
		if (parametriJSON.multiMap.continuousMap != undefined && parametriJSON.multiMap.continuousMap!="")
			mp_continuousMap = parametriJSON.multiMap.continuousMap;
		
		var mp_divContainers= [];
		if (parametriJSON.multiMap.divContainers != undefined && parametriJSON.multiMap.divContainers!="")
			mp_divContainers = parametriJSON.multiMap.divContainers;
		
		var mp_layersMulti= [];
		if (parametriJSON.multiMap.layers != undefined && parametriJSON.multiMap.layers!="")
			mp_layersMulti = parametriJSON.multiMap.layers;
		
			
		
		$('body').csiMappeSecondarie({
			map: '#'+mp_idPrimaryMap // mappa principale
			,nMappe: mp_nSeconday
			,divContainers: mp_divContainers // array stringhe id div contenitori
			,cloneLayers: mp_cloneLayers // se vero clona i livelli della mappa principale
			,mappeContinue: mp_continuousMap
			,layers: mp_layersMulti
			});
		
		// imposta le mappe contiune (true) o che fanno vedere la stessa area (false)
		// da richiamare per allineare o meno le mappe
		$('body').csiMappeSecondarie("mappeContinue",true);
		
	}
	// ----
	
	
	
}


// -----------------------------------------------------------------------------
function addToolPositional(obj, newTool)
{

	if(obj.order != undefined)
		avToolsPositional[obj.order] = newTool;
	else
		avToolsPositional[counterOut++] = newTool;

}

// -----------------------------------------------------------------------------
// scorre l'albero e aggiunge alla mappa tutti i livelli definiti nel JSON
// funzione ricorsiva
function parseJSONLayers(arrML, cartella)
{
	arrML.reverse();
	
	for (var i=0; i<arrML.length; i++)
	{
		if (arrML[i].type.toUpperCase() == "FOLDER")
		{
			if (arrML[i].mapLayers != undefined)
			{
				var nCartella = arrML[i].label;
				if (cartella!=null) nCartella = cartella+"||"+nCartella;
				
				parseJSONLayers(arrML[i].mapLayers, nCartella);
			}else{
				// caso cartella vuota
			}
		}
		else
		{
			var newLayOptions = getLayerOptionsFromJson(arrML[i], cartella);
			if (newLayOptions!= null) $('#map').data("mapQuery").layers(newLayOptions);
		}
	}
	//arrML.reverse();
}

// -----------------------------------------------------------------------------
// dalla definizione JSON crea il livello
function getLayerOptionsFromJson(JSONLay, cartella) {

	var newLay = null;
	switch (JSONLay.type.toUpperCase())
	{
		case "CSI_TMS":
			
			var pFormat = "png";
			if (JSONLay.format != undefined) pFormat = JSONLay.format;
			
			var pLabel = "";
			if (JSONLay.label != undefined) pLabel = JSONLay.label;
			
			var pUrl = "";
			if (JSONLay.datasource != undefined) pUrl = JSONLay.datasource;
			
			var pNumZoomLevels = 10;
			if (JSONLay.numZoomLevels != undefined) pNumZoomLevels = JSONLay.numZoomLevels;
			
			var pLocked = false;
			if (JSONLay.locked != undefined) pLocked = JSONLay.locked;
			
			var pVisibility = true;
			if (JSONLay.visibility != undefined) pVisibility = JSONLay.visibility;

			var pOpacity = 1;
			if (JSONLay.opacity != undefined) pOpacity = JSONLay.opacity;
			
			newLay = {
				type:'csi_tms'
				,getURL: get_url_CSI
				,format: pFormat
				,label: pLabel
				,url: pUrl
				,numZoomLevels: pNumZoomLevels
				,noDelete: pLocked
				,visibility: pVisibility
				,cartellaToc: cartella
				,opacity: pOpacity
			}					
		break;
		
		case "TILECACHE":
			
			var pFormat = "png";
			if (JSONLay.format != undefined) pFormat = JSONLay.format;
			
			var pLabel = "";
			if (JSONLay.label != undefined) pLabel = JSONLay.label;
			
			var pUrl = "";
			if (JSONLay.datasource != undefined) pUrl = JSONLay.datasource;
			
			var pLocked = false;
			if (JSONLay.locked != undefined) pLocked = JSONLay.locked;
			
			var pVisibility = true;
			if (JSONLay.visibility != undefined) pVisibility = JSONLay.visibility;
			
			var pLayers = "";
			if (JSONLay.layers != undefined) pLayers = JSONLay.layers;
			
			var pOpacity = 1;
			if (JSONLay.opacity != undefined) pOpacity = JSONLay.opacity;
			
			newLay = {
				type:'tilecache'
				,format: pFormat
				,label: pLabel
				,url: pUrl
				,numZoomLevels: pNumZoomLevels
				,noDelete: pLocked
				,visibility: pVisibility
				,cartellaToc: cartella
				,layers: pLayers
				,opacity: pOpacity
			}					
		break;
			
		case "CSI_WMS":
			
			var pLayers = "";
			if (JSONLay.layers != undefined) pLayers = JSONLay.layers;
			
			var pLabel = "";
			if (JSONLay.label != undefined) pLabel = JSONLay.label;
			
			var pUrl = "";
			if (JSONLay.datasource != undefined) pUrl = JSONLay.datasource;
			
			var pNumZoomLevels = 10;
			if (JSONLay.numZoomLevels != undefined) pNumZoomLevels = JSONLay.numZoomLevels;
			
			var pLocked = false;
			if (JSONLay.locked != undefined) pLocked = JSONLay.locked;
			
			var pVisibility = true;
			if (JSONLay.visibility != undefined) pVisibility = JSONLay.visibility;

			var pOpacity = 1;
			if (JSONLay.opacity != undefined) pOpacity = JSONLay.opacity;
			
			newLay = {
				type:'csi_wms'
				,label: pLabel
				,url: pUrl
				,layers: pLayers
				,noDelete: pLocked
				,visibility: pVisibility
				,cartellaToc: cartella
				,opacity: pOpacity
			}					
		break;
		
		case "GOOGLE":
			
			var pView = "";
			if (JSONLay.view != undefined) pView = JSONLay.view;
			
			var pLabel = "";
			if (JSONLay.label != undefined) pLabel = JSONLay.label;
			
			var pLocked = false;
			if (JSONLay.locked != undefined) pLocked = JSONLay.locked;
			
			var pVisibility = true;
			if (JSONLay.visibility != undefined) pVisibility = JSONLay.visibility;

			var pOpacity = 1;
			if (JSONLay.opacity != undefined) pOpacity = JSONLay.opacity;
			
			newLay = {
				type:'google'
				,label:pLabel
				,view: pView
				,visibility: pVisibility
				,cartellaToc: cartella
				,opacity: pOpacity
			}									
		break;
	
		case "OSM":
			
			var pLabel = "";
			if (JSONLay.label != undefined) pLabel = JSONLay.label;
			
			var pLocked = false;
			if (JSONLay.locked != undefined) pLocked = JSONLay.locked;
			
			var pVisibility = true;
			if (JSONLay.visibility != undefined) pVisibility = JSONLay.visibility;

			var pOpacity = 1;
			if (JSONLay.opacity != undefined) pOpacity = JSONLay.opacity;
			
			newLay = {
				type:'osm'
				,label:pLabel
				,visibility: pVisibility
				,cartellaToc: cartella
				,opacity: pOpacity
			}					
		break;
	}
	return newLay;
}

function mostraMappaAffiancata()
{
	dbg.log("mostraMappaAffiancata");
	//alert("mappe affiancate");
	innerLayout.toggle('west');
	
}

function cambiaModalitaMappa(tipo)
{
	dbg.log("cambiaModalitaMappa "+tipo);
	switch (tipo)
	{
		case "m1":
			// mappa singola
			innerLayout.close("west");
		break;
		case "m2":
			// mappe doppie affiancate
			innerLayout.open("west");
			$('body').csiMappeSecondarie("mappeContinue",true);
		break;
		case "m3":
			// mappe doppie continue
			innerLayout.open("west");
			$('body').csiMappeSecondarie("mappeContinue",false);
		break;
	}
}
