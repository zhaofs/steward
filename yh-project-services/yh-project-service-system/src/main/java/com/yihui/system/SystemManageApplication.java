package com.yihui.system;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:/dubbo/*.xml")
public class SystemManageApplication implements DisposableBean {

    public static ConfigurableApplicationContext context;
    private static volatile boolean running = true;

	@Override
	public void destroy() throws Exception {
        synchronized (SystemManageApplication.class) {
            running = false;
            SystemManageApplication.class.notify();
        }

	}

	public static void main(String[] args) throws Exception {
		context=SpringApplication.run(SystemManageApplication.class, args);
        synchronized (SystemManageApplication.class) {
            while (running) {
                try {
                	SystemManageApplication.class.wait();
                } catch (Throwable e) {
                }
            }
        }
	}
}
