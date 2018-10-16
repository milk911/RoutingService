package milk.Util;

import milk.models.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class Cookies {

    public static void setCookies(User user, HttpServletResponse response) {
        Cookie cookie = new Cookie("UserName", user.getName());
        cookie.setMaxAge(3600);
        cookie.setPath("/");
        response.addCookie(cookie);

        cookie = new Cookie("UserID", user.getId());
        cookie.setMaxAge(3600);
        cookie.setPath("/");
        response.addCookie(cookie);

    }

}
