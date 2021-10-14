function aggiornaCostoCheckboxTipo(form,ter)
{
  if (ter!=null)
  {
    if (form.pH.checked && form.CSC.checked && form.SO.checked && form.CaCO3.checked
        && form.Ca.checked && form.Mg.checked && form.K.checked && form.N.checked
        && form.P.checked)
    {
      form.Tipo[0].checked=true;
    }
    else
    {
      form.Tipo[1].checked=true;
    }
  }
  else
  {
    if (form.Ca.checked && form.Mg.checked && form.K.checked && form.N.checked
        && form.P.checked)
    {
      form.Tipo[0].checked=true;
    }
    else
    {
      form.Tipo[1].checked=true;
    }
  }
  aggiornaCostoCheckbox(form,ter);
}

function aggiornaCostoCheckboxMicro(form,ter)
{
  if (ter!=null)
  {
    if (form.Fe.checked && form.Mn.checked && form.Zn.checked && form.Cu.checked)
    {
      form.Micro[0].checked=true;
    }
    else
    {
      form.Micro[1].checked=true;
    }
  }
  else
  {
    if (form.Fe.checked && form.Mn.checked && form.Zn.checked && form.Cu.checked
        && form.B.checked)
    {
      form.Micro[0].checked=true;
    }
    else
    {
      form.Micro[1].checked=true;
    }
  }
  aggiornaCostoCheckbox(form,ter);
}

function aggiornaCostoCheckboxMetal(form, ter)
{
	/*if (form.FeTot.checked && form.MnTot.checked && form.ZnTot.checked &&
			form.CuTot.checked && form.BTot.checked && form.CdTot.checked &&
			form.CrTot.checked && form.NiTot.checked && form.PbTot.checked &&
			form.SrTot.checked && form.MetTot.checked)
  {
		//Se i checkbox relativi ai metalli pesanti sono tutti selezionati di default viene selezionato Si nel corrispondente radiobutton
	  form.MetPes[0].checked = true;
	}
	else
	{
		//Se i checkbox relativi ai metalli pesanti sono tutti selezionati di default viene selezionato No nel corrispondente radiobutton
	  form.MetPes[1].checked = true;
	}*/

	//AGRICHIM-38
	//se sono stati selezionati un certo numero di metalli pesanti (da parametro MTNM su tabella PARAMETRO) viene applicato una percentuale di sconto al costo dei singoli metalli (percentuale sconto da parametro MTPR su tabella PARAMETRO)
	var metalliPesantiConteggioSelezionati = 0;
	if (form.FeTot.checked) metalliPesantiConteggioSelezionati++;
	if (form.MnTot.checked) metalliPesantiConteggioSelezionati++;
	if (form.ZnTot.checked) metalliPesantiConteggioSelezionati++;
	if (form.CuTot.checked) metalliPesantiConteggioSelezionati++;
	//if (form.BTot.checked) metalliPesantiConteggioSelezionati++;
	if (form.CdTot.checked) metalliPesantiConteggioSelezionati++;
	if (form.CrTot.checked) metalliPesantiConteggioSelezionati++;
	if (form.NiTot.checked) metalliPesantiConteggioSelezionati++;
	if (form.PbTot.checked) metalliPesantiConteggioSelezionati++;
	//if (form.SrTot.checked) metalliPesantiConteggioSelezionati++;
	form.metalliPesantiConteggioSelezionati.value = metalliPesantiConteggioSelezionati;

  aggiornaCostoCheckbox(form,ter);
}

function calcolaPercentualeScontoMetal(form)
{
	var metalliPesantiConteggioSelezionati = parseInt(form.metalliPesantiConteggioSelezionati.value);
	if (metalliPesantiConteggioSelezionati == 0 || metalliPesantiConteggioSelezionati <= parseInt(form.METALLI_PESANTI_SCONTO_NUMERO.value))
	{

		return 0;
	}
	else
	{
		//se sono in questo caso devo modificare il il valore con la percentuale del 10%
	    return parseInt(form.METALLI_PESANTI_SCONTO_PERCENTUALE.value);
	}
	
}

