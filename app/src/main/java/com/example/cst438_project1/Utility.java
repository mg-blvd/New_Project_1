package com.example.cst438_project1;

import com.example.cst438_project1.DbFiles.Assignment;
import com.example.cst438_project1.DbFiles.Course;
import com.example.cst438_project1.DbFiles.CourseDao;
import com.example.cst438_project1.DbFiles.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import androidx.room.Room;
import androidx.room.RoomDatabase;

public class Utility {


    public static Double computeGrade(Double score, Double maxScore) {
        Double percentage = score / maxScore;
        Double fixedPercent = BigDecimal.valueOf(percentage)
                .setScale(4, RoundingMode.HALF_UP)
                .doubleValue();
        fixedPercent *= 100;
        return fixedPercent;
    }

    public static String getLetterGrade(Double percent) {
        StringBuilder message = new StringBuilder();

        if(percent >= 90) {
            message.append("A - ");
        } else if (percent >= 80) {
            message.append("B - ");
        } else if(percent >= 70) {
            message.append("C - ");
        } else if(percent >= 60) {
            message.append("D - ");
        } else {
            message.append("F - ");
        }
        message.append(percent);
        return message.toString();
    }

    public static void recalculateGrade(Course course, List<Assignment> assignments, CourseDao dao) {
        Double userScores = 0.0;
        Double maxScores = 0.0;

        for (Assignment assignment : assignments) {
            userScores += assignment.getScore();
            maxScores += assignment.getMaxScore();
        }

        Double newGrade = computeGrade(userScores, maxScores);
        course.setCurrentGrade(newGrade);
        dao.update(course);
    }
}
