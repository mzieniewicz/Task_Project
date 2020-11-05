package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TaskMapperTestSuite {
    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void mapToTaskTest() {
        //Given
        TaskDto task1 = new TaskDto(1L, "title", "content");
        //When
        Task resultTask = taskMapper.mapToTask(task1);
        //Then
        assertEquals(1L, resultTask.getId(), 0.01);
        assertEquals("title", resultTask.getTitle());
        assertEquals("content", resultTask.getContent());
    }

    @Test
    public void mapToTaskDtoTest() {
        Task testTask = new Task(7L, "my_task", "content01");
        //When
        TaskDto resultTaskDto = taskMapper.mapToTaskDto(testTask);
        //Then
        assertEquals(7L, resultTaskDto.getId(), 0.01);
        assertEquals("my_task", resultTaskDto.getTitle());
        assertEquals("content01", resultTaskDto.getContent());
    }

    @Test
    public void mapToTaskDtoListTest() {
        //Given
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L, "my_task1", "content1"));
        tasks.add(new Task(2L, "my_task2", "content2"));
        tasks.add(new Task(3L, "my_task3", "content3"));
        //When
        List<TaskDto> dtoList = taskMapper.mapToTaskDtoList(tasks);
        //Then
        assertNotNull(dtoList);
        assertEquals(3, dtoList.size());
        assertEquals(1L, dtoList.get(0).getId(), 0.01);
        assertEquals("my_task2", dtoList.get(1).getTitle());
        assertEquals("content3", dtoList.get(2).getContent());

    }

}
