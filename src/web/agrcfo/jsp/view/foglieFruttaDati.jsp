<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*,java.util.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/foglieFruttaDati.htm");
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
     id="campioneVegetaliFoglieFrutta"
     scope="page"
     class="it.csi.agrc.CampioneVegetaliFoglieFrutta">
    <%
      campioneVegetaliFoglieFrutta.setDataSource(dataSource);
      campioneVegetaliFoglieFrutta.setAut(aut);
      campioneVegetaliFoglieFrutta.setIdRichiesta(aut.getIdRichiestaCorrente());
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
   * contrario controllo se il parametro classeColtura è valorizzato.
   * Se è valorizzato vuol dire che sono in questa pagina perché è stato
   * modificato il valore della combo relativa. Altrimenti se non è valorizzato
   * significa che sono appena arrivato in questa pagina, quindi devo
   * semplicemente controllare se sono in modifica (fase>4) o inserimento
   **/
   String idColtura;
   String giornoCampionamento=null,
          meseCampionamento=null,
          annoCampionamento=null,
          superficieAppezzamento=null,
          giacitura=null,
          idEsposizione=null,
          scheletro=null,
          altitudineSlm=null,
          etaImpianto=null,
          idSpecie=null,
          altraSpecie=null,
          idVarieta=null,
          idInnesto=null,
          idSistemaAllevamento=null,
          altroAllevamento=null,
          sestoImpianto1=null,
          sestoImpianto2=null,
          unitaN=null,
          unitaP2O5=null,
          unitaK2O=null,
          unitaMg=null,
          letamazioneAnno=null,
          tipoConcimazione=null,
          idConcime=null,
          idStadioFenologico=null,
          codiceProduttivita=null,
          campioneTerreno=null,
          macinato=null;
   /**
   * La variabile seguente mi permette di sapere se il valore di idSpecie è
   * valido oppure se non è valido perché è stata ricaricata la pagina dopo
   * aver modificato la classe della coltura
   */
   boolean specieValida=false;
   idColtura=request.getParameter("idColtura");
   if (idColtura!=null || errore!=null)
   {
     /**
     *  La combo è stata ricaricata quindi devo leggere anche gli altri
     *  parametri
     */
     giornoCampionamento=request.getParameter("giornoCampionamento");
     meseCampionamento=request.getParameter("meseCampionamento");
     annoCampionamento=request.getParameter("annoCampionamento");
     superficieAppezzamento=request.getParameter("superficieAppezzamento");
     giacitura=request.getParameter("giacitura");
     idEsposizione=request.getParameter("idEsposizione");
     scheletro=request.getParameter("scheletro");
     altitudineSlm=request.getParameter("altitudineSlm");
     etaImpianto=request.getParameter("etaImpianto");
     idSpecie=request.getParameter("idSpecie");
     altraSpecie=request.getParameter("altraSpecie");
     idVarieta=request.getParameter("idVarieta");
     idInnesto=request.getParameter("idInnesto");
     idSistemaAllevamento=request.getParameter("idSistemaAllevamento");
     altroAllevamento=request.getParameter("altroAllevamento");
     sestoImpianto1=request.getParameter("sestoImpianto1");
     sestoImpianto2=request.getParameter("sestoImpianto2");
     unitaN=request.getParameter("unitaN");
     unitaP2O5=request.getParameter("unitaP2O5");
     unitaK2O=request.getParameter("unitaK2O");
     unitaMg=request.getParameter("unitaMg");
     letamazioneAnno=request.getParameter("letamazioneAnno");
     tipoConcimazione=request.getParameter("tipoConcimazione");
     idConcime=request.getParameter("idConcime");
     idStadioFenologico=request.getParameter("idStadioFenologico");
     codiceProduttivita=request.getParameter("codiceProduttivita");
     campioneTerreno=request.getParameter("campioneTerreno");
     macinato=request.getParameter("macinato");
   }
   else
   {
     if (aut.getFase()>4)
     {
       /**
       *  Devo leggere i dati dal database
       * */
       campioneVegetaliFoglieFrutta.select();
       giornoCampionamento=campioneVegetaliFoglieFrutta.getGiornoCampionamento();
       meseCampionamento=campioneVegetaliFoglieFrutta.getMeseCampionamento();
       annoCampionamento=campioneVegetaliFoglieFrutta.getAnnoCampionamento();
       superficieAppezzamento=campioneVegetaliFoglieFrutta.getSuperficieAppezzamento();
       giacitura=campioneVegetaliFoglieFrutta.getGiacitura();
       idEsposizione=campioneVegetaliFoglieFrutta.getIdEsposizione();
       scheletro=campioneVegetaliFoglieFrutta.getScheletro();
       altitudineSlm=campioneVegetaliFoglieFrutta.getAltitudineSlm();
       etaImpianto=campioneVegetaliFoglieFrutta.getEtaImpianto();
       idColtura=campioneVegetaliFoglieFrutta.getIdColtura();
       idSpecie=campioneVegetaliFoglieFrutta.getIdSpecie();
       altraSpecie=campioneVegetaliFoglieFrutta.getAltraSpecie();
       idVarieta=campioneVegetaliFoglieFrutta.getIdVarieta();
       idInnesto=campioneVegetaliFoglieFrutta.getIdInnesto();
       idSistemaAllevamento=campioneVegetaliFoglieFrutta.getIdSistemaAllevamento();
       altroAllevamento=campioneVegetaliFoglieFrutta.getAltroAllevamento();
       sestoImpianto1=campioneVegetaliFoglieFrutta.getSestoImpianto1();
       sestoImpianto2=campioneVegetaliFoglieFrutta.getSestoImpianto2();
       unitaN=campioneVegetaliFoglieFrutta.getUnitaN();
       unitaP2O5=campioneVegetaliFoglieFrutta.getUnitaP2O5();
       unitaK2O=campioneVegetaliFoglieFrutta.getUnitaK2O();
       unitaMg=campioneVegetaliFoglieFrutta.getUnitaMg();
       letamazioneAnno=campioneVegetaliFoglieFrutta.getLetamazioneAnno();
       tipoConcimazione=campioneVegetaliFoglieFrutta.getTipoConcimazione();
       idConcime=campioneVegetaliFoglieFrutta.getIdConcime();
       idStadioFenologico=campioneVegetaliFoglieFrutta.getIdStadioFenologico();
       codiceProduttivita=campioneVegetaliFoglieFrutta.getCodiceProduttivita();
       campioneTerreno=campioneVegetaliFoglieFrutta.getCampioneTerreno();
       macinato=campioneVegetaliFoglieFrutta.getMacinato();
     }
   }

   /**
    * Devo impostare correttamente i dati nulli (sia che siano arrivati dal
    * database, sia che mi siano stati passati come parametri)
    * */
   if (idColtura==null) idColtura="-1";
   if (giornoCampionamento==null) giornoCampionamento="";
   if (meseCampionamento==null) meseCampionamento="";
   if (annoCampionamento==null) annoCampionamento="";
   if (superficieAppezzamento==null) superficieAppezzamento="";
   if (giacitura==null) giacitura="";
   if (idEsposizione==null) idEsposizione="";
   if (scheletro==null) scheletro="";
   if (altitudineSlm==null) altitudineSlm="";
   if (etaImpianto==null) etaImpianto="";
   if (idSpecie==null) idSpecie="-1";
   if (altraSpecie==null) altraSpecie="";
   if (idVarieta==null) idVarieta="";
   if (idInnesto==null) idInnesto="";
   if (idSistemaAllevamento==null) idSistemaAllevamento="";
   if (altroAllevamento==null) altroAllevamento="";

   if (sestoImpianto1==null) sestoImpianto1="";
   else sestoImpianto1=sestoImpianto1.replace('.',',');
   if (sestoImpianto2==null) sestoImpianto2="";
   else sestoImpianto2=sestoImpianto2.replace('.',',');

   if (unitaN==null) unitaN="";
   if (unitaP2O5==null) unitaP2O5="";
   if (unitaK2O==null) unitaK2O="";
   if (unitaMg==null) unitaMg="";
   if (tipoConcimazione==null) tipoConcimazione="";
   if (idConcime==null) idConcime="";

   if (letamazioneAnno==null) letamazioneAnno="";
   else letamazioneAnno=letamazioneAnno.replace('.',',');

   if (idStadioFenologico==null) idStadioFenologico="";
   if (codiceProduttivita==null) codiceProduttivita="";
   if (campioneTerreno==null) campioneTerreno="";
   if (macinato==null) macinato="";
   Vector cod=new Vector(),desc=new Vector();
   String codStr[],descStr[];

   /**
    * Carico i dati relativi alla data
    **/
   templ.bset("giornoCampionamento",giornoCampionamento);
   templ.bset("meseCampionamento",meseCampionamento);
   templ.bset("annoCampionamento",annoCampionamento);

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
         templ.bset("checkedSi","checked");
   else
         templ.bset("checkedNo","checked");
   /**
    * Carico i dati relativi all'altitudine SLM
    **/
   templ.bset("altitudineSlm",altitudineSlm);
   /**
    * Carico i dati relativi all'età dell'impianto
    **/
   templ.bset("etaImpianto",etaImpianto);
   /**
    * Carico i dati di tutte le classi coltura con tipo coltura "Erbacea" nella
    * combo
    **/
   coltura.selectCodDescClasse(Coltura.COLTURA_FOGLIE_FRUTTA,cod,desc);
   int size=cod.size();
   for (int i=0;i<size;i++ )
   {
     if (idColtura.equals(cod.get(i).toString()))
       templ.set("classe.selected","selected");
     else
       templ.set("classe.selected","");
     templ.set("classe.codice",(String)cod.elementAt(i));
     templ.set("classe.descrizione",(String)desc.elementAt(i));
   }
   /**
    Carico i dati di tutti le specie coltura corrispondente alla classe scelta
   */
   cod.clear();
   desc.clear();
   coltura.selectCodDescSpecie(Coltura.IMPOSTA_QUERY_SPECIE,cod,desc,idColtura);
   size=cod.size();
   for (int i=0;i<size;i++ )
   {
     if (idSpecie.equals(cod.get(i).toString()))
     {
       //Se sono qui vuol dire che ho trovato all'interno della combo una specie
       //corrispondente a quella memorizzata: quindi la specie memorizzata è
       //valida
       specieValida=true;
       templ.set("specie.selected","selected");
     }
     else
       templ.set("specie.selected","");
     templ.set("specie.codice",(String)cod.elementAt(i));
     templ.set("specie.descrizione",(String)desc.elementAt(i));
   }
   if (!specieValida) idSpecie="-1";
   /**
    * Carico i dati relativi all'altra specie eventualmente indicata
    **/
   templ.bset("altraSpecie",altraSpecie);
   /**
   * Carico i dati di tutte le varietà di specie corrispondenti alla specie scelta
   */
   cod.clear();
   desc.clear();
   coltura.selectCodDescSpecie(Coltura.IMPOSTA_QUERY_VARIETA,cod,desc,idSpecie);
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
    * Carico i dati di tutti gli innesti corrispondenti alla specie scelta
    */
   cod.clear();
   desc.clear();
   coltura.selectCodDescSpecie(Coltura.IMPOSTA_QUERY_INNESTO,cod,desc,idSpecie);
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
    * specie scelta
    */
   cod.clear();
   desc.clear();
   coltura.selectCodDescSpecie(Coltura.IMPOSTA_QUERY_ALLEVAMENTO,cod,desc,idSpecie);
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
    * Carico il dato relativo ad eventuali altri allevamenti
    **/
   templ.bset("altroAllevamento",altroAllevamento);
   /**
    * Carico il dato relativo alla distanza tra le piante nel filare
    **/
   templ.bset("sestoImpianto1",sestoImpianto1);
   /**
    * Carico il dato relativo alla distanza tra i filari
    **/
   templ.bset("sestoImpianto2",sestoImpianto2);
   /**
    * Carico il dato relativo alle unità di N
    **/
   templ.bset("unitaN",unitaN);
   /**
    * Carico il dato relativo alle unità di P2O5
    **/
   templ.bset("unitaP2O5",unitaP2O5);
   /**
    * Carico il dato relativo alle unità di K2O
    **/
   templ.bset("unitaK2O",unitaK2O);
   /**
    * Carico il dato relativo alle unità di Mg
    **/
   templ.bset("unitaMg",unitaMg);
   /**
    * Carico il dato relativo alla quantità di letame cosparsa in un anno
    * espressa in q/Ha
    **/
   templ.bset("letamazioneAnno",letamazioneAnno);
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
    * Carico i dati riguardanti li stadi fenologici relativi al materiale
    * del campione
    */
   cod.clear();
   desc.clear();
   coltura.selectCodDesc(Coltura.IMPOSTA_QUERY_STADIO_FENOLOGICO,cod,desc);
   size=cod.size();
   for (int i=0;i<size;i++ )
   {
     if (idStadioFenologico.equals(cod.get(i).toString()))
       templ.set("idStadioFenologico.selected","selected");
     else
       templ.set("idStadioFenologico.selected","");
     templ.set("idStadioFenologico.codice",(String)cod.elementAt(i));
     templ.set("idStadioFenologico.descrizione",(String)desc.elementAt(i));
   }
   /**
    * Carico i dati riguardanti le concimazione organiche
    */
   codStr=beanColtura.getCodProduttivita();
   descStr=beanColtura.getDescProduttivita();
   for(int i=0;i<codStr.length;i++)
   {
     if (codiceProduttivita.equals(codStr[i]))
       templ.set("codiceProduttivita.selected","selected");
     else
       templ.set("codiceProduttivita.selected","");
     templ.set("codiceProduttivita.codice",codStr[i]);
     templ.set("codiceProduttivita.descrizione",descStr[i]);
   }
   /**
    * Carico il dato relativo a precedenti analisi svolte negli ultimi 4 anni
    **/
   templ.bset("campioneTerreno",campioneTerreno);

   if ("FOG".equals(aut.getCodMateriale()))
   {
     templ.newBlock("macinati");
     if ("S".equals(macinato))
       templ.set("macinati.checkedMacinateSi","checked");
     else
       templ.set("macinati.checkedMacinateNo","checked");
   }
%>
<%= templ.text() %>

