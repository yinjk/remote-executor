package com.inori.executor.service.compiler;

public class CompileClassLoader extends ClassLoader {

//    private byte[] bytes;
    private JavaClassObject output;

    public CompileClassLoader(JavaClassObject output) {
        super(CompileClassLoader.class.getClassLoader());
//        this.bytes = bytes;
        this.output = output;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = output.getBytes();
        return super.defineClass(name, bytes, 0, bytes.length);
    }
}
