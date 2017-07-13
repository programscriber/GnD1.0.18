package com.indutech.gnd.controller;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.indutech.gnd.bo.UserListViewBO;
import com.indutech.gnd.service.SignUpService;





@Controller("signUpCont")
@RequestMapping(value = "/signUp")

public class SignUpController {
	

	Logger logger = Logger.getLogger(SignUpController.class);
	
	@Autowired
	private SignUpService signUpService;
	
	
	public SignUpService getSignUpService() {
		return signUpService;
	}


	public void setSignUpService(SignUpService signUpService) {
		this.signUpService = signUpService;
	}

	@RequestMapping(value = "/SignUp" ,method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ModelAndView signUp(ModelMap model, Principal principal)
	{
		try{
			String username = principal.getName();
		}
		catch (NullPointerException ne)
		{
			return new ModelAndView("login");
		}
		return new ModelAndView("signUp");
	}

	@RequestMapping(value = "/newSignUp", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody ModelAndView goSignUp(ModelMap model, Principal principal,
			@RequestParam(value = "username", required = true) String userName,
			@RequestParam(value = "password",required = true) String password,
			@RequestParam(value = "repassword",required = true) String finalPassword,
			@RequestParam(value = "admin", required = false) boolean admin,
			@RequestParam(value = "datagen", required = false) boolean datagen,
			@RequestParam(value = "helpdesk", required = false) boolean helpdesk,
			@RequestParam(value = "rto", required = false) boolean rto,
			@RequestParam(value = "shopfloor", required = false) boolean shopfloor,
			@RequestParam(value = "warehouse", required = false) boolean warehouse)
	{
		
		try{
			String username = principal.getName();
		}
		catch (NullPointerException ne)
		{
			return new ModelAndView("login");
		}
		ModelAndView model1 = new ModelAndView("signUp");
		
			boolean nameAlreadyAvailable = getSignUpService().userNameCheck(userName);
			
			if(nameAlreadyAvailable == true)
			{
				
				model1.addObject("nameAlreadyAvailable", nameAlreadyAvailable);
				return model1;
			}
			else
			{
				if(!password.equals(finalPassword))
				{
					boolean passMisMatch = true;
					model1.addObject("passwordMismatches", passMisMatch);
					return model1;
				}
				List<String> roles = new ArrayList<String>();
				if(admin == true)
					roles.add("ROLE_ADMIN");
				if(datagen == true)
					roles.add("ROLE_DATAGEN");
				if(helpdesk == true)
					roles.add("ROLE_HELPDESK");
				if(rto == true)
					roles.add("ROLE_RTO");
				if(shopfloor == true)
					roles.add("ROLE_SHOPFLOOR");
				if(warehouse == true)
					roles.add("ROLE_WAREHOUSE");
				getSignUpService().addNewRole(userName,password,roles);
				model1.addObject("nameAlreadyAvailable", nameAlreadyAvailable);
			}
			
			
		return model1;
	}
	
	@RequestMapping(value = "/editUser", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody ModelAndView editUser(ModelMap model, Principal principal,
			@RequestParam(value = "userId", required = true) Long userId,
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password",required = true) String password,
			@RequestParam(value = "finalPassword",required = true) String finalPassword,
			@RequestParam(value = "admin", required = false) boolean admin,
			@RequestParam(value = "datagen", required = false) boolean datagen,
			@RequestParam(value = "helpdesk", required = false) boolean helpdesk,
			@RequestParam(value = "rto", required = false) boolean rto,
			@RequestParam(value = "shopfloor", required = false) boolean shopfloor,
			@RequestParam(value = "warehouse", required = false) boolean warehouse)
	{
		
		try{
			String username = principal.getName();
		}
		catch (NullPointerException ne)
		{
			return new ModelAndView("login");
		}
		ModelAndView model1 = new ModelAndView("edituser");
		
			
				if(!password.equals(finalPassword))
				{
					boolean passMisMatch = true;
					model1.addObject("passwordMismatches", passMisMatch);
					return model1;
				}
				List<String> roles = new ArrayList<String>();
				if(admin == true)
					roles.add("ROLE_ADMIN");
				if(datagen == true)
					roles.add("ROLE_DATAGEN");
				if(helpdesk == true)
					roles.add("ROLE_HELPDESK");
				if(rto == true)
					roles.add("ROLE_RTO");
				if(shopfloor == true)
					roles.add("ROLE_SHOPFLOOR");
				if(warehouse == true)
					roles.add("ROLE_WAREHOUSE");
				getSignUpService().editUser(userId,userName,password,roles);
				
				if(userName.equalsIgnoreCase(principal.getName()) && admin==false)
				{
					return new ModelAndView("login");
				}
			
			
			
				return new ModelAndView("redirect:/signUp/userList");
	}
	
	@RequestMapping(value = "/loginPage", produces = "application/json")
	public @ResponseBody ModelAndView loginPage(ModelMap model, Principal principal)
	{
		return new ModelAndView("login");
	}
	
	@RequestMapping(value = "/userList", produces = "application/json")
	public @ResponseBody ModelAndView userList(ModelMap model, Principal principal)
	{
		ModelAndView view = new ModelAndView("userviewlist");
		List<UserListViewBO> userList = new ArrayList<UserListViewBO>();
		List<UserListViewBO> userListWithRole = new ArrayList<UserListViewBO>();
		try{
			String username = principal.getName();
			 userList = getSignUpService().userList();
			 userListWithRole = getSignUpService().userListWithRole();
			 
		}
		catch (NullPointerException ne)
		{
			ne.printStackTrace();
			return new ModelAndView("login");
		}
		view.addObject("userList", userList);
		view.addObject("userListWithRole", userListWithRole);
		return view;
	}
	
	@RequestMapping(value = "/delete",method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ModelAndView delete(ModelMap model, Principal principal,@RequestParam(value = "userId", required = true) Long userId,@RequestParam(value = "userName", required = true) String userName)
	{
		
		
		try{
			String username = principal.getName();
			boolean admin = getSignUpService().userIsAdmin(userId);
			if(admin == true && !username.equalsIgnoreCase(userName))
			{
				ModelAndView view = new ModelAndView("userviewlist");
				List<UserListViewBO> userList = new ArrayList<UserListViewBO>();
				List<UserListViewBO> userListWithRole = new ArrayList<UserListViewBO>();
		
					 userList = getSignUpService().userList();
					 userListWithRole = getSignUpService().userListWithRole();
	
				view.addObject("userList", userList);
				view.addObject("userListWithRole", userListWithRole);
				view.addObject("userIsAdmin", admin);
				return view;
				
				
			}
			getSignUpService().delete(userId);
			if(username.equalsIgnoreCase(userName))
			{
				return new ModelAndView("login");
			}
		}
		catch (NullPointerException ne)
		{
		
			return new ModelAndView("login");
		}
		
		return new ModelAndView("redirect:/signUp/userList");
	}
	
	@RequestMapping(value = "/edit",method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ModelAndView edit(ModelMap model, Principal principal,@RequestParam(value = "userId", required = true) Long userId,@RequestParam(value = "userName", required = true) String userName)
	{
		ModelAndView view = new ModelAndView("edituser");
		List<UserListViewBO> userList = new ArrayList<UserListViewBO>();
		UserListViewBO userBO  = new UserListViewBO();
		try{
			String username = principal.getName();
			boolean admin = getSignUpService().userIsAdmin(userId);
			if(admin == true && !username.equalsIgnoreCase(userName))
			{
				ModelAndView view1 = new ModelAndView("userviewlist");
				List<UserListViewBO> userListWithRole = new ArrayList<UserListViewBO>();
		
					 userList = getSignUpService().userList();
					 userListWithRole = getSignUpService().userListWithRole();
	
				view1.addObject("userList", userList);
				view1.addObject("userListWithRole", userListWithRole);
				view1.addObject("userIsAdmin", admin);
				return view1;
			}
		userList = 	getSignUpService().edit(userId);
		userBO = userList.get(0);
		}
		catch (NullPointerException ne)
		{
		
			return new ModelAndView("login");
		}
		view.addObject("editUser", userBO);
		view.addObject("authorityList", userList);
		return view;
	}
}
