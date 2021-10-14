function salva(form)
{
    var msg='';

    if (form.codMateriale.value == -1)
        msg=msg+'Selezionare un "Materiale"\n';
    if (form.istatComune.value == '')
      msg=msg+'Selezionare il "Comune di provenienza del campione"\n';
    if (form.codLaboratorio.value == -1)
        msg=msg+'Selezionare un "Laboratorio di consegna"\n';
    if (form.codModalitaConsegna.value == -1)
        msg=msg+'Selezionare un "Modalità di consegna"\n';
    if (form.codiceMisuraPsr.value == -1)
    {
        msg = msg + 'Selezionare un adempimento per misura PSR\n';
    }
    else if (motivoObbligatorioSelected == 'S' && form.noteMisuraPsr.value.trim() == '')
	{
    	msg = msg + 'La motivazione adempimento misura PSR è obbligatoria per l\'adempimento selezionato \n';
	}
    if ( msg != '' )
    {
        alert( "Dati incompleti\n\n"+msg );
        return false;
    }
    else
        return true;
}