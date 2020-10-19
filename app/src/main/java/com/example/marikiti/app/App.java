package com.example.marikiti.app;

public class App {

    public static final String USER_PARENT = "2";
    public static final String USER_DRIER = "3";
    public  static final String BASE_URL = "http://34.195.215.247/sudzero/api/";
    public static final String KEY_USER_TYPE = "type";
    public static final String KEY_PROFILE_NAME = "p_name";
    public static final String KEY_PROFILE_IMAGE = "p_image";

    /* all constant declare here*/
    public static class Key {

        public static final int REQUEST_CODE_SIGN_IN = 2003;
        public static final String IS_LOGGED = "is_login";
        public static final String IS_WELCOMED = "is_welcome";
        public static final String ID_LOGGED = "id_logged";
        public static final String KEY_USER_TYPE = "type";
        public static final String CARD_ID = "card_id";
        public static final String LAST_FOUR_DIGIT = "last_four_digit";
        public static final String USER_NAME = "user_name";
        public static final String USER_EMAIL = "email";
        public static final String NOTI_COUNT = "noti_count";
        public static final String SOCIAL_LOGGONG = "social_type";
        public static final String JOB_ID_FOR_CANCEL_REQUEST = "job_id_for_cancel_request";

    }


    // default message
    public static class Message {

        public static final String NO_INTERNET = "<font color='#131313'>Check your <b>Internet</b> connection, and try again</font>";
    }

    public static class Notification{

        public static final int NEW_TOUR_ASSIGNED_TO_GUID = 1;
        public static final int MAKE_PAYMENT_TO_USER = 2;
        public static final int INFORM_GUID_FOR_NEW_ONGOING = 3;
        public static final int RATING_WHILE_TOURS_COMPLETED = 4;


    }

}