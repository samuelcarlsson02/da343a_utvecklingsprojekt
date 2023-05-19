package server.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {

    public ArrayList<String> readFromFile(String filename){
        ArrayList<String> file = new ArrayList<>();
        String line;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            while((line = reader.readLine()) != null){
                file.add(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return file;
    }

    public void writeToFile(String filename, String line){
        try{
            FileWriter writer = new FileWriter(filename);
            writer.write(line);
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
