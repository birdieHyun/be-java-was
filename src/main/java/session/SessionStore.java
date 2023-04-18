package session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SessionStore {

    private static Map<String, Session> cookieStore = new ConcurrentHashMap<>();

    public void saveCookie(Session cookie) {
        cookieStore.put(cookie.getUuid(), cookie);
    }

    public static Optional<Session> findCookieByUUID(String uuid) {
        return findAll().stream()
                .filter(cookie -> cookie.getUuid().equals(uuid))
                .findAny();
    }

    private static List<Session> findAll() {
        return new ArrayList<>(cookieStore.values());
    }
}
