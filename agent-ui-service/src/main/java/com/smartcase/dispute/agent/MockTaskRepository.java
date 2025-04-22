package com.smartcase.dispute.agent;

/**
 * Mock implementation of TaskRepository for local development without a database
 * 
 * @author Rishijeet
 */
import com.smartcase.dispute.model.ManualTask;
import com.smartcase.dispute.model.TaskRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MockTaskRepository implements TaskRepository {
    private final Map<String, ManualTask> taskMap = new HashMap<>();

    // Initialize with sample data
    public MockTaskRepository() {
        // Add sample tasks
        ManualTask task1 = new ManualTask();
        task1.setId(UUID.randomUUID().toString());
        task1.setDisputeReference("DISP-2024-001");
        task1.setStatus("PENDING");
        task1.setPriority(3);
        task1.setAssignee("agent1");
        task1.setCustomerId("CUST001");
        task1.setCategory("TRANSACTION_DISPUTE");
        task1.setAmount(new BigDecimal("156.78"));
        task1.setCreatedAt(LocalDateTime.now().minusDays(1));
        task1.setComments("Customer disputes a charge of $156.78 from Unknown Merchant");

        ManualTask task2 = new ManualTask();
        task2.setId(UUID.randomUUID().toString());
        task2.setDisputeReference("DISP-2024-002");
        task2.setStatus("IN_PROGRESS");
        task2.setPriority(1);
        task2.setAssignee("agent2");
        task2.setCustomerId("CUST002");
        task2.setCategory("FRAUD");
        task2.setAmount(new BigDecimal("429.99"));
        task2.setCreatedAt(LocalDateTime.now().minusHours(4));
        task2.setComments("Multiple suspicious transactions reported by customer");

        ManualTask task3 = new ManualTask();
        task3.setId(UUID.randomUUID().toString());
        task3.setDisputeReference("DISP-2024-003");
        task3.setStatus("PENDING");
        task3.setPriority(1);
        task3.setAssignee(null);
        task3.setCustomerId("CUST003");
        task3.setCategory("REFUND");
        task3.setAmount(new BigDecimal("49.99"));
        task3.setCreatedAt(LocalDateTime.now().minusHours(8));
        task3.setComments("Customer requests refund for subscription service");

        ManualTask task4 = new ManualTask();
        task4.setId(UUID.randomUUID().toString());
        task4.setDisputeReference("DISP-2024-004");
        task4.setStatus("APPROVED");
        task4.setPriority(2);
        task4.setAssignee("agent1");
        task4.setCustomerId("CUST004");
        task4.setCategory("TRANSACTION_DISPUTE");
        task4.setAmount(new BigDecimal("89.50"));
        task4.setCreatedAt(LocalDateTime.now().minusDays(3));
        task4.setResolution("APPROVED");
        task4.setComments("Customer provided receipt. Refund issued.");

        ManualTask task5 = new ManualTask();
        task5.setId(UUID.randomUUID().toString());
        task5.setDisputeReference("DISP-2024-005");
        task5.setStatus("REJECTED");
        task5.setPriority(3);
        task5.setAssignee("agent3");
        task5.setCustomerId("CUST005");
        task5.setCategory("CHARGEBACK");
        task5.setAmount(new BigDecimal("214.75"));
        task5.setCreatedAt(LocalDateTime.now().minusDays(2));
        task5.setResolution("REJECTED");
        task5.setComments("Merchant provided valid proof of purchase.");

        taskMap.put(task1.getId(), task1);
        taskMap.put(task2.getId(), task2);
        taskMap.put(task3.getId(), task3);
        taskMap.put(task4.getId(), task4);
        taskMap.put(task5.getId(), task5);
    }

    @Override
    public List<ManualTask> findByStatus(String status) {
        return taskMap.values()
                .stream()
                .filter(task -> task.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    @Override
    public List<ManualTask> findByStatusAndPriorityOrderByCreatedAtDesc(String status, int priority) {
        return taskMap.values()
                .stream()
                .filter(task -> task.getStatus().equals(status) && task.getPriority() == priority)
                .sorted(Comparator.comparing(ManualTask::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<ManualTask> findByAssignee(String assignee) {
        return taskMap.values()
                .stream()
                .filter(task -> assignee.equals(task.getAssignee()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ManualTask> findByCustomerId(String customerId) {
        return taskMap.values()
                .stream()
                .filter(task -> customerId.equals(task.getCustomerId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ManualTask> findByCategory(String category) {
        return taskMap.values()
                .stream()
                .filter(task -> category.equals(task.getCategory()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ManualTask> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end) {
        return taskMap.values()
                .stream()
                .filter(task -> {
                    LocalDateTime createdAt = task.getCreatedAt();
                    return (createdAt.isEqual(start) || createdAt.isAfter(start)) && 
                           (createdAt.isEqual(end) || createdAt.isBefore(end));
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ManualTask> findAll() {
        return new ArrayList<>(taskMap.values());
    }

    @Override
    public List<ManualTask> findAll(Sort sort) {
        // Simple implementation without sorting
        return findAll();
    }

    @Override
    public Page<ManualTask> findAll(Pageable pageable) {
        // Mock implementation - in real app would handle paging
        return Page.empty();
    }

    @Override
    public List<ManualTask> findAllById(Iterable<String> ids) {
        List<ManualTask> result = new ArrayList<>();
        for (String id : ids) {
            if (taskMap.containsKey(id)) {
                result.add(taskMap.get(id));
            }
        }
        return result;
    }

    @Override
    public long count() {
        return taskMap.size();
    }

    @Override
    public void deleteById(String id) {
        taskMap.remove(id);
    }

    @Override
    public void delete(ManualTask task) {
        taskMap.remove(task.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends String> ids) {
        ids.forEach(this::deleteById);
    }

    @Override
    public void deleteAll(Iterable<? extends ManualTask> tasks) {
        tasks.forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        taskMap.clear();
    }

    @Override
    public <S extends ManualTask> S save(S task) {
        if (task.getId() == null) {
            task.setId(UUID.randomUUID().toString());
        }
        taskMap.put(task.getId(), task);
        return task;
    }

    @Override
    public <S extends ManualTask> List<S> saveAll(Iterable<S> tasks) {
        List<S> result = new ArrayList<>();
        tasks.forEach(task -> result.add(save(task)));
        return result;
    }

    @Override
    public Optional<ManualTask> findById(String id) {
        return Optional.ofNullable(taskMap.get(id));
    }

    @Override
    public boolean existsById(String id) {
        return taskMap.containsKey(id);
    }

    @Override
    public void flush() {
        // No-op for mock implementation
    }

    @Override
    public <S extends ManualTask> S saveAndFlush(S task) {
        return save(task);
    }

    @Override
    public <S extends ManualTask> List<S> saveAllAndFlush(Iterable<S> tasks) {
        return saveAll(tasks);
    }

    @Override
    public void deleteAllInBatch(Iterable<ManualTask> tasks) {
        deleteAll(tasks);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> ids) {
        deleteAllById(ids);
    }

    @Override
    public void deleteAllInBatch() {
        deleteAll();
    }

    @Override
    public ManualTask getOne(String id) {
        return taskMap.get(id);
    }

    @Override
    public ManualTask getById(String id) {
        return taskMap.get(id);
    }

    @Override
    public ManualTask getReferenceById(String id) {
        return taskMap.get(id);
    }

    @Override
    public <S extends ManualTask> Optional<S> findOne(Example<S> example) {
        // Simplified implementation
        return Optional.empty();
    }

    @Override
    public <S extends ManualTask> List<S> findAll(Example<S> example) {
        // Simplified implementation
        return Collections.emptyList();
    }

    @Override
    public <S extends ManualTask> List<S> findAll(Example<S> example, Sort sort) {
        // Simplified implementation
        return Collections.emptyList();
    }

    @Override
    public <S extends ManualTask> Page<S> findAll(Example<S> example, Pageable pageable) {
        // Simplified implementation
        return Page.empty();
    }

    @Override
    public <S extends ManualTask> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends ManualTask> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends ManualTask, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        // Simplified implementation
        return null;
    }
} 