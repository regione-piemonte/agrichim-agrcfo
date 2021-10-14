<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,
                 it.csi.jsf.htmpl.*,
                 it.csi.cuneo.*,
                 java.util.*"
                 isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/tipoCampione.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="beanTipoCampione"
     scope="application"
     class="it.csi.agrc.BeanTipoCampione"/>

<jsp:useBean
     id="tipoCampione"
     scope="request"
     class="it.csi.agrc.TipoCampione">
    <%
      tipoCampione.setDataSource(dataSource);
      tipoCampione.setAut(aut);
      if (aut.getFase()>0)
      {
         /**
          * Se la fase è maggiore di 0 vado a leggere i dati riguardanti
          * la prima fase, inoltre vado a leggere la fase corrente che imposterò
          * nel bean di sessione
          */
        tipoCampione.setIdRichiesta(aut.getIdRichiestaCorrente());
        tipoCampione.select();
        session.setAttribute("aut",aut);
      }
    %>
</jsp:useBean>

<!--
  Il file che includo serve a gestire gli errori dovuti al funzionamento non
  corretto del javascript
-->
<%@ include file="/jsp/view/problemiJavascript.inc" %>

<%
		//Pulizia session
		session.removeAttribute(Constants.SESSION.IS_PRESENTE_ANAGRAFE);

   /**
    * Leggo i parametri che si tovano nel bean
    * */
   String istatComune=tipoCampione.getIstatComune();
   String comune=tipoCampione.getDescComune();
   String etichettaCampione=tipoCampione.getEtichettaCampione();
   String note=tipoCampione.getNote();
   String codMateriale=tipoCampione.getCodMateriale();
   String codLaboratorio=tipoCampione.getCodLaboratorio();
   String codModalitaConsegna=tipoCampione.getCodModalitaConsegna();
   String codiceMisuraPsr = tipoCampione.getCodiceMisuraPsr();
   String noteMisuraPsr = tipoCampione.getNoteMisuraPsr();

   /**
    * Se qualche parametro è nullo devo impostarlo a vuoto altrimenti
    * htmpl si arrabbia
    * */
   if (istatComune==null) istatComune="";
   if (comune==null) comune="";
   if (etichettaCampione==null) etichettaCampione="";
   if (note==null) note="";
   codMateriale = (codMateriale == null && beanTipoCampione.getCodMateriale().length > 0) ? Constants.MATERIALE.CODICE_MATERIALE_TERRENI : codMateriale;
   if (codMateriale==null) codMateriale="";
   if (codLaboratorio==null) codLaboratorio="";
   if (codModalitaConsegna==null) codModalitaConsegna="";
   if (codiceMisuraPsr == null) codiceMisuraPsr = "";
   if (noteMisuraPsr == null) noteMisuraPsr = "";

   templ.bset("istatComune",istatComune);
   templ.bset("comune",comune);
   templ.bset("etichettaCampione",etichettaCampione);
   templ.bset("note",note);
   templ.set("noteMisuraPsr", noteMisuraPsr);

   /**
    Carico i dati di tutti i materiali per visualizzarli nella combo
   */
   String codStr[],descStr[];
   codStr=beanTipoCampione.getCodMateriale();
   descStr=beanTipoCampione.getDescMateriale();
   for(int i=0;i<codStr.length;i++)
   {
     if (codMateriale.equals(codStr[i]))
       templ.set("materiale.selected","selected");
     else
       templ.set("materiale.selected","");
     templ.set("materiale.codice",codStr[i]);
     templ.set("materiale.descrizione",descStr[i]);
   }
   /**
    Carico i dati di tutti i laboratori per visualizzarli nella combo
   */
   codStr=beanTipoCampione.getCodLaboratorio();
   descStr=beanTipoCampione.getDescLaboratorio();
   for(int i=0;i<codStr.length;i++)
   {
     if (codLaboratorio.equals(codStr[i]))
       templ.set("laboratorio.selected","selected");
     else
       templ.set("laboratorio.selected","");
     templ.set("laboratorio.codice",codStr[i]);
     templ.set("laboratorio.descrizione",descStr[i]);
   }
   
  CodiciMisuraPsr codiciMisuraPsr = beanTipoCampione.getCodiciMisuraPsr();
   int size = codiciMisuraPsr.size();
   for (int i = 0; i < size; i++)
   {
   		CodiceMisuraPsr cod = codiciMisuraPsr.get(i);
   		
			templ.newBlock("codiceMisuraPsr");
			templ.set("codiceMisuraPsr.codice", cod.getCodiceMisuraPsr());
			templ.set("codiceMisuraPsr.descrizione", cod.getDescrizioneMisuraPsr());
			templ.set("codiceMisuraPsr.motivoObbligatorio", cod.getMotivoObbligatorio());
			if (cod.getCodiceMisuraPsr().equals(codiceMisuraPsr))
			{
				templ.set("codiceMisuraPsr.selected", "selected");
      }
   }

   /**
    Carico i dati di tutte le modalità di consegna per visualizzarle nella combo
   */
   codStr=beanTipoCampione.getCodModalita();
   descStr=beanTipoCampione.getDescModalita();
   for(int i=0;i<codStr.length;i++)
   {
     if (codModalitaConsegna.equals(codStr[i]))
       templ.set("modalita.selected","selected");
     else
       templ.set("modalita.selected","");
     templ.set("modalita.codice",codStr[i]);
     templ.set("modalita.descrizione",descStr[i]);
   }
   if (aut.getFase()>4)
   {
     templ.bset("disabled","disabled");
     templ.bset("fase5Name","codMateriale");
     templ.bset("fase5Value",codMateriale);
   }
   else
     templ.bset("fase5Name","nothing");
%>
<%= templ.text() %>
