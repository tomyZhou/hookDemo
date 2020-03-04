package com.example.hookdemo;

import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class HookUtil {

    public static void hookOnClickListener(View view) {

        try {

            //得到View的getListenerInfo方法
            Method getListenerInfo = View.class.getDeclaredMethod("getListenerInfo");

            //修改getListenerInfo为可访问(View中的getListenerInfo不是public)
            getListenerInfo.setAccessible(true);
            Object listenerInfo = getListenerInfo.invoke(view);

            //得到原始的OnClickListener对象
            Class<?> listenerInfoClz = Class.forName("android.view.View$ListenerInfo");
            Field mOnClickListener = listenerInfoClz.getDeclaredField("mOnClickListener");
            mOnClickListener.setAccessible(true);
            View.OnClickListener originOnClickListener = (View.OnClickListener) mOnClickListener.get(listenerInfo);

            //代理原始类
            View.OnClickListener hookedOnClickListener = new OnClickListenerProxy(originOnClickListener);

//             或者用动态代理实现上面代码完成上面两步，直接生成代码类，需要多传一个Context参数
//            Object proxyOnClickListener = Proxy.newProxyInstance(context.getClass().getClassLoader(), new Class[]{View.OnClickListener.class}, new InvocationHandler() {
//                @Override
//                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                    Log.d("HookSetOnClickListener", "点击事件被hook到了");//加入自己的逻辑
//                    return method.invoke(onClickListenerInstance, args);//执行被代理的对象的逻辑
//                }
//            });

            mOnClickListener.set(listenerInfo, hookedOnClickListener);

        } catch (Exception e) {
            Log.d("LOGCAT", "hook clickListener failed!", e);
        }
    }
}
