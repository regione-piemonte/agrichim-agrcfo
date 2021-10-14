/* Copyright (c) 2011 by MapQuery Contributors (see AUTHORS for
 * full list of contributors). Published under the MIT license.
 * See https://github.com/mapquery/mapquery/blob/master/LICENSE for the
 * full text of the license. */


/**
#jquery.mapquery.mqOverviewMap.js
The file containing the mqOverviewMap Widget

### *$('selector')*.`mqOverviewMap([options])`
_version added 0.1_
####**Description**: create a widget to show an overview map

 + **options**:
  - **map**: the mapquery instance
  - **title**: Title that will be displayed at the top of the overview window
  - **position**: The position of the overview map dialog (default right bottom)
  - **width**: width of the overview window (default 300px)
  - **height**: height of the overview window (default 200px)

>Returns: widget


The mqOverviewMap widget allows us to display an overview map dialog
(http://jqueryui.com/demos/dialog/) and a toggle button. The dialog will be put
on the given position see (http://jqueryui.com/demos/dialog/#option-position).
The toggle button will be put in the element where the widget is attached to.


     $('#overviewmap').mqOverviewMap({
        map: '#map',
        position: ['right','top']
     });

 */
(function($) {
$.template('mqOverviewMap',
    '<div class="mq-overviewmap-button ui-state-default ui-corner-all">'+
    '<div class="mq-overviewmap-close ui-icon ui-icon-arrowthick-1-se "></div>'+
    '</div>'+
    '<div id="${id}" class="mq-overviewmap ui-widget ">'+
    '</div>');

$.widget("mapQuery.mqOverviewMap", {
    options: {
        // The MapQuery instance
        map: undefined,

        // Title that will be displayed at the top of the overview window
        title: "Overview map",

        //the position of the overview map, default right bottom of the window
        position: ['right','bottom'],

        //initial size of the overviewmap
        width: 150,
        height: 150,
        windowed: true,
        layers: [],
        maximized: false
    },
    _create: function() {
        var map;
        var self = this;
        var element = this.element;
        
        //get the mapquery object
        map = $(this.options.map).data('mapQuery');
        //var OLlays = [map.layers().reverse()[2].olLayer.clone()];
        // ---------------------------------------------------------------------
        // creazione dei livelli
        var OLlays = [];
        
        if(this.options.layers.length == 0){
            dbg.log("No layers defined for overview");
            return true
        };
        
        for (var i=0; i<this.options.layers.length; i++) {
            var newLayOptions = getLayerOptionsFromJson(this.options.layers[i], null)
            
            // aggiunge il layer alla mappa per clonarlo nella TOC
			if (newLayOptions!= null) jQuery('#map').data("mapQuery").layers(newLayOptions);
            OLlays.push(map.layers().reverse()[map.layers().length-1].olLayer.clone());
            map.layers().reverse()[map.layers().length-1].remove();
            
            // lo elimina dalla mappa principale
            //TOC.data("mapQueryCsiLayerManagerSimpleTree")._remove(map.layers().reverse()[map.layers().length-1]);
        }
        // ---------------------------------------------------------------------
        
        this.element.addClass('ui-widget  ui-helper-clearfix ui-corner-all');
        
        var id = "";
        
        if (this.options.windowed)
        {
            id = 'mqOverviewMap-dialog';
            
            $.tmpl('mqOverviewMap',{id: id}).appendTo(element);
            
            var dialogElement = $('#'+id).dialog({
                dialogClass: 'mq-overviewmap-dialog',
                autoOpen: this.options.maximized,
                width: this.options.width,
                height: this.options.height,
                title: this.options.title,
                position: this.options.position,
                resizeStop: function (event, ui) {
                    $('.olMap', this).width($(this).width());
                   $('.olMap', this).height($(this).height());
                },
                close:function(event,ui){
                     $('.mq-overviewmap-close').removeClass(
                'mq-overviewmap-close ui-icon-arrowthick-1-se').addClass(
                'mq-overviewmap-open ui-icon-arrowthick-1-nw');
                }
            });
            var overviewmapsize = {
                w: $(dialogElement).width(),
                h: $(dialogElement).height()
            };
        }else{
            id = this.element.id;
            var overviewmapsize = {
                w: this.options.width,
                h: this.options.height
            };
        }
        var mapOptions = map.olMapOptions;
        //remove the controls, otherwise you end up with recursing events
        delete mapOptions.controls;
        // use the lowest layer of the map as overviewmap
        // TODO: make the layer configurable
        
        var OVmaximized = this.options.maximized;
        
        if (this.options.windowed) {
            var overview = new OpenLayers.Control.OverviewMap(
                {
                    div: document.getElementById(id),
                    size: overviewmapsize,
                    mapOptions: mapOptions,
                    layers: OLlays
                }
            );
        }else{
            var overview = new OpenLayers.Control.OverviewMap(
                {
                    div: document.getElementById(id),
                    size: overviewmapsize,
                    mapOptions: mapOptions,
                    maximized: OVmaximized,
                    layers: OLlays
                }
            );
        }
        map.olMap.addControl(overview);

        // remove OpenLayers blue border around overviewmap
        if (this.options.windowed)
            $('.olControlOverviewMapElement', dialogElement).removeClass('olControlOverviewMapElement');

        element.delegate('.mq-overviewmap-close', 'click', function() {
            $(this).removeClass(
        'mq-overviewmap-close ui-icon-arrowthick-1-se').addClass(
        'mq-overviewmap-open ui-icon-arrowthick-1-nw');
            $('#'+id).dialog('close');
        });
        element.delegate('.mq-overviewmap-open', 'click', function() {
            $(this).removeClass(
        'mq-overviewmap-open ui-icon-arrowthick-1-nw').addClass(
        'mq-overviewmap-close ui-icon-arrowthick-1-se');
            $('#'+id).dialog('open');
        });
    },
    _destroy: function() {
        this.element.removeClass(' ui-widget ui-helper-clearfix ' +
                                 'ui-corner-all')
            .empty();
    }

});
})(jQuery);
