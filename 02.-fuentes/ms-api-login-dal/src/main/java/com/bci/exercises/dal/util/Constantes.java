package com.bci.exercises.dal.util;

public class Constantes {


    public static final String PASS_MSG_ERROR_NULL_EMPTY = "Password null o empty.";
    public static final String PASS_MSG_FORMAT_ERROR = "invalid password format.";
    public static final String PASS_REGEX = "^(?=\\w*\\d+)(?=[^A-Z]*[A-Z][^A-Z]*$)(?=\\w*[a-z])(?=\\w*[A-Z]?)\\S{8,12}$";
    public static final String PASS_NUMBER = "[aA-zZ]";


    public static final String USR_MSG_ERROR ="Invalid USER/PASS.";
    public static final Integer USR_CODE_ERROR =4;

    public static final Integer TOKEN_CODE_ERROR = 3;
    public static final String TOKEN_MSG_ERROR = "Invalid Token.";
    public static final String TOKEN_MSG_MALFORMED = "Malformed Token.";
    public static final String TOKEN_MSG_UNSUPPORTED = "Unsupported Token.";
    public static final String TOKEN_MSG_EXPIRED= "Expired Token.";
    public static final String TOKEN_MSG_ILLEGAL_ARGS= "Illegal Argument Token.";

    public static final Integer PARAM_COD_ERROR= 2;
    public static final String EMAIL_MSG_ERROR_EXIST = "Email exist.";
    public static final String EMAIL_MSG_FORMAT_ERROR = "invalid email format.";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    public static final String NAME_MSG_ERROR_EXIST = "Name exist.";
    public static final String NAME_MSG_ERROR_NULL_EMPTY = "Name null o empty.";

    public static final String ERROR_MSG_INTERNAL = "Error in action.";
    public static final Integer ERROR_CODE_INTERNAL= 1;
    public static String sEMPTY="";
}
