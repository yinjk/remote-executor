package com.inori.executor.service.compiler;

import javafx.animation.PauseTransitionBuilder;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.security.SecureClassLoader;

/**
 * BytesJavaFileManager
 *
 * @author inori
 * @date 2020/12/21
 */
public class BytesJavaFileManager extends ForwardingJavaFileManager {


    private JavaClassObject output;

    /**
     * Creates a new instance of ForwardingJavaFileManager.
     *
     * @param fileManager delegate to this file manager
     */
    public BytesJavaFileManager(JavaFileManager fileManager) {
        super(fileManager);
    }

    @Override
    public JavaFileObject getJavaFileForInput(Location location, String className, JavaFileObject.Kind kind) throws IOException {
        return super.getJavaFileForInput(location, className, kind);
    }

    @Override
    public ClassLoader getClassLoader(Location location) {
        return new SecureClassLoader() {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                byte[] bytes = output.getBytes();
                return super.defineClass(name, bytes, 0, bytes.length);
            }
        };
    }

        /**
         * 获取编译之后的class字节码.
         *
         * @return class byte数组
         */
    public byte[] getCompiledBytes() {
        return output.getBytes();
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        this.output = new JavaClassObject(URI.create("string:///" + className.replaceAll("\\.", "/") + kind.extension), kind);
        return this.output;
    }

}
