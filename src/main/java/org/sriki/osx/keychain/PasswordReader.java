package org.sriki.osx.keychain;

import com.mcdermottroe.apple.OSXKeychain;

public class PasswordReader {

    public String getPassword(String itemName, String accountName) throws Exception {
        if(!isOSX()){
            return "";
        }
        OSXKeychain keychain = OSXKeychain.getInstance();
        return keychain.findGenericPassword(itemName,accountName);
    }

    private boolean isOSX() {
        return System.getProperty("os.name").toLowerCase().contains("os x");
    }

}
