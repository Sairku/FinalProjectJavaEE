document.addEventListener('DOMContentLoaded', function () {
    const messageList = document.querySelector('#message-list'); // Список повідомлень
    const sendButton = document.querySelector('#send-btn'); // Кнопка відправки
    const messageInput = document.querySelector('#message-input'); // Поле для введення повідомлення
    const userId = document.querySelector('#user-id').value; // Отримання ID користувача

    // Відправка нового повідомлення
    sendButton.addEventListener('click', function () {
        const message = messageInput.value;
        if (message.trim() === '') {
            return; // Не відправляти порожнє повідомлення
        }

        fetch(`/messages/${userId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams({
                                receiverId: userId,
                                message
                            }),
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                // Додаємо нове повідомлення в список
                const newMessage = document.createElement('li');
                newMessage.classList.add('message-item');
                newMessage.textContent = message;
                messageList.appendChild(newMessage);
                messageInput.value = ''; // Очищаємо поле вводу
            } else {
                alert(data.msg ? data.msg : "Помилка при відправці повідомлення.");
            }
        })
        .catch(error => console.error('Error sending message:', error));
    });
});
