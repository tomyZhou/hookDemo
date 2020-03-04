package com.example.proxydemo;

/**
 *  userLogin的静态代理对象
 */
public class UserLoginProxy implements ILogin {

    private UserLogin mLogin;

    public UserLoginProxy() {
        mLogin = new UserLogin();
    }

    @Override
    public void userLogin() {
        System.out.println("登录前。。。");
        mLogin.userLogin();
        System.out.println("登录后。。。");
    }

    @Override
    public void userRegister() {

    }
}
