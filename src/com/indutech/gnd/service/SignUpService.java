package com.indutech.gnd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;






import org.springframework.transaction.annotation.Transactional;

import com.indutech.gnd.bo.UserListViewBO;
import com.indutech.gnd.dao.LoginDAOImpl;
import com.indutech.gnd.dto.UserDetail;
import com.indutech.gnd.dto.UserRoles;


@Component("signUpService")
public class SignUpService {
	
	@Autowired
	private LoginDAOImpl loginDAO;
	

	public LoginDAOImpl getLoginDAO() {
		return loginDAO;
	}


	public void setLoginDAO(LoginDAOImpl loginDAO) {
		this.loginDAO = loginDAO;
	}

	
	public boolean userNameCheck(String userName)
	{
		boolean nameAvailablity = false;
		List userList = getLoginDAO().checkUserName(userName);
		
		if(!userList.equals(null)&&userList.size()>0)
		{
			System.out.println("name already available");
			nameAvailablity = true;
		}
		
		return nameAvailablity;
	}
	
	@Transactional
	public void addNewRole(String userName,String password,List<String> roles)
	{
		UserDetail userDetails = new UserDetail();
		userDetails.setUserName(userName);
		userDetails.setPassword(password);
		userDetails.setActive(1);
		Long userId = getLoginDAO().insertNewUser(userDetails);
		for(String role : roles)
		{
		
		UserRoles userRoles = new UserRoles();
		userRoles.setUserId(userId);
		userRoles.setAuthority(role);
		getLoginDAO().insertUserRoles(userRoles);
		}
	}
	
	@Transactional
	public void editUser(Long userId,String userName,String password,List<String> roles)
	{
		UserDetail userDetails = new UserDetail();
		userDetails.setId(userId);
		userDetails.setUserName(userName);
		userDetails.setPassword(password);
		userDetails.setActive(1);
		 getLoginDAO().editUser(userDetails);
		for(String role : roles)
		{
		
		UserRoles userRoles = new UserRoles();
		userRoles.setUserId(userId);
		userRoles.setAuthority(role);
		getLoginDAO().insertUserRoles(userRoles);
		}
	}
	
	@Transactional
	public List<UserListViewBO> userList()
	{
		List<Object[]> listObj = getLoginDAO().viewUserTesting();
		List<UserListViewBO> userList = new ArrayList<UserListViewBO>();
		for(int i = 0; i<listObj.size();i++)
		{
			Object[] master=listObj.get(i);
			UserListViewBO userListBO = new UserListViewBO();
			userListBO.setUserId( Long.valueOf(String
					.valueOf(master[0])));
			userListBO.setUserName((String)master[1]);
			userListBO.setPassword((String)master[2]);
		//	userListBO.setAuthority((String)master[3]);
			userList.add(userListBO);
		}
		return userList;
	}
	
	@Transactional
	public void delete(Long userId)
	{
		getLoginDAO().deleteUser(userId);
	}
	
	@Transactional
	public boolean userIsAdmin(Long userId)
	{
		List isAdmin = getLoginDAO().userIsAdmin(userId);
		if(isAdmin.size()>0)
		{
			return true;
		}
		return false;
	}
	
	@Transactional
	public List<UserListViewBO> edit(Long userId)
	{
		List<Object[]> listObj =  getLoginDAO().editUser(userId);
		List<UserListViewBO> userList = new ArrayList<UserListViewBO>();
		for(int i = 0; i<listObj.size();i++)
		{
			Object[] master=listObj.get(i);
			UserListViewBO userListBO = new UserListViewBO();
			userListBO.setUserId( Long.valueOf(String
					.valueOf(master[0])));
			userListBO.setUserName((String)master[1]);
			userListBO.setPassword((String)master[2]);
			userListBO.setAuthority((String)master[3]);
			userList.add(userListBO);
		}
		return userList;
		
	}
	
	@Transactional
	public List<UserListViewBO> userListWithRole()
	{
		List<Object[]> listObj = getLoginDAO().viewUser();
		List<UserListViewBO> userList = new ArrayList<UserListViewBO>();
		for(int i = 0; i<listObj.size();i++)
		{
			Object[] master=listObj.get(i);
			UserListViewBO userListBO = new UserListViewBO();
			userListBO.setUserId( Long.valueOf(String
					.valueOf(master[0])));
			userListBO.setUserName((String)master[1]);
			userListBO.setPassword((String)master[2]);
			userListBO.setAuthority((String)master[3]);
			userList.add(userListBO);
		}
		return userList;
	}
	
/*	@Transactional
	public List<UserListViewBO> userListTesting()
	{
		List<Object[]> listObj = getLoginDAO().viewUser();
		HashMap <String , List<String>>  map = new  HashMap();
		List<UserListViewBO> userList = new ArrayList<UserListViewBO>();
		List<String> authorityList = new ArrayList<String>();
		for(int i = 0; i<listObj.size();i++)
		{
			Object[] master=listObj.get(i);
			
			for(UserListViewBO uBO : userList)
			{
				if(uBO.getUserName().equals((String)master[1]))
				{
					UserListViewBO userListBO = new UserListViewBO();
					userListBO.setUserId( Long.valueOf(String
							.valueOf(master[0])));
					userListBO.setUserName((String)master[1]);
					userListBO.setPassword((String)master[2]);
					userListBO.setAuthority((String)master[3]);
					userList.add(userListBO);
					 authorityList.add(userListBO.getAuthority());
					map.put(uBO.getUserName(), authorityList);
				}
				else{
					UserListViewBO userListBO = new UserListViewBO();
					userListBO.setUserId( Long.valueOf(String
							.valueOf(master[0])));
					userListBO.setUserName((String)master[1]);
					userListBO.setPassword((String)master[2]);
					userListBO.setAuthority((String)master[3]);
					userList.add(userListBO);
				}
			}
			
			
			
			map.put(userListBO.getUserName(), userList);
		}
		return userList;
	}*/
}
