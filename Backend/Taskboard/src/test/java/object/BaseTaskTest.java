package object;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BaseTaskTest {

    private BaseTask genericTask(LocalDateTime date) {
        return new BaseTask("Test task.", false, date, 0);
    }

    @Test
    void isTaskDateUpdating() {
        var currentDate = LocalDateTime.now();
        var sampleTask = genericTask(currentDate);
        assertEquals(currentDate, sampleTask.getDate());
        sampleTask.changeTaskStatus();
        assertNotEquals(currentDate, sampleTask.getDate());
    }
}
