package workshop;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
class GameConfig{
    int HOW_MANY_QUESTIONS =50;
    int MIN_PLAYER_REQUIRED =2;
    int COIN_NEEDED_TO_WIN =6;
    int GAME_BOARD_SPACE =12;
    public void adjustPlace(Player player){
        player.place+=player.getPlayerRoll()- GAME_BOARD_SPACE;
    }
    public boolean placeOutOfBoard(Player player){
        return player.place+player.getPlayerRoll()> GAME_BOARD_SPACE -1;
    }
}
public class TriviaGame {
    int currentPlayer=0;
    boolean isGettingOutOfPenaltyBox=false;
    GameConfig config=new GameConfig();
    List<Player> players=new ArrayList<Player>();
    Question question=new Question(config.HOW_MANY_QUESTIONS);

    public RevisedTriviaGame(){
        question.createQuestion();
    }
    public boolean isPlayable() {
        return (howManyPlayers() >= config.MIN_PLAYER_REQUIRED);
    }

    public boolean addPlayer(String playerName){
        players.add(new Player(playerName));

        Utility.announce(playerName + " was added");
        Utility.announce("They are player number " + players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }
    public void roll(){
        if(players.get(currentPlayer).inPenaltyBox){
            if(shouldGetOutOfPenaltyBox()){
                isGettingOutOfPenaltyBox = true;
                Utility.announce(players.get(currentPlayer) + " is getting out of the penalty box");

                completeRoll();
            }
            else{
                Utility.announce(players.get(currentPlayer) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }
        }
        else{
            completeRoll();
        }
    }
    private boolean shouldGetOutOfPenaltyBox(){
        return players.get(currentPlayer).getPlayerRoll()%2!=0;
    }
    private void completeRoll(){

        if(config.placeOutOfBoard(players.get(currentPlayer))){
            config.adjustPlace(players.get(currentPlayer));
        }
        Utility.announce(players.get(currentPlayer).getName()
                + "'s new location is "
                + players.get(currentPlayer).place);
        Utility.announce("The category is " + question.getCategoryName());
        question.askQuestion(players.get(currentPlayer));
    }
    public boolean wasCorrectlyAnswered() {
        if(players.get(currentPlayer).inPenaltyBox&&!isGettingOutOfPenaltyBox){
            nextPlayer();
            return  true;
        }
        return correctAnswer();
    }
    private boolean correctAnswer(){
        Utility.announce("Answer was correct!!!!");
        players.get(currentPlayer).purse++;
        Utility.announce(players.get(currentPlayer)
                + " now has "
                + players.get(currentPlayer).purse
                + " Gold Coins.");

        boolean winner = didPlayerWin();
        nextPlayer();
        return winner;
    }
    public boolean wrongAnswer() {
        Utility.announce("Question was incorrectly answered");
        Utility.announce(players.get(currentPlayer) + " was sent to the penalty box");
        players.get(currentPlayer).inPenaltyBox= true;

        nextPlayer();
        return true;
    }
    private boolean didPlayerWin() {
        return !(players.get(currentPlayer).purse== config.COIN_NEEDED_TO_WIN);
    }

    private void nextPlayer(){
        currentPlayer++;
        if(currentPlayer==players.size())currentPlayer=0;
    }
}
class Utility{
    public static void announce(Object message){
        System.out.println(message);
    }
}
class Question{
    private int howManyQuestions;
    private int playersPlace;
    private Category pop=new Category("Pop") ;
    private Category science=new Category("Science") ;
    private Category rock=new Category("Rock") ;
    private Category sports=new Category("Sports") ;
    public Question(int howManyQuestions){
        this.howManyQuestions=howManyQuestions;
    }

    public void createQuestion(){
        for (int i = 0; i < this.howManyQuestions; i++) {
            this.pop.addQuestion();
            this.science.addQuestion();
            this.sports.addQuestion();
            this.rock.addQuestion();
        }
    }

    public Category currentCategory(){
        if(isScience()){
            return this.science;
        }
        if(isPop()){
            return this.pop;
        }
        if(isSports()){
            return this.sports;
        }
        return this.rock;
    }
    public boolean isScience(){
        return this.playersPlace ==1|| this.playersPlace ==5|| this.playersPlace ==9;
    }
    public boolean isPop(){
        return this.playersPlace ==0|| this.playersPlace ==4|| this.playersPlace ==8;
    }
    public boolean isSports(){
        return this.playersPlace ==2|| this.playersPlace ==6|| this.playersPlace ==10;
    }
    public void askQuestion(Player player){
        this.playersPlace =player.place;
        this.currentCategory().ask();
    }
    public String getCategoryName(){
        return currentCategory().categoryName;
    }
}

class Player{
    private String name;
    public int purse;
    public boolean inPenaltyBox;
    public int place;
    private String currentCategory;
    private int playerRoll;
    public  Player(String playerName){
        this.name=playerName;
        this.purse=0;
        this.place=0;
        this.inPenaltyBox=false;
    }

    public int getPlayerRoll() {
        return playerRoll;
    }

    public void setPlayerRoll(int playerRoll) {
        this.playerRoll = playerRoll;
    }
    public String getName() {
        return name;
    }

}
class Category{
    LinkedList categorizedQuestion =null;
    String categoryName;

    public Category(String categoryName){
        categorizedQuestion=new LinkedList();
        this.categoryName=categoryName;
    }
    public String ask() {
        return categorizedQuestion.removeFirst().toString();
    }

    public void addQuestion() {
        int newIndex= categorizedQuestion.size();
        categorizedQuestion.addLast(this.categoryName+" Question "+newIndex);
    }

}
