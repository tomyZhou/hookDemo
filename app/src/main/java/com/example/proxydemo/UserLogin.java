package com.example.proxydemo;

public class UserLogin implements ILogin {

    @Override
    public void userLogin() {
        System.out.println("user Login");
    }

    @Override
    public void userRegister() {
        System.out.println("user Register");
    }
}
