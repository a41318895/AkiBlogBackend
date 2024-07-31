package com.akichou.constant;

/**
 * System Constants
 *
 * @author Aki Chou
 * @date 2024/07/25 Thu.
 */
public class SystemConstants {

    // LOGIN
    public static final String BLOG_LOGIN_REDIS_KEY_PRE = "blogLogin:" ;
    public static final String BLOG_ADMIN_LOGIN_REDIS_KEY_PRE = "adminLogin:" ;

    public static final String ADMIN_USER_TYPE = "1" ;


    // Article
    public static final String ARTICLE_STATUS_PUBLISHED = "0" ;
    public static final String ARTICLE_STATUS_DRAFT = "1" ;

    public static final String ARTICLE_VIEW_COUNT_REDIS_KEY_NAME = "articleViewCount" ;
    public static final Long INIT_VIEW_COUNT = 0L ;

    // Page
    public static final Integer CURRENT_PAGE = 0 ;
    public static final Integer ONCE_SHOW_NUMBER = 10 ;

    // Link
    public static final String LINK_STATUS_REVIEW_PASS = "0" ;
    public static final String LINK_STATUS_REVIEW_NO_PASS = "1" ;
    public static final String LINK_STATUS_UN_REVIEWED = "2" ;

    // Comment
    public static final Long IS_ROOT_COMMENT_ID = -1L ;

    public static final String ARTICLE_COMMENT = "0" ;
    public static final String LINK_COMMENT = "1" ;

    // Menu
    public static final String TYPE_MENU = "C";
    public static final String TYPE_BUTTON = "F";
    public static final String TYPE_CATALOG = "M" ;

    public static final String MENU_NORMAL_STATUS = "0" ;
    public static final String MENU_FORBIDDEN_STATUS = "1" ;

    public static final Long ROOT_MENU_ID = 0L ;

    public static final Integer NOT_FRAME_MENU = 1 ;

    public static final Long ROOT_MENU_PARENT_ID = 0L ;

    // Role
    public static final String ROLE_NORMAL_STATUS = "0" ;

    // Login Delete
    public static final Integer DEL_FLAG_EXIST = 0 ;
    public static final Integer DEL_FLAG_DELETED = 1 ;

    // User
    public static final String USER_TYPE_NORMAL = "0" ;
    public static final String USER_TYPE_ADMIN = "1" ;

    public static final String USER_STATUS_NORMAL = "0" ;
    public static final String USER_STATUS_FORBIDDEN = "1" ;

    // Category
    public static final Long NO_ROOT_CATEGORY_PID = -1L ;

    // Forgot Password
    public static final String FORGOT_PASSWORD_VERIFY_CODE_REDIS_KEY = "forgotPasswordVerifyCode" ;
    public static final Integer MAX_REQUESTS = 1 ;   // max requests per minute
    public static final String VERIFY_CODE_PATH_TO_LIMIT = "/user/verifyCode" ;
    public static final String PASSWORD_FORGOT_PATH_TO_LIMIT = "/user/password/forgot" ;
    public static final String PASS_CODE_VERIFICATION_REDIS_KEY = "codeVerificationPassed" ;
    public static final String PASS_CODE_VERIFICATION_REDIS_VALUE_PRE = "verifiedSuccessfully:" ;
    public static final String BEING_VERIFIED_USER_USERNAME_REDIS_KEY = "beingVerifiedUserUsername" ;
}
