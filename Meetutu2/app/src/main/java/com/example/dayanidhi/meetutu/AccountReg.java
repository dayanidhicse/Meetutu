package com.example.dayanidhi.meetutu;

import java.io.Serializable;


public class AccountReg implements Serializable {
    public String First_name;
    public String Last_name;
    public String Email;
    public String Number;
    public String Password;
    public String skill;
    public String commants;
    public String rate;
    public String _id;


    public AccountReg(String first_name, String last_name, String email, String number, String password){
        this._id=number ;
        this.First_name=first_name;
        this.Last_name = last_name;
        this.Email=email;
        this.Number = number;
        this.Password = password;
    }
    public AccountReg(String skill,String number){
        this._id=number;
        this.skill=skill;

    }
    public AccountReg(String id,String commants,String rate){
        this._id=id;
        this.commants=commants;
        this.rate=rate;

    }




}
