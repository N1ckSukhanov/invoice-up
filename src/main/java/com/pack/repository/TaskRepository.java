package com.pack.repository;

import com.pack.entity.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByStatusNotOrderByChangeDateDesc(Task.Status status, Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.status = 'DONE' AND cast(t.changeDate as date) = local date ORDER BY t.changeDate DESC")
    List<Task> findDoneTasksForDay(Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.status = 'DONE' AND extract(week from t.changeDate) = extract(week from local date) AND extract(year from t.changeDate) = extract(year from local date)  ORDER BY t.changeDate DESC")
    List<Task> findDoneTasksForWeek(Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.status = 'DONE' AND extract(month from t.changeDate) = extract(month from local date) AND extract(year from t.changeDate) = extract(year from local date)  ORDER BY t.changeDate DESC")
    List<Task> findDoneTasksForMonth(Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.status = 'DONE' AND extract(quarter from t.changeDate) = extract(quarter from local date) AND extract(year from t.changeDate) = extract(year from local date)  ORDER BY t.changeDate DESC")
    List<Task> findDoneTasksForQuarter(Pageable pageable);

}