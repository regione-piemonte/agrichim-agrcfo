/*
 * identify su tutti i livelli accesi
 * mostra il risultato in un popup
*/

var ctrl_infoAllPopup = new OpenLayers.Control.WMSGetFeatureInfo({
		title: lng.get("TB_INFOPOP_TITLE")
        , text: lng.get("TB_INFOPOP")
        , icon: 'ui-icon-info'
		,queryVisible: true,
		drillDown: true,
		infoFormat: 'application/vnd.ogc.gml',
		eventListeners: {
			getfeatureinfo: function(event) {
				var testo = event.text;
				mappa.addPopup(new OpenLayers.Popup.FramedCloud(
					"chicken", 
					mappa.getLonLatFromPixel(event.xy),
					null,
					testo,
					null,
					true
				));
				
			}
		}
	});
