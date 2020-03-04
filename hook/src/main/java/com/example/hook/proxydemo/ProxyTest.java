package com.example.hook.proxydemo;


import java.io.FileOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import sun.misc.ProxyGenerator;

/**
 * 动态代理彻底弄明白原理 https://blog.csdn.net/qq_30993595/article/details/90796869
 */
public class ProxyTest {
    public static void main(String args[]) {

        /**
         * 使用静态代理
         */
        UserLoginProxy userLoginProxy = new UserLoginProxy();
        userLoginProxy.userLogin();
        System.out.println("===========这是分隔线==============");


        /**
         * 使用动态代理
         */
        try {
            ILogin proxy = (ILogin) loadProxy(new UserLogin());
            proxy.userLogin();
            proxy.userRegister();

            write();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 这里通过一个很骚的比喻来说明下：一个东厂太监（接口Class对象）有一家子财产，
     * 但是为了侍奉皇帝这一伟大事业，毅然决然的割了DD（没有构造方法），虽然实现了自己的理想，
     * 但是不能繁育下一代（不能构造器创建对象），也就没有后人继承自己的家业；但是好在华佗在世，
     * 江湖上有一个高人（Proxy），发明了一个克隆大法（getProxyClass），不仅克隆出了几乎和
     * 太监一样的下一代（新的Class），还拥有自己的小DD（构造方法），这样这个下一代就能继承
     * 太监的家产（类结构信息，其实是实现了该接口），同时还能娶妻生子，传给下一代（创建实例）
     *
     */

    /**
     * 通过Proxy.getProxyClass,
     * 再通过getConstructor得到构造函数，
     * 再用构造函数newInstance一个代理对象的方式，创建UserLogin的动态代理对象
     * <p>
     * 你给我的一个目标类，我还你一个代理类
     */
    public static Object loadProxy(final Object target) throws Exception {

        //通过目标类target的Class对象和类加载器创建代理类的Class对象
        Class<?> proxyClass = Proxy.getProxyClass(target.getClass().getClassLoader(), target.getClass().getInterfaces());


        //获得代理类的带有InvocationHandler参数的构造方法
        Constructor<?> proxyClassConstructor = proxyClass.getConstructor(InvocationHandler.class);

        //使用前面获得的带有InvocationHandler参数的构造方法创建一个代理对象。
        Object proxy = proxyClassConstructor.newInstance(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("执行" + method.getName() + "前日志...");
                //要执行的方法可能带有参数个数不定，指所以用数据来接收，里面用 Object... args
                Object result = method.invoke(target, args);
                System.out.println("执行" + method.getName() + "后日志...");
                return result;
            }
        });

        System.out.println("..." + (proxy instanceof Proxy));
        System.out.println("..." + proxyClass.getName());
        System.out.println("..." + proxyClass.getSuperclass().getName());
        System.out.println("..." + proxyClass.getSuperclass().getSimpleName());
        System.out.println("..." + proxyClass.getInterfaces()[0].getName());

        /**
         *
         * 从这五点是不是能知道点啥了：动态生成的代理对象的父类是Proxy，
         * 实现了ILogin接口；这也就是为什么能将代理对象强转成ILogin，
         * 从而调用其接口方法；也说明了为什么只能支持动态生成接口代理，
         * 不能动态生成class代理，因为最终生成的代理对象肯定会继承Proxy类，
         * 如果我们提供的类已经继承了其它类，那就不能继承Proxy类了，动态代理
         * 也就无从谈起了
         *
         * ————————————————
         *
         * JDK帮我们生成了这样一个class数据，它继承了Proxy类，
         * 添加了一个带InvocationHandler参数的构造方法，这样
         * 也就明白了为什么使用构造方法反射创建代理对象的时候
         * 传入了一个InvocationHandler参数，因为默认会调用到
         * Proxy类的构造方法，其参数正好是InvocationHandler，
         * 赋值给内部的成员变量h
         */


        return proxy;

    }

    /**
     * Proxy类还有个更简单的方法newProxyInstance，直接返回代理对象
     *
     * @param target
     * @return
     */
    public static Object loadProxy2(final Object target) {                                       //这个类可以实现了好多个接口
        Object proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("执行" + method.getName() + "方法前的处理...");
                Object result = method.invoke(target, args);
                System.out.println("执行" + method.getName() + "方法后的处理...");
                return result;
            }
        });
        return proxy;
    }

    /**
     * ProxyGenerator 这个类是rt.jar 里面的，sun.misc.ProxyGenerator
     *
     * android项目里面没有这个类，所以需要建立java library项目
     *
     * AbstractProcessor 编译时注解处理器也是rt.jar中的 javax.annotation.processing.AbstractProcessor里的，
     *
     * 所以也需要建立java library
     */
    public static void write() {
        byte[] classFile = ProxyGenerator.generateProxyClass("$Proxy0", UserLogin.class.getInterfaces());
        String path = "D:/AndroidStudioProjects/Hookdemo/app/src/main/java/com/example/proxydemo/UserLoginProxy.class";
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(classFile);
            fos.flush();
            System.out.println("代理类class文件写入成功");
        } catch (Exception e) {
            System.out.println("写文件错误");
        }
    }

}
