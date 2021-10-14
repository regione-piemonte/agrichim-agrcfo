// per la gestione dei TMS CSI
// --------------------------------------------------------------------------
function get_url_CSI(bounds)
{
    var res = this.map.getResolution();
    var x = Math.round ((bounds.left - this.maxExtent.left) / (res * this.tileSize.w));
    var y = Math.round ((this.maxExtent.top - bounds.top) / (res * this.tileSize.h));
    var z = this.map.getZoom();
    // start patch: find current layer resolution shift
    if (this.resolutions && this.serverResolutions && this.resolutions.length<this.serverResolutions.length)
    {
        var resCounter=0;
        var serverResCounter=0;
        for (var i=0; i<this.resolutions.length && this.resolutions[i]!=res; i++) {
            resCounter++;
        }
        for (var i=0; i<this.serverResolutions.length && this.serverResolutions[i]!=res; i++) {
            serverResCounter++;
        }
        z += serverResCounter-resCounter;
    }
    // end patch: find current layer resolution shift
    var limit = Math.pow(2, z);
    x = ((x % limit) + limit) % limit;
    y = limit - y - 1;
    var path = z + "/" + x + "/" + y + "." + this.type;
    var url = this.url;
    var t;
    if (url instanceof Array)
        url = this.selectUrl(path, url);
    return url + path;
} 


// legge i parametri in GET dalla URL
// --------------------------------------------------------------------------
function getURLParameter(name) {
	return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
};

/*
 * Array.forEach
 * https://developer.mozilla.org/en-US/docs/JavaScript/Reference/Global_Objects/Array/forEach?redirectlocale=en-US&redirectslug=Core_JavaScript_1.5_Reference%2FObjects%2FArray%2FforEach#Compatibility
 */

 function addForeach(myArray) {
    if ( !myArray.forEach ) {
        myArray.forEach = function(fn, scope) {
          for(var i = 0, len = this.length; i < len; ++i) {
            fn.call(scope, this[i], i, this);
          }
        }
    }
 };