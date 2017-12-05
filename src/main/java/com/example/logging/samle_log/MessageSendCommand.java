package com.example.logging.samle_log;

import java.util.UUID;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageSendCommand {
    private static final Logger logger = LogManager.getLogger(MessageSendCommand.class);
    private static final String SENDER = "message-sender@example.com";
    private static final String SUBJECT = "お知らせ";
    private static final String MESSAGE = "これはお知らせメールです。本日の予定はXXXです。";

    public void execute(int num) {
        IntStream.range(0, num)
            .mapToObj(i -> String.format("long-long-message-mail-address-%d@test.test.example.com", i))
            .forEach(this::send);
    }
    
    private void send(String addr) {
        String message = String.format("msgId:%s\tfrom:%s\tsubject:%s\tbody:%s\taddrs:%s", UUID.randomUUID(), SENDER, SUBJECT, MESSAGE, addr);
        logger.info(message);
        
    }
}
