
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
        CodeReader codeReader = new CodeReader(code);
        className = codeReader.getClassName(false);
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();
        try (BytesJavaFileManager bytesJavaFileManager = new BytesJavaFileManager(javaCompiler.getStandardFileManager(diagnosticCollector, null, null))) {
            List<JavaFileObject> javaFileObjectList = Collections.singletonList(new JavaSourceObject(className, code));

//            List<String> options = new ArrayList<>();
//            options.add("-classpath");
//            StringBuilder sb = new StringBuilder();
//            URLClassLoader urlClassLoader = (URLClassLoader) DynamicCompiler.class.getClassLoader();
//            for (URL url : urlClassLoader.getURLs()) {
//                System.out.println(url.getFile());
//                sb.append(url.getPath()).append(File.pathSeparator);
//            }
//            options.add(sb.toString());


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
//            Class<?> clazz = bytesJavaFileManager.getClassLoader(null).loadClass(className);
            return bytesJavaFileManager.getCompiledBytes();
        } catch (IOException e) {
            e.printStackTrace();
            throw new CompileException(e);
        }
    }
}
