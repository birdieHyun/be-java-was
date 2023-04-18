package controller;

import session.Session;
import session.SessionStore;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserJoinService;
import request.HttpRequest;
import response.HttpResponse;
import service.UserLoginService;

import java.io.IOException;

import static util.HttpMethod.*;
import static util.UserURL.*;

public class UserJoinController {
    private final UserJoinService userJoinService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public UserJoinController(UserJoinService userJoinService) {
        this.userJoinService = userJoinService;
    }

    // TODO : 에러페이지 생성, 회원가입 검증
    public String process(HttpRequest request, HttpResponse response) throws IOException {
        // GET 요청인 경우 분리
        if (request.getMethod().equals(HTTP_GET)) {
            // 회원 가입 폼 보여주기
            if (request.getUrl().equals(JOIN_FORM)) {
                return JOIN_FORM;
            }
        }

        if (request.getMethod().equals(HTTP_POST)) {
            // 회원가입
            if (request.getUrl().equals(CREATE_USER_URL)) {
                return saveUser(request, response);
            }
        }

        return "/error";
    }

    private String saveUser(HttpRequest request, HttpResponse response) throws IOException {
        String requestBody = request.getRequestBody();
        log.debug("회원가입 성공 = {}", requestBody);
        userJoinService.addUser(requestBody);
        return response.redirectHome();
    }
}
