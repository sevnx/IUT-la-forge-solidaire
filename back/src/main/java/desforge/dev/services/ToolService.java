package desforge.dev.services;

import desforge.dev.entities.Tool;
import desforge.dev.entities.User;
import desforge.dev.models.user.BorrowUserResponse;
import desforge.dev.models.user.ToolUserResponse;
import desforge.dev.repositories.BorrowRepository;
import desforge.dev.repositories.ToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolService implements IToolService {
    @Autowired
    private ToolRepository toolRepository;
    @Autowired
    private BorrowRepository borrowRepository;

    @Override
    public List<ToolUserResponse> getUserTools(User user) {
        // Implementation logic to retrieve tools for the user
        return toolRepository.findByowner(user)
        .stream()
                .map(tool -> {
                    ToolUserResponse response = new ToolUserResponse();
                    response.setToolNumber(tool.getIdTool());
                    response.setToolName(tool.getName());
                    response.setDescription(tool.getDescription());
                    response.setImage(tool.getPhoto());
                    if(borrowRepository.existsBytoolBorrow(tool)) {
                        response.setAvailableAt(borrowRepository.findBytoolBorrow(tool).getDateReturn());
                    } else {
                        response.setAvailableAt(null);
                    }
                    return response;
                })
                .toList();

    }
}
