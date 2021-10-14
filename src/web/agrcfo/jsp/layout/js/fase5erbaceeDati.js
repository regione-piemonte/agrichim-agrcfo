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
    if (form.idColtura.value == -1)
        msg=msg+'Selezionare la "Classe coltura"\n';
    if (form.idSpecie.value == -1)
        msg=msg+'Selezionare la "Specie coltura"\n';

    if ( msg != '' )
    {
        alert( "Dati incompleti\n\n"+msg );
        return false;
    }
    else
        return true;
}

