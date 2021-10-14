function salva(form)
{
    var msg='';
    form.indirizzo.value=form.indirizzo.value.trim();
    form.cap.value=form.cap.value.trim();
    if (form.indirizzo.value == '')
        msg=msg+'Valorizzare la voce "Indirizzo" del Proprietario del campione\n';
    if (form.cap.value == '')
        msg=msg+'Valorizzare la voce "CAP" del Proprietario del campione\n';
    else if ((form.cap.value.length != 5) || (!isNumber(form.cap.value)) )
           msg=msg+'Valorizzare correttamente la voce "CAP" del Proprietario del campione\n';
    if (form.istat.value == '')
      msg=msg+'Selezionare il "Comune" del Proprietario del campione\n';
    if (form.eMail.value!='' && controllaMail(form.eMail.value))
      msg=msg+'Valorizzare correttamente la voce "E.mail" del Proprietario del campione\n';

    if (form.azienda.value=='S')
    {
      /**
      * Per sapere se devo proseguire con le verifiche
      * dei dati aziendali, devo verificare che sia stato
      * inserito un codiceAzienda non vuoto
      **/
		var sCodiceAzienda = '';
		if (typeof form.codiceAzienda != "undefined")
		{
			sCodiceAzienda = form.codiceAzienda.value.trim();
			form.codiceAzienda.value = sCodiceAzienda;
		}
		if (sCodiceAzienda == "")
		{
			form.azienda.value='N';
		}
    }

    if (form.azienda.value=='S')
    {
      /**
      * Se sono qui vuol dire che devo controllare anche i dati relativi
      * all'azienda.
      *
      *  Il campo Partita IVA solo una partita iva (quindi deve contenere
      *  solo numeri ed essere lungo 11)
      **/
      lung= form.codiceAzienda.value.length;
      if (lung!=11)
       msg=msg+'Valorizzare una voce corretta in "Partita IVA"\n';
      else
      {
        msgTmp = controllaPartitaIVA(form.codiceAzienda.value);
        if (msgTmp!='')
          msg=msg+'"Partita IVA o Codice fiscale" - '+msgTmp+'\n';
      }
      form.ragioneSociale.value=form.ragioneSociale.value.trim();
      if (form.ragioneSociale.value == '')
        msg=msg+'Valorizzare la voce "Ragione sociale" dell\'impresa o azienda rappresentata\n';
      if ( (form.capAzienda.value != '') && !((form.capAzienda.value.length == 5) && (isNumber(form.capAzienda.value))) )
        msg=msg+'Valorizzare correttamente la voce "CAP" dell\'impresa o azienda rappresentata \n';
      if (form.istatAzienda.value == '')
        msg=msg+'Selezionare il "Comune" dell\'impresa o azienda rappresentata \n';
      if (form.eMailAzienda.value!='' && controllaMail(form.eMailAzienda.value))
        msg=msg+'Valorizzare correttamente la voce "E.mail" dell\'impresa o azienda rappresentata \n';

    }
    if ( msg != '' )
    {
        alert( "Dati incompleti\n\n"+msg );
        return false;
    }
    else
        return true;
}

