import org.code.theater.Theater;

public class TheaterRunner {
    public static void main(String[] args) {
        // Create a scene using the data files
        DataScene scene = new DataScene("data1.txt", "data2.txt");

        // Play the scene (drawScene() is already called inside the constructor)
        Theater.playScenes(scene);
    }
}
