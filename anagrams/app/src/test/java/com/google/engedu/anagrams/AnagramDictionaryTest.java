package com.google.engedu.anagrams;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class AnagramDictionaryTest {
    private AnagramDictionary dictionary;

    public AnagramDictionaryTest() {
        try {
            InputStream inputStream = new FileInputStream(new File("../app/src/main/assets/words.txt"));
            dictionary = new AnagramDictionary(new InputStreamReader(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void isGoodWord() {
        Assertions.assertTrue(dictionary.isGoodWord("nonstop", "post"));
        Assertions.assertFalse(dictionary.isGoodWord("poster", "post"));
        Assertions.assertFalse(dictionary.isGoodWord("lamp post", "post"));
        Assertions.assertTrue(dictionary.isGoodWord("spots", "post"));
        Assertions.assertFalse(dictionary.isGoodWord("apostrophe", "post"));
    }
}