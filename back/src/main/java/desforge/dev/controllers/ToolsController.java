package desforge.dev.controllers;

import desforge.dev.entities.Tool;
import desforge.dev.entities.User;
import desforge.dev.errors.BorrowRequestAlreadyExistsException;
import desforge.dev.errors.ToolNotExistsException;
import desforge.dev.models.tools.CreateBorrowRequest;
import desforge.dev.models.tools.CreateToolRequest;
import desforge.dev.services.IToolService;
import desforge.dev.services.ToolService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/tools")
public class ToolsController {

    @Autowired
    private IToolService toolsService;

    @GetMapping(value = "")
    public String getTools() {
        return "test";
    }

    @PostMapping(value = "/{toolId}/borrow-requests", produces = "application/json")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> createBorrowRequest(@PathVariable("toolId") int toolId, @RequestBody CreateBorrowRequest createBorrowRequest,
                                                    Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        try {
            toolsService.createBorrowRequest(toolId, createBorrowRequest, user);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ToolNotExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (BorrowRequestAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping(value = "", produces = "application/json")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> createTool(@RequestBody CreateToolRequest request, Authentication auth) {
        try {
            toolsService.createTool(request, auth);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(value = "/{toolId}/borrow-requests", produces = "application/json")
    @PreAuthorize("isAuthenticated()")
    public String getBorrowRequest(@PathVariable("toolId") int toolId) {
        return null;
    }
}
