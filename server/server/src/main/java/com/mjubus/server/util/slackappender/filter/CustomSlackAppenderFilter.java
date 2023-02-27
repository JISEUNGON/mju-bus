package com.mjubus.server.util.slackappender.filter;

public interface CustomSlackAppenderFilter {
    public boolean isPass(String message);
}
