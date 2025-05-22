package desforge.dev.controllers;

import desforge.dev.entities.BorrowRequest;
import desforge.dev.enumerations.BorrowRequestState;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersControllers {

    @GetMapping(value = "/{userId}/tools", produces = "application/json")
    public String getUserTools(@PathVariable("userId") int userId) {
        return null;
    }

    @GetMapping(value = "/{userId}/borrows", produces = "application/json")
    public String getUserBorrows(@PathVariable("userId") int userId) {
        return null;
    }

    @GetMapping(value = "/{userId}/borrow-requests", produces = "application/json")
    public String getUserPendingBorrowRequests
            (@PathVariable("userId") int userId,
            @RequestParam(required = false, defaultValue = "PENDING") BorrowRequestState state) {
        return null;
    }

}
