<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*" isThreadSafe="true" %>
<%@ page import="it.csi.agrc.*" isThreadSafe="true" %>
<%@ page import="it.csi.iride2.policy.entity.Identita" %>

<%
  it.csi.jsf.htmpl.Htmpl templ=null ;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="datiAppezzamento"
     scope="request"
     class="it.csi.agrc.DatiAppezzamento">
    <%
      datiAppezzamento.setDataSource(dataSource);
      datiAppezzamento.setAut(aut);
    %>
</jsp:useBean>
<jsp:setProperty name="datiAppezzamento" property="*"/>

<jsp:useBean
     id="fase"
     scope="page"
     class="it.csi.agrc.FasiRichiesta">
    <%
      fase.setDataSource(dataSource);
      fase.setAut(aut);
    %>
</jsp:useBean>

<%
   String metodo = request.getParameter("metodo");
  
   Identita identita = (Identita) session.getAttribute(Constants.SESSION.IDENTITA); 
  //Se sono state impostate le coordinate in gradi mi ricavo quelle utm
  if (!Utili.isEmpty(datiAppezzamento.getGradiNord()))
  {
	  it.csi.cuneo.Coordinate coord=null;
	  if (DatiAppezzamento.GRADI_DECIMALI.equals(datiAppezzamento.getCoordGradi()))
	  {
		  double phiGradi=Double.parseDouble(datiAppezzamento.getGradiNord()+"."+datiAppezzamento.getDecimaliNord());
		  double landaGradi=Double.parseDouble(datiAppezzamento.getGradiEst()+"."+datiAppezzamento.getDecimaliEst());
		  coord=new it.csi.cuneo.Coordinate(phiGradi,landaGradi);
	  }
	  if (DatiAppezzamento.GRADI_MINUTI_DECIMALI.equals(datiAppezzamento.getCoordGradi()))
	  {
		  double phiGradi=Double.parseDouble(datiAppezzamento.getGradiNord());
		  double phiMinuti=Double.parseDouble(datiAppezzamento.getMinutiNord()+"."+datiAppezzamento.getDecimaliNord());
		  double landaGradi=Double.parseDouble(datiAppezzamento.getGradiEst());
		  double landaMinuti=Double.parseDouble(datiAppezzamento.getMinutiEst()+"."+datiAppezzamento.getDecimaliEst());
		  coord=new it.csi.cuneo.Coordinate(phiGradi, phiMinuti, landaGradi, landaMinuti);
	  }
	  if (DatiAppezzamento.GRADI_MINUTI_SECONDI.equals(datiAppezzamento.getCoordGradi()))
	  {
		  double phiGradi=Double.parseDouble(datiAppezzamento.getGradiNord());
		  double phiMinuti=Double.parseDouble(datiAppezzamento.getMinutiNord());
		  double phpSecondi=Double.parseDouble(datiAppezzamento.getSecondiNord()+"."+datiAppezzamento.getDecimaliNord());
		  double landaGradi=Double.parseDouble(datiAppezzamento.getGradiEst());
		  double landaMinuti=Double.parseDouble(datiAppezzamento.getMinutiEst());
		  double landaSecondi=Double.parseDouble(datiAppezzamento.getSecondiEst()+"."+datiAppezzamento.getDecimaliEst());
		  coord=new it.csi.cuneo.Coordinate(phiGradi, phiMinuti, phpSecondi, landaGradi, landaMinuti, landaSecondi);
	  }
	  coord.convertiRadiantiToUtm();
	  datiAppezzamento.setCoordinataNordUtm(""+ new Double(coord.getUtmNord()).longValue());
	  datiAppezzamento.setCoordinataEstUtm(""+ new Double(coord.getUtmEst()).longValue());
  }
  
  
 String errore = null;
  
  /**
  * Controllo gli eventuali errori tramite jsp
  * */
  if(metodo.equals("")) //jira 110 devo restituire l'errore solo se ho cliccato su conferma
  {
  	 errore=datiAppezzamento.ControllaDati();
  }
  /**
  * Se c'è un errore vuol dire che non è stato eseguito corretamente il
  * javascript
  */
  if (errore!=null)
  {
    Utili.forwardConParametri(request, response, "/jsp/view/coordinateGIS.jsp?errore="+errore);
    return;
  }
  
  /*
  	Alla conferma da parte dell'utente, nel caso in cui il comune indicato sia un comune piemontese e nel caso in cui siano stati 
  	inseriti i dati catastali ma non le coordinate geografiche, il sistema calcola il centroide della particella in coordinate UTM.
  */
  if (datiAppezzamento.isRegionePiemonte())
  {
	  if (datiAppezzamento.getCoordinataEstUtm()==null && datiAppezzamento.getCoordinataNordUtm()==null)	  
	  {
		  boolean trovato = datiAppezzamento.ricavaUTMFromDatiCatastali(aut.getIstatComuneRichiesta(), beanParametriApplication, identita.getCodFiscale());
	    if(!trovato)
	    {
	    	  //jira 106
	    	  //I dati catastali inseriti non sono coerenti con il comune di prelievo indicato
    	  if(metodo.equals("")) //jira 110 devo restituire l'errore solo se ho cliccato su conferma
    	  {
	    	  errore = ";3;4";
	    	  Utili.forwardConParametri(request, response, "/jsp/view/coordinateGIS.jsp?erroreCongruenzaSigmaterCatastali="+errore);
			    return;
    	  }
	    }
	  }
	  else
	  {
		  if(metodo.equals("")) //devo fare il controllo solo se ho cliccato su conferma
		  {
		  		errore=datiAppezzamento.verificaUTMInserite(aut.getIstatComuneRichiesta(), beanParametriApplication, identita.getCodFiscale());
		  		if (errore!=null)
		 		{
			  		//coordinate non appartenenti al comune
			  		Utili.forwardConParametri(request, response, "/jsp/view/coordinateGIS.jsp?erroreCongruenzaSigmater="+errore);
			  		return;
		  		}
		  }
	  }
  }

  if(metodo.equals("calcolaUTM"))
  {
	  String p="";
	   Utili.forwardConParametri(request, response, "/jsp/view/coordinateGIS.jsp?parametro="+p);
	   return;
  }
  
  datiAppezzamento.update();

  if (aut.getFase()<2)
  {
   /**
    * Se la fase è minore di 2 devo portarla a 2. Devo quindi
    * memorizzarne il nuovo valore bel bean di sesione oltre a scriverlo
    * nel database.
    */
   fase.updateFase(2);
   aut.setFase((byte)2);
  }
  switch(aut.getUtente().getTipoUtente())
  {
    case 'P':Utili.forward(request, response, "/jsp/view/anagraficaPrivato.jsp");
             break;
    case 'T':Utili.forward(request, response, "/jsp/view/anagraficaTecnico.jsp");
             break;
    case 'L':Utili.forward(request, response, "/jsp/view/anagraficaUtenteLAR.jsp");
             break;
  }
%>


