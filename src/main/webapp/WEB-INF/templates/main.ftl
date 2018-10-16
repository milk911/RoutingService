<#assign sf = JspTaglibs["http://www.springframework.org/tags/form"]>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <title>Home</title>
    <style type="text/css">
        .field {clear:both; text-align:right; line-height:25px; }
    </style>

    <meta http-equiv="X-UA-Compatible" content="IE=11"/>
    <link rel="stylesheet" href="resources/styles.css" />
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.0.2/dist/leaflet.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/leaflet-routing-machine/3.2.4/leaflet-routing-machine.css" />

    <script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript" ></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#test1").click(function(){
                $.get("/ajaxtest",function(data,status){
                    alert("Data: " + data + "\nStatus: " + status);
                });
            });
        });
    </script>
</head>
<body>
    <table align="center" width="90%" bgcolor="#f5f5f5"  style="border: solid; border-color: black" cellpadding="10" cellspacing="0">
        <tr valign="top">
            <td align="left" width="30%">
                <input type="hidden" id="WebClientOperation" name="WebClientOperation" value="${pointsString}">
                ${msg}
                <br>
                <@sf.form action="/main" method="post" modelAttribute="newpoint" class="main">
                    <table>
                        <tr>
                            <td>
                                <div class="field">
                                    <@sf.label path="lat">LAT</@sf.label>
                                    <@sf.input path="lat"/>
                                </div>

                                <div class="field">
                                    <@sf.label path="lon">LON</@sf.label>
                                    <@sf.input path="lon"/>
                                </div>
                                <div class="field">
                                    <@sf.label path="count" >COUNT</@sf.label>
                                    <@sf.input path="count" type="count"/>
                                </div>

                                <input type="submit" value="Add new point"/>
                            </td>

                            <td>
                                <@sf.errors path="lat"/>
                                <@sf.errors path="lon"/>
                                <@sf.errors path="count"/>
                            </td>

                        </tr>
                    </table>
                </@sf.form>
                <br>
                <button id="test" onclick="Route()">Route</button>
                <br>
                <#if points?has_content>
                    <br>Points:
                    <table border="1" cellpadding="4" cellspacing="0">
                        <tr bgcolor="#6495ed">
                            <th>№</th>
                            <th>LAT</th>
                            <th>LON</th>
                            <th>QT.</th>
                        </tr>
                        <#list points as point>
                            <tr><td>${point?index+1}</td>
                                <td>${point.lat}</td>
                                <td>${point.lon}</td>
                                <td>${point.count}</td>
                            </tr>
                        </#list>

                    </table>

                <#else >
                    <p>No points yet!</p>
                </#if>

            </td>
            <td valign="top" align="left" width="70%">

                <div id="map" style="border: 1px solid black; margin-left: auto; margin-right: auto; margin-top: 1%; height: 500px; width:100%;"></div>

                <script src="https://unpkg.com/leaflet@1.0.2/dist/leaflet.js"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/leaflet-routing-machine/3.2.4/leaflet-routing-machine.js"></script>
                <script src="resources/osm.js"></script>
                <script type="text/javascript">
                    function Route() {
                        $.get("/route",function(data,status){
                            alert("Data: " + data + "\nStatus: " + status);
                        });
                        delPolyline();
                        var WebClientOperation = document.getElementById("WebClientOperation").value;
                        //alert(WebClientOperation);
                        addPolyline(WebClientOperation);
                        addLayer();
                        setView();
                    }
                    addMarkers();

                </script>
            </td>
        </tr>
        <tr>
            <td colspan="2" height="50%" bgcolor="#87cefa" align="right">
                <a style="font-family: Verdana; font-size: large">@ ROUTING MACHINE 2018 </a>
            </td>
        <tr>
    </table>
</body>
</html>