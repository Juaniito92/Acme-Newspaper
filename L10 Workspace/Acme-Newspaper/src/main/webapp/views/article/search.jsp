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

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="article" requestURI="${requestURI }" id="row" defaultsort="3" defaultorder="descending">

<security:authorize access="hasRole('ADMIN')">
	<display:column>
		<a href="article/admin/delete.do?articleId=${row.id }">
		<spring:message code="article.delete"/></a>
	</display:column>
</security:authorize>

<security:authorize access="hasRole('USER')">
<display:column>
		<a href="article/user/edit.do?articleId=${row.id }">
		<spring:message code="article.edit"/></a>
	</display:column>
</security:authorize>

<a href="article/display.do?articleId=${row.id }" >
<spring:message code="article.title" var="titleHeader" /></a>
<display:column property="title" title="${titleHeader }" sortable="true"/>

<spring:message code="article.format.date" var="formatDate"/>
<spring:message code="article.publicationMoment" var="publicationMomentHeader"/>
<display:column property="publicationMoment" title="${publicationMomenHeader }" sortable="true" format="${formatDate} }"/>

<spring:message code="article.writer" var="writerHeader"/>
<display:column title="${writerHeader }" value="${row.writer.name }"/>

</display:table>

<security:authorize access="hasRole('USER')">
	<div>
	<a href="article/user/create.do">
		<button>
			<spring:message code="article.create" />
		</button>
	</a>
	</div>
</security:authorize>