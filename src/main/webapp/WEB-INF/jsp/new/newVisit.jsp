<%-- 
    Document   : newVisit
    Created on : Aug 29, 2010, 1:11:24 PM
    Author     : ceiroa
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty user}">
    <jsp:include page="/WEB-INF/jsp/includes/header.jsp" />

    <h2>New Visit</h2>
    <p class="error">${errorMessage}</p>
    <p class="success">${successMessage}</p>
    <p class="info">${infoMessage}</p>
    
    <jsp:include page="/WEB-INF/jsp/includes/clientSearch.jsp" />

    <br/><br/>
    <c:if test="${not empty clients}">
        <table>
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th colspan="4">Create New Visit</th>
            </tr>
            <c:forEach var="client" items="${clients}">
                <tr>
                    <td>${client.firstName}</td>
                    <td>${client.lastName}</td>
                    <td>${client.email}</td>
                    <td>
                        <form action="/iHealth/cervicalSpine/visit.htm" method="post">
	                        <input type="hidden" name="postReqSource" value="newVisit" />
    	                    <input type="hidden" name="clientId" value="${client.id}" />
                            <input type="Submit" value="Cervical Spine" />
                        </form>
                    </td>
                    <td>
                        <form action="/iHealth/upperExtremities/visit.htm" method="post">
	                        <input type="hidden" name="postReqSource" value="newVisit" />
	                        <input type="hidden" name="clientId" value="${client.id}" />
                            <input type="Submit" value="Upper Extremities" />
                        </form>
                    </td>
                    <td>
                        <form action="/iHealth/lumbarSpine/visit.htm" method="post">
	                        <input type="hidden" name="postReqSource" value="newVisit" />
	                        <input type="hidden" name="clientId" value="${client.id}" />
                            <input type="Submit" value="Lumbar Spine" />
                        </form>
                    </td>
                    <td>
                        <form action="/iHealth/lowerExtremities/visit.htm" method="post">
	                        <input type="hidden" name="postReqSource" value="newVisit" />
	                        <input type="hidden" name="clientId" value="${client.id}" />
                            <input type="Submit" value="Lower Extremities" />
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