package com.example.gasholder.parser;


import com.example.gasholder.dao.PointsDAO;
import com.example.gasholder.entity.Discription;
import com.example.gasholder.entity.Point;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@Component
public class Parcer {

    @Autowired
    private PointsDAO pointsDAO;

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
                    sheet.getRow(i).getCell(5).getStringCellValue(), AGZU, sheet.getRow(i).getCell(7).getStringCellValue());
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




}
