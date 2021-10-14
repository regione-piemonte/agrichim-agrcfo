// Codici unicode per le lettere accentate da inserire nei PDF
// \u00EC = ì

package it.csi.agrc.servlet;

import inetsoft.report.StyleConstants;
import inetsoft.report.TabularSheet;
import inetsoft.report.lens.DefaultTableLens;
import inetsoft.report.painter.ImagePainter;
import it.csi.agrc.Anagrafica;
import it.csi.agrc.Anagrafiche;
import it.csi.agrc.Analisi;
import it.csi.agrc.Autenticazione;
import it.csi.agrc.BeanAnalisi;
import it.csi.agrc.BeanColtura;
import it.csi.agrc.BeanParametri;
import it.csi.agrc.CampioneTerrenoDati;
import it.csi.agrc.Coltura;
import it.csi.agrc.Comuni;
import it.csi.agrc.DatiAppezzamento;
import it.csi.agrc.DatiFattura;
import it.csi.agrc.EtichettaCampione;
import it.csi.agrc.Laboratorio;
import it.csi.agrc.OrganizzazioneProfessionale;
import it.csi.cuneo.CuneoLogger;
import it.csi.cuneo.ReportUtils;
import it.csi.cuneo.Utili;
import it.csi.solmr.dto.anag.AnagAziendaVO;

