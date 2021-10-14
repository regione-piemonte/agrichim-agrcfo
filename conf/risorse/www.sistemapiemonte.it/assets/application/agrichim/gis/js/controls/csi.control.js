function csiExtendControl(control,uiClass){
  OpenLayers.Util.extend(control, {
  		activate: function () {
		  
      OpenLayers.Element.addClass(
        this.panel_div,
        uiClass
       );
      className = control.CLASS_NAME.split('.')[2]
      //dbg.log('activate: ' + className)
	  
	  // AGRICHIM
	  disattivaStrumentoCoordinate()
	  
      if ( typeof OpenLayers.Control[className] == 'function' ) { 
        OpenLayers.Control[className].prototype.activate.apply(this, arguments);
      } else {
        OpenLayers.Control.prototype.activate.apply(this, arguments);
      }

    },
     deactivate: function () {
	  
      OpenLayers.Element.removeClass(
                this.panel_div,
                uiClass
              );
      className = control.CLASS_NAME.split('.')[2]
      //dbg.log('deactivate: ' + className)
      if ( typeof OpenLayers.Control[className] == 'function' ) { 
        OpenLayers.Control[className].prototype.deactivate.apply(this, arguments);
      } else {
        OpenLayers.Control.prototype.deactivate.apply(this, arguments);
      }
    }
  });
}


// per gestire il controllo attivo in presenza di più pannelli
var multiPanelActivateControl = function (control) {
  if (!this.active) { return false; }
  if (control.type == OpenLayers.Control.TYPE_BUTTON) {
    control.trigger();
    this.redraw();
    return;
  }
  if (control.type == OpenLayers.Control.TYPE_TOGGLE) {
    if (control.active) {
        control.deactivate();
    } else {
        control.activate();
    }
    this.redraw();
    return;
  }
  
  var panelList = control.map.getControlsByClass("OpenLayers.Control.Panel");
  for (var j=0, pLen=panelList.length; j<pLen; j++) {
    var currPanel = panelList[j];
  
    var c;
    for (var i=0, len=currPanel.controls.length; i<len; i++) {
      c = currPanel.controls[i];
      if (c != control &&
         (c.type === OpenLayers.Control.TYPE_TOOL || c.type == null)) {
          c.deactivate();
      }
    }
  }
  control.activate();
  
}