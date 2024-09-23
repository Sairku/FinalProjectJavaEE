package com.tinder.service;

import com.tinder.dao.MessageDAO;
import com.tinder.dao.MessagesDaoJDBC;
import com.tinder.model.Message;
import java.sql.SQLException;

public class MessageService {
    MessageDAO messageDAO = new MessagesDaoJDBC();

    public Message createMessage (int senderId, int receiverId, String text) throws SQLException {
        Message message = new Message(senderId, receiverId, text);
        messageDAO.create(message);
        return message;
    }


}
