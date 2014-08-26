<%-- 
    Document   : menuNonAdmin
    Created on : Aug 13, 2010, 6:28:22 PM
    Author     : ceiroa
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ul id="tabmenu">
    <li><a <c:if test="${active eq 'newClient'}">class="active"</c:if>  href="/iHealth/newClient/view.htm">New Client</a></li>   	
    <li><a <c:if test="${active eq 'manageUploads'}">class="active"</c:if> href="/iHealth/manageUploads/view.htm">Upload Documents</a></li>
    <li><a <c:if test="${active eq 'newVisit'}">class="active"</c:if> href="/iHealth/newVisit/view.htm">New Visit</a></li>
    <li><a <c:if test="${active eq 'changeMyPssw'}">class="active"</c:if> href="/iHealth/changePssw/view.htm">Change Password</a></li>
</ul>
