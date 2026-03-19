package com.htmcts.worktasks.service;

import com.htmcts.worktasks.domaine.CaseTask;
import com.htmcts.worktasks.repositories.CaseTasksRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CaseworkTaskServicesTest {

    private CaseworkTaskServices caseworkTaskServices;

    @Mock
    private CaseTasksRepository caseTasksRepository;

    private Date time;
    private CaseTask caseTask;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        caseworkTaskServices = new CaseworkTaskServices(caseTasksRepository);

        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.add(Calendar.HOUR_OF_DAY, 1);
        time = instance.getTime();

        caseTask = new CaseTask(1, "Library", "Reading all day", "Started", time);
    }

    @Test
    public void createTask_happy_path_test() {
        when(caseTasksRepository.save(caseTask)).thenReturn(caseTask);

        assertEquals(caseworkTaskServices.createTask(caseTask), caseTask);
    }

    @Test
    public void retrieveTaskbyId_happy_path_test() {
        Integer userId = 1;
        when(caseTasksRepository.findById(userId)).thenReturn(Optional.of(caseTask));
        CaseTask result = caseworkTaskServices.getTaskById(userId);

        assertEquals(caseTask, result);
    }

    @Test
    public void updateStatus_happy_path_test() {
        Integer userId = 1;
        CaseTask caseTaskTwo = new CaseTask(1, "Library", "Reading all day", "Completed", time);
        when(caseTasksRepository.findById(userId)).thenReturn(Optional.of(caseTask));
        when(caseTasksRepository.save(caseTask)).thenReturn(caseTask);
        assertEquals(caseworkTaskServices.updateTaskStatus(userId, "Completed"), caseTaskTwo);
    }

    @Test
    public void deleteTask_happy_path_test() {
        Integer userId = 1;

        when(caseTasksRepository.findById(userId)).thenReturn(Optional.of(caseTask));
        doNothing().when(caseTasksRepository).deleteById(userId);
        caseworkTaskServices.deleteTask(userId);
        verify(caseTasksRepository).deleteById(userId);
    }

    @Test
    public void retrieveAllTasks_happy_path_test() {
        List<CaseTask> caseTaskList = Arrays.asList(caseTask);
        when(caseTasksRepository.findAll()).thenReturn(caseTaskList);
        List<CaseTask> result = caseworkTaskServices.getAllTasks();

        assertEquals(caseTaskList, result);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(caseTasksRepository);
    }

}
