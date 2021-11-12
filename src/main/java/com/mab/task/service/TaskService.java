package com.mab.task.service;

import java.util.List;

import com.mab.task.model.Status;
import com.mab.task.model.TaskAssignment;
import com.mab.task.model.TaskListPagination;

public interface TaskService {
	List<Status> getStatusList() throws Exception;
	Boolean saveTask(TaskAssignment assign) throws Exception;
	Boolean updateTask(TaskAssignment assign) throws Exception;
	Boolean deleteTask(String id) throws Exception;
	TaskAssignment getTaskById(String id) throws Exception;
	List<TaskAssignment> getAllTask() throws Exception;
	List<TaskAssignment> getNotFinishTask() throws Exception;
	TaskListPagination getAllTaskByPagination(int currentPage) throws Exception;
}
