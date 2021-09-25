package com.ekin.system.uploadfile;

import com.ekin.system.uploadfile.application.FileStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/**
 * @author colin
 */
@Service
public class FileUploadConfiguration implements CommandLineRunner {
    private final FileStorageService fileStorageService;

    public FileUploadConfiguration(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @Override
    public void run(String... args) throws Exception {
        fileStorageService.clear();
        fileStorageService.init();
    }
}
