package com.easyText;

import java.util.ArrayList;
import java.util.HashMap;

public class Menu extends Component {

    private HashMap<Integer, MenuItem> items;
    private boolean lastZero;
    
    public Menu(int x, int y, int width, int height, String title, HashMap<Integer, MenuItem> items, boolean lastZero) {
        super(x, y, width, height, title);
        this.items = items;
        this.lastZero = lastZero;
    }

    public void setLastZero(boolean value) {
        this.lastZero = value;
    }

    public boolean getLastZero() {
        return lastZero;
    }

    public int getSelection(String text) {
        for (int i = 0; i < items.size(); i++) {
            MenuItem item = items.get(i);

            if (item.isInKeyWords(text) || text.equalsIgnoreCase(Integer.toString(i))) {
                return i;
            }
        }
        return -1;
    }

    private String fixLine(String line) {
        if (line.length() < width) {
            String espais = " ".repeat(width - line.length());
            return line + espais;
        } else {
            String nouString = line.substring(0, width);
            return nouString;
        }
    }

    public ArrayList<String> render() { 
        ArrayList<String> rst = new ArrayList<String>();
        boolean doneZero = false; 

        int begin = 0;
        if (lastZero) {
            begin = 1;
        }

        rst.add(" ".repeat(width));
        for (int i = begin; i < height; i++) {
             
            if (items.get(i) != null) {
                String linia = " "+ i + "." + items.get(i).getTitle();
                rst.add(fixLine(linia));
            } else {
                if (lastZero && doneZero == false && items.get(0) != null) {
                    String linia0 = " " + "0." + items.get(0).getTitle();
                    rst.add(fixLine(linia0));
                    doneZero = true;
                } else {
                    String liniaBuida = " ".repeat(width);
                    rst.add(liniaBuida);
                }
            } 
        }

        if (!lastZero) {
            rst.remove(rst.size() - 1);
        }

        return rst;
    } 
}
