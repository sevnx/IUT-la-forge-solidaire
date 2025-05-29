package desforge.dev.services;

import desforge.dev.entities.Tool;
import desforge.dev.entities.User;
import desforge.dev.models.user.ToolUserResponse;

import java.util.List;

public interface IToolService {

    public List<ToolUserResponse> getUserTools (User user);
}
