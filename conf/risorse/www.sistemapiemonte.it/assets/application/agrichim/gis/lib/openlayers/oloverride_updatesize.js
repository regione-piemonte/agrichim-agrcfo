/*
 * per inizializzare le mappe di OL anche in div non visibili
*/
OpenLayers.Map.prototype.updateSize = function()
{
     // the div might have moved on the page, also
    this.events.clearMouseCache();
    var newSize = this.getCurrentSize();
    var oldSize = this.getSize();
    if (oldSize == null)
        this.size = oldSize = newSize;
   
    if (!newSize.equals(oldSize))
    {
        // store the new size
        this.size = newSize;
        
        //notify layers of mapresize
        for(var i=0, len=this.layers.length; i<len; i++)
        {
            this.layers[i].onMapResize();                
            if (newSize && !isNaN(newSize.h) && !isNaN(newSize.w))
            {
                this.events.clearMouseCache();
                var oldSize = this.getSize();
                if (oldSize == null) this.size = oldSize = newSize;
                
                var center = this.getCenter();

                if (this.baseLayer != null && center != null)
                {
                    var zoom = this.getZoom();
                    this.zoom = null;
                    this.setCenter(center, zoom);
                    if (!newSize.equals(oldSize))
                    {
                        // store the new size
                        this.size = newSize;
                        
                        //notify layers of mapresize
                        for(var i=0, len=this.layers.length; i<len; i++)
                        {
                            this.layers[i].onMapResize();                
                        }
                        
                        var center = this.getCenter();
                        
                        if (this.baseLayer != null && center != null)
                        {
                            var zoom = this.getZoom();
                            this.zoom = null;
                            this.setCenter(center, zoom);
                        }
                    }
                }
            }
        }
    }
}
