package com.example.ConnectionService.auth;

public class AuthContextHolder {

    private static final   ThreadLocal<Long> currentUserId = new ThreadLocal<>() ;

    public  static  Long getCurrentUserId()
    {
         return  currentUserId.get() ;
    }

    // we are making these method are package private
    static  void  setCurrentUserId(Long userId)
    {
        currentUserId.set(userId);
    }

    static void  clear ()
    {
        currentUserId.remove();
    }
}
