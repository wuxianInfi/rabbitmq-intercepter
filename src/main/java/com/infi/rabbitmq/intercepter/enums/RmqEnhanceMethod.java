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
package com.infi.rabbitmq.intercepter.enums;

import java.util.List;

import com.google.common.collect.Lists;
import com.infi.rabbitmq.intercepter.RmqMethodEnhancer;
import com.infi.rabbitmq.intercepter.method.RmqListenerContainerEnhancer;
import com.infi.rabbitmq.intercepter.method.RmqTemplateEnhancer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author hongtao
 * @version v 0.1 , 2018年1月3日 下午2:22:10
 * @since JDK 1.8
 */
@RequiredArgsConstructor
@Getter
public enum RmqEnhanceMethod {

  // RMQ producer method
  RAMMIT_TEMPLATE_SEND("org.springframework.amqp.rabbit.core.RabbitTemplate", "doSend",
      new RmqTemplateEnhancer()), //
  // RMQ consumer method
  ABASTRACE_LISTENER_CONTAINER(
      "org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer",
      "executeListener", new RmqListenerContainerEnhancer());

  private final String implClass;
  private final String methodName;
  private final RmqMethodEnhancer methodEnhancer;

  public static List<RmqEnhanceMethod> getEnhanceMethods() {
    List<RmqEnhanceMethod> methods = Lists.newArrayList();
    methods.add(RAMMIT_TEMPLATE_SEND);
    methods.add(ABASTRACE_LISTENER_CONTAINER);
    return methods;
  }
}
