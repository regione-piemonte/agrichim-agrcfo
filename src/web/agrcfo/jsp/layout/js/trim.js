String.prototype.trim = function()
{
  /**
  * Non funziona con netscape
  * return( this.replace(/^\s*([\s\S]*\S+)\s*$|^\s*$/,"$1") );
  */

  //Funziona anche con Netscape
  return (this.replace(/\s*$/,"")).replace(/^\s*/,"");
}