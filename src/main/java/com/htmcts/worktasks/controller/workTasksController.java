package com.htmcts.worktasks.controller;

import com.htmcts.worktasks.domaine.CaseTask;
import com.htmcts.worktasks.dto.UpdateStatusDTO;
import com.htmcts.worktasks.service.CaseworkTaskServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class workTasksController {

    @Autowired
    CaseworkTaskServices caseworkTaskServices;

    @CrossOrigin(value = "*")
    @PostMapping("/createTask")
    public ResponseEntity<CaseTask> createTask(@Valid @RequestBody CaseTask newTask) {
        CaseTask task = caseworkTaskServices.createTask(newTask);
        return ResponseEntity.ok(task);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> Map.of("field", e.getField(), "message", e.getDefaultMessage()))
                .toList();
        return ResponseEntity.badRequest().body(Map.of("errors", errors));
    }

    @CrossOrigin(value = "*")
    @GetMapping("/tasksbyid/{idIn}")
    public ResponseEntity<CaseTask> getTaskbyId(@PathVariable("idIn") Integer idIn) {
        CaseTask returnedTask = caseworkTaskServices.getTaskById(idIn);

        return ResponseEntity.ok(returnedTask);
    }

    @CrossOrigin(value = "*")
    @GetMapping("/allcasetasks")
    public ResponseEntity<List<CaseTask>> retrieveAllTasks() {
        List<CaseTask> caseTaskList = caseworkTaskServices.getAllTasks();
        return ResponseEntity.ok(caseTaskList);
    }

    @CrossOrigin(value = "*")
    @PutMapping("/updatestatus")
    public CaseTask updateTaskStatus(@Valid @RequestBody UpdateStatusDTO updateStatusDTO) {
        return caseworkTaskServices.updateTaskStatus(updateStatusDTO.getId(), updateStatusDTO.getStatus());
    }

    @CrossOrigin(value = "*")
    @DeleteMapping("/deletetask/{idIn}")
    public void deleteTask(@PathVariable("idIn") Integer idIn) {
        caseworkTaskServices.deleteTask(idIn);
    }

}
