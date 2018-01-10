package com.borya.util.code;

public enum StatusCode{
	/**success**/
	SUCCESS("200","success",""),
	/**无内容**/
	NO_CONTENT("204","无内容",""),
	/**欠费**/
	ARREARAGE("301","欠费",""),
	/**余额不足**/
	BALANCE_NOT_ENOUGH("302","余额不足",""),
	/**无修改**/
	NO_MODIFIED("304","无修改",""),
	/**请求错误**/
	REQUEST_ERROR("400","请求错误",""),
	/**未授权**/
	UNAUTHORIZED("401","未授权",""),
	/**未授权**/
	FORBID("403","未授权",""),
	/**代理未授权**/
	PROXY_AUTHENTICATION_REQUIRED("407","代理未授权",""),
	/**请求超时**/
	REQUEST_TIMEOUT("408","请求超时",""),
	/**不支持的媒体类型**/
	UNSUPPORT_MEDIA_TYPE("415","不支持的媒体类型",""),
	/**内部服务器错误**/
	INTERNAL_SERVER_ERROR("500","内部服务器错误",""),
	/**系统异常**/
	SYSTEM_EXCEPTION("600","系统异常",""),
	/**版本不匹配**/
	VERSION_ERROR("601","版本不匹配",""),
	/**公司不存在**/
	COMPANY_NOT_FOUND("602","公司不存在",""),
	/**公司已存在**/
	COMPANY_EXISTED("603","公司已存在",""),
	/**XML格式错误**/
	XML_FORMAT_ERROR("605","XML格式错误",""),
	/**公司参数错误**/
	COMPANY_PARAM_ERROR("606","公司参数错误",""),
	/**参数错误**/
	PARAM_ERROR("607","参数错误",""),
	/**参数格式错误**/
	PARAM_FORMAT_ERROR("608","参数格式错误",""),
	/**admin不可编辑**/
	FORBID_MODIFY_ADMIN("609","admin不可编辑",""),
	/**员工不可被修改**/
	FORBID_MODIFY_STAFF("611","员工不可被修改",""),
	/**员工不存在**/
	STAFF_NOT_FOUND("612","员工不存在",""),
	/**员工已存在**/
	STAFF_EXISTED("613","员工已存在",""),
	/**密码不正确**/
	PASSWORD_ERROR("614","密码不正确",""),
	/**员工参数错误**/
	STAFF_PARAM_ERROR("615","员工参数错误",""),
	/**员工工号已存在**/
	STAFFNO_EXISTED("616","员工工号已存在",""),
	/**授权失败**/
	AUTHORIZE_FAILED("617","授权失败",""),
	/**员工工号为空**/
	STAFFNO_IS_NULL("618","员工工号为空",""),
	/**员工头像为空**/
	STAFF_FACE_IS_NULL("620","员工头像为空",""),
	/**部门不存在**/
	DEPT_NOT_FOUND("622","部门不存在",""),
	/**部门已存在**/
	DEPT_EXISTED("623","部门已存在",""),
	/**部门名称为空**/
	DEPT_NAME_IS_NULL("624","部门名称为空",""),
	/**部门电话格式错误**/
	DEPT_PHONE_ERROR("625","部门电话格式错误",""),
	/**部门名称格式错误**/
	DEPT_NAME_ERROR("626","部门名称格式错误",""),
	/**部门传真号码错误**/
	DEPT_FAX_ERROR("627","部门传真号码错误",""),
	/**公司ID与父部门公司ID不一致**/
	DEPT_ID_ERROR("628","公司ID与父部门公司ID不一致",""),
	/**部门名称过长**/
	DEPT_NAME_LENGTH_ERROR("629","部门名称过长",""),
	/**部门下存在员工**/
	DEPT_EXIST_MEMBER("630","部门下存在员工",""),
	/**不能删除未分组**/
	FORBID_DEL_UNGROUPED("631","不能删除未分组",""),
	/**验证码不存在**/
	CAPTCHE_NOT_FOUND("638","验证码不存在",""),
	/**父部门不存在**/
	PARENT_DEPT_NOT_FOUND("640","父部门不存在",""),
	/**部门参数错误**/
	DEPT_PARAM_ERROR("641","部门参数错误",""),
	/**默认部门不能编辑**/
	FORBID_MODIFY_DEFAULT_DEPT("642","默认部门不能编辑",""),
	/**部门名称已存在**/
	DEPT_NAME_EXISTED("643","部门名称已存在",""),
	/**鉴定码错误**/
	IDENTIFY_CODE_ERROR("644","鉴定码错误",""),
	/**用户不存在**/
	USER_NOT_FOUND("645","用户不存在",""),
	/**用户名已存在**/
	USERNAME_EXISTED("646","用户名已存在",""),
	/**用户名或密码错误**/
	USERNAME_OR_PASSWORD_ERROR("647","用户名或密码错误",""),
	/**session会话失效**/
	SESSION_OUT_OF_DATE("648","session会话失效",""),
	/**用户未绑定**/
	USER_NOT_BINDED("649","用户未绑定",""),
	/**用户组不存在**/
	GROUP_NOT_FOUND("650","用户组不存在",""),
	/**默认用户组不可被修改**/
	FORBID_MODIFY_DEFAULT_GROUP("651","默认用户组不可被修改",""),
	/**用户XMPP已离线**/
	OFFLINE("670","用户XMPP已离线",""),
	/**TMSI不匹配**/
	TMSI_NO_MATCH("671","TMSI不匹配",""),
	/**用户被踢**/
	USER_CONFLICT("672","用户被踢",""),
	/**文件不存在**/
	FILE_NOT_FOUND("700","文件不存在",""),
	/**上传的是空文件**/
	FILE_IS_NULL("701","上传的是空文件",""),
	/**没有上传文件**/
	NO_FILE_UPLOAD("712","没有上传文件",""),
	/**照片参数错误**/
	PHOTO_PARAM_ERROR("713","照片参数错误",""),
	/**文件参数错误**/
	FILE_FORMAT_ERROR("714","文件参数错误",""),
	/**文件大小错误**/
	FILE_SIZE_ERROR("715","文件大小错误",""),
	/**包不存在**/
	PACKAGE_NOT_FOUND("721","包不存在",""),
	/**无权限**/
	NO_AUTHORITY("722","无权限",""),
	/**业务申请不存在**/
	NO_CONDITION_DEL("723","业务申请不存在",""),
	/**公告不存在**/
	ANNO_NOT_FOUND("740","公告不存在",""),
	/**系统配置错误**/
	SYSTEM_CONFIG_ERROR("800","系统配置错误",""),
	/**员工工号已存在**/
	MEMBER_STAFFNO_EXISTED("900","员工工号已存在",""),
	/**员工手机号错误**/
	MEMBER_PHONE_ERROR("901","员工手机号错误",""),
	/**员工工作号码错误**/
	MEMBER_OFFICE_ERROR("902","员工工作号码错误",""),
	/**管理员删除自己错误**/
	MEMBER_DELETE_ONESELF_ERROR("903","管理员删除自己错误",""),
	/**admin删除公司管理员错误**/
	ADMIN_DELETE_MANAGER_ERROR("904","admin删除公司管理员错误",""),
	/**员工不能挪动自己**/
	MEMBER_MOVE_ONESELF("905","员工不能挪动自己",""),
	/**无法挪动公司管理员**/
	MEMBER_MOVE_MANAGE("906","无法挪动公司管理员",""),
	/**员工不存在**/
	MEMBER_NOT_FOUND("907","员工不存在",""),
	/**XMPP房间不存在**/
	XMPP_ROOM_NOT_FOUND("1001","XMPP房间不存在",""),
	/**XMPP房间已存在**/
	XMPP_ROOM_EXISTED("1002","XMPP房间已存在",""),
	/**权限错误**/
	PERMISSIONPROFILE_IS_NULL("1101","权限错误",""),
	/**禁止修改**/
	FORBID_MODIFY("1102","禁止修改",""),
	/**公司名称已存在**/
	COMPANY_NAME_EXISTED("20001","公司名称已存在",""),
	/**公司名称为空**/
	COMPANY_NAME_IS_NULL("20002","公司名称为空",""),
	/**公司地址为空**/
	COMPANY_ADDRESS_IS_NULL("20003","公司地址为空",""),
	/**公司邮政编号错误**/
	COMPANY_POSTALCODE_ERROR("20004","公司邮政编号错误",""),
	/**公司传真号码错误**/
	COMPANY_FAX_ERROR("20005","公司传真号码错误",""),
	/**公司名称不可被修改**/
	FORBID_MODIFY_COMPANY_NAME("20006","公司名称不可被修改",""),
	/**公司不存在**/
	COMPANY_NOT_EXISTED("20007","公司不存在",""),
	/**公司联系电话格式错误**/
	COMPANY_PHONE_ERROR("20008","公司联系电话格式错误",""),
	/**公司名称格式错误**/
	COMPANY_NAME_ERROR("20009","公司名称格式错误",""),
	/**公司名称过长**/
	COMPANY_NAME_LENGTH_ERROR("20010","公司名称过长",""),
	/**公司部门已达最大深度**/
	DEPT_LEVEL_TOO_DEEP("20011","公司部门已达最大深度",""),
	/**公司部门个数已达到上限**/
	DEPT_NUMBER_LIMIT("20012","公司部门个数已达到上限",""),
	/**员工已达到最大人数上限**/
	MEMBER_NUMBER_LIMIT("22001","员工已达到最大人数上限",""),
	/**会议创建已达最大上限**/
	CONF_NUMBER_LIMIT("32000","会议创建已达最大上限",""),
	/**公告发送对象为空**/
	ANNO_SEND_OBJECT_IS_NULL("90000","公告发送对象为空",""),
	/**公告有效时间错误**/
	ANNO_VALID_TIME_ERROR("90001","公告有效时间错误",""),
	/**公告操作失败**/
	ANNO_OPEARTE_ERROR("90002","公告操作失败",""),
	/**无法发送该公告**/
	ANNO_UNABLE_SEND("90003","无法发送该公告",""),
	/**无法保存该公告**/
	ANNO_UNABLE_SAVE("90004","无法保存该公告",""),
	/**无效公告**/
	ANNO_INVALID_DATA("90005","无效公告",""),
	/**员工不在公告中**/
	ANNO_NO_INCLUDE_STAFF("90006","员工不在公告中",""),
	/**公告已失效**/
	ANNO_INVALID("90007","公告已失效",""),
	/**接收人员不能为空**/
	ANNO_RECEIVE_MEMBER_NOT_NULL("90008","接收人员不能为空",""),
	/**公告不存在**/
	ANNO_NOT_EXIST("90100","公告不存在",""),
	/**投票已截止**/
	VOTE_ABORT("90200","投票已截止",""),
	/**WEB用户被顶号**/
	WEB_USER_KICK("90201","WEB用户被顶号",""),
	/**无投票项**/
	VOTE_OPTION_IS_NULL("91000","无投票项",""),
	/**已票项**/
	HAVE_VOTED("91001","已票项",""),
	/**远程控制未绑定**/
	REMOTE_CONTROL_UNBOUNDED("92000","远程控制未绑定",""),
	/**正在处理中**/
	PROCESSING("92001","正在处理中",""),
	/**远程控制超时**/
	REMOTE_CONTROL_TIMEOUT("92002","远程控制超时","");

