function controllaCodiceFiscale(cf)
{
	var validiNum, validiLet, i, s, set1, set2, setpari, setdisp;
	if( cf == '' )  return "Inserire un codice fiscale valido";
	cf = cf.toUpperCase();
	if( cf.length != 16 )
		return "Un codice fiscale deve essere composto esattamente da 16 caratteri";
         set1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
         set2 = "ABCDEFGHIJABCDEFGHIJKLMNOPQRSTUVWXYZ";
         setpari = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
         setdisp = "BAKPLCQDREVOSFTGUHMINJWZYX";
         s = 0;
         for( i = 1; i <= 13; i += 2 )
                 s += setpari.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
         for( i = 0; i <= 14; i += 2 )
                 s += setdisp.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
         if( s%26 != cf.charCodeAt(15)-'A'.charCodeAt(0) )
                 return "Codice di controllo del codice fiscale (ultima lettera) non valido";
                return "";

/*
	// return "Codice fiscale contenente un carattere non valido (+'"+cf.charAt(i)+"')";
  validiNum = "0123456789";
  validiLet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

  for( i = 0; i <= 5; i ++ )
    if (validiLet.indexOf( cf.charAt(i)) == -1)
      return "Codice fiscale contenente un carattere non valido in posizione "+(i+1)+" ('"+cf.charAt(i)+"')";
  for( i = 6; i <= 7; i ++ )
    if (validiNum.indexOf( cf.charAt(i)) == -1)
      return "Codice fiscale contenente un carattere non valido in posizione "+(i+1)+" ('"+cf.charAt(i)+"')";
  if (validiLet.indexOf( cf.charAt(i)) == -1)
    return "Codice fiscale contenente un carattere non valido in posizione "+(i+1)+" ('"+cf.charAt(i)+"')";
  for( i = 9; i <= 10; i ++ )
    if (validiNum.indexOf( cf.charAt(i)) == -1)
      return "Codice fiscale contenente un carattere non valido in posizione "+(i+1)+" ('"+cf.charAt(i)+"')";
  if (validiLet.indexOf( cf.charAt(i)) == -1)
    return "Codice fiscale contenente un carattere non valido in posizione "+(i+1)+" ('"+cf.charAt(i)+"')";
  for( i = 12; i <= 14; i ++ )
    if (validiNum.indexOf( cf.charAt(i)) == -1)
      return "Codice fiscale contenente un carattere non valido in posizione "+(i+1)+" ('"+cf.charAt(i)+"')";
  if (validiLet.indexOf( cf.charAt(i)) == -1)
    return "Codice fiscale contenente un carattere non valido in posizione "+(i+1)+" ('"+cf.charAt(i)+"')";
*/

}
