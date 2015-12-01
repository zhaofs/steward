package com.yihui.common.dubbo;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
public class DubboSpringApplication implements DisposableBean {

    static ConfigurableApplicationContext context;
    private static volatile boolean running = true;
    
	public static ConfigurableApplicationContext getContext() {
        return context;
    }
    
	
	@Override
	public void destroy() throws Exception {
        synchronized (DubboSpringApplication.class) {
            running = false;
            DubboSpringApplication.class.notify();
        }
		
	}
	
	public static void main(String[] args) throws Exception {
		context=SpringApplication.run(DubboSpringApplication.class, args);
        synchronized (DubboSpringApplication.class) {
            while (running) {
                try {
                	DubboSpringApplication.class.wait();
                } catch (Throwable e) {
                }
            }
        }
	}
}
