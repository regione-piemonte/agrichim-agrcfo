// FULLSCREEN functions

function enterFullScreenMode(el){


	// risolve baco fuulscreen chrome
    		$("#div_mappa").css("width","100%");
    		$("#div_mappa").css("height","100%");  
			
	riassegnaPadreDialogs(el);
	// TODO: la classe può variare in base al progetto?
	$(".map").css("height","100%");
	
	// Supports most browsers and their versions.
    var requestMethod = el.requestFullScreen || el.webkitRequestFullScreen || el.mozRequestFullScreen || el.msRequestFullScreen;
	
    if (requestMethod) { // Native full screen.
        requestMethod.call(el);
    } else if (typeof window.ActiveXObject !== "undefined") { // Older IE.
		try {
			var wscript = new ActiveXObject("WScript.Shell");
			if (wscript !== null) {
				wscript.SendKeys("{F11}");
			}
		} catch(e) {
			custom_alert("In questo browser non puo' essere eseguita la modalita' tutto schermo.", "Attenzione!", "error");
		}
    }
	el.webkitRequestFullScreen(Element.ALLOW_KEYBOARD_INPUT);
}

function exitFullScreenMode(el) {
	
	// risolve baco fuulscreen chrome
    		$("#div_mappa").css("width",dimensioneMappaX);
    		$("#div_mappa").css("height",dimensioneMappaY); 
			
	riassegnaPadreDialogs($("body"));
	
	var requestMethod = el.cancelFullScreen||el.webkitCancelFullScreen||el.mozCancelFullScreen||el.exitFullscreen;
	if (requestMethod) { // cancel full screen.
		requestMethod.call(el);
	} else if (typeof window.ActiveXObject !== "undefined") { // Older IE.
		try {
			var wscript = new ActiveXObject("WScript.Shell");
			if (wscript !== null) {
				wscript.SendKeys("{F11}");
			}
		} catch(e) {
			custom_alert("In questo browser non puo' essere eseguita la modalita' tutto schermo.", "Attenzione!", "error");
		}
	}
}

function toggleFullScreenMode() {
	
	   var elem = document.getElementById("div_mappa");
	   var isInFullScreen = (document.fullScreenElement && document.fullScreenElement !== null) ||  (document.mozFullScreen || document.webkitIsFullScreen);

	   if (isInFullScreen) {
		   exitFullScreenMode(document);
	   } else {
		   enterFullScreenMode(elem);
	   }
	   return false;
}


function riassegnaPadreDialogs(padre) {
	
	// sposto tutti i dialog e li faccio diventare figli del contenitore della mappa
	var dialogs = $(".ui-dialog").detach();
	dialogs.appendTo(padre);
	dialogs.css("z-index",6000);
}

// 

   var changeHandler = function(){    
   	if(!window.fullScreen){
   		exitFullScreenMode(document);
   	}
   /*                                       
      //NB the following line requrires the libary from John Dyer                         
      var fs = window.fullScreenApi.isFullScreen();
      console.log("f" + (fs ? 'yes' : 'no' ));                               
      if (fs) {                                                              
        alert("In fullscreen, I should do something here");                  
      }                                                                      
      else {                                                                 
        alert("NOT In fullscreen, I should do something here");              
      }
     */                                                                      
   }                                                                         
   document.addEventListener("fullscreenchange", changeHandler, false);      
   document.addEventListener("webkitfullscreenchange", changeHandler, false);
   document.addEventListener("mozfullscreenchange", changeHandler, false);