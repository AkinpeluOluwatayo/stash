package enterprise.elroi.services.media;

import java.util.Map;

public interface MediaInterface {

    Map<String, Object> generateUploadSignature(String folder, String ownerId);
}