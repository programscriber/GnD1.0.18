package com.indutech.gnd.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;

import com.indutech.gnd.bo.UserBO;

import com.indutech.gnd.dao.GNDAppExpection;
import com.indutech.gnd.dao.LoginDAOImpl;
import com.indutech.gnd.dto.UserDetail;
import com.indutech.gnd.service.LoginServiceImpl;

@Controller
@RequestMapping("/loginuser")
public class LoginController {

	Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	private LoginServiceImpl loginService;
	/*cr no 1 starts*/
	@Autowired
	private LoginDAOImpl loginDAO;
	

	public LoginDAOImpl getLoginDAO() {
		return loginDAO;
	}

	public void setLoginDAO(LoginDAOImpl loginDAO) {
		this.loginDAO = loginDAO;
	}
	/*cr no 1 ends*/
	@RequestMapping(value = "/validateuser", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody ModelAndView login(
			@RequestParam(value = "username", required = false) String userName,
			@RequestParam(value = "password", required = false) String password) throws GNDAppExpection {
		UserBO user = new UserBO();
		boolean isLogin = false;
		String url = "recordsearch";
		try {
			user.setUserName(userName);
			user.setPassword(password);
			isLogin = getLoginService().validateLoginUser(user);
			if(isLogin == false) {
				url = "login";
				return new ModelAndView(url, "message", "Invalid Username/password");
			}
		} catch (GNDAppExpection le) {
			throw new GNDAppExpection("Invalid user");
		}

		return new ModelAndView(url);

	}
	
	@RequestMapping(value = "/recordsearch", method = RequestMethod.GET, produces = "application/json")
	public String login(Principal principal, ModelMap model
			) throws GNDAppExpection {
		
		String username = null;
		try {
//			logger.info(principal.getName());
			username = principal.getName();	
			
			model.addAttribute("username", username);
		} catch(NullPointerException ne) {
			return "login";
		}
		return "recordsearch";

	}
	
	
	@RequestMapping(value="/production", method = RequestMethod.GET)
	public String productPermit(ModelMap model, Principal principal ) {
 
		String name = principal.getName();
		model.addAttribute("username", name);
		model.addAttribute("message", "Spring Security Custom Form example");
		return "home";
 
	}
	
	@RequestMapping(value="/shopfloor", method = RequestMethod.GET)
	public String sbiPermit(ModelMap model, Principal principal ) {
 
		String name = principal.getName();
		model.addAttribute("username", name);
		model.addAttribute("message", "Spring Security Custom Form example");
		return "home";
 
	}
	
	@RequestMapping(value="/helpdesk", method = RequestMethod.GET)
	public String helpDeskPermit(ModelMap model, Principal principal ) {
 
		String name = principal.getName();
		model.addAttribute("username", name);
		model.addAttribute("message", "Spring Security Custom Form example");
		return "home";
 
	}
	
	@RequestMapping(value="/sbi", method = RequestMethod.GET)
	public ModelAndView sbiCustomers() {
		return new ModelAndView("sbirecordsearch");
 
	}
 
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(Principal principal, ModelMap model, HttpSession session, SessionStatus status,@RequestParam(value = "logout", required = false) String logout) {
		try {
			String username = principal.getName();
//			logger.info("username is : "+username);
			if(username != null) {
				model.addAttribute("username", username);
				return "home";
			}
			
		} catch(NullPointerException e) {
			return "login";
		}
		if (logout != null) {
			status.setComplete();
	        session.removeAttribute("username");
	        session.invalidate();
		}
		return "login";
 
	}
	
	
	
	@RequestMapping(value="/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {
 
		model.addAttribute("error", "true");
		return "login";
 
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
		
		return "logout";
 
	}
	
	@RequestMapping(value="/home", method = RequestMethod.GET)
	public String goHome(ModelMap model, Principal principal ) {
 
		String username = null;
		try {
			username = principal.getName();		
			model.addAttribute("username", username);
		} catch(NullPointerException ne) {
			return "login";
		}
		return "home";
 
	}
	
	/*cr no 1 starts*/
	@RequestMapping(value="/selectrole", method = RequestMethod.GET)
	public String selectRole(ModelMap model, Principal principal) {
 
		String username = null;
		try {
			username = principal.getName();		
			List roles = getLoginDAO().selectRole(username);
			model.addAttribute("username", username);
			model.addAttribute("roles", roles);
			
		} catch(NullPointerException ne) {
			return "login";
		}
		return "choose";
 
	}  
	

	@RequestMapping(value="/homepage", method = RequestMethod.GET)
	public ModelAndView gohome(@RequestParam(value = "role", required = true) String role ) {
		System.out.println("inside controller");
		ModelAndView model1 = new ModelAndView("home");
		//String username = null;
		try {
			//username = principal.getName();	
			model1.addObject("username", role);
		} catch(NullPointerException ne) {
			ne.printStackTrace();
			return new ModelAndView("login");
		}
		return model1;
 
	}
	/*cr no 1 ends*/
	@RequestMapping(value = "/loginfailure", method = RequestMethod.GET)
	public ModelAndView loginfailure(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");

		return model;

	}


	public LoginServiceImpl getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginServiceImpl loginService) {
		this.loginService = loginService;
	}
}
