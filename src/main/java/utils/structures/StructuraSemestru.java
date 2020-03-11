package utils.structures;

import java.time.LocalDate;

public class StructuraSemestru {
    private LocalDate startSemester;
    private LocalDate startHoliday;
    private LocalDate endHoliday;
    private LocalDate endSemester;

    public LocalDate getStartSemester() {
        return startSemester;
    }

    public void setStartSemester(LocalDate startSemester) {
        this.startSemester = startSemester;
    }

    public LocalDate getStartHoliday() {
        return startHoliday;
    }

    public void setStartHoliday(LocalDate startHoliday) {
        this.startHoliday = startHoliday;
    }

    public LocalDate getEndHoliday() {
        return endHoliday;
    }

    public void setEndHoliday(LocalDate endHoliday) {
        this.endHoliday = endHoliday;
    }

    public LocalDate getEndSemester() {
        return endSemester;
    }

    public void setEndSemester(LocalDate endSemester) {
        this.endSemester = endSemester;
    }

    @Override
    public String toString() {
        return "StructuraSemestru{" +
                "startSemester=" + startSemester +
                ", startHoliday=" + startHoliday +
                ", endHoliday=" + endHoliday +
                ", endSemester=" + endSemester +
                '}';
    }
}
