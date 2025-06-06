package desforge.dev.services;

import desforge.dev.entities.User;
import desforge.dev.errors.borrow_request.BorrowRequestAlreadyExistsException;
import desforge.dev.errors.tools.ToolAlreadyBorrowedException;
import desforge.dev.errors.tools.ToolNotExistsException;
import desforge.dev.models.borrow_request.BorrowRequestCreate;
import desforge.dev.models.tools.ToolBorrowRequest;
import desforge.dev.models.tools.Tool;
import desforge.dev.models.user.UserTool;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IToolService {

    void createTool(User user, String name, String description, MultipartFile image);

    void createBorrowRequest(int idTool, BorrowRequestCreate createborrowRequest, User user)
            throws ToolNotExistsException, BorrowRequestAlreadyExistsException, ToolAlreadyBorrowedException;

    List<UserTool> getUserTools(User user);

    List<Tool> getAllTools(Authentication authentication);

    List<ToolBorrowRequest> getBorrowRequestsByTool(int toolId, User user);
}
