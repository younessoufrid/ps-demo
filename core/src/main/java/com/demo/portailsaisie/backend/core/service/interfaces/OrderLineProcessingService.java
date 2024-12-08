package com.demo.portailsaisie.backend.core.service.interfaces;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface OrderLineProcessingService {
    public void processFileAndSaveOrderLines(MultipartFile file);

    public void processLocalFileAndUpdateOrderLines(MultipartFile file);
}
