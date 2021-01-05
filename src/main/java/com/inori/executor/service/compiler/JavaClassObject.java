package com.inori.executor.service.compiler;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * JavaSourceObject
 *
 * @author inori
 * @date 2020/12/21
 */
public class JavaClassObject extends SimpleJavaFileObject {
    // 存放编译之后的class字节码
    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    public JavaClassObject(URI uri, Kind kind) {
        super(uri, kind);

    }

    /**
     * 获取编译之后的class字节码
     *
     * @return
     */
    public byte[] getBytes() {
        return byteArrayOutputStream.toByteArray();
    }


    /**
     * 重写父类方法
     * 该方法提供给编译器结果输出的OutputStream。
     * <p>
     * 编译器完成编译后，会将编译结果输出到该OutputStream中
     *
     * @return OutputStream
     */
    @Override
    public OutputStream openOutputStream() {
        return this.byteArrayOutputStream;
    }

}
