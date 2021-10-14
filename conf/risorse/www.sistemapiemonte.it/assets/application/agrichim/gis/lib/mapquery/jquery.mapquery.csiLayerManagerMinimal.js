/* Copyright (c) 2011 by MapQuery Contributors (see AUTHORS for
 * full list of contributors). Published under the MIT license.
 * See https://github.com/mapquery/mapquery/blob/master/LICENSE for the
 * full text of the license. */

/**
#jquery.mapquery.csiLayerManagerMinimal.js
The file containing the csiLayerManagerMinimal Widget

### *$('selector')*.`csiLayerManagerMinimal([options])`
_version added 0.1_
####**Description**: create a widget to manage layers

 + **options**:
  - **map**: the mapquery instance
  - **title**: Title that will be displayed at the top of the 
  layer manager (default: Layer Manager)


>Returns: widget

>Requires: jquery.mapquery.legend.js


The csiLayerManagerMinimal allows us to control the order, opacity and visibility
of layers. We can also remove layers. It also shows the legend of the layer if
available and the error messages provided by the legend plugin. It listens to
layerchange event for order, transparancy and opacity changes. It listens to
addlayer and removelayer events to keep track which layers are on the map.


      $('#layermanager').csiLayerManagerMinimal({map:'#map'});


 */


