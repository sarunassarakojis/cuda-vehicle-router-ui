package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil
{
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_ONLY_LETTERS_REGEX = Pattern.compile("^[a-zA-Zą-žĄ-Ž]+$");
    public static final Pattern VALID_ONLY_NUMBERS_REGEX = Pattern.compile("^\\d+(\\.\\d+)?$");
    public static final Pattern VALID_INTEGER_REGEX = Pattern.compile("^[0-9]+$");
    public static final Pattern VALID_RESOLUTION_REGEX = Pattern.compile("^\\d+x\\d+$");
    public static final Pattern VALID_CONTRAST_REGEX = Pattern.compile("^\\d+:1$");
    public static final Pattern VALID_VAT_CODE_REGEX = Pattern.compile("^[A-Z]{2}U?\\d{2,12}$");
    public static final Pattern VALID_NACE_CODE_REGEX = Pattern.compile("^[A-Z]{1}\\d\\d?.\\d\\d?.\\d$");
    public static final Pattern VALID_PHONE_NUMBER_REGEX = Pattern.compile("^[+]?\\d+$");
    private static final Pattern VALID_FULL_NAME_PATTERN = Pattern.compile("^[a-zA-Z]+[ \t][a-zA-Z]+");
    private static final Pattern VALID_WEBSITE_PATTERN = Pattern.compile("^(http[s]?://)?[a-zA-Z]+[.](net|com|se|ru|lt|org)");

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static boolean checkIfFullNamePatterFound(String stringToCheck) {
        return VALID_FULL_NAME_PATTERN.matcher(stringToCheck).find();
    }

    public static boolean checkIfWebsitePatterFound(String stringToCheck) {
        return VALID_WEBSITE_PATTERN.matcher(stringToCheck).find();
    }

    public static boolean checkIfEmail(String stringToCheck)
    {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(stringToCheck);
        return matcher.find();
    }

    public static boolean checkIfOnlyLetters(String stringToCheck)
    {
        Matcher matcher = VALID_ONLY_LETTERS_REGEX.matcher(stringToCheck);
        return matcher.find();
    }

    public static boolean checkIfOnlyNumbers(String stringToCheck)
    {
        Matcher matcher = VALID_ONLY_NUMBERS_REGEX.matcher(stringToCheck);
        return matcher.find();
    }

    public static boolean checkIfInteger(String stringToCheck)
    {
        Matcher matcher = VALID_INTEGER_REGEX.matcher(stringToCheck);
        return matcher.find();
    }

    public static boolean checkIfDate(String stringToCheck)
    {
        try
        {
            String formatted = DATE_FORMAT.format(DATE_FORMAT.parse(stringToCheck));

            return formatted.equals(stringToCheck);

        }
        catch (ParseException e)
        {
            return false;
        }
    }

    public static boolean checkIfResolution(String stringToCheck)
    {
        Matcher matcher = VALID_RESOLUTION_REGEX.matcher(stringToCheck);
        return matcher.find();
    }

    public static boolean checkIfContrast(String stringToCheck)
    {
        Matcher matcher = VALID_CONTRAST_REGEX.matcher(stringToCheck);
        return matcher.find();
    }

    public static boolean checkIfVatCode(String stringToCheck)
    {
        Matcher matcher = VALID_VAT_CODE_REGEX.matcher(stringToCheck);
        return matcher.find();
    }

    public static boolean checkIfNaceCode(String stringToCheck)
    {
        Matcher matcher = VALID_NACE_CODE_REGEX.matcher(stringToCheck);
        return matcher.find();
    }

    public static boolean checkIfPhoneNumber(String stringToCheck)
    {
        Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(stringToCheck);
        return matcher.find();
    }
}
