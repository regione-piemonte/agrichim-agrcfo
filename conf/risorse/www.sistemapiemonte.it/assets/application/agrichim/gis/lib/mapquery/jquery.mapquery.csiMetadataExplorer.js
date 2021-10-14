var tableCSW_ID = "tableCSW";

var urlBaseMetadata = "http://www.ruparpiemonte.it/geocatalogorp/geonetworkrp/srv/it/metadata.show.embedded?uuid=";

var CSWSources = [
    {
        name: "Geoportale Regione Piemonte",
        url: 'http://www.ruparpiemonte.it/geocatalogorp/geonetworkrp/srv/it/csw'
    }
];

function toggleRicercaAvanzata() {
	if($("#dia_me_chk_avanzata").is(':checked'))
		$("#tabellaRicercaAvanzata").show();
	else
		$("#tabellaRicercaAvanzata").hide();
}

(function($) {
$.template('csiMetdataExplorerDialog',
	
	'<div id="dia_metadataexplorer">'
		+'<fieldset>'
			+'<legend>'
				+'<span>${ME_SOR_TITLE}</span>'
			+'</legend>'
			+'<label for="dia_me_services">${ME_SOR_LABEL}</label>'
			+'<select id="dia_me_services"></select>'
		+'</fieldset>'
		
		+'<fieldset id="dia_me_form">'
		
		+'<table class="metaexp_table_form">'
			+'<tr>'
				+'<td>'
					
					+'<legend>'
						+'<span>${ME_RIC_TITLE}</span>'
					+'</legend>'
					+'<label for="dia_me_txt">${ME_RIC_LABEL}</label>&nbsp;&nbsp;&nbsp;'
					+'<input id="dia_me_txt" size="50" />'
					
					+'<fieldset>'
						+'<legend>'
							+'<input type="checkbox" id="dia_me_chk_avanzata" onclick="toggleRicercaAvanzata()" />'
							+'<span>${ME_RICAVA_TITLE}</span>'
						+'</legend>'
						
						+'<table id="tabellaRicercaAvanzata" style="display:none">'
							+'<tr>'
								+'<td>'
									+'<label for="dia_me_titolo">${ME_RICAVA_TIT}</label>'
									+'<input id="dia_me_titolo" />'
								+'</td>'						
								+'<td>'
									+'<label for="dia_me_chk_ext">${ME_RICAVA_EXT}</label>'
									+'<input type="checkbox" id="dia_me_chk_ext" />'
								+'</td>'
							+'</tr>'
							+'<tr>'
								+'<td>'
									+'<label for="dia_me_da">${ME_RICAVA_DA}</label>'
									+'<input id="dia_me_da" />'
								+'</td>'
								+'<td>'
									+'<label for="dia_me_a">${ME_RICAVA_A}</label>'
									+'<input id="dia_me_a" />'
								+'</td>'
							+'</tr>'
						+'</table>'
						
					+'</fieldset>'
				+'</td>'
				+'<td class="metaexp_td_tasti">'
					+'<button id="dia_me_cerca" >${ME_BTN_CERCA}</button>'
					+'<br/>'
					+'<button id="dia_me_reset" >${ME_BTN_RESET}</button>'					
				+'</td>'
			+'<tr>'
		+'</table>'

		+'</fieldset>'
		
		
		+'<div class="me_titolo">${ME_TITLE2}</div>'
		+'<div id="dia_me_list">'
			+'<table id="'+tableCSW_ID+'"></table>'
   +'<div id="tableCSW_pager"></div>'
		+'</div>'
		+'<div class="me_titolo">${ME_TITLE3}</div>'
		+'<div id="dia_me_dettaglio">'
			+'<div id="dia_me_dettaglio_button">'
			+'<button id="dia_me_dettaglio_button_metadata">${ME_BTN_DET_META}</button>'
			+'<button id="dia_me_dettaglio_button_mappa">${ME_BTN_DET_CENTRA}</button>'
			+'<button id="dia_me_dettaglio_button_centra">${ME_BTN_DET_MAPPA}</button>'
			+'</div>'
			+'<div id="dia_me_dettaglio_text"></div>'
		+'</div>'
		
	+'</div>'
);
$.template('csiMetdataExplorerDialogModale',
	'<div id="dia_metadataexplorer_modale">'
	+'<iframe src="" id="dia_metadata_iframe"></iframe> '
	+'</div>');

$.widget("mapQuery.csiMetadataExplorer", {
	
    options: {
    },
	
    _create: function() {
        
        //this.element.addClass('ui-widget  ui-helper-clearfix ui-corner-all');
        
        $.tmpl('csiMetdataExplorerDialog',{
			ME_BTN_CERCA: lng.get("ME_BTN_CERCA"),
			ME_BTN_RESET: lng.get("ME_BTN_RESET"),
			ME_SOR_TITLE: lng.get("ME_SOR_TITLE"),
			ME_SOR_LABEL: lng.get("ME_SOR_LABEL"),
			ME_RIC_TITLE: lng.get("ME_RIC_TITLE"),
			ME_RIC_LABEL: lng.get("ME_RIC_LABEL"),			
			ME_RICAVA_TITLE: lng.get("ME_RICAVA_TITLE"),
			ME_RICAVA_DA: lng.get("ME_RICAVA_DA"),
			ME_RICAVA_A: lng.get("ME_RICAVA_A"),
			ME_RICAVA_EXT: lng.get("ME_RICAVA_EXT"),
			ME_RICAVA_TIT: lng.get("ME_RICAVA_TIT"),
			ME_TITLE2: lng.get("ME_TITLE2"),
			ME_TITLE3: lng.get("ME_TITLE3"),
			ME_BTN_DET_META: lng.get("ME_BTN_DET_META"),
			ME_BTN_DET_CENTRA: lng.get("ME_BTN_DET_CENTRA"),
			ME_BTN_DET_MAPPA: lng.get("ME_BTN_DET_MAPPA")
		}).appendTo(this.element);
		
        $.tmpl('csiMetdataExplorerDialogModale',{
			//ME_BTN_CERCA: lng.get("ME_BTN_CERCA"),
		}).appendTo(this.element);
        
		// DIALOG METADATAEXPLORER
		$("#dia_metadataexplorer").dialog(
        {
            resizable: false,
            height: 600,
            width: 800,
            autoOpen: false,
			title: lng.get("ME_TITLE"),
            //modal: true,
            
            buttons: {
                "Chiudi": function() {
					jQuery("#"+tableCSW_ID).jqGrid("GridUnload");
                    $( this ).dialog( "close" );
                }
            }          
        });
		this._popolaSelectCSW();
		
		// DIALOG FINESTRA MODALE DETTAGLIO
        $("#dia_metadataexplorer_modale").dialog(
        {
            resizable: false,
            height: 550,
            width: 800,
            autoOpen: false,
			modal: true,
			title: lng.get("ME_TITLE_MODALE"),
            //modal: true,
            
            buttons: {
                "Chiudi": function() {
                    $( this ).dialog( "close" );
                }
            }            
        });
		
		$( "#dia_me_da" ).datepicker({
			showOn: "button",
			buttonImage: "img/ico_date.gif",
			buttonImageOnly: true,
			dateFormat: "yy-mm-dd"
		});
		
		$( "#dia_me_a" ).datepicker({
			showOn: "button",
			buttonImage: "img/ico_date.gif",
			buttonImageOnly: true,
			dateFormat: "yy-mm-dd"
		});
		
		// TASTO RICERCA
        $("#dia_me_cerca").button({
                text: true,
                icons: {primary: "ui-icon-search"}
            })
            .click(function( event )
            {
                event.preventDefault();
				
				// avvia la ricerca
				var cswUrl = CSWSources[$('#dia_me_services').val()].url; // valore dalla select
				
				var text = $("#dia_me_txt").val();
				var title = "";
				var lowerCorner = "";
				var upperCorner = "";
				var tempExtentBegin = "";
				var tempExtentEnd = "";
				
				if ($("#dia_me_chk_avanzata").is(':checked')) {
					title = $("#dia_me_titolo").val();
					if ($("#dia_me_chk_ext").is(':checked')) {
						var ext = mq.olMap.getExtent();
						
						// conversione in lat lon
						var mapProjection = mappa.getProjection();
						
						var p1 = new OpenLayers.LonLat(ext.left,ext.bottom)
							.transform(new OpenLayers.Projection(mapProjection), new OpenLayers.Projection("EPSG:4326"));
						var p2 = new OpenLayers.LonLat(ext.left,ext.bottom)
							.transform(new OpenLayers.Projection(mapProjection), new OpenLayers.Projection("EPSG:4326"));
						
						lowerCorner = p1.lon+" "+p1.lat;
						upperCorner = p2.lon+" "+p2.lat;
					}
					tempExtentBegin = $("#dia_me_da").val();
					tempExtentEnd = $("#dia_me_a").val();
							
				}
				var cswXmlRequest = makeCswXmlRequest(text, title, lowerCorner, upperCorner, tempExtentBegin, tempExtentEnd)
				
				requestCswRecords(cswUrl,cswXmlRequest);
            }
        );
		
		// TASTO RESET
        $("#dia_me_reset").button({
                text: true,
                icons: {primary: "ui-icon-circle-close"}
            })
            .click(function( event )
            {
                event.preventDefault();
				jQuery("#"+tableCSW_ID).jqGrid("GridUnload");
				$("#dia_me_form input").val("");
            }
        );

/***********/			
		// TASTO APRE METADATA
        $("#dia_me_dettaglio_button_metadata").button({
                text: true,
                disabled: true,
                icons: {primary: "ui-icon-info"}
            })
            .click(function( event )
            {
                event.preventDefault();
                $("#dia_metadataexplorer_modale").dialog("open");
                // TODO: scrivere il dettaglio in $(#dia_me_dettaglio_text)
                var id = jQuery("#"+tableCSW_ID).getGridParam('selrow');
                var rowData = jQuery("#"+tableCSW_ID).getRowData(id); 
                var id = rowData['identifier'];
                if (id=='') {
                    //TODO traduzione
                    custom_alert('Manca', '', 'alert')
                    return false;
                }
                //dbg.log(id);
				$("#dia_metadata_iframe").attr("src",urlBaseMetadata+id);				
                // ARPIRE METADATO CON ID
				// http://www.ruparpiemonte.it/geocatalogorp/geonetworkrp/srv/it/metadata.show?uuid=51400eea-245b-4ef2-8036-11201e9f1420
                return true;
            }
        );
		
		// TASTO CENTRA MAPPA AL DATO
        $("#dia_me_dettaglio_button_centra").button({
                text: true,
                disabled: true,
                icons: {primary: "ui-icon-bookmark"}
            })
            .click(function( event )
            {
                event.preventDefault();
				// TODO: centra mappa
                var id = jQuery("#"+tableCSW_ID).getGridParam('selrow');
                var rowData = jQuery("#"+tableCSW_ID).getRowData(id); 
                var bbox = rowData['bbox'];
                if (bbox=='') {
                    //TODO traduzione
                    custom_alert('Manca box', '', 'alert')
                    return false;
                }
                //dbg.log(bbox);
                bbox = OpenLayers.Bounds.fromString(bbox);
                
                
                
                bbox = convertExtent(bbox, new OpenLayers.Projection("EPSG:4326"), new OpenLayers.Projection(mappa.getProjection()));
                mq.center({box:bbox.toArray()})
                //dbg.log(bbox);
                return true;
            }
        );
		
		// TASTO AGGIUNGE DATO ALLA MAPPA
        $("#dia_me_dettaglio_button_mappa").button({
                text: true,
                disabled: true,
                icons: {primary: "ui-icon-tag"}
            })
            .click(function( event )
            {
                event.preventDefault();
				// TODO: aggiunge il livello
                var id = jQuery("#"+tableCSW_ID).getGridParam('selrow');
                var rowData = jQuery("#"+tableCSW_ID).getRowData(id); 
                var urlWms = rowData['uri'];
                var title = rowData['titolo'];
                //dbg.log(urlWms);
                if (urlWms=='') {
                    //TODO traduzione
                    custom_alert('Manca url WMS', '', 'alert')
                    return false;
                }
                addSourceAndShowLayers(urlWms,title);
                return true;
            }
        );		
/****************/

    },
    
    _destroy: function() {
        //this.element.removeClass(' ui-widget ui-helper-clearfix ui-corner-all').empty();
    },
	
    _popolaSelectCSW: function(){
        
        $('#dia_me_services').html("");
        // aggiungo le sorgenti predefinite alla combo
        for(var i in CSWSources)
        {
            $('#dia_me_services')
                .append($("<option></option>")
                .attr("value",i)
                .text(CSWSources[i].name));
        }
    }

});
})(jQuery);