function calcolaScontoMetal(form, importo)
{
	var percentualeScontoMetal = calcolaPercentualeScontoMetal(form);
	if (percentualeScontoMetal == 0) return parseFloat(importo);

	importo = parseFloat(importo);
	importo = Math.round(importo * 100) / 100;

	var importoScontato = importo * percentualeScontoMetal / 100;
	importoScontato = Math.round(importoScontato * 100) / 100;
	importoScontato = importo - importoScontato;
	
	return parseFloat(importoScontato);
}

function aggiornaCostoCheckbox(form,ter)
{
  var totale = 0;
  if (ter!=null)
  {
    //Siamo nei terreni
    if (form.Tipo[0].checked)
    {
      totale = parseFloat(totale) + parseFloat(form.ANA_PACCHETTO_TIPO.value);
    }
    else
    {
      if (form.Ca.checked && form.Mg.checked && form.K.checked && form.CSC.checked)
      {
        totale = parseFloat(totale) + parseFloat(form.ANA_PACCHETTO_COMP_SCAMBIO.value);
      }
      else
      {
        if ( form.Ca.checked )
        totale = parseFloat(totale) + parseFloat(form.Ca.value);
        if ( form.Mg.checked )
          totale = parseFloat(totale) + parseFloat(form.Mg.value);
        if ( form.K.checked )
          totale = parseFloat(totale) + parseFloat(form.K.value);
        if ( form.CSC.checked )
        totale = parseFloat(totale) + parseFloat(form.CSC.value);
      }

      if ( form.pH.checked )
          totale = parseFloat(totale) + parseFloat(form.pH.value);
      if ( form.N.checked )
        totale = parseFloat(totale) + parseFloat(form.N.value);
      if ( form.P.checked )
        totale = parseFloat(totale) + parseFloat(form.P.value);
      if ( form.SO.checked )
        totale = parseFloat(totale) + parseFloat(form.SO.value);
      if ( form.CaCO3.checked )
        totale = parseFloat(totale) + parseFloat(form.CaCO3.value);
    }

    if ( form.CaAtt.checked )
      totale = parseFloat(totale) + parseFloat(form.CaAtt.value);

    if (form.fisMeccCodice.value=='Std')
    {
      totale = parseFloat(totale) + parseFloat(form.fisMecc[0].value);
    }
    if (form.fisMeccCodice.value=='4Fra')
    {
      totale = parseFloat(totale) + parseFloat(form.fisMecc[1].value);
    }
    if (form.fisMeccCodice.value=='5Fra')
    {
      totale = parseFloat(totale) + parseFloat(form.fisMecc[2].value);
    }

    if ( form.Sal.checked )
      totale = parseFloat(totale) + parseFloat(form.Sal.value);

    if (form.Micro[0].checked)
    {
      totale = parseFloat(totale) + parseFloat(form.ANA_PACCHETTO_MICROELEMENTI.value);
    }
    else
    {
      if ( form.Fe.checked )
        totale = parseFloat(totale) + parseFloat(form.Fe.value);
      if ( form.Mn.checked )
        totale = parseFloat(totale) + parseFloat(form.Mn.value);
      if ( form.Zn.checked )
        totale = parseFloat(totale) + parseFloat(form.Zn.value);
      if ( form.Cu.checked )
        totale = parseFloat(totale) + parseFloat(form.Cu.value);
    }
    if ( form.B.checked )
      totale = parseFloat(totale) + parseFloat(form.B.value);
    /*if ( form.Um.checked )
      totale = parseFloat(totale) + parseFloat(form.Um.value);*/
  }
  else
  {
    //Materiali diversi dai terreni
    if (form.Tipo[0].checked)
    {
      totale = parseFloat(totale) + parseFloat(form.ANA_PACCHETTO_TIPO.value);
    }
    else
    {
      if ( form.Ca.checked )
      totale = parseFloat(totale) + parseFloat(form.Ca.value);
      if ( form.Mg.checked )
        totale = parseFloat(totale) + parseFloat(form.Mg.value);
      if ( form.K.checked )
        totale = parseFloat(totale) + parseFloat(form.K.value);
      if ( form.N.checked )
        totale = parseFloat(totale) + parseFloat(form.N.value);
      if ( form.P.checked )
        totale = parseFloat(totale) + parseFloat(form.P.value);
    }
    if (form.Micro[0].checked)
    {
      totale = parseFloat(totale) + parseFloat(form.ANA_PACCHETTO_MICROELEMENTI.value);
    }
    else
    {
      if ( form.Fe.checked )
        totale = parseFloat(totale) + parseFloat(form.Fe.value);
      if ( form.Mn.checked )
        totale = parseFloat(totale) + parseFloat(form.Mn.value);
      if ( form.Zn.checked )
        totale = parseFloat(totale) + parseFloat(form.Zn.value);
      if ( form.Cu.checked )
        totale = parseFloat(totale) + parseFloat(form.Cu.value);
      if ( form.B.checked )
        totale = parseFloat(totale) + parseFloat(form.B.value);
    }
    /*if ( form.Um.checked )
      totale = parseFloat(totale) + parseFloat(form.Um.value);*/
  }

  //Metalli pesanti (non dipendono dal tipo di materiale)
  /*if (form.MetPes[0].checked)
  {
	//E' stato scelto Si radiobutton Analisi completa metalli pesanti

    totale = parseFloat(totale) + parseFloat(form.ANA_PACCHETTO_METALLI_PESANTI.value);
  }
  else
  {*/
  	//E' stato scelto No radiobutton Analisi completa metalli pesanti

	  if (form.FeTot.checked)
		{
	  	totale = parseFloat(totale) + calcolaScontoMetal(form, form.FeTot.value);
		}
	  if (form.MnTot.checked)
		{
	    totale = parseFloat(totale) + calcolaScontoMetal(form, form.MnTot.value);
		}
	  if (form.ZnTot.checked)
		{
	    totale = parseFloat(totale) + calcolaScontoMetal(form, form.ZnTot.value);
		}
	  if (form.CuTot.checked)
		{
	    totale = parseFloat(totale) + calcolaScontoMetal(form, form.CuTot.value);
		}
	  if (form.CdTot.checked)
		{
	    totale = parseFloat(totale) + calcolaScontoMetal(form, form.CdTot.value);
		}
	  if (form.CrTot.checked)
		{
	    totale = parseFloat(totale) + calcolaScontoMetal(form, form.CrTot.value);
		}
	  if (form.NiTot.checked)
		{
	    totale = parseFloat(totale) + calcolaScontoMetal(form, form.NiTot.value);
		}
	  if (form.PbTot.checked)
		{
	    totale = parseFloat(totale) + calcolaScontoMetal(form, form.PbTot.value);
		}
	  /*if (form.MetTot.checked)
		{
	    totale = parseFloat(totale) + parseFloat(form.MetTot.value);
		}
  }*/

  // La seguente riga serve per aggirare problemi marci
  // delle funzioni di somma e sottrazione javascript
  totale = Math.round(totale * 100) / 100;
  totale = sprintf("%.2d", totale);
  form.costoAnalisi.value = totale.replace(".",",");
}

