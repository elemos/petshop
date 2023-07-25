package br.com.metaway.petshop;

import jakarta.xml.bind.DatatypeConverter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    /**
     * Converte uma string s em um string c criptografada com md5
     */
    public static String md5(String s){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(s.getBytes());
            byte[] bytes = md.digest();
            String c = DatatypeConverter.printHexBinary(bytes).toUpperCase();
            return c;

        }catch (NoSuchAlgorithmException  e) {
            throw new RuntimeException(e);
        }
    }
}