import java.awt.Color;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class PdfAnagraficaCampione extends PdfServletAgrc
{
  private static final long serialVersionUID = -4325611315132055180L;
  
  // Nel template XML il codice decimale (negativo) che bisogna utilizzare
  // si calcola nel seguente modo:
  // 1) Componenti RGB = 160,160,164 = A0,A0,A4
  // 2) Hex = [A0A0A4]
  // 3) Dec = [A0A0A4] -  [1000000] = 10526884 - 16777216 = -6250332
  //Color PDF_COL_BACKGROUND = new Color(165,162,165);
  static Color PDF_COL_BACKGROUND = new Color(160,160,164);
  static String PDF_ANALISI_CHECKED = "   X";

  HttpSession session;
  ServletContext context;
  Autenticazione aut;
  Object dataSource;
  String idRichiesta;

  BeanColtura beanColtura;
  Coltura coltura;
  Analisi analisi;

  DefaultTableLens tblLens;
  String strTmp;
  Vector cod=new Vector(), desc=new Vector();

  public PdfAnagraficaCampione()
  {
    this.setOutputName("anagraficaCampione.pdf");
    this.setTemplateName("pdfAnagraficaCampione.srt");
  }

  /**
   * Questo metodo serve per stampare le parti comuni dei PDF
   * anagraficaCampioneXXX.pdf
   *
   * @param request Oggetto relativo alla richiesta HTTP corrente
   * @param xss Oggetto relativo allo TabularSheet adeguato
   * @throws Exception
   */
  protected void stampaPdf(HttpServletRequest request,
  		TabularSheet xss)
      throws Exception
  {
    // Questo PDF va generato solo se è stato effettuato con successo il login
    session = request.getSession();
    aut = (Autenticazione)session.getAttribute("aut");
    controllaAut(aut);

    context=session.getServletContext();
    if (Utili.POOLMAN)
      dataSource=context.getAttribute("poolBean");
    else
      dataSource=context.getAttribute("dataSourceBean");

    idRichiesta=request.getParameter("idRichiesta");

    beanColtura = (BeanColtura)context.getAttribute("beanColtura");
    coltura = new Coltura(dataSource, aut);
    analisi = new Analisi(dataSource, aut);
    analisi.select(idRichiesta);

    //Footer
    ReportUtils.setFooter(xss);
    
    /***************************************************************
     * Impostazione delle immagini
     */
    // Inserisce il logo della regione, per tutti
    ImagePainter ip = new ImagePainter(this.getImage("logoRegione.gif"));
    xss.setElement("imgLogoRegione",ip);
    xss.setElement("imgLogoRegione1",ip);
    // Inserisce le linee tratteggiate con forbici, pagina 1
    ip = new ImagePainter(this.getImage("forbici.gif"));

    xss.setElement("imgForbiciTop",ip);
    xss.setElement("imgForbiciBottom",ip);

    //Impostazione dei dati relativi alla testa del PDF: sono dati che si trovano
    //all'interno della tabella parametro e vengono precaricati all'avvio dell'applicativo
    // nel bean BeanParametri
    BeanParametri beanParametriApplication=(BeanParametri)context.getAttribute("beanParametriApplication");

    //le caselle di testo sono doppie perché una si riferisce alla prima pagina (tbx..1)
    //e l'altra a tutte le pagine (tbx..)
    String direzione = "";
    
    if(beanParametriApplication.getDirezione()!=null)
    {
    	direzione = beanParametriApplication.getDirezione();
    }
    
    xss.setElement("tbxAssessorato",beanParametriApplication.getAssessorato()+"\n"+direzione);
    xss.setElement("tbxAssessorato1",beanParametriApplication.getAssessorato()+"\n"+direzione);
    
  //  xss.setElement("tbxAssessorato",beanParametriApplication.getAssessorato()+"\n"+beanParametriApplication.getDirezione());
   // xss.setElement("tbxAssessorato1",beanParametriApplication.getAssessorato()+"\n"+beanParametriApplication.getDirezione());
    xss.setElement("tbxSettore",beanParametriApplication.getSettore()+"\n"+beanParametriApplication.getLabAgr());
    xss.setElement("tbxSettore1",beanParametriApplication.getSettore()+"\n"+beanParametriApplication.getLabAgr());

    /***************************************************************
     * Predisposizione dei dati
     */
    BeanAnalisi beanAnalisi=(BeanAnalisi)context.getAttribute("beanAnalisi");

    EtichettaCampione etiCamp = new EtichettaCampione(dataSource, aut);
    etiCamp.selectPdf(idRichiesta);
    Anagrafiche anag = new Anagrafiche(dataSource, aut);
    Anagrafica anagraficaUtente = anag.getAnagrafica(etiCamp.getAnagraficaUtenteSingolo());
    Anagrafica anagraficaProprietario = anag.getAnagrafica(etiCamp.getAnagraficaProprietario());
    Anagrafica anagraficaRichiedente = anag.getAnagrafica(etiCamp.getAnagraficaRichiedente());
    Anagrafica anagraficaTecnico = anag.getAnagrafica(etiCamp.getAnagraficaTecnico());
    OrganizzazioneProfessionale organizzazioneTecnico = new OrganizzazioneProfessionale(dataSource, aut);
    if (anagraficaTecnico==null)
    {
      if ("T".equals(anagraficaUtente.getTipoUtente()))
      {
        anagraficaTecnico=anagraficaUtente;
        organizzazioneTecnico.select(anagraficaTecnico.getIdOrganizzazione());
      }
    }
    else organizzazioneTecnico.select(anagraficaTecnico.getIdOrganizzazione());

    Laboratorio laboratorioConsegna = new Laboratorio(dataSource, aut);
    Laboratorio laboratorioAnalisi = new Laboratorio(dataSource, aut);
    laboratorioAnalisi.select(etiCamp.getLaboratorioAnalisi(),beanParametriApplication.getPartitaIVALab());
    Comuni comuni = new Comuni(dataSource, aut);

    DatiAppezzamento datiAppezzamento = new DatiAppezzamento(dataSource, aut);
    datiAppezzamento.select(idRichiesta);

    CampioneTerrenoDati ctd = new CampioneTerrenoDati(dataSource, aut);
    ctd.select(idRichiesta);

    DefaultTableLens tblLens = null;
    String tmpStr;

    /***************************************************************
     * Header comune a tutte le pagine
     */
    String etichetta=null;
    laboratorioConsegna.select(null,beanParametriApplication.getPartitaIVALab());
    etichetta="SEDE: "+laboratorioConsegna.getIndirizzoPdf();
    laboratorioConsegna.select(etiCamp.getLaboratorioConsegna(),beanParametriApplication.getPartitaIVALab());
    etichetta+="\nLABORATORIO DI CONSEGNA\n"+laboratorioConsegna.getIndirizzoPdf();
    
    //etichetta+="\n"+beanParametriApplication.getLabConsegna2();
    if(beanParametriApplication.getLabConsegna2()!=null)
    {
      etichetta+="\n"+beanParametriApplication.getLabConsegna2();
    }
    xss.setElement("tbxIndirizzo",etichetta);
    xss.setElement("tbxIndirizzo1",etichetta);

    /***************************************************************
     * Header pagina 2 e seguenti
     */
    tblLens = new DefaultTableLens(xss.getTable("tblTipoCampione"));
    tblLens.setObject(1,0,etiCamp.getDescMateriale());
    xss.setElement("tblTipoCampione", tblLens);
    //Style
    ReportUtils.formatTableHeaderTwoRows(xss, "tblTipoCampione", true);
    
    /***************************************************************
     * Pagina 1 - uguale per tutti i materiali
     */
    tblLens = new DefaultTableLens(xss.getTable("tblLaboratorio"));
    if (laboratorioAnalisi.getCodiceLaboratorio()!=null)
      tblLens.setObject(0,1,laboratorioAnalisi.getDescrizione());
    else
      tblLens.setObject(0,1,laboratorioConsegna.getDescrizione());
    xss.setElement("tblLaboratorio", tblLens);
    //Style
    ReportUtils.formatTableColumLeftRight(xss, "tblLaboratorio", true);

    tblLens = new DefaultTableLens(xss.getTable("tblEtichettaSpazioRiservatoLAR"));
    int size = tblLens.getRowCount() - 1;
    for (int r = 0; r < size; r++)
    {
    	tblLens.setRowBorder(r, 0, StyleConstants.NO_BORDER);
    }
    tblLens.setAlignment(0, 0, StyleConstants.H_CENTER);
    xss.setElement("tblEtichettaSpazioRiservatoLAR", tblLens);

    tblLens = new DefaultTableLens(xss.getTable("tblEtichetta1"));
    tblLens.setObject(1,0,etiCamp.getIdRichiesta());
    tblLens.setObject(1,1,etiCamp.getDescrizioneEtichetta());
    tblLens.setObject(1,2,etiCamp.getCostoAnalisi(beanAnalisi.getImportoSpedizione()).replace('.',',')+" euro");
    xss.setElement("tblEtichetta1", tblLens);
    //Style
    ReportUtils.formatTableEtichetta(xss, "tblEtichetta1", true, StyleConstants.H_CENTER);

    tblLens = new DefaultTableLens(xss.getTable("tblEtichetta2"));
    if (anagraficaTecnico!=null)
    {      
    	tblLens.setObject(1,0,organizzazioneTecnico.getRagioneSociale()+" - "+organizzazioneTecnico.getComune());
    	tblLens.setObject(1,1,anagraficaTecnico.getCognomeRagioneSociale()+" "+anagraficaTecnico.getNome());
    }
    //
    tblLens.setObject(1,2,comuni.getDescrizioneComuneProv(datiAppezzamento.getComuneAppezzamento()));
    xss.setElement("tblEtichetta2", tblLens);

    //Style
    ReportUtils.formatTableEtichetta(xss, "tblEtichetta2", true, StyleConstants.H_CENTER);

    tblLens = new DefaultTableLens(xss.getTable("tblEtichetta3"));
    tmpStr = anagraficaProprietario.getNome();
    if (tmpStr == null) tmpStr = "";
    tblLens.setObject(1,0,anagraficaProprietario.getCognomeRagioneSociale()+" "+tmpStr);
    tblLens.setObject(1,1,anagraficaUtente.getCognomeRagioneSociale()+" "+anagraficaUtente.getNome());
    
    cod.clear();
    desc.clear();
    coltura.selectCodDescSpecie(Coltura.IMPOSTA_QUERY_SPECIE,cod,desc,ctd.getIdColturaAttuale()); 
    tblLens.setObject(1,2,selDescr(beanColtura.getCodColtura(), beanColtura.getDescColtura(), ctd.getIdColturaAttuale()) + " - " + selDescr(cod, desc, ctd.getSpecieAttuale()));

    xss.setElement("tblEtichetta3", tblLens);
    //Style
    ReportUtils.formatTableEtichetta(xss, "tblEtichetta3", true, StyleConstants.H_CENTER);
    
    //Note
    tblLens = new DefaultTableLens(xss.getTable("tblEtichetta4"));
    tblLens.setObject(1,0,etiCamp.getNote());
    xss.setElement("tblEtichetta4", tblLens);

    //Style
    ReportUtils.formatTableEtichetta(xss, "tblEtichetta4", false, StyleConstants.H_LEFT);

    /***************************************************************
     * Pagina 2
     */

		tblLens = new DefaultTableLens(xss.getTable("tblDati1"));
		tblLens.setObject(1,0,etiCamp.getDescrizioneEtichetta());
		xss.setElement("tblDati1", tblLens);
		
		//Style
		ReportUtils.formatTableHeaderTwoRows(xss, "tblDati1", true); 
		
		tblLens = new DefaultTableLens(xss.getTable("tblDati2"));
		tblLens.setColBackground(0,PDF_COL_BACKGROUND);
		tblLens.setColForeground(0,Color.white);
		tblLens.setColBackground(2,PDF_COL_BACKGROUND);
		tblLens.setColForeground(2,Color.white);
		tblLens.setObject(0,1,etiCamp.getIdRichiesta());
		tblLens.setObject(0,3,etiCamp.getDataInserimentoRichiesta());
		if ("00".equals(etiCamp.getStatoAnomalia()))
		  tblLens.setObject(1,1,"bozza");
		else if (etiCamp.getNumeroCampione()!=null)
		  tblLens.setObject(1,1,etiCamp.getNumeroCampione()+"/"+etiCamp.getAnnoCampione());
		tblLens.setObject(1,3,etiCamp.getDataRicezione());
		xss.setElement("tblDati2", tblLens);
		
		//Style
		ReportUtils.formatTableColumLeftRight(xss, "tblDati2", true);
	    
    try
    {
    	//TODO Quando report ridisegnati con SR9 con header
    	// le due seguenti tabelle non serviranno più
    	// e questo blocco try...catch potrà essere eliminato
    	xss.setElement("tblDati1bis", tblLens);
    	xss.setElement("tblDati2bis", tblLens);
    }
    catch (Exception ex)
    {
    	
    }
    tblLens = new DefaultTableLens(xss.getTable("tblDatiNote"));
    tblLens.setObject(0,1,etiCamp.getNote());
    xss.setElement("tblDatiNote", tblLens);
    //Style
    ReportUtils.formatTableColumLeftRight(xss, "tblDatiNote", true);

    tblLens = new DefaultTableLens(xss.getTable("tblRichiedente"));
    proprietarioRichiedenteFill(tblLens,anagraficaRichiedente,
                                anagraficaUtente.getTipoUtente().charAt(0),comuni,
                                tmpStr,true,false, 2);
    xss.setElement("tblRichiedente", tblLens);
    //Style
    ReportUtils.formatTableHeaderOneRow(xss, "lblRichiedente", true); 

    if (anagraficaTecnico!=null)
    {
      tblLens = new DefaultTableLens(xss.getTable("tblOrganizzazione"));
      organizzazioneFill(tblLens,organizzazioneTecnico,comuni,tmpStr);
      xss.setElement("tblOrganizzazione", tblLens);
      //Style
      ReportUtils.formatTableHeaderOneRow(xss, "lblOrganizzazione", true);
      ReportUtils.formatTableColumLeftRight(xss, "tblOrganizzazione", false);
    }
    // Per non rimuovere il blocco "organizzazione", commentare il seguente blocco "else"
    else
    {
    	ReportUtils.removeRows(xss, "tblOrganizzazione", 1);
      //xss.removeElement("lblOrganizzazione"); // lblOrganizzazione
      //xss.removeElement("tblOrganizzazione"); // 
      //xss.removeElement("nlOrganizzazione"); // NewLine dopo tblOrganizzazione
    }

    tblLens = new DefaultTableLens(xss.getTable("tblProprietario"));
    
    //jira 128
    boolean isPresenteAnagrafe = false;
  
    if (anagraficaProprietario.getCodiceIdentificativo() != null && ! "".equals(anagraficaProprietario.getCodiceIdentificativo()))
    {
    	AnagAziendaVO anagAziendaVO = new AnagAziendaVO();
    	anagAziendaVO.setCUAA(anagraficaProprietario.getCodiceIdentificativo());
    	Vector elencoAziende = beanParametriApplication.getAnagServiceCSIInterface().serviceGetListIdAziende(anagAziendaVO, Boolean.TRUE, Boolean.FALSE);
    	isPresenteAnagrafe = elencoAziende != null && elencoAziende.size() > 0;
    	CuneoLogger.debug(this,"Prima chiamata isPresenteAnagrafe " + isPresenteAnagrafe);
    	if(!isPresenteAnagrafe)
    	{
    		anagAziendaVO = new AnagAziendaVO();
    		anagAziendaVO.setPartitaIVA(anagraficaProprietario.getCodiceIdentificativo());
    		elencoAziende = beanParametriApplication.getAnagServiceCSIInterface().serviceGetListIdAziende(anagAziendaVO, Boolean.TRUE, Boolean.FALSE);
        	isPresenteAnagrafe = elencoAziende != null && elencoAziende.size() > 0;
        	CuneoLogger.debug(this,"Seconda chiamata isPresenteAnagrafe " + isPresenteAnagrafe);
        	if(!isPresenteAnagrafe && anagraficaUtente.getTipoUtente().equals("P"))
        	{
        		CuneoLogger.debug(this,"visto che l'utente collegato è un privato provo ancora a cercare utilizzando serviceGetAziendeAAEPAnagrafe");
        		Long[] idAziende = beanParametriApplication.getAnagServiceCSIInterface().serviceGetAziendeAAEPAnagrafe(anagraficaProprietario.getCodiceIdentificativo(), false, Boolean.FALSE, Boolean.TRUE, false, false).getIdAzienda();
        	  	isPresenteAnagrafe = idAziende != null && idAziende.length > 0;
      		   CuneoLogger.debug(this,"dopo l'ulteriore chiamata isPresenteAnagrafe " + isPresenteAnagrafe);
        	}
    	}
    }
   
    proprietarioRichiedenteFill(tblLens,anagraficaProprietario,
                                ' ',comuni,
                                tmpStr,false,isPresenteAnagrafe,0);
    xss.setElement("tblProprietario", tblLens);
    //Style
    ReportUtils.formatTableHeaderOneRow(xss, "lblProprietario", true);
    ReportUtils.formatTableColumLeftRight(xss, "tblProprietario", false);

    /*
        I dati della fattura devono essere riportati solo se "Fattura S/N" = 'S' su tabella "Fattura".
        In questo caso i dati da riportare dipendono dal contenuto del campo "Fatturare a":
        se = 'U' impostare i dati della "Anagrafica utente" su "Etichetta campione"
        se = 'T' impostare i dati della "Anagrafica tecnico" su "Etichetta campione"
        se = 'P' impostare i dati della "Anagrafica proprietario" su "Etichetta campione"
        se = 'O' impostare i dati della "Organizzazione" collegata alla "Anagrafica tecnico"
                 su "Etichetta campione"  se significativa, altrimenti i dati della
                 "Organizzazione" collegata alla "Anagrafica utente".
        se = 'A' impostare i dati della tabella "Dati fattura"
    */
    DatiFattura datiFattura = new DatiFattura(dataSource, aut);
    datiFattura.select(idRichiesta);
    if ("S".equals(datiFattura.getFatturaSN()))
    {
      tblLens = new DefaultTableLens(xss.getTable("tblFattura"));
      fatturaFill(tblLens, datiFattura,
                  anagraficaUtente, anagraficaProprietario,
                  anagraficaTecnico, organizzazioneTecnico,
                  comuni);
      xss.setElement("tblFattura", tblLens);
      //Style
      ReportUtils.formatTableHeaderOneRow(xss, "lblFattura", true);
      ReportUtils.formatTableColumLeftRight(xss, "tblFattura", false);
    }
    // Per non rimuovere il blocco "fattura", commentare il seguente blocco "else"
    else
    {
    	ReportUtils.removeRows(xss, "tblFattura", 1);
      //xss.removeElement("lblFattura"); // lblFattura
      //xss.removeElement("tblFattura"); // tblFattura
      //xss.removeElement("nlFattura"); // NewLine dopo tblFattura
    }

    // Le coordinate vanno popolate nel caso di "TER", "FOG", "FRU"
    // Non utilizzate invece nel caso di "ERB"
    // if (!"ERB".equals(etiCamp.getCodiceMateriale()))
    //
    // Per farla più semplice, popoliamo le coordinate SE ESISTONO
    // anche perché almeno l'informazione sul comune di provenienza
    // c'è sempre
    
    //jira 227
    if(!etiCamp.getDescMateriale().equalsIgnoreCase("Altre Matrici")) {
	    if (datiAppezzamento.getIdRichiesta()!=-1){
	      tblLens = new DefaultTableLens(xss.getTable("tblCoordinate"));
	     //jira 113
	      //Elisa 27/03/2017 Errore di nullpoiner se il campo TIPO_GEOREFERENZIAZIONE sulla tabella DATI_APPEZZAMENTO è vuoto.  
	      if(aut.getUtente().getTipoUtente() == 'P' || aut.getUtente().getTipoUtente() == 'T')
	    	     if(datiAppezzamento.getTipoGeoreferenziazione()!=null && !datiAppezzamento.getTipoGeoreferenziazione().equals("C"))
	    		 {
	    	    	 datiAppezzamento.setSezione("");
	    	    	 datiAppezzamento.setFoglio("");
	    	    	 datiAppezzamento.setParticellaCatastale("");
	    	    	 datiAppezzamento.setSubparticella("");
	    		 }
		  coordinateFill(tblLens, datiAppezzamento, tmpStr);
	      xss.setElement("tblCoordinate", tblLens);
	
	      //Style
	      ReportUtils.formatTableHeaderOneRow(xss, "lblCoordinate", true);
	      ReportUtils.formatTableColumLeftRight(xss, "tblCoordinate", true);
	    }
	    // Per non rimuovere il blocco "coordinate", commentare il seguente blocco "else"
	    else {
	    	ReportUtils.removeRows(xss, "tblCoordinate", 1);
	      //xss.removeElement("lblCoordinate"); // lblCoordinate
	      //xss.removeElement("tblCoordinate"); // tblCoordinate
	    }
    }
  }

  private void proprietarioRichiedenteFill(DefaultTableLens tblLens,
                                  Anagrafica anagrafica,
                                  char tipoUtente,
                                  Comuni comuni,
                                  String tmpStr,
                                  boolean richiedente,
                                  boolean isPresenteAnagrafe, int visibile)
      throws Exception
  {
    StringBuffer sb = new StringBuffer();
    sb.append(anagrafica.getCognomeRagioneSociale());
    tmpStr = anagrafica.getNome();
    if (tmpStr!=null && richiedente)
      sb.append(" ").append(tmpStr);
    switch (tipoUtente)
    {
     case 'P':
       sb.append(" - Utente ").append("privato");
       break;
     case 'T':
       sb.append(" - Utente ").append("tecnico");
       break;
     case 'L':
       sb.append(" - Utente ").append("LAR");
       break;
    }
    tblLens.setObject(0,1,sb.toString());
    tblLens.setObject(1,1,anagrafica.getCodiceIdentificativo());

    sb.setLength(0);
    if (anagrafica.getIndirizzo()!=null)
      sb.append(anagrafica.getIndirizzo()).append(" - ");
    if (anagrafica.getCap()!=null)
      sb.append(anagrafica.getCap()).append(" ");
    sb.append(comuni.getDescrizioneComuneProv(anagrafica.getComuneResidenza()));
    tblLens.setObject(2,1,sb.toString());

    sb.setLength(0);
    tmpStr=anagrafica.getTelefono();
    sb.append(tmpStr==null?"":"tel: "+tmpStr);
    tmpStr=anagrafica.getFax();
    sb.append(tmpStr==null?"":(sb.length()==0?"":" - ")+"fax: "+tmpStr);
    tmpStr=anagrafica.getCellulare();
    sb.append(tmpStr==null?"":(sb.length()==0?"":" - ")+"cell: "+tmpStr);
    tblLens.setObject(3,1,sb.toString());
    tmpStr=anagrafica.getEmail();
    tblLens.setObject(4,1,(tmpStr==null?"":tmpStr));
    
    if(visibile == 0){
	    if (isPresenteAnagrafe)
	    {
	    	 tmpStr="azienda censita";
	    }
	    else
	    {
	    	tmpStr="azienda NON censita";
	     
	    }
       tblLens.setObject(5,1,(tmpStr==null?"":tmpStr));
    }

    return;
  }

  private void organizzazioneFill(DefaultTableLens tblLens,
                                  OrganizzazioneProfessionale organizzazione,
                                  Comuni comuni,
                                  String tmpStr)
      throws Exception
  {
    StringBuffer sb = new StringBuffer();

    tblLens.setObject(0,1,organizzazione.getTipoOrganizzazione());
    tblLens.setObject(1,1,organizzazione.getRagioneSociale());
    tblLens.setObject(2,1,organizzazione.getCfPartitaIva());
    tblLens.setObject(3,1,organizzazione.getSedeTerritoriale());

    sb.setLength(0);
    if (organizzazione.getIndirizzo()!=null)
      sb.append(organizzazione.getIndirizzo()).append(" - ");
    if (organizzazione.getCap()!=null)
      sb.append(organizzazione.getCap()).append(" ");

    sb.append(organizzazione.getComune()).append(" (");
    sb.append(organizzazione.getSiglaProvincia()).append(")");
    tblLens.setObject(4,1,sb.toString());

    sb.setLength(0);
    tmpStr=organizzazione.getTelefono();
    sb.append(tmpStr==null?"":"tel: "+tmpStr);
    tmpStr=organizzazione.getFax();
    sb.append(tmpStr==null?"":(sb.length()==0?"":" - ")+"fax: "+tmpStr);
    tblLens.setObject(5,1,sb.toString());
    tmpStr=organizzazione.getEmail();
    if (tmpStr!=null) tblLens.setObject(6,1,tmpStr);

    return;
  }

  private void coordinateFill(DefaultTableLens tblLens,
                              DatiAppezzamento datiAppezzamento,
                              String tmpStr)
  {
    tblLens.setColBackground(0,PDF_COL_BACKGROUND);
    tblLens.setColForeground(0,Color.white);
    tblLens.setColBackground(2,PDF_COL_BACKGROUND);
    tblLens.setColForeground(2,Color.white);

    tmpStr = datiAppezzamento.getComuneAppezzamentoDescr();
    if (tmpStr == null) tmpStr = "";
    tblLens.setObject(0,1,tmpStr);
    tmpStr = datiAppezzamento.getLocalitaAppezzamento();
    if (tmpStr == null) tmpStr = "";
    tblLens.setObject(0,3,tmpStr);
    
    tmpStr = datiAppezzamento.getSezione();
    if (tmpStr == null) tmpStr = "";
    tblLens.setObject(1,1,tmpStr);
    tmpStr = datiAppezzamento.getFoglio();
    if (tmpStr == null) tmpStr = "";
    tblLens.setObject(1,3,tmpStr);
    tmpStr = datiAppezzamento.getParticellaCatastale();
    if (tmpStr == null) tmpStr = "";
    tblLens.setObject(2,1,tmpStr);
    tmpStr = datiAppezzamento.getSubparticella();
    if (tmpStr == null) tmpStr = "";
    tblLens.setObject(2,3,tmpStr);
    
    tmpStr = datiAppezzamento.getCoordinataNordBoaga();
    if (tmpStr == null) tmpStr = "";
    tblLens.setObject(3,1,tmpStr);
    tmpStr = datiAppezzamento.getCoordinataEstBoaga();
    if (tmpStr == null) tmpStr = "";
    tblLens.setObject(3,3,tmpStr);
    
    
    tmpStr = datiAppezzamento.getCoordinataNordUtm();
    if (tmpStr == null) tmpStr = "";
    tblLens.setObject(4,1,tmpStr);
    tmpStr = datiAppezzamento.getCoordinataEstUtm();
    if (tmpStr == null) tmpStr = "";
    tblLens.setObject(4,3,tmpStr);
    
    //jira 116
    String ccg = datiAppezzamento.getCoordGradi();
    if(ccg!=null){
    if(ccg.equals(DatiAppezzamento.GRADI_DECIMALI)) //DD
    {
    	tmpStr = datiAppezzamento.getGradiNordTot();
        if (tmpStr == null) 
        	  tmpStr = "";
        else
        	tmpStr = tmpStr + "°";
        tblLens.setObject(5,1,tmpStr);
        
        tmpStr = datiAppezzamento.getGradiEstTot();
        if (tmpStr == null) 
        	  tmpStr = "";
        else
        	tmpStr = tmpStr + "°";
        tblLens.setObject(5,3,tmpStr);
    }
    else if(ccg.equals(DatiAppezzamento.GRADI_MINUTI_DECIMALI)) //DM
    {
    	tmpStr= datiAppezzamento.getGradiNord();
    	if(tmpStr != null)
    	{
    		tmpStr = tmpStr + "° ";
    	}
    	if(datiAppezzamento.getMinutiNordTot() !=null)
    	{
    		tmpStr = tmpStr + datiAppezzamento.getMinutiNordTot() + "'";
    	}
    	if (tmpStr == null) tmpStr = "";
    	tblLens.setObject(5,1,tmpStr);
    	
    	/*****************************/
    	tmpStr= datiAppezzamento.getGradiEst();
    	if(tmpStr != null)
    	{
    		tmpStr = tmpStr + "° ";
    	}
    	if(datiAppezzamento.getMinutiEstTot() !=null)
    	{
    		tmpStr = tmpStr + datiAppezzamento.getMinutiEstTot() + "'";
    	}
    	if (tmpStr == null) tmpStr = "";
    	tblLens.setObject(5,3,tmpStr);  		
    }
    else if (ccg.equals(DatiAppezzamento.GRADI_MINUTI_SECONDI)) //DMS
    {
    	tmpStr= datiAppezzamento.getGradiNord();
    	if(tmpStr != null)
    	{
    		tmpStr = tmpStr + "° ";
    	}
    	if(datiAppezzamento.getMinutiNord() !=null)
    	{ 
    		tmpStr = tmpStr + datiAppezzamento.getMinutiNord() + "' ";
    	}
    	if(datiAppezzamento.getSecondiNordTot() != null)
    	{
    		tmpStr = tmpStr + datiAppezzamento.getSecondiNordTot() + "''";
    	}
    	if (tmpStr == null) tmpStr = "";
    	tblLens.setObject(5,1,tmpStr);
    	
    	/*****************************/
    	tmpStr= datiAppezzamento.getGradiEst();
    	if(tmpStr != null)
    	{
    		tmpStr = tmpStr + "° ";
    	}
    	if(datiAppezzamento.getMinutiEst() !=null)
    	{ 
    		tmpStr = tmpStr + datiAppezzamento.getMinutiEst() + "' ";
    	}
    	if(datiAppezzamento.getSecondiEstTot() != null)
    	{
    		tmpStr = tmpStr + datiAppezzamento.getSecondiEstTot() + "''";
    	}
    	if (tmpStr == null) tmpStr = "";
    	tblLens.setObject(5,3,tmpStr);
    }
    }   
    
    
  }

    /*
        I dati della fattura devono essere riportati solo se "Fattura S/N" = 'S' su tabella "Fattura".
        In questo caso i dati da riportare dipendono dal contenuto del campo "Fatturare a":
    */

    /**
     * Questo metodo viene chiamato solo se "S".equals(datiFattura.getFatturaSN())
     *
     * I dati da riportare dipendono dal valore du datiFattura.getFatturare()
     *
     *  se = "U" impostare i dati della "Anagrafica utente" su "Etichetta campione"
     *  se = "T" impostare i dati della "Anagrafica tecnico" su "Etichetta campione"
     *  se = "P" impostare i dati della "Anagrafica proprietario" su "Etichetta campione"
     *  se = "O" impostare i dati della "Organizzazione" collegata alla "Anagrafica tecnico"
     *           su "Etichetta campione"  se significativa, altrimenti i dati della
     *           "Organizzazione" collegata alla "Anagrafica utente".
     *  se = "A" impostare i dati della tabella "Dati fattura"
     *
     * @param tblLens Oggetto dello ReportSheet
     * @param datiFattura Oggetto contenente le informazioni sulla fattura e le info anagrafiche "A"
     * @param anagraficaUtente Oggetto contenente info anagrafiche "U"
     * @param anagraficaProprietario Oggetto contenente info anagrafiche "P"
     * @param anagraficaTecnico Oggetto contenente info anagrafiche "T"
     * @param organizzazioneTecnico Oggetto contenente info anagrafiche "O"
     * @param comuni Oggetto per recuperare "Comune (PV)"
     * @throws Exception
     */
  private void fatturaFill(DefaultTableLens tblLens,
                           DatiFattura datiFattura,
                           Anagrafica anagraficaUtente,
                           Anagrafica anagraficaProprietario,
                           Anagrafica anagraficaTecnico,
                           OrganizzazioneProfessionale organizzazioneTecnico,
                           Comuni comuni)
      throws Exception
  {
    StringBuffer ind=new StringBuffer();
    //in caso si voglia rimettere aggiungere in tutti i pdf 
    //--> <TD Type="string"><![CDATA[Richiesta spedizione]]></TD><TD Type="null"><![CDATA[null]]></TD></TR>
//    if ("S".equals(datiFattura.getSpedizione()))
//      tblLens.setObject(0,1,Utili.iso2unicode("Sì (importo ")+datiFattura.getImportoSpedizione().replace('.',',')+" euro)");
//    else
//      tblLens.setObject(0,1,"No");

    if ("U".equals(datiFattura.getFatturare()))
    {
      ind.setLength(0);
      if (anagraficaUtente.getIndirizzo()!=null)
        ind.append(anagraficaUtente.getIndirizzo()).append(" - ");
      if (anagraficaUtente.getCap()!=null)
        ind.append(anagraficaUtente.getCap()).append(" ");
      ind.append(comuni.getDescrizioneComuneProv(anagraficaUtente.getComuneResidenza()));

      tblLens.setObject(0,1,anagraficaUtente.getCognomeRagioneSociale()+" "+
                            anagraficaUtente.getNome());
      tblLens.setObject(1,1,anagraficaUtente.getCodiceIdentificativo());
      tblLens.setObject(2,1,ind.toString());
    }
    else if ("T".equals(datiFattura.getFatturare()))
    {
      ind.setLength(0);
      if (anagraficaTecnico.getIndirizzo()!=null)
        ind.append(anagraficaTecnico.getIndirizzo()).append(" - ");
      if (anagraficaTecnico.getCap()!=null)
        ind.append(anagraficaTecnico.getCap()).append(" ");
      ind.append(comuni.getDescrizioneComuneProv(anagraficaTecnico.getComuneResidenza()));

      tblLens.setObject(0,1,anagraficaTecnico.getCognomeRagioneSociale()+" "+
                            anagraficaTecnico.getNome());
      tblLens.setObject(1,1,anagraficaTecnico.getCodiceIdentificativo());
      tblLens.setObject(2,1,ind.toString());
    }
    else if ("P".equals(datiFattura.getFatturare()))
    {
      ind.setLength(0);
      if (anagraficaProprietario.getIndirizzo()!=null)
        ind.append(anagraficaProprietario.getIndirizzo()).append(" - ");
      if (anagraficaProprietario.getCap()!=null)
        ind.append(anagraficaProprietario.getCap()).append(" ");
      ind.append(comuni.getDescrizioneComuneProv(anagraficaProprietario.getComuneResidenza()));

      tblLens.setObject(0,1,anagraficaProprietario.getCognomeRagioneSociale());
      tblLens.setObject(1,1,anagraficaProprietario.getCodiceIdentificativo());
      tblLens.setObject(2,1,ind.toString());
    }
    else if ("O".equals(datiFattura.getFatturare()))
    {
      ind.setLength(0);
      if (organizzazioneTecnico.getIndirizzo()!=null)
        ind.append(organizzazioneTecnico.getIndirizzo()).append(" - ");
      if (organizzazioneTecnico.getCap()!=null)
        ind.append(organizzazioneTecnico.getCap()).append(" ");
      String comune=organizzazioneTecnico.getComune();
      if (comune==null) comune="";
      String prov=organizzazioneTecnico.getSiglaProvincia();
      if (prov!=null) prov=" ("+prov+")";
      else prov="";
      ind.append(comune+prov);

      tblLens.setObject(0,1,organizzazioneTecnico.getRagioneSociale());
      tblLens.setObject(1,1,organizzazioneTecnico.getCfPartitaIva());
      tblLens.setObject(2,1,ind.toString());
    }
    else
    {
      ind.setLength(0);
      if (datiFattura.getIndirizzo()!=null)
        ind.append(datiFattura.getIndirizzo()).append(" - ");
      if (datiFattura.getCap()!=null)
        ind.append(datiFattura.getCap()).append(" ");
      ind.append(comuni.getDescrizioneComuneProv(datiFattura.getComune()));

      tblLens.setObject(0,1,datiFattura.getRagioneSociale());
      tblLens.setObject(1,1,datiFattura.getCfPartitaIva());
      tblLens.setObject(2,1,ind.toString());
    }
    //i dati PEC e cod_destinatario sono solo sulla tabella DATI_FATTURA
    tblLens.setObject(3,1,datiFattura.getCod_destinatario());
    tblLens.setObject(4,1,datiFattura.getPec());
  }

  protected String selDescr(String codStr[], String descStr[], String tmpStr)
  {
    if (tmpStr!=null)
      for(int i=0;i<codStr.length;i++)
        if (tmpStr.equals(codStr[i]))
          return descStr[i];
    return "";
  }

  protected String selDescr(Vector cod, Vector desc, String tmpStr)
  {
    return selDescr((String[])cod.toArray(new String[0]),(String[])desc.toArray(new String[0]),tmpStr);
  }

  protected void setCellChecked(DefaultTableLens tblLens, int row, int col, boolean checked)
  {
    if (checked)
    {
    	tblLens.setObject(row, col, PDF_ANALISI_CHECKED);
    }
  }
}