<head>
    <jsp:directive.include
            file="/WEB-INF/jsp/prelude/include-head-meta.jspf"/>
    <title>My Register Failure Page</title>
</head>
<style>
    .container-lg {
        margin-top: 30px;
    }
</style>
<body>
    <div class="container-lg">
        <form action="/user/register" method="post">
            <div class="form-group">
                <label for="name">name</label>
                <input type="text" class="form-control" id="name" name="name" placeholder="Enter name">
            </div>
            <div class="form-group">
                <label for="password">password</label>
                <input type="password" class="form-control" id="password" name="password" aria-describedby="emailHelp" placeholder="Enter password">
            </div>
            <div class="form-group">
                <label for="email">Email address</label>
                <input type="email" class="form-control" id="email" name="email" aria-describedby="emailHelp" placeholder="Enter email">
                <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
            </div>
            <div class="form-group">
                <label for="phoneNumber">phoneNumber</label>
                <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" aria-describedby="phoneNumberHelp" placeholder="Enter phoneNumber">
                <small id="phoneNumberHelp" class="form-text text-muted">We'll never share your phoneNumber with anyone else.</small>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </div>
</body>