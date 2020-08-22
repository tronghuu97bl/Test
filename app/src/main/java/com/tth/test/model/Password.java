package com.tth.test.model;

import java.io.Serializable;

public class Password implements Serializable {
   private String password;
   public Password(){

   }
   public Password(String password){
       this.password = password;
   }
    public Password(int i, String password){
        this.password = password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return password;
    }
}
