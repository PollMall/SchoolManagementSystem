package domains;

public class Tema extends Entity<Integer> {
    private String descriere;
    private Integer startWeek;
    private Integer deadlineWeek;

    public Tema(Integer id,String descriere, Integer startWeek, Integer deadlineWeek) {
        super();
        this.setId(id);
        this.descriere = descriere;
        this.startWeek = startWeek;
        this.deadlineWeek = deadlineWeek;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Integer getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(Integer startWeek) {
        this.startWeek = startWeek;
    }

    public Integer getDeadlineWeek() {
        return deadlineWeek;
    }

    public void setDeadlineWeek(Integer deadlineWeek) {
        this.deadlineWeek = deadlineWeek;
    }

    @Override
    public String toString() {
        return "Tema{" +
                "descriere='" + descriere + '\'' +
                ", startWeek=" + startWeek +
                ", deadlineWeek=" + deadlineWeek +
                '}';
    }
}
