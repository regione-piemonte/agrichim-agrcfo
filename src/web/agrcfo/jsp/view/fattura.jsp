<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*,java.text.*"
    isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/fattura.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean id="beanAnalisi" scope="application" class="it.csi.agrc.BeanAnalisi"/>

<jsp:useBean
     id="fattura"
     scope="request"
     class="it.csi.agrc.DatiFattura">
    <%
      fattura.setDataSource(dataSource);
      fattura.setAut(aut);
    %>
</jsp:useBean>

<jsp:useBean
     id="etichettaCampione"
     scope="page"
     class="it.csi.agrc.EtichettaCampione">
    <%
      etichettaCampione.setDataSource(dataSource);
      etichettaCampione.setAut(aut);
    %>
</jsp:useBean>

<jsp:useBean
     id="analisi"
     scope="page"
     class="it.csi.agrc.Analisi">
    <%
      analisi.setDataSource(dataSource);
      analisi.setAut(aut);
      analisi.setIdRichiesta(aut.getIdRichiestaCorrente());
    %>
</jsp:useBean>

<jsp:useBean
     id="anagrafiche"
     scope="page"
     class="it.csi.agrc.Anagrafiche">
<%
  anagrafiche.setDataSource(dataSource);
  anagrafiche.setAut(aut);
%>
</jsp:useBean>

<jsp:useBean
     id="organizzazioneProfessionale"
     scope="request"
     class="it.csi.agrc.OrganizzazioneProfessionale">
<%
  organizzazioneProfessionale.setDataSource(dataSource);
  organizzazioneProfessionale.setAut(aut);
%>
</jsp:useBean>

<%--
  Il file che includo serve a gestire gli errori dovuti al funzionamento non
  corretto del javascript
--%>
<%@ include file="/jsp/view/problemiJavascript.inc" %>

