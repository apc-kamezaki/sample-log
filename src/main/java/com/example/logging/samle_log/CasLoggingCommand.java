package com.example.logging.samle_log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CasLoggingCommand {
    private static final Logger logger = LogManager.getLogger(CasLoggingCommand.class);
    private static DateFormat logDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
    private static final AuditEvent[] events = AuditEvent.values();
    
    private final Random random = new Random(new Date().getTime());

    public void execute(int num) {
        IntStream.range(0, num).forEach(this::log);
    }
    
    private void log(int current) {
        String who = String.format("user%04d", current / 10);
        String what;
        AuditEvent action = getEvent(current);
        switch (action) {
        case AUTHENTICATION_SUCCESS:
        case AUTHENTICATION_FAILED:
            what = String.format("Supplied credentials: [%s]", who);
            break;
        default:
            what = whats[current % whats.length];
            break;        
        }
        String when = logDateFormat.format(new Date());
        String client = String.format("172.31.200.%d", random.nextInt(255));
        logger.info(String.format(MULTILINE_FORMAT, who, what, action.toString(), when, client));
        try {
            Thread.sleep(random.nextInt(1435));
        } catch (InterruptedException ignore) {
        }
    }
    
    private AuditEvent getEvent(int current) {
        return events[current % events.length];
    }
    
    enum AuditEvent {
        SERVICE_TICKET_CREATED,
        TICKET_GRANTING_TICKET_CREATED,
        AUTHENTICATION_SUCCESS,
        AUTHENTICATION_FAILED,
        SERVICE_TICKET_VALIDATED,
        TICKET_GRANTING_TICKET_NOT_CREATE,
        TICKET_GRANTING_TICKET_DESTROYED
    }
    
    private static String[] whats = {
            "TGT-***********************************************wjO5zwMpEI-cas1",
            "ST-78-gWMX1dk3XbI6tOn3F2eg-cas1",
            "ST-78-gWMX1dk3XbI6tOn3F2eg-cas1 for https://www.example.com/login/cas"
    };
            
    
    private static final String MULTILINE_FORMAT = 
            "Audit trail record BEGIN\n"
            + "=============================================================\n"
            + "WHO: %s\n"
            + "WHAT: %s\n"
            + "ACTION: %s\n"
            + "APPLICATION: CAS\n"
            + "WHEN: %s\n"
            + "CLIENT IP ADDRESS: %s\n"
            + "SERVER IP ADDRESS: 192.168.0.108\n"
            + "=============================================================\n"
            + "\n";
}
