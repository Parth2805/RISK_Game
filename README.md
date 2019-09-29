## SOEN 6441
- Coding Standard : https://www.geeksforgeeks.org/java-naming-conventions/
- Exception handling : https://google.github.io/styleguide/javaguide.html#s6.2-caught-exceptions

## Components

### Map Editor

#### User-driven creation of map elements: country, continent, and connectivity between countries.
- Map editor commands:
- editcontinent -add continentname continentvalue -remove continentname
- editcountry -add countryname continentname -remove countryname
- editneighbor -add countryname neighborcountryname -remove countryname neighborcountryname
- showmap (show all continents and countries and their neighbors)

#### Saving a map to a text file exactly as edited (using the “domination” game map format).
- Map editor command: savemap filename, editmap filename
- Loading a map from an existing “domination” map file to edit or create a new map from scratch if the file does not
exist.

### Verification of map correctness. 
- The map should be automatically validated upon loading and before saving (at
least 3 types of incorrect maps). The validatemap command can be triggered anytime during map editing.
- Map editor command: validatemap

### Game Play
