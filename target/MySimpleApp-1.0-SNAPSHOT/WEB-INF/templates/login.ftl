<#assign sf = JspTaglibs["http://www.springframework.org/tags/form"]>
<html>
<head>
    <title>New user</title>
    <style type="text/css">
        body {font-size:14px;}
        label {float:left; padding-right:10px;}
        .field {clear:both; text-align:right; line-height:25px;}
        .main {float:left;}
    </style>
</head>
<body>
    <@sf.form action="/login" method="post" modelAttribute="loginForm" class="main">
        <table>
        <tr>
            <td>
                <div class="field">
                    <@sf.label path="email">Email</@sf.label>
                    <@sf.input path="email"/>
                </div>
                <div class="field">
                    <@sf.label path="password">Password</@sf.label>
                    <@sf.input path="password" type="password"/>
                </div>

                <input type="submit" value="LOGIN"/>
            </td>

            <td>
                <@sf.errors path="email"/>
                <@sf.errors path="password"/>
            </td>

        </tr>
        <#if errors != "">
            <tr>
                <td style="color: red">USER DOESN'T EXIST!<td>
            </tr>
        </#if>
        </table>
        <a href="sign_up">CREATE NEW USER</a>
    </@sf.form>



</body>
</html>