package org.example;

import java.util.List;

public class ServiceTasksCustom {
    private String fileName;
    private String id;
    private String name;
    private List<String> variables;

    public ServiceTasksCustom(String fileName, String id, String name, List<String> variables) {
        this.fileName = fileName;
        this.id = id;
        this.name = name;
        this.variables = variables;
    }

    // Getters
    public String getFileName() { return fileName; }
    public String getId() { return id; }
    public String getName() { return name; }
    public List<String> getVariables() { return variables; }
}
