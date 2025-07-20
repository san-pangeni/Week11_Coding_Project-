package projects.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

// Represents a row in the project table.
public class Project {
    private Integer projectId;
    private String projectName;
    private BigDecimal estimatedHours;
    private BigDecimal actualHours;
    private Integer difficulty;
    private String notes;

    private List<Material> materials = new ArrayList<>();
    private List<Step> steps = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    // Getters and Setters
    public Integer getProjectId() { return projectId; }
    public void setProjectId(Integer projectId) { this.projectId = projectId; }
    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }
    public BigDecimal getEstimatedHours() { return estimatedHours; }
    public void setEstimatedHours(BigDecimal estimatedHours) { this.estimatedHours = estimatedHours; }
    public BigDecimal getActualHours() { return actualHours; }
    public void setActualHours(BigDecimal actualHours) { this.actualHours = actualHours; }
    public Integer getDifficulty() { return difficulty; }
    public void setDifficulty(Integer difficulty) { this.difficulty = difficulty; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public List<Material> getMaterials() { return materials; }
    public List<Step> getSteps() { return steps; }
    public List<Category> getCategories() { return categories; }

    // Used to print project details to the console.
    @Override
    public String toString() {
        String result = "";
        result += "\nID=" + projectId;
        result += "\n   name=" + projectName;
        result += "\n   estimatedHours=" + estimatedHours;
        result += "\n   actualHours=" + actualHours;
        result += "\n   difficulty=" + difficulty;
        result += "\n   notes=" + notes;
        result += "\n   Materials:" + (materials.isEmpty() ? " [No materials]" : "");
        for (Material material : materials) result += "\n      " + material;
        result += "\n   Steps:" + (steps.isEmpty() ? " [No steps]" : "");
        for (Step step : steps) result += "\n      " + step;
        result += "\n   Categories:" + (categories.isEmpty() ? " [No categories]" : "");
        for (Category category : categories) result += "\n      " + category;
        return result;
    }
}