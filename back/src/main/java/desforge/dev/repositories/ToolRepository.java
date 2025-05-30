package desforge.dev.repositories;


import desforge.dev.entities.Tool;
import desforge.dev.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToolRepository extends JpaRepository<Tool, Integer> {

    List<Tool> findByowner(User owner);


}
