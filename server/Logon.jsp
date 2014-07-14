<%@ page language="java" %>
<%@ taglib uri="struts-html.tld" prefix="html" %>
<%@ taglib uri="struts-logic.tld" prefix="logic" %>
<html>
<head>
	<title>  Logon Page  </title>
    <link rel="stylesheet" type="text/css" href="main.css" />
</head>
		<body>
            <br>
			<h1 align="center"> RESO Client Compliance  </h1>
			
			<html:form action="/logonSubmit" >
				<table width="40%" align="center">

					<tr>
					    The RETS 1.8 Client Compliance Test Platform is an independent Web-based application that guides the user through the process of initiating a series of tests.  These tests ensure that a given RETS client conforms to the RETS 1.5 or RETS 1.7 specification by intercepting and evaluating the requests sent from the RETS client and their requests.   
					    <br>
					    <br>
					    These tests are not just designed for certification.  They can also be used to examine the interoperability of different RETS clients.  The Client Compliance Checker is stateless, which means that you do not have to impose a predefined flow on your client. Use your Client as you normally would to perform RETS transactions. Behind the scenes, the compliance checker will be performing evaluations that you can view in the Test Results screen.
					    
					    <b>HOW DO I BEGIN THE TEST</b>
					    <br/>
					    The RETS Client Tests are passive; your client is evaluated as it makes RETS requests in a normal fashion.  Once you have registered, you can 
					    use your login name and password in your RETS Client to access our RETS Server and begin testing.
					    <br/>
					    RETS login id:	the login id provided to you by the RESO Certification administrator
					    RETS password:	the login id provided to you by the RESO Certification administrator
					    Login URL:		http://localhost:9080/Login/Login.asmx
					    <br/>
					    Login to the above RETS test server and exercise your RETS client as you would normally.  When
					    you want to see if your client has passed RETS Compliance tests, you can come back to this page
					    and use the same login id and password to get access to the Client Test Summary screen,
					    which will report your progress.
					    <br/>
					    When you login here, you will be presented with the Client Test Summary Screen.  This test contains a pass/fail record of individual tests,
					    and allows you to view details for each test.  REMEMBER, logging in to from this page DOES NOT EXECUTE the tests.
					    <br>
			 		</tr>

 		    		<tr>
 		    			<th>UserName</th>
 		    		    <td><html:text property="userName" /></td>
 		    		</tr>
 		    		<tr>
 		    			<th>Password</th>
 		    		    <td><html:password property="password" /></td>
 		    		</tr>
 		    		<tr>
 		    		    <td />
 		    			<td align="right">
                            <html:submit property = "submit" value = "Login" />
            		        </html:form>
                			<form name="RegForm" method="post" action="Registration.jsp">
				                <input type="submit" name="submit" value="New User">
				 		    </form>
                        </td>
 		    		</tr>
 		    	</table>
                
		</body>
</html>