package lk.ijse.dep11.todo.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("api/v1/tasks")
public class TaskHttpController {
    @GetMapping
    public String greeting() {
        return "<h1>Work</h1>";
    }
}
