package com.htmcts.worktasks.dto;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateStatusDTO {
    @NotNull(message = "Id must not be null")
    private Integer id;
    @NotNull(message = "Status must not be null")
    @NotBlank(message = "Status must not be blank")
    private String status;

    public UpdateStatusDTO(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
