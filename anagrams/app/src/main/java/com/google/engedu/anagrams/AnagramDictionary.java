/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private List<String> wordList = new ArrayList<>();
    private Set<String> wordSet = new HashSet<>();
    private Map<String, List<String>> lettersToWords = new HashMap<>();

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while ((line = in.readLine()) != null) {
            String word = line.trim();

            wordList.add(word);
            wordSet.add(word);

            String letters = sortLetters(word);
            List<String> words = lettersToWords.get(letters);
            if (words == null) {
                lettersToWords.put(letters, new ArrayList<>(Arrays.asList(word)));
            } else {
                words.add(word);
            }
        }
    }

    public boolean isGoodWord(String word, String base) {
        return wordSet.contains(word) && !word.contains(base);
    }

    public List<String> getAnagrams(String targetWord) {
        String letters = sortLetters(targetWord);
        List<String> words = lettersToWords.get(letters);

        if (words == null) {
            return new ArrayList<>();
        }

        ArrayList<String> result = new ArrayList<>(words);
        result.remove(targetWord);
        return result;
    }

    private String sortLetters(String word) {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<>();

        for (char c = 'a'; c <= 'z'; c++) {
            result.addAll(getAnagrams(word + c));
        }

        return result;
    }

    public String pickGoodStarterWord() {
        int index = random.nextInt(wordList.size());
        int endIndex = ((index % wordList.size()) - 1 + wordList.size()) % wordList.size();
        String word = wordList.get(index);

        while (index != endIndex && getAnagrams(word).size() <= MIN_NUM_ANAGRAMS) {
            index = (index + 1) % wordList.size();
            word = wordList.get(index);
        }

        if (index == endIndex) {
            return null;
        }
        return word;
    }
}
