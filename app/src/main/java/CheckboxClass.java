public class CheckboxClass {
    private String courseCode;
    private boolean selected;

    public CheckboxClass(String courseCode, boolean Selected) {
        this.courseCode = courseCode;
        this.selected = Selected;
    }

    public boolean isSelected() {return selected;}
    public void select(boolean selected) {this.selected = selected;}
    public String getCourseCode() {return courseCode;}
    public void setCourseCode(String courseCode) {this.courseCode = courseCode;}

}
