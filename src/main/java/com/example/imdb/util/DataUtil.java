package com.example.imdb.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

public class DataUtil {

    public static Stream<String> readGzipFile(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new GZIPInputStream(new FileInputStream(filePath)), StandardCharsets.UTF_8)
            );
            return reader.lines().skip(1); // Skip header
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + filePath, e);
        }
    }

    public static int parseSafeInt(String value) {
        try {
            return value.equals("\\N") ? 0 : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static double parseSafeDouble(String value) {
        try {
            return value.equals("\\N") ? 0.0 : Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

}
