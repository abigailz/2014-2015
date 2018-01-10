package com.tsv.model;

import com.tsv.model.db.Question;
import com.tsv.model.db.User;

public class QuestionEntry {

	private Question question;
	private User user;
	
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return "QuestionEntry [question=" + question + "]";
	}
	
}
