
<%-- 
    Document   : visitsLog
    Created on : Aug 13, 2010, 5:11:27 PM
    Author     : ceiroa
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty user  and user.userType eq 'admin'}">
    <jsp:include page="/WEB-INF/jsp/includes/header.jsp" />
    <h2>Patient Visits</h2>
    <p class="error">${errorMessage}</p>
    <p class="success">${successMessage}</p>
    <p class="info">${infoMessage}</p>

    <!-- Search functionality -->
    <form action="/iHealth/manageVisits/find.htm" method="post">
    	<table>
            <tr>
                <th>First Name:</th>
                <th>Last Name:</th>
                <th>Date(yyyy-MM-dd):</th>
            </tr>
            <tr>
                <td><input type="text" name="firstName"/></td>
                <td><input type="text" name="lastName"/></td>
                <td><input type="text" name="date"/></td>
                <td><input type="Submit" value="Search"></td>
            </tr>
    	</table>
    </form>
    <br/><br/>
    <!-- Visits List -->
    <c:if test="${not empty visits}">
        <table>
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Visit Type</th>
                <th>Date</th>
            </tr>
            <c:forEach var="visit" items="${visits}">
                <tr>
                    <td>${visit.firstName}</td>
                    <td>${visit.lastName}</td>
                    <td>${visit.controller}</td>
                    <td><fmt:parseDate value="${visit.dateCreated}" type="both" pattern="yyyy-MM-dd HH:mm:ss" var="formatedDate"/>
                        <fmt:formatDate value="${formatedDate}" pattern="yyyy-MM-dd" type="date"/></td>
                    <td>
                        <form action="/iHealth/${visit.controller}/visit.htm" method="post">
                            <input type="hidden" name="visitId" value="${visit.id}" />
                            <input type="hidden" name="clientId" value="${visit.clientId}" />
                            <input type="hidden" name="postReqSource" value="visitsLog" />
                            <input type="hidden" name="controller" value="${visit.controller}" />
                            <input type="Submit" value="View/Edit" />
                        </form>
                    </td>
                    <td>
                        <form action="/iHealth/deleteVisit/delete.htm" method="post" onsubmit="return confirmDelete()">
                            <input type="hidden" name="visitId" value="${visit.id}" />
                            <input type="hidden" name="controller" value="${visit.controller}" />
                            <input type="Submit" value="Delete" />
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />
</c:if>

<c:if test="${empty user}">
    <c:redirect url="index.htm" />
</c:if>
