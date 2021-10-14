
// -----------------------------------------------------------------------------
// DEFINIZIONE DEGLI ELEMENTI DEL TEMPLATE DELLA TOC
// -----------------------------------------------------------------------------

// contenitore
$.template('csiLayerManager',
    '<div class="mq-layermanager ui-widget-content  ">'+
    '</div>');

// elemento trascinabile corrispondente al livello
$.template('csiLayerManagerElement',
    '<div class="mq-layermanager-element ui-widget-content ui-corner-all" id="mq-layermanager-element-${id}">'+
        '<div class="mq-layermanager-element-header ui-corner-all ui-helper-clearfix">'+
            '<span class="mq-layermanager-label">'+
                '<input type="checkbox" class="mq-layermanager-element-vischeckbox" id="${id}-visibility" {{if visible}}checked="${visible}"{{/if}} />'+
                '${label}'+
            '</span>'+
        '</div>'+
    '</div>');


// -----------------------------------------------------------------------------
// LOGICA DEL RENDERER
// -----------------------------------------------------------------------------
function CsiLMRendererDefault () {
    
    //this.type = type;
    
    // template del contenitore
    this.getTmplContainer = function() {
        
        return $.tmpl('csiLayerManager');
    }
    
    // contenitore degli elementi associati ai livelli
    this.initLMContainer = function(elemWidget) {
        
        var lmContainer = $.tmpl('csiLayerManager').appendTo(elemWidget);
        
        lmContainer.sortable({
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
        
        return lmContainer;
        
    };
    
    // elemento che rappresenta un livello della mappa nella TOC
    this.getLMElement = function() {
        
    };
}

