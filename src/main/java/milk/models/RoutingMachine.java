package milk.models;

import milk.dao.PointsDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RoutingMachine {

    private static double start_lat = 50.00578072297657;
    private static double start_lon = 36.2290906906128;

    private static int GetLength(double lat1, double lon1, double lat2, double lon2)
    {
        int l=0;
        double ll = 6372795*Math.acos(Math.sin(lat1*Math.PI/180)*Math.sin(lat2*Math.PI/180) + Math.cos(lat1*Math.PI/180)*Math.cos(lat2*Math.PI/180)*Math.cos((lon1 - lon2)*Math.PI/180));
        l = (int) (ll * 1.41);
        if (l<1)
            l = 0;
        return l;
    }

    private static int GetLengthPoints(Point point1, Point point2) {
        if (point1==null)
            return GetLength(start_lat, start_lon, point2.getLat(), point2.getLon());
        else if (point2==null)
            return GetLength(start_lat, start_lon, point1.getLat(), point1.getLon());
        else
            return GetLength(point2.getLat(), point2.getLon(), point1.getLat(), point1.getLon());
    }

    public static String getPointsString(List<Point> PointsList) {

        String pointsString = "";
        for(Point point:PointsList) {
            if (pointsString.length() > 0)
                pointsString+= ",";
            pointsString = pointsString + point.getLat() +"," + point.getLon();
        }
        return pointsString;
    }

    public static String Routing(String userID, PointsDAO pointsDAO) {
        List<Point> Points = null;
        try
        {
            Points = pointsDAO.getAll(userID);
        }
        catch (Exception e) {
            return "";
        }

        if (Points.size()==0)
            return "";
        else if (Points.size()==1)
            return getPointsString(Points);

        int points_count = Points.size();

        List<Point> PointsList = Points.subList(0, points_count);
        List<Point> ResultRoute = new ArrayList<Point>();

        //Integer p1 = 0;
        //Integer p2 = 0;

        //setFarthestPointsIndexes( p1,  p2, PointsList);
        List<List<Integer>> kr_dist = new ArrayList<List<Integer>>(points_count*points_count);

        PrepareDistArray(kr_dist, Points);
        System.out.println(kr_dist);
        //PointsList.clear();

        List<List<Point>> Routes = new ArrayList<List<Point>>(points_count);
        for (int i = 0; i < points_count; i++) {
            List<Point> Route = new ArrayList<Point>(points_count);
            //for (int j = 0; j < points_count; j++) {
            //    Route.add(null);
            //}
            Route.add(0,Points.get(i));
            Routes.add(Route);
        }

        while (true) {
            boolean gain_found = false;
            for (int i = 0; i < kr_dist.size(); i++) {
                Point point1 = Points.get(kr_dist.get(i).get(0));
                int point1_ok = -1;
                int point1_pos = -1;

                for (int ii = 0; ii < points_count; ii++) {
                    if (point1_ok != -1)
                        break;
                    for (int jj = 0; jj < Routes.get(ii).size(); jj++) {
                        if (Routes.get(ii).get(jj) == null)
                            break;
                        if (point1.equals(Routes.get(ii).get(jj))) {
                            point1_pos = jj;
                            if (jj!=0 && jj != Routes.get(ii).size()-1)
                                point1_ok = -2;
                            else
                                point1_ok = ii;

                            break;
                        }
                    }
                }

                if (point1_ok == -2)
                    continue;

                Point point2 = Points.get(kr_dist.get(i).get(1));
                int point2_ok = -1;
                int point2_pos = -1;

                for (int ii = 0; ii < points_count; ii++) {
                    if (point2_ok != -1)
                        break;
                    for (int jj = 0; jj < Routes.get(ii).size(); jj++) {
                        if (Routes.get(ii).get(jj) == null)
                            break;
                        if (point2.equals(Routes.get(ii).get(jj))) {
                            point2_pos = jj;
                            if (jj!=0 && jj != Routes.get(ii).size()-1)
                                point2_ok = -2;
                            else
                                point2_ok = ii;
                            break;
                        }
                    }
                }

                if (point2_ok == -2)
                    continue;

                if (point1_ok == point2_ok)
                    continue;

                if (point1_pos == 0 && point2_pos == 0) {
                    Collections.reverse(Routes.get(point2_ok));
                    Routes.get(point1_ok).addAll(0,Routes.get(point2_ok));
                    for (int c = 0; c < Routes.get(point2_ok).size(); c++) {
                        Routes.get(point2_ok).set(c,null);
                    }
                }

                if (point1_pos == 0 && point2_pos != 0) {
                    //Collections.reverse(Routes.get(point2_ok));
                    Routes.get(point1_ok).addAll(0,Routes.get(point2_ok));
                    for (int c = 0; c < Routes.get(point2_ok).size(); c++) {
                        Routes.get(point2_ok).set(c,null);
                    }

                }

                if (point1_pos != 0 && point2_pos == 0) {
                    //Collections.reverse(Routes.get(point2_ok));
                    Routes.get(point1_ok).addAll(Routes.get(point2_ok));
                    for (int c = 0; c < Routes.get(point2_ok).size(); c++) {
                        Routes.get(point2_ok).set(c,null);
                    }

                }

                if (point1_pos != 0 && point2_pos != 0) {
                    Collections.reverse(Routes.get(point2_ok));
                    Routes.get(point1_ok).addAll(Routes.get(point2_ok));
                    for (int c = 0; c < Routes.get(point2_ok).size(); c++) {
                        Routes.get(point2_ok).set(c,null);
                    }

                }
                System.out.println(Routes.get(point1_ok));

                //System.out.println(point1_ok + " " + point1_ok + " " + kr_dist.get(i).get(2));

            }
            break;
        }
        //Arrays.s

        //PointsList.add(Points.get(p1));
        //PointsList.add(Points.get(p2));

        return "ok";
    }

    private static void PrepareDistArray(List<List<Integer>> kr_dist, List<Point> PointsList) {
        int points_count = PointsList.size();
        for (int i = 0; i < points_count; i++) {
            for (int j = 0; j < points_count; j++) {
                List<Integer> currentRow = new ArrayList<Integer>();
                kr_dist.add(currentRow);
                //kr_dist.add(new ArrayList<Integer>());
                currentRow.add(0);
                currentRow.add(0);
                currentRow.add(0);
                currentRow.set(0,i);
                currentRow.set(1,j);
                currentRow.set(2,0);

                if (i!=j) {
                    int gain = GetLengthPoints(null, PointsList.get(i)) + GetLengthPoints(null, PointsList.get(j)) - GetLengthPoints(PointsList.get(i), PointsList.get(j));
                    currentRow.set(2,gain);
                    //System.out.println(currentRow.get(0) + " " + currentRow.get(1) + " " + gain);
                }

            }
        }
        Collections.sort(kr_dist, new SortbyGain());



    }

    private static void setFarthestPointsIndexes(Integer p1, Integer p2, List<Point> PointsList) {
        int dist0 = 0;

        for (int i = 0; i < PointsList.size(); i++) {
            int dist = GetLengthPoints(null, PointsList.get(i));
            if (dist > dist0 ) {
                p1 = i;
                dist0 = dist;
            }
        }

        dist0 = 0;
        for (int i = 0; i < PointsList.size(); i++) {
            int dist = GetLengthPoints(null, PointsList.get(i));
            if (dist > dist0 && i!=p1) {
                p2 = i;
                dist0 = dist;
            }
        }
        System.out.println(p1);
        System.out.println(p2);
    }






}
