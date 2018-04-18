package com.qiuku.javaweb.mvc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;


/**
 * @TODO:StudentDao.java
 * @author:QIUKU
 */
public class StudentDao {
	
	public void deleteByFlowId(Integer flowId) {
		
		
    	
    	Connection connection = null;
    	PreparedStatement preparedStatement = null;
    	
    	try {
    		String driverclass = "com.mysql.jdbc.Driver";
    		String url = "jdbc:mysql:///mvc_database";
    		String user = "root";
    		String password = "120401";
    		
    		// TODO:加载数据库驱动程序
    		Class.forName(driverclass);
    		// TODO:获取数据库连接
    		connection = DriverManager.getConnection(url, user, password);
    		// TODO:发送并执行SQL语句
    		String sql = "DELETE FROM exam_student WHERE flow_id = ?";
    		// TODO: returns a new default PreparedStatement object containing the pre-compiled SQL statement
    		/**
    		 * If the driver supports precompilation, the method prepareStatement will send the statement to the database for precompilation. 
    		 * Some drivers may not support precompilation.
    		 * In this case, the statement may not be sent to the database until the PreparedStatement object is executed
    		 */
    		preparedStatement = connection.prepareStatement(sql);
    		preparedStatement.setInt(1, flowId);
            preparedStatement.executeUpdate();
           
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			// TODO:preparedStatment
			try {
				if(preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// TODO:connection
			try {
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

    public List<Student> getAll(){
    	
    	List<Student> students = new ArrayList<>();
    	
    	Connection connection = null;
    	PreparedStatement preparedStatement = null;
    	ResultSet resultSet = null;
    	
    	try {
    		String driverclass = "com.mysql.jdbc.Driver";
    		String url = "jdbc:mysql:///mvc_database";
    		String user = "root";
    		String password = "120401";
    		
    		// TODO:加载数据库驱动程序
    		Class.forName(driverclass);
    		// TODO:获取数据库连接
    		connection = DriverManager.getConnection(url, user, password);
    		// TODO:发送并执行SQL语句
    		String sql = "SELECT flow_id,type,id_card,exam_card,student_name,location,grade FROM exam_student";
    		// TODO: returns a new default PreparedStatement object containing the pre-compiled SQL statement
    		/**
    		 * If the driver supports precompilation, the method prepareStatement will send the statement to the database for precompilation. 
    		 * Some drivers may not support precompilation.
    		 * In this case, the statement may not be sent to the database until the PreparedStatement object is executed
    		 */
    		preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            // true if the new current row is valid; false if there are no more rows
            while(resultSet.next()) {
            	int flowId = resultSet.getInt(1);
            	int type = resultSet.getInt(2);
            	String idCard = resultSet.getString(3);
            	String examCard = resultSet.getString(4);
            	String studentName = resultSet.getString(5);
            	String location = resultSet.getString(6);
            	int grade = resultSet.getInt(7);
            	
            	Student student = new Student(flowId, type, idCard, examCard, studentName, location, grade);
            	students.add(student);
            }
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// TODO:resultSet
			try {
				if(resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// TODO:preparedStatment
			try {
				if(preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// TODO:connection
			try {
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    	
    	return students;
    }

}
