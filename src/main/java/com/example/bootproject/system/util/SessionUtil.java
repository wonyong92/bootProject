package com.example.bootproject.system.util;

import javax.servlet.http.HttpSession;

public class SessionUtil {
    private SessionUtil() {
    }

    public static String getLoginId(HttpSession session) {
        Object id = session.getAttribute("id");
        if (id == null) {
            return null;
        }
        return (String) id;
    }


}
