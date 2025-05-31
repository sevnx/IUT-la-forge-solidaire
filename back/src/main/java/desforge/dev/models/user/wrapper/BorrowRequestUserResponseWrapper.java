package desforge.dev.models.user.wrapper;

import desforge.dev.models.user.BorrowRequestUserResponse;

import java.util.List;

public class BorrowRequestUserResponseWrapper {

    private List<BorrowRequestUserResponse> data;
    public List<BorrowRequestUserResponse> getData() {
        return data;
    }

    public void setData(List<BorrowRequestUserResponse> data) {
        this.data = data;
    }


}
