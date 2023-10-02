package org.xurpas.cerebro.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import org.xurpas.cerebro.constants.EditMode;
import org.xurpas.cerebro.constants.Message;
import org.xurpas.cerebro.interfaces.XBaseClass;

public class Cortex {
    Synapse _synapse;
    CachedRowSet _master;
    CachedRowSet _detail;
    //main modules class
    
    String _branchcd;
    String _destinat;
    String _mas_table;
    String _det_table;
    String _sq_master;
    String _sq_detail;
    String _reference;
    
    String [] _mas_list_val;
    String [] _mas_list_sql;
    String [] _mas_list_col;
    String [] _mas_list_cri;
    String [] _mas_list_tit;
    String [] _mas_list_pic;
    
    String [] _det_list_sql;
    String [] _det_list_col;
    String [] _det_list_cri;
    String [] _det_list_tit;
    String [] _det_list_pic;
    
    String [] _mas_ref_fld;
    String [] _det_ref_fld;
    
    int _edit_mode;
    boolean _rec_exist;
    boolean _init_tran;
    boolean _verify_no;
    boolean _showmsg;
    int _ctr;
    
    String _message;
    XBaseClass _event;
    
    public Cortex(Synapse foValue, XBaseClass foEvent){
        _synapse = foValue;
        _event = foEvent;
    }
    
    public void setBranch(String foValue){
        _branchcd = foValue;
    }
    
    public String getBranch(){
        return _branchcd;
    }
    
    public void setDestination(String foValue){
        _destinat = foValue;
    }
    
    public String getDestination(){
        return _destinat;
    }
    
    public void setReference(String fsValue){
        _reference = fsValue;
    }
    
    public void setMasterTable(String fsValue){
        _mas_table = fsValue;
    }
    
    public String getMasterTable(){
        return _mas_table;
    }
    
    public void setDetailTable(String fsValue){
        _det_table = fsValue;
    }
    
    public String getDetailTable(){
        return _det_table;
    }
    
    public void setMasterQuery(String fsValue){
        _sq_master = fsValue;
    }
    
    public String getMasterQuery(){
        return _sq_master;
    }
    
    public void setDetailQuery(String fsValue){
        _sq_detail = fsValue;
    }
    
    public String getDetailQuery(){
        return _sq_detail;
    }
    
    public void setBrowseQuery(int fnIndex, String fsValue){
        _mas_list_sql[fnIndex] = fsValue;
    }
    
    public String getBrowseQuery(int fnIndex){
        return _mas_list_sql[fnIndex];
    }
    
    public void setBrowseColumn(int fnIndex, String fsValue){
        _mas_list_col[fnIndex] = fsValue;
    }
    
    public String getBrowseColumn(int fnIndex){
        return _mas_list_col[fnIndex];
    }
    
    public void setBrowseCriteria(int fnIndex, String fsValue){
        _mas_list_cri[fnIndex] = fsValue;
    }
    
    public String getBrowseCriteria(int fnIndex){
        return _mas_list_cri[fnIndex];
    }
    
    public void setBrowseTitle(int fnIndex, String fsValue){
        _mas_list_tit[fnIndex] = fsValue;
    }
    
    public String getBrowseTitle(int fnIndex){
        return _mas_list_tit[fnIndex];
    }
    
    public void setBrowseFormat(int fnIndex, String fsValue){
        _mas_list_pic[fnIndex] = fsValue;
    }
    
    public String getBrowseFormat(int fnIndex){
        return _mas_list_pic[fnIndex];
    }
    
    public void setDetailBrowseQuery(int fnIndex, String fsValue){
        _det_list_sql[fnIndex] = fsValue;
    }
    
    public String getDetailBrowseQuery(int fnIndex){
        return _det_list_sql[fnIndex];
    }
    
    public void setDetailBrowseColumn(int fnIndex, String fsValue){
        _det_list_col[fnIndex] = fsValue;
    }
    
    public String getDetailBrowseColumn(int fnIndex){
        return _det_list_col[fnIndex];
    }
    
    public void setDetailBrowseCriteria(int fnIndex, String fsValue){
        _det_list_cri[fnIndex] = fsValue;
    }
    
    public String getDetailBrowseCriteria(int fnIndex){
        return _det_list_cri[fnIndex];
    }
    
    public void setDetailBrowseTitle(int fnIndex, String fsValue){
        _det_list_tit[fnIndex] = fsValue;
    }
    
