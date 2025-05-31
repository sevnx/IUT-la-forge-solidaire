package desforge.dev.services;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

    String storeFile(MultipartFile file);
}
