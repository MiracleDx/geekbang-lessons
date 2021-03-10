<head>
    <jsp:directive.include
            file="/WEB-INF/jsp/prelude/include-head-meta.jspf"/>
    <title>My Register Failure Page</title>
</head>
<body>
    <div class="container-lg">
        <!-- Content here -->
        注册 ${user.name} 失败 请重试。
        <br/>
        错误原因: ${error}
    </div>
</body>