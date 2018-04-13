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
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/connectorDatatables.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>


<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <div class="view-box">
                <hr>
                <a class="btn btn-link" onclick="add()">
                    <span  aria-hidden="true" ${tip.equals("esme")?"hidden":""}><spring:message code="connector.add"/></span>
                </a>
                    <table class="table table-striped display" id="datatable">
                        <thead>
                            <tr>
                                <th><spring:message code="connector.name"/></th>
                                <th><spring:message code="connector.info"/></th>
                                <th><spring:message code="connector.smscAddr"/></th>
                                <th><spring:message code="connector.port"/></th>
                                <th><spring:message code="connector.enabled"/></th>
                                <th><spring:message code="connector.jurName"/> </th>
                                <th><spring:message code="contact.contact"/> </th>
                                <th></th>
                                <th></th>
                                <th></th>
                                <th></th>
                            </tr>
                        </thead>
                    </table>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>

<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title" id="modalTitle"><spring:message code="connector.add"/></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="detailsForm">
                    <input type="text" hidden="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="name" class="control-label col-xs-3"><spring:message code="connector.name"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="name" name="name" placeholder="<spring:message code="connector.name"/>" ${tip.equals("esme")?"readonly":""}>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="systemId" class="control-label col-xs-3"><spring:message code="connector.sysId"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="systemId" name="systemId" placeholder="<spring:message code="connector.sysId"/>" ${tip.equals("esme")?"readonly":""}>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="smscAddr" class="control-label col-xs-3"><spring:message code="connector.smscAddr"/></label>

                        <div class="col-xs-9">
                            <input type="smscAddr" class="form-control" id="smscAddr" name="smscAddr" placeholder="<spring:message code="connector.smscAddr"/>" ${tip.equals("esme")?"readonly":""}>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="port" class="control-label col-xs-3"><spring:message code="connector.port"/></label>

                        <div class="col-xs-9">
                            <input type="port" class="form-control" id="port" name="port" placeholder="<spring:message code="connector.port"/>" ${tip.equals("esme")?"readonly":""}>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="enabled" class="control-label col-xs-3"><spring:message code="connector.enabled"/></label>

                        <div class="col-xs-9">
                            <input type="enabled" class="form-control" id="enabled" name="enabled" placeholder="<spring:message code="connector.enabled"/>" ${tip.equals("esme")?"readonly":""}>
                        </div>
                    </div>


                    <div class="form-group">
                        <label for="jurName" class="control-label col-xs-3"><spring:message code="connector.jurName"/></label>



                        <div class="col-xs-9">
                            <input type="jurName" class="form-control" id="jurName" name="jurName" placeholder="<spring:message code="connector.jurName"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="button" onclick="save()" class="btn btn-primary">
                                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editRowContact">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title" id="modalTitleContact"><spring:message code="contact.add"/></h2>
            </div>
            <div class="modal-body">
                <form  class="form-horizontal" id="detailsFormContact">
                    <input type="text" hidden="hidden" id="contactId" name="id">
                    <var id="connectorId"></var>


                    <div class="form-group">
                        <label for="contact" class="control-label col-xs-3"><spring:message code="contact.contact"/></label>

                        <div class="col-xs-9">
                            <input type="text"  class="form-control" id="contact" name="contact" placeholder="<spring:message code="contact.contact"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="description" class="control-label col-xs-3"><spring:message code="contact.description"/></label>

                        <div class="col-xs-9">
                            <textarea type="text" rows="8" class="form-control" id="description" name="description" placeholder="<spring:message code="contact.description"/>"></textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="button" onclick="saveContact()" class="btn btn-primary">
                                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            </button>
                        </div>
                    </div>

                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade right-position" id="emailListModal">
    <div class="modal-dialog">
        <div class="modal-content ">
            <form class="form-horizontal" id="emailList">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h2 class="modal-title" id="modalTitleMailList"><spring:message code="email.list"/></h2>
                </div>
                <div id="checkbox-list"></div>
                <div class="form-group">
                    <div class="col-xs-offset-1 col-xs-9" id="sndEmlBtn">
                        <%--dynamic btn from js--%>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade right-position" id="nameListModal">
    <div class="modal-dialog">
        <div class="modal-content ">
            <form class="form-horizontal" id="nameList">
                <div class="modal-header">
                    <button type="button" class="close nameList-close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h2 class="modal-title" id="modalTitleNameList"><spring:message code="email.connectors"/></h2>
                </div>
                <div id="name-list"></div>
                <div class="form-group">
                    <div class="col-xs-offset-1 col-xs-9" id="addAttrButton">
                        <button type="button"  onclick="sendAttributesBtn()" class="btn btn-primary" id="send-attr">
                            <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="emailModal" role="dialog">
    <div class="modal-dialog-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title" ><spring:message code="send.email"/></h2>
            </div>
            <div class="modal-body ">
                <form class="form-horizontal" id="emailForm">
                    <div class="form-group">
                        <label for="recipient" class="control-label col-xs-1"><spring:message code="email.recipient"/></label>
                        <div class="col-xs-11">
                            <input type="text" class="form-control recipient" id="recipient" name="recipient">
                        </div>
                    </div>
                    <div class="form-group ">
                        <label for="copy" class="control-label col-xs-1"><spring:message code="email.copy"/></label>
                        <div class="col-xs-1">
                            <select class="form-control " id="copy-select">
                                <option value="p.shunina@i-free.com">Ольга Шемелова</option>
                                <option value="shemelova@i-free.com">Полина Шунина</option>
                            </select>
                        </div>
                        <div class="col-xs-10">
                            <input type="text" class="form-control copy" id="copy" name="copy">
                        </div>
                    </div>
                    <div class="form-group subject-forms">
                        <label for="subject" class="control-label col-xs-1"><spring:message code="email.subject"/></label>
                        <div class="col-xs-1">
                            <select class="form-control " id="subject-select">
                                <option value=""></option>
                                <option value="i-Free">Запрос от группы компаний i-Free</option>
                                <option value="SMSServices">Запрос от компании ООО &#34;СМС сервисы&#34;</option>
                                <option value="iFreedom">Запрос от ТОО АйФридом</option>
                                <option value="iFreeKaz">Запрос от АйФри Казахстан</option>
                                <option value="BeFree">Запрос от компании BeFree</option>
                                <option value="BeFree_en">Request from BeFree</option>
                                <option value="i-Free_en">Request from i-Free</option>
                            </select>
                        </div>
                        <div class="col-xs-10 col-xs-offset-0">
                            <input type="text" class="form-control" id="subject" name="subject">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="problem-select" class="control-label col-xs-1"><spring:message code="email.problem"/></label>
                        <div class="col-xs-1">
                            <select class="form-control input-sm" id="problem-select">
                                <option value="void"></option>
                                <option value="disconnect">disconnect</option>
                                <option value="notraffic">notraffic</option>
                                <option value="notraffic-in">notraffic inbound</option>
                                <option value="notraffic-out">notraffic outbound</option>
                                <option value="disconnect-en">disconnect en</option>
                            </select>
                        </div>
                        <label for="signa-select" class="control-label col-xs-1"><spring:message code="email.signa"/></label>
                        <div class="col-xs-1">
                            <select class="form-control input-sm" id="signa-select">
                                <option value="void"></option>
                                <option value="ru">ru</option>
                                <option value="en">en</option>
                            </select>
                        </div>
                    </div>
                    <%--<div class="form-group">--%>

                    <%--</div>--%>
                    <div class="form-group">
                        <label for="message" class="control-label col-xs-1"></label>
                        <div class="col-xs-12">
                            <textarea type="text" rows="16" class="form-control" id="message" name="message"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-offset-1 col-xs-9">
                            <button type="button" onclick="sendAjaxEmail()"  class="btn btn-primary">
                                <span class="glyphicon glyphicon-send" aria-hidden="true"></span>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
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

    var type = '<c:out value="${tip}"/>';


    <c:forEach var='key' items='<%=new String[]{"common.deleted","common.saved","common.enabled","common.disabled","common.errorStatus","contact.add","common.edit","common.delete","contact.edit","contact.delete","connector.edit","connector.delete","send.email","email.list","connector.sysId", "connector.smscAddr","connector.port","connector.enabled","connector.jurName","confirm","contact.copy","common.copy","app.title.custom"}%>'>
    i18n['${key}'] = '<spring:message code="${key}"/>';
    </c:forEach>
</script>
</html>
