package com.Main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.entity.EntityTestSuite;
import com.mapparser.MapParserTestSuite;
import com.playerparser.PlayerCommandsTestSuite;

/**
 * @author Komal
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ EntityTestSuite.class, MapParserTestSuite.class, PlayerCommandsTestSuite.class})
public class MainTestSuite {
}

