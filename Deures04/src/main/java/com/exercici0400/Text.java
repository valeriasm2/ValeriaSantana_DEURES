package com.exercici0400;

import java.util.ArrayList;

public class Text extends Component implements Alignable {

    private String text;
    private int truncate;
    private boolean ellipsis;
    private String align;
    
    public Text(int x, int y, int width, int height, String title, String text, int truncate, boolean ellipsis, String align) {
        super(x, y, width, height, title);
        this.text = text;
        this.truncate = truncate;
        this.ellipsis = ellipsis;
        this.align = align;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setTruncate(int truncate) {
        this.truncate = truncate;
    }

    public int getTruncate() {
        return truncate;
    }

    public void setEllipsis(boolean ellipsis) {
        this.ellipsis = ellipsis;
    }

    public boolean getEllipsis() {
        return ellipsis;
    }

    public void setAlign(String value) {
        this.align = value;
    }

    public String getAlign() {
        return align;
    }

    // Retorna el text amb els salts de línia
    // segons l'amplada disponible, cada salt de línia
    // és un nou element de l'ArrayList
    private ArrayList<String> wrapText() {
        int realWidth = width - 2;
        ArrayList<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();
    
        for (String word : words) {
            if (currentLine.length() + word.length() + 1 > realWidth) {
                lines.add(currentLine.toString().trim());
                currentLine.setLength(0);
            }
            if (currentLine.length() > 0) {
                currentLine.append(" ");
            }
            currentLine.append(word);
        }
    
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }
    
        return lines;
    }

    public ArrayList<String> render() {
        ArrayList<String> rst = wrapText();

        // Afegir linia buida al principi
        rst.add(0, " ".repeat(width)); 

        // Afegir linies buides al final
        for (int i = rst.size(); i < height; i++) {
            rst.add(" ".repeat(width));
        }

        // Asignar text segons alineació 
        for (int i = 1; i < height; i++) {
            String ln = rst.get(i).trim();
            if (null != align) {
                int espais = width - ln.length() - 1;
                switch (align) {
                    case "left" -> {
                        rst.set(i, " " + ln + " ".repeat(espais));
                    }
                    case "right" -> {
                        rst.set(i, " ".repeat(espais) + ln + " ");
                        }
                    case "center" -> {
                        espais = (espais + 1) / 2;
                        int espaisLeft = espais;
                        int espaisRight = espais;
                        if (((espais * 2) + ln.length()) < width) {
                            espaisRight += 1;
                        }
                        rst.set(i, " ".repeat(espaisLeft) + ln + " ".repeat(espaisRight));
                        }
                    default -> { }
                }
            }
        }

        return rst;
    } 
}
