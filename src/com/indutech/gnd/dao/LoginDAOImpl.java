package com.indutech.gnd.dao;

import java.util.List;

import javax.security.auth.login.LoginException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.indutech.gnd.controller.CardStateManagerController;
import com.indutech.gnd.dto.CoreFiles;
import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.User;
import com.indutech.gnd.dto.UserDetail;
import com.indutech.gnd.dto.UserRoles;

@Repository("loginDAO")
public class LoginDAOImpl implements LoginDAO {

	@Autowired
	private SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Override
	public boolean validateLoginUser(User user) throws GNDAppExpection {
		List users = null;
		try {
			users = getSessionFactory()
					.getCurrentSession()
					.createQuery(
							"from User u where u.userName ='"
									+ user.getUserName()
									+ "' and u.password ='"
									+ user.getPassword()+"'").list();
		} catch (HibernateException e) {
		}
		return users.size()>0;
	}
	/*cr no 1 starts*/
	@Transactional
	public List checkUserName(String userName)
	{
		List resultList = null;
		try
		{
			resultList=getSessionFactory().getCurrentSession().createSQLQuery("select * from users where USERNAME like '%"+userName+"%'").list();
			System.out.println(resultList.size());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return resultList;
	}
	
	@Transactional
	public Long insertNewUser(UserDetail userDetails)
	{
		getSessionFactory().getCurrentSession().save(userDetails);
		return userDetails.getId();
	}
	
	@Transactional
	public Long editUser(UserDetail userDetails)
	{
		Query q1 = getSessionFactory().getCurrentSession().createSQLQuery("delete from user_roles where  user_roles.user_id = "+userDetails.getId());
		int result = q1.executeUpdate();
		getSessionFactory().getCurrentSession().saveOrUpdate(userDetails);
		
		return userDetails.getId();
	}
	@Transactional
	public void insertUserRoles(UserRoles userRoles)
	{
		getSessionFactory().getCurrentSession().save(userRoles);
		
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public List<UserDetail> checkPassword(String userName,String password)
	{
		List resultList = null;
		try
		{
			resultList= getSessionFactory().getCurrentSession().createCriteria(UserDetail.class)
					.add(Restrictions.like("userName", "%"+userName+"%"))
					.add(Restrictions.like("password", "%"+password+"%"))
					.list();
			System.out.println(resultList.size());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<String> selectRole(String userName)
	{
		List<String> role = null;
		try
		{
			role = getSessionFactory().getCurrentSession().createSQLQuery("select AUTHORITY from user_roles ur,users us where us.username = '"+userName+"' and ur.USER_ID = us.USER_ID").list();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return role;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List userIsAdmin(Long userId)
	{
		List role = null;
		try
		{
			role = getSessionFactory().getCurrentSession().createSQLQuery("select AUTHORITY from user_roles where AUTHORITY = 'ROLE_ADMIN' and USER_ID="+userId).list();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return role;
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Object[]> viewUser()
	{
		List<Object[]> userList = null;
		try
		{
			userList = getSessionFactory().getCurrentSession().createSQLQuery("select users.USER_ID,users.USERNAME,users.PASSWORD,user_roles.authority from users ,user_roles  where users.user_id = user_roles.user_id").list();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return userList;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public void deleteUser(Long userId)
	{
		
		try
		{
			Query q1 = getSessionFactory().getCurrentSession().createSQLQuery("delete from user_roles where  user_roles.user_id = "+userId);
			int result = q1.executeUpdate();
			Query q2 = getSessionFactory().getCurrentSession().createSQLQuery("delete from users where  users.user_id = "+userId);
			int row = q2.executeUpdate();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Object[]> editUser(Long userId)
	{
		List<Object[]> userList = null;
		try
		{
			userList = getSessionFactory().getCurrentSession().createSQLQuery("select users.USER_ID,users.USERNAME,users.PASSWORD,user_roles.authority from users ,user_roles  where users.user_id = user_roles.user_id and users.user_id = "+userId).list();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return userList;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Object[]> viewUserTesting()
	{
		List<Object[]> userList = null;
		try
		{
			userList = getSessionFactory().getCurrentSession().createSQLQuery("select users.USER_ID,users.USERNAME,users.PASSWORD from users").list();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return userList;
	}
	/*cr no 1 ends*/

}
