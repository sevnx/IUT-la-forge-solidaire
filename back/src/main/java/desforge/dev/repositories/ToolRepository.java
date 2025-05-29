package desforge.dev.repositories;


import desforge.dev.entities.Tool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolRepository extends JpaRepository<Tool, Integer> {
    Tool getToolByIdTool(int idTool);

}
