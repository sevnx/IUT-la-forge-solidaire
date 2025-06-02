package desforge.dev.services;

import desforge.dev.entities.Borrow;
import desforge.dev.entities.BorrowRequest;
import desforge.dev.entities.User;
import desforge.dev.enumerations.BorrowRequestState;
import desforge.dev.errors.borrow.CannotBorrowOwnToolException;
import desforge.dev.errors.borrow_request.BorrowRequestAlreadyExistsException;
import desforge.dev.errors.tools.NotAllowedToGetBorrowRequestTool;
import desforge.dev.errors.tools.ToolAlreadyBorrowedException;
import desforge.dev.errors.tools.ToolNotExistsException;
import desforge.dev.models.borrow_request.BorrowRequestCreate;
import desforge.dev.models.tools.ToolBorrowRequest;
import desforge.dev.models.tools.Tool;
import desforge.dev.models.user.UserTool;
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

        desforge.dev.entities.Tool tool = new desforge.dev.entities.Tool();
        tool.setImageSrc(imageUrl + toolName);
        tool.setDescription(description);
        tool.setName(name);
        tool.setOwner(user);
        toolRepository.save(tool);
    }

    @Override
    public void createBorrowRequest(int idTool, @Valid BorrowRequestCreate createborrowRequest, User user)
            throws ToolNotExistsException, BorrowRequestAlreadyExistsException, ToolAlreadyBorrowedException {
        desforge.dev.entities.Tool tool = toolRepository.findById(idTool).orElseThrow(() -> new ToolNotExistsException("Tool not found"));
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
        List<Borrow> borrow = borrowRepository.findByToolBorrow(tool);
        Borrow t = new Borrow();
        for (Borrow b : borrow) {
            if (b.getDateReturn() != null && b.getDateReturn().after(new Date())) {
                t = b;
            }
        }
        System.out.println("Tool: " + tool.getName() + ", Borrow: " + t.getDateReturn());
        if (t.getDateReturn() != null ) {
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
    public List<UserTool> getUserTools(User user) {
        // Implementation logic to retrieve tools for the user
        return toolRepository.findByOwner(user)
                .stream()
                .map(tool -> {
                    UserTool response = new UserTool();
                    response.setId(tool.getIdTool());
                    response.setName(tool.getName());
                    response.setDescription(tool.getDescription());
                    response.setImageSrc(tool.getImageSrc());
                    List<Borrow> borrow = borrowRepository.findByToolBorrow(tool);
                    Borrow t = new Borrow();
                    for (Borrow b : borrow) {
                        if (b.getDateReturn() != null && b.getDateReturn().after(new Date())) {
                            t = b;
                        }
                    }
                    if (t != null) {
                        response.setAvailableAt(t.getDateReturn());
                    } else {
                        response.setAvailableAt(null);
                    }
                    return response;
                })
                .toList();
    }

    @Override
    public List<Tool> getAllTools(Authentication authentication) {
        List<Tool> allTools = toolRepository.findAll()
                .stream()
                .map(tool -> {
                    return mapToToolResponse(tool, null, authentication == null || !authentication.isAuthenticated());
                })
                .toList();
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            return toolRepository.findByOwnerNot(user)
                    .stream()
                    .map(tool -> {
                        List<Borrow> borrow = borrowRepository.findByToolBorrow(tool);
                        Borrow t = new Borrow();
                        for (Borrow b : borrow) {
                            if (b.getDateReturn() != null && b.getDateReturn().after(new Date())) {
                                t = b;
                            }
                        }
                        return mapToToolResponse(tool, (t != null) ? t.getDateReturn() : null, authentication == null || !authentication.isAuthenticated());
                    })
                    .toList();
        }
        return allTools;
    }

    private Tool mapToToolResponse(desforge.dev.entities.Tool tool, Date availableAt, boolean hideAddress) {
        Tool response = new Tool();
        response.setId(tool.getIdTool());
        response.setName(tool.getName());
        response.setDescription(tool.getDescription());
        response.setImageSrc(tool.getImageSrc());
        response.setAvailableAt(availableAt);
        response.setAddress(hideAddress ? null : tool.getOwner().getAddress());
        return response;
    }

    @Override
    public List<ToolBorrowRequest> getBorrowRequestsByTool(int toolId, User user) {
        desforge.dev.entities.Tool tool = toolRepository.findById(toolId).orElseThrow(() -> new ToolNotExistsException("Tool not found"));
        if (!tool.getOwner().getIdUser().equals(user.getIdUser())) {
            throw new NotAllowedToGetBorrowRequestTool("User not allowed to get borrow requests for this tool");
        }
        List<BorrowRequest> borrowRequests = borrowRequestRepository.findByToolBorrowRequestAndState(toolRepository.findById(toolId).orElseThrow(() -> new ToolNotExistsException("Tool not found")), BorrowRequestState.PENDING);
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
