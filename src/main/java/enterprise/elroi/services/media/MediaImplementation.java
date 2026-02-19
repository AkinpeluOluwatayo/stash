package enterprise.elroi.services.media;

import com.cloudinary.Cloudinary;
import enterprise.elroi.exceptions.media.FailedToGenerateSignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MediaImplementation implements MediaInterface {

    @Autowired
    private Cloudinary cloudinary;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Override
    public Map<String, Object> generateUploadSignature(String folder, String ownerId) {
        try {
            long timestamp = System.currentTimeMillis() / 1000;
            String folderPath = "elroi/" + folder + "/" + ownerId;

            Map<String, Object> params = new HashMap<>();
            params.put("timestamp", timestamp);
            params.put("folder", folderPath);

            // Generate signature
            String signature = cloudinary.apiSignRequest(params, apiSecret);

            Map<String, Object> result = new HashMap<>();
            result.put("signature", signature);
            result.put("timestamp", timestamp);
            result.put("apiKey", apiKey);        // Changed to camelCase
            result.put("folder", folderPath);
            result.put("cloudName", cloudinary.config.cloudName); // Changed to camelCase

            return result;
        } catch (Exception e) {
            throw new FailedToGenerateSignatureException("Failed to generate signature: " + e.getMessage());
        }
    }
}