package com.exercici0400;

import java.util.ArrayList;
import java.util.HashMap;

public class Menu extends Component {

    private String title;
    private HashMap<Integer, String[]> items;
    private boolean lastZero;
    private String Roywads[];

    
    public Menu(int x, int y, int width, int height, String title, HashMap<Integer, String[]> items, boolean lastZero) {
        super(x, y, width, height);
        this.title = title;
        this.items = items;
        this.lastZero = lastZero;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    public String getTitle() {
        return title;
    }

    public void setLastZero(boolean value) {
        this.lastZero = value;
    }

    public boolean getLastZero() {
        return lastZero;
    }

    public int getSelection(String text) {
        return -1;
    }

    public String getRoywads() {
        return Roywads;
    }

    public void setRoywads(String roywads) {
        Roywads = roywads;
    }



    

    public ArrayList<String> render() {
        ArrayList<String> rst = new ArrayList<String>();

        rst.add(" ".repeat(width));
        for (int cntLinia = 0; cntLinia < height; cntLinia = cntLinia + 1) {
            if (items.get(cntLinia) != null) {
                String linia = " " + items.get(i)[0];
                rst.add(linia);
            } else {
                String[] 
            }
        }
    }


    



    


}