function analisiCompletaCheck(controllo,add)
{
  // Questa funzione, facilmente riutilizzabile, accetta tre parametri input
  // (tutti obbligatori, ma non c'è nessun controllo):
  //
  // controllo - corrisponde al singolo controllo da aggiungere/sottrarre
  // add - corrisponde ad un valore true/false che indica se aggiungere (true)
  //       oppure sottrarre (false) il controllo al/dal totale

  // "La seguente equazione booleana è quanto di più sopraffino io abbia scritto
  // tra CONC e AGCHIM" (Michele, 8/4/2003)
  if (controllo.checked == !add)
    controllo.checked=!controllo.checked;
}

function aggiornaCostoRadio(controlloCorrente, controlloUltimo, totale)
{
  // Analoga a aggiornaCosto come signature, questa funzione serve a gestire
  // il costo totale accettando come input un controllo di tipo radio
  // Per il corretto funzionamento, è necessario che nel form sia previsto un
  // <input type="hidden" name=... value="0"> per poter memorizzare l'ultimo
  // valore assunto dal controllo radio. Tale controllo compare nei parametri
  // di input di questa funzione, che sono:
  //
  // controlloCorrente - l'elemento radio selezionato (vedere più sotto per dettagli)
  // controlloUltimo - l'hidden input di cui sopra
  // totale - il controllo contenente il totale del costo delle analisi

  totale.value=totale.value.replace(",",".");
  totale.value = parseFloat(totale.value) - parseFloat(controlloUltimo.value);
  totale.value = parseFloat(totale.value) + parseFloat(controlloCorrente.value);
  totale.value=Math.round(totale.value * 100)/100;
  totale.value=sprintf("%.2d",totale.value);
  totale.value=totale.value.replace(".",",");
  controlloUltimo.value = controlloCorrente.value;
}

