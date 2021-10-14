<jsp:useBean
  id="beanParametriApplication"
  scope="application"
  class="it.csi.agrc.BeanParametri">
</jsp:useBean>

<%
    it.csi.agrc.Autenticazione aut =
         (it.csi.agrc.Autenticazione)session.getAttribute("aut");    
      //(it.csi.agrc.Autenticazione)session.getAttribute("aut");
    if (aut==null)
    {
      it.csi.cuneo.CuneoLogger.debug(this,"Sessione non valida");
      response.sendRedirect(beanParametriApplication.getUrlStartApplicationSP());
      return;
    }
    else
    {
      if (templ!=null)
      {
        templ.setStringProcessor(new it.csi.cuneo.HTMLStringProcessorCuneo());
        templ.bset("nomeUtente",aut.getUtente().getNome()+" "
                   + aut.getUtente().getCognome());

        // Se è vero, allora siamo in RuparPiemonte!
        if (aut.isPA())
        {
          templ.bset("rupar","_ruparpiemonte");
          templ.bset("nomeRete","RuparPiemonte");
        }
        else
          templ.bset("nomeRete","SistemaPiemonte");
      }
    }

%>