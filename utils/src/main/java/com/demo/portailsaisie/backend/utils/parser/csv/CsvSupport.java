package com.demo.portailsaisie.backend.utils.parser.csv;

import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.demo.portailsaisie.backend.utils.parser.csv.exception.CsvGlobalException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

@Slf4j
public class CsvSupport {
    public static <T> List<T> csvToBeanList(InputStreamReader reader,
                                            MappingStrategy<T> mappingStrategy,
                                            char separator) throws CsvGlobalException {
        log.debug("Reading file...");
        long time = System.currentTimeMillis();
        //Defining CsvToBean object
        CsvToBean<T> cb = new CsvToBeanBuilder<T>(reader)
                .withSeparator(separator)
                .withMappingStrategy(mappingStrategy)
                .build();
        //parsing
        try {
            List<T> beans = cb.parse();
            log.debug("Parsing completed in {} ms", System.currentTimeMillis() - time);
            return beans;
        } catch (Exception ex) {
            if (ex.getCause() instanceof CsvException e) {
                throw new CsvGlobalException(e.getMessage(), e.getLineNumber(), e.getLine());
            }
            throw ex;
        }
    }

    public static <T> void beanListToCsv(OutputStreamWriter writer,
                                         MappingStrategy<T> mappingStrategy,
                                         List<T> records,
                                         char separator,
                                         boolean withApplyQuotesToAll) throws IOException {

        log.debug("Starting writing...");
        long time = System.currentTimeMillis();
        // Creating StatefulBeanToCsv object
        StatefulBeanToCsvBuilder<T> builder =
                new StatefulBeanToCsvBuilder<>(writer);
        StatefulBeanToCsv<T> csvWriter =
                builder.withMappingStrategy(mappingStrategy)
                        .withApplyQuotesToAll(withApplyQuotesToAll)
                        .withSeparator(separator)
                        .build();

        // Write list records
        try {
            csvWriter.write(records);
            log.debug("Writing completed in {} ms", System.currentTimeMillis() - time);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            throw new CsvGlobalException(e.getMessage(), e.getLineNumber(), e.getLine());
        }

        // Flush the writer
        writer.flush();
    }

}
