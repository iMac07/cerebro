package org.xurpas.cerebro.interfaces;

import org.xurpas.cerebro.constants.Message;

public interface XBaseClass {
    boolean WillDelete();
    boolean Delete();
    boolean DeleteOthers();
    void DeleteComplete();
    
    boolean WillSave();
    boolean Save();
    boolean SaveOthers();
    void SaveComplete();
    
    boolean WillCancel();
    boolean Cancel();
    boolean CancelOthers();
    void CancelComplete();
    
    void LoadOthers();
    void InitMaster();
    void InitDetail(int ItemNo);
    
    void MasterRetreive(int Index);
    void DetailRetreive(int Index);
    
    void DisplayConfirmation(Message.Type foType, String fsMessage);
}