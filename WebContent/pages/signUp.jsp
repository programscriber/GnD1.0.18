<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class=" js backgroundsize cssanimations csstransitions pointerevents" lang="en"><head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">

        <title>G & D</title>
        <link href="<c:url value='/resources/css/blazer.css'/>" media="screen, projection" rel="stylesheet" type="text/css">
        <link href="<c:url value='/resources/css/style.css'/>" media="screen, projection" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="<c:url value='/resources/js/modernizr-2.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/resources/js/jquery-1.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/resources/js/blazer.js'/>"></script>
        <link rel="stylesheet" href="<c:url value='/resources/css/jquery-ui.css'/>">
        <script src="<c:url value='/resources/js/jquery-1.10.2.js'/>"></script>
        <script src="<c:url value='/resources/js/jquery-ui.js'/>"></script>
        <style type="text/css">
        <style>
			.error {
				padding: 15px;
				margin-bottom: 20px;
				border: 1px solid transparent;
				border-radius: 4px;
				color: #a94442;
				background-color: #f2dede;
				border-color: #ebccd1;
			}
			
			.msg {
				padding: 15px;
				margin-bottom: 20px;
				border: 1px solid transparent;
				border-radius: 4px;
				color: #31708f;
				background-color: #d9edf7;
				border-color: #bce8f1;
			}
			
			#login-box {
				width: 300px;
				padding: 20px;
				margin: 100px auto;
				background: #fff;
				-webkit-border-radius: 2px;
				-moz-border-radius: 2px;
				border: 1px solid #000;
			}
        </style>
        
    </head>

    <body>   	
   
        
        <div class='login-left'>
            <img src="<c:url value="/resources/img/logo-white.png"/>" />
            <div class="clear"></div><br><br>
            <div class="clear"></div><br>
<!--             <span class="text">Some project description here.Some project description here.Some project description here.Some project description here.Some project description here.Some project description here.Some project description here.Some project description here.</span> -->
        </div>
         
        <div class='login-right'>
<!--        		<form action="/GnD/loginuser/validateuser" method="post"> -->
					<form name='loginForm'
			action="/GnD/signUp/newSignUp" method='POST' >
            <div class="center-box">
                <div class="col-md-12">
                    <div class="col-md-12">
                    
	                     <c:if test="${nameAlreadyAvailable eq 'true'}">
							<div class="errorblock">
								<font color="red"> UserName is already available.Please Try New UserName..</font>
							
							</div>
						</c:if>
						<c:if test="${nameAlreadyAvailable eq 'false'}">
							<div class="errorblock">
								Successfully added.. 
							
							</div>
						</c:if>
						 <c:if test="${passwordMismatches eq 'true'}">
							<div class="errorblock">
								<font color="red"> Password mismatches in two fields! </font>
							
							</div>
						</c:if>
						 
                        <h2 class="blue-text padding-l">Add New User</h2>
                        
                        <div class="col-md-8 top-margin">
                            <span class="blue-text" >Username</span><br>
                            
                                <input type="search" name="username"  required  />
                                                
                        </div>
                        <div class="col-md-8 top-margin">
                            <span class="blue-text" >Password</span><br>
                            
                                <input type="password" name="password"  autocomplete = "new-password" required/>
                                               
                        </div>
						<div class="col-md-8 top-margin">
                            <span class="blue-text" >Re-Enter Password</span><br>
                           
                                <input type="password" name="repassword" autocomplete = "new-password" required/>
                                                 
                        </div>
                        <h4>Assign a Role:</h4><br>
                        <input type="checkbox" name="admin" > Admin<br>
 						 <input type="checkbox" name="datagen" > DataGen<br>
 						  <input type="checkbox" name="helpdesk"> Helpdesk<br>
 						  <input type="checkbox" name="rto"  > RTO<br>
 						 <input type="checkbox" name="shopfloor"  > Shopfloor<br>
 						  <input type="checkbox" name="warehouse" > Warehouse<br>
 						  
                        
						
                    </div>
                    
                    <input type="submit" class="btn btn-blue top-margin padding-l" value="Add"  id="checkBtn"/>
                    <a href = "/GnD/signUp/userList"  class="btn btn-blue top-margin padding-l">Users List</a>
                   
                   
                   
                </div>
<!-- 					<p><a href="../j_spring_security_logout">Logout</a></p> -->
                </div>
                
			</form>
			
            </div>
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
           <c:url value="/j_spring_security_logout" var="logoutUrl" />
						<form action="${logoutUrl}" method="post" id="logoutForm">
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</form>
                </div> 	
<!--         </div> -->
<script type="text/javascript">
$(document).ready(function () {
    $('#checkBtn').click(function() {
      checked = $("input[type=checkbox]:checked").length;

      if(!checked) {
        alert("Every field is mandatory.");
        return false;
      }

    });
});
function formSubmit() {
	document.getElementById("logoutForm").submit();
}

</script>
    </body>
</html>
    