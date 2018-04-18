package com.qiuku.javaweb.mvc;

/**
 * @TODO:Student.java
 * @author:QIUKU
 */
public class Student {

	private int flowId;
	
	private int type;

	private String idCard;
	
	private String examCard;
	
	private String studentName;
	
	private String location;
	
	private int grade;
	
	

	/**
	 * Constructor
	 * @param flowId
	 * @param type
	 * @param idCard
	 * @param examCard
	 * @param studentName
	 * @param location
	 * @param grade
	 */
	public Student(int flowId, int type, String idCard, String examCard, String studentName, String location,
			int grade) {
		super();
		this.flowId = flowId;
		this.type = type;
		this.idCard = idCard;
		this.examCard = examCard;
		this.studentName = studentName;
		this.location = location;
		this.grade = grade;
	}
	
	/**
	 * Constructor
	 */
	public Student() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the flowId
	 */
	public int getFlowId() {
		return flowId;
	}

	/**
	 * @param flowId the flowId to set
	 */
	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the idCard
	 */
	public String getIdCard() {
		return idCard;
	}

	/**
	 * @param idCard the idCard to set
	 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	/**
	 * @return the examCard
	 */
	public String getExamCard() {
		return examCard;
	}

	/**
	 * @param examCard the examCard to set
	 */
	public void setExamCard(String examCard) {
		this.examCard = examCard;
	}

	/**
	 * @return the studentName
	 */
	public String getStudentName() {
		return studentName;
	}

	/**
	 * @param studentName the studentName to set
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the grade
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * @param grade the grade to set
	 */
	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	
}
