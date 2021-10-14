function salva(form){
	var msg='';
    if (form.fatturaSN[0].checked){
    	var i;
    	for ( i=0; i<form.fatturare.length; i++)
    		if (form.fatturare[i].checked)
    			break;
    	if (i==form.fatturare.length)
    		msg=msg+'Selezionare l\'intestatario della fattura\n';

    	/**
    	 *  Il campo Partita IVA o Codice fiscale pu� contenere
    	 *  - sia una partita iva (quindi deve contenere solo numeri ed essere
    	 *    lungo 11)
    	 *  - sia un codice fiscale (quindi deve essere lungo 16 e bisogna fare
    	 *    dei controlli ulteriori sulla posizione delle lettere e dei numeri)
    	 *  a seconda che il tipo sia azienda o privato
    	 **/
      	if (form.fatturare[form.fatturare.length-1].checked){
			form.cfPartitaIva.value=form.cfPartitaIva.value.trim();
			form.ragioneSociale.value=form.ragioneSociale.value.trim();
			form.indirizzo.value=form.indirizzo.value.trim();
			lung = form.cfPartitaIva.value.length;
			if (lung!=11 && lung!=16)
				msg=msg+'Valorizzare una voce corretta in "Partita IVA o Codice fiscale"\n';
			else{
				form.cfPartitaIva.value = form.cfPartitaIva.value.toUpperCase();
				if (lung==11){
					msgTmp = controllaPartitaIVA(form.cfPartitaIva.value);
					if (msgTmp!='')
						msg=msg+'"Partita IVA o Codice fiscale" - '+msgTmp+'\n';
				}
				if (lung==16){
					msgTmp = controllaCodiceFiscale(form.cfPartitaIva.value);
					if (msgTmp!='')
						msg=msg+'"Partita IVA o Codice fiscale" - '+msgTmp+'\n';
				}
			}
			
			if (form.ragioneSociale.value == '')
				msg=msg+'Valorizzare la voce "Cognome e Nome o Ragione Sociale"\n';
			if (form.indirizzo.value == '')
				msg=msg+'Valorizzare la voce "Indirizzo"\n';
			if (!((form.cap.value.length == 5) && (isNumber(form.cap.value))) )
				msg=msg+'Valorizzare correttamente la voce "CAP"\n';
			if (form.comune.value == '')
				msg=msg+'Selezionare il "Comune"\n';
		}
      	var cod_destinatario = form.cod_destinatario.value.trim();
      	var pec = form.pec.value.trim();
      	if((cod_destinatario==null || cod_destinatario=="")&&(pec==null || pec==""))
      		msg=msg+'Valorizzare una voce tra "Codice Destinatario" e "PEC"\n';
      	if(cod_destinatario!=null && cod_destinatario!="" && cod_destinatario.length < 7)
      		msg=msg+'"Codice Destinatario" deve essere di 7 caratteri\n';
      	if(pec!=null && pec!="" && !controllaEmail(pec))
      		msg=msg+'"PEC" non valida\n';
    }

    if ( msg != '' ){
        alert( "Dati incompleti o non corretti\n\n"+msg );
        return false;
    }else
        return true;
}

