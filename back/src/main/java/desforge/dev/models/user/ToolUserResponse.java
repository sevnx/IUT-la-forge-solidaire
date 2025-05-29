package desforge.dev.models.user;

import java.util.Date;
import java.util.Optional;

public class ToolUserResponse {
    private int toolNumber;

    private String toolName;
    private String description;
    private String image;

    private Date availableAt;

    public int getToolNumber() {
        return toolNumber;
    }

    public void setToolNumber(int toolNumber) {
        this.toolNumber = toolNumber;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getAvailableAt() {
        return availableAt;
    }

    public void setAvailableAt(Date availableAt) {
        this.availableAt = availableAt;
    }


}
