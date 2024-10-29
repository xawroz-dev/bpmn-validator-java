package org.example;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    private List<ServiceTasksCustom> defaultNameIssues;
    private List<VariableIssue> variableNameIssues;

    public ValidationResult() {
        defaultNameIssues = new ArrayList<>();
        variableNameIssues = new ArrayList<>();
    }

    public void addDefaultNameIssue(ServiceTasksCustom task) {
        defaultNameIssues.add(task);
    }

    public void addVariableIssue(ServiceTasksCustom task, String variable, String suggestion) {
        variableNameIssues.add(new VariableIssue(task, variable, suggestion));
    }

    // Getters
    public List<ServiceTasksCustom> getDefaultNameIssues() { return defaultNameIssues; }
    public List<VariableIssue> getVariableNameIssues() { return variableNameIssues; }
}
