package server.model;

import server.controller.ControllerServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerHandler {
    private ControllerServer controllerServer;
    private String ip;
    private int port;

    public ServerHandler (ControllerServer controllerServer, int port) {
        this.controllerServer = controllerServer;
        this.port = port;
        new Connection(port).start();
    }

    public boolean connectUser(User user) {
        controllerServer.connectUser(user, );
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
}
