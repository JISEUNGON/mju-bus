package com.mjubus.server.service.chatting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjubus.server.vo.ChattingMessage;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class RedisMessageSubServiceTest {

    @Autowired
    ObjectMapper objectMapper;

    private final String HOST = "redis://localhost";
    private RedisClient subClient;
    private RedisClient pubClient;
    private StatefulRedisPubSubConnection<String, String> subConnection;
    private StatefulRedisConnection<String, String> pubConnection;

    private List<String> messageList;

    public RedisMessageSubServiceTest() throws JsonProcessingException {
        subClient = RedisClient.create(HOST);
        pubClient = RedisClient.create(HOST);
        subConnection = subClient.connectPubSub();
        pubConnection = pubClient.connect();
        messageList = new ArrayList<>();
        subConnection.addListener(new RedisPubSubListener<String, String>() {
            @Override
            public void message(String channel, String message) {
                messageList.add(message);
            }

            @Override
            public void message(String pattern, String channel, String message) {

            }

            @Override
            public void subscribed(String channel, long count) {

            }

            @Override
            public void psubscribed(String pattern, long count) {

            }

            @Override
            public void unsubscribed(String channel, long count) {

            }
            @Override
            public void punsubscribed(String pattern, long count) {

            }
        });
    }

    @After
    public void tearDown() throws Exception {
        //test라는 이름이 붙은 모든 채널을 구독 해제한다
        subConnection.sync().punsubscribe("test");
        subClient.shutdown();
        pubClient.shutdown();
    }

    @Before
    public void setUp() throws Exception {
        subClient = RedisClient.create(HOST);
        pubClient = RedisClient.create(HOST);
        subConnection = subClient.connectPubSub();
        pubConnection = pubClient.connect();
    }

    @Test
    @DisplayName("[REDIS][Publish] publish 테스트")
    public void 테스트_redis_pub() {


        //when
        pubConnection.sync().publish("test1", "{\"roomId\": \"test\", \"sender\": \"gildong\", \"message\": \"publish message\"}");
        subConnection.sync().subscribe("test1");

        pubConnection.sync().publish("test2", "{\"roomId\": \"test\", \"sender\": \"chulsu\", \"message\": \"publish message\"}");
        subConnection.sync().subscribe("test2");
        //then
        List<String> channels = pubConnection.sync().pubsubChannels();

        assertNotEquals(channels.size(), 0);
        assertEquals(channels.size(), 2);
        assertEquals(channels.get(0), "test2");
        assertEquals(channels.get(1), "test1");
        // 왜 active channel을 거꾸로 리턴해주는지?그건 모르겠음
    }

    @Test
    @DisplayName("[REDIS][Subscribe] subscribe 테스트")
    public void 테스트_redis_sub() throws InterruptedException, JsonProcessingException {
        //given
        RedisPubSubCommands<String, String> sync = subConnection.sync();

        //when
        //처음에 publish 시 방이 생성되므로 publish 되는 메시지는 구독자가 있을 수 없어야 함
        pubConnection.sync().publish("test", "{\"roomId\": \"test\", \"sender\": \"gildong\", \"message\": \"publish message\"}");
        sync.subscribe("test");
        pubConnection.sync().publish("test", "{\"roomId\": \"test\", \"sender\": \"gildong\", \"message\": \"hello\"}");
        pubConnection.sync().publish("test", "{\"roomId\": \"test\", \"sender\": \"chulsu\", \"message\": \"hi\"}");
        //채널에 들어온 데이터가 1개로 뜰때가 많아서 2번째(chulsu) publish가 되는동안 테스트가 넘어가버리는것 같아 sleep 1초를 추가합니다
        Thread.sleep(1000);

        //then
        assertNotEquals(messageList.size(), 0);
        assertEquals(messageList.size(), 2);

        ChattingMessage firstMessage = objectMapper.readValue(messageList.get(0), ChattingMessage.class);
        assertEquals(firstMessage.getSender(), "gildong");
        assertEquals(firstMessage.getMessage(), "hello");
        assertEquals(firstMessage.getRoomId(), "test");

        ChattingMessage secondMessage = objectMapper.readValue(messageList.get(1), ChattingMessage.class);
        assertNotEquals(secondMessage.getSender(), "gildong");
        assertEquals(secondMessage.getSender(), "chulsu");
        assertEquals(secondMessage.getMessage(), "hi");
        assertEquals(secondMessage.getRoomId(), "test");
    }

}