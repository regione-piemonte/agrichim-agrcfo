
// this Handler.Box will intercept the shift-mousedown
// before Control.MouseDefault gets to see it


var ctrl_shiftBBOX = new OpenLayers.Control({
              title:'Custom Button'
            , text: 'Button (shift)'
            , icon: 'ui-icon-extlink'
            ,CLASS_NAME: "OpenLayers.Control.CustomButton" // OBBLIGATORIO
});

/*
OpenLayers.Util.extend(ctrl_shiftBBOX, {
    draw: function () {
        this.box = new OpenLayers.Handler.Box( control,
            {"done": this.notice},
            {keyMask: OpenLayers.Handler.MOD_SHIFT});
        this.box.activate();
    },
    notice: function (bounds) {
        OpenLayers.Console.userError(bounds);
    },
});
*/