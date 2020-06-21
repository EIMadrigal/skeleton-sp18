public class Main {
    public static void main(String[] args) {
        for (String s : Boggle.solve(5, "exampleBoard.txt")) {
            System.out.println(s);
        }
    }
}
