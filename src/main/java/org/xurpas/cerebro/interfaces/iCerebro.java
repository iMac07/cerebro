/**
 * @author Michael Cuison 2020.12.21
 */
package org.xurpas.cerebro.interfaces;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;

public interface iCerebro {
    public void setConnection(iConnection foValue);
    public iConnection getConnection();
    
    public void setEncryption(iCrypt foValue);
    public iCrypt getEncryption();
    
    public Connection doConnect();
    
    public void setUserID(String fsValue);
    
    public String Encrypt(String fsValue);
    public String Decrypt(String fsValue);
    
    public void SystemDate(Date fdValue);
    public Date SystemDate();
        
    public Object getUserInfo(String fsValue);
    public Object getAppConfig(String fsValue);
    public Object getBranchConfig(String fsValue);
    public Object getSysConfig(String fsValue);
    public Timestamp getServerDate();
    
    public boolean load(String fsProdctID);
    
    public boolean lockUser();
    public boolean loginUser(String fsProdctID);
    public boolean logoutUser();
    public boolean unlockUser();
    
    public boolean beginTrans();
    public boolean commitTrans();
    public boolean rollbackTrans();
    public ResultSet executeQuery(String fsValue);
    public long executeUpdate(String fsValue, String fsTableNme, String fsBranchCd, String fsDestinat);
    public long executeUpdate(String fsValue);
    
    public boolean isUserAuthorized(XApproval foListener, int fnUserLevl);   
    public boolean isUserAuthorized(XApproval foListener, int fnUserLevl, int fnObjAcces);   
    
    public String getMessage();
}
