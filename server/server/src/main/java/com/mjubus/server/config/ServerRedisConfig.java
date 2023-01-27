package com.mjubus.server.config;

import com.mjubus.server.vo.MessageLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

@Configuration
@Profile("server")
public class ServerRedisConfig {

    @Value("${spring.redis.cluster.nodes}")
    private List<String> clusterNodes;

    //참고: https://github.com/codej99/websocket-chat-server/blob/feature/redis-pub-sub/src/main/java/com/websocket/chat/config/RedisConfig.java
    /**
     * redis pub/sub 메시지를 처리하는 listener 설정
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(connectionFactory);
        return redisMessageListenerContainer;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(clusterNodes);
        return new LettuceConnectionFactory(redisClusterConfiguration);
    }

    /**
     * 어플리케이션에서 사용할 redisTemplate 설정
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(MessageLog.class));
        return redisTemplate;
    }
}
