package com.demo.portailsaisie.backend.web.api;

import com.demo.portailsaisie.backend.utils.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackages = {"com.demo.portailsaisie.backend.web.api"})
public class AbstractController {
    private final static Logger logger = LoggerFactory.getLogger(AbstractController.class);

    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<PsvExceptionDTO> handleException(Exception ex) {
        //unknown exception
        logger.error("exception error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new PsvExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                ProcessErrorEnum.TECHNICAL_ERROR.name(),
                                messageSource.getMessage(ProcessErrorEnum.TECHNICAL_ERROR.getKey(), null, LocaleContextHolder.getLocale()),
                                ex.getClass().getSimpleName()
                        )
                );
    }

    /**
     * gérer les exception métiers
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({ProcessException.class, IllegalArgumentException.class})
    protected ResponseEntity<Object> handleBusinessException(ProcessException ex) {
        logger.error("ProcessException error", ex);
        return ResponseEntity.status(ex.getHttpStatusCode())
                .body(
                        new PsvExceptionDTO(ex.getHttpStatusCode(),
                                ex.getProcessError().name(),
                                ex.getMessage(),
                                ex.getClass().getSimpleName()
                        )
                );
    }

    /**
     * gérer les exception techniques
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({TechnicalException.class})
    protected ResponseEntity<Object> handleTechnicalException(TechnicalException ex) {
        logger.error("Technical error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new PsvExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                ProcessErrorEnum.TECHNICAL_ERROR.name(),
                                messageSource.getMessage(ProcessErrorEnum.TECHNICAL_ERROR.getKey(), null, LocaleContextHolder.getLocale()),
                                ex.getClass().getSimpleName()
                        )
                );
    }

    /**
     * gérer les exception techniques
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({NotFoundException.class})
    protected ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        logger.error("Technical error", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        new PsvExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                ProcessErrorEnum.DATA_NOT_FOUND.name(),
                                messageSource.getMessage(ProcessErrorEnum.DATA_NOT_FOUND.getKey(), null, LocaleContextHolder.getLocale()),
                                ex.getClass().getSimpleName()
                        )
                );
    }

    // D'autres handlers à ajouter si besoin
    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    protected ResponseEntity<Object> handleAuthenticationException(BadCredentialsException ex) {
        logger.error("AuthenticationException error", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        new PsvExceptionDTO(HttpStatus.BAD_REQUEST.value(),
                                ProcessErrorEnum.BAD_AUTH_DATA.name(),
                                messageSource.getMessage(ProcessErrorEnum.BAD_AUTH_DATA.getKey(), null, LocaleContextHolder.getLocale()),
                                ex.getClass().getSimpleName()));
    }
}