(function($) {
$.template('csiLayerManagerMinimal',
    '<div class="mq-layermanager ui-widget-content  ">'+
    '</div>');

$.template('csiLayerManagerMinimalElement',
    '<div class="mq-layermanager-element ui-widget-content ui-corner-all" id="mq-layermanager-element-${id}">'+
    //'<div class="mq-layermanager-element-header ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix">'+
    '<div class="mq-layermanager-element-header ui-corner-all ui-helper-clearfix">'+

    '<table style="width:100%; border:none;">'+
    
    '<tr style="border:none;">'+
        '<td rowspan="2" style="width:30px; border:none;">'+
            '<div id="${id}-visibility_div" class="toc_switch{{if visible}}on{{/if}} toc_switch_class" />'+
        '</td>'+
        '<td style="border:none;">'+
            '<span class="mq-layermanager-label">'+ // ui-dialog-title">'+
            '${label}'+
            '</span>'+
            
        '</td>'+
    '</tr>'+
    
    '<tr style="border:none;">'+

        '<td style="border:none;">'+
        
            '<div class="slidertoc mq-layermanager-element-visibility">'+
                '<div class="mq-layermanager-element-slider-container">'+
                    '<div class="mq-layermanager-element-slider">'
                    +'</div>'
                +'</div>'+
            '</div>'+        
        '</td>'+
    '</tr>'+
    
    '</table>'+
    
    '<input type="checkbox" class="mq-layermanager-element-vischeckbox" id="${id}-visibility" {{if visible}}checked="${visible}"{{/if}}'+
    ' style="display:none" '+
    '/>'+

    //'{{if visible}}<img src="css/img/switch_toc.png"id="${id}-visibility"  />{{/if}}'+
    //'{{if !visible}}<img src="css/img/switch_toc_off.png"id="${id}-visibility"  />{{/if}}'+

    
    //'<a class="ui-dialog-titlebar-close ui-corner-all" href="#" role="button">'+
    //'<span class="ui-icon ui-icon-closethick">close</span></a>'+
    '</div>'+
    
    /*
    '<div class="mq-layermanager-element-content">'+
    */

    
    /*
        '<div class="mq-layermanager-element-legend">'+
            '{{if imgUrl}}'+
                '<img src="${imgUrl}" style="opacity:${opacity}"/>'+
            '{{/if}}'+
            '{{if errMsg}}'+
                '${errMsg}'+
            '{{/if}}'+
        '</div>'+
        
    '</div>'+
    */
    '</div>');

$.widget("mapQuery.csiLayerManagerMinimal", {
    options: {
        // The MapQuery instance
        map: undefined,
        
        // Title that will be displayed at the top of the popup
        title: "Layer Manager",
        
        // per differenziare le toc
        wID: 0
    },
    _create: function() {
        
        this.options.wID = Math.floor((1 + Math.random()) * 0x10000);
        
        var map;
        var zoom;
        var numzoomlevels;
        var self = this;
        var element = this.element;
        
        //get the mapquery object
        map = $(this.options.map).data('mapQuery');
        
        this.element.addClass('ui-widget  ui-helper-clearfix ' +
                              'ui-corner-all');
        
        var lmElement = $.tmpl('csiLayerManagerMinimal').appendTo(element);
        //element.find('.ui-icon-closethick').button();
        
        lmElement.sortable({
            axis:'y',
            handle: '.mq-layermanager-element-header',
            update: function(event, ui) {
                var layerNodes = ui.item.siblings().andSelf();
                var num = layerNodes.length-1;
                layerNodes.each(function(i) {
                    var layer = $(this).data('layer');
                    var pos = num-i;
                    self._position(layer, pos);
                });
            }
        });

    //these layers are already added to the map as such won't trigger 
    //and event, we call the draw function directly
        $.each(map.layers().reverse(), function(){
           self._layerAdded(lmElement, this);
        });

        element.delegate('.mq-layermanager-element-vischeckbox',
            'change',function() {
            var checkbox = $(this);
            var element = checkbox.parents('.mq-layermanager-element');
            var layer = element.data('layer');
            var self = element.data('self');
            self._visible(layer,checkbox.is(':checked'));
         });
        
        element.delegate('.toc_switch_class',
            'click',function() {
                
            
            var checkbox = $("#"+this.id.split("_div")[0]);
            checkbox.toggleCheck();
            
            var element = checkbox.parents('.mq-layermanager-element');
            var layer = element.data('layer');
            var self = element.data('self');
            self._visible(layer,checkbox.is(':checked'));
         });

        element.delegate('.ui-icon-closethick', 'click', function() {
            var control = $(this).parents('.mq-layermanager-element');
            self._remove(control.data('layer'));
        });

        //binding events
        map.bind("addlayer",
            {widget:self,control:lmElement},
            self._onLayerAdd);

        map.bind("removelayer",
            {widget:self,control:lmElement},
            self._onLayerRemove);

        map.bind("changelayer",
            {widget:self,map:map,control:lmElement},
            self._onLayerChange);

        map.bind("moveend",
            {widget:self,map:map,control:lmElement},
            self._onMoveEnd);
    },
    _destroy: function() {
        this.element.removeClass(' ui-widget ui-helper-clearfix ' +
                                 'ui-corner-all')
            .empty();
    },
    //functions that actually change things on the map
    //call these from within the widget to do stuff on the map
    //their actions will trigger events on the map and in return
    //will trigger the _layer* functions
    _add: function(map,layer) {
        map.layers(layer);
    },

    _remove: function(layer) {
        layer.remove();
    },

    _position: function(layer, pos) {
        layer.position(pos);
    },

    _visible: function(layer, vis) {
        layer.visible(vis);
    },

    _opacity: function(layer,opac) {
        layer.opacity(opac);
    },

    //functions that change the widget
    _layerAdded: function(widget, layer) {
        
        var wid = this.options.wID;
        
        //dbg.log(wid)
        
        var self = this;
        var error = layer.legend().msg;
        var url;
        switch(error){
            case '':
                url =layer.legend().url;
                if(url==''){error='No legend for this layer';}
                break;
            case 'E_ZOOMOUT':
                error = 'Please zoom out to see this layer';
                break;
            case 'E_ZOOMIN':
                error = 'Please zoom in to see this layer';
                break;
            case 'E_OUTSIDEBOX':
                error = 'This layer is outside the current view';
                break;
        }

        var layerElement = $.tmpl('csiLayerManagerMinimalElement',{
            id: wid+"_"+layer.id,
            label: layer.label,
            position: layer.position(),
            visible: layer.visible(),
            imgUrl: url,
            opacity: layer.visible()?layer.opacity():0,
            errMsg: error
        })
            // save layer layer in the DOM, so we can easily
            // hide/show/delete the layer with live events
            .data('layer', layer)
            .data('self',self)
            .prependTo(widget);
          
       $(".mq-layermanager-element-slider", layerElement).slider({
           max: 100,
           step: 1,
           value: layer.visible()?layer.options.opacity*100:0,
           slide: function(event, ui) {
               var layer = layerElement.data('layer');
               var self =  layerElement.data('self');
               self._opacity(layer,ui.value/100);
           },
           //using the slide event to check for the checkbox often gives errors.
           change: function(event, ui) {
               var layer = layerElement.data('layer');
               var self =  layerElement.data('self');
               if(ui.value>=0.01) {
                   if(!layer.visible()){layer.visible(true);}
               }
               if(ui.value<0.01) {
                   if(layer.visible()){layer.visible(false);}
               }
           }
       });
       
       
    },

    _layerRemoved: function(widget, id) {
        var control = $("#mq-layermanager-element-"+id);
        control.fadeOut(function() {
            $(this).remove();
        });
    },

    _layerPosition: function(widget, layer) {
        var layerNodes = widget.element.find('.mq-layermanager-element');
        var num = layerNodes.length-1;
        var tmpNodes = [];
        tmpNodes.length = layerNodes.length;
        layerNodes.each(function() {
            var layer = $(this).data('layer');
            var pos = num-layer.position();
            tmpNodes[pos]= this;
        });
        for (i=0;i<tmpNodes.length;i++) {
            layerNodes.parent().append(tmpNodes[i]);
        }
    },

    _layerVisible: function(widget, layer) {
        var wid = this.options.wID;
        
        var layerElement = widget.element.find('#mq-layermanager-element-'+wid+"_"+layer.id);
        var checkbox = layerElement.find('.mq-layermanager-element-vischeckbox');
        checkbox[0].checked = layer.visible();        
        
        // spegne gli altri TMS e riproietta la mappa
        // TODO: nel caso di mappe multiple crea ancora dei problemi di interferenza tra le toc
        //if (layer.visible() &&
        //    (layer.options.type == "csi_tms"
        //    || layer.options.type == "tms"
        //    || layer.options.type == "google"
        //    || layer.options.type == "osm")
        //){
        //    cambiaProiezione(layer.olLayer);
        //    
        //    var lay = mq.layers()
        //            //debugga = lay
        //    for (var i=0; i<lay.length; i++) {
        //        if ((lay[i].options.type == "csi_tms" ||
        //             lay[i].options.type == "tms" ||
        //             lay[i].options.type == "google" ||
        //             lay[i].options.type == "osm") &&
        //            (lay[i].id != layer.id))
        //        {
        //            dbg.log('#mq-layermanager-element-'+wid+"_"+lay[i].id)
        //            var layerElement2 = widget.element.find('#mq-layermanager-element-'+wid+"_"+lay[i].id);
        //            var checkbox2 = layerElement2.find('.mq-layermanager-element-vischeckbox');
        //            checkbox2[0].checked = false;
        //            
        //            this._visible(lay[i],false);
        //        }
        //        else{
        //            // corregge la proiezione di tutti i rimanenti livelli
        //            //debugga = lay[i];
        //            //dbg.log(lay[i].olLayer.projection +" "+ lay[i].olLayer.name)
        //            //lay[i].olLayer.projection = layer.olLayer.projection;
        //        }
        //    }
        //}
        //
        //dbg.log("ON OFF");
        
        var divIMGcheck = widget.element.find('#'+wid+"_"+layer.id+"-visibility_div");
        if (layer.visible()) 
            divIMGcheck["0"].setAttribute("class","toc_switchon toc_switch_class")
        else
            divIMGcheck["0"].setAttribute("class","toc_switch toc_switch_class")
        
        /*
        //update the opacity slider as well
        var slider = layerElement.find('.mq-layermanager-element-slider');
        var value = layer.visible()?layer.opacity()*100: 0;
        slider.slider('value',value);

        //update legend image
        layerElement.find('.mq-layermanager-element-legend img').css(
            {visibility:layer.visible()?'visible':'hidden'});
            */
    },

    _layerOpacity: function(widget, layer) {
        /*
        var layerElement = widget.element.find(
            '#mq-layermanager-element-'+layer.id);
        var slider = layerElement.find(
            '.mq-layermanager-element-slider');
        slider.slider('value',layer.opacity()*100);
        //update legend image
        layerElement.find(
            '.mq-layermanager-element-legend img').css(
            {opacity:layer.opacity(),visibility:layer.visible()?'visible':'hidden'});
            */
    },

    _moveEnd: function (widget,lmElement,map) {
        return true;
        /*console.log(mq.layers().length + " / "+ lmElement.length);
        console.log(lmElement);*/
        
        lmElement.empty();
        
        $.each(map.layers().reverse(), function(){
           widget._layerAdded(lmElement, this);
        });
    },

    //functions bind to the map events
    _onLayerAdd: function(evt, layer) {
        evt.data.widget._layerAdded(evt.data.control,layer);
    },

    _onLayerRemove: function(evt, layer) {
        evt.data.widget._layerRemoved(evt.data.control,layer.id);
    },

    _onLayerChange: function(evt, layer, property) {
        
         switch(property) {
            case 'opacity':
                evt.data.widget._layerOpacity(evt.data.widget,layer);
            break;
            case 'position':
                evt.data.widget._layerPosition(evt.data.widget,layer);
            break;
            case 'visibility':
                evt.data.widget._layerVisible(evt.data.widget,layer);
            break;
        }
    },
    _onMoveEnd: function(evt) {
        evt.data.widget._moveEnd(evt.data.widget,evt.data.control,evt.data.map);
    }
});
})(jQuery);



