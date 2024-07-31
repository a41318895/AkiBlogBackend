package com.akichou.customEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * App Http code Enumeration
 *
 * @author Aki Chou
 * @date 2024/06/21 Fri.
 */
@Getter
@AllArgsConstructor
public enum AppHttpCodeEnum {

    // 1xx: Informational HttpCodes
    CONTINUE(100, "繼續"),
    SWITCHING_PROTOCOLS(101, "切換協議"),

    // 2xx: Success HttpCodes
    SUCCESS(200, "操作成功"),
    CREATED(201, "已創建"),
    ACCEPTED(202, "已接受"),
    NO_CONTENT(204, "無內容"),

    // 3xx: Redirection HttpCodes
    MOVED_PERMANENTLY(301, "永久移動"),
    FOUND(302, "臨時移動"),
    NOT_MODIFIED(304, "未修改"),

    // 4xx: Client Error HttpCodes
    BAD_REQUEST(400, "錯誤請求"),
    NEED_LOGIN(401, "需登入後才能操作"),
    NO_OPERATOR_AUTH(403, "無權限操作"),
    CONTENT_NOT_FOUND(404, "找尋不到目標內容"),
    METHOD_NOT_ALLOWED(405, "方法不允許"),
    CONFLICT(409, "衝突"),

    // 5xx: Server Error HttpCodes
    SYSTEM_ERROR(500, "出現錯誤"),
    INTERNAL_SERVER_ERROR(500, "伺服器內部錯誤"),
    NOT_IMPLEMENTED(501, "未實現"),
    BAD_GATEWAY(502, "網關錯誤"),
    SERVICE_UNAVAILABLE(503, "服務不可用"),
    GATEWAY_TIMEOUT(504, "網關超時"),

    // Custom codes HttpCodes
    USERNAME_EXIST(601, "用戶已存在"),
    PHONE_NUMBER_EXIST(602, "手機號碼已存在"),
    EMAIL_EXIST(603, "電子郵件已存在"),
    REQUIRE_USERNAME(604, "需填寫用戶名稱"),
    LOGIN_ERROR(605, "用戶名稱或密碼錯誤"),
    COMMENT_CONTENT_NOT_BLANK(606, "送出的評論內容不得為空白"),
    FILE_TYPE_ERROR(607, "文件類型錯誤，請上傳jpg/png文件"),
    FILE_SIZE_ERROR(608, "文件大小不能超出2MB"),
    USERNAME_NOT_NULL(609, "用戶名不能為空"),
    PASSWORD_NOT_NULL(610, "密碼不能為空"),
    NICKNAME_NOT_NULL(611, "暱稱不能為空"),
    EMAIL_NOT_NULL(612, "電子郵件不能為空"),
    USER_NOT_FOUND(613, "用戶不存在"),
    TAG_NAME_AND_REMARK_NOT_NULL(614, "標籤名稱和備註皆不可為空白"),
    TAG_NOT_FOUND(615, "找不到指定標籤"),
    CATEGORY_NOT_FOUND(616, "找不到指定分類"),
    ARTICLE_NOT_FOUND(617, "找不到指定文章"),
    SOME_TAG_NOT_FOUND(618, "標籤丟失"),
    ILLEGAL_COMMENT_TYPE(619, "不合法的評論類型"),
    SOME_MENU_NOT_FOUND(620, "菜單丟失"),
    LOGIN_USER_GET_FAIL(621, "LoginUser從Redis中獲取失敗"),
    SOME_USER_NOT_FOUND(622, "用戶丟失"),
    SOME_ROLE_NOT_FOUND(623, "角色丟失"),
    LINK_NOT_FOUND(624, "找不到指定友情連結"),
    MENU_NOT_FOUND(625, "找不到指定菜單"),
    ROLE_NOT_FOUND(626, "找不到指定角色"),
    PASS_INFO_CHECK(627, "通過忘記密碼之資料驗證"),
    USER_INFO_NOT_MATCH(628, "用戶相關訊息不匹配"),
    PASS_VERIFICATION_CODE_CHECK(629, "通過忘記密碼之驗證碼驗證"),
    FAIL_VERIFICATION_CODE_CHECK(630, "未通過忘記密碼之驗證碼驗證"),
    VERIFICATION_CODE_EXPIRED(631, "驗證碼已經過期"),
    RESET_PASSWORD_OVERTIME(632, "重置密碼期限超時"),
    RESET_PASSWORD_VERIFY_FAIL(633, "重置密碼之驗證標示值錯誤"),
    RESET_PASSWORD_FINISHED(634, "重置密碼完成") ;

    final int code;
    final String message;
}