// #######################################################################################################
// TODO: generalizzare in un file esterno come per WMS GetCapabilities


var cswFormat = new OpenLayers.Format.CSWGetRecords();
var startPosition = 1;
var maxRecords = 10;
//var text, title, lowerCorner, upperCorner, tempExtentBegin, tempExtentEnd;

//text = "laghi";
var title = "idrografia";
/*
lowerCorner = "-13 10"; 
upperCorner = "-10 13";

tempExtentBegin = "2013-03-14";
tempExtentEnd = "2013-03-16";
*/
function makeCswXmlRequest(text, title, lowerCorner, upperCorner, tempExtentBegin, tempExtentEnd)
{
	var cswXmlRequest = '<csw:GetRecords xmlns:csw="http://www.opengis.net/cat/csw/2.0.2" service="CSW" version="2.0.2" resultType="results" outputFormat="application/xml" outputSchema="http://www.opengis.net/cat/csw/2.0.2" \
		startPosition="'+startPosition+'" maxRecords="'+maxRecords+'"> \
		<csw:Query typeNames="csw:Record"> \
		<csw:ElementSetName>full</csw:ElementSetName>';
		
  if (text || title || lowerCorner || upperCorner || tempExtentBegin || tempExtentEnd) {
    cswXmlRequest += '<csw:Constraint version="1.1.0"> \
    <ogc:Filter xmlns:ogc="http://www.opengis.net/ogc"> \
    <ogc:And>';
  }
	/*---*/
	   if ((text) && (text!='')) {
		  cswXmlRequest += '<ogc:PropertyIsLike wildCard="*" singleChar="." escapeChar="!"> \
		   <ogc:PropertyName>apiso:AnyText</ogc:PropertyName> \
		   <ogc:Literal>'+text+'</ogc:Literal> \
		   </ogc:PropertyIsLike>';
	   }
	   if ((title) && (title!='')) {
		  cswXmlRequest += '<ogc:PropertyIsEqualTo matchCase="false"> \
		   <ogc:PropertyName>title</ogc:PropertyName> \
		   <ogc:Literal>'+title+'</ogc:Literal> \
		   </ogc:PropertyIsEqualTo>';
	   }
	   if ((lowerCorner) && (upperCorner) && (lowerCorner!='') && (upperCorner!='')) {
		  cswXmlRequest += '<ogc:BBOX> \
		   <ogc:PropertyName>ows:BoundingBox</ogc:PropertyName> \
		   <gml:Envelope xmlns:gml="http://www.opengis.net/gml"> \
		   <gml:lowerCorner>'+lowerCorner+'</gml:lowerCorner> \
		   <gml:upperCorner>'+upperCorner+'</gml:upperCorner> \
		   </gml:Envelope> \
		   </ogc:BBOX>';
	   }
	   if ((tempExtentBegin) && (tempExtentBegin!='')) {
		  cswXmlRequest += '<ogc:PropertyIsGreaterThanOrEqualTo> \
		   <ogc:PropertyName>tempExtentBegin</ogc:PropertyName> \
		   <ogc:Literal>'+tempExtentBegin+'T00:00:00Z</ogc:Literal> \
		   </ogc:PropertyIsGreaterThanOrEqualTo>';
	   }
	   if ((tempExtentEnd) && (tempExtentEnd!='')) {
		  cswXmlRequest += '<ogc:PropertyIsLessThanOrEqualTo> \
		   <ogc:PropertyName>tempExtentEnd</ogc:PropertyName> \
		   <ogc:Literal>'+tempExtentEnd+'T23:59:59Z</ogc:Literal> \
		   </ogc:PropertyIsLessThanOrEqualTo>';
	   }
	/*---*/
 if (text || title || lowerCorner || upperCorner || tempExtentBegin || tempExtentEnd) {
    cswXmlRequest += '</ogc:And> \
    </ogc:Filter> \
    </csw:Constraint>';
 }
 cswXmlRequest += '</csw:Query></csw:GetRecords>';
	
	return cswXmlRequest;
}


