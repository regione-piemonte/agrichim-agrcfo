// libreria per la gestione delle chiamate a WMS
// per ricavare i parametri del servizio

var wmsUtils = {
    
    format: new OpenLayers.Format.WMSCapabilities()
    
    ,getLayersFromWMS: function (url, trigger){
        
        var format = this.format;
        
        OpenLayers.Request.GET({
            url: url,
            params: {
                SERVICE: "WMS",
                //VERSION: "1.1.1",
                REQUEST: "GetCapabilities"
            },
            success: function(request) {
                var doc = request.responseXML;
                if (!doc || !doc.documentElement) {
                    doc = request.responseText;
                }
                var capabilities = format.read(doc);
                //debuga = capabilities;
                trigger(capabilities.capability.layers);
            },
            failure: function() {
                // TODO: gestire errore chiamata
            }
        });
        return null;
    }


    ,getCapabilitiesFromWMS: function (url, trigger, widget){
        
        var format = this.format;
        
        OpenLayers.Request.GET({
            url: url,
            params: {
                SERVICE: "WMS",
                //VERSION: "1.1.1",
                REQUEST: "GetCapabilities"
            },
            success: function(request) {
                var doc = request.responseXML;
                if (!doc || !doc.documentElement) {
                    doc = request.responseText;
                }
                var capabilities = format.read(doc);
                //debuga = capabilities;
                trigger(url, capabilities, widget);
            },
            failure: function() {
                // TODO: gestire errore chiamata
                trigger(url, null, widget);
            }
        });
        return null;
    }

};
