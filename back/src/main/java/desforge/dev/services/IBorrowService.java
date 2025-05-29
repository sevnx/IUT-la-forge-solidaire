package desforge.dev.services;

import desforge.dev.entities.User;
import desforge.dev.models.user.BorrowUserResponse;

import java.util.List;

public interface IBorrowService {
    public List<BorrowUserResponse> getUserBorrow(User userBorrow);
}
