package com.inori.executor.service;

import com.inori.executor.service.compiler.CompileException;
import com.inori.executor.service.compiler.DynamicCompiler;
import com.inori.executor.service.executor.ExecuteException;
import com.inori.executor.service.executor.JavaClassExecutor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * ExecutorService
 *
 * @author inori
 * @date 2020/12/21
 */
@Service
public class ExecutorService implements InitializingBean {
    private DynamicCompiler compiler;
    private JavaClassExecutor executor;

    public String execute(String code) throws CompileException, ExecuteException {
        byte[] bytes = compiler.compile("Test", code);
        return executor.execute(bytes);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.executor = new JavaClassExecutor();
        this.compiler = new DynamicCompiler();
    }
}
