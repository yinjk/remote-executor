package com.inori.executor.service.executor;

import java.lang.reflect.Method;

public class JavaClassExecutor {


    public String execute(byte[] classByte) throws ExecuteException {
//        FutureTask<String> futureTask = new FutureTask<>(() ->{
//            HackSystem.clearBuffer();
//            //修改字节码
//            ClassModifier cm = new ClassModifier(classByte);
//            //替换系统的System为自定义的类
//            byte[] modifyBytes = cm.modifyUtf8Constant("java/lang/System", "com/inori/executor/service/executor/HackSystem");
//            //每次都重新实现了类加载器实例，所以可以重复的加载测试类
//            HotSwapClassLoader loader = new HotSwapClassLoader();
//            //加载，通过这个方法，避开了双亲委托模式
//            Class clz = loader.loadBytes(modifyBytes);
//
//            try {
//                Method method = clz.getMethod("main", String[].class);
//                Object args = new String[]{};
//                method.invoke(null, args);
//            } catch (Throwable e) {
////                return new ExecuteException(e);
//                throw new ExecuteException(e);
//            }
//            return HackSystem.getBufferString();
//        });
//        Thread thread = new Thread(futureTask);
//        thread.start();
//        String value = null;
//        try {
//            value = futureTask.get();
//        } catch (Exception e) {
//            thread.stop();
//            throw new ExecuteException(e);
//        }
//        return value;

        HackSystem.clearBuffer();
        //修改字节码
        ClassModifier cm = new ClassModifier(classByte);
        //替换系统的System为自定义的类
        byte[] modifyBytes = cm.modifyUtf8Constant("java/lang/System", "com/inori/executor/service/executor/HackSystem");
        //每次都重新实现了类加载器实例，所以可以重复的加载测试类
        HotSwapClassLoader loader = new HotSwapClassLoader();
        //加载，通过这个方法，避开了双亲委托模式
        Class clz = loader.loadBytes(modifyBytes);

        try {
            Method method = clz.getMethod("main", String[].class);
            Object args = new String[]{};
            method.invoke(null, args);
        } catch (Throwable e) {
            throw new ExecuteException(e);
        }
        return HackSystem.getBufferString();
    }

}
