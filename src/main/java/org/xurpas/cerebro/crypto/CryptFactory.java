/**
 * @author Michael Cuison 2020.12.23
 */
package org.xurpas.cerebro.crypto;

import org.xurpas.cerebro.interfaces.iCrypt;

public class CryptFactory {
    public enum CrypType{
        AESCrypt,
        XCrypt
    }
    
    public static iCrypt make(CryptFactory.CrypType foType){
        switch (foType){
            case AESCrypt:
                return new MySQLAES();
            case XCrypt:
                return new GCrypt();
            default:
                return null;
        }
    }
}
