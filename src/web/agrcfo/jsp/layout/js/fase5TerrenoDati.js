function salva(form)
{
    var msg='';
    if (form.idProfondita.value == -1)
        msg=msg+'Selezionare la "Profondità prelievo"\n';
    if (form.idColturaAttuale.value == -1)
      msg=msg+'Selezionare la "Classe coltura in atto o precedente"\n';
    if (form.specieAttuale.value == -1)
      msg=msg+'Selezionare la "Specie coltura in atto o precedente"\n';    
    if (form.idColturaPrevista.value == -1)
        msg=msg+'Selezionare la "Classe coltura prevista"\n';
    if (form.speciePrevista.value == -1)
        msg=msg+'Selezionare la "Specie coltura prevista"\n';
    if ( (form.annoImpianto.value != '') && (!isNumber(form.annoImpianto.value)))
        msg=msg+'Valorizzare correttamente la voce "Anno dell\'impianto"\n';
    /*if (form.superficieAppezzamento.value == -1)
        msg=msg+'Selezionarela la "Superficie appezzamento"\n'; */

    var produzioneQha =form.produzioneQha.value.replace(",",".");
    if ((produzioneQha != '') &&
      (!isFloat(produzioneQha,2) || (produzioneQha<=0) || (produzioneQha>999)))
        msg=msg+'Valorizzare correttamente la voce "Produzione stimata (q/ha)"\n';

    /*
    if (form.giacitura[1].checked)
    {
      if (form.idEsposizione.value == -1)
        msg=msg+'Selezionare la "Direzione di esposizione del terreno rispetto al sole "\n';
    }*/

    if (form.scheletro[0].checked)
    {
      if ( (form.percentualePietre.value == '') || (!isNumber(form.percentualePietre.value) || (form.percentualePietre.value>100)))
          msg=msg+'Valorizzare correttamente la voce "Percentuale presenza pietre"\n';
    }
    if (form.tipoConcimazione.value == -1)
        msg=msg+'Selezionare la "Concimazione organica"\n';
    if (form.tipoConcimazione.value == 'S' || form.tipoConcimazione.value == 's'
                          ||
        form.tipoConcimazione.value == 'a' || form.tipoConcimazione.value == 'A'
        )
    {
      if (form.idConcime.value == -1)
        msg=msg+'Selezionare il "Concime utilizzato"\n';
    }
    if (form.codiceModalitaColtivazione.value == -1)
        msg=msg+'Selezionare la "Modalità di coltivazione"\n';
    if ( msg != '' )
    {
        alert( "Dati incompleti\n\n"+msg );
        return false;
    }
    else
        return true;
}

