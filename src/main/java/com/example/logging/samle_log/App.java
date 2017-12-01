package com.example.logging.samle_log;

import java.util.List;
import java.util.Locale;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
    private static final Logger logger = LogManager.getLogger(App.class);
    
    public static void main( String[] args ) {
        int num = 1;
        
        CommandLineParser parser = new DefaultParser();
        Options options = setupOptions();
        
        try {
            CommandLine line = parser.parse(options, args);
            
            List<String> params = line.getArgList();
            if (params.size() != 1) {
                showUsage(options);
                System.exit(9);
            }
            if (line.hasOption("n")) {
                num = Integer.parseInt(line.getOptionValue("n"));
                if (num <= 0) {
                    showUsage(options);
                    System.exit(9);
                }
            }
            switch (CommandName.parse(params.get(0))) {
            case ALARM: {
                AlertSendCommand alarmCommand = new AlertSendCommand();
                alarmCommand.execute(num);
                break;
            }
            case MESSAGE: {
                MessageSendCommand messageCommand = new MessageSendCommand();
                messageCommand.execute(num);
                break;
            }
            default:
                showUsage(options);
                System.exit(9);
                break;
            }            
        } catch (ParseException e) {
            logger.error("command parse error", e);
            showUsage(options);
            System.exit(9);
        }
        

        
    }
    
    private static Options setupOptions() {
        Options options = new Options();
        options.addOption("n", true, "num of execution.");
        
        return options;
    }
    
    private static void showUsage(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        
        formatter.printHelp("app command", options);
    }
    
    public static enum CommandName {
        ALARM,
        MESSAGE,
        UNKNOWN;
        
        public static CommandName parse(String name) {
            try {
                return CommandName.valueOf(name.toUpperCase(Locale.US));
            } catch(Exception ignore) {
                return UNKNOWN;
            }
        }
        
    }
}
