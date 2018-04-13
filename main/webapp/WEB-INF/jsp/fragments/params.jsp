
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript">
    var i18n = [];
    i18n["addTitle"] = '<spring:message code="connector.add"/>';
    i18n["editTitle"] = '<spring:message code="connector.edit"/>';
    i18n["addTitleContact"] = '<spring:message code="contact.add"/>';
    i18n["editTitleContact"] = '<spring:message code="contact.edit"/>';
    i18n["emailList"] = '<spring:message code="email.list"/>';
    i18n["email.success"] = '<spring:message code="email.success"/>';

    i18n["info.nocontacts"] = '<spring:message code="info.nocontacts"/>';
    i18n["info.noemails"] = '<spring:message code="info.noemails"/>';
    i18n["info.emailnotselect"] = '<spring:message code="info.emailnotselect"/>';

    i18n["ackAccid"] = '<spring:message code="accident.ack"/>';

    var type = '<c:out value="${tip}"/>';


    <c:forEach var='key' items='<%=new String[]{"common.deleted","common.saved","common.enabled","common.disabled","common.errorStatus","contact.add","common.edit","common.delete","contact.edit","contact.delete","connector.edit","connector.delete","send.email","email.list","connector.sysId", "connector.smscAddr","connector.port","connector.enabled","connector.jurName","confirm","contact.copy","common.copy","app.title.custom"}%>'>
    i18n['${key}'] = '<spring:message code="${key}"/>';
    </c:forEach>
</script>