<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--
  Created by IntelliJ IDEA.
  Date: 07.05.17
  Time: 11:53
  To change this template use File | Settings | File Templates.
--%>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body >
<script type="text/javascript" src="resources/js/accidentDatatables.js" defer></script>
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>


<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <div class="view-box">
                <hr>
                    <table class="table table-striped display" id="accidents">
                        <thead>
                            <tr>
                                <th><spring:message code="accident.name"/></th>
                                <th><spring:message code="accident.date"/></th>
                                <th><spring:message code="accident.actual"/></th>
                                <th><spring:message code="accident.ack"/></th>
                            </tr>
                        </thead>
                    </table>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>


</body>
<jsp:include page="fragments/params.jsp"/>
</html>
