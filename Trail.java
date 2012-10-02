import javax.swing.*;
import java.util.ArrayList;

public class Trail {
    private ArrayList<Coordinate> trailList = new ArrayList<Coordinate>();
    int length = trailList.size();

    public void pushCoordinate(Coordinate coordinate){
        trailList.add(coordinate);
        length++;
        System.out.println(length);
    }


    public void die(){
        trailList.clear();
        length = 0;
    }

    public int getLength() {
        return length;
    }

    public int getCoordinateX(int index){

        return (int)trailList.get(index).getX();
    }

    public int getCoordinateY(int index){

        return (int)trailList.get(index).getY();
    }
}
