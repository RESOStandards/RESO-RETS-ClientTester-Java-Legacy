<%@ taglib uri="struts-html.tld" prefix="html" %>
<%@ taglib uri="struts-logic.tld" prefix="logic" %>
<%@ taglib uri="struts-bean.tld" prefix="bean"%>

<html>
<head>
	<title>  Setup Page  </title>
    <link rel="stylesheet" type="text/css" href="main.css" />
</head>
<body>

<table>
<html:form action="/saveSetup" >

<tr>
	<td>
		Server Host: 
	</td>
	<td>
		<html:text name="SetupScreen" property="serverHost" size="30"/>
	</td>
</tr>

<tr>
	<td>
		Server Port: 
	</td>
	<td>
		<html:text name="SetupScreen" property="serverPort" size="30"/>  
	</td>
</tr>

<tr>
	<td>
		Proxy Port:
	</td>
	<td>
	    <html:text name="SetupScreen" property="proxyPort" size="30"/>  
	</td>
</tr>

<tr>
	<td>
		Database URL: 
	</td>
	<td>
		<html:text name="SetupScreen" property="databaseURL" size="30"/>   
	</td>
</tr>

<tr>
	<td>
		Database Username: 
	</td>
	<td>
		<html:text name="SetupScreen" property="databaseUsername" size="30"/>  
	</td>
</tr>

<tr>
	<td>
		Database Password: 
	</td>
	<td>
		<html:text name="SetupScreen" property="databasePassword" size="30"/>  
	</td>
</tr>

<tr>
	<td>
            <html:submit property = "submit" value = "Save" />
	        </html:submit>
	</td>
</tr>

</html:form>
</table>

</body>
</html>