<%@ page language="java" %>
<%@page import="org.realtor.rets.compliance.client.ClientSession" %>
<%@ taglib uri="struts-html.tld" prefix="html" %>
<%@ taglib uri="struts-logic.tld" prefix="logic" %>
<%@ taglib uri="struts-bean.tld" prefix="bean"%>
<html>
<head>
	<title>  Test Page  </title>
    <link rel="stylesheet" type="text/css" href="main.css" />
</head>
		<body ><div align="center">
			<h4 align="right"><html:link  href="Logon.jsp">Logout </html:link></h4>

		    <h2 align="center"><%=((ClientSession)session.getAttribute("ClientSession")).getUsername()%> - RESO Client Test Summary </h2>
				<table width="65%" border="1" cellpadding="3" align="center">
				    <tr>
				    	Test results can be viewed at any time even before the tests have been run from the Client you are testing.
                        <%--Before you run the tests, all tests will show up as being �Failed.� --%>
				        <br>
				        <br>
				    	Refresh:  This button gets the latest status of the test.
				        <br>
				        <br>
						Reset Tests:  This sets the status of all the tests to failed and clears any past test run.
				        <br>
				        <br>
						Email Tests: Email the complete test results to the RESO Certification administrator.
				        <br>
				        <br>
						Save Test to File:  This saves the status of the tests to a file on your local hard drive.
				        <br>
				        <br>
				    </tr>
				    
					<colgroup span="2">
	                    <col width="20%"/>
	                    <col width="8%"/>
	                </colgroup>
	            	<tr>
						<th align = "center">Test</th>
			    		<th align = "center">Status</th>
			    		<th align = "center">Message</th>
                        <th align = "center">Description</th>
					</tr>
                    <% int i=0; %>
	    			<logic:iterate name="TestScreen" property="tests" type="org.realtor.rets.compliance.client.ClientTest" id="currentTest">
							<tr>
                                <td align = "center"><a href="testdetail.do?testId=<%=currentTest.getId()%>" title="View Test Details"><bean:write name="currentTest" property="testType"/></a></td>
					    		<td align = "center"><bean:write name="currentTest" property="status"/></td>
					    		<td align = "center"><bean:write name="currentTest" property="message"/></td>
                                <td align = "center"><bean:write name="currentTest" property="description"/></td>

							</tr>
		    		</logic:iterate>
							<tr>
					    		<td colspan="4" align = "right">
                                    <br>
									<html:form action="getTests" >
									<html:submit property="submit" value="Refresh"/>
									</html:form>
									<html:form action="resetTests" >
									<html:submit property="submit" value="Reset Tests"/>
									</html:form>
									<!--disable email functionality for now per RESO 02-10-14
                                    <html:form action="emailTests" >
						              EMail: <html:text property="email"/>
									  <html:submit property="submit" value="EMail Tests"/>
									</html:form>
									-->
									<html:form action="saveTests" >
									  <html:submit property="submit" value="Save Tests to File"/>
									</html:form>
                                </td>
							</tr>
			 	</table>

            </div>
		</body>
</html>