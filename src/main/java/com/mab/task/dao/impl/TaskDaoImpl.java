package com.mab.task.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mab.task.dao.TaskDao;
import com.mab.task.model.Assign;
import com.mab.task.model.TaskAssignment;
import com.mab.task.model.TaskListPagination;

@Repository
public class TaskDaoImpl implements TaskDao {
	
	@Autowired
	@Qualifier("uat_jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("uat_namedJdbcTemplate")
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Override
	public Boolean saveTask(TaskAssignment task) throws Exception {
		String sql = "INSERT INTO task VALUES('"+task.getId()+"','"+task.getTaskName()+"', to_date('"+task.getTargetDate() +"','dd-MM-yyyy'),"
				+ "'"+task.getBuizResources() +"', '"+task.getStatus() +"', SYSDATE)"; // to_date('"+task.getCreatedDate() +"','yyyy-MM-dd'))
				
		jdbcTemplate.execute(sql);
		return true;
	}

	@Override
	public Boolean updateTask(TaskAssignment task) throws Exception {
		String sql = "UPDATE task SET taskname= '"+ task.getTaskName() +"',"
				+ "target_Date= to_date('"+task.getTargetDate() +"','dd-MM-yyyy'),"
				+ "buizResource='"+ task.getBuizResources() +"',"
				+ "status='"+task.getStatus() +"' WHERE id = '"+task.getId()+"'";
		jdbcTemplate.execute(sql);
		return true;
	}

	@Override
	public Boolean deleteTask(String id) throws Exception {
		String sql = "DELETE FROM task WHERE id = '"+ id +"'";
		jdbcTemplate.execute(sql);
		return true;
	}

	@Override
	public TaskAssignment getTaskById(String id) throws Exception {
		List<TaskAssignment> list = new ArrayList<>();
		String sql = "SELECT id,taskName, "
				+ " target_date, buizResource as buizResources,status "
				+ "FROM task WHERE id = '"+ id +"'";
		list = namedJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(TaskAssignment.class));
		
		return (list == null || list.size() == 0) ? null : list.get(0);
	}

	@Override
	public List<TaskAssignment> getAllTask() throws Exception { 
		List<TaskAssignment> list = new ArrayList<>();
		String sql = "SELECT id,taskName,"
				+ " to_char(target_date,'dd-mm-yyyy') as targetDate,buizResource as buizResources,status FROM task";
		list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(TaskAssignment.class));
		
		return (list == null || list.size() == 0) ? null : list;
	}
	
	@Override
	public List<TaskAssignment> getNotFinishTask() throws Exception {
		List<TaskAssignment> list = new ArrayList<>();
		String sql = "SELECT id,taskName,"
				+ " to_char(target_date,'dd-mm-yyyy') as targetDate,buizResource as buizResources,status FROM task  WHERE status ='2'";
		list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(TaskAssignment.class));
		
		return (list == null || list.size() == 0) ? null : list;
	}
	
	@Override
	public Boolean saveAssign(Assign assign) throws Exception {
		String sql = "INSERT INTO task_assign VALUES('"+assign.getTaskId()+"','"+ assign.getUserId() +"')";
		jdbcTemplate.execute(sql);
		return true;
	}

	@Override
	public List<String> getResListByTaskId(String taskId) throws Exception {
		String sql = "SELECT userId FROM task_assign where taskId = '"+ taskId +"'";
		
		List<String> data = jdbcTemplate.query(sql, new RowMapper<String>(){
			public String mapRow(ResultSet rs, int rowNum) throws SQLException{
				return rs.getString(1); // 1 column extract 
			}
		});
		return data;
	}

	@Override
	public Boolean deleteAssignByTaskId(String id) throws Exception {
		String sql = "DELETE FROM task_assign WHERE taskId = '"+ id +"'";
		jdbcTemplate.execute(sql);
		return true;
	}

	@Override
	public List<String> getResNameListByTaskId(String taskId) throws Exception {
		String sql = "SELECT ur.name FROM task_assign a"
				+ " INNER JOIN task_user ur ON a.userid = ur.userid"
				+ " where taskId = '"+ taskId +"'";
		
		List<String> data = jdbcTemplate.query(sql, new RowMapper<String>(){
			public String mapRow(ResultSet rs, int rowNum) throws SQLException{
				return rs.getString(1); // 1 column extract 
			}
		});
		return data;
	}

	@Override
	public TaskListPagination getAllTaskByPagination(int currentPage) throws Exception {
		int pageSize = 5; 
		currentPage = currentPage - 1; //start from 0 but formula want to start from 0
		
		int totalCount = jdbcTemplate.queryForObject( "SELECT COUNT (*) FROM task", Integer.class);
		int totalPageSize = (totalCount/pageSize);
		
		
		int startRecord = (currentPage * pageSize) + 1;  
		int endRecord = (currentPage + 1) * pageSize;
		if(totalCount % pageSize != 0){
		      totalPageSize ++;
		}
		
		String sql = "SELECT * FROM ("
				+ "       SELECT row_number() over (ORDER BY target_date) line_number, id, taskName, "
				+ " to_char(target_date,'dd-mm-yyyy') as targetDate, buizResource as buizResources, status FROM task"
				+ " ) WHERE line_number >= '"+startRecord+"' AND line_number <= '"+endRecord+"'"
				+ " ORDER BY targetDate";  //desc
				
		List<TaskAssignment> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(TaskAssignment.class));
		TaskListPagination listData = new TaskListPagination();	
		
		listData.setTotalPages(totalPageSize);
		listData.setCurrentPage(currentPage + 1); //start from 1 in UI
		listData.setPageSize(pageSize);
		listData.setListData(list);
		
		return listData;
	}
}
 