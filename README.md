## Risk Game development
- Coding Standard : https://www.geeksforgeeks.org/java-naming-conventions/
- Exception handling : https://google.github.io/styleguide/javaguide.html#s6.2-caught-exceptions

## Build - 1

### Functional Requirements
- The game must include a command prompt that is available throughout the usage of the game (even if you have a GUI). All the commands should be validated and give proper feedback on their effect or invalidity. Each of the commands should only work in the game phase for which they are designed for. 
- The commands should use the exact same syntax as defined below. Some commands have options preceded by a
dash. Either or both options can be used and may be used multiple times in a command. Command parameters
are noted in italics.

### Map Editor

#### User-driven creation of map elements: country, continent and connectivity between countries.

##### Map editor commands
- editcontinent -add continentname continentvalue -remove continentname
- editcountry -add countryname continentname -remove countryname
- editneighbor -add countryname neighborcountryname -remove countryname neighborcountryname
- showmap (show all continents and countries and their neighbors)
- savemap filename: Saving a map to a text file exactly as edited (using the "domination" game map format).
- editmap filename: Loading a map from an existing "domination" map file to edit or create a new map from scratch if the file does not exist.

##### Verification of map correctness
- The map should be automatically validated upon loading and before saving (at least 3 types of incorrect maps). 
The validatemap command can be triggered anytime during map editing.
- Map editor command: validatemap

### Game Play
- Implementation of a game driver implementing and controlling the game phases according to the Risk rules.
- Game play command: showmap (show all countries and continents, armies on each country, ownership, and connectivity)
