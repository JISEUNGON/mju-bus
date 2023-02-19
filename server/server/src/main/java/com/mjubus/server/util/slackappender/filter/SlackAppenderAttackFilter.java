package com.mjubus.server.util.slackappender.filter;

import java.util.ArrayList;
import java.util.List;

public class SlackAppenderAttackFilter implements CustomSlackAppenderFilter {

    private final String PREFIX = "JWT Token does not begin";
    private final String[] blackListKeyword = {
            "admin",
            "php",
            ".env",
            "mysql",
            "db",
            "dbadmin",
            "database",
            "shell",
            "root",
            "xml",
            "robot",
            "aspx",
            "ico",
            "png",
            "jpeg"
    };

    @Override
    public boolean isPass(String message) {
        for (String keyWord : blackListKeyword) {
            if (message.contains(PREFIX) && message.contains(keyWord)) return true;
        }
        return false;
    }
}