// Le funzioni in questo blocco sono caratteristiche di questa pagina, anche
// se non dovrebbero essere difficilmente riutilizzabili... credo...
function analisiCompletaMicroelementi(form,ter)
{
  // Questa funzione si appoggia alla funzione analisiCompletaCheck
  // L'argomento (obbligatorio) che riceve è il form da cui prelevare
  // i campi che interessano

  analisiCompletaCheck(form.Fe,form.Micro[0].checked);
  analisiCompletaCheck(form.Mn,form.Micro[0].checked);
  analisiCompletaCheck(form.Zn,form.Micro[0].checked);
  analisiCompletaCheck(form.Cu,form.Micro[0].checked);

  if (ter==null)
  {
    analisiCompletaCheck(form.B,form.Micro[0].checked);
  }
  aggiornaCostoCheckbox(form,ter);
}

function analisiTipo(form,ter)
{
  // Questa funzione (come la precedente) si appoggia alla funzione
  // analisiCompletaCheck
  // L'argomento (obbligatorio) che riceve è il form da cui prelevare
  // i campi che interessano
  // L'argomento non obbligatorio se presente siginifica che sto lavorando con i
  // terreni

  if (ter!=null)
  {
    analisiCompletaCheck(form.pH,form.Tipo[0].checked);
    analisiCompletaCheck(form.CSC,form.Tipo[0].checked);
    analisiCompletaCheck(form.SO,form.Tipo[0].checked);
    analisiCompletaCheck(form.CaCO3,form.Tipo[0].checked);
  }
  analisiCompletaCheck(form.Ca,form.Tipo[0].checked);
  analisiCompletaCheck(form.Mg,form.Tipo[0].checked);
  analisiCompletaCheck(form.K,form.Tipo[0].checked);
  analisiCompletaCheck(form.N,form.Tipo[0].checked);
  analisiCompletaCheck(form.P,form.Tipo[0].checked);

  aggiornaCostoCheckbox(form,ter);
}

/*function analisiMetalliPesanti(form,ter)
{
  // Questa funzione si appoggia alla funzione analisiCompletaCheck
  // L'argomento (obbligatorio) che riceve è il form da cui prelevare
  // i campi che interessano

  analisiCompletaCheck(form.FeTot, form.MetPes[0].checked);
  analisiCompletaCheck(form.MnTot, form.MetPes[0].checked);
  analisiCompletaCheck(form.ZnTot, form.MetPes[0].checked);
  analisiCompletaCheck(form.CuTot, form.MetPes[0].checked);
  analisiCompletaCheck(form.BTot, form.MetPes[0].checked);
  analisiCompletaCheck(form.CdTot, form.MetPes[0].checked);
  analisiCompletaCheck(form.CrTot, form.MetPes[0].checked);
  analisiCompletaCheck(form.NiTot, form.MetPes[0].checked);
  analisiCompletaCheck(form.PbTot, form.MetPes[0].checked);
  analisiCompletaCheck(form.SrTot, form.MetPes[0].checked);
  analisiCompletaCheck(form.MetTot, form.MetPes[0].checked);

  aggiornaCostoCheckbox(form, ter);
}*/

