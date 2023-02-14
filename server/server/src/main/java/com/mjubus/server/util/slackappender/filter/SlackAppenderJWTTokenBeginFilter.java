package com.mjubus.server.util.slackappender.filter;

public class SlackAppenderJWTTokenBeginFilter implements CustomSlackAppenderFilter {
    @Override
    public boolean isPass(String message) {
        return message.contains("JWT Token does not begin with Bearer String with URL");
    }
}
