package s.ashiqur.lady.data;

public class User {

    private int age;
    private String email;
    private String pass;
    private String cell;

    private boolean isMarried;
    private boolean isPregnant;
    private boolean isPregnantBefore;
    private boolean isStartedMenstruating;
    private boolean isPlanningToHaveKids;

    public boolean isPlanningToHaveKids() {
        return isPlanningToHaveKids;
    }

    public void setPlanningToHaveKids(boolean planningToHaveKids) {
        isPlanningToHaveKids = planningToHaveKids;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public boolean isMarried() {
        return isMarried;
    }

    public void setMarried(boolean married) {
        isMarried = married;
    }

    public boolean isPregnant() {
        return isPregnant;
    }

    public void setPregnant(boolean pregnant) {
        isPregnant = pregnant;
    }

    public boolean isPregnantBefore() {
        return isPregnantBefore;
    }

    public void setPregnantBefore(boolean pregnantBefore) {
        isPregnantBefore = pregnantBefore;
    }

    public boolean isStartedMenstruating() {
        return isStartedMenstruating;
    }

    public void setStartedMenstruating(boolean startedMenstruating) {
        isStartedMenstruating = startedMenstruating;
    }

    @Override
    public String toString() {
        return "["+age+" "+email+" "+pass+" "+cell+"\n"
                +isMarried+isPregnant+isPregnantBefore+isStartedMenstruating+"]";
    }

    public User(int age, String email, String pass, String cell, boolean isMarried, boolean isPregnant, boolean isPregnantBefore, boolean isStartedMenstruating, boolean isPlanningToHaveKids) {
        this.age = age;
        this.email = email;
        this.pass = pass;
        this.cell = cell;
        this.isMarried = isMarried;
        this.isPregnant = isPregnant;
        this.isPregnantBefore = isPregnantBefore;
        this.isStartedMenstruating = isStartedMenstruating;
        this.isPlanningToHaveKids = isPlanningToHaveKids;
    }
}
