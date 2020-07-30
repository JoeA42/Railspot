package com.btp.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.JSONParser;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

/**
 * This class is takes a Generic value of any kind, an can take that class and write it to a file of .json .txt extension
 * @param <T> Generic value of any kind
 */
public class DataWriter<T>{

    /**
     * This method takes an object and writes it to a .json file or .txt file specified by a string called path
     * @param data Generic type of obj
     * @param path The path to the .json file, or Txt file it needs to be an existing file
     */
    public void writeData(T data, String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter stringWriter = new StringWriter();
        JSONParser jsonParser = new JSONParser();

        String dataString;

        try {
            dataString = objectMapper.writeValueAsString(data);
            String absolutePath = new File(path).getAbsolutePath();
            FileWriter file = new FileWriter(absolutePath);
            file.write(dataString);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
