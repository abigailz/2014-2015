package com.borya.model.db;

/**
 * 用户类型（1：执勤人员，2：任务分配，3：办事）
 * @author Administrator
 *
 */
public enum Type {
	
	/*** 管理员 **/
	manager(0),
	/*** 执勤人员 *****/
	officers(1),

	/*** 任务分配 *****/
	assign(2),
	
	/*** 办事 *****/
	staff(3);
	
	/*** 管理员 **/
	public static final int MANAGER = Type.manager.num();
	/*** 执勤人员 *****/
	public static final int OFFICERS = Type.officers.num();
	/*** 任务分配 *****/
	public static final int ASSIGN = Type.assign.num();
	/*** 办事 *****/
	public static final int STAFF = Type.staff.num();

	private int val;
	private Type(int val){
		this.val = val;
	}
	
	public final int num(){
		return val;
	}
	
	public static final boolean exists(int type){
		for(Type t : Type.values()){
			if(type == t.val){
				return true;
			}
		}
		
		return false;
	}
}
