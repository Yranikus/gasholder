package com.example.gasholder.service;

import com.example.gasholder.dao.CityDAO;
import com.example.gasholder.dao.PointsDAO;
import com.example.gasholder.dao.ReserviorDAO;
import com.example.gasholder.entity.ArryOfPoints;
import com.example.gasholder.entity.Discription;
import com.example.gasholder.entity.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
public class OilWellService {

    @Autowired
    private PointsDAO pointsDAO;
    @Autowired
    private CityDAO cityDAO;
    @Autowired
    private ReserviorDAO reserviorDAO;
    @Autowired
    private ComparatorOfPoints comparatorOfPoints;


    public double calculate(double lat1, double lng1, double lat2, double lng2){
            double earthRadius = 6371.0; // miles (or 6371.0 kilometers)
            double dLat = Math.toRadians(lat2-lat1);
            double dLng = Math.toRadians(lng2-lng1);
            double sindLat = Math.sin(dLat / 2);
            double sindLng = Math.sin(dLng / 2);
            double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                    * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
            double dist = earthRadius * c;
            return dist;
    }


    public void setCityDirection(){
        List<Point> points = pointsDAO.test();
        List<Point> cities = cityDAO.getAllCity();
            for (Point p : points){
                double min = Integer.MAX_VALUE;
                Point minPoint = null;
                for (Point city : cities){
                    double dist = calculate(city.getLatitude(),city.getLongitude(),p.getLatitude(),p.getLongitude());
                    if (dist < min) {
                        min = dist;
                        minPoint = city;
                    }
                }
                String  azimuth = "lol";
                double direction = Math.atan2((minPoint.getLatitude() - p.getLatitude()), (minPoint.getLongitude() - p.getLongitude()));
                direction = Math.toDegrees(direction);
                if (direction >= 67.5 && direction < 112.5) azimuth = "Юг";
                if(direction >= -146 && direction < -112.5) azimuth = "Северововсток";
                if ((direction >= -180 && direction < -146) || (direction <= 180 && direction >= 146 )) azimuth = "Восток";
                if  (direction >= 124 && direction < 146)   azimuth = "Юговосток";
                if (direction >= -112.5 && direction < -67.5) azimuth = "Север";
                if  (direction >= 22.5 && direction < 67.5) azimuth = "Югозапад";
                if ((direction > -22.5 && direction < 22.5)) azimuth = "Запад";
                if (direction >= -67.5 && direction <= -22.5)  azimuth = "Северозапад";
                pointsDAO.saveNearestCity(minPoint.getName(),min,azimuth,p.getId());
            }
    }

    public void setReservoirDirection(){
        List<Point> points = pointsDAO.test();
        List<Point> reservoirs = reserviorDAO.getAll();
        for (Point p : points){
            double min = Integer.MAX_VALUE;
            Point minPoint = null;
            for (Point reservoir : reservoirs){
                double dist = calculate(reservoir.getLatitude(),reservoir.getLongitude(),p.getLatitude(),p.getLongitude());
                if (dist < min) {
                    min = dist;
                    minPoint = reservoir;
                }
            }
            String  azimuth = "lol";
            double direction = Math.atan2((minPoint.getLatitude() - p.getLatitude()), (minPoint.getLongitude() - p.getLongitude()));
            direction = Math.toDegrees(direction);
            if (direction >= 67.5 && direction < 112.5) azimuth = "Юг";
            if(direction >= -146 && direction < -112.5) azimuth = "Северововсток";
            if ((direction >= -180 && direction < -146) || (direction <= 180 && direction >= 146 )) azimuth = "Восток";
            if  (direction >= 112 && direction < 146)   azimuth = "Юговосток";
            if (direction >= -112.5 && direction < -67.5) azimuth = "Север";
            if  (direction >= 22.5 && direction < 67.5) azimuth = "Югозапад";
            if ((direction > -22.5 && direction < 22.5)) azimuth = "Запад";
            if (direction >= -67.5 && direction <= -22.5)  azimuth = "Северозапад";
            pointsDAO.saveNearestReservoir(minPoint.getName(),min,azimuth,p.getId());
        }
    }


    public Discription getDiscription(int id){
        return pointsDAO.getDiscription(id);
    }

    public ArryOfPoints getPoints(){
        return pointsDAO.getPoint();
    }

    public ArryOfPoints getPointByWorkshop(String workshop){
        ArryOfPoints arryOfPoints = pointsDAO.getPointsByWorkshop(workshop);
        arryOfPoints.getFeatures().sort(comparatorOfPoints);
        return arryOfPoints;
    }


}
