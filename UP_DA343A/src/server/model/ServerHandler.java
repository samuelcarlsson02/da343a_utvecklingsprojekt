package server.model;

import model.User;
import server.controller.ControllerServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Handles the client connections, connects the clients to the server.
 * @author Samuel Carlsson and Alexander Giheden
 */
public class ServerHandler {
    private ControllerServer controllerServer;
    private int port;

    /**
     * Constructor that starts the server.
     * @param port port to the server
     * @param controllerServer controller object
     */
    public ServerHandler (int port, ControllerServer controllerServer) {
        this.port = port;
        this.controllerServer = controllerServer;
        new Connection(port).start();
    }

    /**
     * This class represents the server and listens for client connections.
     */
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
                while(!isInterrupted())
                {
                    try
                    {
                        Socket socket = serverSocket.accept();
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        User user = (User) ois.readObject();

                        controllerServer.connectUser(user, ois, socket);

                    }
                    catch(IOException ex)
                    {
                        ex.printStackTrace();
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
