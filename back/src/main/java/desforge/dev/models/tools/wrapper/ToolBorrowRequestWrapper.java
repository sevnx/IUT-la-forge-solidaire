package desforge.dev.models.tools.wrapper;

import desforge.dev.models.tools.ToolBorrowRequest;

import java.util.List;

public class ToolBorrowRequestWrapper {

    private List<ToolBorrowRequest> borrowRequests;

    public List<ToolBorrowRequest> getBorrowRequests() {
        return borrowRequests;
    }

    public void setBorrowRequests(List<ToolBorrowRequest> borrowRequests) {
        this.borrowRequests = borrowRequests;
    }

}
