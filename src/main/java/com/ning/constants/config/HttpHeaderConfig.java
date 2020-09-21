package com.ning.constants.config;

import org.apache.http.cookie.Cookie;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 不在能知，乃在能行 ——nicholas
 * @description
 * @date 2020/9/21 17:31
 */
public class HttpHeaderConfig {
    public static final Map<String, String> GUOTONGNING = new HashMap<>();

    static {
        /*=============GUO TONG NING=============*/
        GUOTONGNING.put("Accept-Encoding", "gzip, deflate, br");
        GUOTONGNING.put("Connection", "keep-alive");
        GUOTONGNING.put("Host", "dreamtowntz.58.com");
        GUOTONGNING.put("Cookie", "PPU=\"UID=61843573843204&UN=smuo6selp&TT=00bc626e6aa97395da3d6e1dbc71bf47&PBODY=aXOQJep2dzHM8uXnkjcvOC8-vL5sWSz68Aq15DEDWKZhKStcyfzc4etGB4O4x_KzttshsImyrY1VjGPDhr7Rw0xmYKAPVlm67m0PmpuO6kn2w9BwdS6RuEqvVLpMsNhhzBTfPl5XdMUWqnRh18L5I8dJYnpdPZyfaP0qddai554&VER=1\"; 58cooper=userid=61843573843204&username=smuo6selp; 58uname=smuo6selp; www58com=UserID=61843573843204&UserName=smuo6selp; wmda_session_id_13722015424310=1600571398083-299a062e-e3e0-1c09; 58tj_uuid=7b9a2936-9cc0-4fc9-a42f-d571e22710fc; init_refer=; new_session=1; new_uv=25; qz_gdt=; spm=; utm_source=; xxzl_cid=\"e4411315e2a946faacc1b1a125c55456\"; 58mac=02:00:00:00:00:00; Accept-Encoding=deflate, gzip; brand=Apple; charset=UTF-8; cimei=0f607264fc6318a92b9e13c65db7cd3c; deviceToken=\"be3da38859e179eb89315435f79398fa0b222e10a05905d75d37623923ecacbc\"; imei=0f607264fc6318a92b9e13c65db7cd3c; machine=iPhone12,1; os=ios; osv=14.0; platform=iphone; r=1792_828; ua=iPhone 11_iOS 14.0; uid=61843573843204; uuid=5AC876E4-7EF4-4DBF-9430-354A2743D5C0; wmda_visited_projects=%3B13722015424310; wmda_new_uuid=1; wmda_uuid=7eb1b4f36bf32167890dc1c2bf6f527c; id58=c5/nfF9GFNt3GkxBE4oPAg==");
        GUOTONGNING.put("accept", "application/json, text/plain, */*");
        GUOTONGNING.put("accept-language", "zh-cn");
        GUOTONGNING.put("imei", "0f607264fc6318a92b9e13c65db7cd3c");
        GUOTONGNING.put("referer", "https://dreamtowntz.58.com/web/v/dreamtown?from=58local");
    }
}
