package com.inori.executor.service.compiler;

import javax.tools.*;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * DynamicCompiler
 *
 * @author inori
 * @date 2020/12/21
 */
public class DynamicCompiler {
    public byte[] compile(String className, String code) throws CompileException {
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();
        try (BytesJavaFileManager bytesJavaFileManager = new BytesJavaFileManager(javaCompiler.getStandardFileManager(diagnosticCollector, null, null))) {
            List<JavaFileObject> javaFileObjectList = Collections.singletonList(new JavaSourceObject(className, code));
            boolean result = javaCompiler.getTask(null, bytesJavaFileManager, diagnosticCollector, null, null, javaFileObjectList).call();
            if (!result) { // 编译失败
                List list = diagnosticCollector.getDiagnostics();
                String error = "";
                for (Object object : list) {
                    Diagnostic d = (Diagnostic) object;
                    error = "Line " + d.getLineNumber() + ": " + d.getMessage(Locale.ENGLISH);
                }
                throw new CompileException(error);
            }
            Class<?> aClass = bytesJavaFileManager.getClassLoader(null).loadClass(className);
            System.out.println(aClass.getName());
            return bytesJavaFileManager.getCompiledBytes();
        } catch (IOException | ClassNotFoundException e) {
            throw new CompileException(e);
        }
    }
}
