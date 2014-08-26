package com.ceiroa.ihealth.model;

public class IntHelper {

    public static int parseInt(String toparse) {
        try {
            return Integer.parseInt(toparse);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