<%
  etichettaCampione.select(""+aut.getIdRichiestaCorrente());
  if(etichettaCampione.getDescMateriale()!=null && etichettaCampione.getDescMateriale().equals("Terreni")){
	  templ.newBlock("fatturaTerreno");
	  byte tariffa = analisi.selectTariffaApplicata();
	
	CuneoLogger.debug(this,"ELISA tariffa "+tariffa);
	  switch (tariffa)
	  {
	    case 1: templ.set("fatturaTerreno.FasciaCChecked","checked");
	            break;
	    case 2: templ.set("fatturaTerreno.FasciaBChecked","checked");
	            break;
	    case 3: templ.set("fatturaTerreno.FasciaAChecked","checked");
	            break;
	    default: templ.set("fatturaTerreno.FasciaCChecked","checked");
	  }
  }

  String utenteBlock = null;
  String fatturaBlock = "";
  
  String idAnagraficaP=null,idAnagraficaU=null,idAnagraficaO=null;
  idAnagraficaP=anagrafiche.getProprietario();
  idAnagraficaU=anagrafiche.getUtente();
  idAnagraficaO=anagrafiche.getOrganizzazione();
  //System.out.println(idAnagrafica);  
  CuneoLogger.debug(this,"ELISA idAnagraficaP "+idAnagraficaP);
  CuneoLogger.debug(this,"ELISA idAnagraficaU "+idAnagraficaU);
  CuneoLogger.debug(this,"ELISA idAnagraficaO "+idAnagraficaO);
  String generalitaP = "",generalitaU = "",generalitaO = "";
  //jira 133
  if(idAnagraficaP != null && !idAnagraficaP.equals("")){
	  Anagrafica anagProp = anagrafiche.getAnagrafica(idAnagraficaP);
	  CuneoLogger.debug(this,"ELISA anagProp "+anagProp.getNome());
	  //jira 127
	  String generalitaString  = "";
	  boolean cognome = false;
	  boolean nome = false;
	  if(anagProp.getCodiceIdentificativo()!=null)
		  generalitaString += anagProp.getCodiceIdentificativo() + " - ";
	  if(anagProp.getCognomeRagioneSociale() != null) {
		  cognome = true;
		  generalitaString += anagProp.getCognomeRagioneSociale() + " ";
	  }
	  if(anagProp.getNome() != null) {   
		  nome = true;
		  generalitaString += anagProp.getNome();
	  }
	  if(!cognome && !nome) {
		  //cioè se entrambi sono null devo togliere il trattino
		  generalitaString = generalitaString.replace("-", "");
	  }
	  //String generalita = "("+anagUte.getCodiceIdentificativo() +" - "+anagUte.getCognomeRagioneSociale()+ " " +anagUte.getNome() +")";
	   CuneoLogger.debug(this,"ELISA generalitaStringProp "+generalitaString);
	   generalitaP = "("+ generalitaString +")";
  }
  if(idAnagraficaU != null && !idAnagraficaU.equals("")){
	  Anagrafica anagUte = anagrafiche.getAnagrafica(idAnagraficaU);
	  CuneoLogger.debug(this,"anagUte "+anagUte.getNome());
	  //jira 127
	  String generalitaString  = "";
	  boolean cognome = false;
	  boolean nome = false;
	  if(anagUte.getCodiceIdentificativo()!=null)
		  generalitaString += anagUte.getCodiceIdentificativo() + " - ";
	  if(anagUte.getCognomeRagioneSociale() != null) {
		  cognome = true;
		  generalitaString += anagUte.getCognomeRagioneSociale() + " ";
	  }
	  if(anagUte.getNome() != null) {   
		  nome = true;
		  generalitaString += anagUte.getNome();
	  }
	  if(!cognome && !nome) {
		  //cioè se entrambi sono null devo togliere il trattino
		  generalitaString = generalitaString.replace("-", "");
	  }
	  //String generalita = "("+anagUte.getCodiceIdentificativo() +" - "+anagUte.getCognomeRagioneSociale()+ " " +anagUte.getNome() +")";
	   CuneoLogger.debug(this,"generalitaStringUte "+generalitaString);
	   generalitaU = "("+ generalitaString +")";
  }
  if(idAnagraficaO != null && !idAnagraficaO.equals("")){
	  organizzazioneProfessionale.select(idAnagraficaO);
	  CuneoLogger.debug(this,"anagOrg "+organizzazioneProfessionale.getRagioneSociale());
	  //jira 127
	  String generalitaString  = "";
	  boolean cognome = false;
	  if(organizzazioneProfessionale.getCfPartitaIva()!=null)
		  generalitaString += organizzazioneProfessionale.getCfPartitaIva() + " - ";
	  if(organizzazioneProfessionale.getRagioneSociale() != null) {
		  cognome = true;
		  generalitaString += organizzazioneProfessionale.getRagioneSociale() + " ";
	  }
	   CuneoLogger.debug(this,"generalitaStringOrg "+generalitaString);
	   generalitaO = "("+ generalitaString +")";
  }
 // System.out.println("generalita "+ generalita);  
  
  switch(aut.getUtente().getTipoUtente())
  {
    case 'P':
      CuneoLogger.debug(this,"ELISA caso P ");
      templ.bset("FasciaDisable","disabled");
      templ.bset("pageBack","anagraficaPrivato.jsp");
      utenteBlock = "utentePrivato";
      templ.newBlock("utentePrivato");
      /**
      *  Se l'utente privato non appartiene ad un'azienda non devo dargli la
      * possibilità di selezionare il radioButton associato all'azienda
      */
      CuneoLogger.debug(this,"ELISA caso P  aut.getUtente().getAnagraficaAzienda() " + aut.getUtente().getAnagraficaAzienda());
      if (aut.getUtente().getAnagraficaAzienda() != null)
      {
        fatturaBlock=".fatturaAzienda";
        templ.newBlock("utentePrivato.fatturaAzienda");
      }
      templ.set("utentePrivato.generalitaU",generalitaU);
      templ.set("utentePrivato.generalitaO",generalitaO);
      //AGRICHIM-18
      //Se l'utente connesso è un utente privato il sistema non permette la scelta della spedizione della fattura 
      templ.set("disabledSpedizioneS", "disabled");
      templ.set("disabledSpedizioneN", "disabled");
      templ.set("disabledSpedizione", "S");
      
      break;

    case 'T':
      templ.bset("FasciaDisable","disabled");
      templ.bset("pageBack","anagraficaTecnico.jsp");
      utenteBlock="utenteTecnico";
      templ.newBlock("utenteTecnico");

			//AGRICHIM-18
      //Se l'utente connesso è un tecnico il sistema non permette la scelta della spedizione della fattura 
      templ.set("disabledSpedizioneS", "disabled");
      templ.set("disabledSpedizioneN", "disabled");
      templ.set("disabledSpedizione", "S");
      
      templ.set("utenteTecnico.generalitaP",generalitaP);
      templ.set("utenteTecnico.generalitaU",generalitaU);
      templ.set("utenteTecnico.generalitaO",generalitaO);
      break;

    case 'L':
      templ.bset("pageBack","anagraficaUtenteLAR.jsp");
      utenteBlock="utenteLAR";
      templ.newBlock("utenteLAR");
      
			//AGRICHIM-18
      //Se l'utente connesso è un LAR non permette la scelta della spedizione della fattura 
      templ.set("disabledSpedizioneS", "disabled");
      templ.set("disabledSpedizioneN", "disabled");
      templ.set("disabledSpedizione", "N");

      /**
      *  Se il proprietario del campione è un privato non deve poter selezionare
      *  i radioButton Tecnico e Organizzazione del tecnico.
      */
      if (etichettaCampione.isProprietarioTecnico())
      {
        fatturaBlock=".fatturaTecnico";
        templ.newBlock("utenteLAR.fatturaTecnico");
        templ.set("utenteLAR.fatturaTecnico.generalitaU",generalitaU);
        templ.set("utenteLAR.fatturaTecnico.generalitaO",generalitaO);
      }
      
      templ.set("utenteLAR.generalitaP",generalitaP);
      

      break;
  }
  
  CuneoLogger.debug(this," sono fuori il case ");
  //String errore=request.getParameter("errore");
  if (aut.getFase()>3 || errore != null)
  {
	  
    /**
     * Se la fase è maggiore di 3 vado a leggere i dati riguardanti
     * la quarta fase
     */
    if (errore == null)
      fattura.select();

    String idRichiesta = fattura.getIdRichiesta();
    if ( idRichiesta == null && null == errore )
    {
      templ.bset("fatturaSNCheckedN","checked");
      templ.bset("spedizioneCheckedN","checked");
    }
    else
    {
      if ("S".equals(fattura.getFatturaSN()))
        templ.bset("fatturaSNCheckedS","checked");
      else
        templ.bset("fatturaSNCheckedN","checked");
      //Agrichim-203 Flag Spedizione sempre = S
//       if ("S".equals(fattura.getSpedizione()))
        templ.bset("spedizioneCheckedS","checked");
//       else
//         templ.bset("spedizioneCheckedN","checked");

      String fatturare = fattura.getFatturare();
      if ("A".equals(fatturare))
        templ.set(utenteBlock+".fatturareCheckedA","checked");
      else if ("T".equals(fatturare))
        templ.set(utenteBlock+fatturaBlock+".fatturareCheckedT","checked");
      else if ("U".equals(fatturare))
        templ.set(utenteBlock+".fatturareCheckedU","checked");
      else if ("O".equals(fatturare))
        templ.set(utenteBlock+fatturaBlock+".fatturareCheckedO","checked");
      else if ("P".equals(fatturare))
        {
           templ.set(utenteBlock+fatturaBlock+".fatturareCheckedP","checked");
        }

      templ.bset("cfPartitaIva",fattura.getCfPartitaIva());
      templ.bset("ragioneSociale",fattura.getRagioneSociale());
      templ.bset("indirizzo",fattura.getIndirizzo());
      templ.bset("cap",fattura.getCap());
      templ.bset("comune",fattura.getComune());
      templ.bset("comuneDesc",fattura.getComuneDesc());
      templ.bset("pec",fattura.getPec());
      templ.bset("cod_destinatario",fattura.getCod_destinatario());
    }
  }
  else
  {
    templ.set(utenteBlock+".fatturareCheckedU","checked");
    templ.bset("fatturaSNCheckedN","checked");
    templ.bset("spedizioneCheckedN","checked");
  }

  String importoSpedizione = beanAnalisi.getImportoSpedizione();
  DecimalFormat nf2 = new DecimalFormat("#0.00");
  templ.bset("importoSpedizione",importoSpedizione);
  templ.bset("importoSpedizioneStr",nf2.format(Double.parseDouble(importoSpedizione)).replace('.',','));
%>

<%= templ.text() %>
