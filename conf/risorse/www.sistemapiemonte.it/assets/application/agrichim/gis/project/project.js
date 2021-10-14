
function projectOnStart(){
	
	 /*$( "#dia_geoest" ).dialog({
		 autoOpen: false,
		 modal: false,
		 height: 450,
		 width: 450 
	});
	 */
	 $("#btn_selezionaCoord")
	 	.button()
	 	.click(function( event ) {
			attivaStrumentoCoordinate();
	 	});	
	 $("#btn_closeModal").button()
	 	.click(function( event ) {
    	/*alert(chosenLat +" "+ chosenLon);*/

      exitFullScreenMode(document);

      if (chosenLon!="" && chosenLat!="")
      {
        document.coordinate.coordinataNordUtm.value = Math.round(chosenLon);
        document.coordinate.coordinataEstUtm.value = Math.round(chosenLat);
        pulisciCoordinateBoagaGradi();
        document.coordinate.tipoGeoreferenziazione.value = 'G';
      }
      chosenLat="";
      chosenLon="";

      click.deactivate();
    	$('#maintoolbar').mqToolbar("activatePreviousControl");
      
      // nasconde l'overlay con un ritardo di 300 millisecondi
      // per dare il tempo al visualizzatore di ricaricarsi bene
      setTimeout(function()
      {
        $('.dumbBoxWrap').hide();
      }, 300);        
			
	 	});
}

function projectAfterCreationMap(){
	
	if(zoomComune != ""){
		zoomComune = zoomComune.split(",");
		mq.center( { box:[parseFloat(zoomComune[0]),parseFloat(zoomComune[1]),parseFloat(zoomComune[2]),parseFloat(zoomComune[3])]});
	}

	if(zoomCoordinate != ""){
		zoomCoordinate = zoomCoordinate.split(",");
		mq.center({position:[parseFloat(zoomCoordinate[0]),parseFloat(zoomCoordinate[1])]});
	}
}

var drawBoxControl;
var strumentoCoordinateAggiunto = false;

OpenLayers.Control.Click = OpenLayers.Class(OpenLayers.Control, {                
                defaultHandlerOptions: {
                    'single': true,
                    'double': false,
                    'pixelTolerance': 0,
                    'stopSingle': false,
                    'stopDouble': false
                },

                initialize: function(options) {
                    this.handlerOptions = OpenLayers.Util.extend(
                        {}, this.defaultHandlerOptions
                    );
                    OpenLayers.Control.prototype.initialize.apply(
                        this, arguments
                    ); 
                    this.handler = new OpenLayers.Handler.Click(
                        this, {
                            'click': this.trigger
                        }, this.handlerOptions
                    );
                }, 

                trigger: function(e) {
                    var lonlat = mappa.getLonLatFromPixel(e.xy);
                    handleClickCoordinate(lonlat.lon, lonlat.lat)
                    //alert("You clicked near " + lonlat.lat + " N, " + lonlat.lon + " E");
                }

            });
var click = new OpenLayers.Control.Click();	

function disattivaStrumentoCoordinate(){
     if (click) click.deactivate();
     document.getElementById("btn_selezionaCoord").style.background= '#669933 url("/assets/application/agrichim/gis/lib/jquery/jquery-ui-1.10.0/css/jquery-ui-1-10-4-agrichim/images/ui-bg_highlight-soft_60_669933_1x100.png") repeat-x scroll 50% 50%';
}
function attivaStrumentoCoordinate(){
	
    //$('#btn_selezionaCoord').effect( "highlight", {color: '#aa0000'}, 600000 );
    //$('#btn_selezionaCoord').css("background-image: none; background-color: rgb(169, 0, 0)");
    
    document.getElementById("btn_selezionaCoord").style.backgroundImage = "none";
    document.getElementById("btn_selezionaCoord").style.backgroundColor = "rgb(169, 0, 0)";
    
    //$( "#btn_selezionaCoord" ).addClass('classeEvidenziaBottone');
    
    
	$('#maintoolbar').mqToolbar("deactivateAllControls")
	
	if(!strumentoCoordinateAggiunto) mappa.addControl(click);
	strumentoCoordinateAggiunto = true;
	click.activate();
}
	
var chosenLat="";
var chosenLon="";