/*function cambiaProiezione(livelloBase)
{
   var mapProj, baseProj, map, newBase, reproject;
   
   map = mq.olMap;
   newBase = livelloBase;
   mapProj = (map.projection && map.projection instanceof OpenLayers.Projection) ? map.projection : new OpenLayers.Projection(map.projection);
   baseProj = newBase.projection;
   reproject = !(baseProj.equals(mapProj));
   if (reproject) {
    
      var center, maxExt;
      //calc proper reporojected center
      center = map.getCenter().transform(mapProj, baseProj);
      var extentAttuale = mq.olMap.getExtent();
      //calc correct reprojected extents
      maxExt = newBase.maxExtent;
      //set map projection, extent, & center of map to proper values
      
      //dbg.log(maxExt);
      
    map.baseLayer.projection = baseProj;
    map.displayProjection = new OpenLayers.Projection("EPSG:32632"); //baseProj
    
      map.projection = baseProj;
      map.maxExtent = maxExt;
      //map.setCenter(extentAttuale);
      map.setCenter(center);
      
   }
   
   
}*/

function cambiaProiezione(layerName) {

       baseLayerProjection = null;
       newLayerProjection = null;
	  var map = mq.olMap;
	  
      //mi cerco la proiezione del layer valorizzo correttamente la 2 var sopra
      //cerco il layer dagli array di base layer
      
      var actualLayer = layerName;
      
      //error message
      if(actualLayer == null){
        alert('Non trovo il layer '+layerName+' questo causa possibili malfunzionamenti');
      }
      
      baseLayerProjection = (map.projection && map.projection instanceof OpenLayers.Projection) ? map.projection : new OpenLayers.Projection(map.projection);
	  
      newLayerProjection = actualLayer.projection
	  
      //error message
      if(newLayerProjection == null){
        alert('Impossibile determinare la nuova proiezione, questo causamalfunzionamenti');
      }
      if(baseLayerProjection == null){
        alert('Impossibile determinare la proiezione della mappa attuale, questo causamalfunzionamenti');
      }
 
      var currentExtent = map.getExtent();
      map.baseLayer.setVisibility(false);
      actualLayer.setVisibility(true);
      map.setBaseLayer(actualLayer);
      //viewport.mappe[idMap].setBaseLayer(actualLayer);
      
      
      if (newLayerProjection != baseLayerProjection) {
        var newExtent = map.convertExtentProj(currentExtent, baseLayerProjection, newLayerProjection);
        
        /*if (newLayerProjection==map.projectionWGS84UTM && newLayerProjection.projection.proj==null) {
          newLayerProjection.projection.proj = map.projectionWGS84UTMproj;
        }*/
        map.setOptions({'projection': newLayerProjection});
        
        actualLayer.addOptions({'projection': newLayerProjection});
        
        map.zoomToExtent(newExtent);   
        map.currentProjection = newLayerProjection;

        for(var i=0; i<map.layers.length; i++) {
            if (map.layers[i].visibility) {
              if (map.layers[i].CLASS_NAME=="OpenLayers.Layer.GML") { 
                if (map.layers[i].loaded && map.layers[i].visibility) {
                  map.layers[i].refresh({force: true}); 
                }
              } else if (map.layers[i].CLASS_NAME=="OpenLayers.Layer.Vector") { 
                  map.layers[i].refresh({force: true}); 
              } else {
                //map.layers[i].addOptions({'projection': newLayerProjection.projection});
              }
            }
        }
      }
      

  };
  
  
  
  OpenLayers.Map.prototype.convertExtentProj = function(extent, sourceProjection, destProjection) {
        var lowerLeft = new Proj4js.Point(extent.left, extent.bottom);
        var upperRight = new Proj4js.Point(extent.right, extent.top);
        var source = new Proj4js.Proj(sourceProjection.projCode);
        var dest = new Proj4js.Proj(destProjection.projCode);
        Proj4js.transform(source, dest, lowerLeft);
        Proj4js.transform(source, dest, upperRight);
        return new OpenLayers.Bounds(lowerLeft.x, lowerLeft.y, upperRight.x, upperRight.y);
  };
  
  
  $.fn.toggleCheck=function(){
       //if(this.tagName==='INPUT'){
           $(this).prop('checked', !($(this).is(':checked')));
       //}

   }
