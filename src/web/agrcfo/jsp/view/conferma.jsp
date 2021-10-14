<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*,java.text.*,java.util.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/conferma.htm");
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

<jsp:useBean
     id="analisi"
     scope="page"
     class="it.csi.agrc.Analisi">
    <%
      analisi.setDataSource(dataSource);
      analisi.setAut(aut);
      analisi.setIdRichiesta(aut.getIdRichiestaCorrente());
    %>
</jsp:useBean>

<%
//carico i dati per un probabile pagamento con pagoPa e li metto in sessione
if(aut.getIdRichiestaCorrente()==-1 || aut.getIdRichiestaCorrente()==0)
	aut.setIdRichiestaCorrente(Long.parseLong((String)request.getParameter("idRichiesta")));

Pagamento p = pagamenti.select(""+aut.getIdRichiestaCorrente());
analisi.select(""+aut.getIdRichiestaCorrente());
templ.bset("idProcedimento_accesso","58");
templ.bset("idProcedimento","58");
templ.bset("idFruitore","58");
if(analisi.getCodiciAnalisi().get(0).equals("AltreM")){
 	if(p.getFatturaRichiestaSN().equals("S"))
 	    templ.bset("codicePagamento","FI15");
 	else
 	    templ.bset("codicePagamento","FI14");
}else {
	if(p.getFatturaRichiestaSN().equals("S"))
        templ.bset("codicePagamento","FI05");
    else
        templ.bset("codicePagamento","FI04");
}
templ.bset("cf",p.getCf()!=null?p.getCf():"");
templ.bset("ruolo","TITOLARE_CF@UTENTI_IRIDE2");
templ.bset("liv","2");
templ.bset("importo",p.getImporto()!=null?p.getImporto():"");
templ.bset("pagatore_idAzienda",p.getPagatore_idAnagraficaAzienda()!=null?p.getPagatore_idAnagraficaAzienda():"");
templ.bset("pagatore_nome",p.getPagatore_nome()!=null?p.getPagatore_nome():"");
templ.bset("pagatore_cognome",p.getPagatore_cognome()!=null?p.getPagatore_cognome():"");
templ.bset("pagatore_codiceFiscale",p.getPagatore_codiceFiscale()!=null?p.getPagatore_codiceFiscale():"");
templ.bset("pagatore_ragioneSociale",p.getPagatore_ragioneSociale()!=null?p.getPagatore_ragioneSociale():"");
templ.bset("pagatore_idUnicoPagatore",p.getPagatore_idUnicoPagatore()!=null?p.getPagatore_idUnicoPagatore():"");
templ.bset("pagatore_piva",p.getPagatore_piva()!=null?p.getPagatore_piva():"");
templ.bset("pagatore_email",p.getPagatore_email()!=null?p.getPagatore_email():"");
templ.bset("pagatore_pec",p.getPagatore_pec()!=null?p.getPagatore_pec():"");
templ.bset("versante_nome",p.getVersante_nome()!=null?p.getVersante_nome():"");
templ.bset("versante_cognome",p.getVersante_cognome()!=null?p.getVersante_cognome():"");
templ.bset("versante_codiceFiscale",p.getVersante_codiceFiscale()!=null?p.getVersante_codiceFiscale():"");
templ.bset("versante_piva",p.getVersante_piva()!=null?p.getVersante_piva():"");
templ.bset("versante_ragioneSociale",p.getVersante_ragioneSociale()!=null?p.getVersante_ragioneSociale():"");
templ.bset("versante_idUnicoPagatore",p.getVersante_idUnicoPagatore()!=null?p.getVersante_idUnicoPagatore():"");
templ.bset("versante_email",p.getVersante_email()!=null?p.getVersante_email():"");
templ.bset("versante_pec",p.getVersante_pec()!=null?p.getVersante_pec():"");
templ.bset("pagoPaWebAppUrl",aut.isPA()?beanParametriApplication.getAgripagopaIsPa():
	aut.isSpid()?beanParametriApplication.getAgripagopaIsSpid():beanParametriApplication.getAgripagopaIsNotPa());
templ.bset("pageReferral",beanParametriApplication.getAgripagopaPageReferral());
templ.bset("pageUrlAnnulla",beanParametriApplication.getAgripagopaAnnulla()+"?ritAnnPag=S");
if(aut.getUtente().getTipoUtente()=='L')
	templ.bset("tipoPagamento","M3");
else
	templ.bset("tipoPagamento","");
session.setAttribute("pagamento_importo",p.getImporto());
//metto in sessione una lista di long
List<Long> id_richieste = new ArrayList<Long>();
id_richieste.add(aut.getIdRichiestaCorrente());
session.setAttribute("pagamento_idRichiesta",id_richieste);
// session.setAttribute("pagamento_idRichiesta",aut.getIdRichiestaCorrente());

//setto il codice fiscale del pagatore perché mi serve dopo per fare la richiesta di verifica pagamento
if(p.getPagatore_codiceFiscale()!=null && !p.getPagatore_codiceFiscale().equals(""))
    aut.setCod_fiscale_pagatore(p.getPagatore_codiceFiscale());
else if(p.getPagatore_piva()!=null && !p.getPagatore_piva().equals(""))
	aut.setCod_fiscale_pagatore(p.getPagatore_piva());
else
	aut.setCod_fiscale_pagatore("");
  
templ.bset("numeroIdentificativo",""+aut.getIdRichiestaCorrente());
  double totale=aut.getCostoAnalisi()!=0?aut.getCostoAnalisi():Double.parseDouble(p.getImporto());
  if (aut.isSpedizioneFattura())  {
    try {
      double importo=Double.parseDouble(beanAnalisi.getImportoSpedizione());
      totale+=importo;
    } catch(Exception e) { }
  }
  DecimalFormat nf2 = new DecimalFormat("#0.00");
  templ.bset("totale",nf2.format(totale));


/**
  * Imposto la fase a 0 ed il numero di richiesta a -1. Questo per evitare che
  * ci siano dei problemi dovuti all'utente che tenta di tornare alle pagine
  * precedenti utilizzando il indietro del borwser
  **/
  aut.setFase((byte)6);
  aut.setIdRichiestaCorrente(-1l);
  session.setAttribute("aut",aut);

  templ.bset("tipoMateriale",aut.getCodMateriale());


  templ.bset("VBCC",beanParametriApplication.getVBCC(),null);

  templ.bset("BOBA",beanParametriApplication.getBOBA(),null);

  templ.bset("GFEP",beanParametriApplication.getGFEP(),null);
%>

<%= templ.text() %>
