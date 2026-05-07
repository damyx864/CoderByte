package com.pub.age_counting;

import java.util.*;
import java.io.*;
import java.net.*;
import java.util.regex.*;

class JavaAgeCountingQwen {
    static void main(String[] args) {
        System.setProperty("http.agent", "Chrome");
        try {
            URI uri = new URI("https://coderbyte.com/api/challenges/json/age-counting");
            URL url = uri.toURL();
            // Perform connection
            URLConnection connection = url.openConnection();

            // Read the response using streams
            String jsonResponse = new BufferedReader(new InputStreamReader(connection.getInputStream()))
                    .lines()
                    .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                    .toString();

            // Parse and count ages >= 50 using streams
            Pattern pattern = Pattern.compile("age=(\\d+)");
            Matcher matcher = pattern.matcher(jsonResponse);

            long count = matcher.results()
                    .mapToLong(m -> Integer.parseInt(m.group(1)))
                    .filter(age -> age >= 50)
                    .count();

            System.out.println(count);
        } catch (MalformedURLException | URISyntaxException malEx) {
            System.out.println(malEx.getMessage());
        } catch (IOException ioEx) {
            System.out.println("Error reading from URL: " + ioEx.getMessage());
        }
    }
}
