/**
 * Copyright 2006-2014 handu.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yihui.common.dubbo;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.alibaba.dubbo.container.Container;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * JavaConfigContainer. (SPI, Singleton, ThreadSafe)
 *
 * @author Jinkai.Ma
 */
public class SpringBootContainer implements Container {

    private static final Logger logger = LoggerFactory.getLogger(SpringBootContainer.class);

    public static final String SPRING_BOOT = "dubbo.spring.boot";

    public static final String DEFAULT_SPRING_BOOT = "com.yihui";

    static ConfigurableApplicationContext context;

    public static ConfigurableApplicationContext getContext() {
        return context;
    }

    public void start() {
        String configPath = ConfigUtils.getProperty(SPRING_BOOT);
        if (configPath == null || configPath.length() == 0) {
            configPath = DEFAULT_SPRING_BOOT;
        }
        context = new AnnotationConfigApplicationContext(configPath);
        context.start();
    }

    public void stop() {
        try {
            if (context != null) {
                context.stop();
                context.close();
                context = null;
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
    }

}
