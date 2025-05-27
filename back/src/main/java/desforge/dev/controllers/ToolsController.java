package desforge.dev.controllers;

import desforge.dev.entities.Tool;
import desforge.dev.entities.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tools")
public class ToolsController {

    private Tool tool;

    @GetMapping(value = "")
    public String getTools() {
        return "test";
    }

    @PostMapping(value = "/{id}/borrow-requests", produces = "application/json")
    public String createBorrowRequest(@PathVariable("id") int id) {
        return null;
    }

    @PostMapping(value = "", produces = "application/json")
    public String createTool(Tool tool) {
        return null;
    }

    @GetMapping(value = "/{toolId}/borrow-requests", produces = "application/json")
    public String getBorrowRequest(@PathVariable("toolId") int toolId) {
        return null;
    }
}
