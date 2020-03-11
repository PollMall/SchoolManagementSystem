package utils.structures;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StructuraAnUniversitar {
    private String anUniversitar;
    private StructuraSemestru semestru1;
    private StructuraSemestru semestru2;

    public StructuraAnUniversitar() {

        this.semestru1=new StructuraSemestru();
        this.semestru2=new StructuraSemestru();
        //pentru sem1.csv
        Path path= Paths.get("src/main/resources/AnUniversitar2019-2020/sem1.csv");
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String line="";
        try(BufferedReader br=new BufferedReader(new FileReader(String.valueOf(path)))) {
            while((line=br.readLine())!=null){
                String[] values=line.split(",");
                this.anUniversitar=values[0];
                this.semestru1.setStartSemester(LocalDate.parse(values[1],dateTimeFormatter));
                this.semestru1.setStartHoliday(LocalDate.parse(values[2],dateTimeFormatter));
                this.semestru1.setEndHoliday(LocalDate.parse(values[3],dateTimeFormatter));
                this.semestru1.setEndSemester(LocalDate.parse(values[4],dateTimeFormatter));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //pentru sem2
        path=Paths.get("src/main/resources/AnUniversitar2019-2020/sem2.csv");
        line="";
        try(BufferedReader br=new BufferedReader(new FileReader(String.valueOf(path)))) {
            while((line=br.readLine())!=null){
                String[] values=line.split(",");
                this.semestru2.setStartSemester(LocalDate.parse(values[0],dateTimeFormatter));
                this.semestru2.setStartHoliday(LocalDate.parse(values[1],dateTimeFormatter));
                this.semestru2.setEndHoliday(LocalDate.parse(values[2],dateTimeFormatter));
                this.semestru2.setEndSemester(LocalDate.parse(values[3],dateTimeFormatter));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getAnUniversitar() {
        return anUniversitar;
    }

    public void setAnUniversitar(String anUniversitar) {
        this.anUniversitar = anUniversitar;
    }

    public StructuraSemestru getSemestru1() {
        return semestru1;
    }

    public void setSemestru1(StructuraSemestru semestru1) {
        this.semestru1 = semestru1;
    }

    public StructuraSemestru getSemestru2() {
        return semestru2;
    }

    public void setSemestru2(StructuraSemestru semestru2) {
        this.semestru2 = semestru2;
    }

    @Override
    public String toString() {
        return "StructuraAnUniversitar{" +
                "anUniversitar='" + anUniversitar + '\'' +
                ", semestru1=" + semestru1 +
                ", semestru2=" + semestru2 +
                '}';
    }

    /**
     *
     * @return null - daca data curenta este in vacanta;
     *          altfel returneaza semestrul curent.
     */
    public Integer getCurrentSemester(LocalDate localDate) {
        if(localDate.compareTo(this.semestru1.getStartSemester())>0 && localDate.compareTo(this.semestru1.getEndSemester())<0)
            return 1;
        if(localDate.compareTo(this.semestru2.getStartSemester())>0 && localDate.compareTo(this.semestru2.getEndSemester())<0)
            return 2;
        return null;
    }

    /**
     *
     * @return null - daca data curenta este in vacanta;
     *          altfel returneaza saptamana curenta din anul universitar.
     */
    public Integer getCurrentWeek(LocalDate localDate) {
        Integer currentSemester=this.getCurrentSemester(localDate);
        Integer currentWeek=null;
        if(currentSemester!=null){
            //LocalDate currentDate=LocalDate.now();
            if(currentSemester==1){
                if(localDate.compareTo(this.semestru1.getEndHoliday())>0){
                    if(localDate.compareTo(this.semestru1.getEndHoliday().plusWeeks(1))<=0)
                        currentWeek = 13;
                    currentWeek = 14;
                }
                else if(localDate.compareTo(this.semestru1.getStartHoliday())<0)
                    currentWeek = (localDate.getDayOfYear()-this.semestru1.getStartSemester().getDayOfYear())/7+1;
            }
            else{
                if(localDate.compareTo(this.getSemestru2().getStartHoliday())<0){
                    currentWeek = (localDate.getDayOfYear()-this.semestru2.getStartSemester().getDayOfYear())/7+1;
                }
                else if(localDate.compareTo(this.getSemestru2().getEndHoliday())>0){
                    currentWeek= (localDate.getDayOfYear()-this.semestru2.getStartSemester().getDayOfYear())/7;
                }
            }
        }
        return currentWeek;
    }
}
