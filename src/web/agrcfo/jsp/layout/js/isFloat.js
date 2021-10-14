function isFloat(Input,precision)
{
	var CharacterCheck;
        var counterPunto = 0;
        var posPunto = 0;

        if (Input.length == 0 ) return false;

	for (var i=0; i < Input.length; i++)
	{
		//Assigns the CharacterCheck variable to each character of input in succession
		CharacterCheck = Input.substring(i, i+1);

		//If the CharacterCheck variable matches any one of the digits in the numbers string,
		//then it is a number and the counter is incremented by one.
		//If it doesn't the counter is not incremented.
                if (CharacterCheck==".")
                {
                         counterPunto++;
                         posPunto = i;
                         if (counterPunto >1) return false;
                }
                if (! (((CharacterCheck >= "0") && (CharacterCheck <= "9")) || (CharacterCheck==".") || (CharacterCheck=="-") ))
                   return false;
	}

        if (typeof precision!="undefined")
            if (posPunto>0)
                if ( (i-posPunto-1) > precision )
                   return false;
        if (Input.length == 1)
        {
          CharacterCheck = Input.substring(0, 1);
          if (CharacterCheck == '.' || CharacterCheck == '-')
             return false;
        }           

        return true;
}

