function salva(form,calcolo)
{
    //return true;
    var msg='';

    var foglioParticella=false;
    /*form.sezione.value=form.sezione.value.toUpperCase();
    if ( form.sezione.value!='' && form.sezione.value!=' ' &&
         (form.sezione.value<'A' || form.sezione.value>'Z' || form.sezione.value.length>1) &&
         !isNumber(form.sezione.value) )
        msg=msg+'Inserire una lettera in "Sezione"\n'; */
    
    //AGRICHIM-46
    //Questo controllo non va fatto nel caso la regione sia diversa da Piemonte
    if (form.piemonte.value == '01')
  	{    
	    if ( form.foglio.value!='' && !isNumber(form.foglio.value) )
	        msg=msg+'Inserire un numero positivo in "Foglio"\n';
	    if ( form.particellaCatastale.value!='' && !isNumber(form.particellaCatastale.value) )
	        msg=msg+'Inserire un numero positivo in "Particella catastale"\n';
	
	    if (msg =='' && form.foglio.value!='' && form.particellaCatastale.value!='')
	      foglioParticella=true;
	
	    if (foglioParticella && form.coordinataNordBoaga.value==''
	        && form.coordinataEstBoaga.value==''
	        && form.coordinataEstUtm.value==''
	        && form.coordinataNordUtm.value=='')
	    {
	      form.tipoGeoreferenziazione.value="M";
	      //if (!confirm("I dati catastali forniscono una indicazione solo parziale delle coordinate di prelievo.\nAl fine di avere una georeferenziazione pi� attendibile si consiglia di ottenere le coordinate\nattraverso l�utilizzo di un GPS in campo o attraverso l�applicativo internet di georeferenziazione.\nIn un futuro prossimo il LAR si riserva di consentire l�accesso al servizio solamente per gli utenti\nche effettuano una corretta georeferenziazione utilizzando gli strumenti sopra indicati.\n\nProseguire?")) return false;
	    }
	    else
	    {
	      if (!isNaN(parseInt(form.coordinataNordBoaga.value,10)))
	        form.coordinataNordBoaga.value=parseInt(form.coordinataNordBoaga.value,10);
	      if (!isNaN(parseInt(form.coordinataEstBoaga.value,10)))
	        form.coordinataEstBoaga.value=parseInt(form.coordinataEstBoaga.value,10);
	      if (!isNaN(parseInt(form.coordinataNordUtm.value,10)))
	        form.coordinataNordUtm.value=parseInt(form.coordinataNordUtm.value,10);
	      if (!isNaN(parseInt(form.coordinataEstUtm.value,10)))
	        form.coordinataEstUtm.value=parseInt(form.coordinataEstUtm.value,10);
	      
	      if (isCoordinateGradiAndBoaga(form))
	    	  msg=msg+'Valorizzare in alternativa le coordinate Gauss-Boaga o le coordinate in gradi sessagesimali\n';
	      
	      msg=msg+verificaCoordinateGradi(form);
	      
	      if ( form.coordinataNordBoaga.value!='' )
	        if ( form.coordinataEstBoaga.value=='' )
	          msg=msg+'Inserire numeri positivi in "Coordinata Nord BOAGA" e in "Coordinata Est BOAGA"\n';
	        else if ( !isNumber(form.coordinataNordBoaga.value) || form.coordinataNordBoaga.value.length!=7)
	          msg=msg+'Inserire un numero di 7 cifre in "Coordinata Nord BOAGA"\n';
	        else
	        {
	          form.coordinataNordUtm.value=parseInt(form.coordinataNordBoaga.value,10)-19;
	          form.tipoGeoreferenziazione.value="M";
	        }
	      if ( form.coordinataEstBoaga.value!='' )
	        if ( form.coordinataNordBoaga.value=='' )
	          msg=msg+'Inserire numeri positivi in "Coordinata Nord BOAGA" e in "Coordinata Est BOAGA"\n';
	        else if ( !isNumber(form.coordinataEstBoaga.value) || form.coordinataEstBoaga.value.length!=7)
	          msg=msg+'Inserire un numero di 7 cifre in "Coordinata Est BOAGA"\n';
	        else
	        {
	          form.coordinataEstUtm.value=parseInt(form.coordinataEstBoaga.value,10)-1000026;
	          form.tipoGeoreferenziazione.value="M";
	        }
	
	      if ( form.coordinataNordUtm.value!='' )
	        if ( form.coordinataEstUtm.value=='' )
	          msg=msg+'Inserire numeri positivi in "Coordinata Nord UTM" e in "Coordinata Est UTM"\n';
	        else if ( !isNumber(form.coordinataNordUtm.value) || form.coordinataNordUtm.value.length!=7)
	          msg=msg+'Inserire un numero di 7 cifre in "Coordinata Nord UTM"\n';
	      if ( form.coordinataEstUtm.value!='' )
	        if ( form.coordinataNordUtm.value=='' )
	          msg=msg+'Inserire numeri positivi in "Coordinata Nord UTM" e in "Coordinata Est UTM"\n';
	        else if ( !isNumber(form.coordinataEstUtm.value) || form.coordinataEstUtm.value.length!=6)
	          msg=msg+'Inserire un numero di 6 cifre in "Coordinata Est UTM"\n';
	
	        if (form.coordinataEstUtm.value == '' && form.coordinataNordUtm.value == '' && form.gradiEst.value == '')
	        	if (calcolo=='si')
	    		  {
	    		   msg = msg + 'Inserire Foglio e Particella catastale oppure le coordinate Gauss-Boaga oppure le coordinate geografiche in gradi';
	    		  }
	    	  else
	    		  {
	    		  msg=msg+'Inserire Foglio e Particella catastale oppure le coordinate UTM oppure le coordinate geografiche\n';
	    		  }    	
	    	}
  		}
      else
    	{   
        if (form.sezione.value != '' || form.foglio.value != '' ||  form.particellaCatastale.value != '' || form.subparticella.value != '' ||
        		form.coordinataNordUtm.value != '' || form.coordinataEstUtm.value != '' || form.coordinataNordBoaga.value != '' || form.coordinataEstBoaga.value != ''
        		|| form.gradiEst.value != '' || form.minutiEst.value != '' || form.secondiEst.value != '' || form.decimaliEst.value != ''
        		|| form.gradiNord.value != '' || form.minutiNord.value != '' || form.secondiNord.value != '' || form.decimaliNord.value != ''	
        )
	    	{
        		msg = msg + 'E\' stato selezionato un comune non appartenente al Piemonte: non � possibile inserire n� le coordinate n� i dati catastali \n';
	    	}
    	}

    if ( msg != '' )
    {
        alert( "Dati incompleti\n\n"+msg );
        return false;
    }
    else
        return true;
}

