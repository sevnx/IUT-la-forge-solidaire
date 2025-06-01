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
