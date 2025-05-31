package desforge.dev.controllers;

import desforge.dev.models.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

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
