
var statoMappaPrecedente = null;
var usaCookies = false;

// ----------------------------------------------------------------------------
function setCookie(ck_name, ck_value) {
    // con la libreria di jquery
    $.cookie(ck_name, ck_value );
}

// ----------------------------------------------------------------------------
function getCookie(ck_name) {
    // con la libreria di jquery
    return $.cookie(ck_name);
}

// ----------------------------------------------------------------------------
// includere questa funzione per attivare il salvataggio e ripristino dello
// stato del viewer al refresh della pagina
function attivaRegistrazioneStato(objMap) {
    
	usaCookies = true;
	
    // ripristina la mappa precedente
	statoMappaPrecedente = getStatoMappaFromCookies();
    // zoom
    if (zoomInizialeRequest!=null) {
        var bbox = zoomInizialeRequest.split(",");
        
        // conversione da 32632 alla proiezione della mappa di sfondo
        var source = new Proj4js.Proj("EPSG:32632");
        var dest = new Proj4js.Proj(mq.olMap.projection);
        
        var lowerLeft = new Proj4js.Point(bbox[0], bbox[1]);
        var upperRight = new Proj4js.Point(bbox[2], bbox[3]);
        
        Proj4js.transform(source, dest, lowerLeft);
        Proj4js.transform(source, dest, upperRight);
        
        var bbox = [lowerLeft.x, lowerLeft.y, upperRight.x, upperRight.y];
        
        dbg.log("zoom iniziale richiesto: ("+lowerLeft.x+","+lowerLeft.y+"),("+ upperRight.x+","+upperRight.y+")");
        
        jQuery('#'+objMap.div.id).data("mapQuery").center({box:bbox});	
    }else{
        if (statoMappaPrecedente.position) {
            jQuery('#'+objMap.div.id).data("mapQuery").center({zoom:statoMappaPrecedente.zoom,
                                                               position:statoMappaPrecedente.position});
        }else{
            //jQuery('#'+objMap.div.id).data("mapQuery").center({box:[313263.0,4879724.0,517049.0,5145994.0]});
            jQuery('#'+objMap.div.id).data("mapQuery").center({box:[507186.0, 5383731.0, 1681258.0, 5967098.0]});	
        }    
    }

    
    
    
    // livelli
    
    
    // registra lo stato della mappa ad ogni cambio
    
    setCookie('mq_stato', true);
    
    // zoom
    objMap.events.register('zoomend', objMap, function () {
        var c = jQuery('#'+objMap.div.id).data("mapQuery").center();
        setCookie('mq_zoom', c.zoom);
        setCookie('mq_position', c.position);
    });
    objMap.events.register('moveend', objMap, function () {
        var c = jQuery('#'+objMap.div.id).data("mapQuery").center();
        setCookie('mq_zoom', c.zoom);
        setCookie('mq_position', c.position);
    });
    
    
}

// ----------------------------------------------------------------------------
function getStatoMappaFromCookies() {
    // recupera dai cookies tutte le informazioni del viewer
    
    var statoMappa = {
        zoom: null,
        position: null
    };
    
    if (getCookie("mq_stato")) {
        
        if (getCookie("mq_position")) {
            c = getCookie("mq_position").split(",");
            for (var i=0; i<c.length; i++) {
                c[i] = parseFloat(c[i]);
            }
            statoMappa.position = c;
            statoMappa.zoom = getCookie("mq_zoom");
            //statoMappa.cartelleToc = eval('(' + getCookie("cartelleToc") + ')');
        }
        
        
    }
    return statoMappa;
}

// ----------------------------------------------------------------------------
function salvaTematismiNeiCookies() {

	if(!usaCookies) return true;
    //alert("tematismi nei cookies");
	tematismi = new Array();
	var mqliv = mq.layers();
    mqliv.reverse();
	for (var i=0; i<mqliv.length; i++) {
        
        if (mqliv[i].options.type == "csi_wms") {
            // TODO: non cancellare ma convertire in stringa come richiesto da mq            
            delete mqliv[i].options.maxExtent;
        }
        
		tematismi[i] = mqliv[i].options;
        
        // per mantenere lo stato ON/OFF
        tematismi[i].visibility = mqliv[i].visible();
        
        /*
        // cartelle della TOC
        var alberoToc = $("#layman_tree").dynatree("getTree").toDict();
        alberoToc = alberoToc.children;
        var cartelleToc = new Array();
        for(var k=0; k<alberoToc.length; k++) {
            if (alberoToc[k].isFolder) 
                cartelleToc[k] = alberoToc[k].title;
        }
        */
	}
    try {
        setCookie("tematismi",JSON.stringify(tematismi));
    } catch(e) {
        // dannato IE!!
    }
	
}



//map.events.register('moveend', map, function () {
//    Set_Cookie('mapx', map.getCenter().lon );
//    Set_Cookie('mapy', map.getCenter().lat );
//    Set_Cookie('mapz', map.getZoom() );
//});
//map.events.register('changebaselayer', map, function () {
//    Set_Cookie('base', map.baseLayer.name);
//});
//
//
//
//And recall the state from cookies:
//
//   var lon = Get_Cookie('mapx');
//   var lat = Get_Cookie('mapy');
//   var zum = Get_Cookie('mapz');
//   if (lon && lat && zum) {
//     map.setCenter(new OpenLayers.LonLat(lon,lat),zum);
//   }
//
//   var base = Get_Cookie('base');
//   if (base) selectBasemap(base);