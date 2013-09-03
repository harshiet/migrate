<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title>Migrate</title>
</head>
<body>
${migrationRequest}
	<%--<form:form commandName="form" action="${flowExecutionUrl}" method="post">
		<table>
			<tr>
				<td></td>
				<td>Rally</td>
				<td>Jira</td>
			</tr>
			<tr>
				<td>URL:</td>
				<td><form:input path="sourceUrl" /></td>
				<td><form:input path="targetUrl" /></td>
			</tr>
			<tr>
				<td>Username:</td>
				<td><form:input path="sourceUsername" /></td>
				<td><form:input path="targetUsername" /></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><form:password path="sourcePassword" showPassword="true" /></td>
				<td><form:password path="targetPassword" showPassword="true" /></td>
			</tr>
		</table>
		<input type="submit" value="login" name="_eventId_validateConnection"/>
	</form:form>--%>
</body>
</html>
