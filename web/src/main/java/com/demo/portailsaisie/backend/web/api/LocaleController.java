package com.demo.portailsaisie.backend.web.api;

import com.demo.portailsaisie.backend.web.PsvUri;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@RestController
@RequestMapping(PsvUri.API_LOCALE)
@RequiredArgsConstructor
public class LocaleController {

    private final LocaleResolver localeResolver;

    @GetMapping("/change_lang")
    public String changeLanguage(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam("lang") String lang) {
        localeResolver.setLocale(request, response, new Locale(lang));
        return "Language changed to " + lang;
    }
}
