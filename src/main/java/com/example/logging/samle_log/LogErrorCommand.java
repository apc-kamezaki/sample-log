package com.example.logging.samle_log;

import java.util.Random;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogErrorCommand {
    private Logger logger = LogManager.getLogger(LogErrorCommand.class);
    private static final String SENDER = "error-sender@example.com";
    private static final String RECEIVER = "sample-receiver@long.example.com";

    public void execute() {
        Random random = new Random(System.currentTimeMillis());
        
        int smtpError = 400 + random.nextInt(100);
        int magicError = 900 + random.nextInt(100);
        UUID messageId = UUID.randomUUID();
        
        logger.info(String.format("from:%s\taddrs:[%s]\tmsgId:%s\terror:smtp:%d smagic:%d", SENDER, RECEIVER, messageId, smtpError, magicError));
    }
}
