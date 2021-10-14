
function init_edit(mappa)
{
    
    
    OpenLayers.Feature.Vector.style['default']['strokeWidth'] = '2';
    var editingVectorLayer = jQuery('#map').data("mapQuery").layers({
        type:'vector',
        label:'Editing Layer',
        styleMap: getSyles()     
    });
    
    var vlayer = editingVectorLayer.olLayer;
    
    /**
    * SEZIONE EDITING
    **/
    ctrl_EditingPoint = new OpenLayers.Control.DrawFeature(
      vlayer,
      OpenLayers.Handler.Point,
        {title: lng.get("TB_EDITPOINT_TITLE") //'Draw a point'
        , text: lng.get("TB_EDITPOINT") //'Point'
        , icon: 'ui-icon-bullet'}
    );
    
    //GLOBALE per la funzione di UNDO
    ctrl_EditingLine = new OpenLayers.Control.DrawFeature(
      vlayer,
      OpenLayers.Handler.Path,
      {title: lng.get("TB_EDITLINE_TITLE") //'Draw a line'
      , text: lng.get("TB_EDITLINE") //'Line'
      , icon: 'ui-icon-minusthick'}
    );
    ctrl_EditingPolygon = new OpenLayers.Control.DrawFeature(
      vlayer,
      OpenLayers.Handler.Polygon,
        {title: lng.get("TB_EDITPOLY_TITLE") //'Draw a Polygon'
        , text: lng.get("TB_EDITPOLY") //'Polygon'
        , icon: 'ui-icon-star'}
    );
    
    ctrl_EditingModify =  new OpenLayers.Control.ModifyFeature(vlayer,
      {title: lng.get("TB_EDITMODIFY_TITLE") //'Modify a feature'
      , text: lng.get("TB_EDITMODIFY") //'Modify'
      , icon: 'ui-icon-wrench'}
    );    
    ctrl_EditingModify.mode = OpenLayers.Control.ModifyFeature.RESHAPE;
    ctrl_EditingModify.mode |= OpenLayers.Control.ModifyFeature.ROTATE;
    /*ctrl_EditingModify.mode = OpenLayers.Control.ModifyFeature.RESIZE;
    ctrl_EditingModify.mode = OpenLayers.Control.ModifyFeature.DRAG;
    */
    
    ctrl_Snap = new OpenLayers.Control.Snapping({layer: editingVectorLayer.olLayer});
    // configure split agent
    ctrl_Split = new OpenLayers.Control.Split({
      layer: editingVectorLayer.olLayer,
      source: editingVectorLayer.olLayer,
      tolerance: 0.0001,
      eventListeners: {
        aftersplit: function(event) {
          flashFeatures(event.features,editingVectorLayer.olLayer);
        }
      }
    });    
            
    mappa.addControl(ctrl_Snap);
    ctrl_Snap.activate();
    mappa.addControl(ctrl_Split);
    ctrl_Split.activate();
    
    
    ctrl_RemoveAllFeatures = new OpenLayers.Control.Button({
      //displayClass: "removeAllFeatures,"
       trigger: removeAllFeatures //CONFIGURARE
      , title: lng.get("TB_EDITREMOVE_TITLE") //'Remove all Features'
      , text: lng.get("TB_EDITREMOVE") //'Remove'
      , icon: 'ui-icon-trash',
      CLASS_NAME: "OpenLayers.Control.CustomFunction"
    });
}
/**
 *
 * EDITNING
 *
 **/

function getEditingLayersId(){
    arrLayer = new Array()
    for(var x in mappa.layers){
        if(mappa.layers[x].CLASS_NAME=="OpenLayers.Layer.Vector"){
            arrLayer.push(mappa.layers[x].id);
        }
    }
    return arrLayer;
}

function removeAllFeatures(){
  editingLayersId = getEditingLayersId()
  for(var id in editingLayersId){
    //dbg.log("removeAllFeatures - "+ editingLayersId[id]);
    mappa.getLayersBy('id',editingLayersId[id])[0].removeAllFeatures()  
  }
}

function getSyles(){
var styles = new OpenLayers.StyleMap({
  "default": new OpenLayers.Style(null, {
      rules: [
          new OpenLayers.Rule({
              symbolizer: {
                  "Point": {
                      pointRadius: 5,
                      graphicName: "square",
                      fillColor: "white",
                      fillOpacity: 0.25,
                      strokeWidth: 1,
                      strokeOpacity: 1,
                      strokeColor: "#f00"
                  },
                  "Line": {
                      strokeWidth: 3,
                      strokeOpacity: 1,
                      strokeColor: "#666666"
                  }
              }
          })
      ]
  }),
  "select": new OpenLayers.Style({
      strokeColor: "#00ccff",
      strokeWidth: 4
  }),
  "temporary": new OpenLayers.Style(null, {
      rules: [
          new OpenLayers.Rule({
              symbolizer: {
                  "Point": {
                      pointRadius: 5,
                      graphicName: "square",
                      fillColor: "white",
                      fillOpacity: 0.25,
                      strokeWidth: 1,
                      strokeOpacity: 1,
                      strokeColor: "#333333"
                  },
                  "Line": {
                      strokeWidth: 3,
                      strokeOpacity: 1,
                      strokeColor: "#00ff00"
                  }
              }
          })
      ]
  })
});
return styles;
}

function flashFeatures(features, vectors, index) {
    if(!index) {
        index = 0;
    }
    var current = features[index];
    if(current && current.layer === vectors) {
        vectors.drawFeature(features[index], "select");
    }
    var prev = features[index-1];
    if(prev && prev.layer === vectors) {
        vectors.drawFeature(prev, "default");
    }
    ++index;
    if(index <= features.length) {
        window.setTimeout(function() {flashFeatures(features, vectors, index)}, 75);
    }
}
  
  
OpenLayers.Event.observe(document, "keydown", function(evt) {
  var handled = false;
  switch (evt.keyCode) {
      case 90: // z
          if (evt.metaKey || evt.ctrlKey) {
              //ctrl_EditingLine.undo();
              handled = true;
          }
          break;
      case 89: // y
          if (evt.metaKey || evt.ctrlKey) {
              //ctrl_EditingLine.redo();
              handled = true;
          }
          break;
      case 27: // esc
          //ctrl_EditingLine.cancel();
          handled = true;
          break;
  }
  if (handled) {
      OpenLayers.Event.stop(evt);
  }
});