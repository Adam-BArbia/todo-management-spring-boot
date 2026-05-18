package net.guides.springboot.todomanagement.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Controller("error")
public class ErrorController {

	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(HttpServletRequest request, Exception ex) {
		ModelAndView mv = new ModelAndView();

		// Print full stack trace to server console (for logs)
		ex.printStackTrace();

		// Build full stack trace string for display (dev only)
		java.io.StringWriter sw = new java.io.StringWriter();
		java.io.PrintWriter pw = new java.io.PrintWriter(sw);
		ex.printStackTrace(pw);
		String fullStack = sw.toString();

		// Put details into the model for the error.jsp debug view
		mv.addObject("exception", ex.toString());
		mv.addObject("stackTrace", fullStack);
		mv.addObject("url", request.getRequestURL());

		mv.setViewName("error");
		return mv;
	}

}
