package com.example.logging.samle_log;

import java.util.UUID;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageSendCommand {
    private static final Logger logger = LogManager.getLogger(MessageSendCommand.class);
    private static final String sender = "message-sender@example.com";

    public void execute(int num) {
        IntStream.range(0, num)
            .mapToObj(i -> String.format("long-long-message-mail-address-%d@test.test.example.com", i))
            .forEach(this::send);
    }
    
    private void send(String addr) {
        String message = String.format("msgId:%s\tfrom:%s\taddrs:%s", UUID.randomUUID(), sender, addr);
        logger.info(message);
        
    }
}
