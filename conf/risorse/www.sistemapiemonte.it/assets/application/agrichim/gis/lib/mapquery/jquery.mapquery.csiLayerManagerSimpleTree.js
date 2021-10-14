
var titoloCartellaRoot = lng.get("LAYMANTREE_ROOTFOLDER");
var WMSSources = [
    {
        name: "PROVA WMS INTERNO - aera",
        url: 'http://dev-geoengine1.csi.it/ws/aera/wms_aera_limiti'
    },
    {
        name: "ISPRA - Limiti amministrativi",
        url: 'http://sgi2.isprambiente.it/wmsconnector/com.esri.wms.Esrimap/Geologica_raster_500k'
    },
    {
        name: "ISPRA - Carta Geologica 1:500.000",
        url: "http://sgi1.isprambiente.it/ArcGIS/services/servizi/limiti_amministrativi/mapserver/WMSServer"
    }
];

var activeLayerElement = null;
var nodoCliccato = null;
(function($) {
	
/*
$.template('csiLayerManagerSimpleTreeAccordion',
    '<div id="layman_accordion" >'+
	    
		'<h3><a href="#">${LAYMANTREE_LIVELLI_TITOLO}</a></h3>'+
		'<div id="layman_acc_mappe"></div>'+
		
		'<h3><a href="#">${LAYMANTREE_LEGENDA_TITOLO}</a></h3>'+
		'<div id="layman_acc_legenda"></div>'+
		
    '</div>');
*/
$.template('csiLayerManagerSimpleTreeAccordion',
    '<div id="layman_accordion" >'
		+'<div id="tabs">'
		+'<ul>'
			+'<li><a href="#layman_acc_mappe">${LAYMANTREE_LIVELLI_TITOLO}</a></li>'
			+'<li><a href="#layman_acc_legenda">${LAYMANTREE_LEGENDA_TITOLO}</a></li>'
		+'</ul>'
		+'<div id="layman_acc_mappe"></div>'
		+'<div id="layman_acc_legenda"></div>'
		
	    /*

		'<h3>${LAYMANTREE_LIVELLI_TITOLO}</h3>'+
		'<div id="layman_acc_mappe"></div>'+
		
		'<h3>${LAYMANTREE_LEGENDA_TITOLO}</h3>'+
		'<div id="layman_acc_legenda"></div>'+
		*/
		
    +'</div>');
	
$.template('csiLayerManagerSimpleTreeToolbar',
    '<div id="toolbar" class="mq-layermanager-toolbar ui-widget-content  ">'+
	    
        '<button id="mqLayManToolTree_addLayer" >${LAYMANTREE_NEWLAY}</button>'+
        '<button id="mqLayManToolTree_delLayer" >${LAYMANTREE_DELLAY}</button>'+
        
		'<button id="mqLayManToolTree_delVectorLayers" >${LAYMANTREE_DELLAYERS}</button>'+
		
        '<button id="mqLayManToolTree_addFolder" >${LAYMANTREE_NEWFOLD}</button>'+
        '<button id="mqLayManToolTree_delFolder" >${LAYMANTREE_DELFOLD}</button>'+
		
		'<button id="mqLayManToolTree_prop" >${LAYMANTREE_PROP}</button>'+
    '</div>');

	// CONTENITORE LEGENDE
$.template('csiLayerManagerSimpleTreeLegend',    
	'<div id="accordion_legenda">'+
		'<div>'+
			'<div id="box_legenda"></div>'+
		'</div>'+
    '</div>');

	// ELEMENTI DELLA LEGENDA
$.template('csiLayerManagerSimpleTreeLegendIMG',
    '<div id="legend_box_${id}">'+
		'<div class="legend_img_title" >${layer_title}</div>'+
		'<img class="legend_img" src="" id="legend_img_${id}" />'+
    '</div>');

	// CONTENITORE TOC PRINCIPALE
$.template('csiLayerManagerSimpleTree',
    '<div id="layman_tree" ></div>');


	
	// DIALOG
$.template('csiLayerManagerSimpleTreeDialogNewFolder',
    '<div id="dia_newfolder" title="${LAYMANTREE_DIANEWFOLDTITLE}">'+
        '<input id="dia_newfolder_str">'+
    '</div>');

$.template('csiLayerManagerSimpleTreeDialogProp',
    '<div id="dia_prop" title="${LAYMANTREE_DIAPROPTITLE}">'+
		// per il cambio nome cartella
        '<div id="laymanprop_name">'+
            '${LAYMANTREE_DIA_PROP_RENAME}'+
			'<br/>'+
            '<input type="text" id="lmp_dir_name" />'+
			'&nbsp;'+
			'<button id="lmp_dir_name_btn" >${LAYMANTREE_DIA_PROP_RENAMEB}</button>'+
        '</div>'+
		// proprietà WMS
        '<div id="laymanprop_infowms">'+
            '<label for="lmp_wms_title">${LAYMANTREE_DIA_PROP_WTIT}</label>'+
            '<input type="text" id="lmp_wms_title" />'+
			'&nbsp;'+
			'<button id="lmp_wms_name_btn" >${LAYMANTREE_DIA_PROP_RENAMEB}</button>'+
			'<br>'+
            '<label style="display:none;" for="lmp_wms_name">${LAYMANTREE_DIA_PROP_WNAME}</label>'+
            '<input style="display:none;" type="text" id="lmp_wms_name" />'+
			'<br>'+


			'<br>'+
            '${LAYMANTREE_DIA_PROP_WDESC}'+
			'<br>'+
			'<br>'+
			'<div id="slider_trasparenza" class="mq-layermanager-element-slider"></div>'+
        '</div>'+
		
    '</div>');
$.template('csiLayerManagerSimpleTreeDialogServices',
    '<div id="dia_services" title="${LAYMANTREE_DIASERVICESTITLE}">'+
        '<div>'+
            '<select id="dia_combo_wms"></select>&nbsp;'+
            '<button id="dia_btn_wms_add" >${LAYMANTREE_ADDBTN}</button>'+
        '</div>'+
        '<div class="me_titolo">Livelli</div>'+
		//'<div id="div_dia_listawms">'+
			'<ol id="dia_listawms">'+
				''+
			'</ol>'+
		//'</div>'+
    '</div>');
            
$.template('csiLayerManagerSimpleTreeDialogNewServices',
    '<div id="dia_services_new" title="${LAYMANTREE_DIANEWSERTITLE}">'+
        '<div>'+
            '<label for="dia_newservice_str">URL: </label>'+
            '<input size="50" id="dia_newservice_str">'+
            
        '</div>'+
    '</div>');

$.template('csiLayerManagerSimpleTreeWMSelement',
	'<li class="ui-widget-content">'+
		'<div class="wmsabsbox">'+
			'<div class="wmsabstxt">${WMStitle}</div>'+

			'<button id="wmsabsbtn_${id}" class="wmsabsbtn">&nbsp;</button>&nbsp;&nbsp;'+

			'<input type="hidden" id="wmsname_${id}" value="${WMSname}" />'+
			'<input type="hidden" id="wmstitle_${id}" value="${WMStitle}" />'+
		'</div>'+

		
		'<div id="wmsabs_${id}" style="display:none;" class="wmsabsinfo">'+
			'<b>${abst}</b>&nbsp;${data}'+
		'</div>'+
		
	'</li>'
	);


$.template('csiLayerManagerSimpleTreeMenu',
	'<ul id="LMST_menu">'+
		'<li><a href="#"><span class="ui-icon ui-icon-disk"></span>Zoom al livello</a></li>'+
		'<li><a href="#">Rimuovi livello</a></li>'+
		'<li><a href="#">Propriet&agrave;</a></li>'+
		//'<li><a href="#">Rimuovi gruppo</a></li>'+
	'</ul>');


	
$.widget("mapQuery.csiLayerManagerSimpleTree", {
    options: {
        // The MapQuery instance
        map: undefined
    },
    _create: function() {
        
        var map;
        var self = this;
        var element = this.element;
        
        
      	// DIALOG: nuova cartella
        $.tmpl('csiLayerManagerSimpleTreeDialogNewFolder',{
           LAYMANTREE_DIANEWFOLDTITLE: lng.get("LAYMANTREE_DIANEWFOLDTITLE")
        }).appendTo(element);
        
        $("#dia_newfolder").dialog(
        {
            resizable: false,
            height:180,
            autoOpen: false,
            
             buttons: {
                "inserisci": function() {
                    $( this ).dialog( "close" );
                    self._addFolder($("#dia_newfolder_str").val());
                },
                "Chiudi": function() {

                    $( this ).dialog( "close" );
                }
            }
        });
        
      	// DIALOG: nuovo WMS
        $.tmpl('csiLayerManagerSimpleTreeDialogServices',{
           LAYMANTREE_DIASERVICESTITLE: lng.get("LAYMANTREE_DIASERVICESTITLE"),
           LAYMANTREE_ADDBTN: lng.get("LAYMANTREE_ADDBTN")
        }).appendTo(element);
        
        $("#dia_services").dialog(
        {
            resizable: false,
            height:350,
            width:600,
            autoOpen: false,
            modal: true,
            
            buttons: {
                "Aggiungi livelli": function() {
					
                    $( this ).dialog( "close" );
					
                    //self._addFolder($("#dia_newfolder_str").val());
                    //var indiceLay = $("#dia_listawms").selectable("option", "active");
					
					var index = [];
					$(".ui-selected", $("#dia_listawms")).each(function(){
						index[index.length] = $("#dia_listawms li").index(this);
					});
					
					for(var i=0; i<index.length; i++)
					{
						var indiceLay = index[i];
						if(indiceLay!=-1)
						{
							var wmsLabel = $("#wmstitle_"+indiceLay).val();
							var wmsLayer = $("#wmsname_"+indiceLay).val();
							
							var indiceWMS = $("#dia_combo_wms").val();
							
							retrieveWMSparamsAndAddToMap('#map', {
								type:'csi_wms',
								label:wmsLabel,
								url:WMSSources[indiceWMS].url,
								layers: wmsLayer
							})
							/*
							jQuery('#map').data('#map').layers({
								type:'wms',
								label:wmsLabel,
								url:WMSSources[indiceWMS].url,
								layers: wmsLayer
							});
							*/
						}
					}
                },
                "Chiudi": function() {

                    $( this ).dialog( "close" );
                }
            }
			
        });
        
        
      	// DIALOG: nuovo WMS inserimento URL
        $.tmpl('csiLayerManagerSimpleTreeDialogNewServices',{
           LAYMANTREE_DIANEWSERTITLE: lng.get("LAYMANTREE_DIANEWSERTITLE")
        }).appendTo(element);
        
        $("#dia_services_new").dialog(
        {
            resizable: false,
            height:120,
            width:500,
            autoOpen: false,
            modal: true,
            
            buttons: {
                "Aggiungi": function() {
                    var newUrl = $("#dia_newservice_str").val();
                    // TODO: migliorare la logica per capire se è una url wms corretta
                    //if(newUrl.substring(0,7)=="http://")
                    if (checkUrl(newUrl)) 
                    {
                        // TODO: migliorare la creazione del nome del nuovo servizio
						/*
                        var newName = newUrl.split("/");
                        newName = newName[newName.length-1];
                        newName = newName;
                        WMSSources[WMSSources.length] = {
                                name: newName,
                                url: newUrl                            
                            };
                            */
                        //self.popolaSelectWMS();
						//self.popolaSelectWMSUltimaSelezione();
						
                        newUrl = newUrl.split("?")[0];
                        
                        self.recuperaDatiWMSSources({url: newUrl});
                        
						// TODO: selezionare il nuovo WMS nella combo alla conferma dell'aggiunta
                        $( this ).dialog( "close" );
                    }else{
						alert(newUrl);
                        custom_alert("Url non valida:\n"+newUrl, "Attenzione!", "error");

                    }
                },
                "Chiudi": function() {

                    $( this ).dialog( "close" );
                }
            }       
			
        });
        
		// DIALOG: proprietà livello/cartella
        $.tmpl('csiLayerManagerSimpleTreeDialogProp',{
           LAYMANTREE_DIAPROPTITLE: lng.get("LAYMANTREE_DIAPROPTITLE"),
		   LAYMANTREE_DIA_PROP_RENAME: lng.get("LAYMANTREE_DIA_PROP_RENAME"),
		   LAYMANTREE_DIA_PROP_RENAMEB: lng.get("LAYMANTREE_DIA_PROP_RENAMEB"),
		   LAYMANTREE_DIA_PROP_WTIT: lng.get("LAYMANTREE_DIA_PROP_WTIT"),
		   LAYMANTREE_DIA_PROP_WNAME: lng.get("LAYMANTREE_DIA_PROP_WNAME"),
		   LAYMANTREE_DIA_PROP_WDESC: lng.get("LAYMANTREE_DIA_PROP_WDESC")		   
        }).appendTo(element);
        
        $("#dia_prop").dialog(
        {
            resizable: false,
            height:250,
            width:350,
            autoOpen: false,
            
            buttons: {
                "Chiudi": function() {
					$( this ).dialog( "close" );
                }
            }        
        });
		/*
		//activeLayerElement = 
		$("#slider_trasparenza").slider({
           max: 100,
           step: 1,
           value: activeLayerElement.visible()?layer.opacity()*100:0,
           slide: function(event, ui) {
               var layer = activeLayerElement.data('layer');
               var self =  activeLayerElement.data('self');
               self._opacity(layer,ui.value/100);
           }
       });
		*/
		// TASTO DEL DIALOG: rinomina la cartella in TOC
        $("#lmp_dir_name_btn").button(
            {
                text: true
            }).click(function(event)
            {
                event.preventDefault();
                // TODO: apre la finestra per aggiungere un wms inserendo la url
				var node = $("#layman_tree").dynatree("getActiveNode");
				node.data.title = $("#lmp_dir_name").val();
				
				// cambia il nome della cartella di appartenenza per tutti i livelli contenuti
				var figli = node.childList;
				for(var k=0; k<figli.length; k++) {
					mq.layersList[figli[k].data.layer.id].options.cartellaToc = $("#lmp_dir_name").val();
				}
				
				node.render();
				
				// PER SALVARE LO STATO DELLA TOC NEI COOKIES
				salvaTematismiNeiCookies();	
            }
        );
	
		// TASTO DEL DIALOG: rinomina il livello in TOC
        $("#lmp_wms_name_btn").button(
            {
                text: true
            }).click(function(event)
            {
                event.preventDefault();
                // TODO: apre la finestra per aggiungere un wms inserendo la url
				var node = $("#layman_tree").dynatree("getActiveNode");
				node.data.title = $("#lmp_wms_title").val();
				mq.layersList[node.data.layer.id].options.label = $("#lmp_wms_title").val();
				node.render();
				// PER SALVARE LO STATO DELLA TOC NEI COOKIES
				salvaTematismiNeiCookies();
            }
        );
		
		
		
				$("#dia_listawms").selectableScroll({
			filter: 'li',
			selecting: function(e, ui) { // on select
				var curr = $(ui.selecting.tagName, e.target).index(ui.selecting); // get selecting item index
				if(e.shiftKey && prev > -1) { // if shift key was pressed and there is previous - select them all
					$(ui.selecting.tagName, e.target).slice(Math.min(prev, curr), 1 + Math.max(prev, curr)).addClass('ui-selected');
					prev = -1; // and reset prev
				} else {
					prev = curr; // othervise just save prev
				}
			}
		});
		
        // SELECT DEL DIALOG: aggiorna la lista
        $('#dia_combo_wms').change(function() { self.onChangeWMSList(); });
        // TASTO DEL DIALOG: aggiunge il WMS
        $("#dia_btn_wms_add").button(
            {
                text: true,
                icons: {primary: "ui-icon-circle-plus"}
            }).click(function(event)
            {
                event.preventDefault();
                // TODO: apre la finestra per aggiungere un wms inserendo la url
                $("#dia_services_new").dialog("open");
            }
        );

        
        self.popolaSelectWMS();
        
		
		// Menu tasto destro sui livelli
		/*
        $.tmpl('csiLayerManagerSimpleTreeMenu',{
           //LAYMANTREE_DIANEWSERTITLE: lng.get("LAYMANTREE_DIANEWSERTITLE")
        }).appendTo(element);
		$("#LMST_menu").menu();
		*/
		
        //get the mapquery object
        map = $(this.options.map).data('mapQuery');
		this.map = map;
        
        this.element.addClass('ui-widget  ui-helper-clearfix ui-corner-all');
		
		// toolbar con accordion
        $.tmpl('csiLayerManagerSimpleTreeAccordion',{
           LAYMANTREE_LEGENDA_TITOLO: lng.get("LAYMANTREE_LEGENDA_TITOLO"),
           LAYMANTREE_LIVELLI_TITOLO: lng.get("LAYMANTREE_LIVELLI_TITOLO")
        }).prependTo(element);
		//$('#layman_accordion').multiAccordion({	active: [0,1]});
		$('#layman_accordion').tabs();
		
        
        // contenuto della toc (albero)
        //var lmElement = $.tmpl('csiLayerManagerSimpleTree').appendTo(element);
		var lmElement = $.tmpl('csiLayerManagerSimpleTree').appendTo($("#layman_acc_mappe"));
		
// ############################################################################## cookies start
		// crea le cartelle che sono salvate nei cookies
		//var cartelleCookies = eval('(' + getCookie("cartelleToc") + ')');
		var cartelleDaCreare = new Array();
		
		// cartella di default principale
//		cartelleDaCreare[cartelleDaCreare.length] = {
//                title: titoloCartellaRoot,
//                isFolder: true,
//                expand: true,
//                //noLink: true,
//                select: true
//            }
		
		/*
		if(cartelleCookies){
			for (var j=0; j<cartelleCookies.length; j++) {
				if (titoloCartellaRoot!=cartelleCookies[j]) {
					cartelleDaCreare[cartelleDaCreare.length] = {
						title: cartelleCookies[j],
						isFolder: true,
						expand: true,
						//noLink: true,
						select: true
					}
				}
			}
		}
		*/
		
		/*
		if(cartelleTocUtente){
			for (var j=0; j<cartelleTocUtente.length; j++) {
				if(titoloCartellaRoot!=cartelleCookies[j]) {
					cartelleDaCreare[cartelleDaCreare.length] = {
						title: cartelleCookies[j],
						isFolder: true,
						expand: true,
						//noLink: true,
						select: true
					}
				}
			}
		}
		*/
		
		
		
		// ############################################################################## cookies end
		
        //this.element.dynatree({
		$("#layman_tree").dynatree({
			
            
            debugLevel: 0,
            //minExpandLevel: 2,
            persist: false, // salva lo stato nei coockies
            checkbox: true,
            selectMode: 3, // 1:single, 2:multi, 3:multi-hier
            
           children: cartelleDaCreare, // dai cookies
//			[{

//                title: titoloCartellaRoot,
//                isFolder: true,
//                expand: true,
//                //noLink: true,
//                select: true
//            }],
            
			onDeactivate: function(node) {
				// tasto proprietà spento 
				$("#mqLayManToolTree_prop").button("disable");
            },
            onActivate: function(node) {
                
				// tasto proprietà acceso
				$("#mqLayManToolTree_prop").button("enable");
				
				if(node.data.isFolder){
					$("#laymanprop_name").show();
					$("#laymanprop_infowms").hide();
					$("#lmp_dir_name").val(node.data.title);
				}else{
					$("#laymanprop_name").hide();
					$("#laymanprop_infowms").show();
					$("#lmp_wms_title").val(node.data.title);	
					activeLayerElement = node.data.layer;									
				}
				// tasto elimina cartella
                if(node.data.isFolder && node.data.title!=titoloCartellaRoot)
                {
                    $("#mqLayManToolTree_delFolder").button("enable");
                }else{
                    $("#mqLayManToolTree_delFolder").button("disable");
                }
            },
            onSelect: function(state, node) {
                    if(node.data.isFolder)
                    {
                        var leafs = node.getChildren();
                        for(var i=0; i<leafs.length; i++)
                            self._visible(leafs[i].data.layer,state);
                    }
                    else
                        self._visible(node.data.layer,state);
                },
                
            dnd: {
                preventVoidMoves: true,
                
                onDragStart: function(node) {
                    return true;
                },
                
                onDragEnter: function(node, sourceNode) {
                    
					if(node.isDescendantOf(sourceNode)) return false;
					
                    if(!node.data.isFolder && sourceNode.data.isFolder) return false;
                    if(node.data.isFolder && !sourceNode.data.isFolder) return true;
					if(node.data.isFolder && sourceNode.data.isFolder) return true;
					
					return ["before", "after"];
                
                },
                onDrop: function(node, sourceNode, hitMode, ui, draggable) {
					// sposta il nodo
                    sourceNode.move(node, hitMode);					
					
					//
                    var tree = $("#layman_tree").dynatree("getTree");
					// numero di cartelle + nodi
                    var position = tree.count();
                    position--; // perchè l'indice parte da 0
                    
					// trovo le cartelle
                    var cartelle = tree.getRoot().getChildren();
                    for(var ik=0; ik<cartelle.length; ik++) // ciclo sulle cartelle
                    {
                        position--; // per eliminare le cartelle dal conteggio
                        
                        var leafs = cartelle[ik].getChildren();
						if (leafs) {
							for(var ak=0; ak<leafs.length; ak++) // ciclo sulle foglie (livelli)
							{								
								if(typeof leafs[ak].data.layer != 'undefined')
									self._position(leafs[ak].data.layer, position);
								
								
								//dbg.log(leafs[ak].data.layer.cartellaToc);
								//dbg.log(sourceNode.data.title);
								//dbg.log(node.data.title);
								
								var mqliv = mq.layers();
								for (var i=0; i<mqliv.length; i++) {
									if (mqliv[i].label == sourceNode.data.title)
									{
										//dbg.log("CAMBIA: "+mq.layersList[mqliv[i].id].cartellaToc);
										mq.layersList[mqliv[i].id].options.cartellaToc = node.data.title;
										//dbg.log("CAMBIATO: "+mq.layersList[mqliv[i].id].cartellaToc);
									}
								}
								// PER SALVARE LO STATO DELLA TOC NEI COOKIES
								salvaTematismiNeiCookies();
								
								position--;
							}
						}

                    }
                    
                }
            }
            
        });
		
		
		
        // toolbar della toc
        var lmToolbar = $.tmpl('csiLayerManagerSimpleTreeToolbar',{
           LAYMANTREE_NEWFOLD: lng.get("LAYMANTREE_NEWFOLD"),
           LAYMANTREE_DELFOLD: lng.get("LAYMANTREE_DELFOLD"),
           LAYMANTREE_NEWLAY: lng.get("LAYMANTREE_NEWLAY"),
           LAYMANTREE_DELLAY: lng.get("LAYMANTREE_DELLAY"),
		   LAYMANTREE_DELLAYERS: lng.get("LAYMANTREE_DELLAYERS"),
		   LAYMANTREE_PROP: lng.get("LAYMANTREE_PROP")
        }).prependTo("#layman_acc_mappe"); // element
        
		// legenda
        $.tmpl('csiLayerManagerSimpleTreeLegend',{
        }).appendTo($("#layman_acc_legenda"));
		
        // TASTO: aggiunge un livello
        $("#mqLayManToolTree_addLayer").button(
            {
                text: false,
                icons: {primary: "ui-icon-circle-plus"}
            }).click(function(event)
        {
            event.preventDefault();
            $("#dia_services").dialog("open");
            self.onChangeWMSList();
        });
        
        // TASTO: rimuove un livello
        $("#mqLayManToolTree_delLayer").button(
            {
                text: false,
                icons: {primary: "ui-icon-circle-minus"}
            }).click(function(event)
        {
            event.preventDefault();
            var activeNode = $("#layman_tree").dynatree("getActiveNode");
            //dbg.log(nodes[ik].data.title)
			try {
				if(activeNode.data.layer.noDelete){
					custom_alert(lng.get("LAYMANTREE_MSG1"), lng.get("LAYMANTREE_MSG1_TIT"), "alert");
				}else{
					if (confirm(lng.get("LAYMANTREE_CONFIRM")+"\n"+activeNode.data.title))
					{
						self._remove(activeNode.data.layer)
						activeNode.remove();
					}
				}
			} catch(e) {
				// cerco di cancellare una cartella con il comando per cancellare i livelli
			}

			
        });
		
        // TASTO: rimuove tutti livelli vettoriali aggiunti (quelli noDelete = false)
        $("#mqLayManToolTree_delVectorLayers").button(
            {
                text: false,
                icons: {primary: "ui-icon-scissors"}
            }).click(function(event)
        {
            event.preventDefault();
			
			var nodesToDelete = new Array();
			
			var tree = $("#layman_tree").dynatree("getTree");
			var root = tree.getRoot();
            var cartelle = root.getChildren();
			if(cartelle){
				for(var ik=0; ik<cartelle.length; ik++){
					var nodi = cartelle[ik].getChildren();
					if(nodi){
						for(var i=0; i<nodi.length; i++){
							if(!nodi[i].data.layer.noDelete){
								nodesToDelete[nodesToDelete.length] = nodi[i];
							}
						}
					}
				}
			}
			for(var i=0; i<nodesToDelete.length; i++)
			{
				self._remove(nodesToDelete[i].data.layer);
				nodesToDelete[i].remove();
			}
        });
        
        // TASTO: aggiunge una nuova cartella
        $("#mqLayManToolTree_addFolder").button(
            {
                text: false,
                icons: {primary: "ui-icon-folder-open"}
            }).click(function(event)
        {
            event.preventDefault();
            $("#dia_newfolder").dialog("open");
        });
        
        // TASTO:  cancella una cartella e i livelli contenuti
        $("#mqLayManToolTree_delFolder").button(
            {
                text: false,
                icons: {primary: "ui-icon-folder-open"}
            }).click(function(event)
        {
            event.preventDefault();
            
            var activeNode = $("#layman_tree").dynatree("getActiveNode");
            var nodes = activeNode.getChildren();
			// controllo se contiene un livello NODELETE
			var contieneNoDelete = false;
			if (nodes) {
				for(var ik=0; ik<nodes.length; ik++)
				{

					if(nodes[ik].data.layer.noDelete)
						contieneNoDelete = true;
				}				
			}
			if (contieneNoDelete) {
				custom_alert(lng.get("LAYMANTREE_MSG1"), lng.get("LAYMANTREE_MSG1_TIT"), "alert");
			}else{
				if (nodes) {
					for(var ik=0; ik<nodes.length; ik++)
						self._remove(nodes[ik].data.layer)
				}

				activeNode.remove();
			}
			
			// PER SALVARE LO STATO DELLA TOC NEI COOKIES
			salvaTematismiNeiCookies();
        });
        $("#mqLayManToolTree_delFolder").button("disable");
        
        // TASTO:  proprietà livello/cartella
        $("#mqLayManToolTree_prop").button(
            {
				disabled: true,
                text: false,
                icons: {primary: "ui-icon-wrench"}
            }).click(function(event)
        {
            event.preventDefault();
			$("#dia_prop").dialog("open");
			
			//dbg.log(activeLayerElement);
			
              
			$("#slider_trasparenza").slider({
				max: 100,
				step: 1,
				value: activeLayerElement.visible()?activeLayerElement.opacity()*100:0,
				slide: function(event, ui) {
					//var layer = activeLayerElement.data('layer');
					//var self =  activeLayerElement.data('self');
					self._opacity(activeLayerElement , ui.value/100);
				}
			});
			$("#dia_prop").dialog("open");			
            // TODO: aprire finestra proprietà
        });
        
		$.tmpl('csiLayerManagerSimpleTreeMenu',{
		   //LAYMANTREE_DIANEWSERTITLE: lng.get("LAYMANTREE_DIANEWSERTITLE")
		}).appendTo($("body"));
		//$("#LMST_menu").menu();

						// TODO: eseguire la funzione corrispondente alla voce di menu selezionata
						
						//activeLayerElement // livello corrispondente al click destro sull'albero
		
		$( "#LMST_menu" ).menu({
			select: function( event, ui ) {
				//dbg.log(ui.item.index());
				switch (ui.item.index()) {
					
					case 0: // zoom al livello
						if (activeLayerElement.maxExtent!=null)
							mq.center({box:activeLayerElement.maxExtent});
						else
							custom_alert("Estensione massima non definita", "Attenzione", "alert");
						break;
					
					case 1: // rimuovi livello
						if (!activeLayerElement.noDelete) {
							if (confirm(lng.get("LAYMANTREE_CONFIRM")+"\n"+nodoCliccato.data.title)) {
								self._remove(nodoCliccato.data.layer)
								nodoCliccato.remove();
							}
						}else{
							custom_alert("Impossibile rimuovere un livello bloccato.", "Attenzione", "alert");
						}
						break;
					
					case 2: // proprietà
						$("#dia_prop").dialog("open");
						$("#slider_trasparenza").slider({
							max: 100,
							step: 1,
							value: activeLayerElement.visible()?activeLayerElement.opacity()*100:0,
							slide: function(event, ui) {
								//var layer = activeLayerElement.data('layer');
								//var self =  activeLayerElement.data('self');
								self._opacity(activeLayerElement , ui.value/100);
							}
						});												
						break;
					
					case 3: // rimuovi gruppo
						break;
				}
				
				
				//dbg.log(ui);
			}
		});
		$("#LMST_menu").hide();
		
		$('#LMST_menu').click(function() {
			$('#LMST_menu').hide();
		});
		$(document).click(function() {
			$('#LMST_menu').hide();
		});
        
        //these layers are already added to the map as such won't trigger 
        //and event, we call the draw function directly
        $.each(map.layers().reverse(), function(){
           self._layerAdded(lmElement, this);
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
		
		// rimuove la legenda
		$("#legend_box_"+layer.id).remove();
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
    
    _addFolder: function(foldName){
        
		// per definire sottocartelle
		// foldName = dir1||dir2||dir3...
		var tree = $("#layman_tree").dynatree("getTree");
        var parentNode = tree.getRoot();
		var cartellaEsiste;
        
		foldName = foldName.split("||");
		for (var i=0; i<foldName.length; i++)
		{
			cartellaEsiste = false;
			
			// controllo se la cartella esiste
			if(foldName[i] != ""){
				
				//var nodoInCuiCercare = null;
				//if (i==0)
				//	nodoInCuiCercare = $("#layman_tree").dynatree("getRoot");
				//else
				//	nodoInCuiCercare = parentNode; 
					
				parentNode.visit(function(nd){
					if(nd.data.title == foldName[i])
					{
						parentNode = nd;
						cartellaEsiste = true;
					}
				});
			}
			
			if (!cartellaEsiste)
			{
				var beforeNode = parentNode.getChildren();
				if (beforeNode!=null) beforeNode = beforeNode[0];
				
				//if (parentNode == tree.getRoot()) beforeNode = null;
				
				if (foldName[i]!="") {
					parentNode.addChild(
						{
							title: foldName[i],
							isFolder: true,
							expand: true,
							select: true            
						}
						,beforeNode
					);
					parentNode = parentNode.getChildren()[0];
				}
				//if (parentNode == tree.getRoot())
				//{
				//	parentNode = parentNode.getChildren();
				//	parentNode = parentNode[parentNode.length-1]
				//}
				//else
			}
		}
		
		
		// PER SALVARE LO STATO DELLA TOC NEI COOKIES
		salvaTematismiNeiCookies();
    },

    recuperaDatiWMSSources: function(newSource){
		// se la fonte è già presente la seleziona nel menu a tendina
		// altrimenti fa la getcapabilities
		
    var sorgenteEsistente = false;
    for(var i=0; i<WMSSources.length; i++) {
     if (WMSSources[i].url == newSource.url) {
      this.popolaSelectWMS(i);
      sorgenteEsistente = true;
     }
		}
		
		if (!sorgenteEsistente)
		{
			$("#dia_listawms").empty();
			$("#dia_listawms").html('<div id="recupero_info">Attendere: recupero informazioni...</div>');
			wmsUtils.getCapabilitiesFromWMS(newSource.url,this.aggiungiAndSelezionaWMSSources, this);
		}
	},
	
  	aggiungiAndSelezionaWMSSources: function(url, capabilities, widget){
		// chiama la getcapabilities e aggiunge una nuova sorgente al menu a tendina
		// recuperando il nome dalla chiamata, quindi popola la lista dei livelli del wms
  if (capabilities == null || !capabilities.service) {
    $("#dia_listawms").html("");
    custom_alert("Url non valida", "Attenzione!", "error");
    return false;
  }
  
		//{name: nome, url: url}
		//dbg.log(url);
		//dbg.log(capabilities);
		//capabilities.service.title
		WMSSources[WMSSources.length] = {
            name: capabilities.service.title,
            url: url                            
        };
		
		//dbg.log(widget)
		
		// devo usare widget e farmelo arrivare come parametro
		// perchè la chiamata è generata non dal widget stesso ma come
		// trigger dalla funzione che fa la getcapabilities
		widget.popolaSelectWMSUltimaSelezione();
		widget._refreshWMSList(capabilities.capability.layers);
		//wmsUtils.getCapabilitiesFromWMS(url,this._refreshWMSList);
	},
	
	popolaSelectWMS: function(selezione){
        
        $('#dia_combo_wms').html("");
        // aggiungo le sorgenti predefinite alla combo
        for(var i in WMSSources)
        {
            $('#dia_combo_wms')
                .append($("<option></option>")
                .attr("value",i)
                .text(WMSSources[i].name));
        }
		$('#dia_combo_wms').val(selezione);
    },
    
	popolaSelectWMSUltimaSelezione: function(){this.popolaSelectWMS(WMSSources.length-1);},
	
	
    // aggiorna l'elenco dei livelli del WMS
    // in base alla voce selezionata nella combo della finestra nuovo livello
    onChangeWMSList: function(){
        $("#dia_listawms").html("");
        
        var url = $("#dia_combo_wms").val();
        url = WMSSources[url].url;
		$("#dia_listawms").html('<div id="recupero_info">Attendere: recupero informazioni...</div>');
        wmsUtils.getLayersFromWMS(url,this._refreshWMSList);
        
    },
	
    // aggiorna la lista dei livelli del wms selezionato
    _refreshWMSList: function(lay){
        
		$("#dia_listawms").empty();
        for(var k=0; k<lay.length; k++)
        {
            $.tmpl('csiLayerManagerSimpleTreeWMSelement',{
                id: k,
                WMStitle: lay[k]["title"],
                data: lay[k]["anstract"],
                WMSname: lay[k]["name"],
                abst: lng.get("LAYMANTREE_ABSTRACT") 
            }).appendTo($("#dia_listawms"));
            
			// TASTO: info del livello
			if (lay[k]["abstract"]=="") {
				$("#wmsabsbtn_"+k).hide();
			}else{
				$("#wmsabsbtn_"+k).button(
				{

					text: false,
					icons: {primary: "ui-icon-info"}
				}).click(function(event)
				{

					event.preventDefault();
					var index = event.currentTarget.id.split("_")[1];
					$("#wmsabs_"+index).slideToggle();
				});							
			}		
			
			
            //dbg.log(lay[k]);
        }
        
        $( "#dia_listawms" ).selectable("refresh");
    },
    
	// aggiorna la legenda
	_refreshLegend: function(widget, layer){
        var self = this;
		
		url =layer.legend().url;
		if(url==''){error='No legend for this layer';}
		else{
			$("#legend_img_"+layer.id).attr("src",url);
		}
		/*		
        var error = layer.legend().msg;
        var url;
		dbg.log(error)
        switch(error){
            case '':
                url =layer.legend().url;
                if(url==''){error='No legend for this layer';}
				else{
					$("#legend_img_"+layer.id).attr("src",url);
				}
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
        */

		//dbg.log(url);
	},
	
    //functions that change the widget
    _layerAdded: function(widget, layer) {
        var self = this;
		/*
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
        */
		
        var isWMS = false;
		var icona = "ico_map";
        if(layer.isVector || layer.olLayer.layers)
		{
			isWMS = true;
			icona = "ico_vector";
		}
		if(layer.noDelete) icona += "_lock";
        icona += ".png";
		
		//dbg.log("------"+this.element.attr("id"));
		
        //var tree = $("#"+this.element.attr("id")).dynatree("getTree");
		var tree = $("#layman_tree").dynatree("getTree");
		
		// cerca la cartella padre
		//var node = tree.getNodeByKey("_2");
		var cartellaEsiste = false;
		var nomeCartella = layer.options.cartellaToc;
		
		//dbg.log(nomeCartella +" ## "+layer.label);
		
		//while (!cartellaEsiste) {
		//	
		//	//dbg.log(nomeCartella +" ## "+layer.label);
		//	//dbg.log(" ------- ");
		//	
		//	// scorro le cartelle esistenti per vedere se c'è
		//	//alert(layer.options.cartellaToc);
		//	if (nomeCartella == undefined) {
		//		nomeCartella = null;
		//	}
		//	
		//	if(nomeCartella != null ){
		//		
		//		$("#layman_tree").dynatree("getRoot").visit(function(nd){
		//			if(nd.data.title == nomeCartella)
		//			{
		//				node = nd;
		//				cartellaEsiste = true;
		//			}
		//				
		//		});
		//		
		//		// se non c'è la creo
		//		if (!cartellaEsiste) {
		//			self._addFolder(nomeCartella);
		//			if (nomeCartella.indexOf("||") != -1 ) {
		//				nomeCartella = nomeCartella.split("||");
		//				nomeCartella = nomeCartella[nomeCartella.length-1];
		//				//dbg.log(">>>>>>>>>>>> "+nomeCartella);
		//			}
		//		}
		//	}else cartellaEsiste = true;
		//}
        //var node = tree.getNodeByKey("_2");
        
		
		// comincia ad assegnarlo alla radice
		//var node = $("#layman_tree").dynatree("getRoot");
		
        var node = $("#layman_tree").dynatree("getRoot");
		
		// cerca la cartella corrispondente
		if (nomeCartella!=null) {
			nomeCartella = nomeCartella.split("||");
			for(var k=0; k<nomeCartella.length; k++)
			{
				node.visit(function(nd)
				{
					//dbg.log(nd.data.title);
					if(nd.data.title == nomeCartella[k])
						node = nd;
				});
				//dbg.log("-------");
			}
		}
		
        var before = null;
		
		if(node.getChildren()!= null)
		    before = node.getChildren()[0];
		
		
		//if(nomeCartella != null)
		//{
			var childNode = node.addChild(
				{
					title: layer.label,
					icon: icona,
					select: layer.visible(),
					//select: true,
					layer: layer
					//noLink: true
				}
				,before
			);
		//}
		//else{
		//	
		//	var childNode = $("#layman_tree").dynatree("getRoot").addChild(
		//		{
		//			title: layer.label,
		//			icon: icona,
		//			select: layer.visible(),
		//			//select: true,
		//			layer: layer
		//			//noLink: true
		//		}
		//	);
		//}
		
		$(childNode.li).attr("layindex",layer.id);
		
		// legenda
        $.tmpl('csiLayerManagerSimpleTreeLegendIMG',{
           layer_title: layer.label,
		   id: layer.id
        }).appendTo($("#box_legenda"));
		
		// click al tasto destro sul livello
		$(childNode.li).bind('contextmenu', function(e){
			e.preventDefault();
			var layindex = $(e.currentTarget).attr("layindex");
			var lays = self.map.layers();
			
			//dbg.log(e);
			
			for(var i in lays)
			{
				if(lays[i].id == layindex)
				{
					//alert("remove "+lays[i].id);
					//lays[i].remove()
					
					
					// TODO: disattivare i tasti in base alla voce cliccata
					
					
					activeLayerElement = lays[i];
					
					nodoCliccato = null;
					
					$("#layman_tree").dynatree("getRoot").visit(function(node){
						if (node.data.title == activeLayerElement.label) {
							nodoCliccato = node;
						}
					});	
					
					/*
					var tree = $("#layman_tree").dynatree("getTree");
					for(var i=0; i<tree.getRoot().childList.length; i++) {
						
						dbg.log(tree.getRoot().childList[i].data.title);
						
						if (tree.getRoot().childList[i].data.title == activeLayerElement.label) {
							node = tree.getRoot().childList[i];
						}
					}
					*/
					//dbg.log(nodoCliccato.data.isFolder);
					
					if (nodoCliccato != null) {
						if(nodoCliccato.data.isFolder){
							$("#laymanprop_name").show();
							$("#laymanprop_infowms").hide();
							$("#lmp_dir_name").val(nodoCliccato.data.title);
						}else{
							$("#laymanprop_name").hide();
							$("#laymanprop_infowms").show();
							$("#lmp_wms_title").val(nodoCliccato.data.title);
							activeLayerElement = nodoCliccato.data.layer;
						}					
					}
					
					//var activeNode = $("#layman_tree").dynatree("getActiveNode");
					//dbg.log(nodes[ik].data.title)
					//if (confirm(lng.get("LAYMANTREE_CONFIRM")+"\n"+activeNode.data.title)) {
					//	self._remove(activeNode.data.layer)
					//	activeNode.remove();
					//}
					
					$("#LMST_menu").show();
					$("#LMST_menu").css("left",e.clientX+"px");
					$("#LMST_menu").css("top",e.clientY+"px");
					
				}
			}
			return false;
		});
		
		self._refreshLegend(widget, layer);
		
		// PER SALVARE LO STATO DELLA TOC NEI COOKIES
		salvaTematismiNeiCookies();

		
    },

    
    // TODO: VERIFICARE SE LE FUNZIONI SOTTO SERVONO ANCORA O POSSONO ESSERE ALMENO SEMPLIFICATE
    
    _layerRemoved: function(widget, id) {
        var control = $("#mq-layermanager-element-"+id);
        control.fadeOut(function() {
            $(this).remove();
        });
		// PER SALVARE LO STATO DELLA TOC NEI COOKIES
		salvaTematismiNeiCookies();
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
		// PER SALVARE LO STATO DELLA TOC NEI COOKIES
		salvaTematismiNeiCookies();
    },
	// ---------------------------------------------------------------------

//    _layerVisible: function(widget, layer) {
//        /*

//        var layerElement =
//        widget.element.find('#mq-layermanager-element-'+layer.id);
//        var checkbox =
//        layerElement.find('.mq-layermanager-element-vischeckbox');
//        checkbox[0].checked = layer.visible();
//        //update the opacity slider as well
//        var slider = layerElement.find('.mq-layermanager-element-slider');
//        var value = layer.visible()?layer.opacity()*100: 0;
//        slider.slider('value',value);
//

//        //update legend image
//        layerElement.find('.mq-layermanager-element-legend img').css(
//            {visibility:layer.visible()?'visible':'hidden'});
//            */
//
//		// PER SALVARE LO STATO DELLA TOC NEI COOKIES
//		salvaTematismiNeiCookies();
//
//    },
	
	
	_layerVisible: function(widget, layer, nonPropagare) {
		
		if (nonPropagare == null)
		{
			var layerElement = widget.element.find('#mq-layermanager-element-'+layer.id);
			
			//var checkbox = layerElement.find('.mq-layermanager-element-vischeckbox');
			//checkbox[0].checked = layer.visible();        
			
			// spegne gli altri TMS e riproietta la mappa
			if (layer.visible() && (layer.options.type == "csi_tms"
								|| layer.options.type == "tms"
								|| layer.options.type == "google"
								|| layer.options.type == "osm"))
			{
				cambiaProiezione(layer.olLayer);
				
				// cicla sugli altri livelli per cercare i cataloghi e spegne quelli concorrenti
				var lay = mq.layers()
				for (var i=0; i<lay.length; i++)
				{
					if (lay[i].id != layer.id && (lay[i].options.type == "csi_tms"
													|| lay[i].options.type == "tms"
													|| lay[i].options.type == "google"
													|| lay[i].options.type == "osm")
						)
					{
						// spegne il livello mapquery concorrente
						mq.layersList[lay[i].id].visible(false)
						//// deseleziona nell'albero
						$("#layman_tree").dynatree("getRoot").visit(function(node)
						{
							if(lay[i].label == node.data.title)
							{
								//dbg.log("OFF: "+node.data.title);
								node.select(false);
							}
						});
						//dbg.log(lay[i]);
					}
				}
			}
			
			// PER SALVARE LO STATO DELLA TOC NEI COOKIES
			salvaTematismiNeiCookies();
		}

    },

	// ---------------------------------------------------------

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
		
        $.each(map.layers().reverse(), function(){
           widget._refreshLegend(lmElement, this);
        });
		
        /*
        lmElement.empty();
        $.each(map.layers().reverse(), function(){
           widget._layerAdded(lmElement, this);
        });
        */
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
    },
	
	arrayCartelleDaCreare : [],
	
	initTree: function(ml, INnomeCartella) {
		this.initTreeFolders(ml, INnomeCartella);

		this.arrayCartelleDaCreare.reverse();
		//dbg.log(this.arrayCartelleDaCreare);
		
		for (var i=0; i<this.arrayCartelleDaCreare.length; i++)
			this._addFolder(this.arrayCartelleDaCreare[i]);
			
	},
	
	initTreeFolders: function(ml, INnomeCartella){
		
		var nomeCartella = "";
		if (INnomeCartella!=null) nomeCartella = INnomeCartella;
		
		for (var i=0; i<ml.length; i++)
		{
			if (ml[i].type.toUpperCase() == "FOLDER")
			{
				if (nomeCartella!="") nomeCartella += "||"+ml[i].label;
				else nomeCartella = ml[i].label;
				
				if (ml[i].mapLayers == undefined)
				{
					// cartella vuota
					this.arrayCartelleDaCreare.push(nomeCartella);
				}
				else{
					// cartella con contenuto
					this.initTreeFolders(ml[i].mapLayers, nomeCartella);
			}
			}else{
				// non cartella
				this.arrayCartelleDaCreare.push(nomeCartella);
			}
			nomeCartella = "";
			if (INnomeCartella!=null) nomeCartella = INnomeCartella
		}
		
		var uniqueNames = [];
		$.each(this.arrayCartelleDaCreare, function(i, el){
		    if($.inArray(el, uniqueNames) === -1) uniqueNames.push(el);
		});
		
		//this.arrayCartelleDaCreare.reverse();
		
		//this.arrayCartelleDaCreare = uniqueNames;
	}
	
});
})(jQuery);

function cambiaProiezione(layerName) {

	//dbg.log("cambiaProiezione ---->"+layername);
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

// #############################################################################
// #############################################################################
// #############################################################################

// STRUMENTI PER ESPORTARE LA TOC (editor Veronica)
	function exportTOC()
	{
		albero = $("#layman_tree").dynatree("getTree");
		var ch = albero.getRoot().getChildren();
		ML = new Array();
		for (var i=0; i<ch.length; i++)
		{
			ML.push(makeMapLayer(ch[i]));
		}
		var strJSON = JSON.stringify(ML);
		strJSON = strJSON.replace(/,/g, ",<br>");
		$("#JSONexp").html(strJSON);
	}
  
	function makeMapLayer(treeNode)
	{
		//dbg.log(treeNode);
		tn = treeNode
		
		var nML = new MapLayer();
		nML.label = treeNode.data.title;
		if (treeNode.data.isFolder) {
			nML.type = "folder";
		}else{
			//dbg.log(treeNode.data.layer.options.type);
			switch (treeNode.data.layer.options.type) {
				case "csi_tms":
					nML.type = "TMS_CSI";
					nML.visibility = treeNode.data.layer.visible();
					nML.opacity = treeNode.data.layer.opacity();
					nML.locked = treeNode.data.layer.noDelete;
					
					nML.datasource = treeNode.data.layer.options.url;
					nML.format = treeNode.data.layer.options.format;
				break;
				case "csi_wms":
					nML.type = "CSI_WMS";
					nML.visibility = treeNode.data.layer.visible();
					nML.opacity = treeNode.data.layer.opacity();
					nML.locked = treeNode.data.layer.noDelete;
					
					nML.datasource = treeNode.data.layer.options.url;
					nML.layers = treeNode.data.layer.options.layers;
					nML.legend = treeNode.data.layer.options.legend;
				break;
				case "osm":
					nML.type = "OSM";
					nML.visibility = treeNode.data.layer.visible();
					nML.opacity = treeNode.data.layer.opacity();
					nML.locked = treeNode.data.layer.noDelete;
				break;
				case "google":
					nML.type = "GOOGLE";
					nML.visibility = treeNode.data.layer.visible();
					nML.opacity = treeNode.data.layer.opacity();
					nML.locked = treeNode.data.layer.noDelete;
					
					nML.locked = treeNode.data.layer.noDelete;
					nML.view = treeNode.data.layer.options.view;
				break;
			
			}
			nML.type = treeNode.data.layer.options.type;
		}
		
		
		
		var ch = treeNode.getChildren();
		if (ch!=null) {
			for (var i=0; i<ch.length; i++)
				nML.mapLayers.push(makeMapLayer(ch[i]));
		}
		if (nML.mapLayers.length==0) {
			delete nML.mapLayers;
		}
		return nML;
	}
  
  // OGGETTO PER IL JSON
	function MapLayer()
	{
		this.type = "";
		this.label = "";
		this.mapLayers = new Array();
	}
  
  
  