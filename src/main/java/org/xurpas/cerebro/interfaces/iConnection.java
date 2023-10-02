/**
 * @author Michael Cuison 2020.12.21
 */
package org.xurpas.cerebro.interfaces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;

public interface iConnection {
    public void setProperty(iProperty foValue);
    
    public iProperty getProperty();
    
    public void IsOnline(boolean fbValue);
    public boolean IsOnline();
    
    public Timestamp getServerDate();
    
    public boolean beginTrans();
    public boolean commitTrans();
    public boolean rollbackTrans();
    
    public Connection doConnect();
    public Connection getConnection();
    
    public ResultSet executeQuery(String fsValue);
    public long executeUpdate(String fsValue);
   
    public String getMessage();

}