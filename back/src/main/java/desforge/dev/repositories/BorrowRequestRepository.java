package desforge.dev.repositories;

import desforge.dev.entities.BorrowRequest;
import desforge.dev.entities.Tool;
import desforge.dev.entities.User;
import desforge.dev.enumerations.BorrowRequestState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BorrowRequestRepository extends JpaRepository<BorrowRequest, Integer> {
    Optional<BorrowRequest> findByidRequest(int idRequest);

    List<BorrowRequest> findBytoolBorrowRequest(Tool toolBorrowRequest);

    List<BorrowRequest> findBytoolBorrowRequestAndState(Tool toolBorrowRequest, BorrowRequestState state);

    List<BorrowRequest> findByUserBorrowRequestAndState(User userBorrowRequest, BorrowRequestState state);

}
