package desforge.dev.controllers;

import desforge.dev.entities.BorrowRequest;
import desforge.dev.entities.User;
import desforge.dev.enumerations.BorrowRequestState;
import desforge.dev.models.user.BorrowRequestUserResponse;
import desforge.dev.models.user.BorrowUserResponse;
import desforge.dev.models.user.ToolUserResponse;
import desforge.dev.services.IBorrowRequestService;
import desforge.dev.services.IBorrowService;
import desforge.dev.services.IToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UsersControllers {

    @Autowired
    private IBorrowService borrowService;
    private IBorrowRequestService borrowRequestService;

    private IToolService toolService;

    @GetMapping(value = "/tools", produces = "application/json")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String,List<ToolUserResponse>>> getUserTools(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(Map.of("data", toolService.getUserTools(user)));
    }

    @GetMapping(value = "/borrows", produces = "application/json")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String,List<BorrowUserResponse>>> getUserBorrows(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(Map.of("data", borrowService.getUserBorrow(user)));
    }

    @GetMapping(value = "/borrow-requests", produces = "application/json")
    public ResponseEntity<Map<String,List<BorrowRequestUserResponse>>> getUserPendingBorrowRequests
            (Authentication authentication,
            @RequestParam(required = false, defaultValue = "PENDING") BorrowRequestState state) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(Map.of("data", borrowRequestService.getBorrowRequestsByUser(user, state)));
    }
}
