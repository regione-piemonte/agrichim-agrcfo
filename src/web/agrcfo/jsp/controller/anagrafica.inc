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
     id="anagUte"
     scope="request"
     class="it.csi.agrc.Anagrafica">
<%
  anagUte.setDataSource(dataSource);
  anagUte.setAut(aut);
%>
</jsp:useBean>

<jsp:useBean
     id="anagAzi"
     scope="request"
     class="it.csi.agrc.Anagrafica">
<%
  anagAzi.setDataSource(dataSource);
  anagAzi.setAut(aut);
%>
</jsp:useBean>

<%
  Utente utente = aut.getUtente();

  anagUte.setIdAnagrafica(utente.getAnagraficaUtente());
  anagUte.setCognomeRagioneSociale(request.getParameter("cognome"));
  anagUte.setNome(request.getParameter("nome"));
  anagUte.setCodiceIdentificativo(request.getParameter("codiceFiscale"));
  anagUte.setIndirizzo(request.getParameter("indirizzo"));
  anagUte.setCap(request.getParameter("cap"));
  anagUte.setComuneResidenza(request.getParameter("istat"));
  anagUte.setEmail(request.getParameter("eMail"));
  anagUte.setTelefono(request.getParameter("telefono"));
  anagUte.setCellulare(request.getParameter("cellulare"));
  anagUte.setFax(request.getParameter("fax"));

  // Leggo la partita IVA che arriva dal form
  String codiceAzienda = request.getParameter("codiceAzienda");
  if (codiceAzienda!=null && "".equals(codiceAzienda))
    codiceAzienda=null;

  Anagrafica anagAziNew = null;
  // Se è stata inserita (codiceAzienda!=null), allora cerco il record corrispondente nel DB
  // Se invece non è presente la partita IVA (codiceAzienda==null) allora devo annullare il legame utente-azienda
  if (codiceAzienda!=null) {
    // Creo il legame
    anagAziNew = anagrafiche.getAnagraficaDaCF(codiceAzienda);
    if (anagAziNew!=null) {
      anagUte.setIdAnagraficaAzienda(anagAziNew.getIdAnagrafica());
      utente.setAnagraficaAzienda(anagAziNew.getIdAnagrafica());
    }
  }else{
    // annullo il legame
    anagUte.setIdAnagraficaAzienda(null);
    utente.setAnagraficaAzienda(null);
  }

  /**
   * Controllo gli eventuali errori tramite jsp
   * */
  errore=anagUte.ControllaDati(anagUte.ANAGRAFICA_PRIVATO);
  if (errore==null) anagUte.update();

  if (utente.getAnagraficaAzienda()!=null && codiceAzienda!=null) {
    anagAzi.setIdAnagrafica(utente.getAnagraficaAzienda());
    anagAzi.setCodiceIdentificativo(codiceAzienda);
    anagAzi.setCognomeRagioneSociale(request.getParameter("ragioneSociale"));
    anagAzi.setIndirizzo(request.getParameter("indirizzoAzienda"));
    anagAzi.setCap(request.getParameter("capAzienda"));
    anagAzi.setComuneResidenza(request.getParameter("istatAzienda"));
    anagAzi.setTelefono(request.getParameter("telefonoAzienda"));
    anagAzi.setFax(request.getParameter("faxAzienda"));
    anagAzi.setEmail(request.getParameter("eMailAzienda"));
    anagAzi.setTipoPersona("G");

    /**
     * Controllo gli eventuali errori tramite jsp
     **/
    if (errore==null) 
    	errore=anagAzi.ControllaDati(anagAzi.ANAGRAFICA_AZIENDA);
    else {
      String temp=anagAzi.ControllaDati(anagAzi.ANAGRAFICA_AZIENDA);
      if (temp!=null) errore+=temp;
    }
    if (errore==null)
      // Se il record esiste già (anagAziNew!=null), devo aggiornare
      // altrimenti (anagAzi==null), devo inserire
//      if (anagAziNew!=null)
//        anagAzi.update();
//      else
//        anagAzi.insert();
      anagAzi.insertUpdate();
  }

  // Visto che posso aver modificato i dati dell'attributo "di sessione"
  // contenente i dati utente, aggiorno la sessione
  it.csi.agrc.AutenticazioneQry autQry = new it.csi.agrc.AutenticazioneQry(); 
  autQry.setDataSource(dataSource);
  aut.setUtente(autQry.caricaDati(utente.getIdentificativoUtenteIride(),
                 utente.getCodiceFiscale(),
                 codiceAzienda));
  session.setAttribute("aut",aut);
%>