function isCoordinateGradiAndBoaga(form)
{
	/*
		Alla conferma da parte dell�utente, se sono state indicate sia le coordinate Gauss-Boaga che le coordinate in gradi sessagesimali, 
		il sistema visualizza il messaggio di errore �Valorizzare in alternativa le coordinate Gauss-Boaga o le coordinate in gradi sessagesimali�
	*/
	if (( form.coordinataEstBoaga.value!='' || form.coordinataNordBoaga.value!='' ) &&
			(form.gradiEst.value != '' || form.minutiEst.value != '' || form.secondiEst.value != '' || form.decimaliEst.value != ''
        		|| form.gradiNord.value != '' || form.minutiNord.value != '' || form.secondiNord.value != '' || form.decimaliNord.value != ''))
		return true;
	return false;
}

function verificaCoordinateGradi(form)
{
	if (form.gradiEst.value != '' || form.minutiEst.value != '' || form.secondiEst.value != '' || form.decimaliEst.value != ''
        		|| form.gradiNord.value != '' || form.minutiNord.value != '' || form.secondiNord.value != '' || form.decimaliNord.value != '')
	{
		var coordGradi=$('input:radio[name=coordGradi]:checked').val();
		if (coordGradi=='DD')
		{
			//Gradi decimali
			if (form.gradiEst.value == '' || form.decimaliEst.value == ''
        		|| form.gradiNord.value == '' || form.decimaliNord.value == '')
				return "Valorizzare tutti i campi relativi alle coordinate in Gradi Nord e Est oppure lasciare tutti i campi vuoti\n";
			else
			{
				if (isNotNumber(form.gradiEst.value) || isNotNumber(form.decimaliEst.value)
	        		|| isNotNumber(form.gradiNord.value) || isNotNumber(form.decimaliNord.value))
					return "Inserire solo valori numerici nei campi relativi alle coordinate in Gradi Nord e Est\n";
				
				if (parseInt(form.gradiEst.value,10)<0 || parseInt(form.decimaliEst.value,10)<0
		        		|| parseInt(form.gradiNord.value,10)<0 || parseInt(form.decimaliNord.value,10)<0)
					return "Inserire solo valori numerici positivi nei campi relativi alle coordinate in Gradi Nord e Est\n";
			}
		}
		if (coordGradi=='DM')
	    {
			//Gradi e minuti decimali
			if (form.gradiEst.value == '' || form.minutiEst.value == '' || form.decimaliEst.value == ''
        		|| form.gradiNord.value == '' || form.minutiNord.value == '' || form.decimaliNord.value == '')
				return "Valorizzare tutti i campi relativi alle coordinate in Gradi Nord e Est oppure lasciare tutti i campi vuoti\n";
			else
			{
				if (isNotNumber(form.gradiEst.value) || isNotNumber(form.minutiEst.value) || isNotNumber(form.decimaliEst.value)
	        		|| isNotNumber(form.gradiNord.value) || isNotNumber(form.minutiNord.value) || isNotNumber(form.decimaliNord.value))
					return "Inserire solo valori numerici nei campi relativi alle coordinate in Gradi Nord e Est\n";
				
				if (parseInt(form.gradiEst.value,10)<0 || parseInt(form.minutiEst.value,10)<0 || parseInt(form.decimaliEst.value,10)<0
		        		|| parseInt(form.gradiNord.value,10)<0 || parseInt(form.minutiNord.value,10)<0 || parseInt(form.decimaliNord.value,10)<0)
					return "Inserire solo valori numerici positivi nei campi relativi alle coordinate in Gradi Nord e Est\n";
					
				if (parseInt(form.minutiEst.value,10)>59 || parseInt(form.minutiNord.value,10)>59)
					return "I minuti devono essere numeri compresi fra 0 e 59\n";
			}
	    }
		if (coordGradi=='DMS')
		{
			//Gradi, minuti e secondi
			if (form.gradiEst.value == '' || form.minutiEst.value == '' || form.secondiEst.value == '' || form.decimaliEst.value == ''
        		|| form.gradiNord.value == '' || form.minutiNord.value == '' || form.secondiNord.value == '' || form.decimaliNord.value == '')
				return "Valorizzare tutti i campi relativi alle coordinate in Gradi Nord e Est oppure lasciare tutti i campi vuoti\n";
			else
			{
				if (isNotNumber(form.gradiEst.value) || isNotNumber(form.minutiEst.value) || isNotNumber(form.secondiEst.value) || isNotNumber(form.decimaliEst.value)
	        		|| isNotNumber(form.gradiNord.value) || isNotNumber(form.minutiNord.value) || isNotNumber(form.secondiNord.value) || isNotNumber(form.decimaliNord.value))
					return "Inserire solo valori numerici nei campi relativi alle coordinate in Gradi Nord e Est\n";
				
				if (parseInt(form.gradiEst.value,10)<0 || parseInt(form.minutiEst.value,10)<0 || parseInt(form.secondiEst.value,10)<0 || parseInt(form.decimaliEst.value,10)<0
		        		|| parseInt(form.gradiNord.value,10)<0 || parseInt(form.minutiNord.value,10)<0 || parseInt(form.secondiNord.value,10)<0 || parseInt(form.decimaliNord.value,10)<0)
					return "Inserire solo valori numerici positivi nei campi relativi alle coordinate in Gradi Nord e Est\n";
					
				if (parseInt(form.minutiEst.value,10)>59 || parseInt(form.secondiEst.value,10)>59
		        		|| parseInt(form.minutiNord.value,10)>59 || parseInt(form.secondiNord.value,10)>59)
					return "I minuti ed i secondi devono essere numeri compresi fra 0 e 59\n";
			}
		}
        form.tipoGeoreferenziazione.value="M";                          
	}
	return '';
}