function handleClickCoordinate(lat, lon){
     aggiungiMarkatore(lat, lon);
     
	//alert("project.js - function handleClickCoordinate()");
	/*
  click.deactivate();
	$('#maintoolbar').mqToolbar("activatePreviousControl")
  */
  chosenLat = lat;
  chosenLon = lon;
	/* alert(lat +" "+ lon); */
	/*
  document.coordinate.coordinataNordUtm.value = Math.round(lon);
  document.coordinate.coordinataEstUtm.value = Math.round(lat);
  document.coordinate.tipoGeoreferenziazione.value = 'G';
  */
  /* $('.dumbBoxWrap').hide(); */
}

/*
function compilaWizElencoLay(){
	
	$("#wiz_elenco_lay").html("");
	var lista = mq.layers();
	var strHTML = "";
	for(var i=0; i<lista.length; i++){
		
		if(lista[i].options.type == "csi_wms"){	
			strHTML  = "<li>";
			strHTML += '<input type="radio" value="'+lista[i].id+'" name="radioLayWiz"';
			if(i==0) strHTML += ' checked="cheched" ';
			strHTML += '/>';
			strHTML += lista[i].label;
			strHTML += "</li>";
			
			$("#wiz_elenco_lay").append(strHTML);
		}

	}
		
	
}

var wiz_lay = "";
var wiz_mail = "";
var wiz_motivo = "";
var wiz_gettone = "";

function clickWizard(wn){

	switch (wn) {
	case 0:
		$("#dia_geoest_content_wizard_1").hide();
		$("#dia_geoest_content_wizard_2").hide();
		$("#dia_geoest_content_wizard_3").hide();
		$("#dia_geoest_content_wizard_4").hide();
		$("#dia_geoest_content_intro").show();
		
		break;
	case 1:
		$("#dia_geoest_content_intro").hide();
		$("#dia_geoest_content_wizard_1").show();
		compilaWizElencoLay();		
		break;	
		
	case 2:
		wiz_lay = $("#dia_geoest_content_wizard_1 input[type='radio']:checked").val();
		$("#dia_geoest_content_wizard_1").hide();
		$("#dia_geoest_content_wizard_2").show();
		break;	
		
	case 3:
		wiz_mail = $("#wiz_mail").val();
		wiz_motivo = $("#wiz_motivo").val();
		
		inviaRichiestaGettone();
		break;	

	case 4:
		wiz_gettone = $("#gettone").val();
		$("#gettone_wait").hide();
		inviaRichiestaScarico();		
		break;	
		
	default:
		break;
	}
}

function inviaRichiestaGettone(){
	
	var centro = mq.center().position.join();
	var deltaExtent =  mq.olMap.getExtent().right - mq.olMap.getExtent().left;
	var metriXpixel = deltaExtent / mq.olMap.size.w;

	
	var wMappa = mq.layersList[wiz_lay].olLayer.url;
	var wmsLay = "";
	if(mq.layersList[wiz_lay].options.type = "csi_wms")
		wmsLay = mq.layersList[wiz_lay].options.layers;

	$("#getGettone_button").hide();
	$("#getGettone_wait").show();
	
	$.ajax({
		type: "POST",
		url: "secserv/getgettone.php",
		data: { 
			wmail: wiz_mail,
			wmap: wMappa,
			wmot: wiz_motivo,
			wcen: centro,
			wres: metriXpixel,
			wlay: wmsLay,
			wtype: mq.layersList[wiz_lay].options.type
			},
		dataType : "json"
	}).done(function(msg ) {
		if(msg.esito){
			$("#dia_geoest_content_wizard_2").hide();
			$("#dia_geoest_content_wizard_3").show();
			$("#getGettone_wait").hide();
			$("#getGettone_button").show();
		}else{
			alert("Attenzione: "+msg.msg);
			$("#getGettone_wait").hide();
			$("#getGettone_button").show();
		}		
	})
	.fail(function() {
		alert( "Impossibile inviare la richiesta." );
		$("#getGettone_wait").hide();
		$("#getGettone_button").show();
	});
}

function inviaRichiestaScarico(){
	
	var msg = "Clicca sui link seguenti per procedere al download dell'immagine ";
	msg += '(<a target="download" href="secserv/scarico.php?gettone='+wiz_gettone+'&tipo=1">file immagine<\a>)';
	msg += ' e del relativo file di georeferenziazione '
	msg += '(<a target="download" href="secserv/scarico.php?gettone='+wiz_gettone+'&tipo=2">world file<\a>)';
	
	$("#dia_geoest_content_wizard_3").hide();
	$("#dia_geoest_content_wizard_4").show();
	$("#gettone_wait").hide();
	$("#geoest_msg_end").html(msg);
	
	/*
	$("#gettone_wait").show();
	$.ajax({
		type: "POST",
		url: "secserv/getscarico.php",
		data: { 
			gettone: wiz_gettone
			}
	})
	.done(function( msg ) {
		$("#dia_geoest_content_wizard_3").hide();
		$("#dia_geoest_content_wizard_4").show();
		$("#gettone_wait").hide();
		$("#geoest_msg_end").html(msg);
	});
	* /
}

var prefissoLabelLivelliSecure = "";
var isLogged = false;
function toggleLogin(){
	if(isLogged) WMSLogout();
	else WMSLogin();
}

function WMSLogin(){
	
	$.ajax({
		url: "secserv/login.php",
		data: { user: "myuser", pass: "mypass" },
		dataType : "json"
		})
	.done(function(msg ) {
		if(msg.logged){
			// LOGIN
			isLogged = true;
			$("#btn_login").button({ label: "Logout" });
			$("#loginLock").attr("src","img/unlocked.png");	
			addHiddenLayers(msg.wms);
		}else{
			alert("Utente non riconosciuto!");
		}		
	})
	.fail(function() {
		alert( "Errore Login" );
	});
}

function WMSLogout(){
	
	$.ajax({
		url: "secserv/logout.php"
		})
	.done(function(msg ) {
		if(msg=="1"){
			// LOGOUT
			isLogged = false;
			$("#btn_login").button({ label: "Login" });
			$("#loginLock").attr("src","img/locked.png");		
			removeHiddenLayers();
		}else{
			alert("Utente non riconosciuto!");
		}		
	})
	.fail(function() {
		alert( "Errore Logout" );
	});	

}

function addHiddenLayers(secListWMS){
	// aggiunge alle toc di tutte le mappe i livelli securizzati
	
	for(var i=0; i<secListWMS.length; i++){
		
		var newLiv = {
	            type: secListWMS[i].type,
	            label: prefissoLabelLivelliSecure + secListWMS[i].label,
	            url: secListWMS[i].datasource,
	            layers: secListWMS[i].layers,
	            visibility: secListWMS[i].visibility,
	            locked: false
	           };
		$('#map').data("mapQuery").layers(newLiv);		
	
		for(var k=0; k<nMappeSecondarie; k++){
			jQuery('#mapSec_'+(k+1)).data("mapQuery").layers(newLiv);
		}
		
	}
	
}

function removeHiddenLayers(){
	// rimuove dalle mappe i livelli aggiunti con il login
	
	var m = $('#map').data("mapQuery");
	var ml = m.layers();
	
	for(var i=0; i<ml.length; i++){
		if(ml[i].label.indexOf(prefissoLabelLivelliSecure) != -1)
			ml[i].remove();
	}
	
	for(var k=0; k<nMappeSecondarie; k++){
		m = $('#mapSec_'+(k+1)).data("mapQuery");
		ml = m.layers();
		for(var i=0; i<ml.length; i++){
			if(ml[i].label.indexOf(prefissoLabelLivelliSecure) != -1)
				ml[i].remove();
		}
	}
	
	// sposta un po' la mappa per rinfrescare le toc
	// TODO: trovare un metodo migliore...
	var centro = mq.center();
	centro.position[0] +=10;
	centro.position[1] +=10;
	mq.center({position:centro.position});
	
	
}


// ####################################################################################

function myGetURLParameters(paramName, sURL) 
{
    if (sURL.indexOf("?") > 0)
    {
       var arrParams = sURL.split("?");         
       var arrURLParams = arrParams[1].split("&");      
       var arrParamNames = new Array(arrURLParams.length);
       var arrParamValues = new Array(arrURLParams.length);     
       var i = 0;
       for (i=0;i<arrURLParams.length;i++)
       {
        var sParam =  arrURLParams[i].split("=");
        arrParamNames[i] = sParam[0];
        if (sParam[1] != "")
            arrParamValues[i] = unescape(sParam[1]);
        else
            arrParamValues[i] = "No Value";
       }

       for (i=0;i<arrURLParams.length;i++)
       {
                if(arrParamNames[i] == paramName){
				//alert("Param:"+arrParamValues[i]);
                return arrParamValues[i];
             }
       }
       return "";
    }
	return "";

}


	// rimappatura delle funzioni mapcomposer richiamate dal catalogo geonetwork
	function addWMSLayer(url){
		
		// http://sdi.provincia.bz.it/geoserver/PAB_WMS03_Trasporti/wms?service=WMS&layer=PAB_WMS03_Trasporti:Intersezione ferroviaria
		// http://geomap.reteunitaria.piemonte.it/ws/gsareprot/rp-01/areeprotwms/wms_gsareprot_1?layer=ZPS
		
		var layWMS = myGetURLParameters("layer",url);
		var sld = myGetURLParameters("sld",url);
		
		//dbg.log("addWMSLayer: \n"+url);
		//dbg.log("sld: \n"+sld);
		
		
		if ( (url.toUpperCase().indexOf("GETCAPABILITIES") !== -1) || (layWMS == "") )
		{
			url = url.split("?")[0];
			dbg.log(url)
			addSourceAndShowLayers(url)
		}
		
		
		//dbg.log(sld);
		url = url.split("?")
		var urlWMS = url[0];
		// TODO: trovare un metodo migliore per estrarre i layers
		
		
		
		/*
		var layWMS = url[1].split("layer=");
		if(layWMS.length>0)
		{
			layWMS = layWMS[].split("&");
			if(layWMS.length>0) layWMS = layWMS[0];
		}
		* /
		//dbg.log(urlWMS);
		//dbg.log(layWMS);
		
		if (sld!="") {
			retrieveWMSparamsAndAddToMap('#map', {
				type:'csi_wms',
				label:layWMS,
				url:urlWMS,
				layers: layWMS,
				sld:sld
			})
		}else{
			retrieveWMSparamsAndAddToMap('#map', {
				type:'csi_wms',
				label:layWMS,
				url:urlWMS,
				layers: layWMS
			})
		}
        
		apriPaginaScelta("mappa");
	}
	
	function addSourceAndShowLayers(url,title){
		
		//dbg.log("addSourceAndShowLayers: "+title+" \n"+url);
		
		// TODO: pensare qualcosa di più furbo per creare il nome
		/*
		if (title==undefined) {
				var nome = url.split("/");
				nome = nome[nome.length-1];
		} else {
				nome = title;
		}
		* /
		//$('#layermanagergn').csiLayerManagerSimpleTree({map:'#map'});
		
		if (url.toUpperCase().indexOf("?") !== -1) 
		{
			url = url.split("?")[0];
		}
		
		//apriPaginaScelta("mappa");
		$("#dia_services").dialog("open");
		/ *
        WMSSources[WMSSources.length] = {
            name: nome,
            url: url                            
        };
        * /
		$('#layermanagergn').csiLayerManagerSimpleTree("recuperaDatiWMSSources", {url: url});
		//$('#layermanagergn').csiLayerManagerSimpleTree("popolaSelectWMSUltimaSelezione");
		//$('#layermanagergn').csiLayerManagerSimpleTree("onChangeWMSList");
		
        //layerManager._popolaSelectWMS();		
        //layerManager._onChangeWMSList();
	}



	// *******************************************************************************************
	
	function apriPaginaScelta(sezione)
	{
		if(sezione==null)
		{
			get = parseGetVars();
			sezione = get["sezione"];
		}
		if(sezione=="catalogo")
		{
			removeClass(document.getElementById("geopuls_2"), "geopulsante_2_selezionato");
			addClass(document.getElementById("geopuls_2"), "geopulsante_2");
			
			removeClass(document.getElementById("geopuls_1"), "geopulsante_1");
			addClass(document.getElementById("geopuls_1"), "geopulsante_1_selezionato");
			
			//appTabs.setActiveTab(0);
			$("#div_mappa").hide();
			$("#div_catalogo").show();
			
			// chiude tutti i dialog
			$(":ui-dialog").each(function(key, value){
			   $(value).dialog("close");
			})
			
		}
		if(sezione=="mappa")
		{
			removeClass(document.getElementById("geopuls_1"), "geopulsante_1_selezionato");
			addClass(document.getElementById("geopuls_1"), "geopulsante_1");
			
			removeClass(document.getElementById("geopuls_2"), "geopulsante_2");
			addClass(document.getElementById("geopuls_2"), "geopulsante_2_selezionato");
			
			//appTabs.setActiveTab(1);
			$("#div_mappa").show();
			$("#div_catalogo").hide();
		}
	}		
	
	// FUNZIONI PER GESTIRE I TASTI NEL BANNER
	function parseGetVars()
	{
	  var argo = new Object();
	  var query = window.location.search.substring(1);
	  if (query)
	  {
	  	
		var strList = query.split('&');
		for(str=0; str<strList.length; str++)
		{
		  var parts = strList[str].split('=');
		  argo[unescape(parts[0])] = unescape(parts[1]);
		}
	  }
	  return argo;
	}

	function hasClass(ele,cls) {
		return ele.className.match(new RegExp('(\\s|^)'+cls+'(\\s|$)'));
	}
	
	function addClass(ele,cls) {
		if (!this.hasClass(ele,cls)) ele.className += " "+cls;
	}
	
	function removeClass(ele,cls) {
		if (hasClass(ele,cls)) {
			var reg = new RegExp('(\\s|^)'+cls+'(\\s|$)');
			ele.className=ele.className.replace(reg,' ');
		}
	}
	
	function accendiPulsante(nP)
	{
		if(nP == 1)
		{
			removeClass(document.getElementById("geopuls_1"), "geopulsante_1");
			addClass(document.getElementById("geopuls_1"), "geopulsante_1_selezionato");
			
			removeClass(document.getElementById("geopuls_2"), "geopulsante_2_selezionato");
			addClass(document.getElementById("geopuls_2"), "geopulsante_2");
			
			apriPaginaScelta("catalogo");
		}
		if(nP == 2)
		{
			removeClass(document.getElementById("geopuls_2"), "geopulsante_2");
			addClass(document.getElementById("geopuls_2"), "geopulsante_2_selezionato");
			
			removeClass(document.getElementById("geopuls_1"), "geopulsante_1_selezionato");
			addClass(document.getElementById("geopuls_1"), "geopulsante_1");
			
			apriPaginaScelta("mappa");
		}
	}
	// *******************************************************************************************
	
	*/
	
    $(function() {
        
        $("#buttonToc" )
            .button({
                //icons: {
                //    secondary: "ui-icon-triangle-1-w"
                //}
            })
            .click(function( event ) {
                event.preventDefault();
                $("#layermanagergn").toggle("slow");
                
                //// cambio icona
                //if($( "#buttonToc" ).button( "option", "icons" ).secondary == "ui-icon-triangle-1-w")
                //    $( "#buttonToc" ).button( "option", "icons", {secondary: "ui-icon-triangle-1-e"});
                //else
                //    $( "#buttonToc" ).button( "option", "icons", {secondary: "ui-icon-triangle-1-w"});
            });
			
		/*
        $("#buttonFind" )
            .button({
				text: true,
                icons: {
                    secondary: "ui-icon-triangle-1-e"
                }
            })
            .click(function( event ) {
                event.preventDefault();
                $("#div_find").toggle("slow");
                
                //// cambio icona
                //if($( "#buttonFind" ).button( "option", "icons" ).secondary == "ui-icon-triangle-1-e")
                //    $( "#buttonFind" ).button( "option", "icons", {secondary: "ui-icon-triangle-1-w"});
                //else
                //    $( "#buttonFind" ).button( "option", "icons", {secondary: "ui-icon-triangle-1-e"});
            });
			
			
		
        $("#btnRicercaClear" )
            .button({})
            .click(function( event ) {
                event.preventDefault();
				clearRicercaComuni();
            });
			
//        $("#btnRicerca" )
//            .button({})
//            .click(function( event ) {
//                event.preventDefault();
//				showComune($("#nome_comune").val())
//            });
			*/
			
    });
	


