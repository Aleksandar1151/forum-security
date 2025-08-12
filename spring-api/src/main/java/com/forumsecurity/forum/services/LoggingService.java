package com.forumsecurity.forum.services;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class LoggingService {

    private static final String LOG_FILE_PATH = "Logs.txt";

    public void log(String data) {
        try {
            // Check if the file exists, and create it if it doesn't
            File logFile = new File(LOG_FILE_PATH);
            if (!logFile.exists()) {
                logFile.createNewFile(); // Create the file if it does not exist
            }

            // Append the log to the file
            try (FileWriter fileWriter = new FileWriter(logFile, true)) { // true for append mode
                fileWriter.write(data + System.lineSeparator()); // Add a newline after each log
            }

        } catch (IOException e) {
            e.printStackTrace(); // Log the exception (could be enhanced to log to console or another file)
        }
    }
}
