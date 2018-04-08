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
	name="followUp" requestURI="${requestURI }" id="row">

	<!-- Attributes -->
	<spring:message code="followUp.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="followUp.summary" var="sumaryHeader" />
	<display:column property="summary" title="${sumaryHeader}" sortable="true"/>
	

	<spring:message code="followUp.publicationMoment"
		var="publicationMomentHeader" />
	<display:column property="publicationMoment"
		title="${publicationMomentHeader}" sortable="true" />

	

	<spring:message code="followUp.text" var="textHeader" />
	<display:column property="text" title="${textHeader}" sortable="true" />

	<spring:message code="followUp.pictures" var="picturesHeader" />
	<display:column property="pictures" title="${picturesHeader}"
		sortable="true" /> 


	<%-- 
	<spring:message code="followUp.article" var="article"/>
	<display:column title="${article}">
		<a href="rendezvous/list-rspv.do?userId=${row.id }">
			<spring:message code="user.rendezvouses.link"/>
	</display:column>
	
	<spring:message code="followUp.user" var="user"/>
	<display:column title="${user}">
		<a href="rendezvous/list-rspv.do?userId=${row.id }">
			<spring:message code="user.rendezvouses.link"/>
	</display:column>
	
	 --%>
	 
	 
</display:table>

<a href="javascript:window.history.back();">&laquo; <spring:message code="terms.back"/></a>