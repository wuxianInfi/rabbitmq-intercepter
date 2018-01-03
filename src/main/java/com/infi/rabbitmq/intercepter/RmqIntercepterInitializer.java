/*
 * Copyright 2014-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.infi.rabbitmq.intercepter;


import com.infi.rabbitmq.intercepter.enums.RmqEnhanceMethod;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hongtao
 * @version v 0.1 , 2018年1月3日 下午2:30:24
 * @since JDK 1.8
 */
@Slf4j
public class RmqIntercepterInitializer {

  private final ClassPool classPool = ClassPool.getDefault();
  private volatile boolean initialized = false;

  private RmqIntercepterInitializer() {
    // Using multiple class loader for web environment.
    classPool.insertClassPath(new ClassClassPath(this.getClass()));
  }

  private static class InstanceHolder {
    private static final RmqIntercepterInitializer INSTANCE = new RmqIntercepterInitializer();
  }

  public static RmqIntercepterInitializer getInstance() {
    return InstanceHolder.INSTANCE;
  }

  /**
   * Normally initialize it at the entry of application.
   */
  public void init() {
    // Double checking.
    if (initialized) {
      return;
    }
    synchronized (RmqIntercepterInitializer.class) {
      if (initialized) {
        return;
      }
      long start = System.currentTimeMillis();
      try {
        for (RmqEnhanceMethod enhanceMethod : RmqEnhanceMethod.getEnhanceMethods()) {
          generateClass(enhanceMethod);
        }
        log.info("RmqIntercepterInitializer initialize successfully, timeCost = {} ms",
            System.currentTimeMillis() - start);
      } catch (Exception e) {
        log.info("RmqIntercepterInitializer initialize failed, timeCost =  {} ms",
            System.currentTimeMillis() - start, e);
      } finally {
        initialized = true;
      }
    }
  }

  private void generateClass(RmqEnhanceMethod enhanceMethod)
      throws NotFoundException, CannotCompileException {
    CtClass ctClass = classPool.getCtClass(enhanceMethod.getImplClass());
    for (CtMethod method : ctClass.getMethods()) {
      if (method.getName().equals(enhanceMethod.getMethodName()))
        enhanceMethod.getMethodEnhancer().generateMethod(ctClass, method);
    }
    ctClass.toClass(this.getClass().getClassLoader(), null);
  }
}
