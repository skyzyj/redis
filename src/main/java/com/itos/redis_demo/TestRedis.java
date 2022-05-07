package com.itos.redis_demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class TestRedis {
    @Autowired
    @Qualifier("myRedisTemplate")
    RedisTemplate redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    ObjectMapper objectMapper;

    public void testRedis(){
        stringRedisTemplate.opsForValue().set("hello01","china");
        Object hello = stringRedisTemplate.opsForValue().get("hello01");
        System.out.println(hello);
    }

    public void testRedisHash(){
        Person sky = new Person("jim", 19);

        //redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));

        Jackson2HashMapper jm = new Jackson2HashMapper(objectMapper,false);

        redisTemplate.opsForHash().putAll("person2",jm.toHash(sky));
        Map<Object, Object> map = redisTemplate.opsForHash().entries("person2");
        Person p = objectMapper.convertValue(map, Person.class);
        System.out.println(p);
    }

    //发布消息
    public void sendMessage(){
        redisTemplate.convertAndSend("ch1","hello");
    }
    //订阅消息
    public void getMessage(){
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        connection.subscribe(new MessageListener() {
            @Override
            public void onMessage(Message message, byte[] pattern) {
                byte[] body = message.getBody();
                System.out.println(new String(body));
            }
        }, "ch1".getBytes());
        while (true){ }
    }
}
