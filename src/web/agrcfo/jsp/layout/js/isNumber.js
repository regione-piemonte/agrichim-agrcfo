function isNumber(Input)
{
	var CharacterCheck;

        if (Input.length == 0 ) return false;

	for (var i=0; i < Input.length; i++)
	{
		//Assigns the CharacterCheck variable to each character of input in succession
		CharacterCheck = Input.substring(i, i+1);

		//If the CharacterCheck variable matches any one of the digits in the numbers string,
		//then it is a number and the counter is incremented by one.
		//If it doesn't the counter is not incremented.
		 if (! ((CharacterCheck >= "0") && (CharacterCheck <= "9")) )
                   return false;
	}

	//If the counter does not match the length of the number of characters in the input then one of
	//the characters was non-numeric. That means the user is unable to diferentiate between a number and letter.
        return true;
}

function isNotNumber(Input)
{
	return !isNumber(Input);
}

function conversioneDDtoDM(input, minuti, decimal)
{
  /*                                                                   
    Conversione DD > DM
		gg,ddddd
		la parte decimale dei gradi (intesa come 0,dddddd) deve essere
		moltiplicata per 60, il numero ottenuto sono i minuti decimali
		(arrotondamento al 6° decimale)
  */
	input='0.'+input;
	input=(input*60);
  
	var minutiNum=Math.floor(input);
	var decimalNum=input-minutiNum;

	decimalNum = +decimalNum.toFixed(6);
  decimalNum=(""+decimalNum).substr(2);
  
  if (decimalNum=='') decimalNum='0';               
  
	minuti.val(minutiNum);
	decimal.val(decimalNum);
}

function conversioneDMtoDD(inputGradi, inputMinuti, inputDecimali, gradi, minuti, decimal)
{
  /*
    Conversione DM > DD
    gg°   mm,ddddd'
    i minuti decimali mm,dddddd devono essere divisi per 60, il numero
    ottenuto viene sommato ai gradi per ottenere i gradi decimali
    (arrotondamento al 6° decimale)
  */
  inputDecimali='0.'+inputDecimali;
  inputMinuti=(Number(inputMinuti)+Number(inputDecimali))/60;
  
  inputGradi= Number(inputGradi) + Math.floor(inputMinuti);
  
  var decimalNum=inputMinuti-Math.floor(inputMinuti);
  
  inputDecimali = +decimalNum.toFixed(6);
  inputDecimali=(""+inputDecimali).substr(2);
  
  if (inputDecimali=='') inputDecimali='0';               
  
  minuti.val("");
  gradi.val(inputGradi);
  decimal.val(inputDecimali);
}

function conversioneDMStoDD(inputGradi, inputMinuti, inputSecondi, inputDecimali, gradi, minuti, secondi, decimal)
{
  /*
    Conversione DMS > DD
    gg°   mm'  ss,ddddd''
    i secondi ss,dddddd devono essere divisi per 60, il numero ottenuto
    viene sommato ai minuti (mm) per ottenere i minuti decimali (qui sarebbe
    meglio non arrotondare)
    i minuti decimali ottenuti mm,dddddd devono essere divisi per 60, il
    numero ottenuto viene sommato ai gradi (gg) per ottenere i gradi
    decimali (arrotondamento al 6° decimale)
  */
  inputDecimali='0.'+inputDecimali;
  inputMinuti=(Number(inputMinuti)+(Number(inputSecondi)+Number(inputDecimali))/60)/60;
  
  inputGradi= Number(inputGradi) + Math.floor(inputMinuti);
  
  var decimalNum=inputMinuti-Math.floor(inputMinuti);
  
  inputDecimali = +decimalNum.toFixed(6);
  inputDecimali=(""+inputDecimali).substr(2);
  
  if (inputDecimali=='') inputDecimali='0';               
  
  minuti.val("");
  secondi.val("");  
  gradi.val(inputGradi);
  decimal.val(inputDecimali);
}

function conversioneDDtoDMS(inputDecimali, minuti, secondi, decimal)
{
  /*
    Conversione DD > DMS
    gg,ddddd
    la parte decimale dei gradi (intesa come 0,dddddd) deve essere
    moltiplicata per 60
    la parte intera del numero ottenuto sono i minuti
    la parte decimale del numero ottenuto (intesa come 0,dddddd) viene
    ancora moltiplicata per 60 e il numero ottenuto sono i secondi decimali
    (arrotondamento al 6° decimale)
  */
  
  inputDecimali='0.'+inputDecimali;
  var temp=Number(inputDecimali)*60;
  
  
  minutiNum= Math.floor(temp);
  
  var secondiNum=(temp-minutiNum)*60;
  
  
  
  var decimalNum=secondiNum-Math.floor(secondiNum);
  
  inputDecimali = +decimalNum.toFixed(6);
  inputDecimali=(""+inputDecimali).substr(2);
               
  if (inputDecimali=='') inputDecimali='0';               
  
  minuti.val(minutiNum);
  secondi.val(Math.floor(secondiNum));  
  decimal.val(inputDecimali);
}


function conversioneDMtoDMS( inputDecimali, secondi, decimal)
{
  /*
    Conversione DM > DMS
    gg mm,ddddd
    la parte decimale dei minuti (intesa come 0,dddddd) deve essere
    moltiplicata per 60, il numero ottenuto sono i secondi decimali
    (arrotondamento al 6° decimale)
  */
  inputDecimali='0.'+inputDecimali;
  var temp=Number(inputDecimali)*60;
  
  var secondiNum=Math.floor(temp)
  
  inputDecimali=temp-secondiNum;
  
  inputDecimali = +inputDecimali.toFixed(6);
  inputDecimali=(""+inputDecimali).substr(2);
  
  if (inputDecimali=='') inputDecimali='0';               
  
  secondi.val(secondiNum);
  decimal.val(inputDecimali);
}

function conversioneDMStoDM(inputMinuti, inputSecondi, inputDecimali, minuti, secondi, decimal)
{
  /*
    Conversione DMS > DM
    gg°   mm'  ss,ddddd''
    i secondi ss,dddddd devono essere divisi per 60, il numero ottenuto
    viene sommato ai minuti (mm) per ottenere i minuti decimali
    (arrotondamento al 6° decimale)
  */
  inputDecimali='0.'+inputDecimali;
  var temp=Number(inputMinuti)+(Number(inputSecondi)+Number(inputDecimali))/60;
  
  inputMinuti=Math.floor(temp);
  
  var decimalNum=temp-inputMinuti;
  
  inputDecimali = +decimalNum.toFixed(6);
  inputDecimali=(""+inputDecimali).substr(2);
  
  if (inputDecimali=='') inputDecimali='0';               
  
  minuti.val(inputMinuti);
  secondi.val("");  
  decimal.val(inputDecimali);
}
