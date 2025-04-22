package com.smartcase.dispute.agent;

/**
 * Controller for the dashboard UI
 * 
 * @author Rishijeet
 */
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.smartcase.dispute.model.TaskRepository;
import com.smartcase.dispute.model.ManualTask;

@Controller
public class DashboardController {
    
    private final TaskRepository taskRepository;
    
    @Autowired
    public DashboardController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
    @GetMapping("/dashboard")
    public String showDashboard(
            @RequestParam(required = false, defaultValue = "all") String filter,
            Model model) {
        
        List<ManualTask> tasks;
        
        switch (filter) {
            case "pending":
                tasks = taskRepository.findByStatus("PENDING");
                break;
            case "highPriority":
                tasks = taskRepository.findByStatusAndPriorityOrderByCreatedAtDesc("PENDING", 1);
                break;
            case "fraud":
                tasks = taskRepository.findByCategory("FRAUD");
                break;
            case "approved":
                tasks = taskRepository.findByStatus("APPROVED");
                break;
            case "rejected":
                tasks = taskRepository.findByStatus("REJECTED");
                break;
            default:
                tasks = taskRepository.findAll();
                break;
        }
        
        model.addAttribute("tasks", tasks);
        model.addAttribute("activeFilter", filter);
        
        // Add counts for dashboard metrics
        model.addAttribute("pendingCount", taskRepository.findByStatus("PENDING").size());
        model.addAttribute("highPriorityCount", taskRepository.findByStatusAndPriorityOrderByCreatedAtDesc("PENDING", 1).size());
        model.addAttribute("fraudCount", taskRepository.findByCategory("FRAUD").size());
        
        return "dashboard"; // Refers to src/main/resources/templates/dashboard.html
    }
}
