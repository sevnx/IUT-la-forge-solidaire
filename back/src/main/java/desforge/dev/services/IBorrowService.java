package desforge.dev.services;

import desforge.dev.entities.User;
import desforge.dev.models.user.UserBorrow;

import java.util.List;

public interface IBorrowService {
    List<UserBorrow> getUserBorrow(User userBorrow);
}
