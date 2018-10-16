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
    <@sf.form action="/sign_up" method="post" modelAttribute="userForm" class="main">
        <table>
        <tr>
            <td>
                <div class="field">
                    <@sf.label path="name">Name</@sf.label>
                    <@sf.input path="name"/>

                </div>

                <div class="field">
                    <@sf.label path="email">Email</@sf.label>
                    <@sf.input path="email"/>

                </div>
                <div class="field">
                    <@sf.label path="password1">Password</@sf.label>
                    <@sf.input path="password1" type="password"/>

                </div>
                <div class="field">
                    <@sf.label path="password2">Confirm password</@sf.label>
                    <@sf.input path="password2" type="password"/>

                </div>
                <input type="submit" value="ADD"/>
            </td>

            <td>
                <@sf.errors path="name"/>
                <@sf.errors path="email"/>
                <@sf.errors path="password1"/>
                <@sf.errors path="password2"/>
            </td>

        </tr>



        </table>
    </@sf.form>

<#--    <form action="/users/new" method="post">
        <input name="name" type="text" placeholder="name">
        <input name="surname" type="text" placeholder="surname">
        <input name="email" type="email" placeholder="email">
        <input type="submit">
    </form>-->

</body>
</html>