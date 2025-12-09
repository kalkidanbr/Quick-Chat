import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class QuickChatServer {
    private static final int PORT = 5000;
    private static Set<ClientHandler> clientHandlers = ConcurrentHashMap.newKeySet();

    public static void main(String[] args) {
        System.out.println("QuickChat server listening on port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(socket);
                clientHandlers.add(handler);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
        }
    }

    public static void broadcast(String message, ClientHandler excludeUser) {
        for (ClientHandler client : clientHandlers) {
            if (client != excludeUser) {
                client.sendMessage(message);
            }
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter writer;
        private String userName;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void sendMessage(String message) {
            if (writer != null) {
                writer.println(message);
            }
        }

        @Override
        public void run() {
            try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ) {
                writer = new PrintWriter(socket.getOutputStream(), true);
                writer.println("Enter your name: ");
                userName = reader.readLine();
                if (userName == null || userName.isEmpty()) {
                    userName = "Anonymous";
                }
                broadcast(userName + " has joined the chat", this);

                String clientMessage;
                while ((clientMessage = reader.readLine()) != null) {
                    if (clientMessage.equalsIgnoreCase("/quit")) {
                        break;
                    }
                    broadcast("[" + userName + "]: " + clientMessage, this);
                }
            } catch (IOException e) {
                System.out.println("Client error: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {}
                clientHandlers.remove(this);
                broadcast(userName + " has left the chat", this);
            }
        }
    }
}