//var cswUrl = "http://www.ruparpiemonte.it/geocatalogorp/geonetworkrp/srv/it/csw"
myResponse="";
myDbg="";

function requestCswRecords(cswUrl,cswXmlRequest) {
			//OpenLayers.ProxyHost = "proxy.php?csurl=";
	
	// correzione baco: se non svuoto la tabella non viene eseguita la nuova chiamata
	jQuery("#"+tableCSW_ID).jqGrid("GridUnload");
	
    jQuery("#"+tableCSW_ID).jqGrid({
      
        datatype: function(postdata) {
          var self = this;
          
          $("#load_"+$.jgrid.jqID(self.p.id)).show();
          
          var cswStartPosition = self.p.page * self.p.rowNum - 9;
          cswXmlRequest = cswXmlRequest.replace(/startPosition="\d*/,'startPosition="'+cswStartPosition);

          var opts = {
            url : cswUrl,
            data : cswXmlRequest
          };
          OpenLayers.Util.applyDefaults(opts, {
          success : function(response) {
            
            $(self).empty();//clearGridData(false);
            
            myResponse = response.responseText;
            myCswObj = cswFormat.read(myResponse)
            myDbg = self;
            cswTotRecord = myCswObj.SearchResults.numberOfRecordsMatched;
            cswRecordsReturned = myCswObj.SearchResults.numberOfRecordsReturned
            cswNextRecord = myCswObj.SearchResults.nextRecord;
            
            self.p.lastpage = (cswTotRecord%maxRecords == 0) ? Math.floor(cswTotRecord/maxRecords) : Math.floor(cswTotRecord/maxRecords)+1;
            self.updatepager()
            
            addForeach(myCswObj.records);
            myCswObj.records.forEach(function(e,i,a){
            // TODO: verificare come passare un array singolo senza ciclare
              var wmsURI = '';
              // TODO: verificare il tipo degli URI e filtrare sui WMS
              for (var c in e.URI) {
                  if((e.URI[c].protocol)&&(e.URI[c].protocol.indexOf('OGC:WMS')!=-1)) wmsURI=e.URI[c].value;
              }
              jQuery("#"+tableCSW_ID).jqGrid('addRowData',i,[{
                  titolo:(e.title)? e.title[0].value : '',
                  soggetto:(e.subject)? joinObj(e.subject) : '',
                  creatore:(e.creator)? e.creator[0].value : '',
                  data:(e.date)? e.date[0].value : '',
                  uri: wmsURI,
                  abstract:(e.abstract)? e.abstract[0] : '',
                  bbox:(e.bounds)? e.bounds.toBBOX() : '',
                  identifier:(e.identifier)? e.identifier[0].value : ''
                  }]);
          });
          $("#load_"+$.jgrid.jqID(self.p.id)).hide();
          }
         
          });
          var request = OpenLayers.Request.POST(opts);
    
        },
    
    
    colNames:['Titolo','Soggetto','Creatore','Data ultima modifica','Url','Abstract','BBox','Identifier'],
    colModel:[
    {name:'titolo',index:'titolo'},
    {name:'soggetto',index:'soggetto'},
    {name:'creatore',index:'creatore',hidden:true},
    {name:'data',index:'data',hidden:true},
    {name:'uri',index:'uri',hidden:true},
    {name:'abstract',index:'abstract',hidden:true},
    {name:'bbox',index:'bbox',hidden:true},
    {name:'identifier',index:'identifier',hidden:true}
    ],
    pager: '#tableCSW_pager',
    rowNum: maxRecords,
    multiselect: false,
    autowidth:true,
    altRows: true,
    shrinkToFit: true,
    onSelectRow: function(id){
      if(id){
      var rowData = jQuery(this).getRowData(id); 
      
      var abstract = rowData['abstract'];
      //addWMS_ChooseLayer(urlWMS);
      //dbg.log(rowData['uri']);
      $('#dia_me_dettaglio_text').text(abstract)
      
      if (rowData['identifier']!='') {
      $('#dia_me_dettaglio_button_metadata').button( "enable" );
      } else {
      $('#dia_me_dettaglio_button_metadata').button( "disable" );
      };
      
      if (rowData['uri'] != '') {
      $('#dia_me_dettaglio_button_mappa').button( "enable" );
      } else {
      $('#dia_me_dettaglio_button_mappa').button( "disable" );
      };
      
      if (rowData['bbox'] !='') {
      $('#dia_me_dettaglio_button_centra').button( "enable" );    
      } else {
      $('#dia_me_dettaglio_button_centra').button( "disable" );
      };  
      
      }
    },
    /*onPaging: function (pgButton) {
        var pagerId = this.p.pager.substr(1); // ger paper id like "pager"
        var newValue = $('input.ui-pg-input', "#pg_" + $.jgrid.jqID(pagerId)).val();
            // newValue is in the most cases the same as in this.p.page
            // only wrong values like -10 entered by user will not update
            // "page" parameter
        //.getGridParam('page')
        alert(newValue+" - "+this.p.page);
        if (pgButton === "user" && newValue > 2) { // some tests
            //return "stop";
        }
    },*/
    caption: "CSW"
    });

}

function joinObj(arrObj){
   if ($.isArray(arrObj)) { 
         var arr=[];
         addForeach(arrObj);
         arrObj.forEach(function(e,i,a){arr.push(e.value);})
         return arr.join(",");
   }
   return false;
}
