/**
 * Funzione che apre una finestra di popup
 * Nome predefinito della finestra di popup: popUpAgrc
 * Dimensioni predefinite della finestra di popup: 600x400
 */
var hWin;
function popPdf(str,x,y,nome)
{
  if (x==null)
  {
    x=600;
    y=400;
  }
  if (nome==null)
    nome="popUpAgrcFO";
  
  var num=Math.floor((Math.random() * 10000) + 1);

  var left=(window.screen.availWidth-x)/2;
  var top=(window.screen.availHeight-y)/2;
  hWin = window.open(str+'?ver='+num,nome,'scrollbars=yes,resizable=yes,width='+x+',height='+y+',top='+top+',left='+left+',status=yes,location=no,toolbar=no');
  hWin.focus();
}

function pop(str,x,y,nome)
{
  if (x==null)
  {
    x=600;
    y=400;
  }
  if (nome==null)
    nome="popUpAgrcFO";

  var left=(window.screen.availWidth-x)/2;
  var top=(window.screen.availHeight-y)/2;
  hWin = window.open(str,nome,'scrollbars=yes,resizable=yes,width='+x+',height='+y+',top='+top+',left='+left+',status=yes,location=no,toolbar=no');
  hWin.focus();
}



/**
 * La funzione seguente � utilizzata per la modifica e la cancellazione:
 * se il parametro modo � valorizzato implica cancellazione,
 * in caso contrario modifica
 */
function popGes(str,form,modo,x,y,nome) {
    var valore;
    if (form.radiobutton == null) return;
    if (form.radiobutton[0])
    {   // entra qui se il radiobutton � un array
        // (un solo radiobutton NON � un array)
        for (i=0; i<form.radiobutton.length; i++)
        {
            if (form.radiobutton[i].checked)
            {
                valore=form.radiobutton[i].value;
                break;
            }
        }
    }
    else if (form.radiobutton.checked) valore=form.radiobutton.value;

    if (modo!= null)
    {
        form.cancella.value = valore;
        form.action=str;
        form.submit();
    }
    else
    {
        str+="?modifica="+valore;
        pop(str,x,y,nome);
    }
}

function risultatoAnalisi(destinazione,x,y,tipo){
	var inputElements = document.getElementsByName('radiobutton');
	td_richiesta = [];
	for(var i=0; inputElements[i]; ++i){
        if(inputElements[i].checked){
             td_richiesta.push(inputElements[i].parentElement.children);
             break;
        }
    }
	if(td_richiesta[0].descMateriale.value=="ALTRE MATRICI"){
		alert("Il risultato dell'analisi delle matrici diverse dai terreni deve essere richiesto direttamente al Laboratorio");
	}else if (stato40Selected=='40') 
		pop(destinazione,x,y,tipo); 
	else 
		alert('Il risultato dell\'analisi non è ancora disponibile.\nIl referto non è ancora stato emesso.');
	
}

function copiaRichiesta(){
	var inputElements = document.getElementsByName('radiobutton');
	td_richiesta = [];
	for(var i=0; inputElements[i]; ++i){
        if(inputElements[i].checked){
             td_richiesta.push(inputElements[i].parentElement.children);
             break;
        }
    }
	if(td_richiesta[0].descMateriale.value=="ALTRE MATRICI")
		alert("La copia della richiesta non e' possibile per le altre matrici");
	else
		document.elencoCampioni.submit();
}



/**
 * La funzione seguente � utilizzata per il dettaglio
 */
function popGesDet(str,form,x,y,param) {
    var valore,descMateriale;
    if (form.radiobutton == null) return;
    if (form.radiobutton[0]){ 
    	// entra qui se il radiobutton � un array (un solo radiobutton NON � un array)
        for (i=0; i<form.radiobutton.length; i++) {
            if (form.radiobutton[i].checked) {
                valore=form.radiobutton[i].value;
                break;
            }
        }
    }else if (form.radiobutton.checked) 
    	valore=form.radiobutton.value;
    str+="?dettaglio="+valore;
    if (param != null) 
    	str+="&sedeScelta="+param;
    pop(str,x,y);
}

/**
 * Le variabili e le funzioni seguenti sono utilizzate
 * per recuperare il comune selezionato nel popup (codice istat e descrizione)
 */
var ctrlCodiceIstat, ctrlDescrizione;
function popComune(istat,descrizione)
{
  ctrlCodiceIstat=istat;
  ctrlDescrizione=descrizione;
  var url='../view/comunePOP.jsp';
  if (istat.value != '') url=url+"?istatSearch="+istat.value;
  pop(url,400,200,'comunePOPFO');
}
function leggiComune(controllo)
{
  var indice=controllo.selectedIndex;
  ctrlCodiceIstat.value=controllo[indice].value;
  ctrlDescrizione.value=controllo[indice].text;
}

/**
 * I metodi seguenti sono utilizzati per abilitare/disabilitare
 * i controlli dei form (attualmente implementati per type="text"
 * e type="radio"
 */
function disableText(control)
{
  control.disabled=true;
  control.style.color="#808080";
  control.style.backgroundColor="#F0F0EF";
}
function enableText(control)
{
  control.disabled=false;
  control.style.color="#000000";
  control.style.backgroundColor="#FFFFFF";
}

function disableRadio(control)
{
  for(var i=0;i<control.length;i++)
    control[i].disabled=true;
}
function enableRadio(control)
{
  for(var i=0;i<control.length;i++)
    control[i].disabled=false;
}


/**
 * Abilita un campo di testo
 * */
function abilitaText(text)
{
  text.disabled=false;
}

/**
 * Disabilita un campo di testo
 * */
function disabilitaText(text)
{
  text.value="";
  text.disabled=true;
}

/**
 * Abilita una combo
 * */
function abilitaCombo(select)
{
  select.disabled=false;
}

/**
 * Disabilita una combo
 * */
function disabilitaCombo(select)
{
  select.options.selectedIndex=0;
  select.disabled=true;
}

/**
 * Usata nell'onload di una pagina: a seconda del valore di un radio
 * abilita o disabilita una combo
 * */
function controllaRadioCombo(radio,select)
{
  if (radio[0].checked) disabilitaCombo(select);
  else abilitaCombo(select);
}

/**
 * Usata nell'onload di una pagina: a seconda del valore di un radio
 * abilita o disabilita un campo di testo
 * */
function controllaRadioText(radio,text,input)
{
  if (input==null) input=0;
  if (radio[input].checked) abilitaText(text);
  else disabilitaText(text);
}
function controllaEmail(mail){
	var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return re.test(mail);
}
function infoPrivacy(url){
	var info1 = document.getElementById("informativa1").checked;
	var info2 = document.getElementById("informativa2").checked;
	if(!info1 || !info2)
		alert("Per completare l'invio della richiesta e' necessario confermare la visione dell'informativa e l'assenso al trattamento dei dati personali");
	else if (confirm('Si ricorda che, dopo aver inviato la richiesta di analisi al laboratorio,\nnon sara\' piu\' possibile effettuare modifiche sulla stessa.\n\nSi conferma la scelta?'))
		window.location.href=url;
}