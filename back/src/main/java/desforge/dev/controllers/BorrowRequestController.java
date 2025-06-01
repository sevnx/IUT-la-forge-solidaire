package desforge.dev.controllers;


import desforge.dev.entities.User;
import desforge.dev.errors.borrow_request.BorrowRequestNotFoundException;
import desforge.dev.errors.borrow_request.BorrowRequestNotPendingException;
import desforge.dev.errors.borrow_request.NotAllowedStateBorrowRequestException;
import desforge.dev.models.borrow_request.BorrowRequestsUpdateState;
import desforge.dev.models.error.ErrorResponse;
import desforge.dev.services.IBorrowRequestService;
import desforge.dev.services.IErrorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrow-requests")
public class BorrowRequestController {
    @Autowired
    IBorrowRequestService borrowRequestService;
    @Autowired
    IErrorService errorService;

    @PutMapping(value = "/{requestId}", produces = "application/json")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Update borrow request state",
            description = "Updates the state of a borrow request.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Borrow request state update data",
                    content = @Content(schema = @Schema(implementation = BorrowRequestsUpdateState.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Borrow request state updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Borrow request not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Not allowed to change the state of this borrow request",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Not allowed to change the state of this borrow request",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "409", description = "Borrow request not pending",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public ResponseEntity<?> borrowRequestResponse(@PathVariable("requestId") int requestId, Authentication authentication,
                                                   @Valid @RequestBody BorrowRequestsUpdateState borrowRequestState) {
        try {
            User user = (User) authentication.getPrincipal();
            borrowRequestService.setBorrowRequestStatus(requestId, borrowRequestState.getState(), user);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (BorrowRequestNotFoundException e) {
            return errorService.buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (NotAllowedStateBorrowRequestException e) {
            return errorService.buildErrorResponse(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (IllegalArgumentException e) {
            return errorService.buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (BorrowRequestNotPendingException e) {
            return errorService.buildErrorResponse(HttpStatus.CONFLICT, e.getMessage());
        }
    }
}