/**
 * Il valore di pagina mi indica da dove è stata invocata questa funzione:
 * pagina = 1: terrenoTipoAnalisi.htm
 * pagina = 2: erbaceeTipoAnalisi.htm
 * pagina = 3: foglieFruttaTipoAnalisi.htm (Foglie Fruttiferi e Vite)
 * pagina = 4: foglieFruttaTipoAnalisi.htm (Frutta)
 * */
function costoTotale(form,ter)
{
  if (ter!=null)
  {
    if (form.pH.checked) aggiornaCostoCheckbox(form,ter);
    if (form.CSC.checked) aggiornaCostoCheckbox(form,ter);
    if (form.SO.checked) aggiornaCostoCheckbox(form,ter);
    if (form.CaCO3.checked) aggiornaCostoCheckbox(form,ter);
    if (form.CaAtt.checked) aggiornaCostoCheckbox(form,ter);
    if (form.Sal.checked) aggiornaCostoCheckbox(form,ter);
    for (i=0; i<form.fisMecc.length; i++)
    {
      if (form.fisMecc[i].checked)
      {
        aggiornaCostoCheckbox(form,ter);
        form.fisMeccUltimo.value=form.fisMecc[i].value;
        break;
      }
    }
  }
  if (form.Ca.checked) aggiornaCostoCheckbox(form,ter);
  if (form.Mg.checked) aggiornaCostoCheckbox(form,ter);
  if (form.K.checked) aggiornaCostoCheckbox(form,ter);
  if (form.N.checked) aggiornaCostoCheckbox(form,ter);
  if (form.P.checked) aggiornaCostoCheckbox(form,ter);
  //if (form.Um.checked) aggiornaCostoCheckbox(form,ter);
  if (form.Fe.checked) aggiornaCostoCheckbox(form,ter);
  if (form.Mn.checked) aggiornaCostoCheckbox(form,ter);
  if (form.Zn.checked) aggiornaCostoCheckbox(form,ter);
  if (form.Cu.checked) aggiornaCostoCheckbox(form,ter);
  if (form.B.checked) aggiornaCostoCheckbox(form,ter);
  
  //Metalli pesanti
  if (form.FeTot.checked) aggiornaCostoCheckboxMetal(form,ter);
  if (form.MnTot.checked) aggiornaCostoCheckboxMetal(form,ter); 
  if (form.ZnTot.checked) aggiornaCostoCheckboxMetal(form,ter);
  if (form.CuTot.checked) aggiornaCostoCheckboxMetal(form,ter);
  if (form.CdTot.checked) aggiornaCostoCheckboxMetal(form,ter);
  if (form.CrTot.checked) aggiornaCostoCheckboxMetal(form,ter);
  if (form.NiTot.checked) aggiornaCostoCheckboxMetal(form,ter);
  if (form.PbTot.checked) aggiornaCostoCheckboxMetal(form,ter);
  //if (form.SrTot.checked) aggiornaCostoCheckbox(form,ter);
  //if (form.MetTot.checked) aggiornaCostoCheckbox(form,ter);
}

/**
 * La funzione seguente è utilizzata per il dettaglio dei costi
 */
