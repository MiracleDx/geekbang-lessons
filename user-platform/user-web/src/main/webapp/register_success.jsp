<head>
    <jsp:directive.include
            file="/WEB-INF/jsp/prelude/include-head-meta.jspf"/>
    <title>My Register Succuss Page</title>
</head>
<body>
    <div class="container-lg">
        <!-- Content here -->
        注册 ${user.name} 成功
        <br/>
        当前数据库中共 ${fn:length(users)} 条数据，分为是：
        <c:forEach items="${users}" var="item">
            <div>${item}</div>
        </c:forEach>
    </div>
</body>