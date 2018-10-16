
var map = L.map('map').setView([50.0, 36.13], 11);
var popup;
var route1waypoints = [];
var router = new L.Routing.OSRMv1({});
var route1line;
var base_marker;

map.on('click', function(e) {
    var lat_elem = document.getElementById('lat');
    var lon_elem = document.getElementById('lon');
   // alert("Lat, Lon : " + e.latlng.lat + ", " + e.latlng.lng)
    lat_elem.value = e.latlng.lat;
    lon_elem.value = e.latlng.lng;
});

var firstpolyline = new L.Polyline([], {
    color: '#FF00FF',
    weight: 1,
    opacity: 1,
    smoothFactor: 1
}).addTo(map);

L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}{r}.png', {
    attribution: '© OpenStreetMap contributors'
}).addTo(map);


var markers = new L.FeatureGroup();

var currentMarkers = [];

function addBase_Marker(lat, lon){

    var LeafIcon = L.Icon.extend({
        options: {
            shadowUrl: 'leaf-shadow.png',
            iconSize:     [38, 95],
            shadowSize:   [50, 64],
            iconAnchor:   [22, 94],
            shadowAnchor: [4, 62],
            popupAnchor:  [-3, -76]
        }
    });

    var greenIcon = new LeafIcon({iconUrl: 'img/leaf-green.png'})
    base_marker = new L.marker(L.latLng(lat, lon));
    base_marker.addTo(map);
}

function addMarker(text, lat, lon, color, address){

    currentMarker = L.latLng(lat, lon);



    currentMarkers.push(currentMarker);



    popup = document.createElement('div');
    popup.className = 'divText';

    t = document.createTextNode(address);

    popup.appendChild(t);
    document.body.appendChild(popup);
    popup.id = 'popup';


    if (color == 1){
        popup1 = document.createElement('div');

        t1 = document.createTextNode(text);
        popup1.appendChild(t1);
        document.body.appendChild(popup1);
        popup1.id = 'popup1';



    }



    if (color == 1){
        var myIcon = L.divIcon({className: 'numberCircleGreen', iconSize: 2});
    }
    else{
        if (color == 2){
            var myIcon = L.divIcon({className: 'numberCircleBlue', iconSize: 0.5});
        }
        else if(color == 3){
            var myIcon = L.divIcon({className: 'numberCircleGrey', iconSize: 0.5});
        }
        else
        {
            var myIcon = L.divIcon({className: 'numberCircleLightBlue', iconSize: 0.5});
        }

    }


    var marker = new L.marker([lat, lon], {icon: myIcon});
    marker.bindTooltip(popup, {direction: 'top'});
    popup.id = 'hello';

    var elem = document.getElementById('hello');
    elem.parentNode.removeChild(elem);


    markers.addLayer(marker);

    if (color == 1){

        var myIcon1 = L.divIcon({iconSize: 0});
        var marker1 = new L.marker([lat, lon], {icon: myIcon1});

        marker1.bindTooltip(popup1, {permanent: true,  className: 'popupText', direction: 'centerright'});

        markers.addLayer(marker1);
        popup1.id = 'hello1';

    }


}

function addLayer(){
    map.addLayer(markers);

}

function setView(){

    var group = new L.latLngBounds(currentMarkers);
    map.fitBounds(group);

}

function clearMarkers(){
    try
    {
        currentMarkers.length=0;
        markers.clearLayers();
    }
    catch(err)
    {
    }

}


function getPolylinePoints(point1, point2){
    currentL = L.latLng(point1, point2);
    pointsOfPolyline.push(currentL);
}

function addPolyline(pointsOfLine){


    separated = pointsOfLine.split(',');
    points = [];
    for (index = 0; index < separated.length; index = index + 2) {
        currentL = L.latLng(separated[index], separated[index+1]);
        points.push(currentL);
    }
    firstpolyline.setLatLngs(points);
    points.length = 0;


}

function delPolyline(){

    firstpolyline.setLatLngs([]);
    route1waypoints.length = 0;
}

function addRoutePoint(lat, lon){

    route1waypoints.push(L.Routing.waypoint(L.latLng(lat, lon)));
}

function delRoute(){

    try
    {
        route1line.remove();
    }
    catch(err)
    {
    }
}

function showRoute(){

    route1plan = L.Routing.plan(route1waypoints, {
        createMarker: function(i, wp) {
            return false;
        }});

    router.route(route1waypoints, function(error, routes) {
        route1line = L.Routing.line(routes[0]);
        route1line.addTo(map);

    }, null, {});
}

function addMarkers(){
    clearMarkers();
    var WebClientOperation = document.getElementById("WebClientOperation").value;

    separated = WebClientOperation.split(',');
    points = [];
    addMarker("START", 50.00578072297657, 36.2290906906128, 1, "START");
    for (index = 0; index < separated.length; index = index + 3) {
         addMarker("Point " + index/3, separated[index], separated[index+1], 2, "Point " + index/2 + "(" + separated[index+2] + ")");
    }

    addLayer();
    setView();

}
