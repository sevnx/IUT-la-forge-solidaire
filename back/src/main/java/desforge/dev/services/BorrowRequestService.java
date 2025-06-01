package desforge.dev.services;

import desforge.dev.entities.Borrow;
import desforge.dev.entities.BorrowRequest;
import desforge.dev.entities.User;
import desforge.dev.enumerations.BorrowRequestState;
import desforge.dev.errors.borrow_request.BorrowRequestNotFoundException;
import desforge.dev.errors.borrow_request.BorrowRequestNotPendingException;
import desforge.dev.errors.borrow_request.NotAllowedStateBorrowRequestException;
import desforge.dev.models.user.UserBorrowRequest;
import desforge.dev.repositories.BorrowRepository;
import desforge.dev.repositories.BorrowRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BorrowRequestService implements IBorrowRequestService {

    @Autowired
    private BorrowRequestRepository borrowRequestRepository;
    @Autowired
    private BorrowRepository borrowRepository;

    public void setBorrowRequestStatus(int idRequest, BorrowRequestState status, User user) {
        BorrowRequest borrowRequest = borrowRequestRepository.findByIdRequest(idRequest)
                .orElseThrow(() -> new BorrowRequestNotFoundException("Borrow request not found"));
        if (!borrowRequest.getToolBorrowRequest().getOwner().getIdUser().equals(user.getIdUser())) {
            throw new NotAllowedStateBorrowRequestException("User not allowed to change the state of this borrow request");
        }
        if (borrowRequest.getState() == BorrowRequestState.ACCEPTED && status == BorrowRequestState.REFUSED) {
            throw new IllegalArgumentException("Cannot change state from ACCEPTED to REFUSED");
        }

        if (borrowRequest.getState() != BorrowRequestState.PENDING) {
            throw new BorrowRequestNotPendingException("Borrow request not pending");
        }

        List<BorrowRequest> borrowRequests = borrowRequestRepository.findByToolBorrowRequest(borrowRequest.getToolBorrowRequest());
        for (BorrowRequest request : borrowRequests) {
            request.setState(BorrowRequestState.REFUSED);
        }
        borrowRequest.setState(status);
        if (borrowRequest.getState() == BorrowRequestState.ACCEPTED) {
            Borrow borrow = new Borrow();
            borrow.setUserBorrow(borrowRequest.getUserBorrowRequest());
            borrow.setToolBorrow(borrowRequest.getToolBorrowRequest());
            borrow.setDateBorrow(new Date());
            borrow.setDateReturn(borrowRequest.getReturnDate());

            borrowRepository.save(borrow);
        }
        borrowRequestRepository.save(borrowRequest);
    }

    public List<UserBorrowRequest> getBorrowRequestsByUser(User user, BorrowRequestState state) {
        List<BorrowRequest> borrowRequests = borrowRequestRepository.findByUserBorrowRequestAndState(user, state);
        return borrowRequests.stream().map(borrowRequest -> {
            UserBorrowRequest response = new UserBorrowRequest();
            response.setName(borrowRequest.getToolBorrowRequest().getName());
            response.setDescription(borrowRequest.getToolBorrowRequest().getDescription());
            response.setImageSrc(borrowRequest.getToolBorrowRequest().getImageSrc());
            response.setDateRequest(borrowRequest.getRequestDate());
            return response;
        }).toList();
    }
}
