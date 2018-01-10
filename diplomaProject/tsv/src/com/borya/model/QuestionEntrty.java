package com.borya.model;

import com.borya.model.db.Question;
import com.borya.model.db.User;

public class QuestionEntrty {

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
		return "QuestionEntrty [question=" + question + ", user=" + user + "]";
	}
	
}
