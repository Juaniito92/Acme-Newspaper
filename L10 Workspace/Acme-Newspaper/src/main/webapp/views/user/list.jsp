<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

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

<!-- displaying grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="user" requestURI="${requestURI }" id="row">

	<!-- Attributes -->

	<display:column title="${articlesHeader}">
		<a href="user/display.do?userId=${row.id}">
			<spring:message code="user.display"/>
		</a>
	</display:column>

	<spring:message code="user.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="user.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="user.articles" var="articlesHeader"/>
	<display:column title="${articlesHeader}">
		<a href="articles/list.do?userId=${row.id}">
			<spring:message code="user.articlesWritten"/>
		</a>
	</display:column>
	
	<security:authorize access="ADMIN">
	<spring:message code="chirp.delChirp" var="deleteHeader"/>
		<display:column title="${deleteHeader}">
			<a href="chirp/delete.do?chirpId=${row.id}">
				<spring:message code="chirp.delete"/>
			</a>
		</display:column>
	</security:authorize>
	
</display:table>



<a href="javascript:window.history.back();">&laquo; <spring:message code="terms.back"/></a>





