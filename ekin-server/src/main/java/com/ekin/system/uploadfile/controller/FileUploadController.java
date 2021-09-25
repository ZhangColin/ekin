package com.ekin.system.uploadfile.controller;

import com.ekin.system.uploadfile.application.FileStorageService;
import com.ekin.system.uploadfile.domain.UploadFile;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

import static com.cartisan.responses.ResponseUtil.success;

/**
 * @author colin
 */
@RestController
public class FileUploadController {
    private final FileStorageService fileStorageService;

    public FileUploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        fileStorageService.save(file);
        return success("Upload file successfully: " + file.getOriginalFilename());
    }

    @GetMapping("files")
    public ResponseEntity<List<UploadFile>> files() {
        return success(fileStorageService.load()
                .map(path -> {
                    final String fileName = path.getFileName().toString();
                    final String url = MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                            "getFile",
                            path.getFileName().toString()).build().toString();

                    return new UploadFile(fileName, url);
                })
                .collect(Collectors.toList()));
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable("filename") String filename) {
        final Resource file = fileStorageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
