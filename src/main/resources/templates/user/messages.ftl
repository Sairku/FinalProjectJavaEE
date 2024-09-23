<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>User List</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div id="chat-container">
    <ul id="message-list">
        <!-- Повідомлення будуть додаватися тут -->
    </ul>
    <input type="text" id="message-input" placeholder="Type message ">
    <button id="send-btn">Send</button>
    <input type="hidden" id="user-id" value="${userId}">
</div>
<script src="/js/messages.js"></script>
</body>
</html>