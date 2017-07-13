<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html
	class=" js backgroundsize cssanimations csstransitions pointerevents"
	lang="en">
<head>
</head>
<body>
	<ul>
                    <li id="icons"  class="selected"><a href="<c:url value='/loginuser/home'/>">Home</a></li>
                		
                    	<li id="icons"><a href="<c:url value='/search/searchcard'/>">Search</a></li>
                    	
                    	<c:if test='<%=request.isUserInRole("ROLE_HELPDESK")||request.isUserInRole("ROLE_ADMIN")%>' >
						  <li id="icons"><a href="<c:url value='/master/masterdb'/>">Master DB</a></li>
						  
						<!-- <li id="icons"><a href="<c:url value='/master/email'/>">Email</a></li>-->
					  </c:if>  
					  
					  <c:if test='<%=request.isUserInRole("ROLE_WAREHOUSE")||request.isUserInRole("ROLE_HELPDESK")||request.isUserInRole("ROLE_ADMIN")%>' >
						  <li id="prototype"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
					  </c:if>
					  
					   <c:if test='<%=request.isUserInRole("ROLE_SHOPFLOOR")||request.isUserInRole("ROLE_ADMIN")%>' >
						  <li id="prototype"><a href="/GnD/shopfloor/home" method='POST'>Shop floor</a></li>
					    </c:if>  
                    	
                    <c:if test='<%=request.isUserInRole("ROLE_ADMIN")%>' >
                       
                        
<!--                         <li id="styleguide"><a href="#">I/O Config</a></li> -->
<!--                         <li id="screens"><a href="#">Time-Out Config</a></li> -->
						<li id="prototype"><a href="/GnD/notification/notificationview">Notifications</a>
				<!-- <li id="prototype"><a href="/GnD/shopfloor/home">Shop floor</a></li> -->		
						<li id="prototype"><a href="/GnD/signUp/SignUp">Add User</a></li>
<%-- 						 <li id="icons"><a href="<c:url value='/emailcont/editemailTable'/>">Email</a></li> --%>
                                          
					</c:if>					  
					
					   <c:if test='<%=request.isUserInRole("ROLE_WAREHOUSE")||request.isUserInRole("ROLE_ADMIN")%>' >
						  <li id="prototype"><a href="/GnD/shopfloor/warehouse">Warehouse</a></li>
						   </c:if> 
						   
					   <c:if test='<%=request.isUserInRole("ROLE_RTO")||request.isUserInRole("ROLE_ADMIN")%>' >
						  <li id="prototype"  ><a href="/GnD/shopfloor/rto">RTO</a></li>
					  </c:if>
					   <c:if test='<%=request.isUserInRole("ROLE_DATAGEN")||request.isUserInRole("ROLE_ADMIN")%>' >
                    	<li id="icons" ><a href="<c:url value='/qcprocess'/>">QC</a></li>
					</c:if>
					   	<li id="prototype"><a href="/GnD/search/getReport">Reports</a></li>
					      <li id="prototype"><a href="javascript:formSubmit()"> Logout</a>
                    </ul>
	

</body>
</html>