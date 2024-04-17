package com.pack.service;

import com.pack.entity.Task;
import com.pack.repository.TaskRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.IsoFields;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TaskService {

    public static final int TASK_PAGE_SIZE = 50;
    public static final Locale LOCALE = Locale.of("ru");
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public TasksResult getTasksByStatus(int pageNumber) {
        // Find all tasks that aren't done and split them by status
        Map<Task.Status, List<Task>> tasksByStatus =
                repository.findAllByStatusNotOrderByChangeDateDesc(
                                Task.Status.DONE,
                                PageRequest.of(pageNumber, TASK_PAGE_SIZE)
                        ).stream()
                        .collect(Collectors.groupingBy(Task::getStatus));
        return new TasksResult(
                tasksByStatus.get(Task.Status.FILL),
                tasksByStatus.get(Task.Status.CHECK),
                tasksByStatus.get(Task.Status.CHKGB),
                tasksByStatus.get(Task.Status.WORK)
        );
    }


    public TasksResult getTasksByStatus() {
        return getTasksByStatus(0);
    }


    public CountResult getCompletedByDay() {
        List<Task> tasks =
                repository.findDoneTasksForDay(PageRequest.of(0, TASK_PAGE_SIZE));

        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH:00");
        Map<String, Long> taskCountsPerHour = countsPerUnit(tasks, hourFormatter::format);

        List<String> labels = IntStream.range(0, 24).mapToObj("%02d:00"::formatted).toList();
        List<Long> counts = labelsToCounts(labels, taskCountsPerHour);

        return new CountResult(
                labels,
                counts,
                tasks
        );
    }

    private static List<Long> labelsToCounts(List<String> labels, Map<String, Long> counts) {
        return labels.stream().map(k -> counts.getOrDefault(k, 0L)).toList();
    }

    public CountResult getCompletedByWeek() {
        List<Task> tasks =
                repository.findDoneTasksForWeek(PageRequest.of(0, TASK_PAGE_SIZE));

        Function<LocalDateTime, String> dayOfWeekFormatter = t -> formatDayOfWeek(t.getDayOfWeek());
        Map<String, Long> taskCountsPerDay = countsPerUnit(tasks, dayOfWeekFormatter);

        List<String> labels = Arrays.stream(DayOfWeek.values()).map(this::formatDayOfWeek).toList();
        List<Long> counts = labelsToCounts(labels, taskCountsPerDay);

        return new CountResult(
                labels,
                counts,
                tasks
        );
    }

    private String formatDayOfWeek(DayOfWeek dayOfWeek) {
        return dayOfWeek.getDisplayName(TextStyle.SHORT, LOCALE);
    }

    public CountResult getCompletedByMonth() {
        List<Task> tasks =
                repository.findDoneTasksForMonth(PageRequest.of(0, TASK_PAGE_SIZE));

        Function<LocalDateTime, String> monthFormatter = t -> "%02d".formatted(t.getDayOfMonth());
        Map<String, Long> tasCountsPerDay = countsPerUnit(tasks, monthFormatter);

        List<String> labels = IntStream.rangeClosed(1, YearMonth.now().lengthOfMonth()).mapToObj("%02d"::formatted).toList();
        List<Long> counts = labelsToCounts(labels, tasCountsPerDay);

        return new CountResult(
                labels,
                counts,
                tasks
        );
    }


    public CountResult getCompletedByQuarter() {
        List<Task> tasks =
                repository.findDoneTasksForQuarter(PageRequest.of(0, TASK_PAGE_SIZE));

        Map<String, Long> taskCountsPerMonth =
                countsPerUnit(tasks, t -> formatMoth(t.getMonth()));

        int quarter = LocalDate.now().get(IsoFields.QUARTER_OF_YEAR);
        List<String> labels = getMonthNamesForQuarter(quarter);
        List<Long> counts = labelsToCounts(labels, taskCountsPerMonth);

        return new CountResult(
                labels,
                counts,
                tasks
        );
    }

    private List<String> getMonthNamesForQuarter(int quarter) {
        return (switch (quarter) {
            case 1 -> List.of(Month.JANUARY, Month.FEBRUARY, Month.MARCH);
            case 2 -> List.of(Month.APRIL, Month.MAY, Month.JUNE);
            case 3 -> List.of(Month.JULY, Month.AUGUST, Month.SEPTEMBER);
            case 4 -> List.of(Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER);
            default -> throw new IllegalArgumentException("There are only four quarters");
        }).stream().map(this::formatMoth).toList();
    }

    private String formatMoth(Month month) {
        return month.getDisplayName(TextStyle.SHORT, LOCALE);
    }

    public record CountResult(
            List<?> labels,
            List<Long> counts,
            List<Task> tasks
    ) {
    }

    private Map<String, Long> countsPerUnit(List<Task> tasks, Function<LocalDateTime, String> formatter) {
        return tasks.stream()
                .map(Task::getChangeDate)
                .map(Timestamp::toLocalDateTime)
                .map(formatter)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));
    }

    public record TasksResult(
            List<Task> fill,
            List<Task> check,
            List<Task> checkGB,
            List<Task> work
    ) {
    }
}
