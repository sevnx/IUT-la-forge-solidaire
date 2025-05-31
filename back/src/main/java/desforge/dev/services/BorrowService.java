package desforge.dev.services;

import desforge.dev.entities.User;
import desforge.dev.models.user.BorrowUserResponse;
import desforge.dev.repositories.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowService implements IBorrowService {
    @Autowired
    private BorrowRepository borrowRepository;

    public List<BorrowUserResponse> getUserBorrow(User userBorrow) {
        return borrowRepository.findBorrowByuserBorrow(userBorrow)
                .stream()
                .map(borrow -> {
                    BorrowUserResponse response = new BorrowUserResponse();
                    response.setToolName(borrow.getToolBorrow().getName());
                    response.setDescription(borrow.getToolBorrow().getDescription());
                    response.setImage(borrow.getToolBorrow().getPhoto());
                    response.setDateReturn(borrow.getDateReturn());
                    return response;
                })
                .toList();
    }
}
