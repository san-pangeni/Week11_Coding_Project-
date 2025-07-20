package projects.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import projects.entity.Project;
import projects.exception.DbException;

public class ProjectDao {
    private static final String PROJECT_TABLE = "project";

    // Create
    public Project insertProject(Project project) {
        String sql = "INSERT INTO " + PROJECT_TABLE 
            + " (project_name, estimated_hours, actual_hours, difficulty, notes) "
            + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection()) {
            startTransaction(conn);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                setParameter(stmt, 1, project.getProjectName(), String.class);
                setParameter(stmt, 2, project.getEstimatedHours(), BigDecimal.class);
                setParameter(stmt, 3, project.getActualHours(), BigDecimal.class);
                setParameter(stmt, 4, project.getDifficulty(), Integer.class);
                setParameter(stmt, 5, project.getNotes(), String.class);
                
                stmt.executeUpdate();
                Integer projectId = getLastInsertId(conn, PROJECT_TABLE);
                
                commitTransaction(conn);
                project.setProjectId(projectId);
                return project;
            } catch (Exception e) {
                rollbackTransaction(conn);
                throw new DbException(e);
            }
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    // Read All
    public List<Project> fetchAllProjects() {
        String sql = "SELECT * FROM " + PROJECT_TABLE + " ORDER BY project_name";
        try (Connection conn = DbConnection.getConnection()) {
            startTransaction(conn);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    List<Project> projects = new LinkedList<>();
                    while(rs.next()) {
                        projects.add(extract(rs, Project.class));
                    }
                    return projects;
                }
            } catch (Exception e) {
                rollbackTransaction(conn);
                throw new DbException(e);
            }
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }
    
    // Read by ID
    public Optional<Project> fetchProjectById(Integer projectId) {
        String sql = "SELECT * FROM " + PROJECT_TABLE + " WHERE project_id = ?";
        try (Connection conn = DbConnection.getConnection()) {
            startTransaction(conn);
            try {
                Project project = null;
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    setParameter(stmt, 1, projectId, Integer.class);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            project = extract(rs, Project.class);
                        }
                    }
                }
                commitTransaction(conn);
                return Optional.ofNullable(project);
            } catch (Exception e) {
                rollbackTransaction(conn);
                throw new DbException(e);
            }
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    // Update
    public boolean modifyProjectDetails(Project project) {
        String sql = "UPDATE " + PROJECT_TABLE + " SET "
            + "project_name = ?, estimated_hours = ?, actual_hours = ?, "
            + "difficulty = ?, notes = ? WHERE project_id = ?";

        try (Connection conn = DbConnection.getConnection()) {
            startTransaction(conn);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                setParameter(stmt, 1, project.getProjectName(), String.class);
                setParameter(stmt, 2, project.getEstimatedHours(), BigDecimal.class);
                setParameter(stmt, 3, project.getActualHours(), BigDecimal.class);
                setParameter(stmt, 4, project.getDifficulty(), Integer.class);
                setParameter(stmt, 5, project.getNotes(), String.class);
                setParameter(stmt, 6, project.getProjectId(), Integer.class);
                
                boolean modified = stmt.executeUpdate() == 1;
                commitTransaction(conn);
                return modified;
            } catch (Exception e) {
                rollbackTransaction(conn);
                throw new DbException(e);
            }
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    // Delete
    public boolean deleteProject(Integer projectId) {
        String sql = "DELETE FROM " + PROJECT_TABLE + " WHERE project_id = ?";
        try (Connection conn = DbConnection.getConnection()) {
            startTransaction(conn);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                setParameter(stmt, 1, projectId, Integer.class);
                boolean deleted = stmt.executeUpdate() == 1;
                commitTransaction(conn);
                return deleted;
            } catch (Exception e) {
                rollbackTransaction(conn);
                throw new DbException(e);
            }
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    /* =================== HELPER METHODS =================== */

    @SuppressWarnings("unchecked")
    protected <T> T extract(ResultSet rs, Class<T> class1) throws SQLException {
        Project project = new Project();
        project.setActualHours(rs.getBigDecimal("actual_hours"));
        project.setEstimatedHours(rs.getBigDecimal("estimated_hours"));
        project.setDifficulty(rs.getObject("difficulty", Integer.class));
        project.setNotes(rs.getString("notes"));
        project.setProjectId(rs.getObject("project_id", Integer.class));
        project.setProjectName(rs.getString("project_name"));
        return (T)project;
    }

    protected Integer getLastInsertId(Connection conn, String table) throws SQLException {
        String sql = "SELECT LAST_INSERT_ID()";
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return null;
            }
        }
    }

    protected void setParameter(PreparedStatement stmt, int i, Object value, Class<?> class1) throws SQLException {
        if (Objects.isNull(value)) {
            stmt.setNull(i, java.sql.Types.VARCHAR);
        } else if (class1.equals(String.class)) {
            stmt.setString(i, (String) value);
        } else if (class1.equals(Integer.class)) {
            stmt.setInt(i, (Integer) value);
        } else if (class1.equals(BigDecimal.class)) {
            stmt.setBigDecimal(i, (BigDecimal) value);
        }
    }

    protected void startTransaction(Connection conn) throws SQLException {
        conn.setAutoCommit(false);
    }
    
    protected void commitTransaction(Connection conn) throws SQLException {
        conn.commit();
    }
    
    protected void rollbackTransaction(Connection conn) {
        try {
            conn.rollback();
        } catch (SQLException e) {
            // Nothing to do
        }
    }
}