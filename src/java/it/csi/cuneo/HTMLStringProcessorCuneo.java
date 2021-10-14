package it.csi.cuneo;
import java.util.*;

/**
 * Title:        Agrichim - Front Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public final class HTMLStringProcessorCuneo extends it.csi.jsf.htmpl.StringProcessor
{
  private static final long serialVersionUID = 6571905318657764276L;

  private Map caratteriSpeciali = new TreeMap(
        /**
         * Il comparatore ha la sola utilità di dare la precedenza
         * alla chiave "&" in modo che non vengano fatte
         * "sostituzioni sovrapposte"
         */
        new Comparator() {
            public int compare(Object o1, Object o2) {
                String s1 = (String) o1;
                String s2 = (String) o2;
                if ("&".equals(s1)) {
                    if ("&".equals(s2)) {
                        return 0;
                    }
                    return -1;
                }

                if ("&".equals(s2)) {
                    return 1;
                }
                return s1.compareTo(s2);
            }

            public boolean equals(String obj) {
                return obj.equals(this);
            }
        }
    );

    public String process(String input) {
        if (input == null) {
            return null;
        }

        StringBuffer newString = new StringBuffer(input);

        Map cs = caratteriSpeciali;
        Set entrySet = cs.entrySet() ;
        Iterator iter = entrySet.iterator();
        while (iter.hasNext()) {
            Map.Entry item = (Map.Entry)iter.next();
            String str = newString.toString();
            String key = (String)item.getKey();
            String value = (String)item.getValue();
            int idxChar;
            int fromIndex = 0;
            while ((idxChar = str.indexOf(key, fromIndex)) != -1) {
                //int start = idxChar;
                int end = idxChar + key.length();
                newString.replace(idxChar, end, value);
                str = newString.toString();
                fromIndex = end + 1;
            }
        }
        return newString.toString();
    }


   public HTMLStringProcessorCuneo() {
        Map cs = caratteriSpeciali;
        cs.put("\"", "&quot;");
        cs.put("<", "&lt;");
        cs.put(">", "&gt;");
   }
}