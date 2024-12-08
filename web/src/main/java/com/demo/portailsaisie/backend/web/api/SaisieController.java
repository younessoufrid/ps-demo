package com.demo.portailsaisie.backend.web.api;

import com.demo.portailsaisie.backend.core.service.interfaces.ArticleOccurrenceService;
import com.demo.portailsaisie.backend.web.PsvUri;
import com.demo.portailsaisie.backend.web.dto.mapping.SaisieWoMapper;
import com.demo.portailsaisie.backend.web.dto.saisie.SaisieWo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PsvUri.API_SAISIE)
@RequiredArgsConstructor
public class SaisieController {

    private final ArticleOccurrenceService articleOccurrenceService;
    private final SaisieWoMapper saisieWoMapper;

    @PutMapping(value = "/update_quantity", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaisieWo> updateQuantity(@RequestBody SaisieWo saisieWo) {
        return ResponseEntity.status(HttpStatus.OK).body(this.saisieWoMapper.map(articleOccurrenceService.updateQuantity(this.saisieWoMapper.map(saisieWo))));
    }

    @PutMapping(value = "/hide_line", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaisieWo> hideLine(@RequestBody SaisieWo saisieWo) {
        return ResponseEntity.status(HttpStatus.OK).body(this.saisieWoMapper.map(articleOccurrenceService.hideLine(this.saisieWoMapper.map(saisieWo))));
    }

    @PutMapping(value = "/show_hidden_lines", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> showHiddenLines(@RequestBody SaisieWo saisieWo) {
        return ResponseEntity.status(HttpStatus.OK).body(articleOccurrenceService.showHiddenLines(saisieWo.getIdOrder(), saisieWo.getDate()));
    }

    @PutMapping(value = "/reset_quantity", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaisieWo> resetQuantity(@RequestBody SaisieWo saisieWo) {
        return ResponseEntity.status(HttpStatus.OK).body(this.saisieWoMapper.map(articleOccurrenceService.resetQuantity(this.saisieWoMapper.map(saisieWo))));
    }

    @PutMapping(value = "/reset_month_quantity", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SaisieWo>> resetOrderQuantity(@RequestBody SaisieWo saisieWo) {
        return ResponseEntity.status(HttpStatus.OK).body(saisieWoMapper.map(articleOccurrenceService.resetOrderQuantity(saisieWo.getIdOrder(), saisieWo.getDate())));
    }

    @PutMapping(value = "/text_ligne", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaisieWo> updateTextLigne(@RequestBody SaisieWo saisieWo) {
        return ResponseEntity.status(HttpStatus.OK).body(this.saisieWoMapper.map(articleOccurrenceService.updateTextLigne(this.saisieWoMapper.map(saisieWo))));
    }

    @PutMapping(value = "/validate/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> validateMonthQuantity(@PathVariable Long orderId) {
        return ResponseEntity.status(HttpStatus.OK).body(articleOccurrenceService.validateMonthQuantity(orderId));
    }
}
