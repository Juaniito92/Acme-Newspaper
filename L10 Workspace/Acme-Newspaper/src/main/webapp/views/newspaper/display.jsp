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

<h3>
	<b><spring:message code="newspaper.title" />:&nbsp;</b>
	<jstl:out value="${newspaper.title}" />
</h3>

<b><spring:message code="newspaper.description" />:&nbsp;</b>
<jstl:out value="${newspaper.description}" />
<br />

<img src="<jstl:out value="${newspaper.picture}"/>" width="450"
	height="174">
<br />

<b><spring:message code="newspaper.publisher" />:&nbsp;</b>
<a href="user/display.do?userId=${newspaper.publisher.id}"><jstl:out
		value="${newspaper.publisher.name} ${newspaper.publisher.surname}" /></a>
<br />

<spring:message var="patternDate" code="newspaper.pattern.date" />
<b><spring:message code="newspaper.publicationDate" />:&nbsp;</b>
<fmt:formatDate value="${newspaper.publicationDate}"
	pattern="${patternDate}" />
<br />

<jstl:if test="${newspaper.isPrivate == true}">
	<h3 style="text-transform: uppercase; color: red;">
		<b><spring:message code="newspaper.newspaperPrivate" /></b>
	</h3>
</jstl:if>

<display:table name="${newspaper.articles}" id="row"
	requestURI="newspaper/display.do" pagesize="5" class="displaytag">

	<spring:message var="titleHeader" code="newspaper.title" />
	<display:column title="${titleHeader}">
		<a href="article/display.do?articleId=${row.id}"><jstl:out
				value="${row.title}" /></a>
	</display:column>

	<spring:message var="writerHeader" code="newspaper.writer" />
	<display:column title="${writerHeader}">
		<a href="user/display.do?userId=${row.writer.id}"><jstl:out
				value="${row.writer.name} ${row.writer.surname}" /></a>
	</display:column>
	
	<spring:message var="summaryHeader" code="newspaper.summary" />
	<display:column property="summary" title="${summaryHeader}"/>
	
</display:table>

<security:authorize access="hasRole('USER')">
	<a href="article/user/create.do?newspaperId=${newspaper.id}"><spring:message
			code="newspaper.createArticle" /></a>
	<br />
</security:authorize>

<acme:cancel url="newspaper/list.do" code="newspaper.back" />