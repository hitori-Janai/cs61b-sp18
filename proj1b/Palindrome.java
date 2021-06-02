public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        LinkedListDeque<Character> linkedListDeque = new LinkedListDeque<>();
        char[] charArray = word.toCharArray();
        for (char c : charArray) {
            linkedListDeque.addLast(c);
        }
        return linkedListDeque;
    }

    /**
     * Any word of length 1 or 0 is a palindrome.
     * @param word
     * @return
     */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> wordToDeque = wordToDeque(word);
        while (wordToDeque.size() > 1) {
            if (!cc.equalChars(wordToDeque.removeFirst(), wordToDeque.removeLast())) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word) {
        char[] charArray = word.toCharArray();
        for (int i = 0; i < charArray.length / 2; i++) {
            if (charArray[i] != charArray[charArray.length - 1 - i]) {
                return false;
            }
        }
        return true;
    }

}
