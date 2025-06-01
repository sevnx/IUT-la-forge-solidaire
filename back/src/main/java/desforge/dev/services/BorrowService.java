package desforge.dev.services;

import desforge.dev.entities.User;
import desforge.dev.models.user.UserBorrow;
import desforge.dev.repositories.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowService implements IBorrowService {
    @Autowired
    private BorrowRepository borrowRepository;

    public List<UserBorrow> getUserBorrow(User userBorrow) {
        return borrowRepository.findBorrowByUserBorrow(userBorrow)
                .stream()
                .map(borrow -> {
                    UserBorrow response = new UserBorrow();
                    response.setName(borrow.getToolBorrow().getName());
                    response.setDescription(borrow.getToolBorrow().getDescription());
                    response.setImageSrc(borrow.getToolBorrow().getImageSrc());
                    response.setDateReturn(borrow.getDateReturn());
                    response.setDateRequest(borrow.getDateBorrow());
                    return response;
                })
                .toList();
    }
}
