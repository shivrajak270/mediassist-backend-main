package com.shivraj.medassist.ServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shivraj.medassist.Dto.MailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


//@Service
//public class NotificationPublisher {
//
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    public void publish(MailDTO mailDTO)
//            throws JsonProcessingException {
//
//        String json =
//                objectMapper.writeValueAsString(mailDTO);
//        System.out.println("this is json"+json);
//
//        redisTemplate.convertAndSend(
//                "notification-channel",
//                json
//        );
//    }
//    public void publishsample(String message){
//        redisTemplate.convertAndSend("sample-channel",message);
//    }
//}
@Service
public class NotificationPublisher {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void publish(MailDTO dto)
            throws JsonProcessingException {

        String json =
                objectMapper.writeValueAsString(dto);

        Map<String, String> map = new HashMap<>();

        map.put("data", json);

        RecordId recordId =
                redisTemplate.opsForStream()
                        .add(
                                "notification-stream",
                                map
                        );

        System.out.println("MESSAGE ADDED TO STREAM");
        System.out.println(recordId);
    }
}
