package desforge.dev.repositories;

import desforge.dev.entities.Borrow;
import desforge.dev.entities.Tool;
import desforge.dev.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BorrowRepository extends JpaRepository<Borrow, Integer> {
    List<Borrow> findBorrowByUserBorrow(User userBorrow);

    boolean existsByToolBorrow(Tool toolBorrow);

    Borrow findBytoolBorrow(Tool toolBorrow);

    List<Borrow> findByToolBorrow(Tool toolBorrow);
}
