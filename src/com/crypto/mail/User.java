package com.crypto.mail;

import java.security.PublicKey;

public class User {

    public static String username;
    public static String password;

    public User(){
        String username = " ";
        String password = " ";
    }
    public User( String user, String pass){
        String username = user;
        String password = pass;
    }
}
