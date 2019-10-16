/**
 * This is a Test suite Class for testing Entity
 * @author Maryam
 * @author Mahmoudreza
 * @version 0.0.1
 */
package com.entity;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TestCard.class, TestContinent.class, TestCountry.class, TestPlayer.class})

public class EntityTestSuite {
}