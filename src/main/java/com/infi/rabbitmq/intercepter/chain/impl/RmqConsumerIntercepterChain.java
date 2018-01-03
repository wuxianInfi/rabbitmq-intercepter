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
package com.infi.rabbitmq.intercepter.chain.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.springframework.amqp.core.Message;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.infi.rabbitmq.intercepter.chain.RmqIntercepterChain;
import com.infi.rabbitmq.intercepter.model.RmqContext;
import com.infi.rabbitmq.intercepter.spi.RmqConsumerIntercepter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author hongtao
 * @version v 0.1 , 2018年1月3日 下午2:57:20
 * @since JDK 1.8
 */
@Slf4j
public class RmqConsumerIntercepterChain implements RmqIntercepterChain {

  private static final List<RmqConsumerIntercepter> interceptors = load();

  static List<RmqConsumerIntercepter> load() {
    ServiceLoader<RmqConsumerIntercepter> intercepters =
        ServiceLoader.load(RmqConsumerIntercepter.class);
    Iterator<RmqConsumerIntercepter> it = intercepters.iterator();
    List<RmqConsumerIntercepter> list = Lists.newArrayList(it);
    if (!CollectionUtils.isEmpty(list)) {
      Collections.sort(list, (i1, i2) -> {
        return i1.getOrder() - i2.getOrder();
      });
      list.forEach(i -> log.info("load rmq consumer intercepter {}", i.getClass().getName()));
      return Collections.unmodifiableList(list);
    } else {
      log.info("No rmq consumer intercepter found");
      return Lists.newArrayList();
    }

  }

  @Override
  public void applyPreHandle(Message message, RmqContext context) {
    if (CollectionUtils.isEmpty(interceptors)) {
      return;
    }
    for (int i = 0; i < interceptors.size(); i++) {
      interceptors.get(i).preHandle(message, context);
    }
  }

  @Override
  public void applyAfterHandle(Message message, RmqContext context, long timeUsed, Throwable e) {
    if (CollectionUtils.isEmpty(interceptors)) {
      return;
    }
    for (int i = interceptors.size() - 1; i >= 0; i--) {
      if (e != null) {
        interceptors.get(i).exceptionCaught(message, context, timeUsed, e);
      } else {
        interceptors.get(i).afterCompletion(message, context, timeUsed);
      }
    }
  }
}
