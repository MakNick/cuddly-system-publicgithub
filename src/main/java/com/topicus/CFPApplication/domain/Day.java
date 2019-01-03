package com.topicus.CFPApplication.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(value = "Day", description = "Duration of the conference")
public class Day {
	
	@Id
	@ApiModelProperty(position = 1, required = true, value="Start date of the conference",
			example = "2018-05-23T01:20:30")
	private LocalDateTime start;
	
	@ApiModelProperty(position = 2, required = true,  value="End date of the conference",
			example = "2018-05-23T01:20:30")
	private LocalDateTime end;
	
	@ManyToOne
	@ApiModelProperty(position = 3, value="Holds the conference to which this object belongs")
	private Conference conference;

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public Conference getConference() {
		return conference;
	}

	public void setConference(Conference conference) {
		this.conference = conference;
	}

}
