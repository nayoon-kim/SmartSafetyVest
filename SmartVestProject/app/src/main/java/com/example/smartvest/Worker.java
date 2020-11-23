package com.example.smartvest;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Worker implements Serializable {
    int worker_id = 0;
    String vest_id = "";
    int director_id = 0;
    int warning_signal = 0;
    double latitude = 0;
    double longitude = 0;
    double methane = 0;
    double lpg = 0;
    double carbon_monoxide = 0;
    double temperature = 0;
    double humidity = 0;
    int state_methane = 0;
    int state_lpg = 0;
    int state_carbon_monoxide = 0;
    int state_temperature = 0;
    int state_humidity = 0;
    int report_signal = 0;

    public Worker(){

    }
    public Worker(int worker_id, String vest_id, int warning_signal, double latitude, double longitude,
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
        setReport_signal(0);
    }

    public void setWorker_id(int worker_id) {
        this.worker_id = worker_id;
    }

    public void setVest_id(String vest_id) {
        this.vest_id = vest_id;
    }

    public void setDirector_id(int director_id) {
        this.director_id = director_id;
    }

    public void setWarning_signal(int warning_signal) {
        this.warning_signal = warning_signal;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setMethane(Double methane) {
        this.methane = methane;
    }

    public void setLpg(Double lpg) {
        this.lpg = lpg;
    }

    public void setCarbon_monoxide(Double carbon_monoxide) {
        this.carbon_monoxide = carbon_monoxide;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public void setState_methane(int state_methane) {
        this.state_methane = state_methane;
    }

    public void setState_lpg(int state_lpg) {
        this.state_lpg = state_lpg;
    }
    public void setState_carbon_monoxide(int state_carbon_monoxide) {
        this.state_carbon_monoxide = state_carbon_monoxide;
    }
    public void setState_temperature(int state_temperature) {
        this.state_temperature = state_temperature;
    }
    public void setState_humidity(int state_humidity) {
        this.state_humidity = state_humidity;
    }
    public void setReport_signal(int report_signal) {
        this.report_signal = report_signal;
    }
    public int getWorker_id() {
        return worker_id;
    }


    @NonNull
    @Override
    public String toString() {
        return "Worker [worker_id=" + worker_id + ", vest_id=" + vest_id
                + ", warning_signal=" + warning_signal + ", latitude=" + latitude + ", longitude=" + longitude
                + ", methane=" + methane + ", lpg=" + lpg + ", carbon_monoxide=" + carbon_monoxide + ", temperature="
                + temperature + ", humidity=" + humidity+ ", report_signal=" + report_signal + "]";
    }
}
