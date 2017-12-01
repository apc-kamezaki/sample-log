package com.example.logging.samle_log;

import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AlertSendCommand {
    private static final String sender = "sender@example.com";
    private static final int BLOCK_SIZE = 99;
    private Logger logger = LogManager.getLogger(AlertSendCommand.class);
    
    public void execute(int num) {
        if (num < 1)    return;
        
        int blockNum =  (int) Math.ceil(num / (double) BLOCK_SIZE);
        IntStream.range(0, blockNum)
            .mapToObj(block -> makeAddressBlockList(block, num))
            .forEach(this::send);
    }
    
    private List<String> makeAddressBlockList(int blockNum, int maxNum) {
        int start = blockNum * BLOCK_SIZE;
        int end = (maxNum - start) > BLOCK_SIZE ? start + BLOCK_SIZE : maxNum;
        
        return IntStream.range(start, end)
            .mapToObj(i -> String.format("long-long-name-address-for-logging-test-%d@long.long.example.com", i))
            .collect(Collectors.toList());
    }
    
    private void send(List<String> addrList) {
        
        StringJoiner joiner = new StringJoiner(",");
        addrList.stream().forEach(joiner::add);
        String message = String.format("msgId:%s\tfrom:%s\taddrs:%s", UUID.randomUUID(), sender, joiner.toString());
        
        logger.info(message);
    }
}
