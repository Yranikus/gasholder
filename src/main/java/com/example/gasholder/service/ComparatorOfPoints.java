package com.example.gasholder.service;

import com.example.gasholder.entity.PointJs;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ComparatorOfPoints implements Comparator<PointJs> {
    @Override
    public int compare(PointJs o1, PointJs o2) {
        return (getNummericSubstr(o1.getName()) - getNummericSubstr(o2.getName()));
    }

    private int getNummericSubstr(String name){
        Pattern p = Pattern.compile("\\b(\\d{1,6})([А-я]{1,4})(?=,*|\\b)");
        Matcher m = p.matcher(name);
        try  {
            m.find();
            return Integer.parseInt(m.group(1));
        }
        catch (IllegalStateException e){
            return Integer.parseInt(name);
        }
    }

}
