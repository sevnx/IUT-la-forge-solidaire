package desforge.dev.models.tools;

import jakarta.validation.constraints.NotBlank;

public class CreateToolRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String imagePath;

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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
