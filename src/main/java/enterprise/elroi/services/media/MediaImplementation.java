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

            Map<String, Object> params = new HashMap<>();
            params.put("timestamp", timestamp);
            params.put("folder", "elroi/" + folder + "/" + ownerId);

            String signature = cloudinary.apiSignRequest(params, apiSecret);

            Map<String, Object> result = new HashMap<>();
            result.put("signature", signature);
            result.put("timestamp", timestamp);
            result.put("api_key", apiKey);
            result.put("folder", "elroi/" + folder + "/" + ownerId);
            result.put("cloud_name", cloudinary.config.cloudName);

            return result;
        } catch (Exception e) {
            throw new FailedToGenerateSignatureException("Failed to generate signature: " + e.getMessage());
        }
    }
}