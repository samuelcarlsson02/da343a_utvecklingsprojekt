package server.model;

import model.Message;
import model.User;
import server.controller.ControllerServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerHandler {
    private ControllerServer controllerServer;
    private ServerSocket serverSocket;
    private int port;

    public ServerHandler (int port, ControllerServer controllerServer) {
        this.port = port;
        this.controllerServer = controllerServer;
        new Connection(port).start();
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
                        System.out.println("Waiting for client to connect");
                        Socket socket = serverSocket.accept();
                        System.out.println("Client connecting");
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        User user = (User) ois.readObject();
                        System.out.println("User " + user.getUsername() + " is created");

                        controllerServer.connectUser(user, ois, socket);
                        System.out.println("After connectuser method");
                    }
                    catch(IOException ex)
                    {
                        System.err.println(ex);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            catch(IOException ex)
            {
                System.err.println(ex);
            }
        }
    }
}
