package ru.netology.graphics.image;

public class Schema implements TextColorSchema {
    public Schema(char[] symbols) {
    }

    @Override
    public char convert(int color) {
        char convert = '-';

        if (color < 32) {
            convert = '#'; //#
        } else if (color < 64) {
            convert = '$';//$
        } else if (color < 96) {
            convert = '@';//@
        } else if (color < 128) {
            convert = '%';//%
        } else if (color < 160) {
            convert = '*'; //*
        } else if (color < 192) {
            convert = '+'; // +
        } else if (color < 224) {
            convert = '.'; //.
        }

        return convert;
    }
}