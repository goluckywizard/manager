package ru.nsu.manager;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Status {
    String status;
    Set<String> data;
    int count = 0;

    public Status() {
        status = "IN_PROGRESS";
        data = Collections.synchronizedSet(new HashSet<>());
        count = 0;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public synchronized void increaseCount() {
        count++;
    }
}
