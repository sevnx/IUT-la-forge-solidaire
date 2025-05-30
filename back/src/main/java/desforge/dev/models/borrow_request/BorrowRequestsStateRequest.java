package desforge.dev.models.borrow_request;

import desforge.dev.enumerations.BorrowRequestState;
import io.swagger.v3.oas.annotations.media.Schema;

public class BorrowRequestsStateRequest {
    @Schema(
            description = "New state for the borrow request. Only 'ACCEPTED' or 'REFUSED' are allowed.",
            allowableValues = { "ACCEPTED", "REFUSED" }
    )
    private BorrowRequestState state;

    public BorrowRequestState getState() {
        return state;
    }

    public void setState(BorrowRequestState state) {
        if (state != BorrowRequestState.ACCEPTED && state != BorrowRequestState.REFUSED) {
            throw new IllegalArgumentException("State must be ACCEPTED or REFUSED");
        }
        this.state = state;
    }
}
