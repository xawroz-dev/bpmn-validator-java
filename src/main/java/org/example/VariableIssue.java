package org.example;

public class VariableIssue {
    private ServiceTasksCustom task;
    private String variable;
    private String suggestion;

    public VariableIssue(ServiceTasksCustom task, String variable, String suggestion) {
        this.task = task;
        this.variable = variable;
        this.suggestion = suggestion;
    }

    // Getters
    public ServiceTasksCustom getTask() { return task; }
    public String getVariable() { return variable; }
    public String getSuggestion() { return suggestion; }
}
