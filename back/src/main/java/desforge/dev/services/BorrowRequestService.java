package desforge.dev.services;

import desforge.dev.entities.BorrowRequest;
import desforge.dev.entities.User;
import desforge.dev.enumerations.BorrowRequestState;
import desforge.dev.models.user.BorrowRequestUserResponse;
import desforge.dev.repositories.BorrowRequestRepository;
import desforge.dev.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowRequestService implements IBorrowRequestService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BorrowRequestRepository borrowRequestRepository;

    public void setBorrowRequestStatus(int idRequest, BorrowRequestState status) {
        BorrowRequest borrowRequest = borrowRequestRepository.findByidRequest(idRequest)
                .orElseThrow(() -> new IllegalArgumentException("Borrow request not found"));
        List<BorrowRequest> borrowRequests = borrowRequestRepository.findBytoolBorrowRequest(borrowRequest.getToolBorrowRequest());
        for (BorrowRequest request : borrowRequests) {
            request.setState(BorrowRequestState.REFUSED);
        }
        borrowRequest.setState(status);
        borrowRequestRepository.save(borrowRequest);
    }

    public List<BorrowRequestUserResponse> getBorrowRequestsByUser(User user, BorrowRequestState state) {
        List<BorrowRequest> borrowRequests = borrowRequestRepository.findByUserBorrowRequestAndState(user, state);
        return borrowRequests.stream().map(borrowRequest -> {
            BorrowRequestUserResponse response = new BorrowRequestUserResponse();
            response.setName(borrowRequest.getToolBorrowRequest().getName());
            response.setDescription(borrowRequest.getToolBorrowRequest().getDescription());
            response.setImage(borrowRequest.getToolBorrowRequest().getPhoto());
            return response;
        }).toList();
    }
}