    public String getDetailBrowseTitle(int fnIndex){
        return _det_list_tit[fnIndex];
    }
    
    public void setDetailBrowseFormat(int fnIndex, String fsValue){
        _det_list_pic[fnIndex] = fsValue;
    }
    
    public String getDetailBrowseFormat(int fnIndex){
        return _det_list_pic[fnIndex];
    }
    
    public void setMessage(String fsValue){
        _message = fsValue;
    }
    
    public String getMessage(){
        return _message;
    }
    
    public void setDisplayConfirmation(boolean foValue){
        _showmsg = foValue;
    }
    
    public int getItemCount(){
        try {
            _detail.last();
            return _detail.getRow();
        } catch (SQLException e) {
            e.printStackTrace();
            setMessage(e.getMessage());
            return -1;
        }
    }
    
    public void setDetail(int fnRow, Object foIndex, Object foValue){
        if (!_init_tran) return;
        if (fnRow > getItemCount() - 1) return; 
        
        try {
            _detail.absolute(fnRow + 1);
            
            if (foIndex instanceof Number){
                if ((int) foIndex == 0 || _det_list_sql[(int) foIndex] == null){
                    _detail.updateObject((int) foIndex + 1 , foValue);
                    _detail.updateRow();
                } else {
                    getDetCode(fnRow, (int) foIndex, (String) foValue, false);
                }
            } else {
                _detail.updateObject((String) foIndex, foValue);
                _detail.updateRow();
            }
            
            _event.DetailRetreive(fnRow);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }
    
    public Object getDetail(int fnRow, Object foIndex){
        if (!_init_tran) return null;
        if (fnRow > getItemCount() - 1) return null; 
        
        try {
            _detail.absolute(fnRow + 1);
            
            if (foIndex instanceof Number){
                if ((int) foIndex == 0 || _det_list_sql[(int) foIndex] == null){
                    return _detail.getObject((int) foIndex + 1 );
                } else {
                    return _mas_list_val[(int) foIndex];
                }
            } else {
                
                return _master.getObject((String) foIndex);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void setMaster(Object foIndex, Object foValue){
        if (!_init_tran) return;
        
        try {
            _master.last();
            if (_master.getRow() == 0) return;

            _master.first();
            if (foIndex instanceof Number){
                if ((int) foIndex == 0 || _mas_list_sql[(int) foIndex] == null){
                    _master.updateObject((int) foIndex + 1 , foValue);
                    _master.updateRow();
                } else {
                    getMasCode((int) foIndex, (String) foValue, false);
                }
            } else {
                _master.updateObject((String) foIndex, foValue);
                _master.updateRow();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }
    
    public Object getMaster(Object foIndex){
        if (!_init_tran) return null;
        
        try {
            _master.last();
            if (_master.getRow() == 0) return null;

            _master.first();
            
            if (foIndex instanceof Number){
                if ((int) foIndex == 0 || _mas_list_sql[(int) foIndex] == null){
                    return _master.getObject((int) foIndex + 1 );
                } else {
                    return _mas_list_val[(int) foIndex];
                }
            } else {
                
                return _master.getObject((String) foIndex);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean InitTransaction(){
        if (_synapse == null) {
            setMessage("Main application is not set.");
            return false;
        }
        
        if (_event == null){
            setMessage("Event listener is not set.");
            return false;
        }
        
        if (_branchcd.isEmpty()) _branchcd = (String) _synapse.getBranchConfig("sBranchCd");
        
        if (_mas_table.isEmpty() || _det_table.isEmpty() || _sq_master.isEmpty() || _sq_detail.isEmpty() || _reference.isEmpty()){
            setMessage("Unset master-detail info detected.");
            return false;
        }
        
        String lasRefField [] = _reference.split("\\|");
        if (lasRefField.length != 2) {
            setMessage("Invalid reference field detected.");
            return false;
        }
        
        _mas_ref_fld = lasRefField[0].split("»");
        _det_ref_fld = lasRefField[1].split("»");
        
        if (_mas_ref_fld.length != _det_ref_fld.length){
            setMessage("Reference field discrepancy detected.");
            return false;
        }
          
        try {
            if (!clearMaster()) return false;
            
            _mas_list_val = new String [_master.getMetaData().getColumnCount()];
            _mas_list_sql = new String [_master.getMetaData().getColumnCount()];
            _mas_list_col = new String [_master.getMetaData().getColumnCount()];
            _mas_list_tit = new String [_master.getMetaData().getColumnCount()];
            _mas_list_pic = new String [_master.getMetaData().getColumnCount()];
            _mas_list_cri = new String [_master.getMetaData().getColumnCount()];
            
            if (!clearDetail()) return false;
            _det_list_sql = new String [_detail.getMetaData().getColumnCount()];
            _det_list_col = new String [_detail.getMetaData().getColumnCount()];
            _det_list_tit = new String [_detail.getMetaData().getColumnCount()];
            _det_list_pic = new String [_detail.getMetaData().getColumnCount()];
            _det_list_cri = new String [_detail.getMetaData().getColumnCount()];
        } catch (SQLException e) {
            e.printStackTrace();
            setMessage(e.getMessage());
            return false;
        }
        
        _init_tran = true;
        _edit_mode = EditMode.READY;
        
        return true;
    }
    
    public boolean NewTransaction(){
        if (!_init_tran) {
            setMessage("Class is not initialized.");
            return false;
        }
        
        System.out.println("Adding new transaction.");
        
        try {
            clearMaster();
            for (int lnCtr = 0; lnCtr <= _mas_list_val.length -1; lnCtr++){
                _mas_list_val[lnCtr] = "";
            }

            clearDetail();
        } catch (SQLException ex) {
            ex.printStackTrace();
            setMessage(ex.getMessage());
            return false;
        }

        _edit_mode = EditMode.ADDNEW;
        _event.InitMaster();
        _event.InitDetail(0);
        System.out.println("Transaction added.");
        
        return true;
    }
    
    public boolean SaveTransaction(){
        if (!_init_tran) {
            setMessage("Class is not initialized.");
            return false;
        }
        
        if (_showmsg){
            if (_edit_mode != EditMode.READY){
                setMessage("Invalid edit mode detected.");
                return false;
            }
            
            _event.DisplayConfirmation(Message.Type.YESNO, "Are you sure you want to save this transaction?");
            if (!System.getProperty("system.confirmation.value").equals("1")) {
                setMessage("Saving aborted.");
                return false;
            }
            
            System.out.println("Saving transaction.");
//            TODO
//            if (!hasRights2Save()){
//                setMessage("Transaction was not saved.");
//                return false;
//            }
        }
        
        if (isRefEmpty()){
            setMessage("Reference field cannot contain empty value.\n\nTransaction was not saved.");
            return false;
        }
        
        if (_showmsg) _synapse.beginTrans();
    
        if (!_event.WillSave()){
            if (_showmsg) _synapse.rollbackTrans();
            setMessage("Saving aborted.");
            return false;
        }
        
        try {
            _master.first();
            _master.updateString("sModified", (String) _synapse.getUserInfo("sUserIDxx"));
            _master.updateRow();
            
            //the user saved the transaction on the child class
            if (_event.Save()){
                //save other information on the child class
                if (!_event.SaveOthers()){
                    if (_showmsg) _synapse.rollbackTrans();
                    return false;
                }
                
                if (_showmsg) _synapse.commitTrans();
                _event.SaveComplete();
                
                setMessage("Transaction saved successfully.");
                _edit_mode = EditMode.READY;
                _rec_exist = true;
                
                return true;
            }
            
            //is the entry no needs to be verified?
            if (_verify_no) {
                _master.updateInt("nEntryNox", getItemCount());
                _master.updateRow();
            }
            
            //tables must be re-opened to include the actual fields only
            String lsMasSQL = "SELECT * FROM " + _mas_table;
            String lsDetSQL = "SELECT * FROM " + _det_table + " ORDER BY nEntryNox";
            
            ResultSet loRS;
            CachedRowSet loMaster;
            CachedRowSet loDetail;
            RowSetFactory factory = RowSetProvider.newFactory();
            
            if (_edit_mode == EditMode.ADDNEW){
                lsMasSQL = MiscUtil.addCondition(lsMasSQL, "0=1");
                lsDetSQL = MiscUtil.addCondition(lsDetSQL, "0=1");
            
                loRS = _synapse.executeQuery(lsMasSQL);
                loMaster = factory.createCachedRowSet();
                loMaster.populate(loRS);
                MiscUtil.close(loRS);
                
                loRS = _synapse.executeQuery(lsDetSQL);
                loDetail = factory.createCachedRowSet();
                loDetail.populate(loRS);
                MiscUtil.close(loRS);
                
                if (!addNewTransaction(loMaster, loDetail)){
                    if (_showmsg) _synapse.rollbackTrans();
                    return false;
                }
            } else {
                String lsField;
                int lnCol;
                
                for (_ctr = 0; _ctr <= _det_ref_fld.length -1; _ctr++){
                    lsField = _det_ref_fld[_ctr];
                    lnCol = lsField.indexOf(".");
                    if (lnCol > 0) lsField = lsField.substring(lnCol + 1);
                    
                    //to be continued
                }
                //to be continued
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (_showmsg) _synapse.rollbackTrans();
            setMessage(e.getMessage());
            return false;
        }
        
        if (_showmsg) _synapse.commitTrans();
        
        return true;
    }
    
    public boolean OpenTransaction(String fsValue){
        if (!_init_tran) {
            setMessage("Class is not initialized.");
            return false;
        }
        
        String lasReference[] = fsValue.split("»");
        if (lasReference.length != _mas_ref_fld.length){
            setMessage("Invalid references to open transaction.");
            return false;
        }
        
        System.out.println("Opening transaction.");

        try {
            String lsSQL = _sq_master;
            for (_ctr = 0; _ctr <= lasReference.length -1; _ctr++){
                if (!lasReference[_ctr].isEmpty()){
                    lsSQL = MiscUtil.addCondition(lsSQL, _mas_ref_fld[_ctr] + " = " + SQLUtil.toSQL(lasReference[_ctr]));
                }
            }
            
            ResultSet loRS;
            RowSetFactory factory = RowSetProvider.newFactory();
            
            loRS = _synapse.executeQuery(lsSQL);
            _master = factory.createCachedRowSet();
            _master.populate(loRS);
            MiscUtil.close(loRS);
            
            lsSQL = _sq_detail;
            for (_ctr = 0; _ctr <= lasReference.length -1; _ctr++){
                if (!lasReference[_ctr].isEmpty()){
                    lsSQL = MiscUtil.addCondition(lsSQL, _det_ref_fld[_ctr] + " = " + SQLUtil.toSQL(lasReference[_ctr]));
                }
            }
            
            loRS = _synapse.executeQuery(lsSQL);
            _detail = factory.createCachedRowSet();
            _detail.populate(loRS);
            MiscUtil.close(loRS);
            
            if (getItemCount() <= 0){
                _detail.last();
                _detail.moveToInsertRow();
                MiscUtil.initRowSet(_detail);
                _detail.insertRow();
                _detail.moveToCurrentRow();
                
                _event.InitDetail(0);
            }
            
            //verify if there is a record retreived
            _master.last();
            if (_master.getRow() == 0){
                setMessage("No record pass the given criteria. No transaction opened.");
                return false;
            }
            
            loadDescription();
            
            if (_verify_no){
                if (getItemCount() != _master.getInt("nEntryNox")){
                    setMessage("Transaction discrepancy detected.");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            setMessage(e.getMessage());
            return false;
        }
        
        _rec_exist = true;
        _edit_mode = EditMode.READY;
        
        _event.LoadOthers();
        System.out.println("Transaction was opened successfully.");
        
        return true;
    }
    
    private void loadDescription() throws SQLException{
        for (_ctr = 1; _ctr <= _master.getMetaData().getColumnCount(); _ctr++){
            if (!_mas_list_sql[_ctr].isEmpty()){
                if (_master.getObject(_ctr) != null){
                    getMasDesc(_ctr-1, _master.getString(_ctr));
                }
            }
        }
    }
    
    private void getMasCode(int fnIndex, String fsValue, boolean fbSearch){
        //TODO
        //ito yung ginagamit pag may browse para sa column
    }
    
    private void getDetCode(int fnRow, int fnIndex, String fsValue, boolean fbSearch){
        //TODO
        //ito yung ginagamit pag may browse para sa column
    }
    
    private void getMasDesc(int lnIndex, String lsValue) throws SQLException{
        if (lsValue.isEmpty()) return;
        
        if (_mas_list_cri[lnIndex].isEmpty()){
            if (_mas_list_col[lnIndex].isEmpty()){
                return;
            } else {
                _mas_list_cri[lnIndex] = _mas_list_col[lnIndex];
            }
        }
        
        _mas_list_val[lnIndex] = "";
        
        String lasRef [] = _mas_list_cri[lnIndex].split("»");
        
        String lsSQL = _mas_list_sql[lnIndex];
        lsSQL = MiscUtil.addCondition(lsSQL, lasRef[0] + "= " + SQLUtil.toSQL(lsValue));
        
        ResultSet loRS = _synapse.executeQuery(lsSQL);
        
        if (loRS.next()) _mas_list_val[lnIndex] = loRS.getString(1);
    }
    
    private boolean addNewTransaction(CachedRowSet loMaster, CachedRowSet loDetail) throws SQLException{
        String lsSQL = "";
        String lsField;
        String lsDestinat = "";
        int lnField;
        
        for (_ctr = 1; _ctr <= loMaster.getMetaData().getColumnCount()-3; _ctr++){
            lsField = loMaster.getMetaData().getColumnName(_ctr);
            if (lsField.equals("sDestinat")) lsDestinat = _master.getString("sDestinat");
            
            //check if field exist in table and in class
            for (lnField = 1; lnField <= _master.getMetaData().getColumnCount(); lnField++){
                if (_master.getMetaData().getColumnName(lnField).equalsIgnoreCase(lsField)){
                    lsSQL += ", " + lsField + " = " + SQLUtil.toSQL(_master.getObject(lsField));
                }
            }
        }
        
        if (_destinat.isEmpty()) lsDestinat = _destinat;
        
        lsSQL = "INSERT INTO " + _mas_table + " SET" +
                lsSQL.substring(2) +
                ", sModified = " + SQLUtil.toSQL((String) _synapse.getUserInfo("sUserIDxx")) +
                ", dModified = " + SQLUtil.toSQL(_synapse.getServerDate());
        
        if (_synapse.executeUpdate(lsSQL, _mas_table, _branchcd, lsDestinat) == 0) {
            setMessage("Unable to Add Transaction Master.");
            return false;
        }
        
        _detail.beforeFirst();
        while (_detail.next()){
            lsSQL = "";
            lsField = loDetail.getMetaData().getColumnName(0);
            if (_detail.getString(lsField).isEmpty()) break;
            
            for (_ctr = 1; _ctr <= loDetail.getMetaData().getColumnCount()-2; _ctr++){
                lsField = loDetail.getMetaData().getColumnName(_ctr);
            
                //check if field exist in table and in class
                for (lnField = 1; lnField <= _detail.getMetaData().getColumnCount(); lnField++){
                    if (_detail.getMetaData().getColumnName(lnField).equalsIgnoreCase(lsField)){
                        lsSQL += ", " + lsField + " = " + SQLUtil.toSQL(_master.getObject(lsField));
                    }
                }
            }
            
            lsSQL = "INSERT INTO " + _det_table + " SET" +
                    lsSQL.substring(2) +
                    ", dModified = " + SQLUtil.toSQL(_synapse.getServerDate());
            
            
            if (_synapse.executeUpdate(lsSQL, _det_table, _branchcd, lsDestinat) == 0) {
                setMessage("Unable to Add Transaction Detail.");
                return false;
            }
        }
        
        return true;
    }
    
    private boolean isRefEmpty(){
        String lsField;
        String lnCol;
        
        boolean lbResult = true;
        for (_ctr = 0; _ctr <= _mas_ref_fld.length -1; _ctr++){
            lsField = _det_ref_fld[_ctr];
//            lnCol = InStr(lsField, ".")
//            If lnCol > 0 Then lsField = Mid(lsField, lnCol + 1)
//            If p_oMaster(lsField) = Empty Then Exit Function

        }
        
        return false;
    }
    
    private boolean clearMaster() throws SQLException{
        String lsSQL;
        ResultSet loRS;

        RowSetFactory factory = RowSetProvider.newFactory();

        //create empty master record
        lsSQL = MiscUtil.addCondition(_sq_master, "0=1");
        loRS = _synapse.executeQuery(lsSQL);
        _master = factory.createCachedRowSet();
        _master.populate(loRS);
        MiscUtil.close(loRS);

        _master.last();
        _master.moveToInsertRow();
        MiscUtil.initRowSet(_master);
        _master.insertRow();
        _master.moveToCurrentRow();
        
        return true;
    }
    
    private boolean clearDetail() throws SQLException{
        String lsSQL;
        ResultSet loRS;

        RowSetFactory factory = RowSetProvider.newFactory();

        //create empty master record
        lsSQL = MiscUtil.addCondition(_sq_detail, "0=1");
        loRS = _synapse.executeQuery(lsSQL);
        _detail = factory.createCachedRowSet();
        _detail.populate(loRS);
        MiscUtil.close(loRS);

        _detail.last();
        _detail.moveToInsertRow();
        MiscUtil.initRowSet(_detail);
        _detail.insertRow();
        _detail.moveToCurrentRow();
        
        return true;
    }
}
