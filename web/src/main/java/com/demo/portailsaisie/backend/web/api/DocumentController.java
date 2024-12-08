package com.demo.portailsaisie.backend.web.api;

import com.demo.portailsaisie.backend.core.service.interfaces.OrderLineProcessingService;
import com.demo.portailsaisie.backend.utils.Constants;
import com.demo.portailsaisie.backend.utils.parser.model.ExcelCsvRow;
import com.demo.portailsaisie.backend.utils.parser.ExcelCsvUtils;
import com.demo.portailsaisie.backend.utils.parser.enums.DocumentExtension;
import com.demo.portailsaisie.backend.web.PsvUri;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(PsvUri.API_DOCUMENT)
public class DocumentController {
    private final OrderLineProcessingService orderLineProcessingService;

    @PostMapping("/upload")
    public ResponseEntity<Object> parseFile(@RequestParam("file") MultipartFile file) {
        orderLineProcessingService.processFileAndSaveOrderLines(file);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/import")
    public ResponseEntity<Object> parseLocalFile(@RequestParam("file") MultipartFile file) {
        orderLineProcessingService.processLocalFileAndUpdateOrderLines(file);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/download")
    public ResponseEntity<Object> downloadFile(@RequestParam("file") MultipartFile file) {
        // Check if file is empty
        if (file.isEmpty()) {
            // Handle empty file
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        String filename = file.getOriginalFilename();
        Objects.requireNonNull(filename);
        try {
            if (filename.endsWith(DocumentExtension.Values.EXCEL) || filename.endsWith(DocumentExtension.Values.OLD_EXCEL)) {
                byte[] excelBytes = ExcelCsvUtils.generateExcelFile(ExcelCsvUtils.parseExcelFile(file.getInputStream(), ExcelCsvRow.class), ExcelCsvRow.class);
                headers.setContentDispositionFormData("data", "data.xlsx");
                headers.setContentType(MediaType.parseMediaType(Constants.EXCEL_MEDIA_TYPE));
                return ResponseEntity
                        .ok()
                        .headers(headers)
                        .body(excelBytes);
            } else if (filename.endsWith(DocumentExtension.Values.CSV)) {
                headers.setContentDispositionFormData("data", "data.csv");
                headers.setContentType(MediaType.parseMediaType(Constants.CSV_MEDIA_TYPE));
                byte[] csvBytes = ExcelCsvUtils.generateCsvFile(ExcelCsvUtils.parseCsvFile(file.getInputStream(), ExcelCsvRow.class), ExcelCsvRow.class);
                return ResponseEntity
                        .ok()
                        .headers(headers)
                        .body(csvBytes);
            } else {
                return ResponseEntity.badRequest().body("Unsupported file type");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
