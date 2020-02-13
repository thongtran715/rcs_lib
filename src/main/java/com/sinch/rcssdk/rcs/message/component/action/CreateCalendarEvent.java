package com.sinch.rcssdk.rcs.message.component.action;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sinch.rcssdk.rcs.message.enums.ActionType;
@JsonIgnoreProperties({"type"})
public class CreateCalendarEvent extends Action {

    private String start_time;
    private String end_time;
    private String title;
    private String description;

    public CreateCalendarEvent() {
        super(ActionType.create_calendar_event);
    }

    public CreateCalendarEvent(String start_time, String end_time, String title, String description) {
        super(ActionType.create_calendar_event);
        this.start_time = start_time;
        this.end_time = end_time;
        this.title = title;
        this.description = description;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
