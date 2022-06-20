package com.example.todolist.form;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.todolist.dao.*;
import com.example.todolist.entity.*;;

@Controller
public class TodoController {
	

	//入力画面
	@RequestMapping("/form")
	public String form(Model model, Form form) {
		model.addAttribute("maintitle","Todoリスト");
		return "form/input";
	}

	//確認画面(localhost:8080/confirm)
	@RequestMapping("/confirm")
	public String confirm(Model model, @Validated Form form, BindingResult result) {

		if(result.hasErrors()) {
			model.addAttribute("maintitle", "再入力");
			return "form/input";
		}

		model.addAttribute("maintitle", "確認ページ");
		return "form/confirm";
	}


	//完了画面・登録(INSERT)
	//DAOのオブジェクトが座る椅子を用意
	private final TodoDao tododao;

	//DAOを予め控室に待機させておき、必要なときに呼んで座らせる
	@Autowired
	public TodoController(TodoDao tododao) {
		this.tododao = tododao;
	}

	@RequestMapping ("/complete")
	public String complete(Form form, Model model){

		EntForm entform = new EntForm();
		entform.setTitle(form.getTitle());//フォームの値をエンティティに入れ直し
		 entform.setDetail(form.getDetail());//フォームの値をエンティティに入れ直し

		tododao.insertDb(entform);
		return "form/complete";
	}
	@RequestMapping("/")
	public String top(Model model ,Form form ) {
		 List<EntForm> list = tododao.searchDb();
	 	 model.addAttribute("dbList", list);
		model.addAttribute("maintitle","Todoリスト");
		
		return "index";
	}
	 

	// //削除(DELETE)
	 @RequestMapping("/del/{id}")
	 public String destory(@PathVariable Long id ) {
	 	tododao.deleteDb(id);
	 	return "redirect:/";
	 }
	  @RequestMapping("/check/{id}")
	  public String check(@PathVariable Long id ) {
	  	tododao.checkDb(id);
	  	return "redirect:/";
	  }

	 //更新画面の表示(SELECT)
	 @RequestMapping("/edit/{id}")
	 public String editView(@PathVariable Long id, Model model) {

	 	//DBからデータを1件取ってくる(リストの形)
	 	List<EntForm> list = tododao.selectOne(id);
	 	//リストから、オブジェクトだけをピックアップ
	 	EntForm entformdb = list.get(0);

	//スタンバイしているViewに向かって、データを投げる
	 	model.addAttribute("form", entformdb);
	 	model.addAttribute("title", "編集ページ");
	 	return "form/edit";
	 }
	 //更新処理(UPDATE)
	 @RequestMapping("/edit/{id}/exe")
	 public String editExe(@PathVariable Long id, Model model, Form form) {

	 	//フォームの値をエンティティに入れ直し
	 	EntForm entform = new EntForm();
		 entform.setDetail(form.getDetail());
	 	entform.setTitle(form.getTitle());
		

	 	//更新の実行
	 	tododao.updateDb(id,entform);

	 	//一覧画面へリダイレクト
	 	return "redirect:/";
	}

}
