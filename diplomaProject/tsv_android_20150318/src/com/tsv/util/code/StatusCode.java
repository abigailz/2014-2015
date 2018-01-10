package com.tsv.util.code;

public abstract interface StatusCode{
	/**success**/
	public static int SUCCESS = 200;
	/**无内容**/
	public static int NO_CONTENT = 204;

	/**无修改**/
	public static int NO_MODIFIED = 304;
	/**请求错误**/
	public static int REQUEST_ERROR = 400;
	/**未授权**/
	public static int UNAUTHORIZED = 401;
	/**未授权**/
	public static int FORBID = 403;
	/**代理未授权**/
	public static int PROXY_AUTHENTICATION_REQUIRED = 407;
	/**请求超时**/
	public static int REQUEST_TIMEOUT = 408;
	/**不支持的媒体类型**/
	public static int UNSUPPORT_MEDIA_TYPE = 415;
	/**内部服务器错误**/
	public static int INTERNAL_SERVER_ERROR = 500;
	/**系统异常**/
	public static int SYSTEM_EXCEPTION = 600;
	/**版本不匹配**/
	public static int VERSION_ERROR = 601;
	/**XML格式错误**/
	public static int XML_FORMAT_ERROR = 605;
	/**参数错误**/
	public static int PARAM_ERROR = 607;
	/**参数格式错误**/
	public static int PARAM_FORMAT_ERROR = 608;
	/**admin不可编辑**/
	public static int FORBID_MODIFY_ADMIN = 609;
	/**密码不正确**/
	public static int PASSWORD_ERROR = 614;
//	/**授权失败**/
//	public static int AUTHORIZE_FAILED = 617;
	/**验证码不存在**/
	public static int CAPTCHE_NOT_FOUND = 638;
	/**鉴定码错误**/
	public static int IDENTIFY_CODE_ERROR = 644;
	/**用户不存在**/
	public static int USER_NOT_FOUND = 6456;
	/**用户名已存在**/
	public static int USERNAME_EXISTED = 646;
	/**用户名或密码错误**/
	public static int USERNAME_OR_PASSWORD_ERROR = 647;
	/**用户未绑定**/
	public static int USER_NOT_BINDED = 649;
	/**TMSI不匹配**/
	public static int TMSI_NO_MATCH = 671;
	/**用户被踢**/
	public static int USER_CONFLICT = 672;
	/**文件不存在**/
	public static int FILE_NOT_FOUND = 700;
	/**上传的是空文件**/
	public static int FILE_IS_NULL = 701;
	/**没有上传文件**/
	public static int NO_FILE_UPLOAD = 712;
	/**无权限**/
	public static int NO_AUTHORITY = 722;
	
}