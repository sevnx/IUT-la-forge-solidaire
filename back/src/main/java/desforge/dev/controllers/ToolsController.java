package desforge.dev.controllers;

import desforge.dev.entities.User;
import desforge.dev.errors.borrow.CannotBorrowOwnToolException;
import desforge.dev.errors.borrow_request.BorrowRequestAlreadyExistsException;
import desforge.dev.errors.tools.NotAllowedToGetBorrowRequestTool;
import desforge.dev.errors.tools.ToolAlreadyBorrowedException;
import desforge.dev.errors.tools.ToolNotExistsException;
import desforge.dev.models.borrow_request.BorrowRequestCreate;
import desforge.dev.models.error.ErrorResponse;
import desforge.dev.models.tools.ToolList;
import desforge.dev.services.IErrorService;
import desforge.dev.services.IToolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/tools")
public class ToolsController {

    @Autowired
    private IToolService toolsService;

    @Autowired
    private IErrorService errorService;

    @GetMapping(value = "")
    public ResponseEntity<ToolList> getTools(Authentication authentication) {
        ToolList toolResponseWrapper = new ToolList();
        toolResponseWrapper.setData(toolsService.getAllTools(authentication));
        return ResponseEntity.ok(toolResponseWrapper);
    }

    @PostMapping(value = "/{toolId}/borrow-requests", produces = "application/json")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Create a borrow request for a tool",
            description = "Creates a borrow request for the specified tool.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Borrow request data",
                    content = @Content(schema = @Schema(implementation = BorrowRequestCreate.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Borrow request created successfully"),
                    @ApiResponse(responseCode = "404", description = "Tool not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request data",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "409", description = "Borrow request already exists or tool is already borrowed",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Cannot borrow own tool",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public ResponseEntity<?> createBorrowRequest(@PathVariable("toolId") int toolId, @RequestBody BorrowRequestCreate createBorrowRequest,

                                                 Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        try {
            toolsService.createBorrowRequest(toolId, createBorrowRequest, user);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ToolNotExistsException e) {
            return errorService.buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            return errorService.buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (BorrowRequestAlreadyExistsException | ToolAlreadyBorrowedException e) {
            return errorService.buildErrorResponse(HttpStatus.CONFLICT, e.getMessage());
        } catch (CannotBorrowOwnToolException e) {
            return errorService.buildErrorResponse(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @PostMapping(value = "")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Create a new tool",
            description = "Creates a new tool and returns a success response.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Tool creation data",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = CreateToolForm.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tool created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public ResponseEntity<?> createTool(Authentication auth,
                                        @RequestParam String name,
                                        @RequestParam String description,
                                        @RequestParam MultipartFile image
    ) {
        try {
            User user = (User) auth.getPrincipal();
            toolsService.createTool(user, name, description, image);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalArgumentException e) {
            return errorService.buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping(value = "/{toolId}/borrow-requests", produces = "application/json")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get borrow requests for a tool",
            description = "Retrieves all borrow requests associated with the specified tool.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Borrow requests retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Tool not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Not allowed to get borrow requests for this tool",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public ResponseEntity<?> getBorrowRequest(@PathVariable("toolId") int toolId, Authentication auth) {
        try {
            User user = (User) auth.getPrincipal();
            return ResponseEntity.ok(Map.of("data", toolsService.getBorrowRequestsByTool(toolId, user)));
        } catch (ToolNotExistsException e) {
            return errorService.buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (NotAllowedToGetBorrowRequestTool e) {
            return errorService.buildErrorResponse(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @Schema(name = "ToolForm", description = "Form data for creating a tool")
    private static class CreateToolForm {
        @Schema(description = "Name of the tool")
        public String name;

        @Schema(description = "Description of the tool")
        public String description;

        @Schema(description = "Image file", type = "string", format = "binary")
        public MultipartFile image;
    }
}
