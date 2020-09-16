package com.example.cst438_project1;

import com.example.cst438_project1.DbFiles.Assignment;
import com.example.cst438_project1.DbFiles.Course;
import com.example.cst438_project1.DbFiles.CourseDao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class Utility {


    /**
     * Computes what the percentage grade is for a given assignment
     * @param score - has the score the user received
     * @param maxScore - has the max score the user could have received
     * @return - the percentage score the user got
     * @author Misael Guijarro
     */
    public static Double computeGrade(Double score, Double maxScore) {
        Double percentage = score / maxScore;
        Double fixedPercent = BigDecimal.valueOf(percentage)
                .setScale(4, RoundingMode.HALF_UP)
                .doubleValue();
        fixedPercent *= 100;
        return fixedPercent;
    }

    /**
     * Returns the letter grade the the user would have received based on their letter grade
     * @param percent - a double with their percent grade
     * @return - a letter grade with their percentage
     * @author Misael Guijarro
     */
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

    /**
     * recalculates the grade user would have received in a course based on the assignments
     * @param course - The course we are going to update
     * @param assignments - the assignment related to the course
     * @param dao - The dao we are going to use to update the course
     * @author Misael Guijarro
     */
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
