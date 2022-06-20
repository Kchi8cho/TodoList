package com.example.todolist.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.todolist.entity.*;

@Repository
public class TodoDao{

	//DB操作をするオブジェクトの用意
	private final JdbcTemplate db;
	@Autowired
	public TodoDao(JdbcTemplate db) {
		this.db = db;
	}

	//登録(INSERT)
	public void insertDb(EntForm entform) {
		 db.update("INSERT INTO task (id,detail,title,done) VALUES(?,?,?,?)",entform.getId() ,entform.getTitle(),entform.getDetail(),entform.getDone());

	}

	//全件検索(SELECT)
	public List<EntForm> searchDb(){
		String sql = "SELECT * FROM task";
		//データベースから取り出したデータをresultDB1に入れる
		List<Map<String, Object>> resultDb1 = db.queryForList(sql);

		//画面に表示しやすい形のList(resultDB2)を用意
		List<EntForm> resultDb2 = new ArrayList<EntForm>();

		//1件ずつピックアップ
		for(Map<String,Object> result1:resultDb1) {
			//データ1件分を1つのまとまりとしたEntForm型の「entformdb」を生成
			EntForm entformdb = new EntForm();
			//id、nameのデータをentformdbに移す
		    entformdb.setId((int)result1.get("id"));
			entformdb.setTitle((String)result1.get("title"));
			 entformdb.setDetail((String)result1.get("detail"));
			 entformdb.setDone((Boolean)result1.get("done"));
			//移し替えたデータを持ったentformdbを、resultDB2に入れる
			resultDb2.add(entformdb);
		}

		//Controllerに渡す
		return resultDb2;
	}

	//削除(DELETE)
	public void deleteDb(Long id) {
		//コンソールに表示
		System.out.println("削除しました");
		//DBからデータを削除
		db.update("delete from task where id=?", id);
	}
	
	public void checkDb(Long id ) {
		List<Map<String,Object>> a = db.queryForList("SELECT done FROM task WHERE id = ?", id);
		Boolean b = (Boolean)a.get(0).get("done");
		db.update("UPDATE task SET done= ? WHERE id = ?",!b, id);
	}

	//更新画面の表示(SELECT)
	public List<EntForm> selectOne(Long id) {
		//コンソールに表示
		System.out.println("編集画面を出します");

		//データベースから目的の1件を取り出して、そのままresultDB1に入れる
		List<Map<String, Object>> resultDb1 = db.queryForList("SELECT * FROM task where id=?", id);

		//画面に表示しやすい形のList(resultDB2)を用意
		List<EntForm> resultDb2=new ArrayList<EntForm>();

		//1件ずつピックアップ
		for(Map<String,Object> result1:resultDb1) {
			//データ1件分を1つのまとまりとするので、EntForm型の「entformdb」を生成
			EntForm entformdb = new EntForm();
			//id、title,detail,doneのデータをentformdbに移す
			entformdb.setId((int)result1.get("id"));
			entformdb.setTitle((String)result1.get("title"));
			entformdb.setDetail((String)result1.get("detail"));
			entformdb.setDone((Boolean)result1.get("done"));
			//移し替えたデータを持ったentformdbを、resultDB2に入れる
			resultDb2.add(entformdb);
		}

		//Controllerに渡す
		return resultDb2;

	}

	//更新の実行(UPDATE)
	public void updateDb(Long id, EntForm entform) {
		//コンソールに表示
		System.out.println("編集の実行");

		//UPDATEを実行
		db.update("UPDATE task SET title = ? ,detail =? WHERE id = ?",entform.getTitle(),entform.getDetail(), id);
		
	}
}

