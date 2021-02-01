package org.progmatic.messenger.configurators;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyControllerAdvice {

    Logger LOGGER = LoggerFactory.getLogger(MyControllerAdvice.class);

    @ExceptionHandler(Exception.class)
    public String handleErrors(Exception ex, Model model){
        LOGGER.error("Error caught by handleErrors", ex);
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("trace", ExceptionUtils.getStackTrace(ex));
        return "error";
    }

}
