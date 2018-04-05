<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="article/display.do" modelAttribute="articleForm">

<jstl:forEach var="picturesHeader" items="${article.pictures }">
	<img src="<jstl:out value="${article.pictures}"/>"width="450" height="174">
	<br/>
</jstl:forEach>

<h1><b><jstl:out value="${article.title }"/></b></h1>
<br/>

<jstl:out value="${article.publicationMoment }"/>
<br/>

<h2><b><jstl:out value="${article.summary }"/></b></h2>
<br/>

<jstl:out value="${article.body }"/>
<br/>

<jstl:out value="${article.body }"/>
<br/>

<b><spring:message code="article.writer"/>:&nbsp;</b>
<jstl:out value="${article.writer.name }${article.writer.surname }"/>
<br/>

<b><spring:message code="article.newspaper"/>:&nbsp;</b>
<jstl:out value="${article.newspaper }"/>
<br/>



<a href="followUp/list.do?articleId=${row.id }">
<spring:message code="article.followUps"/></a>


</form:form>
