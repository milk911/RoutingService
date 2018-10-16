<html>
<head>
    <title>Users</title>
</head>
    <body>
        <#if users?has_content>
            <ul>
                <#list users as user>
                    <li>${user.name} ${user.email} ${user.password}</li>
                </#list>
            </ul>
        <#else >
            <p>No users yet!</p>
        </#if>
        <a href="/users/new">NEW USER</a>
    </body>
</html>