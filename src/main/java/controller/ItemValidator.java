package controller;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemValidator implements Serializable {
    
    // Pattern for item name (1-50 characters)
    private String namePattern = "^.{1,50}$";
    
    // Pattern for price (positive number with up to 2 decimal places)
    private String pricePattern = "^\\d+(\\.\\d{1,2})?$";
    
    // Pattern for quantity (non-negative integer)
    private String quantityPattern = "^\\d+$";
    
    // Pattern for description (0-255 characters)
    private String descriptionPattern = "^.{0,255}$";
    
    // Pattern for manufacturer (0-50 characters)
    private String manufacturerPattern = "^.{0,50}$";
    
    // Pattern for type (must be one of the allowed types)
    private String[] validTypes = {"Energy", "Health", "Home Automation", "Security", "Sensors"};
    
    public ItemValidator() {}
    
    public boolean validate(String pattern, String input) {
        Pattern regEx = Pattern.compile(pattern);
        Matcher match = regEx.matcher(input);
        return match.matches();
    }
    
    public boolean validateName(String name) {
        return name != null && !name.trim().isEmpty() && validate(namePattern, name);
    }
    
    public boolean validatePrice(String price) {
        if (price == null || price.isEmpty()) {
            return false;
        }
        
        // Validate price format
        if (!validate(pricePattern, price)) {
            return false;
        }
        
        // Validate price is greater than 0
        try {
            double priceValue = Double.parseDouble(price);
            return priceValue > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public boolean validateQuantity(String quantity) {
        if (quantity == null || quantity.isEmpty()) {
            return false;
        }
        
        // Validate quantity format
        if (!validate(quantityPattern, quantity)) {
            return false;
        }
        
        // Validate quantity is non-negative
        try {
            int quantityValue = Integer.parseInt(quantity);
            return quantityValue >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public boolean validateDescription(String description) {
        // Description can be null or empty
        if (description == null || description.isEmpty()) {
            return true;
        }
        
        // If provided, it must be at most 255 characters
        return validate(descriptionPattern, description);
    }
    
    public boolean validateManufacturer(String manufacturer) {
        // Manufacturer can be null or empty
        if (manufacturer == null || manufacturer.isEmpty()) {
            return true;
        }
        
        // If provided, it must be at most 50 characters
        return validate(manufacturerPattern, manufacturer);
    }
    
    public boolean validateType(String type) {
        if (type == null || type.isEmpty()) {
            return false;
        }
        
        // Check if type is in the list of valid types
        for (String validType : validTypes) {
            if (validType.equals(type)) {
                return true;
            }
        }
        
        return false;
    }
}