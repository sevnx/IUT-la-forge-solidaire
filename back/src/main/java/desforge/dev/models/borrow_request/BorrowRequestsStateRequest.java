package desforge.dev.models.borrow_request;

import desforge.dev.enumerations.BorrowRequestState;

public class BorrowRequestsStateRequest {
    private BorrowRequestState state;

    public BorrowRequestState getState() {
        return state;
    }

    public void setState(BorrowRequestState state, boolean validate) {
        if (state != BorrowRequestState.ACCEPTED && state != BorrowRequestState.REFUSED) {
            throw new IllegalArgumentException("State must be ACCEPTED or REFUSED");
        }
        this.state = state;
    }
}
