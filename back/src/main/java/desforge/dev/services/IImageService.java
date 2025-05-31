package desforge.dev.services;

import org.springframework.web.multipart.MultipartFile;

public interface IImageService {

    String storeFile(MultipartFile file);
}
