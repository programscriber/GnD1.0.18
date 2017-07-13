<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html
	class=" js backgroundsize cssanimations csstransitions pointerevents"
	lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">

<title>G & D</title>
<link href="<c:url value='/resources/css/blazer.css'/>"
	media="screen, projection" rel="stylesheet" type="text/css">
<link href="<c:url value='/resources/css/style.css'/>"
	media="screen, projection" rel="stylesheet" type="text/css">
<script type="text/javascript"
	src="<c:url value='/resources/js/modernizr-2.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jquery-1.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/blazer.js'/>"></script>
<link rel="stylesheet"
	href="<c:url value='/resources/css/jquery-ui.css'/>">
<script src="<c:url value='/resources/js/jquery-1.10.2.js'/>"></script>
<script src="<c:url value='/resources/js/jquery-ui.js'/>"></script>


<link href="<c:url value='/resources/css/infragistics.theme.css'/>"
	rel="stylesheet" />
<link href="<c:url value='/resources/css/infragistics.css'/>"
	rel="stylesheet" />


<!-- Ignite UI Required Combined JavaScript Files -->
<script src="<c:url value='/resources/js/infragistics.core.js'/>"></script>
<script src="<c:url value='/resources/js/infragistics.lob.js'/>"></script>

<style>
#checkboxSelectCombo, #checkboxSelectCombo:hover {
	/*background-color: #f7f8f8 !important;*/
	border: 1px solid #9db5cd !important;
}

.ui-igcombo-buttonicon, .ui-igcombo-clearicon {
	margin-left: 3px;
	margin-top: -7px;
	position: absolute;
	top: 50%;
}

.ui-icon-triangle-1-s {
	background-position: 0;
}

.ui-icon {
	background-position: 0;
	height: 16px;
	width: 16px;
}

#tableid {
	width: 100%;
	max-width: 100%;
	overflow-x: scroll;
	overflow-y: scroll;
}

.ui-igcombo-clear {
	display: none !important;
}
.table {
	position: relative;
   width: 100%;
  top: 120px;
  border-collapse: collapse;
}
.table td,
.table th {
position:relative;
  padding: 0;
  padding: 10px 9px 6px;
  border: 1px solid #dadbdc;
  border-bottom: 0;
  white-space: pre-wrap;
}

  select {
  font-size: 12px;
    border:1px solid #ccc;
    vertical-align:top;
    height:20px;
}
input, select{
  box-sizing: border-box;
  -moz-box-sizing: border-box;
  -webkit-box-sizing: border-box;
}
</style>
</head>
<body>
 
	<div class="page-header" style="position: fixed width:100%">
		<div class="page-header-wrapper container">
			<div class="main-logo">Giesecke &amp; Devrient</div>
	
		 <div class="tab-menu dark">
                <ul>
                
                		<li id="icons"  ><a href="<c:url value='/loginuser/home'/>">Home</a></li>
                		
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
						<li id="prototype" class="selected"><a href="/GnD/signUp/SignUp">Add User</a></li>
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
              
                </div> 	
                	
           </div>
           <c:url value="/j_spring_security_logout" var="logoutUrl" />
						<form action="${logoutUrl}" method="post" id="logoutForm">
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</form>
       </div>

       <div class="clear"></div>
     <c:if test = "${userIsAdmin eq true}">
      <script> alert("You can't delete and edit other Admin user! ")</script>
      </c:if>
                    <table class="table">
                    <thead>
                            <tr>
                            	
                              
                               <th>UserId</th>
                                <th>Username</th>
                                <th>Password</th>
                                <th>Role</th>
                              	<th>Delete</th>
                            </tr>
                        </thead>
                        <tbody>
                      
                        <c:forEach var="userListVal" items="${userList}">
                            <tr  >
                         		 
                        		<td ><a href = "/GnD/signUp/edit?userId=${userListVal.userId}&userName=${userListVal.userName}" >${userListVal.userId}</a></td>      
                                <td >${userListVal.userName}</td>    
                                <c:set var="admin" value="false" />    
                                 <c:forEach var="userListWithRoleVal" items="${userListWithRole}">
                             <c:if test="${userListWithRoleVal.userName == userListVal.userName}"> 
                             <c:if test="${userListWithRoleVal.authority == 'ROLE_ADMIN'}">
                             <c:set var="admin" value="true" />
                             </c:if> 
  							  	</c:if> 
								</c:forEach> 
								 <c:if test="${admin eq 'true'}">                                                           
                                <td>*******</td>     
                                  </c:if>    
                                  <c:if test="${admin eq 'false'}">  
                                 <td> ${userListVal.password }</td>
                                    </c:if>                                                    
                                <td>   
                             <select style="width: 200px">  
  							  <c:forEach var="userListWithRoleVal" items="${userListWithRole}">
                             <c:if test="${userListWithRoleVal.userName == userListVal.userName}"> 
                               <option value="">    ${userListWithRoleVal.authority}     </option> 
  							  	</c:if> 
								</c:forEach> 
                                </select>  
									 </td>                                                         
                                
                                <td><a href ="/GnD/signUp/delete?userId=${userListVal.userId}&userName=${userListVal.userName}" onclick="return confirm('Are you sure you want to delete this User?');">Delete</a></td>  
                              
                                                                                                     
                            </tr> 
                            </c:forEach>                                                                     
                        </tbody>
                    </table>
<script>
        
        function formSubmit() {
    		document.getElementById("logoutForm").submit();
    	}
     
         </script>
              </body>
              </html>  

                    
