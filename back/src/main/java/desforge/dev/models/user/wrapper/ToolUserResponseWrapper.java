package desforge.dev.models.user.wrapper;

import desforge.dev.models.user.ToolUserResponse;

import java.util.List;

public class ToolUserResponseWrapper {
    private List<ToolUserResponse> data;

    public List<ToolUserResponse> getData() {
        return data;
    }

    public void setData(List<ToolUserResponse> data) {
        this.data = data;
    }
}
