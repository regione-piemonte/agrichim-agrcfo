package it.csi.cuneo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import it.csi.wso2.apiman.oauth2.helper.extra.cxf.CxfImpl;

public class CxfImplAgrcFO extends CxfImpl
{
  private String xAuthorization;
  
  public CxfImplAgrcFO(String xAuthorization) {
    this.xAuthorization = xAuthorization;
   }
  
  public void iniectTokenFirst(Object port, String token)
  {
    BindingProvider bp = (BindingProvider)port;
    Map<String, Object> map = bp.getRequestContext();
    Map<String, List<String>> hs = new HashMap<String, List<String>>();
    hs.put("Authorization", Arrays.asList(new String[] {
        (new StringBuilder()).append("Bearer ").append(token).toString()
    }));
    // AGGIUNTA  PER PROPAGARE CREDENZIALI JAAS
    hs.put("X-Authorization", Arrays.asList(new String[] {
        (new StringBuilder()).append("Basic ").append(xAuthorization).toString()
    }));
    
    map.put("javax.xml.ws.http.request.headers", hs);
  }
  
  
}
