package com.inori.executor.service.executor;

/**
 * HotSwapClassLoader
 *
 * @author inori
 * @date 2020/12/17
 */
public class HotSwapClassLoader extends ClassLoader {

    public HotSwapClassLoader() {
        super(HotSwapClassLoader.class.getClassLoader());
    }

    public Class loadBytes(byte[] bytes) {
        return defineClass(null, bytes, 0, bytes.length);
    }

}
