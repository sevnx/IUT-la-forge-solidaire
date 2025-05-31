package desforge.dev.models.tools;

import java.util.Date;

public class ToolResponse {
    private int idTool;
    private String name;
    private String description;
    private String imageUrl;
    private String address;
    private Date availableAt;

    public int getIdTool() {
        return idTool;
    }

    public void setIdTool(int idTool) {
        this.idTool = idTool;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getAvailableAt() {
        return availableAt;
    }

    public void setAvailableAt(Date availableAt) {
        this.availableAt = availableAt;
    }


}
