package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTest {
    @InjectMocks
    private DbService service;
    @Mock
    private TaskRepository repository;

    @Test
    public void getAllTasksTest() {
        //Given
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task(2L, "task_name", "test");
        Task task2 = new Task(22L, "task_name2", "test2");
        tasks.add(task1);
        tasks.add(task2);
        when(repository.findAll()).thenReturn(tasks);
        //When
        List<Task> resultList = service.getAllTasks();
        //Then
        // assertNotNull(resultList);
        assertEquals(2, resultList.size());
        assertEquals("task_name", resultList.get(0).getTitle());
        assertEquals(22L, resultList.get(1).getId(), 0.01);
    }

    @Test
    public void getNullTaskTest() {
        //Given
        when(repository.findById(5L)).thenReturn(null);
        //When
        Optional<Task> resultTask = service.getTask(5L);
        //Then
        assertNull(resultTask);
    }

    @Test
    public void saveTaskTest() {
        //Given
        Task task1 = new Task(1L, "task_name", "test");
        when(repository.save(task1)).thenReturn(task1);
        //When
        Task resultTask = service.saveTask(task1);
        //Then
        assertEquals(1L, resultTask.getId(), 0.01);
        assertEquals("task_name", resultTask.getTitle());
        assertEquals("test", resultTask.getContent());
    }

}
