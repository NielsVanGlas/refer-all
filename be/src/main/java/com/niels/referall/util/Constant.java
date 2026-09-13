package com.niels.referall.util;

public class Constant {

    // Errors
    // 400 BAD REQUEST
    public static final String ERR_400_01 = "An account with the same tax code already exists";
    public static final String ERR_400_02 = "An account with the same e-mail already exists";
    public static final String ERR_400_03 = "An account with the same document ID already exists";
    public static final String ERR_400_04 = "An account for the same license number already exists";
    public static final String ERR_400_05 = "The given file is empty";
    public static final String ERR_400_06 = "The max file size is 5MB";
    public static final String ERR_400_07 = "The given patient doesn't exist";
    // 401 UNAUTHORIZED
    public static final String ERR_401_01 = "Unauthorized";
    // 404 NOT FOUND
    public static final String ERR_404_01 = "Account not found";
}
