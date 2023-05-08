package ru.nsu.manager.dto;

import java.util.List;
import java.util.Set;

public class StatusDTO {
    String status;
    Set<String> data;

    public StatusDTO() {
        status = "IN_PROGRESS";
        data = null;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<String> getData() {
        return data;
    }

    public void setData(Set<String> data) {
        this.data = data;
    }

    public StatusDTO(String status, Set<String> data) {
        this.status = status;
        this.data = data;
    }
}
