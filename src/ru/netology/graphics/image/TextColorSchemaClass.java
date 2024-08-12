package ru.netology.graphics.image;

public class TextColorSchemaClass implements TextColorSchema {
    char[] chars = {'#', '$', '@', '%', '*', '+', '-', '\\'};

    //char[] chars = {'▇', '●', '◉', '◍', '◎', '○', '☉', '◌', '-'}; //не для Windows
    @Override
    public char convert(int color) {
        return chars[Math.round(color / 32 - 5 / 10)];
    }
}
