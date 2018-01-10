package com.borya.model.db;

/**
 * 状态（1：未处理，2：已分配，3：处理完成，4：已关闭）
 * @author Administrator
 *
 */
public enum State {

	Default(0),
	
	/** 未处理 **/
	Untreated(1),
	
	/** 已分配 ***/
	Allocated(2),

	/** 处理完成 ****/
	Complete(3),
	
	/** 已关闭  ***/
	Close(4);
	
	public static final int DEFAULT = State.Default.num();
	
	public static final int UNTREATED = State.Untreated.num();

	public static final int COMPLETE = State.Complete.num();

	public static final int CLOSE = State.Close.num();
	
	private int val;
	private State(int val){
		this.val = val;
	}
	
	public final int num(){
		return val;
	}
	
	public static final boolean exists(int state){
		for(State t : State.values()){
			if(state == t.val){
				return true;
			}
		}
		
		return false;
	}
}