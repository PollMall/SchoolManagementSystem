package domains;

import java.time.LocalDate;

public class Nota extends Entity<String> {
    private LocalDate data;
    private String profesor;
    private Integer valoare;
    private String feedback;

    public Nota(Integer idStudent,Integer idTema,LocalDate data, String profesor, Integer valoare, String feedback) {
        this.setId(idStudent+" "+idTema);
        this.data = data;
        this.profesor = profesor;
        this.valoare = valoare;
        this.feedback = feedback;
    }

    public Integer getNota() {
        return valoare;
    }

    public void setNota(Integer nota) {
        this.valoare = nota;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    @Override
    public String toString() {
        return "Nota{" +
                "data=" + data +
                ", profesor='" + profesor + '\'' +
                ", nota=" + valoare +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
