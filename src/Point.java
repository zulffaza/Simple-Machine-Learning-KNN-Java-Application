public class Point {

    private double mAge;
    private double mWeight;
    private double mDistance;
    private int mAnswer;

    public Point() {
        this(0, 0, 0);
    }

    public Point(double age, double weight) {
        this(age, weight, 0);
    }

    public Point(double age, double weight, int answer) {
        mAge = age;
        mWeight = weight;
        mAnswer = answer;
    }

    public double getAge() {
        return mAge;
    }

    public void setAge(double age) {
        mAge = age;
    }

    public double getWeight() {
        return mWeight;
    }

    public void setWeight(double weight) {
        mWeight = weight;
    }

    public double getDistance() {
        return mDistance;
    }

    public void setDistance(double distance) {
        mDistance = distance;
    }

    public int getAnswer() {
        return mAnswer;
    }

    public void setAnswer(int answer) {
        mAnswer = answer;
    }

    public String dataPoint() {
        return mAnswer == 1 ? "Yes" : "No";
    }

    public boolean searchDistance(double distance) {
        return mDistance == distance;
    }
}
