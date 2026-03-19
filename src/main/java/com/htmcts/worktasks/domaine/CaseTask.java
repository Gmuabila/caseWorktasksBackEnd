package com.htmcts.worktasks.domaine;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;


import java.util.Date;
import java.util.Objects;

@Entity
public class CaseTask {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotBlank(message = "Title cannot be blank")
    @NotNull(message = "The title must not be null")
    private String title;
    private String description;
    @NotBlank(message = "Status cannot be blank")
    @NotNull(message = "The status must not be null")
    private String status;
    @NotNull(message = "Date cannot be null")
    @Future(message = "Date must be in the future")
    private Date due;

    public CaseTask(Integer id, String title, String description, String status, Date due) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.due = due;
    }

    public CaseTask() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CaseTask caseTask = (CaseTask) o;
        return id.equals(caseTask.id) && title.equals(caseTask.title) && Objects.equals(description, caseTask.description) && status.equals(caseTask.status) && Objects.equals(due, caseTask.due);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, status, due);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    public String getStatus() {
        return status;
    }

    public void setStatus(@NonNull String status) {
        this.status = status;
    }

    @NonNull
    public Date getDue() {
        return due;
    }

    public void setDue(@NonNull Date due) {
        this.due = due;
    }
}
