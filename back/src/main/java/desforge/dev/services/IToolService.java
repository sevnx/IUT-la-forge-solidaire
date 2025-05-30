package desforge.dev.services;

import desforge.dev.entities.User;
import desforge.dev.errors.borrow_request.BorrowRequestAlreadyExistsException;
import desforge.dev.errors.tools.ToolNotExistsException;
import desforge.dev.models.borrow_request.CreateBorrowRequest;
import desforge.dev.models.tools.CreateToolRequest;
import desforge.dev.models.tools.ToolBorrowRequest;
import desforge.dev.models.tools.ToolResponse;
import desforge.dev.models.user.ToolUserResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IToolService {

    void createTool(@Valid CreateToolRequest request, User user);
    void createBorrowRequest(int idTool, CreateBorrowRequest createborrowRequest, User user)
            throws ToolNotExistsException, BorrowRequestAlreadyExistsException;
    List<ToolUserResponse> getUserTools(User user);

    List<ToolResponse> getAllTools(Authentication authentication);

    List<ToolBorrowRequest> getBorrowRequestsByTool(int toolId,User user);
}
