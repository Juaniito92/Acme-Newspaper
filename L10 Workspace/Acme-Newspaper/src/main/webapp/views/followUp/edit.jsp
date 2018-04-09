<%-- edit.jsp de Application --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI }" modelAttribute="followUpForm">


	<acme:textbox code="followUp.title" path="title" />
	<br />

	<acme:textarea code="followUp.summary" path="summary" />
	<br />

	<%-- <acme:textbox code="followUp.publicationMoment"
		path="publicationMoment" placeholder="dd/MM/yyyy" />
	<br /> --%>

	<acme:textbox code="followUp.text" path="text" />
	<br />

	<acme:textarea code="article.pictures" path="pictures" />
	<br />
	
	<acme:selectObligatory items="${article}" itemLabel="title" code="followUp.article" path="articleId"/>
	<br/>

<security:authorize access="hasRole('USER')">

	<acme:submit name="save" code="followUp.save" />
	&nbsp;
	<acme:cancel url="followUp/user/list.do?articleId=${row.id}" code="followUp.back" />
</security:authorize>
</form:form>