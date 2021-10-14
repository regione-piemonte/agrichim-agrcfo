
(function($) {
$.template('csiLangSelector',
    '<div class="mq-langSelector ui-widget-content ">'+
        '<button class="langSelectoButton" id="langSelector_IT" >IT</button>'+
        '<button class="langSelectoButton" id="langSelector_EN" >EN</button>'+
        '<button class="langSelectoButton" id="langSelector_AR" >AR</button>'+
        '<button class="langSelectoButton" id="langSelector_ZH" >ZH</button>'+
    '</div>');


$.widget("mapQuery.csiLangSelector", {
    options: {
    },
    _create: function() {
        
        this.element.addClass('ui-widget  ui-helper-clearfix ui-corner-all');
        
        $.tmpl('csiLangSelector',{}).prependTo(this.element);
        
        $(".langSelectoButton").button({})
            .click(function( event )
            {
                event.preventDefault();
                
                var newLang = this.id.split("_")[1];
                
                var nUrl = ""+window.location;
                var urlSplitted = nUrl.split("?");
                if(urlSplitted.length>0)
                    nUrl = urlSplitted[0]+"?lang="+newLang;
                else
                    nUrl += "?lang="+newLang;
                
                window.location.assign(nUrl);
            }
        );
    },
    
    _destroy: function() {
        this.element.removeClass(' ui-widget ui-helper-clearfix ui-corner-all').empty();
    }

});
})(jQuery);
