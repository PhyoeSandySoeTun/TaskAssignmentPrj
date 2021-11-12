package com.mab.task.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mab.task.model.TaskAssignment;
import com.mab.task.service.TaskService;

@Controller
public class WelcomeController {

	@Autowired
	private TaskService taskService;

	@GetMapping("/")
	public String notFinishedAssign(Model model) {
		try {
			model.addAttribute("statusList", taskService.getStatusList());
			List<TaskAssignment> list = taskService.getNotFinishTask();
			model.addAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "notFinishAssign";
	}

}
