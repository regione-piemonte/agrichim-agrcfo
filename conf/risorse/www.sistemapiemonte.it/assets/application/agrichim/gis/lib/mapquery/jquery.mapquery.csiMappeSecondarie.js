var nMappeSecondarie = 0;
var idMappaGeneratoreEventi = null;
var mappeContinue = true;

// variabili di servizio
var layersMQ1 = new Array();
var layersMQ2 = new Array();
var layersMQ3 = new Array();
var layersMQ4 = new Array();
var indiceLivMQ = 0;

(function($) {

    $.template('mappaSecondariaDialog',
		'<div id="dia_mapSec_${id}">'+
		    '<div id="mapSec_${id}" class="mapSec"></div>'+
			'<div id="tocSec_${id}" class="tocSec" style="display:none"></div>'+
			'<button id="btnTocSec_${id}" class="btnTocSec">&nbsp;</button>'+
	    '</div>');

    $.template('mappaSecondaria',
			'<div id="mapSec_${id}" class="mapSec"></div>'+
			'<div id="tocSec_${id}" class="tocSec" style="display:none"></div>'+
			'<button id="btnTocSec_${id}" class="btnTocSec">cataloghi</button>'
	    );

	$.widget("mapQuery.csiMappeSecondarie", {
		
		options: {
			map: undefined,
			// oggetto mapquery
			
			nMappe: 1,
			// numero di mappe secondarie
			
			divContainers: Array(),
			// id dei div che contengono le mappe
			
			cloneLayers: false,
			// se vero carica nelle mappe secondarie i livelli della mappa principale
			
			mappeContinue: true,
			// se falso le mappe vengono centrate sulla stessa area
			// se vero le mappe mostano la mappa in continuo
			
			layers: new Array()
			

	
		},
		
		
		// -----------------------------------------------------------------------------
		// scorre l'albero e aggiunge alla mappa tutti i livelli definiti nel JSON
		// funzione ricorsiva
		_parseJSONLayersMM: function(arrML, cartella)
		{
			if(arrML instanceof Array){}else{return true;}
			arrML.reverse();
			
			for (var i=0; i<arrML.length; i++)
			{
				if(typeof(arrML[i].type)=="undefined") return true;
				
				if (arrML[i].type.toUpperCase() == "FOLDER")
				{
					if (arrML[i].mapLayers != undefined)
					{
						var nCartella = arrML[i].label;
						if (cartella!=null) nCartella = cartella+"||"+nCartella;
						
						this._parseJSONLayersMM(arrML[i].mapLayers, nCartella);
					}else{
						// caso cartella vuota
					}
				}
				else
				{
					var newLayOptions = getLayerOptionsFromJson(arrML[i], cartella);
					if (newLayOptions!= null){
						if(indiceLivMQ==0) layersMQ1.push(newLayOptions);
						if(indiceLivMQ==1) layersMQ2.push(newLayOptions);
						if(indiceLivMQ==2) layersMQ3.push(newLayOptions);
						if(indiceLivMQ==3) layersMQ4.push(newLayOptions);
					}
				}
			}
			//arrML.reverse();
		},
		
		_create: function() {
			
			var self = this;
			map = $(this.options.map).data('mapQuery'); // da errore in IE
			
			nMappeSecondarie = this.options.nMappe;
			mappeContinue = this.options.mappeContinue;
			layers = this.options.layers;
			
			// init della variabile
			idMappaGeneratoreEventi =  $(this.options.map).data('mapQuery').olMap.div.id;
			
			// creazione della mappa OL a partire dai parametri della mappa principale
			livelliClonati = new Array();
			if(this.options.cloneLayers){
				for(var i in map.layersList)
					livelliClonati[livelliClonati.length] = map.layersList[i].options;
			}else{
				
				//alert("");
				for(var i=0; i<layers.length; i++)
				{
					//console.log(layers[i]);
					//layers[i] = eval('('+layers[i]+')');
					//console.log(layers[i]);
						
					self._parseJSONLayersMM(eval('('+layers[i]+')'));
					indiceLivMQ++;							
				}
					
				
				//debu = layers;
				
				
					
			}			
			
			for(var i=1; i<=this.options.nMappe; i++)
			{
				// hmtl dei contenitori mappe sec.
				if(this.options.divContainers[i-1]==null)
				{
					$.tmpl('mappaSecondariaDialog',{
						id: i
					}).appendTo(this.element);
				}else{
					$.tmpl('mappaSecondaria',{
						id: i
					}).appendTo($("#"+this.options.divContainers[i-1]));
				}
				
				if(this.options.divContainers[i-1]==null)
				{
					// trasformazione in dialog se non definito il contenitore
					$("#dia_mapSec_"+i).dialog(
					{
						title: "Mappa "+i,
						resizable: true,
						height:350,
						width:400,
						autoOpen: true,
						position: { 
							my: "left top",
							at: "left+"+(i*40)+" top+"+(i*40),
							of: "#map"
						}
						,dragStop: function( event, ui ) {
							aggiornaMappeDragStop(this.id);
						}
						,drag: function( event, ui ) {
							aggiornaMappeDragStop(this.id);
						}
					});					
				}
				
				

				// genera le mappe
				if(self.options.cloneLayers)
				{
					if(livelliClonati.length>0)
					{
						$('#mapSec_'+i).mapQuery({
							projection: map.projection,
							maxExtent: map.maxExtent,
							layers: livelliClonati
						});						
					}					
				}else{
					
					dbg.log("i = "+i);
					if(i==1)
					{
						$('#mapSec_'+i).mapQuery({
							projection: map.projection,
							maxExtent: map.maxExtent,
							layers: layersMQ1
						});
					}
					if(i==2)
					{
						$('#mapSec_'+i).mapQuery({
							projection: map.projection,
							maxExtent: map.maxExtent,
							layers: layersMQ2
						});	
					}
					if(i==3)
					{
						$('#mapSec_'+i).mapQuery({
							projection: map.projection,
							maxExtent: map.maxExtent,
							layers: layersMQ3
						});	
					}
					if(i==4)
					{
						$('#mapSec_'+i).mapQuery({
							projection: map.projection,
							maxExtent: map.maxExtent,
							layers: layersMQ4
						});	
					}
				}
				
				

				
				// TOC integrata nelle mappe secondarie
				// SPECIFICARE IL TIPO COME FOSSE UNA QUALSIASI MAPPA
				$('#tocSec_'+i).csiLayerManagerMinimal({map:'#mapSec_'+i});
				//$('#tocSec_'+i).csiLayerManagerSimpleTree({map:'#mapSec_'+i});
				
				// tasto che apre/chiude la toc integrata
			    $('#btnTocSec_'+i).button({
					//icons: {primary: "ui-icon-image"},
					//text:false
					}).click(function(event)
					{
						event.preventDefault();
						$('#tocSec_'+this.id.split("_")[1]).toggle();
					});
				
				// centra la mappa
				$('#mapSec_'+i).data("mapQuery").center({box:[313263.0,4879724.0,517049.0,5145994.0]});
				$('#mapSec_'+i).data("mappaSecondaria",true);
				
			}
			
			// collega gli eventi della mappa principale
			map.olMap.events.register("moveend", map.olMap , function(e){aggiornaMappe(e.object);});
			map.olMap.events.register("mouseover", map.olMap , function(e){selezionaMappa(this);});
			map.olMap.events.register("move", map.olMap , function(e){aggiornaMappe(e.object);});
			
			// collega gli eventi delle mappe secondarie
			for(var i=1; i<=nMappeSecondarie; i++)
			{
				var indice = parseInt(i);
				
				$('#mapSec_'+i).data("mapQuery")
					.olMap.events.register(
						"moveend",
						$('#mapSec_'+i).data("mapQuery").olMap,
						function(e){aggiornaMappe(e.object);
						});
					
				$('#mapSec_'+i).data("mapQuery")
					.olMap.events.register(
						"mouseover",
						$('#mapSec_'+i).data("mapQuery").olMap,
						function(e){selezionaMappa(this);});
						
				$('#mapSec_'+i).data("mapQuery")
					.olMap.events.register(
						"move",
						$('#mapSec_'+i).data("mapQuery").olMap,
						function(e){aggiornaMappe(e.object);
						});
			}
			
			// ALLINEA TUTTE LE MAPPE
			aggiornaMappe(map.olMap);
			
		},
		
		_destroy: function() {
			// none
		},
		
		addLayer2Map: function(param){
			$('#mapSec_'+param.idMap).data("mapQuery").layers(param.layers);
		},
		
		mappeContinue: function(inContinuo){
			// affianca le mappe o le centra in base al parametro
			this.options.mappeContinue = inContinuo;
			mappeContinue = inContinuo;
			aggiornaMappe($(this.options.map).data('mapQuery').olMap);		
		}
		
	});
	
})($);

