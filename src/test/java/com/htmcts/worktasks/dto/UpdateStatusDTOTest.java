package com.htmcts.worktasks.dto;

import com.htmcts.worktasks.domaine.CaseTask;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdateStatusDTOTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getId() {
    }

    @Test
    void setId() {
    }

    @Test
    void getStatus() {
    }

    @Test
    void setStatus() {
        CaseTask caseTask = new CaseTask();
        caseTask.setStatus("Completed");
        assertEquals("Completed", caseTask.getStatus());

    }
}