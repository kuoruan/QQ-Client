package com.buaa.utils;

import com.buaa.domain.MessageResult;

public class MessageUtil {
    public static MessageResult analyzeMessage(String str) {
        MessageResult mr = new MessageResult(false);
        if (str == null) {
            return mr;
        }
        str = str.trim();
        if (str.matches("\\d{1,}\\{.*\\}")) {
            mr.setRight(true);
            mr.setMessageType(Integer.parseInt(str.substring(0, str.indexOf("{"))));
            mr.setJsonString(str.substring(str.indexOf("{"), str.length()));
        } else if (str.matches("\\d{1,}\\[.*\\]")) {
            mr.setRight(true);
            mr.setMessageType(Integer.parseInt(str.substring(0, str.indexOf("["))));
            mr.setJsonString(str.substring(str.indexOf("["), str.length()));
        } else {
            mr.setRight(false);
        }
        return mr;
    }
}
