function controllaPartitaIVA(pi)
{
	if( pi == '' )  return "Inserire una partita IVA valida";
	if( pi.length != 11 )
		return "Una partita IVA deve essere composta esattamente da 11 caratteri numerici";
	validi = "0123456789";
	for( i = 0; i < 11; i++ ){
		if( validi.indexOf( pi.charAt(i) ) == -1 )
			return "Partita IVA contenente un carattere non valido in posizione "+(i+1)+" ('"+pi.charAt(i)+"')";
			// return "Partita IVA contenente un carattere non valido (+'"+pi.charAt(i)+"')";
	}
	s = 0;
	for( i = 0; i <= 9; i += 2 )
		s += pi.charCodeAt(i) - '0'.charCodeAt(0);
	for( i = 1; i <= 9; i += 2 ){
		c = 2*( pi.charCodeAt(i) - '0'.charCodeAt(0) );
		if( c > 9 )  c = c - 9;
		s += c;
	}
	if( ( 10 - s%10 )%10 != pi.charCodeAt(10) - '0'.charCodeAt(0) )
		return "Codice di controllo della partita IVA (ultimo numero) non valido";
	return "";
}
