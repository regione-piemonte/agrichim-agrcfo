/**
 * Questsa funzione controlla che una data passata gg mm aa sia
 * corretta. Restituisce true se la data è corretta
 * Se vincolo è valorizzato controlla che la data inserita sia al
 * massimo uguale alla data odierna
 * */
function controlla(gg,mm,aa,vincolo)
{
     if (gg.length!=2) gg='0'+gg;
     if (mm.length!=2) mm='0'+mm;
     if (aa.length!=4) return false;
     strdata=gg+"/"+mm+"/"+aa;
     data = new Date(aa,mm-1,gg);
     daa=data.getFullYear().toString();
     dmm=(data.getMonth()+1).toString();
        dmm=dmm.length==1?"0"+dmm:dmm
     dgg=data.getDate().toString();
        dgg=dgg.length==1?"0"+dgg:dgg;
     dddata=dgg+"/"+dmm+"/"+daa;
     if (dddata!=strdata) return false;
     if (vincolo==null)
     {
       return true;
     }
     else
     {
       today = new Date();
       if (today.getTime()<data.getTime())
        return false;
       return true;
     }
}

/**
 * Questa funzione controlla che la data passata gg1 mm1 aa1 sia
 * minore o uguale alla data passata gg2 mm2 aa2. In questo caso
 * mi restituisce true, altrimenti false. Prerequisito perchè avvenga
 * tutto correttamente e che le date siano corrette.
 * */
function confronta(gg1,mm1,aa1,gg2,mm2,aa2)
{
     data1 = new Date(aa1,mm1-1,gg1);
     data2 = new Date(aa2,mm2-1,gg2);
     if (data1.getTime()<=data2.getTime()) return true;
     else return false;
}
