package controller;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;
import service.UserLoginService;
import session.Session;
import session.SessionStore;

import java.io.IOException;
import java.util.UUID;

import static util.HttpMethod.*;
import static util.UserURL.*;

public class UserLoginController {
    private final UserLoginService userLoginService;
    private final SessionStore sessionStore;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public UserLoginController(UserLoginService userLoginService, SessionStore cookieStore) {
        this.userLoginService = userLoginService;
        this.sessionStore = cookieStore;
    }

    public String process(HttpRequest request, HttpResponse response, Session cookie) throws IOException {
        if (request.getMethod().equals(HTTP_GET)){
            // 로그인 폼 보여주기
            if (request.getUrl().equals(LOGIN_FORM)) {
                return LOGIN_FORM;
            }
        }

        if (request.getMethod().equals(HTTP_POST)) {
            // 로그인, 로그인 실패 시 실패 페이지로 이동
            if (request.getUrl().equals(LOGIN_USER)) {
                return loginUser(request, response, cookie);
            }
        }

        return "/error";
    }

    private String loginUser(HttpRequest request, HttpResponse response, Session cookie) throws IOException {
        String requestBody = request.getRequestBody();
        User loginUser = userLoginService.login(requestBody);

        if (loginUser == null) {
            return response.returnToLoginFailed();
        }
        cookie.setUuid(UUID.randomUUID().toString());
        cookie.setUser(loginUser);
        response.setHeader("Set-cookie", String.format("sid=%s; Path=/", cookie.getUuid()));
        sessionStore.saveCookie(cookie);
        return response.redirectHome();
    }
}
