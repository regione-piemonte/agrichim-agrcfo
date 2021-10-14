var innerLayout, outerLayout;

function aggiornaPosizionamentoOL(){
	
	//dbg.log("aggiornaPosizionamentoOL");
	
	//w = "100%";
	//mq.olMap.div.style.width = w;
	mq.olMap.updateSize();
	for (var i=0; i<10; i++) {
		if (document.getElementById('mapSec_'+i))
			jQuery('#mapSec_'+i).data("mapQuery").olMap.updateSize();
	}
	//jQuery('#mapSec_1').data("mapQuery").olMap.updateSize();
	//
	//var altezzaToc = $("#layman_accordion").css("height").split(".")[0].split("px")[0];
	//var altezzaLegenda = altezzaToc-50;
	//altezzaToc -= 78;
	//$("#layman_tree").css("height",altezzaToc+"px");
	//$("#box_legenda").css("height",altezzaLegenda+"px");
}

function aggiornaLayoutInterno() {
	innerLayout.resizeAll();
	aggiornaPosizionamentoOL();
}


function init_layout()
{
    // INIZIALIZZAZIONE DEL LAYOUT
    
	
//    outerLayout = $("body").layout(
//		{
//	    	onresize: aggiornaLayoutInterno,
//			north__size:			165
//		//,	center__childOptions:	layoutSettings_BASE
//		,	paneClass:				"csi_mapPane" 		// default = 'ui-layout-pane'
//		}
//								   
//								   );
    
	innerLayout = $("#div_mappa").layout(layoutSettings_BASE);
	

	
	//$('#div_mappa').layout({
	//	onresize: function () {alert('whenever anything on layout is redrawn.')}
	//});
	/*
    var westSelector = "body > .ui-layout-west"; // outer-west pane
	var eastSelector = "body > .ui-layout-east"; // outer-east pane

/*    
    // CREATE SPANs for close-buttons - using unique IDs as identifiers
    $("<span></span>").attr("id", "west-closer" ).prependTo( westSelector );
    $("<span></span>").attr("id", "east-closer").prependTo( eastSelector );
    // BIND layout events to close-buttons to make them functional
    outerLayout.addCloseBtn("#west-closer", "west");
    outerLayout.addCloseBtn("#east-closer", "east");
  */  
    /*
    $('body').layout({
          fxName:               "drop"
        , applyDefaultStyles:   false
        , fxSpeed:              "slow"
        , spacing_closed:       14
        , initClosed:           true
        
        , north__initClosed:     false
        , north__fxName:         "scale"
        
        , east__size:           260
        
        , south__size:           200
        
        , center__applyDefaultStyles: false
        
        
        }
    );
    */
	
}



var layoutSettings_BASE = {
		name: "baseLayout" // NO FUNCTIONAL USE, but could be used by custom code to 'identify' a layout
		
	
	
	    // options.defaults apply to ALL PANES - but overridden by pane-specific settings
	
	,	defaults: {
			//onresize: aggiornaPosizionamentoOL,
			size:					"auto"
        ,   paneClass:				"ui-widget-content" //"csi_genericPane"
		//,	paneClass:				"pane" 		// default = 'ui-layout-pane'
		//,	resizerClass:			"resizer"	// default = 'ui-layout-resizer'
		//,	togglerClass:			"toggler"	// default = 'ui-layout-toggler'
		//,	buttonClass:			"button"	// default = 'ui-layout-button'
		//,	contentSelector:		".content"	// inner div to auto-size so only it scrolls, not the entire pane!
		,	contentIgnoreSelector:	"span"		// 'paneSelector' for content to 'ignore' when measuring room for content
		,	togglerLength_open:		35			// WIDTH of toggler on north/south edges - HEIGHT on east/west edges
		,	togglerLength_closed:	35			// "100%" OR -1 = full height
		,	hideTogglerOnSlide:		true		// hide the toggler when pane is 'slid open'
		,	togglerTip_open:		"Close This Pane"
		,	togglerTip_closed:		"Open This Pane"
		,	resizerTip:				"Resize This Pane"
		//	effect defaults - overridden on some panes
		,	fxName:					"slide"		// none, slide, drop, scale
		,	fxSpeed_open:			750
		,	fxSpeed_close:			1500
		//,	fxSettings_open:		{ easing: "easeInQuint" }
		//,	fxSettings_close:		{ easing: "easeOutQuint" }
	}
    ,
		north: {
            //paneClass:              "csi_toolbarPane"
          size:					42
        //,   maxSize:				42
        ,   minSize:				42
		//,	spacing_open:			10			// cosmetic spacing
		//,	togglerLength_open:		0			// HIDE the toggler button
		//,	togglerLength_closed:	-1			// "100%" OR -1 = full width of pane
		,	resizable: 				false
		//,	slidable:				false
		//	override default effect
		//,	fxName:					"none"
		,	showOverflowOnHover:		true
		,   closable:				false
		}
    
	,	south: {
			size:					42
		,	slidable:				false		// REFERENCE - cannot slide if spacing_closed = 0
		,	initClosed:				false
		,	resizable: 				false
		,   closable:				false
		}
		
	,	west: {
			size:					400
		//,	spacing_closed:			21			// wider space when closed
		//,	togglerLength_closed:	21			// make toggler 'square' - 21x21
		//,	togglerAlign_closed:	"top"		// align to top of resizer
		,	togglerLength_open:		0			// NONE - using custom togglers INSIDE west-pane
		,	togglerTip_open:		"Close West Pane"
		,	togglerTip_closed:		"Open West Pane"
		,	resizerTip_open:		"Resize West Pane"
		//,	slideTrigger_open:		"click" 	// default
		,	initClosed:				true
		,	minWidth:				300
		//	add 'bounce' option to default 'slide' effect
		,	fxSettings_open:		{ easing: "easeOutBounce" }
		//,   closable:				false
		}
    
	,	east: {
			size:					150
		//,	spacing_closed:			21			// wider space when closed
		//,	togglerLength_closed:	21			// make toggler 'square' - 21x21
		//,	togglerAlign_closed:	"top"		// align to top of resizer
		//,	togglerLength_open:		0 			// NONE - using custom togglers INSIDE east-pane
		,	togglerTip_open:		"Close East Pane"
		,	togglerTip_closed:		"Open East Pane"
		,	resizerTip_open:		"Resize East Pane"
		,	slideTrigger_open:		"mouseover"
		,	initClosed:				true
		//,	togglerLength_open:		0
		//	override default effect, speed, and settings
		,	fxName:					"drop"
		,	fxSpeed:				50
        ,	resizable: 				false
		//,	fxSettings:				{ easing: "" } // nullify default easing
		}
	,	center: {
		//	paneSelector:			"#mainContent" 			// sample: use an ID to select pane instead of a class
			onresize: 				aggiornaPosizionamentoOL
		,	minWidth:				200
		,	minHeight:				300
        ,	paneClass:				"csi_mapPane" 		// default = 'ui-layout-pane'
		}
		
		
		
	};
   