function showInfoDialog(features, echoTarget)
{
	$("#dia_info").dialog("open");
	
	// pulisce il risultato precedente
	$("#"+echoTarget).empty();
	
	debuga = features;
	
/*	
*/
	var typePrev = "";
	var lastDivResult = null;
	
	var divInfo = jQuery('<div id="infoResult"/>', {}).appendTo("#"+echoTarget);
	
	
	var tabsActive = new Array();
	for(var i=0; i<features.length; i++)
	{
		// titolo accordion
		if(features[i].type != typePrev){
			tabsActive[tabsActive.length] = i;
			typePrev = features[i].type;
			jQuery('<h3><a href="#">'+features[i].type+'</a></h3>', {})
				.appendTo(divInfo);
			lastDivResult = jQuery('<div/>', {});
			lastDivResult.appendTo(divInfo);
		}
		// dati
		for(var att in features[i].data)
		{
			jQuery('<div><strong>'+att+': </strong>'+urlize(features[i].data[att],{"target":"_blank"})+'</div>', {})
				.appendTo(lastDivResult);
			
			if (att=="id_sezione_topo") {
				jQuery('<button onclick="apriGrafico('+features[i].data[att]+')">Apri il grafico</button>', {})
					.appendTo(lastDivResult);
			}
		}
		jQuery('<hr/>', {}).appendTo(lastDivResult);
		
	}
	if(features.length==0)
		jQuery('<div id="noDataInfo">Nessun dato trovato.</div>', {}).appendTo("#"+echoTarget);
	
	$('#infoResult').multiAccordion({active: tabsActive});
	//$("#"+echoTarget).html(htmlInfo);
}

/*
 * identify su tutti i livelli accesi
 * mostra il risultato in un popup
*/

var ctrl_infoAll_icon = 'ui-icon-info';
try {
	if (parametriJSON.toolbar.infoall.icon != undefined)
		ctrl_infoAll_icon = parametriJSON.toolbar.infoall.icon;
} catch(e) {}

ctrl_infoAll = new OpenLayers.Control.WMSGetFeatureInfo({
		title: lng.get("TB_INFO_TITLE")
        , text: lng.get("TB_INFO")
		, icon: 'ui-icon-info'
		,queryVisible: true,
		drillDown: true,
		infoFormat: 'application/vnd.ogc.gml',
		eventListeners: {
			beforegetfeatureinfo: function(event) {
				// al click sulla mappa
				$("#"+this.params.target).empty();
				$("#dia_info").dialog("open");
				jQuery('<div id="recupero_info">Recupero dati in corso...</div>', {}).appendTo("#"+this.params.target);
				
			},
			getfeatureinfo: function(event) {
                //$("#"+this.params.target).empty();
                //$.fn.GML_parser(event.text, $("#"+this.params.target));
				var features = event.features;
				
				// evidenziazione geometrie
				if (event.features && event.features.length) {
					dbg.log("evidenzia");
					debugga = event.features;
					highlightLayer.destroyFeatures();
		            highlightLayer.addFeatures(event.features);
					highlightLayer.redraw();
				}				
				this.callback(features, this.params.target);
			}
		},
  params: {target:"divInfo"},
  callback: showInfoDialog
});
