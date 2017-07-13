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
    </head>



    <body>
    
        <style>
            .right-content{
                /*text-align: center;*/
                margin-left: auto;
                margin-right: auto;
                left: 18%;
                float: none;
                border-left: medium transparent;
            }
            .table-header {
                width: 100%;
            }
            .col-md-4{
                width: 33.333%;
            }
            textarea {
   				 width: 280px;
  				  height: 62px;
}
        </style>

        <div class="page-header">
            <div class="page-header-wrapper container">
                <div class="main-logo">
                    Giesecke &amp; Devrient
                </div>
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
						<li id="prototype" class="selected"><a href="/GnD/signUp/SignUp">Edit User</a></li>
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
        <form  action="/GnD/signUp/editUser" method="post">
                <div class="right-content record">
                	 <!-- <input type="button" class="btn btn-l btn-blue" onclick="history.back()" value ="Back"/> -->
                	 <a href = "/GnD/signUp/userList"  class="btn btn-blue top-margin padding-l">Back</a>
                    <h2 class="blue-text">Edit User Details</h2>
                    <c:if test = "${passwordMismatches eq true }">
                    <font color="red"> Password mismatches in two fields! </font>
                    </c:if>
                    <form>
                                  
                        <div class="row right-con">
                            <div class="col-md-4">
                                <span class="blue-text" >User ID</span>
                                <div class="select-menu select-menu-native select-menu-light">
                                    <input  type="text" name="userId" class="text-field product" value="${editUser.userId}" readonly />
                                </div>                        
                            </div>
                            <div class="col-md-4">
                                <span class="blue-text" >User Name</span>
                                <div class="select-menu select-menu-native select-menu-light">
                                    <input  type="text" name="userName" class="text-field" value="${editUser.userName}" readonly />
                                </div>                        
                            </div>
                            <div class="col-md-4">
                                <span class="blue-text" >Password</span>
                                <div class="select-menu select-menu-native select-menu-light">
                                    <input type="password"  name="password" class="text-field" value="${editUser.password}" required />
                                </div>                        
                            </div>
                            <br><br><br>
                            <div class="second-line">
                                <div class="col-md-4">
                                    <span class="blue-text" >Re-Enter Password</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input  type="password" name="finalPassword" class="text-field product" value="${editUser.password}" required />
                                    </div>                        
                                </div>
                                </div>
                                
                            </div>
                            
                                <h4>Role:</h4><br>
                                 <c:set var="admin" value="false" />
								<c:forEach var="userListVal" items="${authorityList}">   
  								<c:if test="${userListVal.authority  == 'ROLE_ADMIN'}">
  								
   								 <c:set var="admin" value="true" />
  								</c:if>
								</c:forEach>
								
 								 <c:if test="${admin eq 'true'}">
  								<input type="checkbox" name="admin"  checked  > Admin<br>
  								</c:if>
  								 <c:if test="${admin eq 'false'}">
    							<input type="checkbox" name="admin"   > Admin<br>
  								</c:if>
  								
  								
  								
  								<c:set var="datagen" value="false" />
								<c:forEach var="userListVal" items="${authorityList}">   
  								<c:if test="${userListVal.authority  == 'ROLE_DATAGEN'}">
  								
   								 <c:set var="datagen" value="true" />
  								</c:if>
								</c:forEach>
								
 								 <c:if test="${datagen eq 'true'}">
  								<input type="checkbox" name="datagen"  checked  > datagen<br>
  								</c:if>
  								 <c:if test="${datagen eq 'false'}">
    							<input type="checkbox" name="datagen"   > datagen<br>
  								</c:if>
  								
  								
  								<c:set var="helpdesk" value="false" />
								<c:forEach var="userListVal" items="${authorityList}">   
  								<c:if test="${userListVal.authority  == 'ROLE_HELPDESK'}">
  								
   								 <c:set var="helpdesk" value="true" />
  								</c:if>
								</c:forEach>
								
 								 <c:if test="${helpdesk eq 'true'}">
  								<input type="checkbox" name="helpdesk"  checked  > helpdesk<br>
  								</c:if>
  								 <c:if test="${helpdesk eq 'false'}">
    							<input type="checkbox" name="helpdesk"   > helpdesk<br>
  								</c:if>
                                
                                
                                <c:set var="rto" value="false" />
								<c:forEach var="userListVal" items="${authorityList}">   
  								<c:if test="${userListVal.authority  == 'ROLE_RTO'}">
  								
   								 <c:set var="rto" value="true" />
  								</c:if>
								</c:forEach>
								
 								 <c:if test="${rto eq 'true'}">
  								<input type="checkbox" name="rto"  checked  > rto<br>
  								</c:if>
  								 <c:if test="${rto eq 'false'}">
    							<input type="checkbox" name="rto"   > rto<br>
  								</c:if>
  								
  								
  								<c:set var="shopfloor" value="false" />
								<c:forEach var="userListVal" items="${authorityList}">   
  								<c:if test="${userListVal.authority  == 'ROLE_SHOPFLOOR'}">
  								
   								 <c:set var="shopfloor" value="true" />
  								</c:if>
								</c:forEach>
								
 								 <c:if test="${shopfloor eq 'true'}">
  								<input type="checkbox" name="shopfloor"  checked  > shopfloor<br>
  								</c:if>
  								 <c:if test="${shopfloor eq 'false'}">
    							<input type="checkbox" name="shopfloor"   > shopfloor<br>
  								</c:if>
                                
                                
                                
                                <c:set var="warehouse" value="false" />
								<c:forEach var="userListVal" items="${authorityList}">   
  								<c:if test="${userListVal.authority  == 'ROLE_WAREHOUSE'}">
  								
   								 <c:set var="warehouse" value="true" />
  								</c:if>
								</c:forEach>
								
 								 <c:if test="${warehouse eq 'true'}">
  								<input type="checkbox" name="warehouse"  checked  > warehouse<br>
  								</c:if>
  								 <c:if test="${warehouse eq 'false'}">
    							<input type="checkbox" name="warehouse"   > warehouse<br>
  								</c:if>
             <!--                 <c:forEach var="userListVal" items="${authorityList}">     
                             <c:if test="${userListVal.authority == 'ROLE_ADMIN'}"> 
                        <input type="checkbox" name="admin"  <c:if test="${userListVal.authority == 'ROLE_ADMIN'}"> checked </c:if>  > Admin<br>
                        </c:if>
                        </c:forEach>
                        <c:forEach var="userListVal" items="${authorityList}"> 
                        <c:if test="${userListVal.authority == 'ROLE_DATAGEN'}">
 						 <input type="checkbox" name="datagen"  <c:if test="${userListVal.authority == 'ROLE_DATAGEN'}"> checked </c:if> > DataGen<br>
 						 </c:if>
                        </c:forEach>
                         <c:forEach var="userListVal" items="${authorityList}"> 
                         <c:if test="${userListVal.authority == 'ROLE_HELPDESK'}">
 						  <input type="checkbox" name="helpdesk" <c:if test="${userListVal.authority == 'ROLE_HELPDESK'}"> checked </c:if> > Helpdesk<br>
 						  </c:if>
                        </c:forEach>
                        <c:forEach var="userListVal" items="${authorityList}"> 
                        <c:if test="${userListVal.authority == 'ROLE_RTO'}">
 						  <input type="checkbox" name="rto"   <c:if test="${userListVal.authority == 'ROLE_RTO'}"> checked </c:if>> RTO<br>
 						  </c:if>
                        </c:forEach>
                         <c:forEach var="userListVal" items="${authorityList}"> 
                         <c:if test="${userListVal.authority == 'ROLE_SHOPFLOOR'}">
 						 <input type="checkbox" name="shopfloor"  <c:if test="${userListVal.authority == 'ROLE_SHOPFLOOR'}"> checked </c:if>> Shopfloor<br>
 						 </c:if>
                        </c:forEach>
                        <c:forEach var="userListVal" items="${authorityList}"> 
                        <c:if test="${userListVal.authority == 'ROLE_WAREHOUSE'}">
 						  <input type="checkbox" name="warehouse" <c:if test="${userListVal.authority == 'ROLE_WAREHOUSE'}"> checked </c:if>> Warehouse<br>
 						  </c:if>
                               </c:forEach>     -->
                                 <input type="submit" class="btn btn-blue top-margin padding-l" value="Edit"  id="checkBtn"/>
                        </div>   
                      </form>
                    <div class="clear"></div>
                    
        <div class="clear"></div>
        <div id="footer" class="page-footer">
            <div class="container">
                <div class="footer-columns">
                    <div class="footer-left-column">
                        <div class="footer-version">
                            <strong>Web Tracking Tool</strong> Version 1.0.11
                        </div>
                        <ul class="footer-links">
                            <li><a href="#">Contact</a></li>
                            <li><a href="#">Site Terms</a></li>
                            <li><a href="#">Privacy</a></li>
                        </ul>
                    </div>
                    <div class="footer-right-column">
                        <div class="footer-logo">
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
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