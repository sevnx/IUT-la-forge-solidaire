package desforge.dev.services;

import desforge.dev.entities.BorrowRequest;
import desforge.dev.entities.Tool;
import desforge.dev.entities.User;
import desforge.dev.enumerations.BorrowRequestState;
import desforge.dev.errors.BorrowRequestAlreadyExistsException;
import desforge.dev.errors.ToolNotExistsException;
import desforge.dev.models.tools.CreateBorrowRequest;
import desforge.dev.models.tools.CreateToolRequest;
import desforge.dev.repositories.BorrowRequestRepository;
import desforge.dev.repositories.ToolRepository;
import desforge.dev.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;


import java.io.File;
import java.util.Date;
import java.util.UUID;

@Service
public class ToolService implements IToolService {

    @Autowired
    private ToolRepository toolRepository;

    @Autowired
    private BorrowRequestRepository borrowRequestRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void createTool(@Valid CreateToolRequest request, Authentication authentication) throws ToolNotExistsException {
        String imgPath = request.getImagePath();
        int dotIndex = imgPath.lastIndexOf(".");
        String extension = imgPath.substring(dotIndex + 1);
        if (!extension.equals("png") && !extension.equals("jpg") && !extension.equals("jpeg")) {
            throw new IllegalArgumentException();
        }
        File file = new File(imgPath);
        if(!file.exists() || file.isDirectory()) {
            throw new IllegalArgumentException();
        }

        User user = (User) authentication.getPrincipal();
        Tool tool = new Tool();
        tool.setPhoto(request.getImagePath());
        tool.setDescription(request.getDescription());
        tool.setName(request.getName());
        tool.setOwner(user);
        toolRepository.save(tool);
    }

    @Override
    public void createBorrowRequest(int idTool, @Valid CreateBorrowRequest createborrowRequest, User user)
            throws ToolNotExistsException, BorrowRequestAlreadyExistsException {
        Tool tool = toolRepository.findById(idTool).orElseThrow(() -> new ToolNotExistsException("Tool not found"));
        Date date = createborrowRequest.getReturnDate();
        if (date.before(new Date())) {
            throw new IllegalArgumentException();
        }

        if (!(userRepository.findByIdUser(user.getIdUser()).getBorrowRequests().size() == 0)) {
            throw new BorrowRequestAlreadyExistsException("User already has a pending borrow request");
        }

        if (user.equals(tool.getOwner())) {
            throw new BorrowRequestAlreadyExistsException();
        }

        BorrowRequest borrowRequest = new BorrowRequest();
        borrowRequest.setRequestDate(new Date());
        borrowRequest.setUserBorrowRequest(user);
        borrowRequest.setToolBorrowRequest(tool);
        borrowRequest.setReturnDate(createborrowRequest.getReturnDate());
        borrowRequest.setState(BorrowRequestState.PENDING);
        borrowRequestRepository.save(borrowRequest);
    }


}
