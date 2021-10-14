/*
 * identify su tutti i livelli accesi
 * mostra il risultato in un popup
*/



OpenLayers.Control.Click   = OpenLayers.Class(OpenLayers.Control, {                
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
                    
                    var lonlat = mq.olMap.getLonLatFromPixel(e.xy);
                    var y = lonlat.lat;
                    var x = lonlat.lon;
                    
                    var txtInfo = "";
                    
                    for(var i=0; i<listaRicerche.length; i++)
                    {
                        var box = listaRicerche[i].value.split(",");
                        var ax = box[0];
                        var ay = box[1];
                        var bx = box[2];
                        var by = box[3];
                        if ( (ax < x && x < bx) && (ay < y && y < by) )
                        {
                            if (txtInfo != "") txtInfo += ", ";
                            txtInfo += listaRicerche[i].label;
                        }
                    }
                    if (txtInfo == "") txtInfo = "Nessun dato trovato";
                    alert(txtInfo);
                }

});

var ctrl_infoRicerca = new OpenLayers.Control.Click({
            title:'Custom Button'
            //, text: 'Button (shift)'
            //, icon: 'ui-icon-extlink'
            ,CLASS_NAME: "OpenLayers.Control.Click " // OBBLIGATORIO
            
            , text: "" //lng.get("TB_MEASURE") //'Misura'
            , icon: 'ui-icon-minusthick'
            , persist: false
          
});