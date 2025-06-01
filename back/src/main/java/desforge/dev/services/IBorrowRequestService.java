package desforge.dev.services;

import desforge.dev.entities.User;
import desforge.dev.enumerations.BorrowRequestState;
import desforge.dev.models.user.BorrowRequestUserResponse;

import java.util.List;

public interface IBorrowRequestService {
    void setBorrowRequestStatus(int idRequest, BorrowRequestState status, User user);

    List<BorrowRequestUserResponse> getBorrowRequestsByUser(User user, BorrowRequestState state);
}
