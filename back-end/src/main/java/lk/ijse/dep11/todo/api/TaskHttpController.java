package lk.ijse.dep11.todo.api;

import lk.ijse.dep11.todo.to.TaskTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/tasks")
public class TaskHttpController {

    private final DataSource dataSource;

    @Autowired
    public TaskHttpController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping(produces = "application/json", params = {"email"})
    public List<TaskTO> getAllTaskByEmail(@RequestParam String email) {
        List<TaskTO> taskList = new LinkedList<>();

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM task WHERE email = ? ORDER BY id";
            try (PreparedStatement stm = connection.prepareStatement(sql)) {
                stm.setString(1, email);
                try (ResultSet rst = stm.executeQuery()) {
                    while (rst.next()) {
                        int id = rst.getInt("id");
                        String description = rst.getString("description");
                        boolean status = rst.getBoolean("status");
                        taskList.add(new TaskTO(id, description, status, email));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving tasks for email: " + email, e);
        }

        return taskList;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = "application/json", consumes = "application/json")
    public TaskTO createTask(@RequestBody @Validated TaskTO task) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stm = connection
                    .prepareStatement("INSERT INTO task (description, status, email) VALUES (?, FALSE, ?)",
                            Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, task.getDescription());
            stm.setString(2, task.getEmail());
            stm.executeUpdate();
            ResultSet generatedKeys = stm.getGeneratedKeys();
            generatedKeys.next();
            int id = generatedKeys.getInt(1);
            task.setId(id);
            task.setStatus(false);
            return task;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{id}", consumes = "application/json")
    public void updateTask(@PathVariable int id,
                           @RequestBody @Validated(TaskTO.Update.class) TaskTO task) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmExist = connection
                    .prepareStatement("SELECT * FROM task WHERE id = ?");
            stmExist.setInt(1, id);
            if (!stmExist.executeQuery().next()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task Not Found");
            }

            PreparedStatement stm = connection
                    .prepareStatement("UPDATE task SET description = ?, status=? WHERE id=?");
            stm.setString(1, task.getDescription());
            stm.setBoolean(2, task.getStatus());
            stm.setInt(3, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") int taskId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmExist = connection
                    .prepareStatement("SELECT * FROM task WHERE id = ?");
            stmExist.setInt(1, taskId);
            if (!stmExist.executeQuery().next()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task Not Found");
            }

            PreparedStatement stm = connection.prepareStatement("DELETE FROM task WHERE id=?");
            stm.setInt(1, taskId);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
