package com.mjubus.server.util.slackappender;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.LayoutBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.maricn.logback.SlackAppender;
import com.mjubus.server.util.slackappender.filter.SlackAppenderAttackFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CustomSlackAppender extends SlackAppender {

    private final static String API_URL = "https://slack.com/api/chat.postMessage";

    private CustomSlackAppenderChain filterChain;

    public CustomSlackAppender() {
        this.filterChain = new CustomSlackAppenderChain();
        filterChain.addFilter(new SlackAppenderAttackFilter());
    }

    private static Layout<ILoggingEvent> defaultLayout = new LayoutBase<ILoggingEvent>() {
        public String doLayout(ILoggingEvent event) {
            return "-- [" + event.getLevel() + "]" +
                    event.getLoggerName() + " - " +
                    event.getFormattedMessage().replaceAll("\n", "\n\t");
        }
    };

    @Override
    protected void append(ILoggingEvent evt) {
        try {
            if (super.getWebhookUri() != null && !super.getWebhookUri().isEmpty()) {
                sendMessageWithWebhookUri(evt);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            addError("Error posting log to Slack.com (" + super.getChannel() + "): " + evt, ex);
        }
    }

    private void sendMessageWithWebhookUri(final ILoggingEvent evt) throws IOException {
        String[] parts = super.getLayout().doLayout(evt).split("\n", 2);

        Map<String, Object> message = new HashMap<>();
        message.put("channel", super.getChannel());
        message.put("username", super.getUsername());
        message.put("icon_emoji", super.getIconEmoji());
        message.put("icon_url", super.getIconUrl());
        message.put("text", parts[0]);

        // Send the lines below the first line as an attachment.
        if (parts.length > 1 && parts[1].length() > 0) {
            Map<String, String> attachment = new HashMap<>();
            attachment.put("text", parts[1]);
            if (super.getColorCoding()) {
                attachment.put("color", colorByEvent(evt));
            }

            message.put("attachments", Collections.singletonList(attachment));
        }

        ObjectMapper objectMapper = new ObjectMapper();
        final byte[] bytes = objectMapper.writeValueAsBytes(message);
        if (!filterChain.validateAndPass(parts[0])) {
            postMessage(super.getWebhookUri(), "application/json", bytes);
        }
    }

    private String colorByEvent(ILoggingEvent evt) {
        if (Level.ERROR.equals(evt.getLevel())) {
            return "danger";
        } else if (Level.WARN.equals(evt.getLevel())) {
            return "warning";
        } else if (Level.INFO.equals(evt.getLevel())) {
            return "good";
        }

        return "";
    }

    private void postMessage(String uri, String contentType, byte[] bytes) throws IOException {
        final HttpURLConnection conn = (HttpURLConnection) new URL(uri).openConnection();
        conn.setConnectTimeout(super.getTimeout());
        conn.setReadTimeout(super.getTimeout());
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setFixedLengthStreamingMode(bytes.length);
        conn.setRequestProperty("Content-Type", contentType);

        final OutputStream os = conn.getOutputStream();
        os.write(bytes);

        os.flush();
        os.close();
    }
}