// gestione spostamento reciproco delle mappe

function aggiornaMappeDragStop(k)
{
	var i = k.split("dia_mapSec_")[1];
	aggiornaMappe(jQuery('#mapSec_'+i).data("mapQuery").olMap);
}

function selezionaMappa(m) {
	idMappaGeneratoreEventi = m.div.id;
}

function aggiornaMappe(e)
{
	
	if(e.div.id != idMappaGeneratoreEventi) return true;
	
	var mapSourceExt = e.getExtent();
	// usa il zoom level per evitare il sali scendi al cambio mappa attiva
	
	var centro = e.getCenter()
	
	// mappa di partenza
	// pixel
	var m1_off = $("#"+e.div.id).offset();
	var m1_xp1 = m1_off.left;
	var m1_xp2 = m1_xp1 + $("#"+e.div.id).width();
	var m1_yp1 = m1_off.top;
	var m1_yp2 = m1_yp1 + $("#"+e.div.id).height();
	// geo
	var m1_x1 = mapSourceExt.left;
	var m1_x2 = mapSourceExt.right;
	var m1_y1 = mapSourceExt.bottom;
	var m1_y2 = mapSourceExt.top;
	
	// dimensione del pixel (metri per pixel)
	var Dp = (m1_x2 - m1_x1) / (m1_xp2 - m1_xp1);
	
	// aggiorna mappe secondarie
	for(var i=1; i<=nMappeSecondarie; i++)
	{
		if(('mapSec_'+i) != idMappaGeneratoreEventi)		
		{
			var centroX = centro.lon;
			var centroY = centro.lat;
			
			if (mappeContinue) {
				// pixel
				var m2_off = $('#mapSec_'+i).offset();
				var m2_xp1 = m2_off.left;
				var m2_yp1 = m2_off.top;
				
				var m2_xp2 = m2_xp1 + $('#mapSec_'+i).width();
				var m2_yp2 = m2_yp1 + $('#mapSec_'+i).height();
				// geo
				
				var m2_x1 = m1_x1 - ( (m1_x2 - m1_x1) * (m1_xp1 - m2_xp1) / (m1_xp2 - m1_xp1) );
				var m2_y2 = m1_y2 + ( (m1_y2 - m1_y1) * (m1_yp1 - m2_yp1) / (m1_yp2 - m1_yp1) );
				
				centroX = m2_x1 + Dp * ((m2_xp2 - m2_xp1)/2);
				centroY = m2_y2 - Dp * ((m2_yp2 - m2_yp1)/2);
			}
			
			jQuery('#mapSec_'+i).data("mapQuery")
				.center({position: [ centroX, centroY ], zoom: e.zoom});
		}
	}
	// se si muove una mappa secondaria, aggiorna la mappa principale, che aggiornerà le secondarie rimanenti
	if(map.olMap.div.id != idMappaGeneratoreEventi)
	{
		var centroX = centro.lon;
		var centroY = centro.lat;
			
		if (mappeContinue) {
			// pixel
			var m2_off = $('#'+map.olMap.div.id).offset();
			var m2_xp1 = m2_off.left;
			var m2_yp1 = m2_off.top;
			
			var m2_xp2 = m2_xp1 + $('#'+map.olMap.div.id).width();
			var m2_yp2 = m2_yp1 + $('#'+map.olMap.div.id).height();
			// geo
			
			var m2_x1 = m1_x1 - ( (m1_x2 - m1_x1) * (m1_xp1 - m2_xp1) / (m1_xp2 - m1_xp1) );
			var m2_y2 = m1_y2 + ( (m1_y2 - m1_y1) * (m1_yp1 - m2_yp1) / (m1_yp2 - m1_yp1) );
			
			centroX = m2_x1 + Dp * ((m2_xp2 - m2_xp1)/2);
			centroY = m2_y2 - Dp * ((m2_yp2 - m2_yp1)/2);
		}
		
		jQuery('#'+map.olMap.div.id).data("mapQuery")
			.center({position: [ centroX, centroY ], zoom: e.zoom});
	}
}