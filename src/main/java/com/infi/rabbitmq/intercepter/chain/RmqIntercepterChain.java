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
package com.infi.rabbitmq.intercepter.chain;

import org.springframework.amqp.core.Message;

import com.infi.rabbitmq.intercepter.model.RmqContext;

/**
 * @author hongtao
 * @version v 0.1 , 2018年1月3日 下午3:17:28
 * @since JDK 1.8
 */
public interface RmqIntercepterChain {

  /**
   * Apply pre-handle before produce/consume message
   * 
   * @param message message to be produced or consumed
   * @param context context contains exchange,routing key, queues...
   */
  public void applyPreHandle(Message message, RmqContext context);

  /**
   * Apply after-handle after produce/consume message
   * 
   * @param message message to be produced or consumed
   * @param context context contains exchange,routing key, queues...
   * @param timeUsed time used for produce/consume message
   * @param e caught exception
   */
  public void applyAfterHandle(Message message, RmqContext context, long timeUsed, Throwable e);
}
