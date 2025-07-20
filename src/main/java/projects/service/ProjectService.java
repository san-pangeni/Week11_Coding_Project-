package projects.service;

import java.util.List;
import java.util.NoSuchElementException;
import projects.dao.ProjectDao;
import projects.entity.Project;
import projects.exception.DbException;

// This class acts as a bridge between the UI and the DAO.
public class ProjectService {
    private ProjectDao projectDao = new ProjectDao();

    // Calls DAO to add a project.
    public Project addProject(Project project) {
        return projectDao.insertProject(project);
    }

    // Calls DAO to fetch all projects.
    public List<Project> fetchAllProjects() {
        return projectDao.fetchAllProjects();
    }

    // Calls DAO to fetch a single project.
    public Project fetchProjectById(Integer projectId) {
        return projectDao.fetchProjectById(projectId).orElseThrow(
            () -> new NoSuchElementException("Project with project ID=" + projectId + " does not exist."));
    }

    // Calls DAO to modify project details.
    public void modifyProjectDetails(Project project) {
        if (!projectDao.modifyProjectDetails(project)) {
            throw new DbException("Project with ID=" + project.getProjectId() + " could not be modified.");
        }
    }

    // Calls DAO to delete a project.
    public void deleteProject(Integer projectId) {
        if (!projectDao.deleteProject(projectId)) {
            throw new DbException("Project with ID=" + projectId + " could not be deleted.");
        }
    }
}