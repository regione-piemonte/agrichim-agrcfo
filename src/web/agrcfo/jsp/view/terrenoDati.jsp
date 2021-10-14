<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*,java.util.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/terrenoDati.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="beanColtura"
     scope="application"
     class="it.csi.agrc.BeanColtura"/>

<jsp:useBean
     id="coltura"
     scope="page"
     class="it.csi.agrc.Coltura">
    <%
      coltura.setDataSource(dataSource);
      coltura.setAut(aut);
    %>
</jsp:useBean>

<jsp:useBean
     id="campioneTerrenoDati"
     scope="page"
     class="it.csi.agrc.CampioneTerrenoDati">
    <%
      campioneTerrenoDati.setDataSource(dataSource);
      campioneTerrenoDati.setAut(aut);
      campioneTerrenoDati.setIdRichiesta(aut.getIdRichiestaCorrente());
    %>
</jsp:useBean>

<!--
  Il file che includo serve a gestire gli errori dovuti al funzionamento non
  corretto del javascript
-->
<%@ include file="/jsp/view/problemiJavascript.inc" %>

<%
   /**
   * Per prima cosa devo vedere se sono qui perché c'e stato un errore. In caso
   * contrario controllo se il parametro colturaAttuale è valorizzato.
   * Se è valorizzato vuol dire che sono in questa pagina perché è stato
   * modificato il valore della combo relativa. Altrimenti se non è valorizzato
   * significa che sono appena arrivato in questa pagina, quindi devo
   * semplicemente controllare se sono in modifica (fase>4) o inserimento
   **/

   String idColturaAttuale = null,
          idColturaPrevista = null,
          idProfondita = null,
          nuovoImpianto = null,
          specieAttuale = null,
          speciePrevista = null,
          idVarieta = null,
          idInnesto = null,
          annoImpianto = null,
          idSistemaAllevamento = null,
          produzioneQha = null,
          superficieAppezzamento = null,
          giacitura = null,
          idEsposizione = null,
          scheletro = null,
          percentualePietre = null,
          stoppie = null,
          tipoConcimazione = null,
          idConcime = null,
          idLavorazioneTerreno = null,
          idIrrigazione = null,
          codiceModalitaColtivazione = null;

   /**
   * La variabile seguente mi permette di sapere se il valore di idColturaAttuale
   * è valido oppure se non è valido perché è stata ricaricata la pagina dopo
   * aver modificato la classe della coltura
   */
   boolean specieValida=false;


   idColturaAttuale = request.getParameter("idColturaAttuale");

   if ((idColturaAttuale!=null && Utili.isNumber(idColturaAttuale)) || errore!=null)
   {
     /**
     *  La combo è stata ricaricata quindi devo leggere anche gli altri
     *  parametri
     */
      if(Utili.isNumber(request.getParameter("idColturaPrevista")))
    	  idColturaPrevista = request.getParameter("idColturaPrevista");
      idProfondita = request.getParameter("idProfondita");
      nuovoImpianto = request.getParameter("nuovoImpianto");
      specieAttuale = request.getParameter("specieAttuale");
      speciePrevista = request.getParameter("speciePrevista");
      idVarieta = request.getParameter("idVarieta");
      idInnesto = request.getParameter("idInnesto");
      annoImpianto = request.getParameter("annoImpianto");
      idSistemaAllevamento = request.getParameter("idSistemaAllevamento");
      produzioneQha = request.getParameter("produzioneQha");
      superficieAppezzamento = request.getParameter("superficieAppezzamento");
      giacitura = request.getParameter("giacitura");
      idEsposizione = request.getParameter("idEsposizione");
      scheletro = request.getParameter("scheletro");
      percentualePietre = request.getParameter("percentualePietre");
      stoppie = request.getParameter("stoppie");
      tipoConcimazione = request.getParameter("tipoConcimazione");
      idConcime = request.getParameter("idConcime");
      idLavorazioneTerreno = request.getParameter("idLavorazioneTerreno");
      idIrrigazione = request.getParameter("idIrrigazione");
      codiceModalitaColtivazione = request.getParameter("codiceModalitaColtivazione");
   }
   else
   {
     if (aut.getFase()>4)
     {
        /**
        *  Devo leggere i dati dal database
        * */
        campioneTerrenoDati.select();
        idColturaAttuale = campioneTerrenoDati.getIdColturaAttuale();
        idColturaPrevista = campioneTerrenoDati.getIdColturaPrevista();
        idProfondita = campioneTerrenoDati.getIdProfondita();
        nuovoImpianto = campioneTerrenoDati.getNuovoImpianto();
        specieAttuale = campioneTerrenoDati.getSpecieAttuale();
        speciePrevista = campioneTerrenoDati.getSpeciePrevista();
        idVarieta = campioneTerrenoDati.getIdVarieta();
        idInnesto = campioneTerrenoDati.getIdInnesto();
        annoImpianto = campioneTerrenoDati.getAnnoImpianto();
        idSistemaAllevamento = campioneTerrenoDati.getIdSistemaAllevamento();
        produzioneQha = campioneTerrenoDati.getProduzioneQha();
        superficieAppezzamento = campioneTerrenoDati.getSuperficieAppezzamento();
        giacitura = campioneTerrenoDati.getGiacitura();
        idEsposizione = campioneTerrenoDati.getIdEsposizione();
        scheletro = campioneTerrenoDati.getScheletro();
        percentualePietre = campioneTerrenoDati.getPercentualePietre();
        stoppie = campioneTerrenoDati.getStoppie();
        tipoConcimazione = campioneTerrenoDati.getTipoConcimazione();
        idConcime = campioneTerrenoDati.getIdConcime();
        idLavorazioneTerreno = campioneTerrenoDati.getIdLavorazioneTerreno();
        idIrrigazione = campioneTerrenoDati.getIdIrrigazione();
        codiceModalitaColtivazione = campioneTerrenoDati.getCodiceModalitaColtivazione();
     }
   }

   /**
    * Devo impostare correttamente i dati nulli (sia che siano arrivati dal
    * database, sia che mi siano stati passati come parametri)
    * */
   if (idColturaAttuale==null) idColturaAttuale="-1";
   if (idColturaPrevista==null) idColturaPrevista="-1";
   if (idProfondita==null) idProfondita="-1";
   if (nuovoImpianto==null) nuovoImpianto="";
   if (specieAttuale==null) specieAttuale="-1";
   if (speciePrevista==null) speciePrevista="-1";
   if (idVarieta==null) idVarieta="-1";
   if (idInnesto==null) idInnesto="-1";
   if (annoImpianto==null) annoImpianto="";
   if (idSistemaAllevamento==null) idSistemaAllevamento="-1";

   if (produzioneQha==null) produzioneQha="";
   else produzioneQha=produzioneQha.replace('.',',');

   if (superficieAppezzamento==null) superficieAppezzamento="-1";
   if (giacitura==null) giacitura="";
   if (idEsposizione==null) idEsposizione="-1";
   if (scheletro==null) scheletro="";
   if (percentualePietre==null) percentualePietre="";
   if (stoppie==null) stoppie="";
   if (tipoConcimazione==null) tipoConcimazione="-1";
   if (idConcime==null) idConcime="-1";
   if (idLavorazioneTerreno==null) idLavorazioneTerreno="-1";
   if (idIrrigazione==null) idIrrigazione="-1";
   if (codiceModalitaColtivazione==null) codiceModalitaColtivazione="-1";

   Vector cod=new Vector(),desc=new Vector();
   String codStr[],descStr[];

   /**
    * Carico la combo delle Profondità prelievo
    **/
   codStr=beanColtura.getCodProfonditaPrelievo();
   descStr=beanColtura.getDescProfonditaPrelievo();
   for(int i=0;i<codStr.length;i++)
   {
     if (idProfondita.equals(codStr[i]))
       templ.set("idProfondita.selected","selected");
     else
       templ.set("idProfondita.selected","");
     templ.set("idProfondita.codice",codStr[i]);
     templ.set("idProfondita.descrizione",descStr[i]);
   }

    /**
    * Carico i dati di tutte le classi coltura nelle combo delle
    * coltura precedenti (o in atto) e previste
    **/
   codStr=beanColtura.getCodColtura();
   descStr=beanColtura.getDescColtura();

   /**
    * Memorizzo in questa variabile la descrizione coltura prevista.
    * Questo perché solo se questa coltura è uguale a :
    * fruttiferi o specie forestali o vite
    * devo attivare la casella NUOVO IMPIANTO
    * */
   String colturaPrev=null;

   for(int i=0;i<codStr.length;i++)
   {
     if (idColturaAttuale.equals(codStr[i]))
       templ.set("idColturaAttuale.selected","selected");
     else
       templ.set("idColturaAttuale.selected","");
     templ.set("idColturaAttuale.codice",codStr[i]);
     templ.set("idColturaAttuale.descrizione",descStr[i]);
     if (idColturaPrevista.equals(codStr[i]))
     {
       colturaPrev=descStr[i];
       templ.set("idColturaPrevista.selected","selected");
     }
     else
       templ.set("idColturaPrevista.selected","");
     templ.set("idColturaPrevista.codice",codStr[i]);
     templ.set("idColturaPrevista.descrizione",descStr[i]);
   }

   boolean disabledNuovoImpianto=false;

   if ("fruttiferi".equalsIgnoreCase(colturaPrev)
       || "specie forestali".equalsIgnoreCase(colturaPrev)
       || "vite".equalsIgnoreCase(colturaPrev))
   {
     templ.bset("disabledNuovoImpianto","");
   }
   else
   {
     disabledNuovoImpianto=true;
     templ.bset("disabledNuovoImpianto","DISABLED");
   }


   /**
    *  Imposto il radion button del Nuovo impianto
    **/
   if ("N".equals(nuovoImpianto) && !disabledNuovoImpianto)
       templ.bset("checkedNo","checked");
   else
       templ.bset("checkedSi","checked");


   /**
   * Carico i dati di tutti le specie coltura (precedenti o in atto)
   * corrispondente alla classe coltura scelta
   */
   cod.clear();
   desc.clear();
   coltura.selectCodDescSpecie(Coltura.IMPOSTA_QUERY_SPECIE,cod,desc,idColturaAttuale);
   int size=cod.size();
   for (int i=0;i<size;i++ )
   {
     if (specieAttuale.equals(cod.get(i).toString()))
       templ.set("specieAttuale.selected","selected");
     else
       templ.set("specieAttuale.selected","");
     templ.set("specieAttuale.codice",(String)cod.elementAt(i));
     templ.set("specieAttuale.descrizione",(String)desc.elementAt(i));
   }
   /**
   * Carico i dati di tutti le specie coltura (previste)
   * corrispondente alla classe coltura scelta
   */
   cod.clear();
   desc.clear();
   coltura.selectCodDescSpecie(Coltura.IMPOSTA_QUERY_SPECIE,cod,desc,idColturaPrevista);
   size=cod.size();
   for (int i=0;i<size;i++ )
   {
     if (speciePrevista.equals(cod.get(i).toString()))
     {
       //Se sono qui vuol dire che ho trovato all'interno della combo una speciePrevista
       //corrispondente a quella memorizzata: quindi la speciePrevista memorizzata è
       //valida
       specieValida=true;
       templ.set("speciePrevista.selected","selected");
     }
     else
       templ.set("speciePrevista.selected","");
     templ.set("speciePrevista.codice",(String)cod.elementAt(i));
     templ.set("speciePrevista.descrizione",(String)desc.elementAt(i));
   }
   if (!specieValida) speciePrevista="-1";
   /**
   * Carico i dati di tutte le varietà di specie corrispondenti alla
   * speciePrevista scelta
   */
   cod.clear();
   desc.clear();
   coltura.selectCodDescSpecie(Coltura.IMPOSTA_QUERY_VARIETA,cod,desc,speciePrevista);
   size=cod.size();
   for (int i=0;i<size;i++ )
   {
     if (idVarieta.equals(cod.get(i).toString()))
       templ.set("idVarieta.selected","selected");
     else
       templ.set("idVarieta.selected","");
     templ.set("idVarieta.codice",(String)cod.elementAt(i));
     templ.set("idVarieta.descrizione",(String)desc.elementAt(i));
   }
   /**
    * Carico i dati di tutti gli innesti corrispondenti alla
    * speciePrevista scelta
    */
   cod.clear();
   desc.clear();
   coltura.selectCodDescSpecie(Coltura.IMPOSTA_QUERY_INNESTO,cod,desc,speciePrevista);
   size=cod.size();
   for (int i=0;i<size;i++ )
   {
     if (idInnesto.equals(cod.get(i).toString()))
       templ.set("idInnesto.selected","selected");
     else
       templ.set("idInnesto.selected","");
     templ.set("idInnesto.codice",(String)cod.elementAt(i));
     templ.set("idInnesto.descrizione",(String)desc.elementAt(i));
   }
   /**
    * Carico i dati di tutti i sistemi di allevamento corrispondenti alla
    * speciePrevista scelta
    */
   cod.clear();
   desc.clear();
   coltura.selectCodDescSpecie(Coltura.IMPOSTA_QUERY_ALLEVAMENTO,cod,desc,speciePrevista);
   size=cod.size();
   for (int i=0;i<size;i++ )
   {
     if (idSistemaAllevamento.equals(cod.get(i).toString()))
       templ.set("idSistemaAllevamento.selected","selected");
     else
       templ.set("idSistemaAllevamento.selected","");
     templ.set("idSistemaAllevamento.codice",(String)cod.elementAt(i));
     templ.set("idSistemaAllevamento.descrizione",(String)desc.elementAt(i));
   }
   /**
    * Carico il dato relativo agli anni dell'impianto
    **/
   templ.bset("annoImpianto",annoImpianto);
   /**
    * Carico la combo delle Superfici di appezzamento
    **/
   codStr=beanColtura.getCodSuperficie();
   descStr=beanColtura.getDescSuperficie();
   for(int i=0;i<codStr.length;i++)
   {
     if (superficieAppezzamento.equals(codStr[i]))
       templ.set("superficieAppezzamento.selected","selected");
     else
       templ.set("superficieAppezzamento.selected","");
     templ.set("superficieAppezzamento.codice",codStr[i]);
     templ.set("superficieAppezzamento.descrizione",descStr[i]);
   }
   /**
    * Carico il dato relativo alla Produzione stimata (q/ha)
    **/
   templ.bset("produzioneQha",produzioneQha);
   /**
    *  Imposto il radion button della Giacitura
    **/
   if ("A".equals(giacitura))
         templ.bset("checkedAcclive","checked");
   else
         templ.bset("checkedPiano","checked");
   /**
     * Carico la combo delle direzioni di esposizione del terreno rispetto al
     * sole
     **/
  codStr=beanColtura.getCodEsposizione();
  descStr=beanColtura.getDescEsposizione();
  for(int i=0;i<codStr.length;i++)
  {
    if (idEsposizione.equals(codStr[i]))
      templ.set("idEsposizione.selected","selected");
    else
      templ.set("idEsposizione.selected","");
    templ.set("idEsposizione.codice",codStr[i]);
    templ.set("idEsposizione.descrizione",descStr[i]);
   }
   /**
    *  Imposto il radion button della presenza di pietre o ghiaie
    **/
   if ("S".equals(scheletro))
         templ.bset("checkedPietre","checked");
   else
         templ.bset("checkedGhiaie","checked");
   /**
    * Carico il dato relativo alla Percentuale presenza pietre
    **/
   templ.bset("percentualePietre",percentualePietre);
   /**
    *  Imposto il radion button dell' Interramento stoppie
    **/
   if ("S".equals(stoppie))
         templ.bset("checkedStoppieSi","checked");
   else
         templ.bset("checkedStoppieNo","checked");
   /**
    * Carico i dati riguardanti le concimazione organiche
    */
   codStr=beanColtura.getCodConcimazioneOrganica();
   descStr=beanColtura.getDescConcimazioneOrganica();
   for(int i=0;i<codStr.length;i++)
   {
     if (tipoConcimazione.equals(codStr[i]))
       templ.set("tipoConcimazione.selected","selected");
     else
       templ.set("tipoConcimazione.selected","");
     templ.set("tipoConcimazione.codice",codStr[i]);
     templ.set("tipoConcimazione.descrizione",descStr[i]);
   }
   if ("A".equals(tipoConcimazione.toUpperCase())
                    ||
       "S".equals(tipoConcimazione.toUpperCase())
      )
   {
     /**
      * Carico i dati riguardanti il concime utilizzato
      */
     codStr=beanColtura.getCodConcimazione();
     descStr=beanColtura.getDescConcimazione();
     for(int i=0;i<codStr.length;i++)
     {
       if (idConcime.equals(codStr[i]))
         templ.set("idConcime.selected","selected");
       else
         templ.set("idConcime.selected","");
       templ.set("idConcime.codice",codStr[i]);
       templ.set("idConcime.descrizione",descStr[i]);
     }
   }
   /**
    * Carico i dati riguardanti lo Stato del terreno
    */
   codStr=beanColtura.getCodLavorazioneTerreno();
   descStr=beanColtura.getDescLavorazioneTerreno();
   for(int i=0;i<codStr.length;i++)
   {
     if (idLavorazioneTerreno.equals(codStr[i]))
       templ.set("idLavorazioneTerreno.selected","selected");
     else
       templ.set("idLavorazioneTerreno.selected","");
     templ.set("idLavorazioneTerreno.codice",codStr[i]);
     templ.set("idLavorazioneTerreno.descrizione",descStr[i]);
   }
   /**
    * Carico i dati riguardanti il Tipo di irrigazione effettuata
    */
   codStr=beanColtura.getCodIrrigazione();
   descStr=beanColtura.getDescIrrigazione();
   for(int i=0;i<codStr.length;i++)
   {
     if (idIrrigazione.equals(codStr[i]))
       templ.set("idIrrigazione.selected","selected");
     else
       templ.set("idIrrigazione.selected","");
     templ.set("idIrrigazione.codice",codStr[i]);
     templ.set("idIrrigazione.descrizione",descStr[i]);
   }
   /**
    * Carico i dati riguardanti le Modalità di coltivazione
    */
   codStr=beanColtura.getCodModalitaColtivazione();
   descStr=beanColtura.getDescModalitaColtivazione();
   for(int i=0;i<codStr.length;i++)
   {
     if (codiceModalitaColtivazione.equals(codStr[i]))
       templ.set("codiceModalitaColtivazione.selected","selected");
     else
       templ.set("codiceModalitaColtivazione.selected","");
     templ.set("codiceModalitaColtivazione.codice",codStr[i]);
     templ.set("codiceModalitaColtivazione.descrizione",descStr[i]);
   }
%>
<%= templ.text() %>

