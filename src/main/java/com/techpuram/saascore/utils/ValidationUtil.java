package com.techpuram.saascore.utils;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ValidationUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SuppressWarnings("unchecked")
    public static boolean validateFieldValue(String fieldType, String validationRule, Object value) {
        try {
            // Parse the validation rule JSON
            Map<String, Object> rules = objectMapper.readValue(validationRule, Map.class);

            switch (fieldType) {
                case "SINGLELINE":
                case "MULTILINE":
                    return validateString(value, rules);
                case "EMAIL":
                    return validateRegex(value, (String) rules.get("regex"));
                case "NUMBER":
                    return validateNumber(value, rules);
                case "GEOLOCATION":
                    return validateGeolocation(value, rules);
                case "PICKLIST":
                    return validatePicklist(value, rules);
                default:
                    throw new IllegalArgumentException("Unsupported field type: " + fieldType);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean validateString(Object value, Map<String, Object> rules) {
        String str = value.toString();
        Integer maxLength = (Integer) rules.get("maxLength");
        return maxLength == null || str.length() <= maxLength;
    }

    private static boolean validateRegex(Object value, String regex) {
        return value.toString().matches(regex);
    }

    private static boolean validateNumber(Object value, Map<String, Object> rules) {
        int num = Integer.parseInt(value.toString());
        Integer min = (Integer) rules.get("min");
        Integer max = (Integer) rules.get("max");
        return (min == null || num >= min) && (max == null || num <= max);
    }

    @SuppressWarnings("unchecked")
    private static boolean validateGeolocation(Object value, Map<String, Object> rules) {
        Map<String, Double> geo = (Map<String, Double>) value;
        Double lat = geo.get("latitude");
        Double lon = geo.get("longitude");
        double[] latRange = (double[]) rules.get("latitude_range");
        double[] lonRange = (double[]) rules.get("longitude_range");
        return lat >= latRange[0] && lat <= latRange[1] && lon >= lonRange[0] && lon <= lonRange[1];
    }

    @SuppressWarnings("unchecked")
    private static boolean validatePicklist(Object value, Map<String, Object> rules) {
        return ((List<String>) rules.get("allowedValues")).contains(value.toString());
    }
}
