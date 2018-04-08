<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<div>
	<a href="/Acme-Newspaper"><img src="images/logo.png" alt="Acme-Newspaper Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
			<ul>
				<li><a href="administrator/display.do"><spring:message code="master.page.administrator.information" /></a></li>
			</ul>
		</security:authorize>
		
		<security:authorize access="hasRole('USER')">
		<li><a class="fNiv"><spring:message	code="master.page.user" /></a>
			<ul>
				<li><a href="newspaper/user/create.do"><spring:message code="master.page.user.newspaperCreate" /></a></li>
				<li><a href="newspaper/user/list.do"><spring:message code="master.page.user.listNewspapers" /></a></li>
				<li><a href="newspaper/user/list-nonPublished.do"><spring:message code="master.page.user.listNewspapersNonPublished" /></a></li>
			</ul>
			
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">

		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.register" /></a>
			<ul>
				<li><a href="user/register.do"><spring:message code="master.page.registerUser" /></a></li>
			</ul>			
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
					
				</ul>
			</li>
	
		</security:authorize>
		
		<li><a class="fNiv" href="newspaper/list.do"><spring:message code="master.page.avalibleNewspapers" /></a></li>
		<li><a class="fNiv" href="user/list.do"><spring:message code="master.page.listUsers" /></a></li>
		<li><a class="fNiv" href="terms/list.do"><spring:message code="master.page.termsAndConditions" /></a></li>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

