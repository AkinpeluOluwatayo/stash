package enterprise.elroi.controllers;

import enterprise.elroi.services.media.MediaInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/stash/media")
// Note: Global CORS in SecurityConfig handles this now
public class MediaController {

    @Autowired
    private MediaInterface mediaService;

    @GetMapping("/signature")
    public ResponseEntity<Map<String, Object>> generateUploadSignature(
            @RequestParam String folder,
            @RequestParam String ownerId) {
        // Log the hit to ensure the request is reaching the controller
        System.out.println("Generating signature for owner: " + ownerId);

        Map<String, Object> signature = mediaService.generateUploadSignature(folder, ownerId);
        return ResponseEntity.ok(signature);
    }
}