// RICERCA COMUNI
// -------------------------------------------------------------------------------------
	
var comuniLayer;

var urlWFSLimiti = "http://<WEB_SERVER_HOST:PORT>/ws/aipo/wfs_aipo_limiti_comunali?SERVICE=WFS&";
var urlWMSLimiti = "http://<WEB_SERVER_HOST:PORT>/ws/aipo/wfs_aipo_limiti_comunali?SERVICE=WMS&";
/*
proj32632 = new OpenLayers.Projection('EPSG:32632');
proj900913 = new OpenLayers.Projection('EPSG:900913');
proj4326 = new OpenLayers.Projection('EPSG:4326');
* /

function getListaComuniFromWfs()
{

	var map = mq.olMap;

    filtroWFS = new OpenLayers.Filter.Comparison({
	    type: OpenLayers.Filter.Comparison.NOT_EQUAL_TO,
	    property: "comune",
	    value: "xxx"
    });
	
    protocolloWFS = new OpenLayers.Protocol.WFS({
			version: '1.0.0',
			url:  urlWFSLimiti,
			featureType: "Comuni",
			geometryName: 'msGeometry',
			featureNS: 'http://mapserver.gis.umn.edu/mapserver',
			featurePrefix: 'ms',
			srsName: "EPSG:32632",
			filter: filtroWFS
		});
	
	//protocolloWFS.defaultFilter = filtroWFS;
	risposta = protocolloWFS.read();
	dbg.log(risposta);
	
}

function getListaComuniFromWms() {
	
	protocolloWMS = OpenLayers.Protocol.WMS({
                url: urlWMSLimiti,
                readFormat: new OpenLayers.Format.GeoJSON(),
                outputFormat: "JSON",
                featureType: "Comuni",
                //featurePrefix: 'gn',
                //featureNS: "http://geonetwork-opensource.org",
                //geometryName: 'SHAPE',
                maxFeatures: 10});
	
	
}

function setHTML(a) {
	alert(a);
}

function cercaGraficoSezione(a) {
	alert(a);
}

function clearRicercaComuni() {
    if (comuniLayer) {
    	console.log("Rimuovo comuniLayer precedente dalla mappa.");
    	comuniLayer.removeAllFeatures();
		$("#nome_comune").val("");
		if (comuniLayer) {
			//mq.olMap.removeLayer(comuniLayer);
		}
    	
    }
}


$.widget( "custom.catcomplete", $.ui.autocomplete, {
	_renderMenu: function( ul, items )
	{
		var that = this,
		currentCategory = "";
		$.each( items, function( index, item ) {
			if ( item.category != currentCategory )
			{
				ul.append( "<li class='ui-autocomplete-category'>" + item.category + "</li>" );
				currentCategory = item.category;
			}
			that._renderItemData( ul, item );
		});
	}
});

function showComuneDaLista(dove){
	
	clearRicercaComuni();
	var map = mq.olMap;
	var bbExt = dove.split(",");
	
	// zoom sulla selezione
	$('#map').data("mapQuery").center({box:bbExt});
	
	// evidenziazione dell'area	
	var minx = bbExt[0];
	var miny = bbExt[1];
	var maxx = bbExt[2];
	var maxy = bbExt[3];
	
	var points = [
		new OpenLayers.Geometry.Point(minx, miny),
		new OpenLayers.Geometry.Point(maxx, miny),
		new OpenLayers.Geometry.Point(maxx, maxy),
		new OpenLayers.Geometry.Point(minx, maxy)
	];
	var ring = new OpenLayers.Geometry.LinearRing(points);
	var polygon = new OpenLayers.Geometry.Polygon([ring]);
	
	// create some attributes for the feature
	var attributes = {name: "my name", bar: "foo"};
	
	var feature = new OpenLayers.Feature.Vector(polygon, attributes);
	comuniLayer = new OpenLayers.Layer.Vector("area di ricerca",
		{
			styleMap: new OpenLayers.StyleMap({
				strokeWidth: 2,
				strokeColor: "#C41919",
				fillColor: "#FFFFFF",
				fillOpacity: 0.35
			})
		}
	);
	comuniLayer.addFeatures([feature]);
	
	map.addLayers([comuniLayer]);
}

function showComune(toponimo) {
	
	var map = mq.olMap;
	
    console.log("Ricerca toponimo: " + toponimo);

    // Controllo che ci sia effettivamente input valido
    if (!toponimo || toponimo.trim().length == 0) {
	window.alert("Inserire il nome del comune da inquadrare.");
	return;
    }
    
	// PER RIMUOVERE LE VECCHIE RICERCHE
    //if (comuniLayer) {
    //	console.log("Rimuovo comuniLayer precedente dalla mappa.");
    //	comuniLayer.removeAllFeatures();
    //	map.removeLayer(comuniLayer);
    //}
	clearRicercaComuni();

    // Controllo per la ricerca del comune
    comuniLayer = new OpenLayers.Layer.Vector("WFS", {
		strategies: [new OpenLayers.Strategy.Fixed()],
		protocol: new OpenLayers.Protocol.WFS({
			version: '1.0.0',
			url:  urlWFSLimiti,
			featureType: "Comuni",
			geometryName: 'msGeometry',
			featureNS: 'http://mapserver.gis.umn.edu/mapserver',
			featurePrefix: 'ms',
			srsName: "EPSG:32632"
		}),
		projection: new OpenLayers.Projection('EPSG:32632'),
		styleMap: new OpenLayers.StyleMap({
			strokeWidth: 2,
			strokeColor: "#555555",
			fillColor: "#FFFFFF",
			fillOpacity: 0.5
		}),
		displayInLayerSwitcher: false,
		visibility: true
    });
	
    // Filtro exact match
    var exactMatchFilter = new OpenLayers.Filter.Comparison({
	    type: OpenLayers.Filter.Comparison.EQUAL_TO,
	    property: "comune",
	    value: toponimo.toUpperCase()
    });
    comuniLayer.filter = exactMatchFilter;
    comuniLayer.events.register("loadend", null, function() {
	    document.getElementById("map").style.cursor='default';
	    var num = comuniLayer.features ? comuniLayer.features.length : 0;
	    
	    // Ho eseguito un exactMatch?
	    if (comuniLayer.filter == exactMatchFilter) {
	    	console.log("Ho eseguito il filtro exactMatch");
	    
		    if (num == 0) {
		    	// La ricerca per exact match non ha funzionato: eseguo una ricerca per sottostringa
		    	comuniLayer.removeAllFeatures();
		    	map.removeLayer(comuniLayer);
		        comuniLayer.filter = new OpenLayers.Filter.Comparison({
		    	    type: OpenLayers.Filter.Comparison.LIKE,
		    	    property: "comune",
		    	    value: '*' + toponimo.toUpperCase() + '*'
		        });
		        map.addLayers([comuniLayer]); // Questo scatena anche il caricamento effettivo
		        document.getElementById("map").style.cursor='progress';
		    } else {
		    	// Trovato un poligono: lo mostro (qualcuno, probabilmente OpenLayers, si proccupa di rimettere a posto il cursore)
		    	map.zoomToExtent(comuniLayer.getDataExtent());
		    }
	    } else {
	    	// Ho eseguito il filtro con LIKE
	    	console.log("Ho eseguito il filtro con LIKE");

		    if (num <= 0 || num > 9) {
		    	// La ricerca non ha prodotto un risultato utile
		    	map.removeLayer(comuniLayer);
		    	comuniLayer = null;
		    	window.alert("La ricerca di \"" + toponimo + "\" ha prodotto " + num
		    			+ " risultato/i.\nModifica il testo di ricerca e riprova.");
		    } else {
		    	// Trovato un po' di poligoni: li mostro (qualcuno, probabilmente OpenLayers, si proccupa di rimettere a posto il cursore)
		    	map.zoomToExtent(comuniLayer.getDataExtent());
		    }
	    }
    });
    map.addLayers([comuniLayer]); // Questo scatena anche il caricamento effettivo
    document.getElementById("map").style.cursor='progress';
}


// -----------------------------------------------------------------------------
function apriGrafico(idSez)
{
	$("#dialogGraf").dialog( "open" );
	caricaDatiGrafico("F7",idSez);
}















//
////<!-- export_olon-------------------------------------------------------->
//function export_olon() 
//{
//	//var format_olon = new OpenLayers.Format.OLON();
//	//format_ows = new OpenLayers.Format.OWSContext();
//	//format_ows = new OpenLayers.Format.OWSCommon();
//	
//	//format_ows = new OpenLayers.Format.OWSContext.v0_3_1()
//	
//	var map = mq.olMap;
//	
//	format_ows = new OpenLayers.Format.OWSContext();
//	
//	//try
//	//{      
//	   //map.removeControl(vectPanel);
//	   //vectPanel = null;
//	  
//	  map.bounds = map.getExtent();
//	  map.resolution = map.getResolution();
//	  
//	  debugga = map;
//	  var content = format_ows.write(map,true);
//	  dbg.log(content);
//	  /*
//	  $("export_area").value = content;
//	  $("export_area").style.display = "block";
//	  $("export_link").href = "data:text," + escape(content);
//	  $("export_link").style.display="block";
//	  */
//	  //vectPanel = new OpenLayers.Control.EditingToolbar(vector);
//	  //map.addControl(vectPanel);
//	//}
//	//catch (e) { alert("export_olon: " + e.message); }
//  
//} //export_olon