	private String num;
	private String msg;
	private String debugInfo;
	private boolean debugSwitch = false;
	private StatusCode(String num,String msg,String debugInfo){this.num=num;this.msg=msg;this.debugInfo = debugInfo;}
	public String num(){return num;}
	public String msg(){return msg;}
	public String debugInfo(){return debugInfo;}
	public String toJSON() {
		String debugInfo = this.debugInfo;
		if(debugSwitch){this.debugInfo = "";return "{\"code\":\"" + num + "\",\"msg\":\"" + msg + "\",\"debugInfo\":\"" + debugInfo + "\"}";}
		return "{\"code\":\"" + num + "\",\"msg\":\"" + msg + "\"}";}
	public String toJSON(String msg) {
		String debugInfo = this.debugInfo;
		if(debugSwitch){this.debugInfo = "";return "{\"code\":\"" + num + "\",\"msg\":\"" + msg + "\",\"debugInfo\":\"" + debugInfo + "\"}";}
		return "{\"code\":\"" + num + "\",\"msg\":\"" + msg + "\"}";}
	public String toString() {return num;}
	public String toDiyJson(String attrName,Object value){
		String debugInfo = this.debugInfo;
		if(debugSwitch){this.debugInfo = "";return "{\"code\":\"" + num + "\",\"msg\":\"" + msg + "\",\"debugInfo\":\"" + debugInfo + "\",\"" + attrName + "\":" + value + "}";}
		return "{\"code\":\"" + num + "\",\"msg\":\"" + msg + "\",\"" + attrName + "\":" + value + "}";}
	public String toDiyJson(String attrName,String value){
		String debugInfo = this.debugInfo;
		if(debugSwitch){this.debugInfo = "";return "{\"code\":\"" + num + "\",\"msg\":\"" + msg + "\",\"debugInfo\":\"" + debugInfo + "\",\"" + attrName + "\":\"" + value + "\"}";}
		
		return "{\"code\":\"" + num + "\",\"msg\":\"" + msg + "\",\"" + attrName + "\":" + value + "}";
	}
	public String toDiyJson(boolean valueIsString,String attrName,String value){
		String debugInfo = this.debugInfo;
		if(debugSwitch){this.debugInfo = "";return "{\"code\":\"" + num + "\",\"msg\":\"" + msg + "\",\"debugInfo\":\"" + debugInfo + "\",\"" + attrName + "\":\"" + value + "\"}";}
		if(valueIsString){
			return "{\"code\":\"" + num + "\",\"msg\":\"" + msg + "\",\"" + attrName + "\":\"" + value + "\"}";
		}else{
			return "{\"code\":\"" + num + "\",\"msg\":\"" + msg + "\",\"" + attrName + "\":" + value + "}";
		}
	}
}