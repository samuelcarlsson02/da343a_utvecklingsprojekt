package server.model;

import model.Message;
import model.User;
import server.controller.ControllerServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerHandler {
    private ControllerServer controllerServer;
    private ServerSocket serverSocket;
    private int port;

    public ServerHandler (ServerSocket serverSocket, int port, ControllerServer controllerServer) {
        this.serverSocket = serverSocket;
        this.port = port;
        this.controllerServer = controllerServer;
    }

    public boolean connectUser(User user) {
        try {
            while (true) {
                serverSocket = new ServerSocket(port);
                Socket socket = serverSocket.accept();
                boolean connected = controllerServer.connectUser(user, socket);
                System.out.println("Connection established in ServerHandler");
                return connected;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private class Connection extends Thread
    {
        private int port;

        /**
         * Constructor
         * @param port the port that the server listens to
         */
        public Connection(int port)
        {
            this.port = port;
        }

        /**
         * Method that starts the server and listens to client connections.
         */
        public void run()
        {
            try (ServerSocket serverSocket = new ServerSocket(port))
            {
                while(true)
                {
                    try
                    {
                        Socket socket = serverSocket.accept();


                    }
                    catch(IOException ex)
                    {
                        System.err.println(ex);
                    }
                }
            }
            catch(IOException ex)
            {
                System.err.println(ex);
            }
        }
    }

    private class ClientHandler extends Thread
    {
        private Socket socket;
        private Buffer<Message> messageBuffer = new Buffer<>();
        private ObjectOutputStream oos;

        /**
         * Constructor
         * @param socket client socket
         */
        public ClientHandler(Socket socket)
        {
            try
            {
                this.socket = socket;
                oos = new ObjectOutputStream(socket.getOutputStream());
            }
            catch (IOException ex)
            {
                System.err.println("ClientHandler konstruktor");
            }
        }

        /**
         * Method that adds a message to the buffer
         * @param message message that is added
         */
        public void addMessage(Message message)
        {
            messageBuffer.put(message);
        }

        /**
         * Method that handles messages for a client
         */
        public void run()
        {
            try
            {
                while(true)
                {
                    try
                    {
                        Message message = messageBuffer.get();
                        oos.writeObject(message);
                        oos.flush();
                    }
                    catch (IOException ex)
                    {
                        System.err.println("MessageServer ClientHandler Run");
                    }
                    catch (Exception ex)
                    {
                        System.out.println("MessageServer ClientHandler ClassNotFound");
                    }
                }
            }
            catch (Exception ex)
            {
                System.out.println(ex);
            }
        }
    }
}
