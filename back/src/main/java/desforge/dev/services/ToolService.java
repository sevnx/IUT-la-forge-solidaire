package desforge.dev.services;

import desforge.dev.entities.BorrowRequest;
import desforge.dev.entities.Tool;
import desforge.dev.entities.User;
import desforge.dev.enumerations.BorrowRequestState;
import desforge.dev.errors.borrow_request.BorrowRequestAlreadyExistsException;
import desforge.dev.errors.borrow.CannotBorrowOwnToolException;
import desforge.dev.errors.tools.NotAllowedToGetBorrowRequestTool;
import desforge.dev.errors.tools.ToolAlreadyBorrowedException;
import desforge.dev.errors.tools.ToolNotExistsException;
import desforge.dev.models.borrow_request.CreateBorrowRequest;
import desforge.dev.models.tools.ToolBorrowRequest;
import desforge.dev.models.tools.ToolResponse;
import desforge.dev.models.user.ToolUserResponse;
import desforge.dev.repositories.BorrowRepository;
import desforge.dev.repositories.BorrowRequestRepository;
import desforge.dev.repositories.ToolRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class ToolService implements IToolService {

    @Autowired
    private ToolRepository toolRepository;

    @Autowired
    private BorrowRequestRepository borrowRequestRepository;

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private IImageService fileService;

    @Value("${image.url}")
    private String imageUrl;

    @Override
    public void createTool(User user, String name, String description, MultipartFile image) throws ToolNotExistsException {
        String imgPath = image.getOriginalFilename();
        if (imgPath == null) {
            throw new ToolNotExistsException("Image not found");
        }
        int dotIndex = imgPath.lastIndexOf(".");
        String extension = imgPath.substring(dotIndex + 1);
        if (!extension.equals("png") && !extension.equals("jpg") && !extension.equals("jpeg")) {
            throw new IllegalArgumentException("Invalid extension: " + extension);
        }

        String toolName = fileService.storeFile(image);

        Tool tool = new Tool();
        tool.setImageSrc(imageUrl+toolName);
        tool.setDescription(description);
        tool.setName(name);
        tool.setOwner(user);
        toolRepository.save(tool);
    }

    @Override
    public void createBorrowRequest(int idTool, @Valid CreateBorrowRequest createborrowRequest, User user)
            throws ToolNotExistsException, BorrowRequestAlreadyExistsException, ToolAlreadyBorrowedException {
        Tool tool = toolRepository.findById(idTool).orElseThrow(() -> new ToolNotExistsException("Tool not found"));
        Date date = createborrowRequest.getReturnDate();
        if (date.before(new Date())) {
            throw new IllegalArgumentException("Return date cannot be in the past");
        }

        if (!borrowRequestRepository.findByUserBorrowRequestAndState(user, BorrowRequestState.PENDING).isEmpty()) {
            throw new BorrowRequestAlreadyExistsException("User already has a pending borrow request");
        }

        if (user.getIdUser().equals(tool.getOwner().getIdUser())) {
            throw new CannotBorrowOwnToolException("User not allowed to borrow own tool");
        }

        if (borrowRepository.findBytoolBorrow(tool) != null) {
            throw new ToolAlreadyBorrowedException("This tool has already been borrowed");
        }
        BorrowRequest borrowRequest = new BorrowRequest();
        borrowRequest.setRequestDate(new Date());
        borrowRequest.setUserBorrowRequest(user);
        borrowRequest.setToolBorrowRequest(tool);
        borrowRequest.setReturnDate(createborrowRequest.getReturnDate());
        borrowRequest.setState(BorrowRequestState.PENDING);
        borrowRequestRepository.save(borrowRequest);
    }

    @Override
    public List<ToolUserResponse> getUserTools(User user) {
        // Implementation logic to retrieve tools for the user
        return toolRepository.findByowner(user)
                .stream()
                .map(tool -> {
                    ToolUserResponse response = new ToolUserResponse();
                    response.setId(tool.getIdTool());
                    response.setName(tool.getName());
                    response.setDescription(tool.getDescription());
                    response.setImageSrc(tool.getImageSrc());
                    if(borrowRepository.existsBytoolBorrow(tool)) {
                        response.setAvailableAt(borrowRepository.findBytoolBorrow(tool).getDateReturn());
                    } else {
                        response.setAvailableAt(null);
                    }
                    return response;
                })
                .toList();
    }

    @Override
    public List<ToolResponse> getAllTools(Authentication authentication) {
        // Implementation logic to retrieve all tools
        return toolRepository.findAll()
                .stream()
                .map(tool -> {
                    ToolResponse response = new ToolResponse();
                    response.setId(tool.getIdTool());
                    response.setName(tool.getName());
                    response.setDescription(tool.getDescription());
                    response.setImageSrc(tool.getImageSrc());

                    if (borrowRepository.findBytoolBorrow(tool) != null) {
                        response.setAvailableAt(borrowRepository.findBytoolBorrow(tool).getDateReturn());
                    }
                    if(authentication != null) {
                        response.setAddress(tool.getOwner().getAddress());
                    }

                    return response;
                })
                .toList();
    }

    @Override
    public List<ToolBorrowRequest> getBorrowRequestsByTool(int toolId,User user){
        Tool tool = toolRepository.findById(toolId).orElseThrow(() -> new ToolNotExistsException("Tool not found"));
        if(!tool.getOwner().getIdUser().equals(user.getIdUser())) {
            throw new NotAllowedToGetBorrowRequestTool("User not allowed to get borrow requests for this tool");
        }
        List<BorrowRequest> borrowRequests = borrowRequestRepository.findBytoolBorrowRequestAndState(toolRepository.findById(toolId).orElseThrow(() -> new ToolNotExistsException("Tool not found")), BorrowRequestState.PENDING);
        return borrowRequests.stream().map(borrowRequest -> {
            ToolBorrowRequest response = new ToolBorrowRequest();
            response.setId(borrowRequest.getIdRequest());
            response.setUsername(borrowRequest.getUserBorrowRequest().getUsername());
            response.setRequestDate(borrowRequest.getRequestDate());
            response.setReturnDate(borrowRequest.getReturnDate());
            return response;
        }).toList();
    }


}
