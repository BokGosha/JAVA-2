package ru.mirea.pr_19.components.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.mirea.pr_19.components.ShedulerService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

@Service
@ManagedResource
@RequiredArgsConstructor
public class ShedulerServiceImpl implements ShedulerService {
    private final UniversityServiceImpl universityService;
    private final StudentServiceImpl studentService;
    private final ObjectMapper mapper;

    @Value("${application.persistent-dir}")
    private String persistentDir;

    @SneakyThrows
    @ManagedOperation
    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void persist() {
        File dir = new File(persistentDir);

        if (dir.exists()) {
            FileUtils.deleteDirectory(dir);
        }

        dir.mkdirs();

        new File(dir, "universities").mkdir();
        new File(dir, "students").mkdir();

        saveAllEntities(dir);
    }

    private void saveAllEntities(File dir) {
        universityService.getUniversities().forEach(
                university -> {
                    try {
                        Files.write(dir.toPath().resolve("universities").resolve(university.getId() + ".json"),
                                mapper.writeValueAsBytes(university)
                        );
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

        studentService.getStudents().forEach(
                student -> {
                    try {
                        Files.write(dir.toPath().resolve("students").resolve(student.getId() + ".json"),
                                mapper.writeValueAsBytes(student)
                        );
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }
}
