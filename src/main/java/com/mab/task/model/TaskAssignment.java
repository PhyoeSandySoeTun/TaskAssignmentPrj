package com.mab.task.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class TaskAssignment {

	private String id;
	@NotEmpty(message = "Task Name is required.")
	private String taskName;
	@NotNull(message = "Target Date is required.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date target_Date;
	@NotEmpty(message = "IT Resource is required.")
	private List<String> itResources;
	@NotEmpty(message = "Business Resource is required.")
	private String buizResources;
	private String status;
	private String createdDate;

	private String targetDate;
	private String itRes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getBuizResources() {
		return buizResources;
	}

	public void setBuizResources(String buizResources) {
		this.buizResources = buizResources;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getItResources() {
		return itResources;
	}

	public void setItResources(List<String> itResources) {
		this.itResources = itResources;
	}

	public String getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(String targetDate) {
		this.targetDate = targetDate;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public Date getTarget_Date() {
		return target_Date;
	}

	public void setTarget_Date(Date target_Date) {
		this.target_Date = target_Date;
	}

	public String getItRes() {
		return itRes;
	}

	public void setItRes(String itRes) {
		this.itRes = itRes;
	}

}
