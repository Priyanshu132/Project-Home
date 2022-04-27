package mind.blower.home;

public class Student_deatils {
    private String name;
    private int mob;
    private String branch;
   private int room;
   private String course;

    public Student_deatils(String name,  String branch, String course,int room, int mob) {
        this.name = name;
        this.mob = mob;
        this.branch = branch;
        this.room = room;
        this.course = course;
    }

    public Student_deatils() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getLoc() {
//        return loc;
//    }
//
//    public void setLoc(String loc) {
//        this.loc = loc;
//    }

//

//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getID() {
//        return ID;
//    }
//
//    public void setID(String ID) {
//        this.ID = ID;
//    }

//    public String getReligion() {
//        return religion;
//    }
//
//    public void setReligion(String religion) {
//        this.religion = religion;
//    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getMob() {
        return mob;
    }

    public void setMob(int mob) {
        this.mob = mob;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
