package iok.la.com.medicaltreatmentapplication.jpush;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/21 0021.
 */

public class JPushResultBean implements Serializable {

	/**
	 * act_money : 10.0 act_content : 换个方法 act_id : 868 act_time : 2017-01-21
	 * 18:20:39 act_addr : 青岛市市南区香港中路五矿大厦 act_username : 北北 act_userphone :
	 * 18669832683
	 */

	private String act_money;
	private String act_content;
	private String act_id;
	private String act_time;
	private String act_addr;
	private String act_username;
	private String act_userphone;

	public String getAct_money() {
		return act_money;
	}

	public void setAct_money(String act_money) {
		this.act_money = act_money;
	}

	public String getAct_content() {
		return act_content;
	}

	public void setAct_content(String act_content) {
		this.act_content = act_content;
	}

	public String getAct_id() {
		return act_id;
	}

	public void setAct_id(String act_id) {
		this.act_id = act_id;
	}

	public String getAct_time() {
		return act_time;
	}

	public void setAct_time(String act_time) {
		this.act_time = act_time;
	}

	public String getAct_addr() {
		return act_addr;
	}

	public void setAct_addr(String act_addr) {
		this.act_addr = act_addr;
	}

	public String getAct_username() {
		return act_username;
	}

	public void setAct_username(String act_username) {
		this.act_username = act_username;
	}

	public String getAct_userphone() {
		return act_userphone;
	}

	public void setAct_userphone(String act_userphone) {
		this.act_userphone = act_userphone;
	}
}
