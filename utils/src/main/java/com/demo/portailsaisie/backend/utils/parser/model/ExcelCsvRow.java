package com.demo.portailsaisie.backend.utils.parser.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.demo.portailsaisie.backend.utils.parser.csv.CustomComaSeparatorDoubleConverter;
import com.demo.portailsaisie.backend.utils.parser.csv.CustomDateConverter;
import com.demo.portailsaisie.backend.utils.parser.enums.ExcelCsvColumn;
import com.demo.portailsaisie.backend.utils.parser.excel.annotation.ExcelBindByName;
import com.demo.portailsaisie.backend.utils.parser.excel.annotation.ExcelDate;
import com.demo.portailsaisie.backend.utils.parser.excel.annotation.ExcelNumber;
import lombok.*;

import java.util.Date;

@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExcelCsvRow {

    @ExcelBindByName(column = ExcelCsvColumn.Values.SALES_SITE)
    @CsvBindByName(column = ExcelCsvColumn.Values.SALES_SITE)
    private String salesSiteReference;

    @ExcelBindByName(column = ExcelCsvColumn.Values.CLIENT_COMMAND)
    @CsvBindByName(column = ExcelCsvColumn.Values.CLIENT_COMMAND)
    private String clientOrder;

    @ExcelBindByName(column = ExcelCsvColumn.Values.CLIENT_NAME)
    @CsvBindByName(column = ExcelCsvColumn.Values.CLIENT_NAME)
    private String clientName;

    @ExcelBindByName(column = ExcelCsvColumn.Values.SERVICE_START_DATE)
    @ExcelDate(value = "dd/MM/yyyy")
    @CsvCustomBindByName(column = ExcelCsvColumn.Values.SERVICE_START_DATE, converter = CustomDateConverter.class)
    private Date startService;

    @ExcelBindByName(column = ExcelCsvColumn.Values.SERVICE_END_DATE)
    @ExcelDate(value = "dd/MM/yyyy")
    @CsvCustomBindByName(column = ExcelCsvColumn.Values.SERVICE_END_DATE, converter = CustomDateConverter.class)
    private Date endService;

    @ExcelBindByName(column = ExcelCsvColumn.Values.AFFAIR)
    @CsvBindByName(column = ExcelCsvColumn.Values.AFFAIR)
    private String affair;

    @ExcelBindByName(column = ExcelCsvColumn.Values.COMMAND_NUMBER, required = true)
    @CsvBindByName(column = ExcelCsvColumn.Values.COMMAND_NUMBER, required = true)
    private String orderNumber;

    @ExcelBindByName(column = ExcelCsvColumn.Values.ORDER_REFERENCE)
    @CsvBindByName(column = ExcelCsvColumn.Values.ORDER_REFERENCE)
    private String orderReference;

    @ExcelBindByName(column = ExcelCsvColumn.Values.LINE, required = true)
    @CsvBindByName(column = ExcelCsvColumn.Values.LINE, required = true)
    private Double line;

    @ExcelBindByName(column = ExcelCsvColumn.Values.ARTICLE)
    @CsvBindByName(column = ExcelCsvColumn.Values.ARTICLE)
    private String article;

    @ExcelBindByName(column = ExcelCsvColumn.Values.BU_REFERENCE)
    @CsvBindByName(column = ExcelCsvColumn.Values.BU_REFERENCE)
    private String buReference;

    @ExcelBindByName(column = ExcelCsvColumn.Values.DESIGNATION)
    @CsvBindByName(column = ExcelCsvColumn.Values.DESIGNATION)
    private String designation;

    @ExcelBindByName(column = ExcelCsvColumn.Values.LINE_TEXT)
    @CsvBindByName(column = ExcelCsvColumn.Values.LINE_TEXT)
    private String textLine;

    @ExcelBindByName(column = ExcelCsvColumn.Values.QUANTITY)
    @CsvCustomBindByName(column = ExcelCsvColumn.Values.QUANTITY, converter = CustomComaSeparatorDoubleConverter.class)
    private Double quantity;

    @ExcelBindByName(column = ExcelCsvColumn.Values.SALE_UNIT)
    @CsvBindByName(column = ExcelCsvColumn.Values.SALE_UNIT)
    private String saleUnit;

    @ExcelBindByName(column = ExcelCsvColumn.Values.NET_PRICE_HT)
    @ExcelNumber("#,##0.00")
    @CsvCustomBindByName(column = ExcelCsvColumn.Values.NET_PRICE_HT, converter = CustomComaSeparatorDoubleConverter.class)
    private Double priceNetHT;

    @ExcelBindByName(column = ExcelCsvColumn.Values.DELIVERY_TYPE)
    @CsvBindByName(column = ExcelCsvColumn.Values.DELIVERY_TYPE)
    private String delivery;

    @ExcelBindByName(column = ExcelCsvColumn.Values.CDR)
    @CsvBindByName(column = ExcelCsvColumn.Values.CDR)
    private String cdr;

    @ExcelBindByName(column = ExcelCsvColumn.Values.ACTIVITY)
    @CsvBindByName(column = ExcelCsvColumn.Values.ACTIVITY)
    private String activity;

    @ExcelBindByName(column = ExcelCsvColumn.Values.PROJECT)
    @CsvBindByName(column = ExcelCsvColumn.Values.PROJECT)
    private String project;

    @ExcelBindByName(column = ExcelCsvColumn.Values.PRODUCT)
    @CsvBindByName(column = ExcelCsvColumn.Values.PRODUCT)
    private String product;

    @ExcelBindByName(column = ExcelCsvColumn.Values.CLIENT)
    @CsvBindByName(column = ExcelCsvColumn.Values.CLIENT)
    private String client;
}
