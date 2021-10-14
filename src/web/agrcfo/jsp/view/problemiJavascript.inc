<%
  /**
    * Vado a leggere il parametro errore: se questo è valorizzato
    * significa che sono stato rispedito in questa pagina dal controller a
    * causa di un errore all'interno dei dati covuto al mancato funzionamento
    * del javascript
    **/
   String errore=request.getParameter("errore");
   String erroreCongruenzaSigmater=request.getParameter("erroreCongruenzaSigmater");
   String erroreCongruenzaSigmaterCatastali=request.getParameter("erroreCongruenzaSigmaterCatastali");
   if (errore==null)
    errore=(String)request.getAttribute("errore");
   if (errore!= null)
   {
     int err[]=Utili.idTokenize(errore);
     templ.newBlock("errore");
     for(int i=0;i<err.length;i++)
       templ.bset("errore"+err[i],"<img src=\"../layout/im/ko.gif\" alt=\"Campo errato\">",null);
   }
   else
     if (erroreCongruenzaSigmater!= null)
     {
       int err[]=Utili.idTokenize(erroreCongruenzaSigmater);
       templ.newBlock("erroreCongruenzaSigmater");
       for(int i=0;i<err.length;i++)
         templ.bset("errore"+err[i],"<img src=\"../layout/im/ko.gif\" alt=\"Campo errato\">",null);
     }
     else
     if (erroreCongruenzaSigmaterCatastali!= null)
     {
       int err[]=Utili.idTokenize(erroreCongruenzaSigmaterCatastali);
       templ.newBlock("erroreCongruenzaSigmaterCatastali");
       for(int i=0;i<err.length;i++)
         templ.bset("errore"+err[i],"<img src=\"../layout/im/ko.gif\" alt=\"Campo errato\">",null);
     }
  
%>
