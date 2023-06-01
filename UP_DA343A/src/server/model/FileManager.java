package server.model;

import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FileManager {

    public ArrayList<String> readFromContacts(String username, String filename) {
        ArrayList<String> contacts = new ArrayList<>();
        String filepath = getFilesFolderPath() + File.separator + filename;
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                String usernameInFile = parts[0].trim();
                String contact = parts[1].trim();
                if (usernameInFile.equals(username)) {
                    contacts.add(contact);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    public String[] readFromLog(String filename, String startTime, String endTime) {
        ArrayList<String> filteredRows = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalTime startLocalTime = LocalTime.parse(startTime);
        LocalTime endLocalTime = LocalTime.parse(endTime);

        String filepath = getFilesFolderPath() + File.separator + filename;
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String timestampString = line.substring(0, 19);
                LocalDateTime timestamp = LocalDateTime.parse(timestampString, formatter);
                LocalTime rowTime = timestamp.toLocalTime();
                if (!rowTime.isBefore(startLocalTime) && !rowTime.isAfter(endLocalTime)) {
                    filteredRows.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filteredRows.toArray(new String[0]);
    }

    public void writeToFile(String filename, String line){
        try{
            String filepath = getFilesFolderPath() + "\\" + filename;
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, true));
            writer.write(line);
            writer.newLine();
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public String getFilesFolderPath(){
        String directory = null;
        try{
            directory = String.valueOf(new File(new File(".").getCanonicalPath() + "\\UP_DA343A\\files"));
        } catch (IOException e){
            e.printStackTrace();
        }
        return directory;
    }
}
