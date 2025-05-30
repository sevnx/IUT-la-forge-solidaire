package desforge.dev.models.user.wrapper;

import desforge.dev.models.user.BorrowUserResponse;

import java.util.List;

public class BorrowUserResponseWrapper {

    private List<BorrowUserResponse> data;
    public List<BorrowUserResponse> getData() {
        return data;
    }

    public void setData(List<BorrowUserResponse> data) {
        this.data = data;
    }



}
