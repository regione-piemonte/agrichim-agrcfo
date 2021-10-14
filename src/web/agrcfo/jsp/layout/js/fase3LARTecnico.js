function salva(form)
{
    var msg='';
    form.cognome.value=form.cognome.value.trim();
    form.nome.value=form.nome.value.trim();
    form.codiceFiscale.value=form.codiceFiscale.value.trim();
    form.indirizzo.value=form.indirizzo.value.trim();
    form.telefono.value=form.telefono.value.trim();
    form.cellulare.value=form.cellulare.value.trim();

    if (form.cognome.value == '')
      msg=msg+'Valorizzare la voce "Cognome"\n';
    if (form.nome.value == '')
      msg=msg+'Valorizzare la voce "Nome"\n';
    lung= form.codiceFiscale.value.length;
    if (lung!= 16)
      msg=msg+'Valorizzare una voce corretta in "Codice fiscale"\n';
    else
    {
       form.codiceFiscale.value = form.codiceFiscale.value.toUpperCase();
       msgTmp = controllaCodiceFiscale(form.codiceFiscale.value);
       if (msgTmp!='') msg=msg+'"Codice fiscale" - '+msgTmp+'\n';
    }
    if (form.indirizzo.value == '')
      msg=msg+'Valorizzare la voce "Indirizzo"\n';
    if (form.cap.value == '')
        msg=msg+'Valorizzare la voce "CAP"\n';
    if ( (form.cap.value != '') && !((form.cap.value.length == 5) && (isNumber(form.cap.value))) )
      msg=msg+'Valorizzare correttamente la voce "CAP"\n';
    if (form.istatComune.value == '')
      msg=msg+'Selezionare il "Comune"\n';
    if (form.eMail.value!='' && controllaMail(form.eMail.value))
      msg=msg+'Valorizzare correttamente la voce "E.mail"\n';

    if (form.telefono.value == '' && form.cellulare.value == '')
    {
      msg=msg+'Valorizzare la voce "Telefono fisso"\n';
      msg=msg+'Valorizzare la voce "Cellulare "\n';
    }
    if ( msg != '' )
    {
        alert( "Dati incompleti\n\n"+msg );
        return false;
    }
    else
        return true;
}

