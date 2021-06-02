import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    CharacterComparator offByOne = new OffByOne();
    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertFalse(palindrome.isPalindrome("cat"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("121"));
        assertTrue(palindrome.isPalindrome("caac"));
    }

    @Test
    public void testIsOffByOnePalindrome() {
        assertTrue(palindrome.isPalindrome("flake",offByOne));
        assertTrue(palindrome.isPalindrome("1",offByOne));
        assertTrue(palindrome.isPalindrome("",offByOne));
    }
}
