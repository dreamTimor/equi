package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bean.Experiment_report;
import com.bean.User;
import com.dao.ExperimentReportDao;

@Service
@Scope("prototype")
public class ExperimentReportService {
	@Autowired
	private ExperimentReportDao daoExperiment;
	
	
	public List<Experiment_report> getList(String page, String rows, User user){
		return daoExperiment.getList(page, rows, user);
	}
	public int getListSize(){
		return daoExperiment.getListSize();
	}
	public List<Experiment_report> query(String queryData, User user){
		return daoExperiment.query(queryData, user);
	}
	
	
	public int add(Experiment_report experiment, String iconFileName){
		experiment.setFilename(iconFileName);
		return daoExperiment.add(experiment);
	}
	public int delete(int id){
		Experiment_report experiment = new Experiment_report();
		experiment.setId(id);
		return daoExperiment.delete(experiment);
	}
	
}
