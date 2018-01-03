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

/** 
 * @author hongtao 
 * @version  v 0.1 , 2018年1月3日 下午2:27:46
 * @since  JDK 1.8     
 */
public class RmqTemplateEnhancer extends AbstractRmqMethodEnhancer {

  @Override
  protected String buildMethodBody(String methodOriginalName) {
    return String.format("" //
        + "{" //
        + "  long begin = System.currentTimeMillis();" //
        + "  com.infi.rabbitmq.intercepter.chain.RmqIntercepterChain intercepter = com.infi.rabbitmq.intercepter.chain.RmqIntercepterChainFactory.create(\"PRODUCER\");" //
        + "  com.infi.rabbitmq.intercepter.model.RmqContext context = com.infi.rabbitmq.intercepter.model.RmqContext.builder().exchange($2).routingKey($3).build();"//
        + "  intercepter.applyPreHandle($4, context);" //
        + "  Throwable throwable = null;" //
        + "  try {" //
        + "    %s($$);" //
        + "  } catch (Throwable e) {" //
        + "    throwable = e;" //
        + "    throw e;"//
        + "  } finally {" //
        + "    long timeUsed = System.currentTimeMillis() - begin;"//
        + "    intercepter.applyAfterHandle($4, context, timeUsed, throwable);"//
        + "  }" //
        + "}", methodOriginalName);
  }

}
