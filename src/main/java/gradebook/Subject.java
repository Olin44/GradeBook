package gradebook;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.*;

public class Subject {


    private final String name;
    private final List<Grade> grades;


    public Subject(String name) {
        this(name, new ArrayList<>());
    }

    public Subject(String name, List<Grade> grades) {
        Objects.requireNonNull(name, "Name can't be null");
        this.name = name;
        this.grades = grades;
    }

    public String getName() {
        return name;
    }

    public void addGrade(Grade grade) {
        Objects.requireNonNull(grade, "Grade can't be null");
        grades.add(grade);
    }

    public List<Grade> grades() {
        return grades;
    }

    public Optional<Grade> lastGrade() {
        Optional<Grade> gradeOptional = grades.stream().reduce((first, second) -> second).stream().findFirst();
        return gradeOptional;
    }

    public void addAll(List<Grade> grades) {
        this.grades.addAll(grades);
    }

    public List<Grade> gradesByMonth(Month month) {
        return grades.stream()
                .filter(grade -> Objects.equals(grade.rateDate().getMonth(), month))
                .toList();
    }

    public OptionalDouble averageGradeRates() {
        return grades.stream()
                .mapToDouble(grade -> grade.rate().value())
                .average();
    }

    public List<Grade> gradesByDay(DayOfWeek day) {
        return grades.stream()
                .filter(grade -> Objects.equals(grade.rateDate().getDayOfWeek(), day))
                .toList();
    }

    public OptionalDouble minGradeRate() {
        return grades.stream()
                .mapToDouble(grade -> grade.rate().value())
                .min();
    }

    public OptionalDouble maxGradeRate() {
        return grades.stream()
                .mapToDouble(grade -> grade.rate().value())
                .max();
    }

    public List<Grade> gradesByWeek(int weekNumber) {
        return grades.stream()
                .filter(grade -> grade.rateDate().get(WeekFields.of(Locale.getDefault()).weekOfYear()) == weekNumber)
                .toList();
    }

    public long countGradesByRate(Rate rate) {
        return grades.stream()
                .filter(grade -> Objects.equals(grade.rate(), rate))
                .count();
    }

    public List<Grade> gradesWithoutDescription() {
        return grades.stream()
                .filter(grade -> Objects.equals(grade.description(), null))
                .toList();
    }
}
