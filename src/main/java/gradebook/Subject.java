package gradebook;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.OptionalDouble;

public class Subject {
    private final String name;
    private final List<Grade> gradeList;

    public Subject(String name, List<Grade> gradeList) {
        if (gradeList == null) {
            gradeList = new ArrayList<>();
        }
        this.name = name;
        this.gradeList = gradeList;
    }


    public void addGrade(Grade grade) {
        gradeList.add(grade);
    }

    public List<Grade> getGrades() {
        return gradeList;
    }

    public Grade getLast() {
        return gradeList.get(gradeList.size() - 1);
    }

    public void addAll(List<Grade> grades) {
        gradeList.addAll(grades);
    }

    public List<Grade> getGradesByMonth(Month month) {
        return gradeList.stream().filter(p -> p.rateDate().getMonth().equals(month)).toList();
    }

    public OptionalDouble getAverageGradeRates() {
        return gradeList.stream().mapToDouble(a -> a.rate().value()).average();
    }

    public List<Grade> getGradesByDay(DayOfWeek dayOfWeek) {
        return gradeList.stream().filter(p -> p.rateDate().getDayOfWeek().equals(dayOfWeek)).toList();
    }

    public Double getMinGradeRate() {
        return gradeList.stream().mapToDouble(a -> a.rate().value()).min().getAsDouble();
    }

    public Double getMaxGradeRate() {
        return gradeList.stream().mapToDouble(a -> a.rate().value()).max().getAsDouble();
    }

    public List<Grade> getGradesByWeek(int weekNumber) {
        return gradeList.stream().filter(p -> p.rateDate().get(WeekFields.of(Locale.getDefault()).weekOfYear()) == weekNumber).toList();
    }

    public long countGradesByRate(Rate lookingRate) {
        return gradeList.stream().filter(f -> f.rate().equals(lookingRate)).count();
    }
}
