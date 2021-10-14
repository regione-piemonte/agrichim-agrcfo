var lng = {
    
    lang: "IT",
    
    // setta il linguaggio in uso (default IT - italiano)
    setLanguage: function (language) {
        if(language)
        {
            switch(language.toUpperCase())
            {
                // inglese
                //case "EN":
                //    this.lang = "EN";
                //break;
                
                // arabo
                //case "AR":
                //    this.lang = "AR";
                //break;
                
                // cinese
                //case "ZH":
                //    this.lang = "ZH";
                //break;
                
                // italiano
                default:
                    this.lang = "IT";
            }
        }
    },
    
    // restituisce la stringa nel linguaggio corrente
    get: function (key) {
        
        if(this.langStrings[this.lang][key])
            return this.langStrings[this.lang][key];
        return "";
    },
    
    // Stringhe tradotte
    langStrings:
    {
        IT: langTranslationsIT
        //,EN: langTranslationsEN
        //,ZH: langTranslationsZH
        //,AR: langTranslationsAR
    }
    
}
