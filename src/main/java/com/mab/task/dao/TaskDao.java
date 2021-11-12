package com.mab.task.dao;

import java.util.List;

import com.mab.task.model.Assign;
import com.mab.task.model.TaskAssignment;
import com.mab.task.model.TaskListPagination;

public interface TaskDao {
	Boolean saveTask(TaskAssignment assign) throws Exception;
	Boolean updateTask(TaskAssignment assign) throws Exception;
	Boolean deleteTask(String id) throws Exception;
	TaskAssignment getTaskById(String id) throws Exception;
	List<TaskAssignment> getAllTask() throws Exception;
	List<TaskAssignment> getNotFinishTask() throws Exception;
	TaskListPagination getAllTaskByPagination(int currentPage) throws Exception;
	Boolean saveAssign(Assign assign) throws Exception;
	Boolean deleteAssignByTaskId(String id) throws Exception;
	List<String> getResListByTaskId(String taskId) throws Exception;
	List<String> getResNameListByTaskId(String taskId) throws Exception;
}
