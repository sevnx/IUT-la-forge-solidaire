package desforge.dev.controllers;


import desforge.dev.entities.User;
import desforge.dev.models.auth.RegisterRequest;
import desforge.dev.models.borrow_request.BorrowRequestsStateRequest;
import desforge.dev.services.IBorrowRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrow-requests")
public class BorrowRequestController {
    @Autowired
    IBorrowRequestService borrowRequestService;
    @PutMapping(value = "/{requestId}", produces = "application/json")
    @PreAuthorize("isAuthenticated()")
    public void borrowRequestResponse(@PathVariable("requestId") int requestId, Authentication authentication,
                                      @Valid @RequestBody BorrowRequestsStateRequest registerRequest) {
        borrowRequestService.setBorrowRequestStatus(requestId, registerRequest.getState());
    }
}
