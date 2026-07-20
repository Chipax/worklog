package com.worklog.demo.services;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String save(MultipartFile file);

}
