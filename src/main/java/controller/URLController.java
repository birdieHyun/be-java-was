package controller;

import config.AppConfig;
import session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;

import java.io.IOException;

public class URLController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserJoinController userJoinController = AppConfig.userJoinController();
    private final UserLoginController userLoginController = AppConfig.userLoginController();


    // TODO : 메서드 분리해주고, UserController와 UserJoinController 분리하기
    public String mapUrl(String path, HttpRequest httpRequest, HttpResponse httpResponse, Session cookie) throws IOException {
        // localhost:8080 기본화면으로 이동
        if (path.equals("/")) {
            httpResponse.setStatus(200);
            return "/index.html";
        }
        // user 회원가입
        if (path.startsWith("/user/form")) {
            return userJoinController.process(httpRequest, httpResponse);
        }

        // user 로그인
        if (path.startsWith("/user/login")) {
            return userLoginController.process(httpRequest, httpResponse, cookie);
        }


        // 기본 경로로 이동
        httpResponse.setStatus(200);
        return path;
    }
}
