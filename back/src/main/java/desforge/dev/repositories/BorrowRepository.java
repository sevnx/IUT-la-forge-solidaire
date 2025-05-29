package desforge.dev.repositories;

import desforge.dev.entities.Borrow;
import desforge.dev.entities.Tool;
import desforge.dev.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowRepository extends JpaRepository<Borrow, Integer> {
    List<Borrow> findBorrowByuserBorrow(User userBorrow);
    boolean existsBytoolBorrow(Tool toolBorrow);

    Borrow findBytoolBorrow(Tool toolBorrow);
}
