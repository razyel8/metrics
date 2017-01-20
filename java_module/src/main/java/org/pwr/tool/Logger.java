package org.pwr.tool;

import org.jboss.logging.LogMessage;

import java.util.ArrayList;

public class Logger {
    public static LogPriority priorityLevel;
    static ArrayList<LogMessage> logs;
    static{
        logs = new ArrayList<>();
        priorityLevel = LogPriority.DEBUG;
    }

    public static void log(String message, LogPriority priority){
        logs.add(new LogMessage(message, priority));
        if(priority.val >= priorityLevel.val){
            System.out.println(message);
        }

    }


    public static class LogMessage{
        LogPriority priority;
        String message;

        LogMessage(String message, LogPriority priority){
            this.message = message;
            this.priority = priority;
        }

    }
    public enum LogPriority{
        TRACE(0), DEBUG(1), INFO(2), WARN(3), ERROR(4);
        int val;
        LogPriority(int val){
            this.val = val;
        }
    }

}
