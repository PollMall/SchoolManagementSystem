package domains;

/**
 * Clasa studentilor
 */
public class Student extends Entity<Integer> {


    private String nume;
    private String prenume;
    private Integer grupa;
    private String email;
    private String cadruDidacticIndrumatorLab;

    /**
     * Creaza obiecte de tip Student
     * @param id - id-ul studentului
     * @param nume - numele studentului
     * @param prenume - prenumele studentului
     * @param grupa - grupa studentului
     * @param email - emailul studentului
     * @param cadruDidacticIndrumatorLab - cadrul didactic indrumator laborator asociat studentului
     */
    public Student(Integer id,String nume, String prenume, Integer grupa, String email, String cadruDidacticIndrumatorLab) {
        super();
        this.setId(id);
        this.nume = nume;
        this.prenume = prenume;
        this.grupa = grupa;
        this.email = email;
        this.cadruDidacticIndrumatorLab = cadruDidacticIndrumatorLab;
    }

    /**
     * @return numele studentului
     */
    public String getNume() {
        return nume;
    }

    /**
     * Seteaza un nou nume pentru student
     * @param nume - noul nume
     */
    public void setNume(String nume) {
        this.nume = nume;
    }

    /**
     * @return prenumele studentului
     */
    public String getPrenume() {
        return prenume;
    }

    /**
     * Seteaza un nou prenume pentru Student
     * @param prenume - noul prenume
     */
    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    /**
     * @return grupa din care face parte studentul
     */
    public Integer getGrupa() {
        return grupa;
    }

    /**
     * Asigneaza studentului o noua grupa
     * @param grupa - noua grupa
     */
    public void setGrupa(Integer grupa) {
        this.grupa = grupa;
    }

    /**
     * @return emailul studentului
     */
    public String getEmail() {
        return email;
    }

    /**
     * Seteaza un nou email pentru Student
     * @param email - noul email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return numele cadrului didactic indrumator laborator asociat studentului
     */
    public String getCadruDidacticIndrumatorLab() {
        return cadruDidacticIndrumatorLab;
    }

    /**
     * Seteaza un nou cadru didactic indrumator laborator studentului
     * @param cadruDidacticIndrumatorLab - noul nume al cadrului didactic indrumator laborator asociat studentului
     */
    public void setCadruDidacticIndrumatorLab(String cadruDidacticIndrumatorLab) {
        this.cadruDidacticIndrumatorLab = cadruDidacticIndrumatorLab;
    }

    /**
     * @return string-ul ce contine datele studentului
     */
    @Override
    public String toString() {
        return "Student{" +
                "nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", grupa=" + grupa +
                ", email='" + email + '\'' +
                ", cadruDidacticIndrumatorLab='" + cadruDidacticIndrumatorLab + '\'' +
                '}';
    }
}
