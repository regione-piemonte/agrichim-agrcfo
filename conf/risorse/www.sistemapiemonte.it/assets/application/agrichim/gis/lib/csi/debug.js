var dbg = {
    
    debugModeOn: false,
    
    activate: function (active) {
        this.debugModeOn = (active==true);
    }
    ,
    log: function (v) {
        if(this.debugModeOn) console.log(v);
    }
    
}


