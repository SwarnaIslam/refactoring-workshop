# TriviaGame class refactoring
## Large class smell:
TriviaGame class has too many instance variables and contains too many responsibilities. 
#### Remedies:
**Extract class:**
I have divided the responsibilies into different classes containing related responsibilies. Question class will perform all responsibilities logically related to question. I have moved askQuestion(), currentCategory() to it. 
It will create question of different categories using createQuestion() method.
**Replacing data value with object:**
To handle different type of question category, Category class is created which will be called from Question class based on player's place.
## Magic Numbers:
Many constants were used without clear meaning. For example, 
```
public boolean isPlayable() {
  return (howManyPlayers() >= 2);
}
```
Here the purpose of 2 is not clear.
#### Renaming constant:
I have given all the constants meaningfull name and moved them to a seperate GameConfig class to make use of data object.
```
int HOW_MANY_QUESTIONS =50;
int MIN_PLAYER_REQUIRED =2;
int COIN_NEEDED_TO_WIN =6;
int GAME_BOARD_SPACE =12;
```
## Black sheep:
announce() method should not be a responsibility of TriviaGame logically.
#### Remedies:
I have moved the announce() method to a separate utility class, as it is not directly related to the game logic. 

## Primitive Obsession
Information related to players were declared as arrays instead of using class.
#### Remedies:
**Making separate class:**
A separate class Player is created to handle player related data. For example, player's name, roll, place, purse, inPenalty.
**Replace array with List:**
List of player objects is created.
```
List<Player> players=new ArrayList<Player>();
```
## Switch Statement smell:
if statement is duplicated multiple times in askQuestion() method.
#### Remedies:
**Replace type code with Strategy:**
In askQuestion(), instead of checking category type first and then operating on it, currentCategory() will directly return object of Category class based on category type and askQuestion() will then operate on the object. This way we can reduce if statement significantly. 
## Duplicate code
**Extract method:**
Process of determining next player is calculated many times. A method nextPlayer() is created to do the calculation.
'''
private void nextPlayer(){
    currentPlayer++;
    if(currentPlayer==players.size())currentPlayer=0;
}
'''
Also duplicate code occurred in roll(), wasCorrectlyAnswered() which have been extracted using completeRoll() and correctAnswer() methods.
## Long Method
#### Remedies:
**Extract method:**
To reduce method length because of duplications, method extraction is used. 
**Decompose conditional:**
To reduce conditional complexity, I have extracted method from the condion in roll() 
```
public void roll(){
  .....
  if(shouldGetOutOfPenaltyBox()){
      isGettingOutOfPenaltyBox = true;
      Utility.announce(players.get(currentPlayer) + " is getting out of the penalty box");

      completeRoll();
  }
  .....
}
private boolean shouldGetOutOfPenaltyBox(){
    return players.get(currentPlayer).getPlayerRoll()%2!=0;
}
```
```
private void completeRoll(){
  .....
  if(config.placeOutOfBoard(players.get(currentPlayer))){
      config.adjustPlace(players.get(currentPlayer));
  }
  .....
}
public boolean outOfBoard(Player player){
    return player.place+player.getPlayerRoll()> GAME_BOARD_SPACE -1;
}
```
## Inappropriate Naming
add() method's purpose is not clear. What is it adding? Actually add() method is used to add players in the game.
#### Remedies:
**Giving meaningful name:**
A method name should say exactly what it does. add() method is renamed addPlayer().

# PlaintextToHtmlConverter class refactoring
## Inappropriate Naming
field name source is misleading. It is actually containing content of the text file. But source can also mean a file's source.
#### Remedies:
**Giving meaningful name:**
source is replaced by the name text. addNewLine() is also misleading as it is not clear what is being added as new line. So it is renamed as addAnNewHtmlLine.
## Primitive Obsession
Simple while loop is used to read character of string. 
#### Remedies:
**Use of enhanced for loop:**
It allows iterating over an array or other type of collection, and access each element of the collection in turn, without having to use an index variable. 
```
for (char character: this.text.toCharArray() ){
    ......
}
```
## Switch Statement
Duplicated use of switch statement.
#### Remedies:
**Use of Map:**
Encoded HTMl value is mapped with corresponding plaintext character.
```
htmlEscape.put("<","&lt");
htmlEscape.put(">","&gt");
htmlEscape.put("&","&amp");
```
## Long method
basicHtmlEncode() method is too hard to quickly comprehend. 
#### Remedies:
**Method extraction:**
Returning final result is done by calling getFinalResult() method.
**Using enhanced for loop:**
Unnecessary variable and method using is reduced.

## Black sheep
File reading should not be a responsibility of PlaintextToHtmlConverter class.
#### Remedies:
readTextFile() file is moved to a newly created class HtmlUtil. 
## Comments
Comments were used before addNewLine() as it was not self-describing.
#### Remedies:
As the newly renamed method addAnNewHtmlLine() is able to describe itself, comments were removed.

# FizzBuzz class refactoring
## conditional complexity
It is not clear why strReturn should be set to different value based on condition.
#### Remedies:
**Method extraction:**
Methods with meaningful name will be able reduce confusion to understand conditions.
```
private static boolean isFizz(int currentNumber){
    return currentNumber%3==0;
}
private static boolean isBuzz(int currentNumber){
    return currentNumber%5==0;
}
private static boolean isFizzBuzz(int currentNumber){
    return currentNumber%15==0;
}
```
    
