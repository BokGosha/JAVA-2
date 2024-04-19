package ru.mirea.pr_19.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.mirea.pr_19.services.RecordService;

@RestController
@RequestMapping("/record")
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;

    @PostMapping(value = "/create")
    public void createRecord(@RequestParam Long studentId, @RequestParam Long universityId) {
        recordService.createRecord(studentId, universityId);
    }

    @PostMapping(value = "/remove")
    public void removeRecord(@RequestParam Long studentId) {
        recordService.removeRecord(studentId);
    }
}
