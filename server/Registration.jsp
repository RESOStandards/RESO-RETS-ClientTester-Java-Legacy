<%@ page language="java" %>
<%@ taglib uri="struts-html.tld" prefix="html" %>
<%@ taglib uri="struts-logic.tld" prefix="logic" %>
<%@ taglib uri="struts-bean.tld" prefix="bean"%>

<html>
	<head>
	<title>  Registration Page  </title>
    <link rel="stylesheet" type="text/css" href="main.css" />
	</head>
<body>
	<h4 align="right"><html:link  href="Logon.jsp">Logout </html:link></h4>
	<h1 align="center"> RESO Client Compliance Registration Form </h1>
 		<html:form action="/saveRegistration">
 		  <table width="50%" align="center">
 		  <colgroup span="2">
                        <col width="15%"/>
                        <col width="35%"/>
          </colgroup>

			<tr>          
               Welcome to the RESO Client Compliance Testing platform.
               Please fill in all of the fields listed below. <br>
               Your <b>Login and password </b> will be used by your RETS Client as a login name and password 
               to connect to the RETS Server used for testing.  These will be provided to you by the RESO Certification administrator upon completion of your application process. They will also server as a login
               and password to the Client Test Summary screen.  Detailed instructions about the
               testing process will follow after you have hit "Submit" and are successfully
               registered.</p>
               <br>
			</tr>
			
 		    <tr>   
 		    	<th>Company</th>
 		    	<td><html:text property="company" /></td>
 		    </tr>
 		    
 		    <tr>
 		    	<th>Product</th>
 		    	<td><html:text property="product" maxlength="255" size="50" /></td>
 		    </tr>
 		    <tr>
 		    	<th>Name</th>
 		    	<td><html:text property="name" maxlength="255" size="50" /></td>
 		    </tr>
 		    <tr>
 		    	<th>Phone</th>
 		    	<td><html:text property="phone" maxlength="255" size="50" /></td>
 		    </tr>
 		    <tr>
 		         <th>Version</th>
 		         <td><html:select property="version">
 		               <html:option value="v1.0">1.8</html:option>
 		             </html:select></td>
 		    </tr>
 		   <!--
 		    <tr>
 		    	<th>Standard Name</th>
 		    	<td><html:checkbox property="naming" /></td>
 		    </tr>
 		    <tr>
 		    	<th>Regular</th>
 		        <td><html:checkbox property="naming" /></td>
 		    </tr>
	    -->
 	 	
	</table>
 		   		  
 		  
 		  <table width="50%" align="center">
 		  <colgroup span="2">
                        <col width="15%"/>
                        <col width="35%"/>
          </colgroup>    
 		     	<tr>
 		     		<th>Login</th>
 		     		<td><html:text property="email" maxlength="255" size="50" /></td>
 		     	</tr>
			     	<tr>
 		     		<th>User-Agent</th>
 		     		<td><html:text property="userAgent" maxlength="255" size="50" /></td>
 		     	</tr>
 		
				<tr>
 		    		<th>Enter Password</th>
 		    		<td><html:password property="password1" redisplay="false" /></td>
 		    	</tr>
 		    	<tr>
 		    		<th>Reenter Password</th>
 		    		<td><html:password property="password2" redisplay="false" /></td>
 		    	</tr>
 		    	
 		    	<tr>
 		    			<td />
 		    			<td align="left"><html:submit property = "submit" value = "Submit" />
 		    		    <html:reset property="reset" value = "Cancel" /></td>
 		    	</tr>
 		   </table>
 		    
       </html:form> 	

    </body>
</html>