function salva(form)
{
    var msg='';
    if (!controlla(form.giornoCampionamento.value,form.meseCampionamento.value,form.annoCampionamento.value))
        msg=msg+'Valorizzare correttamente la "Data campionamento"\n';
    else
    {
      if (!controlla(form.giornoCampionamento.value,form.meseCampionamento.value,form.annoCampionamento.value,true))
        msg=msg+'La "Data campionamento" non può essere superiore alla data odierna\n';
    }
    if (form.giacitura[1].checked)
    {
      if (form.idEsposizione.value == -1)
        msg=msg+'Selezionare la "Direzione di esposizione del terreno rispetto al sole "\n';
    }
    if (!isNumber(form.altitudineSlm.value))
        msg=msg+'Valorizzare correttamente la voce "Altitudine SLM"\n';
    if (!isNumber(form.etaImpianto.value))
        msg=msg+'Valorizzare correttamente la voce "Età dell\'impianto"\n';
    if (form.idColtura.value == -1)
        msg=msg+'Selezionare la "Classe coltura"\n';
    form.altraSpecie.value=form.altraSpecie.value.trim();
    if (form.idSpecie.value == -1 && form.altraSpecie.value=='')
        msg=msg+'Selezionare la "Specie coltura" o valorizzare la voce "Altra specie"\n';
    form.altroAllevamento.value=form.altroAllevamento.value.trim();
    if (form.idSistemaAllevamento.value == -1 && form.altroAllevamento.value=='')
        msg=msg+'Selezionare il "Sistema allevamento" o valorizzare la voce "Altro allevamento"\n';

    var sestoImpianto1 =form.sestoImpianto1.value.replace(",",".");
    var sestoImpianto2 =form.sestoImpianto2.value.replace(",",".");

    if ((sestoImpianto1 != '') &&
      (!isFloat(sestoImpianto1,2) || (sestoImpianto1<=0) || (sestoImpianto1>99)))
        msg=msg+'Valorizzare correttamente la voce "Distanza tra le piante nel filare"\n';
    if ((sestoImpianto2 != '') &&
      (!isFloat(sestoImpianto2,2) || (sestoImpianto2<=0) || (sestoImpianto2>99)))
        msg=msg+'Valorizzare correttamente la voce "Distanza tra i filari"\n';

    if ( (form.unitaN.value != '') && (!isNumber(form.unitaN.value)))
        msg=msg+'Valorizzare correttamente la voce "Unità N"\n';
    if ( (form.unitaP2O5.value != '') && (!isNumber(form.unitaP2O5.value)))
        msg=msg+'Valorizzare correttamente la voce "Unità P2O2"\n';
    if ( (form.unitaK2O.value != '') && (!isNumber(form.unitaK2O.value)))
        msg=msg+'Valorizzare correttamente la voce "Unità K2O"\n';
    if ( (form.unitaMg.value != '') && (!isNumber(form.unitaMg.value)))
        msg=msg+'Valorizzare correttamente la voce "Unità Mg"\n';

    var letamazioneAnno =form.letamazioneAnno.value.replace(",",".");
    if ((letamazioneAnno != '') &&
      (!isFloat(letamazioneAnno,2) || (letamazioneAnno<=0) || (letamazioneAnno>99)))
        msg=msg+'Valorizzare correttamente la voce "Quantità di letame cosparsa in un anno espressa in q/Ha"\n';

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
    if (form.idStadioFenologico.value == -1)
        msg=msg+'Selezionare la "Stadio fenologico"\n';
    if ( msg != '' )
    {
        alert( "Dati incompleti\n\n"+msg );
        return false;
    }
    else
        return true;
}

