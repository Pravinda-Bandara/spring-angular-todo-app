package lk.ijse.dep11.todo.api;

import lk.ijse.dep11.todo.to.TaskTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
