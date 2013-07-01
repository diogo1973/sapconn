package pt.ulht.tfc.sapconn.domain;

import pt.ist.fenixframework.FenixFramework;

public class SapConnManager extends SapConnManager_Base {
    
    public static SapConnManager getInstance() {
      return FenixFramework.getRoot();
      }
}
