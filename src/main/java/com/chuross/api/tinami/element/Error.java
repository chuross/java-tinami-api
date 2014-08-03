package com.chuross.api.tinami.element;

import org.apache.commons.lang3.StringUtils;
import org.simpleframework.xml.Attribute;

import java.util.Arrays;

public class Error {

    @Attribute(name = "msg")
    private String message;

    public String getMessage() {
        return message;
    }

    public String[] getMessages() {
        if(StringUtils.isBlank(message)) {
            return new String[0];
        }
        String[] messages = message.split(" ");
        if(messages.length == 0) {
            return new String[0];
        }
        return Arrays.copyOfRange(messages, 0, messages.length);
    }

}
