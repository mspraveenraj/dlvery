package com.project.dlvery.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppErrorController implements ErrorController {

	@RequestMapping({ "/{path:[^\\.]*}", "/{path1:[^\\.]*}/{path2:[^\\.]*}", "/{path1:[^\\.]*}/{path2:[^\\.]*}/{path3:[^\\.]*}"})
	    public String redirect() {
	        return "forward:/";
	    } 
	
    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String handleError(HttpServletRequest request) {
        //Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        //for brevity, only handling 404
        //if (status != null) {
            ///Integer statusCode = Integer.valueOf(status.toString());
            //if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "forward:/notFound";
            //}
        //}
       // return "forward:/notFound";
    }

 
    @Override
    public String getErrorPath() {
        return PATH;
    }
}