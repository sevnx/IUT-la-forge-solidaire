package desforge.dev.controllers;


import desforge.dev.entities.User;
import desforge.dev.enumerations.BorrowRequestState;
import desforge.dev.models.error.ErrorResponse;
import desforge.dev.models.user.wrapper.BorrowRequestUserResponseWrapper;
import desforge.dev.models.user.wrapper.BorrowUserResponseWrapper;
import desforge.dev.models.user.wrapper.ToolUserResponseWrapper;
import desforge.dev.services.IBorrowRequestService;
import desforge.dev.services.IBorrowService;
import desforge.dev.services.IToolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IBorrowService borrowService;
    @Autowired
    private IBorrowRequestService borrowRequestService;
    @Autowired
    private IToolService toolService;

    @GetMapping(value = "/tools", produces = "application/json")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get user tools",
            description = "Returns a list of tools owned by the authenticated user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tools retrieved successfully")
            }
    )
    public ResponseEntity<ToolUserResponseWrapper> getUserTools(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        ToolUserResponseWrapper toolUserResponseWrapper = new ToolUserResponseWrapper();
        toolUserResponseWrapper.setData(toolService.getUserTools(user));
        return ResponseEntity.ok(toolUserResponseWrapper);
    }

    @GetMapping(value = "/borrows", produces = "application/json")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get user borrows",
            description = "Returns a list of borrow records for the authenticated user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Borrows retrieved successfully")
            }
    )
    public ResponseEntity<BorrowUserResponseWrapper> getUserBorrows(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        BorrowUserResponseWrapper responseWrapper = new BorrowUserResponseWrapper();
        responseWrapper.setData(borrowService.getUserBorrow(user));
        return ResponseEntity.ok(responseWrapper);
    }

    @GetMapping(value = "/borrow-requests", produces = "application/json")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get user pending borrow requests",
            description = "Returns a list of pending borrow requests for the authenticated user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pending borrow requests retrieved successfully")
            }
    )
    public ResponseEntity<BorrowRequestUserResponseWrapper> getUserPendingBorrowRequests
            (Authentication authentication,
             @RequestParam(required = false, defaultValue = "PENDING") BorrowRequestState state) {
        User user = (User) authentication.getPrincipal();
        BorrowRequestUserResponseWrapper responseWrapper = new BorrowRequestUserResponseWrapper();
        responseWrapper.setData(borrowRequestService.getBorrowRequestsByUser(user, state));
        return ResponseEntity.ok(responseWrapper);
    }

    @GetMapping(value = "")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Check Authentification",
            description = "Checks if the user is authenticated",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The user is authenticated"),
                    @ApiResponse(responseCode = "401", description = "The user is not authenticated",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public ResponseEntity<?> checkAuthentification() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
