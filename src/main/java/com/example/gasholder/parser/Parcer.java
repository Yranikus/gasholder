package com.example.gasholder.parser;


import com.example.gasholder.dao.CityDAO;
import com.example.gasholder.dao.PointsDAO;
import com.example.gasholder.dao.ReserviorDAO;
import com.example.gasholder.entity.Discription;
import com.example.gasholder.entity.Point;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;



@Component
public class Parcer {

    @Autowired
    private PointsDAO pointsDAO;
    @Autowired
    private CityDAO cityDAO;
    @Autowired
    private ReserviorDAO reserviorDAO;

    public void savePoints(InputStream inputStream) throws IOException {
        Workbook book =  WorkbookFactory.create(inputStream);
        Sheet sheet = book.getSheetAt(0);
        int i = 1;
        while (sheet.getRow(i) != null){
            String[] gsp = sheet.getRow(i).getCell(1).getStringCellValue().split(",");
            String name = sheet.getRow(i).getCell(0).toString();
            if (name.contains(".")) {
                String[] kek = name.split("\\.");
                name = kek[0];
            }
            String AGZU = sheet.getRow(i).getCell(6).toString();
            if (AGZU.contains(".")) {
                String[] kek = AGZU.split("\\.");
                AGZU = kek[0];
            }
            Discription discription = new Discription(sheet.getRow(i).getCell(4).getStringCellValue(),
                    sheet.getRow(i).getCell(5).getStringCellValue(), AGZU, sheet.getRow(i).getCell(7).getStringCellValue(),null,0,null,null,0,null);
            System.out.println(name);
            System.out.println(i);
            Point point = new Point(name,
                    Double.parseDouble(gsp[0]), Double.parseDouble(gsp[1]));
            try {
                pointsDAO.save(point, discription);
            }
            catch (Exception e){
            }
            i++;
        }
    }

    public void saveCity(InputStream inputStream) throws IOException{
        Workbook book =  WorkbookFactory.create(inputStream);
        Sheet sheet = book.getSheetAt(0);
        int i = 1;
        while (sheet.getRow(i) != null){
            if (sheet.getRow(i).getCell(1).getStringCellValue().toString().equals("Республика Башкортостан") ||
                    sheet.getRow(i).getCell(1).getStringCellValue().toString().equals("Республика Татарстан") ||
                    sheet.getRow(i).getCell(1).getStringCellValue().toString().equals("Пермский край") ||
                    sheet.getRow(i).getCell(1).getStringCellValue().toString().equals("Свердловская область") ||
                    sheet.getRow(i).getCell(1).getStringCellValue().toString().equals("Челябинская область")
            ) {
                Point city = new Point(sheet.getRow(i).getCell(3).getStringCellValue() + " " + sheet.getRow(i).getCell(4).getStringCellValue() +".",
                        sheet.getRow(i).getCell(9).getNumericCellValue(), sheet.getRow(i).getCell(10).getNumericCellValue());
                System.out.println(city);
                try {
                    cityDAO.saveCity(city);
                } catch (Exception e) {
                }
            }
            i++;
        }
    }

    public void saveReservoir(InputStream inputStream) throws IOException {
        Workbook book =  WorkbookFactory.create(inputStream);
        Sheet sheet = book.getSheetAt(0);
        int i = 1;
        while (sheet.getRow(i) != null){
            Point point = new Point();
            String name = "Безымянный водоем";
            if (sheet.getRow(i).getCell(0) != null){
                name = sheet.getRow(i).getCell(0).toString();
            }
            point.setName(name);
            point.setLatitude(Double.parseDouble(sheet.getRow(i).getCell(1).getStringCellValue()));
            point.setLongitude(Double.parseDouble(sheet.getRow(i).getCell(2).getStringCellValue()));
            reserviorDAO.saveReservoir(point);
            i++;
        }
    }




}
