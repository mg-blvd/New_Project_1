package com.example.cst438_project1;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utility {
    public static String computeGrade(Double score, Double maxScore) {
        Double percentage = score / maxScore;
        Double fixedPercent = BigDecimal.valueOf(percentage)
                .setScale(4, RoundingMode.HALF_UP)
                .doubleValue();
        fixedPercent *= 100;
        return getLetterGrade(fixedPercent);
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
}
