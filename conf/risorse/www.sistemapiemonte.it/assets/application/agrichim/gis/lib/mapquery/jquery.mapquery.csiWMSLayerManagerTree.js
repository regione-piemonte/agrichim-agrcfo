
(function($) {


$.template('csiWMSLayerManagerTree',
    '<div class="mq-wmslayermanager">'+
    '</div>');
/*
$.template('csiWMSLayerManagerTreeElement',
    '<div class="mq-wmslayermanager-element ui-widget-content ui-corner-all" id="mq-wmslayermanager-element-${id}">'+
        '<div class="mq-wmslayermanager-element-content">'+
            '<div class="mq-wmslayermanager-element-header">'+
                '<span>'+
                    '<input type="checkbox" class="mq-wmslayermanager-element-vischeckbox" id="${id}-visibilitywms" {{if visible}}checked="${visible}"{{/if}} />'+
                    '<span class="labelwms" labelwms="${label}">${alias}</span>'+
                '</span>'+
            '</div>'+
        '</div>'+
    '</div>');
*/

$.widget("mapQuery.csiWMSLayerManagerTree", {
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
        
        var lmWMSElement = $.tmpl('csiWMSLayerManagerTree')
            .appendTo(element);
        this.lmWMSElement = lmWMSElement;
        
        var albero = [];
        var ia = 0;
        if(layer.olLayer.layers)
        {            
            var livWMS = layer.olLayer.layers.split(",");
            for(var i=0; i<livWMS.length; i++)
            {
                albero[ia] = {
                    title: livWMS[i],
                    icon: "ico_map.png",
                    select: true,
                    csi_id: layer.id+"-"+i,
                };
                
                ia++
                /*
                // stato del checkbox
                var layerElement = $.tmpl('csiWMSLayerManagerTreeElement',{
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
                */
            }
        }
        
        lmWMSElement.dynatree({
            
            debugLevel: 0,
            persist: false, // salva lo stato nei coockies
            checkbox: true,
            selectMode: 3, // 1:single, 2:multi, 3:multi-hier
            
            children: albero,
            
            onSelect: function(node) {
                    self._refreshLayers();
                },
                
            dnd: {
                preventVoidMoves: true,
                
                onDragStart: function(node) {
                    return true;
                },
                
                onDragEnter: function(node, sourceNode) {
                    if(node.parent !== sourceNode.parent){
                      return false;
                    }
                    return ["before", "after"];
                },
                
                onDrop: function(node, sourceNode, hitMode, ui, draggable) {
                    //console.log(node.data.myparam);
                    self._refreshLayers();
                    sourceNode.move(node, hitMode);
                },
            }
            
        });
        
        /*
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
        });*/
    },
    
    _refreshLayers: function() {
        
        var layerString = "";
        var a = this.lmWMSElement.dynatree("getSelectedNodes");
        
        for(var i=0; i<a.length; i++)
        {
            if(layerString!="") layerString += ","
            layerString += a[i].data.title;
        }
        
        /*
        $(a).each(function() {
            b = $(this) //.data.title)
        });
        */
        /*
        
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
        */
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
    
});
})(jQuery);