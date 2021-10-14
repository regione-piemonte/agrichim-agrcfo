<%@ page errorPage="/jsp/view/errorePopup.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/comunePOP.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>

<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="beanProvincia"
     scope="application"
     class="it.csi.agrc.BeanProvincia">
</jsp:useBean>

<jsp:useBean
     id="comuni"
     scope="page"
     class="it.csi.agrc.Comuni">
<%
  comuni.setDataSource(dataSource);
  comuni.setAut(aut);
%>
</jsp:useBean>

<%
  String istatSearch = request.getParameter("istatSearch");
  String idProvinciaSearch = request.getParameter("idProvinciaSearch");
  //CuneoLogger.debug(this,"\ncomunePOP.jsp - istatSearch='"+istatSearch+"'");
  //CuneoLogger.debug(this,"comunePOP.jsp - idProvinciaSearch(1)='"+idProvinciaSearch+"'");
  if ((null == idProvinciaSearch || !Utili.isNumber(idProvinciaSearch)) && null != istatSearch )
    idProvinciaSearch = istatSearch.substring(0,3);
  //CuneoLogger.debug(this,"comunePOP.jsp - idProvinciaSearch(2)='"+idProvinciaSearch+"'");

  String idProvinciaCurrent;
  String idProv[],siglaProv[];
  idProv=beanProvincia.getIdProvincia();
  siglaProv=beanProvincia.getSiglaProvincia();
  for(int i=0;i<idProv.length;i++)
  {
    templ.set("provincia.idProvincia", idProv[i]);
    templ.set("provincia.siglaProvincia", siglaProv[i]);
    if ( idProv[i].equals(idProvinciaSearch) )
    {
      //CuneoLogger.debug(this,"comunePOP.jsp - idProvinciaCurrent='"+idProvinciaCurrent+"' "+p.getSiglaProvincia());
      templ.set("provincia.selected", "selected");
    }
  }

  if ( null != idProvinciaSearch && !"".equals(idProvinciaSearch) && Utili.isNumber(idProvinciaSearch)) {
      comuni.fill(idProvinciaSearch);
      int size=comuni.size();
      Comune c;
      String istatCurrent;
      for (int ii=0;ii<size;ii++) {
        c = comuni.get(ii);
        istatCurrent = c.getCodiceIstat();
        templ.set("comune.codiceIstat", istatCurrent );
        templ.set("comune.descrizione", c.getDescrizione());
        if ( istatCurrent.equals(istatSearch) )
          templ.set("comune.selected", "selected");
      }
    }
%>

<%= templ.text() %>

