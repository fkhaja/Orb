package com.sin.orb.controller;

import com.sin.orb.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private int counter = 4;
    private List<Map<String, String>> tasks = new ArrayList<>() {{
        add(new HashMap<>() {{
            put("id", "1");
            put("value", "Text #1");
        }});
        add(new HashMap<>() {{
            put("id", "2");
            put("value", "Text #2");
        }});
        add(new HashMap<>() {{
            put("id", "3");
            put("value", "Text #3");
        }});
    }};

    @GetMapping
    public List<Map<String, String>> getAllTasks() {
        return tasks;
    }

    @GetMapping("{id}")
    public Map<String, String> getTask(@PathVariable String id) {
        return findTask(id);
    }

    @PostMapping
    public Map<String, String> createTask(@RequestBody Map<String, String> task) {
        task.put("id", String.valueOf(counter++));
        tasks.add(task);

        return task;
    }

    @PutMapping("{id}")
    public Map<String, String> updateTask(@PathVariable String id, @RequestBody Map<String, String> task) {
        Map<String, String> existingTask = findTask(id);
        existingTask.putAll(task);
        existingTask.put("id", id);

        return existingTask;
    }

    @DeleteMapping("{id}")
    public void deleteTask(@PathVariable String id) {
        Map<String, String> task = findTask(id);
        tasks.remove(task);
    }


    private Map<String, String> findTask(@PathVariable String id) {
        return tasks.stream()
                    .filter(task -> task.get("id").equals(id))
                    .findFirst()
                    .orElseThrow(NotFoundException::new);
    }
}
