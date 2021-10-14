function ricercaElenco(form)
{
  var msg='';
  var corretti=true;
  if ( (form.idRichiestaDa.value != '') && !isNumber(form.idRichiestaDa.value))
  {
    corretti=false;
    msg=msg+'Valorizzare correttamente la voce "Numero della richiesta da"\n';
  }
  if ( (form.idRichiestaA.value != '') && !isNumber(form.idRichiestaA.value))
  {
    corretti=false;
    msg=msg+'Valorizzare correttamente la voce "Numero della richiesta a"\n';
  }

  if (corretti)
  {
    if (parseInt(form.idRichiestaDa.value) > parseInt(form.idRichiestaA.value))
      msg=msg+'"Numero della richiesta da" non deve essere maggiore di "Numero della richiesta a"\n';
  }

  corretti=true;

  if (!(form.dataAGiorno.value=='' && form.dataAMese.value=='' && form.dataAAnno.value==''))
  {
    if (!controlla(form.dataAGiorno.value,form.dataAMese.value,form.dataAAnno.value))
    {
      corretti=false;
      msg=msg+'Valorizzare correttamente la "Data di richiesta analisi precedente al  "\n';
    }
  }
  else corretti=false;

  form.dataA.value=form.dataAGiorno.value+"/"+form.dataAMese.value+"/"+form.dataAAnno.value;
  if ( form.dataA.value == "//" )
    form.dataA.value = "";

  if (!(form.dataDaGiorno.value=='' && form.dataDaMese.value=='' && form.dataDaAnno.value==''))
  {
    if (!controlla(form.dataDaGiorno.value,form.dataDaMese.value,form.dataDaAnno.value))
    {
      corretti=false;
      msg=msg+'Valorizzare correttamente la "Data di richiesta analisi posteriore  al  "\n';
    }
  }
  else corretti=false;

  form.dataDa.value=form.dataDaGiorno.value+"/"+form.dataDaMese.value+"/"+form.dataDaAnno.value;
  if ( form.dataDa.value == "//" )
    form.dataDa.value = "";

  if (corretti && !confronta(form.dataDaGiorno.value,form.dataDaMese.value,form.dataDaAnno.value,form.dataAGiorno.value,form.dataAMese.value,form.dataAAnno.value))
     msg=msg+'"Data di richiesta analisi posteriore al" non deve essere maggiore di "Data di richiesta analisi precedente al"\n';

  /**
     *  Il campo Partita IVA o Codice fiscale può contenere
     *  - sia una partita iva (quindi deve contenere solo numeri ed essere
     *    lungo 11)
     *  - sia un codice fiscale (quindi deve essere lungo 16 e bisogna fare
     *    dei controlli ulteriori sulla posizione delle lettere e dei numeri)
     **/
  lung= form.codiceFiscale.value.length;
  if ((lung!=11) && (lung!=16) && (lung!=0) )
    msg=msg+'Valorizzare una voce corretta in "Codice fiscale o Partita IVA"\n';
  else
  {
     form.codiceFiscale.value = form.codiceFiscale.value.toUpperCase();
     if (lung==11)
     {
        msgTmp = controllaPartitaIVA(form.codiceFiscale.value);
        if (msgTmp!='')
            msg=msg+'"Codice fiscale o Partita IVA" - '+msgTmp+'\n';
     }
     if (lung==16)
     {
        msgTmp = controllaCodiceFiscale(form.codiceFiscale.value);
        if (msgTmp!='')
            msg=msg+'"Codice fiscale o Partita IVA" - '+msgTmp+'\n';
     }
  }
  if ( (form.cognome.value != '') && (form.cognome.value.length<2))
    msg=msg+'Inserire almeno 2 caratteri nella voce "Cognome o Ragione sociale proprietario"\n';
  if ( (form.etichetta.value != '') && (form.etichetta.value.length<4))
    msg=msg+'Inserire almeno 4 caratteri nella voce "Etichetta"\n';

  if ( msg != '' )
  {
      alert( "Dati non corretti\n\n"+msg );
      return false;
  }
  return true;
}
