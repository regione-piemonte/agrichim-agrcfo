
(function($) {
    
$.template('csiWMSLayerManager',
    '<div class="mq-wmslayermanager">'+
    '</div>');

$.template('csiWMSLayerManagerElement',
    '<div class="mq-wmslayermanager-element ui-widget-content ui-corner-all" id="mq-wmslayermanager-element-${id}">'+
    /*
        '<div class="mq-wmslayermanager-element-header ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix">'+
            '<span class="mq-layermanager-label ui-dialog-title">${label}</span>'+
                '<a class="ui-dialog-titlebar-close ui-corner-all" href="#" role="button">'+
            '<span class="ui-icon ui-icon-closethick">close</span></a>'+
        '</div>'+
    */  
        '<div class="mq-wmslayermanager-element-content">'+
            '<div class="mq-wmslayermanager-element-header">'+
                '<span>'+
                    '<input type="checkbox" class="mq-wmslayermanager-element-vischeckbox" id="${id}-visibilitywms" {{if visible}}checked="${visible}"{{/if}} />'+
                    '<span class="labelwms" labelwms="${label}">${alias}</span>'+
                '</span>'+
            '</div>'+
        '</div>'+
    '</div>');

$.widget("mapQuery.csiWMSLayerManager", {
    options: {
        layer: undefined
    },
    
    _create: function() {
        //var map;
        //var zoom;
        //var numzoomlevels;
        var self = this;
        var element = this.element;
        var layer = this.options.layer;
        this.layer = layer;        
        
        var lmWMSElement = $.tmpl('csiWMSLayerManager')
            .appendTo(element);
        this.lmWMSElement = lmWMSElement;
        
        if(layer.olLayer.layers)
        {            
            var livWMS = layer.olLayer.layers.split(",");
            for(var i=0; i<livWMS.length; i++)
            {
                // stato del checkbox
                //var isChecked = $("#"+layer.id+"-"+i+"-visibilitywms").is(':checked');
                //dbg.log(isChecked);
                
                var layerElement = $.tmpl('csiWMSLayerManagerElement',{
                    id: layer.id+"-"+i,
                    label: livWMS[i],
                    alias: livWMS[i],
                    visible: true,
                })
                    // save layer layer in the DOM, so we can easily
                    // hide/show/delete the layer with live events
                    .data('wmslayer', livWMS[i])
                    .data('id', layer.id+"-"+i)
                    .appendTo(lmWMSElement);
                    
                // associa al click sul chekbox l'update del sortable per gestire l'on/off dei livelli
                $("#"+layer.id+"-"+i+"-visibilitywms").change(function () {
                    
                    self._refreshLayers();
                    
                });
                
            }
        }
        
        lmWMSElement.sortable({
            axis:'y',
            placeholder: "ui-state-highlight",
            handle: '.mq-wmslayermanager-element-header',
            update: function(event, ui) {
                
                var layerNodes = ui.item.siblings().andSelf();
                var num = layerNodes.length-1;
                var layerString = "";
                layerNodes.each(function(i) {
                    var layer = $(this).data('wmslayer');
                    var pos = num-i;
                    
                    var isChecked = $("#"+$(this).data('id')+"-visibilitywms").is(':checked');
                    if(isChecked)
                    {
                        if(layerString!="") layerString += ","
                        layerString += layer;
                    }
                });
                self._position(layerString);
                
            }
        });
    },
    
    _refreshLayers: function() {
        
        var layerString = "";
        
        var a = this.lmWMSElement.sortable("toArray");
        var layerNodes = this.lmWMSElement.sortable("option")//.item.siblings().andSelf()
        
        for(var i=0; i<a.length; i++)
        {
            var isChecked = $("#"+a[i]).find(".mq-wmslayermanager-element-vischeckbox").is(':checked');
            var layername = $("#"+a[i]).find(".labelwms").attr("labelwms");
            if(isChecked)
            {
                if(layerString!="") layerString += ","
                layerString += layername;
            }
        };
        this._position(layerString);
    },
    
    _destroy: function() {
        this.element.removeClass(' ui-widget ui-helper-clearfix ui-corner-all').empty();
    },
    
    _position: function(layerString) {
        for(var i=0; i<this.layer.map.olMap.layers.length; i++)
        {
            if(this.layer.label == this.layer.map.olMap.layers[i].name)
            {
                this.layer.olLayer.layers = layerString;
                this.layer.map.olMap.layers[i].params.LAYERS = layerString;
                this.layer.map.olMap.layers[i].redraw();
            }
        }
    }
    
    /*
    _visible: function(layer, vis) {
        layer.visible(vis);
    },

    _opacity: function(layer,opac) {
        layer.opacity(opac);
    },

    //functions that change the widget
    _layerAdded: function(widget, layer) {
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

        var layerElement = $.tmpl('csiWMSLayerManagerElement',{
            id: layer.id,
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
           value: layer.visible()?layer.opacity()*100:0,
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
        var layerElement =
        widget.element.find('#mq-layermanager-element-'+layer.id);
        var checkbox =
        layerElement.find('.mq-layermanager-element-vischeckbox');
        checkbox[0].checked = layer.visible();
        //update the opacity slider as well
        var slider = layerElement.find('.mq-layermanager-element-slider');
        var value = layer.visible()?layer.opacity()*100: 0;
        slider.slider('value',value);

        //update legend image
        layerElement.find('.mq-layermanager-element-legend img').css(
            {visibility:layer.visible()?'visible':'hidden'});
    },

    _layerOpacity: function(widget, layer) {
        var layerElement = widget.element.find(
            '#mq-layermanager-element-'+layer.id);
        var slider = layerElement.find(
            '.mq-layermanager-element-slider');
        slider.slider('value',layer.opacity()*100);
        //update legend image
        layerElement.find(
            '.mq-layermanager-element-legend img').css(
            {opacity:layer.opacity(),visibility:layer.visible()?'visible':'hidden'});
    },

    _moveEnd: function (widget,lmElement,map) {
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
  */
});
})(jQuery);