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
            "sql",
            "shell",
            "root",
            "xml",
            "cgi",
            "vendor",
            ".github",
            "robot",
            "aspx",
            "ico",
            "png",
            "jpeg"
    };

    @Override
    public boolean isPass(String message) {
        if (!message.contains(PREFIX)) return false;
        for (String keyWord : blackListKeyword) {
            if (message.contains(keyWord)) return true;
        }
        return false;
    }
}
