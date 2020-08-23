package com.tth.test.model;

import java.io.Serializable;

public class Password implements Serializable {
    private int passwordid;
   private String password;
   private String name;
   public Password(){

   }

    public Password(int passwordid, String password, String name){
       this.passwordid = passwordid;
        this.password = password;
        this.name = name;
    }
    public int getPasswordid(){
       return passwordid;
    }
    public void setPasswordid(int passwordid){
       this.passwordid = passwordid;
    }
    public void setName(String name){
       this.name = name;
    }
    public String getName(){
       return name;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return password;
    }
}
