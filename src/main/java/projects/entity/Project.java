package projects.entity;

import java.math.BigDecimal;

public class Project {
    private Integer projectId;
    private String projectName;
    private BigDecimal estimatedHours;
    private BigDecimal actualHours;
    private Integer difficulty;
    private String notes;

    // Getters and Setters
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public BigDecimal getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(BigDecimal estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public BigDecimal getActualHours() {
        return actualHours;
    }

    public void setActualHours(BigDecimal actualHours) {
        this.actualHours = actualHours;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // toString() method for printing project details
    @Override
    public String toString() {
        String result = "";
        result += "\n  ID=" + projectId;
        result += "\n  name=" + projectName;
        result += "\n  estimatedHours=" + estimatedHours;
        result += "\n  actualHours=" + actualHours;
        result += "\n  difficulty=" + difficulty;
        result += "\n  notes=" + notes;
        return result;
    }
}