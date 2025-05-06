package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class DatabaseUtils {

    // Method to generate a unique String ID with a prefix
    public static String generateUniqueId(String idPrefix) {
        // Get the current timestamp in the format "yyyyMMddHHmmssSSS"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String timestamp = sdf.format(new Date());

        // Generate a random UUID for added uniqueness
        String uuid = UUID.randomUUID().toString().substring(0, 8); // Use the first 8 characters of the UUID for brevity

        // Combine the prefix, timestamp, and UUID to create a unique ID
        return idPrefix + timestamp + "-" + uuid;
    }
}
