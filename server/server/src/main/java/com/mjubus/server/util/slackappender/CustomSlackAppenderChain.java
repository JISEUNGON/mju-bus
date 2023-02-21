package com.mjubus.server.util.slackappender;

import com.mjubus.server.util.slackappender.filter.CustomSlackAppenderFilter;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomSlackAppenderChain {
    private List<CustomSlackAppenderFilter> filterList;

    public CustomSlackAppenderChain() {
        filterList = new LinkedList<>();
    }

    public CustomSlackAppenderChain addFilter(CustomSlackAppenderFilter filter) {
        filterList.add(filter);
        return this;
    }

    public boolean validateAndPass(String message) {
        AtomicBoolean passTicket = new AtomicBoolean(false);

        filterList.forEach((filter -> {
            if (filter.isPass(message)) {
                passTicket.set(true);
            }
        }));
        return passTicket.get();
    }
}
