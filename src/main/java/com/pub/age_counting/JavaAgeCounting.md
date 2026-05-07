# Java Age Counting
## Requirement 
In the Java file, write a program to perform a GET request on the route https://coderbyte.com/api/challenges/json/age-counting which contains a data key, and the value is a format: key=STRING, age=INTEGER.
Your goal is to count how many items exist that have an age equal to or greater than 50 and print this final value

## Example
### Input
```json
{"data": "key=IAfpK, age=58, key=WNVdi, age=64, key=jp9zt, age=47"}
```

### Output
```2```

### Given
```java
import java.util.*;
import java.io.*;
import java.net.*;
import java.util.regex.*;

class Main {
    public static void main(String[] args) {
        System.setProperty("http.agent", "Chrome");
        try {
            URI uri = new URI("https://coderbyte.com/api/challenges/json/age-counting");
            URL url = uri.toURL();
            // Perform connection
            URLConnection connection = url.openConnection();
            // Implementation here
        } catch (MalformedURLException | URISyntaxException malEx) {
            System.out.println(malEx.getMessage());
        }
    }
}
```