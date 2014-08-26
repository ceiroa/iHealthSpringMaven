<%-- 
    Document   : adminMenu
    Created on : Aug 13, 2010, 6:26:39 PM
    Author     : ceiroa
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ul id="tabmenu">
    <li><a <c:if test="${active eq 'manageClients'}">class="active"</c:if> href="/iHealth/manageClients/view.htm">Manage Clients</a></li>
    <li><a <c:if test="${active eq 'newClient'}">class="active"</c:if> href="/iHealth/newClient/view.htm">New Client</a></li>
    <li><a <c:if test="${active eq 'manageUploads'}">class="active"</c:if> href="/iHealth/manageUploads/view.htm">Upload Documents</a></li>
    <li><a <c:if test="${active eq 'visits'}">class="active"</c:if> href="/iHealth/manageVisits/view.htm">Manage Visits</a></li>
    <li><a <c:if test="${active eq 'newVisit'}">class="active"</c:if> href="/iHealth/newVisit/view.htm">New Visit</a></li>
    <li><a <c:if test="${active eq 'manageUsers'}">class="active"</c:if> href="/iHealth/manageUsers/view.htm">Manage Users</a></li>
    <li><a <c:if test="${active eq 'changeMyPssw'}">class="active"</c:if> href="/iHealth/changePssw/view.htm">Change Password</a></li>
</ul>