function popCostiDet(str,form,pagina,x,y)
{
  var parametri="?totale="+form.costoAnalisi.value;
  parametri+="&ANA_PACCHETTO_TIPO="+sprintf("%.2d",form.ANA_PACCHETTO_TIPO.value);
  parametri+="&ANA_PACCHETTO_MICROELEMENTI="+sprintf("%.2d",form.ANA_PACCHETTO_MICROELEMENTI.value);
  //parametri+="&ANA_PACCHETTO_METALLI_PESANTI=" + sprintf("%.2d", form.ANA_PACCHETTO_METALLI_PESANTI.value);

  if (pagina==1)
  {
    parametri+="&ANA_PACCHETTO_COMP_SCAMBIO="+sprintf("%.2d",form.ANA_PACCHETTO_COMP_SCAMBIO.value);
    if (form.pH.checked) parametri+="&pH="+sprintf("%.2d",form.pH.value);
    if (form.CSC.checked) parametri+="&CSC="+sprintf("%.2d",form.CSC.value);
    if (form.SO.checked) parametri+="&SO="+sprintf("%.2d",form.SO.value);
    if (form.CaCO3.checked) parametri+="&CaCO3="+sprintf("%.2d",form.CaCO3.value);
    if (form.CaAtt.checked) parametri+="&CaAtt="+sprintf("%.2d",form.CaAtt.value);
    if (form.Sal.checked) parametri+="&Sal="+sprintf("%.2d",form.Sal.value);
    if (form.fisMecc[0].checked)
      parametri+="&Std="+sprintf("%.2d",form.fisMecc[0].value);
    if (form.fisMecc[1].checked)
      parametri+="&4Fra="+sprintf("%.2d",form.fisMecc[1].value);
    if (form.fisMecc[2].checked)
      parametri+="&5Fra="+sprintf("%.2d",form.fisMecc[2].value);
  }
  if (form.Ca.checked) parametri+="&Ca="+sprintf("%.2d",form.Ca.value);
  if (form.Mg.checked) parametri+="&Mg="+sprintf("%.2d",form.Mg.value);
  if (form.K.checked) parametri+="&K="+sprintf("%.2d",form.K.value);
  if (form.N.checked) parametri+="&N="+sprintf("%.2d",form.N.value);
  if (form.P.checked) parametri+="&P="+sprintf("%.2d",form.P.value);
  //if (form.Um.checked) parametri+="&Um="+sprintf("%.2d",form.Um.value);
  if (pagina!=4)
  {
    if (form.Fe.checked) parametri+="&Fe="+sprintf("%.2d",form.Fe.value);
    if (form.Mn.checked) parametri+="&Mn="+sprintf("%.2d",form.Mn.value);
    if (form.Zn.checked) parametri+="&Zn="+sprintf("%.2d",form.Zn.value);
    if (form.Cu.checked) parametri+="&Cu="+sprintf("%.2d",form.Cu.value);
    if (form.B.checked) parametri+="&B="+sprintf("%.2d",form.B.value);
  }
  
  //Metalli pesanti
  if (form.FeTot.checked) parametri += "&FeTot=" + sprintf("%.2d", calcolaScontoMetal(form, form.FeTot.value));
  if (form.MnTot.checked) parametri += "&MnTot=" + sprintf("%.2d", calcolaScontoMetal(form, form.MnTot.value));  
  if (form.ZnTot.checked) parametri += "&ZnTot=" + sprintf("%.2d", calcolaScontoMetal(form, form.ZnTot.value));
  if (form.CuTot.checked) parametri += "&CuTot=" + sprintf("%.2d", calcolaScontoMetal(form, form.CuTot.value));
  if (form.CdTot.checked) parametri += "&CdTot=" + sprintf("%.2d", calcolaScontoMetal(form, form.CdTot.value));
  if (form.CrTot.checked) parametri += "&CrTot=" + sprintf("%.2d", calcolaScontoMetal(form, form.CrTot.value));
  if (form.NiTot.checked) parametri += "&NiTot=" + sprintf("%.2d", calcolaScontoMetal(form, form.NiTot.value));
  if (form.PbTot.checked) parametri += "&PbTot=" + sprintf("%.2d", calcolaScontoMetal(form, form.PbTot.value));
  //if (form.SrTot.checked) parametri += "&SrTot=" + sprintf("%.2d", calcolaScontoMetal(form, form.SrTot.value));
  //if (form.MetTot.checked) parametri += "&MetTot=" + sprintf("%.2d", form.MetTot.value);

  //alert(parametri);
  str+=parametri;
  pop(str,x,y);
}

function invia(form)
{
    form.costoAnalisiTot.value=form.costoAnalisi.value.replace(",",".");
    if (parseFloat(form.costoAnalisiTot.value) != 0 || (form.CaAtt!=null && form.CaAtt.checked)
        /*|| form.Um.checked*/)
      form.submit();
    else
    {
      alert("Attenzione!!\nNon è stata selezionata nessuna analisi");
    }
}