var idMemoriaControlloAttivo = 0;


(function($) {

    $.widget("mapQuery.mqToolbar", {
        
        options: {
            map: undefined,
            activeControls: undefined,
            defaultControl: undefined            
        },
        
		deactivateAllControls: function(){
            

            
			for(var i=0; i<this.controlli.length; i++){
                if(this.controlli[i].active) idMemoriaControlloAttivo = i;
				this.controlli[i].deactivate();
            }
		},
		
        activatePreviousControl: function(){

            
            this.controlli[idMemoriaControlloAttivo].activate();
        },

		activePan: function(){
		
		},
		
        _create: function() {
            var map;
            var self = this;
            var element = this.element;
            map = $(this.options.map).data('mapQuery');
            
            appEll = element;
            
            this.element.addClass('ui-widget ui-helper-clearfix ui-widget-header ui-corner-all');
            
            //dbg.log(this.options.activeControls)
            
            // definisce i controlli di default richiesti
            var controlliDefault = new Array();
            for(var i=0; i<this.options.activeControls.length; i++)
            {
                switch (this.options.activeControls[i].name)
                {
                    case "ctrl_nav":
                        this.ctrl_nav = new OpenLayers.Control.NavigationHistory({
                          previousOptions: {
                              title: lng.get("TB_HISPREV_TITLE")
                              , text: (this.options.activeControls[i].showText)? lng.get("TB_HISPREV") : ""
                              , icon: this.options.activeControls[i].iconPrev
                          },
                          nextOptions: {
                              title: lng.get("TB_HISNEXT_TITLE")
                              , text: (this.options.activeControls[i].showText)? lng.get("TB_HISNEXT") : ""
                              , icon: this.options.activeControls[i].iconNext
                          },
                          displayClass: "navHistory"
                        });
                        // parent control must be added to the map
                        map.olMap.addControl(this.ctrl_nav);
                        controlliDefault[controlliDefault.length] = this.ctrl_nav.previous;
                        controlliDefault[controlliDefault.length] = this.ctrl_nav.next;
                    break;
                    
                    case "custom":
                        
                        //dbg.log(this.options.activeControls[i].customControl);
                        
                        controlliDefault[controlliDefault.length] = this.options.activeControls[i].customControl;
                        csiExtendControl(this.options.activeControls[i].customControl,"ui-state-down");

                    break;
                    
                    case "ctrl_CustomFunction":
                        // AL CLICK ESEGUE LA FUNZIONE
                        controlliDefault[controlliDefault.length] = new OpenLayers.Control.Button({
                            trigger: this.options.activeControls[i].customFunction
                            , title: this.options.activeControls[i].title 
                            , text: (this.options.activeControls[i].showText)? this.options.activeControls[i].text : ""
                            , icon: this.options.activeControls[i].icon
                            , CLASS_NAME: "OpenLayers.Control.CustomFunction"
                        });
                    break;
                    
                    default:
                        controlliDefault[controlliDefault.length] = this._getControlWidget(this.options.activeControls[i]);
                        if(controlliDefault[controlliDefault.length-1]==null) controlliDefault.pop();
                }
            }
            
            // definizione del controllo attivo di default
            var appDC = {
                name : this.options.defaultControl
            }
            var defaultControl = this._getControlWidget(appDC);
            //var defaultControl = this.ctrl_ZoomInbox
            
            // creazione del pannello dei controlli di openlayers
            this.olPanel = new OpenLayers.Control.Panel({
                  defaultControl: defaultControl,
                  createControlMarkup: csiControlMarkup,
                  activateControl:multiPanelActivateControl,
                  div: document.getElementById(element.attr("id"))
                });
            
            // assegnazione dei controlli al pannello
            var toolBarNavControls = controlliDefault;
            this.olPanel.addControls(toolBarNavControls);
            map.olMap.addControl(this.olPanel);
			
			this.controlli = controlliDefault;
        },
        
        _destroy: function() {
            this.element.removeClass(' ui-widget ui-helper-clearfix ui-corner-all').empty();
        },
        
        // aggiunge un tool personalizzato
        addCustomControl: function(tool) {
            var self = this;
            var map;
            map = $(this.options.map).data('mapQuery');
            // TODO
        },
        
        _getControlWidget: function(ctrl){
        // restituisce il controllo dal  nome
        // se non è ancora definito lo istanzia
            
            //dbg.log(ctrl);
            
            switch(ctrl.name){
                // PAN
                case "ctrl_DragPan":
                    if(this.ctrl_DragPan == undefined)
                    {
                        this.ctrl_DragPan = new OpenLayers.Control.DragPan({
                            title: lng.get("TB_PAN_TITLE")
                          , text: (ctrl.showText)? lng.get("TB_PAN") : ""
                          , icon: ctrl.icon
                        });
                        csiExtendControl(this.ctrl_DragPan,"ui-state-down");
                    }
                    return this.ctrl_DragPan;
                break;
                
                // ZOOM IN
                case "ctrl_ZoomInbox":
                    if(this.ctrl_ZoomInbox == undefined)
                    {
                        this.ctrl_ZoomInbox = new OpenLayers.Control.ZoomBox({
                            title: lng.get("TB_ZOOMIN_TITLE")
                          , text: (ctrl.showText)? lng.get("TB_ZOOMIN") : ""
                          , out: false
                          , icon: ctrl.icon
                        });
                        csiExtendControl(this.ctrl_ZoomInbox,"ui-state-down");
                    }
                    return this.ctrl_ZoomInbox;
                break;
                
                // ZOOM OUT
                case "ctrl_ZoomOutbox":
                    if(this.ctrl_ZoomOutbox == undefined)
                    {
                        this.ctrl_ZoomOutbox =	new OpenLayers.Control.ZoomBox({
                          title: lng.get("TB_ZOOMOUT_TITLE")
                          , text: (ctrl.showText)? lng.get("TB_ZOOMOUT") : ""
                          , out: true
                          , icon: ctrl.icon
                        });
                        csiExtendControl(this.ctrl_ZoomOutbox,"ui-state-down");
                    }
                    return this.ctrl_ZoomOutbox;
                break;
                
                // ZOOM ESTENSIONE MASSIMA
                case "ctrl_ZoomToMaxExtent":
                    if(this.ctrl_ZoomToMaxExtent == undefined)
                    {
                        this.ctrl_ZoomToMaxExtent = new OpenLayers.Control.ZoomToMaxExtent({
                          title: lng.get("TB_MAXEXT_TITLE")
                          , text: (ctrl.showText)? lng.get("TB_MAXEXT") : ""
                          , icon: ctrl.icon
                        });
                        csiExtendControl(this.ctrl_ZoomToMaxExtent,"ui-state-down");
                    }
                    return this.ctrl_ZoomToMaxExtent;
                break;
                
                /*
                // AL CLICK ESEGUE UNA FUNZIONE
                case "ctrl_CustomFunction":
                    if(this.ctrl_CustomFunction == undefined)
                    {
                        this.ctrl_CustomFunction = new OpenLayers.Control.Button({
                            trigger: ctrl.customFunction
                            , title: ctrl.title //lng.get("TB_CSTFUN_TITLE")
                            , text: ctrl.text //(ctrl.showText)? lng.get("TB_CSTFUN") : ""
                            , icon: ctrl.icon
                            , CLASS_NAME: "OpenLayers.Control.CustomFunction"
                        });
                    }
                    return this.ctrl_CustomFunction;
                break;
                */
                
            }
            return null;
        }
        
    });
})(jQuery);

    
// definizione dell'html del tasto
function csiControlMarkup(control) {
    // dbg.log(control);
    var button = document.createElement('button')
    if(control.text=="")
    {
        $(button).button({icons: { primary: control.icon}, text: false, label: control.title});
        /*.tooltip(
        {
            position: {
                my: "center bottom-20",
                at: "center top",
                using: function( position, feedback ) {
                    $( this ).css( position );
                    $( "<div>" )
                    .addClass( "arrow" )
                    .addClass( feedback.vertical )
                    .addClass( feedback.horizontal )
                    .appendTo( this );
                }
            }
        });*/
        //.tooltip({show: {effect: "slideDown",delay: 250}});
    }
    else
    {
        $(button).button({icons: { primary: control.icon}, text: true, label: control.text});
        /*.tooltip(
        {
            position: {
                my: "center bottom-20",
                at: "center top",
                using: function( position, feedback ) {
                    $( this ).css( position );
                    $( "<div>" )
                    .addClass( "arrow" )
                    .addClass( feedback.vertical )
                    .addClass( feedback.horizontal )
                    .appendTo( this );
                }
            }
        });*/
        //.tooltip({show: {effect: "slideDown",delay: 250}});
    }
    return button;
}
