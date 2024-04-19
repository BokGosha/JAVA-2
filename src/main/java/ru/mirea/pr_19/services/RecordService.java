package ru.mirea.pr_19.services;

public interface RecordService {
    void createRecord(Long studentId, Long universityId);
    void removeRecord(Long studentId);
}
