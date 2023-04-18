package config;

import controller.URLController;
import controller.UserJoinController;
import controller.UserLoginController;
import session.Session;
import session.SessionStore;
import service.UserJoinService;
import service.UserLoginService;

public class AppConfig {

    public static UserJoinController userJoinController() {
        return new UserJoinController(userJoinService());
    }

    public static UserLoginController userLoginController() {
        return new UserLoginController(userLoginService(), sessionStore());
    }

    private static SessionStore sessionStore() {
        return new SessionStore();
    }

    private static UserJoinService userJoinService() {
        return new UserJoinService();
    }

    public static URLController urlController() {
        return new URLController();
    }

    private static UserLoginService userLoginService() {
        return new UserLoginService();
    }

    private static SessionStore cookieStore() {
        return new SessionStore();
    }
}
