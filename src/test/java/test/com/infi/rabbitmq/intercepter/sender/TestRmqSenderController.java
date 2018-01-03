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
package test.com.infi.rabbitmq.intercepter.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hongtao
 * @version v 0.1 , 2018年1月3日 下午3:53:07
 * @since JDK 1.8
 */
@RestController
public class TestRmqSenderController {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @RequestMapping(value = "test", method = RequestMethod.GET)
  public String test() {
    rabbitTemplate.convertAndSend("poc.sample.exchange", "sample", "test messages");
    return "successed to send message";
  }
}
