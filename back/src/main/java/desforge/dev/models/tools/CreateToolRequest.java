package desforge.dev.models.tools;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public class CreateToolRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private MultipartFile imageSrc;

    public CreateToolRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(MultipartFile imageSrc) {
        this.imageSrc = imageSrc;
    }
}
