package com.mab.task.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mab.task.model.TaskAssignment;
import com.mab.task.model.TaskListPagination;
import com.mab.task.model.User;
import com.mab.task.service.TaskService;
import com.mab.task.service.UserService;

@Controller
@RequestMapping("/task")
public class TaskController {
	private final Logger log = LoggerFactory.getLogger(TaskController.class);
	
	@Autowired
	private TaskService taskService;

	@Autowired
	private UserService userService;
	
	@GetMapping("/taskAssignment")
	public String assignmentForm(Model model) throws Exception {
		initializeData(model);
		model.addAttribute("form", new TaskAssignment());

		return "taskForm";
	}

	@PostMapping("/taskAssignment")
	public String saveAssign(@Valid @ModelAttribute("form") TaskAssignment form, BindingResult result, Model model) {
		initializeData(model);
			if(!result.hasErrors()) {
				try {
					taskService.saveTask(form);
					model.addAttribute("form", new TaskAssignment());
					model.addAttribute("message", "Saved Success");
				} catch (Exception e) {
					log.error("Task Assignment Saved Failed: " + e.getMessage());
					e.printStackTrace();
				}
			} else {
				model.addAttribute("form", form);
			}
			
		//return "taskForm";
	      return "redirect:/task/taskListPagination?currentPage=1";
	}

	@GetMapping("/taskList")
	public String showTaskView(Model model) {
		try {
			model.addAttribute("statusList", taskService.getStatusList());
			List<TaskAssignment> list = taskService.getAllTask();
			model.addAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "taskList";
	}
	
	@GetMapping("/taskListPagination")
	public String showTaskViewByPagination(@RequestParam("currentPage") int currentPage, Model model) {
		try {
			model.addAttribute("statusList", taskService.getStatusList());
			TaskListPagination list = taskService.getAllTaskByPagination(currentPage);
			model.addAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "taskListPagination";
	}
	
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable(name = "id") String id, Model model) {
		initializeData(model);
		try {
			TaskAssignment task = taskService.getTaskById(id);
			model.addAttribute("form", task);
		} catch (Exception ex) {
			
		}
		return "taskForm";
	}

	@GetMapping("/delete/{id}")
	public String deleteForm(@PathVariable(name = "id") String id, Model model) {
		try { 	
			taskService.deleteTask(id);
		} catch (Exception ex) {
		}
		return "redirect:/task/taskList";
	}
	
	@GetMapping("/search")
	public String showSearchUserForm(Model model) {
		model.addAttribute("user",new User());
		try {
			model.addAttribute("list", userService.getResourceUserList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return "searchForm";
		return "multipleSearch";
	}
	
	@PostMapping("/search")
	public String searchUser(@ModelAttribute("user") User searchUser,Model model) {
		try {
			model.addAttribute("user", searchUser);
			model.addAttribute("list", userService.searchUser(searchUser.getName()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "searchForm";
	}
	
	

	private void initializeData(Model model) {
		try {
			model.addAttribute("resourceList", userService.getResourceUserList());
			model.addAttribute("statusList", taskService.getStatusList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
	}

}
