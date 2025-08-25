package com.csc.demo.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Enumeration;

public class PFXAliasReader {
    public static void main(String[] args) {
        String pfxFilePath = "D:\\CSC\\CSC-ESIGN\\Test-Class3Individual2022\\emusig2.pfx";
        String password = "1";       

        try (InputStream pfxInputStream = new FileInputStream(pfxFilePath)) {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(pfxInputStream, password.toCharArray());

            Enumeration<String> aliases = keyStore.aliases();

            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                System.out.println("Alias: " + alias);

                if (keyStore.isKeyEntry(alias)) {
                    System.out.println("Alias '" + alias + "' is associated with a private key.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}