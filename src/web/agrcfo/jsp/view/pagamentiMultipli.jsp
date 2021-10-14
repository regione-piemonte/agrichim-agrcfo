<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*,java.text.*,java.util.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/pagamentiMultipli.htm");
%>
<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>
<jsp:useBean id="beanAnalisi" scope="application" class="it.csi.agrc.BeanAnalisi"/>

<jsp:useBean
     id="pagamenti"
     scope="page"
     class="it.csi.agrc.Pagamento">
<%
    pagamenti.setDataSource(dataSource);
    pagamenti.setAut(aut);
%>
</jsp:useBean>

<%
//carico i dati per un probabile pagamento con pagoPa e li metto in sessione
String IdRichieste = (String) request.getParameter("idRichiesta");
String sceltaPagatore = request.getParameter("sceltaPagatore")!=null?(String) request.getParameter("sceltaPagatore"):"";
String[] array_id_ric = IdRichieste.split(",");
Pagamento pagamentiMultipli = pagamenti.selectMultiploCompleto(IdRichieste,sceltaPagatore);
templ.bset("idProcedimento_accesso","58");
templ.bset("idProcedimento","58");
templ.bset("idFruitore","58");
templ.bset("cf",pagamentiMultipli.getCf()!=null?pagamentiMultipli.getCf():"");
templ.bset("ruolo","TITOLARE_CF@UTENTI_IRIDE2");
templ.bset("liv","2");
templ.bset("importo",pagamentiMultipli.getImporto()!=null?pagamentiMultipli.getImporto():"");
if(pagamentiMultipli.getFatturaRichiestaSN().equals("S")){
	templ.newBlock("attuaPagamento");
    templ.bset("sceltaPagatore",sceltaPagatore);
    templ.bset("idRichiesta",IdRichieste);
//     templ.bset("codicePagamento","FI05");
// 	templ.bset("pagatore_idAzienda",pagamentiMultipli.getPagatore_idAnagraficaAzienda()!=null?pagamentiMultipli.getPagatore_idAnagraficaAzienda():"");
// 	templ.bset("pagatore_nome",pagamentiMultipli.getPagatore_nome()!=null?pagamentiMultipli.getPagatore_nome():"");
// 	templ.bset("pagatore_cognome",pagamentiMultipli.getPagatore_cognome()!=null?pagamentiMultipli.getPagatore_cognome():"");
// 	templ.bset("pagatore_codiceFiscale",pagamentiMultipli.getPagatore_codiceFiscale()!=null?pagamentiMultipli.getPagatore_codiceFiscale():"");
// 	templ.bset("pagatore_ragioneSociale",pagamentiMultipli.getPagatore_ragioneSociale()!=null?pagamentiMultipli.getPagatore_ragioneSociale():"");
// 	templ.bset("pagatore_idUnicoPagatore",pagamentiMultipli.getPagatore_idUnicoPagatore()!=null?pagamentiMultipli.getPagatore_idUnicoPagatore():"");
// 	templ.bset("pagatore_piva",pagamentiMultipli.getPagatore_piva()!=null?pagamentiMultipli.getPagatore_piva():"");
// 	templ.bset("pagatore_email",pagamentiMultipli.getPagatore_email()!=null?pagamentiMultipli.getPagatore_email():"");
// 	templ.bset("pagatore_pec",pagamentiMultipli.getPagatore_pec()!=null?pagamentiMultipli.getPagatore_pec():"");
// 	templ.bset("versante_nome",pagamentiMultipli.getVersante_nome()!=null?pagamentiMultipli.getVersante_nome():"");
// 	templ.bset("versante_cognome",pagamentiMultipli.getVersante_cognome()!=null?pagamentiMultipli.getVersante_cognome():"");
// 	templ.bset("versante_codiceFiscale",pagamentiMultipli.getVersante_codiceFiscale()!=null?pagamentiMultipli.getVersante_codiceFiscale():"");
// 	templ.bset("versante_piva",pagamentiMultipli.getVersante_piva()!=null?pagamentiMultipli.getVersante_piva():"");
// 	templ.bset("versante_ragioneSociale",pagamentiMultipli.getVersante_ragioneSociale()!=null?pagamentiMultipli.getVersante_ragioneSociale():"");
// 	templ.bset("versante_idUnicoPagatore",pagamentiMultipli.getVersante_idUnicoPagatore()!=null?pagamentiMultipli.getVersante_idUnicoPagatore():"");
// 	templ.bset("versante_email",pagamentiMultipli.getVersante_email()!=null?pagamentiMultipli.getVersante_email():"");
// 	templ.bset("versante_pec",pagamentiMultipli.getVersante_pec()!=null?pagamentiMultipli.getVersante_pec():"");
}else if(pagamentiMultipli.getFatturaRichiestaSN().equals("N") && array_id_ric.length>1 && aut.getUtente().getTipoUtente()=='T'){
	templ.newBlock("sceltaPagamento");
	if(sceltaPagatore!=null && !sceltaPagatore.equals("")){
		if(sceltaPagatore.equals("Proprietario"))
	        templ.set("sceltaPagamento.checkedP","checked");
	    else if(sceltaPagatore.equals("Tecnico"))
	        templ.set("sceltaPagamento.checkedT","checked");
	    else if(sceltaPagatore.equals("Organizzazione"))
	        templ.set("sceltaPagamento.checkedO","checked");
		//il pagamento avviene sempre dopo che uno dei pagatori è stato selezionato
		templ.newBlock("attuaPagamento");
	}
	templ.bset("sceltaPagatore",sceltaPagatore);
    templ.bset("idRichiesta",IdRichieste);
//     templ.bset("codicePagamento","FI04");
//     templ.bset("pagatore_idAzienda",pagamentiMultipli.getScelta_pagatore_idAnagraficaAzienda()!=null?pagamentiMultipli.getScelta_pagatore_idAnagraficaAzienda():"");
//     templ.bset("pagatore_nome",pagamentiMultipli.getScelta_pagatore_nome()!=null?pagamentiMultipli.getScelta_pagatore_nome():"");
//     templ.bset("pagatore_cognome",pagamentiMultipli.getScelta_pagatore_cognome()!=null?pagamentiMultipli.getScelta_pagatore_cognome():"");
//     templ.bset("pagatore_codiceFiscale",pagamentiMultipli.getScelta_pagatore_codiceFiscale()!=null?pagamentiMultipli.getScelta_pagatore_codiceFiscale():"");
//     templ.bset("pagatore_ragioneSociale",pagamentiMultipli.getScelta_pagatore_ragioneSociale()!=null?pagamentiMultipli.getScelta_pagatore_ragioneSociale():"");
//     templ.bset("pagatore_idUnicoPagatore",pagamentiMultipli.getScelta_pagatore_idUnicoPagatore()!=null?pagamentiMultipli.getScelta_pagatore_idUnicoPagatore():"");
//     templ.bset("pagatore_piva",pagamentiMultipli.getScelta_pagatore_piva()!=null?pagamentiMultipli.getScelta_pagatore_piva():"");
//     templ.bset("pagatore_email",pagamentiMultipli.getScelta_pagatore_email()!=null?pagamentiMultipli.getScelta_pagatore_email():"");
//     templ.bset("pagatore_pec",pagamentiMultipli.getScelta_pagatore_pec()!=null?pagamentiMultipli.getScelta_pagatore_pec():"");
//     templ.bset("versante_nome",pagamentiMultipli.getScelta_versante_nome()!=null?pagamentiMultipli.getScelta_versante_nome():"");
//     templ.bset("versante_cognome",pagamentiMultipli.getScelta_versante_cognome()!=null?pagamentiMultipli.getScelta_versante_cognome():"");
//     templ.bset("versante_codiceFiscale",pagamentiMultipli.getScelta_versante_codiceFiscale()!=null?pagamentiMultipli.getScelta_versante_codiceFiscale():"");
//     templ.bset("versante_piva",pagamentiMultipli.getScelta_versante_piva()!=null?pagamentiMultipli.getScelta_versante_piva():"");
//     templ.bset("versante_ragioneSociale",pagamentiMultipli.getScelta_versante_ragioneSociale()!=null?pagamentiMultipli.getScelta_versante_ragioneSociale():"");
//     templ.bset("versante_idUnicoPagatore",pagamentiMultipli.getScelta_versante_idUnicoPagatore()!=null?pagamentiMultipli.getScelta_versante_idUnicoPagatore():"");
//     templ.bset("versante_email",pagamentiMultipli.getScelta_versante_email()!=null?pagamentiMultipli.getScelta_versante_email():"");
//     templ.bset("versante_pec",pagamentiMultipli.getScelta_versante_pec()!=null?pagamentiMultipli.getScelta_versante_pec():"");
}else if(pagamentiMultipli.getFatturaRichiestaSN().equals("N")){
	templ.newBlock("attuaPagamento");
	templ.bset("sceltaPagatore",sceltaPagatore);
    templ.bset("idRichiesta",IdRichieste);
//     templ.bset("codicePagamento","FI04");
//     templ.bset("pagatore_idAzienda",pagamentiMultipli.getPagatore_idAnagraficaAzienda()!=null?pagamentiMultipli.getPagatore_idAnagraficaAzienda():"");
//     templ.bset("pagatore_nome",pagamentiMultipli.getPagatore_nome()!=null?pagamentiMultipli.getPagatore_nome():"");
//     templ.bset("pagatore_cognome",pagamentiMultipli.getPagatore_cognome()!=null?pagamentiMultipli.getPagatore_cognome():"");
//     templ.bset("pagatore_codiceFiscale",pagamentiMultipli.getPagatore_codiceFiscale()!=null?pagamentiMultipli.getPagatore_codiceFiscale():"");
//     templ.bset("pagatore_ragioneSociale",pagamentiMultipli.getPagatore_ragioneSociale()!=null?pagamentiMultipli.getPagatore_ragioneSociale():"");
//     templ.bset("pagatore_idUnicoPagatore",pagamentiMultipli.getPagatore_idUnicoPagatore()!=null?pagamentiMultipli.getPagatore_idUnicoPagatore():"");
//     templ.bset("pagatore_piva",pagamentiMultipli.getPagatore_piva()!=null?pagamentiMultipli.getPagatore_piva():"");
//     templ.bset("pagatore_email",pagamentiMultipli.getPagatore_email()!=null?pagamentiMultipli.getPagatore_email():"");
//     templ.bset("pagatore_pec",pagamentiMultipli.getPagatore_pec()!=null?pagamentiMultipli.getPagatore_pec():"");
//     templ.bset("versante_nome",pagamentiMultipli.getVersante_nome()!=null?pagamentiMultipli.getVersante_nome():"");
//     templ.bset("versante_cognome",pagamentiMultipli.getVersante_cognome()!=null?pagamentiMultipli.getVersante_cognome():"");
//     templ.bset("versante_codiceFiscale",pagamentiMultipli.getVersante_codiceFiscale()!=null?pagamentiMultipli.getVersante_codiceFiscale():"");
//     templ.bset("versante_piva",pagamentiMultipli.getVersante_piva()!=null?pagamentiMultipli.getVersante_piva():"");
//     templ.bset("versante_ragioneSociale",pagamentiMultipli.getVersante_ragioneSociale()!=null?pagamentiMultipli.getVersante_ragioneSociale():"");
//     templ.bset("versante_idUnicoPagatore",pagamentiMultipli.getVersante_idUnicoPagatore()!=null?pagamentiMultipli.getVersante_idUnicoPagatore():"");
//     templ.bset("versante_email",pagamentiMultipli.getVersante_email()!=null?pagamentiMultipli.getVersante_email():"");
//     templ.bset("versante_pec",pagamentiMultipli.getVersante_pec()!=null?pagamentiMultipli.getVersante_pec():"");
}
 templ.bset("pagoPaWebAppUrl",aut.isPA()?beanParametriApplication.getAgripagopaIsPa():
     aut.isSpid()?beanParametriApplication.getAgripagopaIsSpid():beanParametriApplication.getAgripagopaIsNotPa());
