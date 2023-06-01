package server.model;

import server.controller.ControllerServer;

public class Logger {
    private FileManager fileManager;
    private ControllerServer controllerServer;

    public Logger(ControllerServer controllerServer){
        this.controllerServer = controllerServer;
        fileManager = new FileManager();
    }

    public void addLogEntry(String logEntry) {
        fileManager.writeToFile("serverlog.txt", controllerServer.getCurrentDateAndTime() + ": " + logEntry);
    }

    public String[] getLogEntries(String startTime, String endTime) {
        return fileManager.readFromFile("serverlog.txt", startTime, endTime);
    }
}
