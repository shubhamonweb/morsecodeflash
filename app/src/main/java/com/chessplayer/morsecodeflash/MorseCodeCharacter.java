package com.chessplayer.morsecodeflash;

/**
 * Created by barriosj on 5/22/15.
 */
public class MorseCodeCharacter {

    private String letter;
    private String code;

    public MorseCodeCharacter(String letter, String code) {
        this.letter = letter;
        this.code = code;
    }

    public MorseCodeCharacter() {
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}