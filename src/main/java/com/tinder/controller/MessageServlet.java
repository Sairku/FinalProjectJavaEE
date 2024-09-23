package com.tinder.controller;

import com.tinder.model.User;
import com.tinder.service.MessageService;
import com.tinder.service.UserService;
import com.tinder.util.CookieHelper;
import com.tinder.util.TemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/messages/*")
public class MessageServlet extends HttpServlet {


    // Захардкожені повідомлення для тестування
    private Map<Integer, List<String>> messages = new HashMap<>();
    private MessageService messageService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        messageService = new MessageService();
        userService = new UserService();
        // Ініціалізуємо декілька захардкожених повідомлень
        messages.put(1, Arrays.asList("Привіт! Як справи?", "Я добре, дякую!"));
        messages.put(2, Arrays.asList("Привіт, давно не бачилися!", "Так, погоджуюсь!"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo(); // Отримуємо частину URL після /messages/
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is missing.");
            return;
        }

        String[] splits = pathInfo.split("/");
        if (splits.length < 2) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid User ID.");
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(splits[1]);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID should be a number.");
            return;
        }

        // Отримуємо повідомлення для користувача
        List<String> userMessages = messages.getOrDefault(userId, Arrays.asList("Повідомлень немає"));

        // Відправляємо повідомлення на сторінку
        Map<String, Object> data = new HashMap<>();

        data.put("userId", userId);
        data.put("messages", userMessages);

        TemplateEngine.render(resp, "user/messages.ftl", data);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String userEmail = CookieHelper.getEmail(req);
        int receiverId = Integer.parseInt(req.getParameter("receiverId"));
        String msg = req.getParameter("message");

        try {
            User user = userService.getUser(userEmail);
            messageService.createMessage(user.getId(), receiverId, msg);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"success\": true}");

        } catch (SQLException e) {
            e.printStackTrace();
            resp.setContentType("application/json");
            try {
                resp.getWriter().write("{\"success\": false, \"msg\": \"" + e.getMessage() + "\"}");
            } catch (IOException ex) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
            resp.setStatus(500);
        }


    }
}
