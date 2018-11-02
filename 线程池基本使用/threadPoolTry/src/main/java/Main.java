import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        Executor excutor = Executors.newFixedThreadPool(3);
        int[][] arr = new int[14][];
        for(int i = 0;i<arr.length;++i){
            arr[i] = new int[10];
            for(int j = 0;j<10;++j){
                arr[i][j] = (int)(Math.random()*10);
            }
        }

        for(int[] subarr: arr){
            excutor.execute(()->System.out.println(sum(subarr)));
        }
    }

    private static int sum(int[] arr){
        return Arrays.stream(arr).sum();
    }
}
