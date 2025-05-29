package desforge.dev.services;

import desforge.dev.errors.BorrowRequestAlreadyExistsException;
import desforge.dev.errors.ToolNotExistsException;
import desforge.dev.models.tools.CreateBorrowRequest;
import desforge.dev.models.tools.CreateToolRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

public interface IToolService {

    void createTool(@Valid CreateToolRequest request, Authentication authentication);
    void createBorrowRequest(int idTool, CreateBorrowRequest createborrowRequest, Authentication authentication)
            throws ToolNotExistsException, BorrowRequestAlreadyExistsException;
}