function fatturaSNCheck(disabledSpedizione){
	if (document.fattura.fatturaSN[1].checked) {
//	    disableRadio(document.fattura.spedizione);
//	    document.fattura.spedizione[1].checked=true;
	    disableRadio(document.fattura.fatturare);
	    disableText(document.fattura.cfPartitaIva);
	    disableText(document.fattura.ragioneSociale);
	    disableText(document.fattura.indirizzo);
	    disableText(document.fattura.cap);
	    disableText(document.fattura.comune);
	    disableText(document.fattura.comuneDesc);
	    //PEC e cod_destinatario vengono trattati in maniera differente: non possono essere disabilitati come gli altri quindi faccio tutto qui
	    disabilitaText(document.fattura.pec);
	    disableText(document.fattura.pec);
	    disabilitaText(document.fattura.cod_destinatario);
	    disableText(document.fattura.cod_destinatario);
	
	    //Pulizia dati "Intestare fattura a" e "Altri estremi"
	    fatturareCheck(document.fattura, 'true');
	}  else  {
	  	alert("A decorrere dal 1 gennaio 2019 e' in vigore l'obbligo di emissione della fattura elettronica. Se intendete ricevere per questa richiesta la fattura elettronica tramite il Sistema di Interscambio nazionale, dovete inserire il vostro Codice Destinatario e la vostra PEC in basso in questa finestra."+
				"\nInoltre:\n"+
				"1) controllate la correttezza degli estremi di fatturazione (denominazione, indirizzo, Partita IVA e Codice Fiscale)\n"+
				"2) se l'azienda non e' censita, fornite entrambi i dati di Partita IVA e Codice fiscale aggiungendo i dati mancanti nelle note\n"+
				//"3) il pagamento deve essere effettuato dall'intestatario della fattura\n"+
				//"4) il pagamento deve essere effettuato dopo aver prenotato il campione e nella causale deve essere riportato il numero di richiesta analisi.\n"+
				//"Per informazioni potete contattare il Laboratorio all'indirizzo di posta elettronica agrochimico@regione.piemonte.it oppure telefonicamente ai numeri telefonici: 0174 701762 o 011 4323062.");
				"Per informazioni potete contattare il Laboratorio all'indirizzo di posta elettronica agrochimico@regione.piemonte.it oppure telefonicamente al numero telefonico: 011 4321009.");
	  /*
		alert("A decorrere dal 1 gennaio 2019 e\' in vigore l\'obbligo di emissione della fattura elettronica. " +
		  "Se intendete ricevere per questa richiesta la fattura elettronica tramite il Sistema di Interscambio " +
		  "nazionale dovete fornire il vostro Codice Destinatario e la vostra PEC in basso in questa finestra. " +//mail al Laboratorio Agrochimico
		  "Potete chiedere chiarimenti all\'indirizzo di posta elettronica agrochimico@regione.piemonte.it " +//inviare il codice
		  "oppure contattare telefonicamente il Laboratorio ai numeri telefonici: 011 4323062 o 011 4321009." +
		  "")//indicando sempre il numero di richiesta campione.*/
//		if (disabledSpedizione != 'S'){
//			document.fattura.spedizione[0].disabled=false;
//			document.fattura.spedizione[0].checked=true;
//		}
		enableRadio(document.fattura.fatturare);
		if(document.fattura.fatturare.value == 'P')
			document.getElementById("idPulsanteCercaComune").style.display = 'none';
		else if(document.fattura.fatturare.value != 'A'){
			disableText(document.fattura.cfPartitaIva);
			disableText(document.fattura.ragioneSociale);
			disableText(document.fattura.indirizzo);
			disableText(document.fattura.cap);
			disableText(document.fattura.comune);
			disableText(document.fattura.comuneDesc);
		}
  	}
}

function fatturareCheck(form, deseleziona){
	var i = 0;
	var isAltriEstremi = false;
	var campoFatturare;
	
	if (form.fatturare != null){
		if (form.fatturare[0]) {
			//E' un array di radiobutton con lo stesso nome
			for (i = 0; i < form.fatturare.length; i++) {
				if (form.fatturare[i].checked)	{
					campoFatturare = form.fatturare[i].value;
					if (deseleziona == 'true')
						form.fatturare[i].checked = false;
					else if (form.fatturare[i].value == 'A')
						isAltriEstremi = true;
				} 
			}  		
		}else{
			//C'è un solo radiobutton
			if (form.fatturare.checked) {
				campoFatturare = form.fatturare.value;
				if (deseleziona == 'true')
					form.fatturare.checked = false;
				else if (form.fatturare.value == 'A')
					isAltriEstremi = true;
			}
		}
	}

	if (isAltriEstremi){
		enableText(form.cfPartitaIva);
		enableText(form.ragioneSociale);
		enableText(form.indirizzo);
		enableText(form.cap);
		enableText(document.fattura.comune);
		enableText(document.fattura.comuneDesc);
		enableText(document.fattura.pec);
		enableText(document.fattura.cod_destinatario);
	  	document.getElementById("idPulsanteCercaComune").style.display = '';
	} else if(campoFatturare!='A') {
		disableText(form.cfPartitaIva);
		disableText(form.ragioneSociale);
		disableText(form.indirizzo);
		disableText(form.cap);
	    form.comune.value = '';
	    form.comuneDesc.value = '';
	    document.getElementById("idPulsanteCercaComune").style.display = 'none';
	    disableText(document.fattura.comune);
		disableText(document.fattura.comuneDesc);
		enableText(document.fattura.pec);
		enableText(document.fattura.cod_destinatario);
	} else { //significa che nessun campo fatturare a è stato selezionato
		disableText(form.cfPartitaIva);
		disableText(form.ragioneSociale);
		disableText(form.indirizzo);
		disableText(form.cap);
	    form.comune.value = '';
	    form.comuneDesc.value = '';
	    document.getElementById("idPulsanteCercaComune").style.display = 'none';
	    disableText(document.fattura.comune);
		disableText(document.fattura.comuneDesc);
		disableText(document.fattura.pec);
		disableText(document.fattura.cod_destinatario);
	}
}