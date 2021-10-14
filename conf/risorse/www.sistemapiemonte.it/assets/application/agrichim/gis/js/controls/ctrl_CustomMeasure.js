/*
* Esempio di controllo MISURA
*/
// style the sketch fancy
            var sketchSymbolizers = {
                "Point": {
                    pointRadius: 4,
                    graphicName: "square",
                    fillColor: "white",
                    fillOpacity: 1,
                    strokeWidth: 1,
                    strokeOpacity: 1,
                    strokeColor: "#555555"
                },
                "Line": {
                    strokeWidth: 3,
                    strokeOpacity: 1,
                    strokeColor: "#550000",
                    strokeDashstyle: "dash"
                },
                "Polygon": {
                    strokeWidth: 2,
                    strokeOpacity: 1,
                    strokeColor: "#cc0000",
                    fillColor: "white",
                    fillOpacity: 0.3
                }
            };
            var styleMisure = new OpenLayers.Style();
            styleMisure.addRules([
                new OpenLayers.Rule({symbolizer: sketchSymbolizers})
            ]);
            var misureStyleMap = new OpenLayers.StyleMap({"default": styleMisure});

$(document).ready(function() {

    $('body').append('<div id="dia_measure"><div id="dia_measure_text"></div></div>');
    
    $("#dia_measure").dialog(
    {
        resizable: false,
        height: 120,
        width: 300,
        autoOpen: false,
        title: lng.get("DIA_MEASURE"),
        
        buttons: {
            Ok: function() {
                $( this ).dialog( "close" );
            }
        }            
    });    
});

    

  function handleMeasurements(event) {
      var geometry = event.geometry;
      var units = event.units;
      var order = event.order;
      var measure = event.measure;
      var element = document.getElementById('dia_measure_text');
      var out = "";
      if(order == 1) {
          out += "" + measure.toFixed(2) + " " + units;
      } else {
          out += "" + measure.toFixed(2) + " " + units + "<sup>2</" + "sup>";
      }
      
      myDbg = measure;
      
      element.innerHTML = out;
      $("#dia_measure").dialog("open")
  }
var ctrl_MeasureLine = new OpenLayers.Control.Measure(OpenLayers.Handler.Path,
  {title: lng.get("TB_MEASURE_TITLE") //'Misura linee'
  , text: "" //lng.get("TB_MEASURE") //'Misura'
  , icon: 'ui-icon-minusthick'
  , persist: true
  , handlerOptions: {
        layerOptions: {
            //renderers: renderer,
            styleMap: misureStyleMap
        }
    }
});
ctrl_MeasureLine.setImmediate(true);

var ctrl_MeasurePolygon = new OpenLayers.Control.Measure(OpenLayers.Handler.Polygon,
  {title: lng.get("TB_MEASUREPOLY_TITLE") //'Misura poligoni'
  , text: "" //lng.get("TB_MEASUREPOLY") //'Misura'
  , icon: 'ui-icon-star'
  , persist: true
  , handlerOptions: {
        layerOptions: {
            //renderers: renderer,
            styleMap: misureStyleMap
        }
    }
});
ctrl_MeasurePolygon.setImmediate(true);

measureControls = [ctrl_MeasureLine,ctrl_MeasurePolygon]
  var mcontrol;
    for(var key in measureControls) {
        mcontrol = measureControls[key];
        mcontrol.events.on({
            "measure": handleMeasurements,
            "measurepartial": handleMeasurements
        });
    }
    
    
