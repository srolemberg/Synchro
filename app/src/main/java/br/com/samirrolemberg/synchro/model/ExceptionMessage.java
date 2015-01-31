package br.com.samirrolemberg.synchro.model;

import java.io.Serializable;

public class ExceptionMessage implements Serializable{

	private static final long serialVersionUID = -9048279257449943887L;

	private Throwable throwable;
	
	@SuppressWarnings("unused")
	private ExceptionMessage(){
		super();
	}
	public ExceptionMessage(Throwable e){
		super();
		this.throwable = e;
	}
	public Throwable getThrowable() {
		return throwable;
	}
	public void setThrowable(Throwable e) {
		this.throwable = e;
	}
	
}
