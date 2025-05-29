package desforge.dev.repositories;

import desforge.dev.entities.BorrowRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRequestRepository extends JpaRepository<BorrowRequest, Integer> {

}
