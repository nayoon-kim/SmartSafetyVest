package com.hanium_ict.smartvest.vo;

import java.util.Date;

public class WorkerVO {

	private int worker_id;
	private String vest_id;
	private int director_id;
	private int warning_signal;
	private int report_signal;
	private double latitude;
	private double longitude;
	private double methane;
	private double lpg;
	private double carbon_monoxide;
	private double temperature;
	private double humidity;
	private int state_methane = 0;
	private int state_lpg = 0;
	private int state_carbon_monoxide = 0;
	private int state_temperature = 0;
	private int state_humidity = 0;
	
	public WorkerVO() {}
	
	public WorkerVO(int director_id) {
		setDirector_id(director_id);
	}
	public WorkerVO(int worker_id, int report_signal) {
		setWorker_id(worker_id);
		setReport_signal(report_signal);
	}
	
	public WorkerVO(int worker_id, String vest_id, int warning_signal, double latitude, double longitude, 
			double methane, double lpg, double carbon_monoxide, double temperature, double humidity, int state_methane, int state_lpg, int state_carbon_monoxide, int state_temperature, int state_humidity) {
		setWorker_id(worker_id);
		setVest_id(vest_id);
		setWarning_signal(warning_signal);
		setLatitude(latitude);
		setLongitude(longitude);
		setMethane(methane);
		setLpg(lpg);
		setCarbon_monoxide(carbon_monoxide);
		setTemperature(temperature);
		setHumidity(humidity);
		setState_methane(state_methane);
		setState_lpg(state_lpg);
		setState_carbon_monoxide(state_carbon_monoxide);
		setState_temperature(state_temperature);
		setState_humidity(state_humidity);
	}
	
	public int getState_methane() {
		return state_methane;
	}

	public void setState_methane(int state_methane) {
		this.state_methane = state_methane;
	}

	public int getState_lpg() {
		return state_lpg;
	}

	public void setState_lpg(int state_lpg) {
		this.state_lpg = state_lpg;
	}

	public int getState_carbon_monoxide() {
		return state_carbon_monoxide;
	}

	public void setState_carbon_monoxide(int state_carbon_monoxide) {
		this.state_carbon_monoxide = state_carbon_monoxide;
	}

	public int getState_temperature() {
		return state_temperature;
	}

	public void setState_temperature(int state_temperature) {
		this.state_temperature = state_temperature;
	}

	public int getState_humidity() {
		return state_humidity;
	}

	public void setState_humidity(int state_humidity) {
		this.state_humidity = state_humidity;
	}

	public int getWorker_id() {
		return worker_id;
	}
	public void setWorker_id(int worker_id) {
		this.worker_id = worker_id;
	}
	public String getVest_id() {
		return vest_id;
	}
	public void setVest_id(String vest_id) {
		this.vest_id = vest_id;
	}
	public int getDirector_id() {
		return director_id;
	}
	public void setDirector_id(int director_id) {
		this.director_id = director_id;
	}
	public int getWarning_signal() {
		return warning_signal;
	}
	public void setWarning_signal(int warning_signal) {
		this.warning_signal = warning_signal;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getMethane() {
		return methane;
	}
	public void setMethane(double methane) {
		this.methane = methane;
	}
	public double getCarbon_monoxide() {
		return carbon_monoxide;
	}
	public void setCarbon_monoxide(double carbon_monoxide) {
		this.carbon_monoxide = carbon_monoxide;
	}
	public double getLpg() {
		return lpg;
	}
	public void setLpg(double lpg) {
		this.lpg = lpg;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public double getHumidity() {
		return humidity;
	}
	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}
	public int getReport_signal() {
		return report_signal;
	}
	public void setReport_signal(int report_signal) {
		this.report_signal = report_signal;
	}
	@Override
	public String toString() {
		return "WorkerVO [worker_id=" + worker_id + ", vest_id=" + vest_id
				+ ", warning_signal=" + warning_signal + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", methane=" + methane + ", lpg=" + lpg + ", carbon_monoxide=" + carbon_monoxide + ", temperature="
				+ temperature + ", humidity=" + humidity+ ", report_signal=" + report_signal + "]";
	}
	
}
