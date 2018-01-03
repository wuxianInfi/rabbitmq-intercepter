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
package com.infi.rabbitmq.intercepter.method;

import com.infi.rabbitmq.intercepter.RmqMethodEnhancer;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

/**
 * @author hongtao
 * @version v 0.1 , 2018年1月3日 下午2:26:31
 * @since JDK 1.8
 */
public abstract class AbstractRmqMethodEnhancer implements RmqMethodEnhancer {

  /**
   * Rename old method to name with format(XXX$original). Then duplicate the method with original
   * name for use as intercepter.
   */
  @Override
  public void generateMethod(CtClass ctClass, CtMethod methodOriginal)
      throws CannotCompileException {
    String methodNewName = methodOriginal.getName();
    String methodOriginalName = methodOriginal.getName() + "$original";
    methodOriginal.setName(methodOriginalName);
    CtMethod methodNew = CtNewMethod.copy(methodOriginal, methodNewName, ctClass, null);
    String methodBody = buildMethodBody(methodOriginalName);
    methodNew.setBody(methodBody);
    ctClass.addMethod(methodNew);
  }

  protected abstract String buildMethodBody(String methodOriginalName);

}
