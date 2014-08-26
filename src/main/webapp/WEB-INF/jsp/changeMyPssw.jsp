<%--
    Document   : manageUsers
    Created on : Aug 29, 2010, 1:13:55 PM
    Author     : ceiroa
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty user}">
    <jsp:include page="/WEB-INF/jsp/includes/header.jsp" />

    <h2>Change Password</h2>
    <p class="error">${errorMessage}</p>
    <p class="success">${successMessage}</p>
    <p class="info">${infoMessage}</p>

    <form action="/iHealth/changePssw/change.htm" method="post">
        <input type="hidden" name="id" value="${user.id}"/>
        <table>
            <tr>
                <td>New Password:</td>
                <td><input type="password" name="password1" /></td>
            </tr>
            <tr>
                <td>Confirm Password:</td>
                <td><input type="password" name="password2" /></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td><input type="Submit" value="Submit" /></td>
            </tr>
        </table>
    </form>

    <jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />
</c:if>

<c:if test="${empty user}">
    <c:redirect url="index.htm" />
</c:if>