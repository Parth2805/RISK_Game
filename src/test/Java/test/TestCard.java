package test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.*;
import com.entity.Card;
import com.config.CardType;


public class TestCard {
    Card cr = null;

    @BeforeClass
    public static void beforeAllTesting() {
        System.out.println("This is before testing");
    }
    @Before
    public void beforeTest() {
        cr = new Card(CardType.CARD_TYPE_CAVALRY);
        cr = new Card(CardType.CARD_TYPE_ARTILLERY);
        cr = new Card(CardType.CARD_TYPE_INFANTRY);
    }

    @AfterClass
    public static void afterPerformingTests() {
        System.out.println("The test is done");
    }

    @Test
    public void testGetCardKind() {
        assertNotNull(cr.getCardKind());
        System.out.println("'assertNotNull' test is passed");
    }

}
