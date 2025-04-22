package com.smartcase.dispute.agent;

/**
 * REST Controller for agent task operations
 * 
 * @author Rishijeet
 */
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import com.smartcase.dispute.model.TaskRepository;
import com.smartcase.dispute.model.ManualTask;

@RestController
@RequestMapping("/api/tasks")
public class AgentController {

    private final TaskRepository taskRepository;

    @Autowired
    public AgentController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public ResponseEntity<List<ManualTask>> getPendingTasks(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer priority,
            @RequestParam(required = false) String category) {
        
        if (status != null && priority != null) {
            return ResponseEntity.ok(taskRepository.findByStatusAndPriorityOrderByCreatedAtDesc(status, priority));
        } else if (status != null) {
            return ResponseEntity.ok(taskRepository.findByStatus(status));
        } else if (category != null) {
            return ResponseEntity.ok(taskRepository.findByCategory(category));
        } else {
            return ResponseEntity.ok(taskRepository.findAll());
        }
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ManualTask> getTask(@PathVariable String taskId) {
        return taskRepository.findById(taskId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
    }

    @PostMapping("/{taskId}/decision")
    public ResponseEntity<ManualTask> submitDecision(
            @PathVariable String taskId,
            @RequestParam String decision,
            @RequestParam(required = false) String comments) {
        
        ManualTask task = taskRepository.findById(taskId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
            
        if (!task.getStatus().equals("PENDING")) {
            return ResponseEntity.badRequest().body(task);
        }
        
        task.setStatus(decision.equals("approve") ? "APPROVED" : "REJECTED");
        task.setUpdatedAt(LocalDateTime.now());
        task.setResolution(decision);
        
        if (comments != null) {
            task.setComments(comments);
        }
        
        taskRepository.save(task);
        
        return ResponseEntity.ok(task);
    }
    
    @PostMapping("/{taskId}/assign")
    public ResponseEntity<ManualTask> assignTask(
            @PathVariable String taskId,
            @RequestParam String assignee) {
        
        ManualTask task = taskRepository.findById(taskId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
            
        task.setAssignee(assignee);
        task.setUpdatedAt(LocalDateTime.now());
        taskRepository.save(task);
        
        return ResponseEntity.ok(task);
    }
    
    @GetMapping("/stats")
    public ResponseEntity<TaskStats> getTaskStats() {
        TaskStats stats = new TaskStats();
        stats.setPendingCount(taskRepository.findByStatus("PENDING").size());
        stats.setApprovedCount(taskRepository.findByStatus("APPROVED").size());
        stats.setRejectedCount(taskRepository.findByStatus("REJECTED").size());
        stats.setHighPriorityCount(taskRepository.findByStatusAndPriorityOrderByCreatedAtDesc("PENDING", 1).size());
        return ResponseEntity.ok(stats);
    }
    
    // Inner class for task statistics
    public static class TaskStats {
        private int pendingCount;
        private int approvedCount;
        private int rejectedCount;
        private int highPriorityCount;
        
        // Getters and setters
        public int getPendingCount() { return pendingCount; }
        public void setPendingCount(int pendingCount) { this.pendingCount = pendingCount; }
        
        public int getApprovedCount() { return approvedCount; }
        public void setApprovedCount(int approvedCount) { this.approvedCount = approvedCount; }
        
        public int getRejectedCount() { return rejectedCount; }
        public void setRejectedCount(int rejectedCount) { this.rejectedCount = rejectedCount; }
        
        public int getHighPriorityCount() { return highPriorityCount; }
        public void setHighPriorityCount(int highPriorityCount) { this.highPriorityCount = highPriorityCount; }
    }
} 