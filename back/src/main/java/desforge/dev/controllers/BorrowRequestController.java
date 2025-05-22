package desforge.dev.controllers;


import desforge.dev.entities.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrow-requests")
public class BorrowRequestController {

    @PutMapping(value = "/register{requestId}", produces = "application/json")
    public User borrowRequestResponse(@PathVariable("requestId") int requestId) { //Ajouter le state
        return null;
    }
}
