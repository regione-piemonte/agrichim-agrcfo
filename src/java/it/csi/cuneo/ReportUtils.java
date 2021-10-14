package it.csi.cuneo;

import inetsoft.report.ReportElement;
import inetsoft.report.StyleConstants;
import inetsoft.report.StyleSheet;
import inetsoft.report.TabularSheet;
import inetsoft.report.lens.DefaultTableLens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

/**
 * Title:        Agrichim - Front Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author 			 Nadia B.
 * @version 1.0.0
 */

public final class ReportUtils
{
	public static Color PDF_COL_BACKGROUND = new Color(160,160,164);

  /**
   * Nel footer viene visualizzato soltanto il numero di pagina allineato a destra
   * @param report
   */
  protected static final void setPageNumberRight(TabularSheet report)
  {
    report.setCurrentFooter(StyleSheet.DEFAULT_FOOTER);
    if (report.getFooterFromEdge() == 0)
    {
    	report.setFooterFromEdge(0.15);
    }
    report.setCurrentFont(new Font("SansSerif", Font.ITALIC, 7));
    report.setCurrentAlignment(StyleConstants.H_RIGHT|StyleConstants.V_BOTTOM);
    report.addFooterText("Pagina {P} di {N}");    
  }

  /**
   * Footer
   * @param report
   */
  public static final void setFooter(TabularSheet report)
  {
  	setPageNumberRight(report);
  }
  
  /**
   * Rimuove n righe a partire da quella dove è posizionato l'elemento passato in input
   * @param report
   * @param nameElement
   * @param numRowsDeleted
   */
  public static final void removeRows(TabularSheet report, String nameElement, int numRowsDeleted)
  {
	  try
	  {
	    Point elStartCell = report.getElementCell(report.getElement(nameElement));
	    int rowPosition = (int) Math.round(elStartCell.getY());
    	report.removeRows(rowPosition, numRowsDeleted);
    }
    catch (Exception e)
    {
    	CuneoLogger.error(e, "Errore nella rimozione di un elemento del report (non presente?): " + nameElement);
    	CuneoLogger.error(e, Utili.getStackTraceAsString(e));
    }
  }

  /**
   * Restituisce il font dell'elemento del report specificato
   * @param report
   * @param nameElement
   * @return Font
   * @throws Exception
   */
  public static Font getFont(TabularSheet report, String nameElement) throws Exception
  {
    ReportElement reportElement = (ReportElement) report.getElement(nameElement);
    return reportElement.getFont();    
  }

  /**
   * Restituisce il font in grassetto
   * @param report
   * @param nameElement
   * @return Font
   * @throws Exception
   */
  public static Font getFontBold(TabularSheet report, String nameElement) throws Exception
  {
    Font font = getFont(report, nameElement);
    
    return new Font(font.getFontName(), Font.BOLD, font.getSize());    
  }

  /**
   * Restituisce il font normale
   * @param report
   * @param nameElement
   * @return Font
   * @throws Exception
   */
  public static Font getFontPlain(TabularSheet report, String nameElement) throws Exception
  {
    Font font = getFont(report, nameElement);
    
    return new Font(font.getFontName(), Font.PLAIN, font.getSize());    
  }

