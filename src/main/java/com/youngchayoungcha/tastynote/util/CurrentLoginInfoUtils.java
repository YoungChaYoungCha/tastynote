package com.youngchayoungcha.tastynote.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentLoginInfoUtils {

    public static Long getLoginMemberId(){
        String memberId = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Long.parseLong(memberId);
    }
}
