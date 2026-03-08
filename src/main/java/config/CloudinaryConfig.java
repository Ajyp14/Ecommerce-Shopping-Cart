package config;

import com.cloudinary.Cloudinary;
import java.util.HashMap;
import java.util.Map;

public class CloudinaryConfig {

    private static Cloudinary cloudinary;

    static {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dlplu1hqw");
        config.put("api_key", "981261735194399");
        config.put("api_secret", "N52hZGqy3P9QU8zpAz6jtzJ6gNc");

        cloudinary = new Cloudinary(config);
    }

    public static Cloudinary getCloudinary() {
        return cloudinary;
    }
}