	/**
	 * Formatta le colonne di destra e sinistra
	 * @param report
	 * @param tableName
	 * @param isBold
	 * @throws Exception
	 */
	public static void formatTableColumLeftRight(TabularSheet report, String tableName, boolean isBold) throws Exception
	{
		DefaultTableLens dtl = new DefaultTableLens(report.getTable(tableName));
		
		int numCols = dtl.getColCount();
		
		Font font = getFont(report, tableName);
		if (isBold)
		{
			dtl.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize()));
		}
			
		for (int c = 0; c < numCols; c++)
		{
			if (c % 2 == 0)
			{
				//Colonna di sinistra
				
				dtl.setColBackground(0, PDF_COL_BACKGROUND);
				dtl.setColForeground(0, Color.white);			
			}
		}
		
		report.setElement(tableName, dtl);
	}

	/**
	 * Formatta la tabella di intestazione composta da una riga
	 * @param report
	 * @param tableName
	 * @param isBold
	 * @throws Exception
	 */
	public static void formatTableHeaderOneRow(TabularSheet report, String tableName, boolean isBold) throws Exception
	{
		DefaultTableLens dtl = new DefaultTableLens(report.getTable(tableName));
		
		Font font = getFont(report, tableName);
		if (isBold)
		{
			int numCols = dtl.getColCount();
			for (int c = 0; c < numCols; c++)
			{
				dtl.setFont(0, c, new Font(font.getFontName(), Font.BOLD, font.getSize()));		
			}
		}

		//Allineamento al centro
		dtl.setAlignment(0, 0, StyleConstants.H_CENTER);
		
		dtl.setRowBackground(0, PDF_COL_BACKGROUND);
		dtl.setRowForeground(0, Color.white);
		
		report.setElement(tableName, dtl);
	}

	/**
	 * Formatta la tabella di intestazione composta da due righe
	 * @param report
	 * @param tableName
	 * @param isBold
	 * @throws Exception
	 */
	public static void formatTableHeaderTwoRows(TabularSheet report, String tableName, boolean isBold) throws Exception
	{
		DefaultTableLens dtl = new DefaultTableLens(report.getTable(tableName));

		if (isBold)
		{
			dtl.setFont(getFontBold(report, tableName));
		}

		//Allineamento al centro
		dtl.setAlignment(0, 0, StyleConstants.H_CENTER);
		dtl.setAlignment(1, 0, StyleConstants.H_CENTER);
		
		dtl.setRowBackground(0, PDF_COL_BACKGROUND);
		dtl.setRowForeground(0, Color.white);
		
		//Viene tolto il bordo che separa le due righe
		dtl.setRowBorder(0, 0, StyleConstants.NO_BORDER);
		
		report.setElement(tableName, dtl);
	}

	/**
	 * Formatta la tabella contenente l'etichetta
	 * @param report
	 * @param tableName
	 * @param isBold
	 * @throws Exception
	 */
	public static void formatTableEtichetta(TabularSheet report, String tableName, boolean isBold, int alignment) throws Exception
	{
		DefaultTableLens dtl = new DefaultTableLens(report.getTable(tableName));
		
		Font font = getFont(report, tableName);
		Font fontBold = getFontBold(report, tableName);
		Font fontPlain = getFontPlain(report, tableName);

		int numRows = dtl.getRowCount();
		int numCols = dtl.getColCount();
			
		for (int r = 0; r < numRows; r++)
		{
			boolean nascondiBordo = false;

			if (r % 2 != 0)
			{
				//Riga di sotto
				dtl.setRowFont(r, isBold ? fontBold : fontPlain);
				
				if (r != 0) nascondiBordo = true;				
			}
			else
			{
				//Riga di sopra
				
				//Carattere più piccolo
				dtl.setRowFont(r, new Font(font.getFontName(), font.getStyle(), font.getSize() - 2));
			}
			for (int c = 0; c < numCols; c++)
			{
				if (r % 2 != 0)
				{
					//Riga di sotto	
					//Allineamento passato in input
					dtl.setAlignment(r, c, alignment);
				}
				else
				{
					//Riga di sopra
					//Allineamento al centro
					dtl.setAlignment(r, c, StyleConstants.H_CENTER);
				}
				if (nascondiBordo)
				{
					//Viene tolto il bordo che separa le due righe
					dtl.setRowBorder(r - 1, c, StyleConstants.NO_BORDER);					
				}		
			}
		}
		
		report.setElement(tableName, dtl);
	}

  /**
   * Intestazione tabella
   * @param report
   * @param dtl
   * @param tableName
   */
  public static void setIntestazioneTabella(TabularSheet report, DefaultTableLens dtl, String tableName) throws Exception
  {
    setIntestazioneTabella(report, dtl, tableName, false);
  }

  /**
   * Intestazione tabella
   * @param report
   * @param dtl
   * @param tableName
   * @param isPageOverflowProblem
   */
  public static void setIntestazioneTabella(TabularSheet report, DefaultTableLens dtl, String tableName, boolean isPageOverflowProblem) throws Exception
  {
    if (isPageOverflowProblem)
    {
    	dtl.setHeaderRowCount(0);
    }
    else
    {
      //Se vale true invece significa che la tabella sborda sul footer della pagina;
      //per risolvere il problema la tabella sia da designer sia da codice NON deve avere l'intestazione impostata
    }

    Font fontBold = getFontBold(report, tableName);
    int iRowsHeader = dtl.getRowCount();
    int iColCount = dtl.getColCount();
    
    for (int r = 0; r < iRowsHeader; r++)
    {
    	dtl.setRowFont(r, fontBold);
      for (int c = 0; c < iColCount; c++)
      {
      	dtl.setAlignment(r, c, StyleConstants.H_CENTER);
      }
    }
    
    report.setElement(tableName, dtl);
  }
}