package com.rgk.pedometer.model;

public class StepInfo {
	public int id;
	public int walkSteps;
	public int runSteps;
	public String calories;
	public long date;
	public int dayInWeek;
	public String distance;
	public String formatDate;

	public StepInfo() {
	}

	public StepInfo(int id, int walkSteps, int runSteps, String calories,
			long date, String formatDate, int dayInWeek, String distance) {
		this.id = id;
		this.walkSteps = walkSteps;
		this.runSteps = runSteps;
		this.calories = calories;
		this.date = date;
		this.formatDate = formatDate;
		this.dayInWeek = dayInWeek;
		this.distance = distance;

	}

	@Override
	public String toString() {
		return "walkSteps=" + walkSteps + ", runSteps+" + runSteps
				+ ", calories=" + calories + ", date=" + date + ", formatDate="
				+ formatDate + ", dayInWeek=" + dayInWeek + ", distance="
				+ distance;
	}

}
