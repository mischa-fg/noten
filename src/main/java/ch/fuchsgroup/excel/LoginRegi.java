/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.excel;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author misch
 */
public class LoginRegi {
    public String registrieren(User u){
        String key = key();
        Keyuser ku = new Keyuser();
        String pwhashed = BCrypt.hashpw(u.getPasswort(), BCrypt.gensalt());
        String keyhashed = BCrypt.hashpw(key,BCrypt.gensalt());
        ku.setApikey(keyhashed);
        ku.setPasswort(pwhashed);
        ku.setUsername(u.getUsername());
        EnitiyManagerExcel eme = new EnitiyManagerExcel();
        boolean usernameFrei = eme.usernameFrei(ku);
        if(usernameFrei){
            boolean b = eme.keyUserHinzufuegen(ku);
            if(b){
                return key;
            }else{
                return null;
            }
            
        }else{
            return null;
        }
    }
    public String key(){
    //Key erzeugen
        int n = 20;
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz";
  
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);
  
        for (int i = 0; i < n; i++) {
  
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                = (int)(AlphaNumericString.length()
                        * Math.random());
  
            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                          .charAt(index));
        }
        return sb.toString();
    }
    
    public String login(User u){
        EnitiyManagerExcel eme = new EnitiyManagerExcel();
        boolean loginKor = eme.userLogin(u);
        if(loginKor){
            return u.getUsername();
        }else{
            return null;
        }
        //return loginKor;
        
    }
}
