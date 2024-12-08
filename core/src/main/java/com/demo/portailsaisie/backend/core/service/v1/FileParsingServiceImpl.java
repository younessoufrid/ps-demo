package com.demo.portailsaisie.backend.core.service.v1;

import com.demo.portailsaisie.backend.core.service.interfaces.FileParsingService;
import com.demo.portailsaisie.backend.utils.exception.ProcessErrorEnum;
import com.demo.portailsaisie.backend.utils.exception.ProcessException;
import com.demo.portailsaisie.backend.utils.parser.ExcelCsvUtils;
import com.demo.portailsaisie.backend.utils.parser.csv.exception.CsvGlobalException;
import com.demo.portailsaisie.backend.utils.parser.enums.DocumentExtension;
import com.demo.portailsaisie.backend.utils.parser.excel.exception.ExcelGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FileParsingServiceImpl implements FileParsingService {

    public <T> List<T> parseFile(MultipartFile file, Class<T> clazz) {
        if (file == null || file.isEmpty()) {
            throw new ProcessException(ProcessErrorEnum.BAD_FILE);
        }
        String filename = file.getOriginalFilename();
        List<T> parsedLines = null;
        try {
            assert filename != null;
            if (filename.endsWith(DocumentExtension.Values.EXCEL) || filename.endsWith(DocumentExtension.Values.OLD_EXCEL)) {
                parsedLines = ExcelCsvUtils.parseExcelFile(file.getInputStream(), clazz);
            } else if (filename.endsWith(DocumentExtension.Values.CSV)) {
                parsedLines = ExcelCsvUtils.parseCsvFile(file.getInputStream(), clazz);
            } else {
                throw new ProcessException(ProcessErrorEnum.BAD_FILE_TYPE);
            }
        } catch (IOException e) {
            throw new ProcessException(ProcessErrorEnum.FILE_IO_ERROR);
        } catch (CsvGlobalException e) {
            throw new ProcessException(ProcessErrorEnum.BAD_FILE_DATA, e.getMessage(), String.valueOf(e.getLineNumber()));
        } catch (ExcelGlobalException e) {
            throw new ProcessException(ProcessErrorEnum.BAD_FILE_DATA, e.getMessage(), String.valueOf(e.getLineNumber()));
        }
        return parsedLines;
    }

}
