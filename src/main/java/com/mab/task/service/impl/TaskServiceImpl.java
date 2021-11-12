package com.mab.task.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mab.task.dao.TaskDao;
import com.mab.task.model.Assign;
import com.mab.task.model.Status;
import com.mab.task.model.TaskAssignment;
import com.mab.task.model.TaskListPagination;
import com.mab.task.service.TaskService;
import com.mab.task.util.Util;

@Service
public class TaskServiceImpl implements TaskService {
	private final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

	@Autowired
	private TaskDao taskDao;

	@Override
	public Boolean updateTask(TaskAssignment assign) throws Exception {
		return taskDao.updateTask(assign);
	}

	@Override
	public Boolean deleteTask(String id) throws Exception {
		return taskDao.deleteTask(id);
	}

	@Override
	public Boolean saveTask(TaskAssignment task) throws Exception {
		String pattern = "dd-MM-yyyy";
		if(task.getId().isEmpty()) {
			task.setId(Util.generateID());
			task.setTargetDate(new SimpleDateFormat(pattern).format(task.getTarget_Date()));
			taskDao.saveTask(task);

			Assign assign = new Assign();
			assign.setTaskId(task.getId());
			for (int i = 0; i < task.getItResources().size(); i++) {
				assign.setUserId(task.getItResources().get(i));
				taskDao.saveAssign(assign);
			}
		} else {
			task.setTargetDate(new SimpleDateFormat(pattern).format(task.getTarget_Date()));
			taskDao.updateTask(task);
			taskDao.deleteAssignByTaskId(task.getId());
			Assign assign = new Assign();
			assign.setTaskId(task.getId());
			for (int i = 0; i < task.getItResources().size(); i++) {
				assign.setUserId(task.getItResources().get(i));
				taskDao.saveAssign(assign);
			}
		}
		return true;
	}

	@Override
	public List<Status> getStatusList() throws Exception {
		List<Status> statusCbo = new ArrayList<Status>();
		Status status = new Status();
		status.setCode(1);
		status.setDescription("Completed");

		Status status2 = new Status();
		status2.setCode(0);
		status2.setDescription("Start");

		Status status1 = new Status();
		status1.setCode(2);
		status1.setDescription("Failed");

		statusCbo.add(status2);
		statusCbo.add(status);
		statusCbo.add(status1);
		return statusCbo;
	}

	@Override
	public TaskAssignment getTaskById(String id) throws Exception {
		TaskAssignment task = new TaskAssignment();
		task = taskDao.getTaskById(id);
		
		List<String> resList = taskDao.getResListByTaskId(id);
		task.setItResources(resList);
		return task;
	}

	@Override
	public List<TaskAssignment> getAllTask() throws Exception {
		List<TaskAssignment> taskList = taskDao.getAllTask();
		String res = "";
		
		for(int i=0; i<taskList.size(); i++) {
			List<String> resList = taskDao.getResNameListByTaskId(taskList.get(i).getId());
			for(int j=0; j< resList.size(); j++) {
				res += resList.get(j) + ",";
			}
			if(!res.isEmpty()) 
				res = res.substring(0, res.length() -1);
			//System.out.println(res);
			taskList.get(i).setItRes(res);
			res = "";
		}
		
		
		for(TaskAssignment task: taskList) {
			if(task.getStatus().equals("0")) {
				task.setStatus("Start");
			} else if (task.getStatus().equals("1")) {
				task.setStatus("Completed");
			} else
				task.setStatus("Failed");
		}
		
		return taskList;
	}

	@Override
	public TaskListPagination getAllTaskByPagination(int currentPage) throws Exception {
		TaskListPagination taskList = taskDao.getAllTaskByPagination(currentPage);
		String res = "";
		List<TaskAssignment> dataList = taskList.getListData();
		
		for(int i=0; i<dataList.size(); i++) {
			List<String> resList = taskDao.getResNameListByTaskId(dataList.get(i).getId());
			for(int j=0; j< resList.size(); j++) {
				res += resList.get(j) + ",";
			}
			if(!res.isEmpty()) 
				res = res.substring(0, res.length() -1);
			//System.out.println(res);
			dataList.get(i).setItRes(res);
			res = "";
		}
		
		for(TaskAssignment task: dataList) {
			if(task.getStatus().equals("0")) {
				task.setStatus("Start");
			} else if (task.getStatus().equals("1")) {
				task.setStatus("Completed");
			} else
				task.setStatus("Failed");
		}
		return taskList;
	}

}
