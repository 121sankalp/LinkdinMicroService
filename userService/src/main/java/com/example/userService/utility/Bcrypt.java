package com.example.userService.utility;

import org.mindrot.jbcrypt.BCrypt;

public class Bcrypt {
    public  static  String hash(String s)
    {
        return BCrypt.hashpw(s, BCrypt.gensalt()) ;
    }

    public static Boolean match(String passwordText , String hashedPassword)
    {
        return BCrypt.checkpw(passwordText ,hashedPassword ) ;
    }
}
