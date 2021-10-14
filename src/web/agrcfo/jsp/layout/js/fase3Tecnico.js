function salva(form)
{
    var msg='';
    form.codiceIdentificativo.value=form.codiceIdentificativo.value.trim();
    form.cognomeRagioneSociale.value=form.cognomeRagioneSociale.value.trim();
    form.nome.value=form.nome.value.trim();
    form.cap.value=form.cap.value.trim();

    /**
     *  Il campo Partita IVA o Codice fiscale può contenere
     *  - sia una partita iva (quindi deve contenere solo numeri ed essere
     *    lungo 11)
     *  - sia un codice fiscale (quindi deve essere lungo 16 e bisogna fare
     *    dei controlli ulteriori sulla posizione delle lettere e dei numeri)
     *  a seconda che il tipo sia azienda o privato
     **/
    var azienda=form.tipoPersona[0].checked;
    lung= form.codiceIdentificativo.value.length;
    if (!((lung==11 && azienda) || (lung==16 && !azienda)))
      msg=msg+'Valorizzare una voce corretta in "Partita IVA o Codice fiscale"\n';
    else
    {
       form.codiceIdentificativo.value = form.codiceIdentificativo.value.toUpperCase();
       if (lung==11)
       {
          msgTmp = controllaPartitaIVA(form.codiceIdentificativo.value);
          if (msgTmp!='')
              msg=msg+'"Partita IVA o Codice fiscale" - '+msgTmp+'\n';
       }
       else
       {
          msgTmp = controllaCodiceFiscale(form.codiceIdentificativo.value);
          if (msgTmp!='')
              msg=msg+'"Partita IVA o Codice fiscale" - '+msgTmp+'\n';
       }
    }


    if (form.cognomeRagioneSociale.value == '')
      msg=msg+'Valorizzare la voce "Cognome o Ragione Sociale"\n';
    if (form.nome.value == '' && !azienda)
      msg=msg+'Valorizzare la voce "Nome"\n';
    if ( (form.cap.value != '') && !((form.cap.value.length == 5) && (isNumber(form.cap.value))) )
      msg=msg+'Valorizzare correttamente la voce "CAP"\n';
    if (form.comuneResidenza.value == '')
      msg=msg+'Selezionare il "Comune"\n';
    if (form.email.value!='' && controllaMail(form.email.value))
      msg=msg+'Valorizzare correttamente la voce "E.mail"\n';
    if ( msg != '' )
    {
        alert( "Dati incompleti\n\n"+msg );
        return false;
    }
    else
        return true;
}

