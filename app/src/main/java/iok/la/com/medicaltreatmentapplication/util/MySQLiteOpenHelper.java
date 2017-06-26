package iok.la.com.medicaltreatmentapplication.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * SQLiteOpenHelper是一个数据库操作的帮助类
 * 实现SQLiteOpenHelper的作用：
 * 1.重写onCreate()方法，创建出数据库的表结构及添加一些初始化记录
 * 2.重写onUpgrade()方法 更新数据库表结构(软件升级)
 * @author Administrator
 *
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

//	类没有实例化,不能用作父类构造器的参数，必须声明为静态
	private static final String name = "person.db"; //数据库名称
	private static final int version = 2;// 数据库版本
	
	/**
	 * @param context 上下文对象 数据库创建在哪个目录下
	 * @param name    数据库的名称
	 * @param factory 表示在查询的时候获得一个游标工厂(数据库的查询结果集)一般使用null(即代表使用系统默认的游标工厂)
	 * @param version 数据库的版本 正整数表示必须>=1
	 */
	public MySQLiteOpenHelper(Context context) {
		super(context, name, null, version);
		
	}

	/**
	 * 当数据库第一次被创建的时候回调的方法
	 * db：数据库对象 在这个对象当中有创建，删除以及执行各种sql命令的方法
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table person (id integer primary key autoincrement,name varchar(20),age varchar(10))");

	}

	/**
	 * 当数据库版本更新的时候回调的方法
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (newVersion != oldVersion) {//说明数据库版本发生变化
			Log.i("TAG", "数据库版本更新啦！");
			//修改数据库的表结构 增加一列
			db.execSQL("alter table person add money varchar(50)");
			
//			//升级之前删除旧的数据表
//			db.execSQL("drop table if exists person");
//			// 创建新的表
//			onCreate(db);
		}

	}

}
