package com.demo.portailsaisie.backend.core.service.interfaces;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface FileParsingService {
    public <T> List<T> parseFile(MultipartFile file, Class<T> clazz);
}
