<%@ page language="java"%>
<%@ taglib uri="struts-html.tld" prefix="html" %>
<%@ taglib uri="struts-logic.tld" prefix="logic" %>
<%@ taglib uri="struts-bean.tld" prefix="bean" %>

<%@ page import = "java.util.*,
                   org.realtor.rets.compliance.client.view.TestDetailScreen,
                   org.realtor.rets.compliance.client.view.TestScreen,
                   org.realtor.rets.compliance.client.ClientTest" %>

<html>
  <script type="text/javascript">
    function submit()
    {
      document.testdetail.submit();
    }
  </script>
	<head>
		<title>  Test Details </title>
	    <link rel="stylesheet" type="text/css" href="main.css" />
    </head>
		<body>
			<h4 align="right"><html:link href="Logon.jsp">Logout </html:link></h4>
			<h4 align="right"><html:link href="getTests.do">Return to Test Screen</html:link></h4>
			<h1 align="center"> Test Detail Page </h1>
		   
 		  	<table border="1" width="80%" align="center">
 		  	    <tr>
      		  	    This screen shows the details of a particular test including if it passed, any messages, and the full HTTP request and response.
      		  	    <br>
      		  	    <br>
 		  	    </tr>
 		  		<colgroup span="2">
                        <col width="10%"/>
                        <col width="20%"/>
            	</colgroup>
 		  		<tr>
 		         <th>Select the Test</th>
			<html:form action="/testdetail">
 		         <td>
			          <html:select name="TestDetailScreen" property="testId" onchange="submit();">
			            <html:options collection="tests" property="id" labelProperty="testType"/>
			          </html:select>
 		         </td>
 		    </html:form>
 		      </tr>
               <tr> <td colspan="2"><bean:write name="TestDetailScreen" property="description"/> : <bean:write name="TestDetailScreen" property="passed"/></td> </tr>
               <tr> <td colspan="2">Message:<br><bean:write name="TestDetailScreen" property="message"/></td> </tr>
               <tr> <td colspan="2">Request:<br><bean:write name="TestDetailScreen" property="request"/></td> </tr>
               <tr> <td colspan="2">Response:<br><bean:write name="TestDetailScreen" property="response"/></td> </tr>
 		  	</table>
		</body>
</html>



