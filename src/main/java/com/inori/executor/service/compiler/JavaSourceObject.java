package com.inori.executor.service.compiler;

import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * JavaSourceObject
 *
 * @author inori
 * @date 2020/12/21
 */
public class JavaSourceObject extends SimpleJavaFileObject {
    // 存放java源码内容
    private String contents;

    public JavaSourceObject(String className, String contents) {
        super(URI.create("string:///" + className.replaceAll("\\.", "/") + Kind.SOURCE.extension), Kind.SOURCE);
        this.contents = contents;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return contents;
    }


}
