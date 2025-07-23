package com.pub.age_counting;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class JavaAgeCounting {

    public static void main(String[] args) {
        System.setProperty("http.agent", "Chrome");
        try {
            URI uri = new URI("https://coderbyte.com/api/challenges/json/age-counting");
            URL url = uri.toURL();
            try {
                // Perform connection
                URLConnection connection = url.openConnection();
                // Acquire input stream
                InputStream responseInputStream = connection.getInputStream();
                // Parse input data and get the age input as List
                var ageList = parseInputToJson(responseInputStream, "data", "UTF-8");
                // Traverse the list and provide the final answer
                int nrOfPeopleOver50 = countItemsOverThreshold(ageList, 50, true);
                System.out.println(nrOfPeopleOver50);
                assert nrOfPeopleOver50 == 128 : "Wrong result";
            } catch (AssertionError | IOException ae) {
                System.out.println(ae.getMessage());
            }
        } catch (MalformedURLException | URISyntaxException malEx) {
            System.out.println(malEx.getMessage());
        }
    }

    private static List<Integer> parseInputToJson(InputStream rawDataInputStream, String objectName, String charSet) throws IOException {
        // Create the reader from the input stream's response
        try (Reader reader = new InputStreamReader(rawDataInputStream, charSet)) {
            // Extract the 'data' field value from the JSON input
            JsonObject jsonObj = new Gson().fromJson(reader, JsonObject.class);
            String data = jsonObj.get(objectName).toString();
            reader.close();

            // Parse the data field into a List and return it
            return extractIntegers(data);
        }
    }

    private static List<Integer> extractIntegers(String inputData) {
        if (!Objects.isNull(inputData) && !inputData.isBlank()) {
            // Pattern to identify the field with age
            Pattern agePattern = Pattern.compile("age=(\\d+)", Pattern.CASE_INSENSITIVE);
            // Pattern to extract the age num
            Pattern ageNumberPattern = Pattern.compile("(\\d+)");
            // Split the data by comma first taking white spaces into consideration
            return Arrays.stream(inputData.split(",\\s+")).parallel()
                    // Keep only the age entries
                    .filter(x -> agePattern.matcher(x).matches())
                    // Find the age number as String from each entry as Match
                    .map(y -> ageNumberPattern.matcher(y).results().findFirst())
                    // Test it exists
                    .filter(Optional::isPresent)
                    // Extract the age number as string and convert it to Integer
                    .map(z -> Integer.valueOf(z.get().group(1)))
                    // Return the List
                    .toList();
        } else {
            return List.of();
        }
    }

    private static int countItemsOverThreshold(List<Integer> itemList, int threshold, boolean inclusive) {
        return itemList.parallelStream()
                .map(n -> n >= threshold && inclusive || n > threshold ? 1 : 0)
                .reduce(0, Integer::sum);
    }
}