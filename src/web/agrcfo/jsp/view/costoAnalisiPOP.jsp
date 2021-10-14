<%@ page errorPage="/jsp/view/errorePopup.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.jsf.htmpl.*,java.util.*,it.csi.agrc.*,java.text.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/costoAnalisiPOP.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>

<jsp:useBean id="beanAnalisi" scope="application" class="it.csi.agrc.BeanAnalisi"/>

<%

  DecimalFormat nf2 = new DecimalFormat("#0.00");
  java.util.Hashtable analisiHash=beanAnalisi.getAnalisi();
  String descrizione,valore;
  String pH,calcio,magnesio,potassio,azoto,fosforo;
  String csc,sostanzaOrganica,calcareTotale;
  String ferro,manganese,rame,zinco,boro;

  if ("TER".equals(aut.getCodMateriale()))
  {
    pH=request.getParameter(Analisi.ANA_PH);
    sostanzaOrganica=request.getParameter(Analisi.ANA_SOSTANZAORGANICA);
    potassio=request.getParameter(Analisi.ANA_POTASSIO);
    magnesio=request.getParameter(Analisi.ANA_MAGNESIO);
    fosforo=request.getParameter(Analisi.ANA_FOSFORO);
    csc=request.getParameter(Analisi.ANA_CAPACITASCAMBIOCATIONICO);
    calcio=request.getParameter(Analisi.ANA_CALCIO);
    calcareTotale=request.getParameter(Analisi.ANA_CALCARETOTALE);
    azoto=request.getParameter(Analisi.ANA_AZOTO);
    ferro=request.getParameter(Analisi.ANA_ZINCO);
    manganese=request.getParameter(Analisi.ANA_RAME);
    rame=request.getParameter(Analisi.ANA_MANGANESE);
    zinco=request.getParameter(Analisi.ANA_FERRO);

    if (pH!=null && sostanzaOrganica!=null && potassio!=null && magnesio!=null
        && fosforo!=null && csc!=null && calcio!=null && calcareTotale!=null
        && azoto!=null)
    {
      descrizione=(String)analisiHash.get(Analisi.ANA_PACCHETTO_TIPO);
      valore=request.getParameter("ANA_PACCHETTO_TIPO");
      if (valore==null) valore="0";
      templ.newBlock("analisi");
      templ.set("analisi.descrizione",descrizione);
      templ.set("analisi.valore",valore.replace('.',','));
    }
    else
    {
      if (potassio!=null && magnesio!=null && csc!=null && calcio!=null)
      {
        descrizione=(String)analisiHash.get(Analisi.ANA_PACCHETTO_COMP_SCAMBIO);
        valore=request.getParameter("ANA_PACCHETTO_COMP_SCAMBIO");
        if (valore==null) valore="0";
        templ.newBlock("analisi");
        templ.set("analisi.descrizione",descrizione);
        templ.set("analisi.valore",valore.replace('.',','));
      }
      else
      {
        if (potassio!=null)
        {
          descrizione=(String)analisiHash.get(Analisi.ANA_POTASSIO);
          templ.newBlock("analisi");
          templ.set("analisi.descrizione",descrizione);
          templ.set("analisi.valore",potassio.replace('.',','));
        }
        if (magnesio!=null)
        {
          descrizione=(String)analisiHash.get(Analisi.ANA_MAGNESIO);
          templ.newBlock("analisi");
          templ.set("analisi.descrizione",descrizione);
          templ.set("analisi.valore",magnesio.replace('.',','));
        }
        if (csc!=null)
        {
          descrizione=(String)analisiHash.get(Analisi.ANA_CAPACITASCAMBIOCATIONICO);
          templ.newBlock("analisi");
          templ.set("analisi.descrizione",descrizione);
          templ.set("analisi.valore",csc.replace('.',','));
        }
        if (calcio!=null)
        {
          descrizione=(String)analisiHash.get(Analisi.ANA_CALCIO);
          templ.newBlock("analisi");
          templ.set("analisi.descrizione",descrizione);
          templ.set("analisi.valore",calcio.replace('.',','));
        }
      }
      if (pH!=null)
      {
        descrizione=(String)analisiHash.get(Analisi.ANA_PH);
        templ.newBlock("analisi");
        templ.set("analisi.descrizione",descrizione);
        templ.set("analisi.valore",pH.replace('.',','));
      }
      if (sostanzaOrganica!=null)
      {
        descrizione=(String)analisiHash.get(Analisi.ANA_SOSTANZAORGANICA);
        templ.newBlock("analisi");
        templ.set("analisi.descrizione",descrizione);
        templ.set("analisi.valore",sostanzaOrganica.replace('.',','));
      }
      if (fosforo!=null)
      {
        descrizione=(String)analisiHash.get(Analisi.ANA_FOSFORO);
        templ.newBlock("analisi");
        templ.set("analisi.descrizione",descrizione);
        templ.set("analisi.valore",fosforo.replace('.',','));
      }
      if (calcareTotale!=null)
      {
        descrizione=(String)analisiHash.get(Analisi.ANA_CALCARETOTALE);
        templ.newBlock("analisi");
        templ.set("analisi.descrizione",descrizione);
        templ.set("analisi.valore",calcareTotale.replace('.',','));
      }
      if (azoto!=null)
      {
        descrizione=(String)analisiHash.get(Analisi.ANA_AZOTO);
        templ.newBlock("analisi");
        templ.set("analisi.descrizione",descrizione);
        templ.set("analisi.valore",azoto.replace('.',','));
      }
    }
    if (ferro!=null && manganese!=null && rame!=null && zinco!=null)
    {
      descrizione=(String)analisiHash.get(Analisi.ANA_PACCHETTO_MICROELEMENTI);
      valore=request.getParameter("ANA_PACCHETTO_MICROELEMENTI");
      if (valore==null) valore="0";
      templ.newBlock("analisi");
      templ.set("analisi.descrizione",descrizione);
      templ.set("analisi.valore",valore.replace('.',','));
    }
    else
    {
      if (zinco!=null)
      {
        descrizione=(String)analisiHash.get(Analisi.ANA_ZINCO);
        templ.newBlock("analisi");
        templ.set("analisi.descrizione",descrizione);
        templ.set("analisi.valore",zinco.replace('.',','));
      }
      if (rame!=null)
      {
        descrizione=(String)analisiHash.get(Analisi.ANA_RAME);
        templ.newBlock("analisi");
        templ.set("analisi.descrizione",descrizione);
        templ.set("analisi.valore",rame.replace('.',','));
      }
      if (manganese!=null)
      {
        descrizione=(String)analisiHash.get(Analisi.ANA_MANGANESE);
        templ.newBlock("analisi");
        templ.set("analisi.descrizione",descrizione);
        templ.set("analisi.valore",manganese.replace('.',','));
      }
      if (ferro!=null)
      {
        descrizione=(String)analisiHash.get(Analisi.ANA_FERRO);
        templ.newBlock("analisi");
        templ.set("analisi.descrizione",descrizione);
        templ.set("analisi.valore",ferro.replace('.',','));
      }
    }

    valore=request.getParameter(Analisi.ANA_UMIDITA);
    if (valore!=null)
    {
      descrizione=(String)analisiHash.get(Analisi.ANA_UMIDITA);
      templ.newBlock("analisi");
      templ.set("analisi.descrizione",descrizione);
      templ.set("analisi.valore",valore.replace('.',','));
    }

    valore=request.getParameter(Analisi.ANA_SALINITA);
    if (valore!=null)
    {
      descrizione=(String)analisiHash.get(Analisi.ANA_SALINITA);
      templ.newBlock("analisi");
      templ.set("analisi.descrizione",descrizione);
      templ.set("analisi.valore",valore.replace('.',','));
    }

    valore=request.getParameter(Analisi.ANA_CALCAREATTIVO);
    if (valore!=null)
    {
      descrizione=(String)analisiHash.get(Analisi.ANA_CALCAREATTIVO);
      templ.newBlock("analisi");
      templ.set("analisi.descrizione",descrizione);
      templ.set("analisi.valore",valore.replace('.',','));
    }

    valore=request.getParameter(Analisi.ANA_BORO);
    if (valore!=null)
    {
      descrizione=(String)analisiHash.get(Analisi.ANA_BORO);
      templ.newBlock("analisi");
      templ.set("analisi.descrizione",descrizione);
      templ.set("analisi.valore",valore.replace('.',','));
    }

    valore=request.getParameter(Analisi.ANA_STANDARD);
    if (valore!=null)
    {
      descrizione=(String)analisiHash.get(Analisi.ANA_STANDARD);
      templ.newBlock("analisi");
      templ.set("analisi.descrizione",descrizione);
      templ.set("analisi.valore",valore.replace('.',','));
    }

    valore=request.getParameter(Analisi.ANA_A4FRAZIONI);
    if (valore!=null)
    {
      descrizione=(String)analisiHash.get(Analisi.ANA_A4FRAZIONI);
      templ.newBlock("analisi");
      templ.set("analisi.descrizione",descrizione);
      templ.set("analisi.valore",valore.replace('.',','));
    }

    valore=request.getParameter(Analisi.ANA_A5FRAZIONI);
    if (valore!=null)
    {
      descrizione=(String)analisiHash.get(Analisi.ANA_A5FRAZIONI);
      templ.newBlock("analisi");
      templ.set("analisi.descrizione",descrizione);
      templ.set("analisi.valore",valore.replace('.',','));
    }
  }
  else
  {
    potassio=request.getParameter(Analisi.ANA_POTASSIO);
    magnesio=request.getParameter(Analisi.ANA_MAGNESIO);
    fosforo=request.getParameter(Analisi.ANA_FOSFORO);
    calcio=request.getParameter(Analisi.ANA_CALCIO);
    azoto=request.getParameter(Analisi.ANA_AZOTO);
    ferro=request.getParameter(Analisi.ANA_ZINCO);
    manganese=request.getParameter(Analisi.ANA_RAME);
    rame=request.getParameter(Analisi.ANA_MANGANESE);
    zinco=request.getParameter(Analisi.ANA_FERRO);
    boro=request.getParameter(Analisi.ANA_BORO);

    if (potassio!=null && magnesio!=null
        && fosforo!=null && calcio!=null && azoto!=null)
    {
      descrizione=(String)analisiHash.get(Analisi.ANA_PACCHETTO_TIPO);
      valore=request.getParameter("ANA_PACCHETTO_TIPO");
      if (valore==null) valore="0";
      templ.newBlock("analisi");
      templ.set("analisi.descrizione",descrizione);
      templ.set("analisi.valore",valore.replace('.',','));
    }
    else
    {
      if (potassio!=null)
      {
        descrizione=(String)analisiHash.get(Analisi.ANA_POTASSIO);
        templ.newBlock("analisi");
        templ.set("analisi.descrizione",descrizione);
        templ.set("analisi.valore",potassio.replace('.',','));
      }
      if (magnesio!=null)
      {
        descrizione=(String)analisiHash.get(Analisi.ANA_MAGNESIO);
        templ.newBlock("analisi");
        templ.set("analisi.descrizione",descrizione);
        templ.set("analisi.valore",magnesio.replace('.',','));
      }
      if (calcio!=null)
      {
        descrizione=(String)analisiHash.get(Analisi.ANA_CALCIO);
        templ.newBlock("analisi");
        templ.set("analisi.descrizione",descrizione);
        templ.set("analisi.valore",calcio.replace('.',','));
      }
      if (fosforo!=null)
      {
        descrizione=(String)analisiHash.get(Analisi.ANA_FOSFORO);
        templ.newBlock("analisi");
        templ.set("analisi.descrizione",descrizione);
        templ.set("analisi.valore",fosforo.replace('.',','));
      }
      if (azoto!=null)
      {
        descrizione=(String)analisiHash.get(Analisi.ANA_AZOTO);
        templ.newBlock("analisi");
        templ.set("analisi.descrizione",descrizione);
        templ.set("analisi.valore",azoto.replace('.',','));
      }
    }
    if (ferro!=null && manganese!=null && rame!=null && zinco!=null && boro!=null)
    {
      descrizione=(String)analisiHash.get(Analisi.ANA_PACCHETTO_MICROELEMENTI);
      valore=request.getParameter("ANA_PACCHETTO_MICROELEMENTI");
      if (valore==null) valore="0";
      templ.newBlock("analisi");
      templ.set("analisi.descrizione",descrizione);
      templ.set("analisi.valore",valore.replace('.',','));
    }
    else
    {
      if (zinco!=null)
      {
        descrizione=(String)analisiHash.get(Analisi.ANA_ZINCO);
        templ.newBlock("analisi");
        templ.set("analisi.descrizione",descrizione);
        templ.set("analisi.valore",zinco.replace('.',','));
      }
      if (rame!=null)
      {
        descrizione=(String)analisiHash.get(Analisi.ANA_RAME);
        templ.newBlock("analisi");
        templ.set("analisi.descrizione",descrizione);
        templ.set("analisi.valore",rame.replace('.',','));
      }
      if (manganese!=null)
      {
        descrizione=(String)analisiHash.get(Analisi.ANA_MANGANESE);
        templ.newBlock("analisi");
        templ.set("analisi.descrizione",descrizione);
        templ.set("analisi.valore",manganese.replace('.',','));
      }
      if (ferro!=null)
      {
        descrizione=(String)analisiHash.get(Analisi.ANA_FERRO);
        templ.newBlock("analisi");
        templ.set("analisi.descrizione",descrizione);
        templ.set("analisi.valore",ferro.replace('.',','));
      }
      if (boro!=null)
      {
        descrizione=(String)analisiHash.get(Analisi.ANA_BORO);
        templ.newBlock("analisi");
        templ.set("analisi.descrizione",descrizione);
        templ.set("analisi.valore",boro.replace('.',','));
      }
    }
    valore=request.getParameter(Analisi.ANA_UMIDITA);
    if (valore!=null)
    {
      descrizione=(String)analisiHash.get(Analisi.ANA_UMIDITA);
      templ.newBlock("analisi");
      templ.set("analisi.descrizione",descrizione);
      templ.set("analisi.valore",valore.replace('.',','));
    }
  }

	//Metalli pesanti
	String FeTot = request.getParameter(Analisi.ANA_FERRO_TOTALE);
	String MnTot = request.getParameter(Analisi.ANA_MANGANESE_TOTALE);
	String ZnTot = request.getParameter(Analisi.ANA_ZINCO_TOTALE);
	String CuTot = request.getParameter(Analisi.ANA_RAME_TOTALE);
	//String BTot = request.getParameter(Analisi.ANA_BORO_TOTALE);
	String CdTot = request.getParameter(Analisi.ANA_CADMIO_TOTALE);
	String CrTot = request.getParameter(Analisi.ANA_CROMO_TOTALE);
	String NiTot = request.getParameter(Analisi.ANA_NICHEL_TOTALE);
	String PbTot = request.getParameter(Analisi.ANA_PIOMBO_TOTALE);
	//String SrTot = request.getParameter(Analisi.ANA_STRONZIO_TOTALE);
	//String MetTot = request.getParameter(Analisi.ANA_ALTRO_METALLO_TOTALE);

  /*if (FeTot != null && MnTot != null && ZnTot != null &&
  		CuTot != null && BTot != null && CdTot != null &&
  		CrTot != null && NiTot != null && PbTot != null &&
			SrTot != null && MetTot != null)
  {
  	//Se sono tutti selezionati viene visualizzata la descrizione del relativo pacchetto
  
    descrizione = (String) analisiHash.get(Analisi.ANA_PACCHETTO_METALLI_PESANTI);
    valore = request.getParameter("ANA_PACCHETTO_METALLI_PESANTI");
    if (valore == null)
    {
    	valore = "0";
    }
    templ.newBlock("analisi");
    templ.set("analisi.descrizione", descrizione);
    templ.set("analisi.valore", valore.replace('.',','));
  }
  else
  {*/
  	//Viene visualizzata la descrizione solo degli elementi selezionati
  
  	setDettaglioCosto(FeTot, (String) analisiHash.get(Analisi.ANA_FERRO_TOTALE), templ);
  	setDettaglioCosto(MnTot, (String) analisiHash.get(Analisi.ANA_MANGANESE_TOTALE), templ);
  	setDettaglioCosto(ZnTot, (String) analisiHash.get(Analisi.ANA_ZINCO_TOTALE), templ);
  	setDettaglioCosto(CuTot, (String) analisiHash.get(Analisi.ANA_RAME_TOTALE), templ);
  //	setDettaglioCosto(BTot, (String) analisiHash.get(Analisi.ANA_BORO_TOTALE), templ);
  	setDettaglioCosto(CdTot, (String) analisiHash.get(Analisi.ANA_CADMIO_TOTALE), templ);
  	setDettaglioCosto(CrTot, (String) analisiHash.get(Analisi.ANA_CROMO_TOTALE), templ);
  	setDettaglioCosto(NiTot, (String) analisiHash.get(Analisi.ANA_NICHEL_TOTALE), templ);
  	setDettaglioCosto(PbTot, (String) analisiHash.get(Analisi.ANA_PIOMBO_TOTALE), templ);
  //	setDettaglioCosto(SrTot, (String) analisiHash.get(Analisi.ANA_STRONZIO_TOTALE), templ);
  	/*setDettaglioCosto(MetTot, (String) analisiHash.get(Analisi.ANA_ALTRO_METALLO_TOTALE), templ);
  }*/
    
  valore = request.getParameter("totale");
  if (valore != null)
  {
  	valore = valore.replace(',','.');
  }

  /**
   * Se isSpedizioneFattura è true significa che devo visualizzare il blocco
   * relativo alla fattura. Devo inoltre sommare il costo della spedizione
   * al totale
   * */
  if (aut.isSpedizioneFattura())
  {
    templ.newBlock("fattura");
    templ.set("fattura.spedizioneFattura",beanAnalisi.getImportoSpedizione().replace('.',','));
    try
    {
      double importo=Double.parseDouble(beanAnalisi.getImportoSpedizione());
      double totale=Double.parseDouble(valore);
      totale+=importo;
      valore=nf2.format(totale).replace('.',',');
    }
    catch(Exception e)
    {}
  }
  templ.bset("totale",valore.replace('.',','));
%>
<%= templ.text() %>

<%!
	public void setDettaglioCosto(String valore, String descrizione, Htmpl templ)
	{
    if (valore != null)
    {
      templ.newBlock("analisi");
      templ.set("analisi.descrizione", descrizione);
      templ.set("analisi.valore", valore.replace('.',','));
   
    }
	}
%>