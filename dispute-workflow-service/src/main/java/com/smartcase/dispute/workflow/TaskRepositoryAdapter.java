package com.smartcase.dispute.workflow;

import com.smartcase.dispute.model.ManualTask;
import com.smartcase.dispute.model.TaskRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TaskRepositoryAdapter implements TaskRepository {

    private final WorkflowTaskRepository repository;

    public TaskRepositoryAdapter(WorkflowTaskRepository repository) {
        this.repository = repository;
    }

    // Convert ManualTask from model to workflow ManualTask
    private com.smartcase.dispute.workflow.ManualTask convertToWorkflowTask(ManualTask modelTask) {
        if (modelTask == null) return null;
        
        com.smartcase.dispute.workflow.ManualTask task = new com.smartcase.dispute.workflow.ManualTask();
        task.setId(modelTask.getId());
        task.setDisputeReference(modelTask.getDisputeReference());
        task.setCustomerId(modelTask.getCustomerId());
        task.setAmount(modelTask.getAmount());
        task.setPriority(modelTask.getPriority());
        task.setCategory(modelTask.getCategory());
        task.setStatus(modelTask.getStatus());
        task.setAssignee(modelTask.getAssignee());
        task.setCreatedAt(modelTask.getCreatedAt());
        task.setUpdatedAt(modelTask.getUpdatedAt());
        task.setResolution(modelTask.getResolution());
        task.setComments(modelTask.getComments());
        return task;
    }

    // Convert workflow ManualTask to model ManualTask
    private ManualTask convertToModelTask(com.smartcase.dispute.workflow.ManualTask workflowTask) {
        if (workflowTask == null) return null;
        
        ManualTask task = new ManualTask();
        task.setId(workflowTask.getId());
        task.setDisputeReference(workflowTask.getDisputeReference());
        task.setCustomerId(workflowTask.getCustomerId());
        task.setAmount(workflowTask.getAmount());
        task.setPriority(workflowTask.getPriority());
        task.setCategory(workflowTask.getCategory());
        task.setStatus(workflowTask.getStatus());
        task.setAssignee(workflowTask.getAssignee());
        task.setCreatedAt(workflowTask.getCreatedAt());
        task.setUpdatedAt(workflowTask.getUpdatedAt());
        task.setResolution(workflowTask.getResolution());
        task.setComments(workflowTask.getComments());
        return task;
    }

    @Override
    public List<ManualTask> findByStatus(String status) {
        return repository.findByStatus(status).stream()
                .map(this::convertToModelTask)
                .collect(Collectors.toList());
    }

    @Override
    public List<ManualTask> findByStatusAndPriorityOrderByCreatedAtDesc(String status, int priority) {
        return repository.findByStatusAndPriorityOrderByCreatedAtDesc(status, priority).stream()
                .map(this::convertToModelTask)
                .collect(Collectors.toList());
    }

    @Override
    public List<ManualTask> findByAssignee(String assignee) {
        return repository.findByAssignee(assignee).stream()
                .map(this::convertToModelTask)
                .collect(Collectors.toList());
    }

    @Override
    public List<ManualTask> findByCustomerId(String customerId) {
        return repository.findByCustomerId(customerId).stream()
                .map(this::convertToModelTask)
                .collect(Collectors.toList());
    }

    @Override
    public List<ManualTask> findByCategory(String category) {
        return repository.findByCategory(category).stream()
                .map(this::convertToModelTask)
                .collect(Collectors.toList());
    }

    @Override
    public List<ManualTask> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end) {
        return repository.findByCreatedAtBetween(start, end).stream()
                .map(this::convertToModelTask)
                .collect(Collectors.toList());
    }

    @Override
    public <S extends ManualTask> S save(S entity) {
        com.smartcase.dispute.workflow.ManualTask workflowTask = convertToWorkflowTask(entity);
        com.smartcase.dispute.workflow.ManualTask savedTask = repository.save(workflowTask);
        return (S) convertToModelTask(savedTask);
    }

    @Override
    public <S extends ManualTask> List<S> saveAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        for (S entity : entities) {
            result.add(save(entity));
        }
        return result;
    }

    @Override
    public Optional<ManualTask> findById(String id) {
        return repository.findById(id).map(this::convertToModelTask);
    }

    @Override
    public boolean existsById(String id) {
        return repository.existsById(id);
    }

    @Override
    public List<ManualTask> findAll() {
        return repository.findAll().stream()
                .map(this::convertToModelTask)
                .collect(Collectors.toList());
    }

    @Override
    public List<ManualTask> findAll(Sort sort) {
        return repository.findAll(sort).stream()
                .map(this::convertToModelTask)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ManualTask> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::convertToModelTask);
    }

    @Override
    public List<ManualTask> findAllById(Iterable<String> ids) {
        return repository.findAllById(ids).stream()
                .map(this::convertToModelTask)
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(ManualTask entity) {
        repository.deleteById(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends String> ids) {
        repository.deleteAllById(ids);
    }

    @Override
    public void deleteAll(Iterable<? extends ManualTask> entities) {
        for (ManualTask entity : entities) {
            deleteById(entity.getId());
        }
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public void flush() {
        repository.flush();
    }

    @Override
    public <S extends ManualTask> S saveAndFlush(S entity) {
        return save(entity);
    }

    @Override
    public <S extends ManualTask> List<S> saveAllAndFlush(Iterable<S> entities) {
        List<S> result = saveAll(entities);
        flush();
        return result;
    }

    @Override
    public void deleteAllInBatch(Iterable<ManualTask> entities) {
        List<String> ids = new ArrayList<>();
        for (ManualTask entity : entities) {
            ids.add(entity.getId());
        }
        repository.deleteAllByIdInBatch(ids);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> ids) {
        repository.deleteAllByIdInBatch(ids);
    }

    @Override
    public void deleteAllInBatch() {
        repository.deleteAllInBatch();
    }

    @Override
    public ManualTask getOne(String id) {
        return convertToModelTask(repository.getReferenceById(id));
    }

    @Override
    public ManualTask getById(String id) {
        return convertToModelTask(repository.getReferenceById(id));
    }

    @Override
    public ManualTask getReferenceById(String id) {
        return convertToModelTask(repository.getReferenceById(id));
    }

    // Unsupported operations that require Example - using safer implementations
    @Override
    public <S extends ManualTask> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends ManualTask> List<S> findAll(Example<S> example) {
        return new ArrayList<>();
    }

    @Override
    public <S extends ManualTask> List<S> findAll(Example<S> example, Sort sort) {
        return new ArrayList<>();
    }

    @Override
    public <S extends ManualTask> Page<S> findAll(Example<S> example, Pageable pageable) {
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
        return null;
    }
} 