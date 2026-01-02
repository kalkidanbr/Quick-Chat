QuickChat – Java Multi-Client Chat Application

Welcome to QuickChat, a simple Java chat app where multiple users can talk in real-time!

🚀 Features

Chat with multiple users at the same time

Pick a username for yourself

Broadcast messages to everyone connected

Leave the chat anytime with /quit

💻 Requirements

Java Development Kit (JDK) 8 or higher

Terminal or IDE (Eclipse, IntelliJ, VS Code, etc.)

⚡ How to Run QuickChat

Compile the server and client

javac QuickChatServer.java
javac QuickChatClient.java


Start the server first

java QuickChatServer


You’ll see: QuickChat server listening on port 5000

Start each client in a new terminal

java QuickChatClient


Enter your username when prompted

Type messages to chat with others

Type /quit to leave the chat

💬 Example

Client 1:

Enter your name: Alice
[Alice has joined the chat]
Hello everyone!


Client 2:

Enter your name: Bob
[Alice has joined the chat]
Hi Alice!

📝 Notes

Server must run before clients

Each client needs its own terminal window

/quit exits the chat gracefully
