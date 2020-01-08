package com.baizhi.chz.config;

import redis.clients.jedis.Jedis;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConf {
    @Bean
    public Jedis getJesis(){
        return new Jedis("198.168.32.137",6379);
    }

}
