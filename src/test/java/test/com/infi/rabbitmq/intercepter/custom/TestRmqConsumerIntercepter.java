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
package test.com.infi.rabbitmq.intercepter.custom;

import org.springframework.amqp.core.Message;

import com.infi.rabbitmq.intercepter.model.RmqContext;
import com.infi.rabbitmq.intercepter.spi.RmqConsumerIntercepter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author hongtao
 * @version v 0.1 , 2018年1月3日 下午3:47:16
 * @since JDK 1.8
 */
@Slf4j
public class TestRmqConsumerIntercepter implements RmqConsumerIntercepter {

  @Override
  public int getOrder() {
    return 0;
  }

  @Override
  public void preHandle(Message message, RmqContext context) {
    log.info("PreHandle, context = {}", context);
  }

  @Override
  public void afterCompletion(Message message, RmqContext context, long timeUsed) {
    log.info("afterCompletion, timeUsed = {}, context = {}", timeUsed, context);
  }

  @Override
  public void exceptionCaught(Message message, RmqContext context, long timeUsed, Throwable e) {
    log.info("exceptionCaught, timeUsed = {}, context = {}", timeUsed, context, e);
  }

}
