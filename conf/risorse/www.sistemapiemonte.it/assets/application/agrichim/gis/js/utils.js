
// attiva il controllo
function setActiveTool(ctrl_name)
{
    activateControl(ctrl_name);
}


function checkUrl(url){
    return url.match(/(http|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&amp;:/~\+#]*[\w\-\@?^=%&amp;/~\+#])?/);

    //return url.match(/([a-z0-9_\-]{1,5}:\/\/)?(([a-z0-9_\-]{1,}):([a-z0-9_\-]{1,})\@)?((www\.)|([a-z0-9_\-]{1,}\.)+)?([a-z0-9_\-]{3,})(\.[a-z]{2,4})(\/([a-z0-9_\-]{1,}\/)+)?([a-z0-9_\-]{1,})?(\.[a-z]{2,})?(\?)?(((\&)?[a-z0-9_\-]{1,}(\=[a-z0-9_\-]{1,})?)+)?/);
                    //  (((f|ht){1}tp://)[-a-zA-Z0-9@:%_\+.~#?&//=]+)
}

// restituisce il nome del controllo attivo
function getActiveTool()
{
    for(key in mapControls)
    {
        if(mapControls[key].active === true)
            return key;
    }
    return null;
}


function addWMS_Layer(url){
    
    //http://sdi.provincia.bz.it/geoserver/PAB_WMS03_Trasporti/wms?service=WMS&layer=PAB_WMS03_Trasporti:Intersezione ferroviaria
    
    //dbg.log(url);
    url = url.split("?")
    var urlWMS = url[0];
    var layWMS = (url[1].split("&")[1]).split("=")[1];
    
    //dbg.log(urlWMS);
    //dbg.log(layWMS);
    
    retrieveWMSparamsAndAddToMap('#map', {
        type:'wms',
        label:layWMS,
        url:urlWMS,
        layers: layWMS
    })
    /*
    jQuery('#map').data("mapQuery").layers({
        type:'wms',
        label:layWMS,
        url:urlWMS,
        layers: layWMS,
    });
    */
}


function addWMS_ChooseLayer(url){
    
    //dbg.log(url);
    
    // TODO: pensare qualcosa di più furbo per creare il nome
    var nome = url.split("/");
    nome = nome[nome.length-1];
    
    //$('#layermanagergn').csiLayerManagerSimpleTree({map:'#map'});
    
    $("#dia_services").dialog("open");
    WMSSources[WMSSources.length] = {
        name: nome,
        url: url                            
    };
    $('#layermanagergn').csiLayerManagerSimpleTree("popolaSelectWMSUltimaSelezione");
    $('#layermanagergn').csiLayerManagerSimpleTree("onChangeWMSList");
    
    //layerManager._popolaSelectWMS();		
    //layerManager._onChangeWMSList();
}

// visualizza una finestra modale per i messaggi di sistema (alert)
function custom_alert(output_msg, title_msg, type_msg)
{
    if (!title_msg)
        title_msg = 'Alert';
        
    var icona = "msg";
    switch (type_msg) {
        case "error":
            icona = "error";
        break;
        case "alert":
            icona = "alert";
        break;
    }
    //if (!output_msg)
    //    htmlMSG = 'No Message to Display.';

    var htmlMSG = '<table id="alert_custom_table">'
            +'<tr>'
                +'<td>'
                    +'<img src="/assets/application/agrichim/gis/img/ico_'+icona+'.png" />'
                +'</td>'
                +'<td>'
                    +output_msg;
                +'</td>'
            +'<tr>'
        +'</table>';
    
    $("<div></div>").html(htmlMSG).dialog({
        title: title_msg,
        resizable: false,
        modal: true,
        buttons: {
            "Ok": function() 
            {
                $( this ).dialog( "close" );
            }
        }
    });
}
convertExtent = function(extent, sourceProjection, destProjection) {
      var lowerLeft = new OpenLayers.LonLat(extent.left, extent.bottom).transform(sourceProjection, destProjection);
      var upperRight = new OpenLayers.LonLat(extent.right, extent.top).transform(sourceProjection, destProjection);
      return new OpenLayers.Bounds(lowerLeft.lon, lowerLeft.lat, upperRight.lon, upperRight.lat);
};
