package com.smartcase.dispute.workflow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface TaskRepository extends JpaRepository<ManualTask, String> {
    
    List<ManualTask> findByStatus(String status);
    
    List<ManualTask> findByStatusAndPriorityOrderByCreatedAtDesc(String status, int priority);
    
    List<ManualTask> findByAssignee(String assignee);
    
    List<ManualTask> findByCustomerId(String customerId);
    
    List<ManualTask> findByCategory(String category);
    
    List<ManualTask> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