// templ.bset("pageReferral",beanParametriApplication.getAgripagopaPageReferral());
// templ.bset("pageUrlAnnulla",beanParametriApplication.getAgripagopaAnnulla()+"?ritAnnPag=S");
// if(aut.getUtente().getTipoUtente()=='L')
//     templ.bset("tipoPagamento","M3");
// else
//     templ.bset("tipoPagamento","");
session.setAttribute("pagamento_importo",pagamentiMultipli.getImporto());
//metto in sessione una lista di long
String[] id_richieste = IdRichieste.split(",");
List<Long> id_richieste_long = new ArrayList<Long>();
for(String richiesta : id_richieste){
	if(richiesta.contains("A")) {
        String am = richiesta.substring(0,richiesta.length()-1);
        richiesta = am;
    }
	id_richieste_long.add(Long.parseLong(richiesta));
}
session.setAttribute("pagamento_idRichiesta",id_richieste_long);

//setto il codice fiscale del pagatore perché mi serve dopo per fare la richiesta di verifica pagamento
if(sceltaPagatore!=null && !sceltaPagatore.equals("")){
	if(pagamentiMultipli.getScelta_pagatore_codiceFiscale()!=null && !pagamentiMultipli.getScelta_pagatore_codiceFiscale().equals(""))
        aut.setCod_fiscale_pagatore(pagamentiMultipli.getScelta_pagatore_codiceFiscale());
    else if(pagamentiMultipli.getScelta_pagatore_piva()!=null && !pagamentiMultipli.getScelta_pagatore_piva().equals(""))
        aut.setCod_fiscale_pagatore(pagamentiMultipli.getScelta_pagatore_piva());
    else
        aut.setCod_fiscale_pagatore("");
}else{
	if(pagamentiMultipli.getPagatore_codiceFiscale()!=null && !pagamentiMultipli.getPagatore_codiceFiscale().equals(""))
	    aut.setCod_fiscale_pagatore(pagamentiMultipli.getPagatore_codiceFiscale());
	else if(pagamentiMultipli.getPagatore_piva()!=null && !pagamentiMultipli.getPagatore_piva().equals(""))
		aut.setCod_fiscale_pagatore(pagamentiMultipli.getPagatore_piva());
	else
		aut.setCod_fiscale_pagatore("");
}

templ.bset("idRichiesteMultiple",IdRichieste);
  double totale=Double.parseDouble(pagamentiMultipli.getImporto());
  if (aut.isSpedizioneFattura())  {
    try {
      double importo=Double.parseDouble(beanAnalisi.getImportoSpedizione());
      totale+=importo;
    } catch(Exception e) { }
  }
  DecimalFormat nf2 = new DecimalFormat("#0.00");
  templ.bset("totale",nf2.format(totale));


  session.setAttribute("aut",aut);
%>

<%= templ.text() %>
