/*
	Author: Shehriar Burney
	University of Illinois at Chicago
	Course: CS 342, Prof. Mark Hallenbeck

	Description: This application is a gambling game called Keno. The application uses javafx to create a UI for the game and allow the user some help navigating the application.
	There is a Rules page in the application where you can learn how to play the game, as well as a window which shows you the odds of winning in the game.
 */

import javafx.animation.*;
import javafx.scene.shape.Box;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.transform.Rotate;

import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.Text;
import javafx.stage.Window;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.*;


public class JavaFXTemplate extends Application implements EventHandler<ActionEvent>{
	private int countGames = 0;

	private boolean newLook = false;
	private String mainFont = "Verdana";
	private String backgroundGrey = "#949494";
	private Bet bet = new Bet();
	private Drawing drawing = new Drawing();
	private ArrayList<Button> buttonGridList = new ArrayList<Button>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("KENO");

		// Create the moving cuboid and the text on the main menu
		Box box = new Box();
		box = createCube();

		Text text = new Text();
		text = createText();


		Menu menu = new Menu("Options");

		// Create Play menu
		MenuItem playButton = new MenuItem("Play!");
		playButton.setOnAction(e -> playStage(primaryStage).show());

		// Create Rules menu
		MenuItem showRulesButton = new Menu("Rules");
		showRulesButton.setOnAction(e -> rulesStage().show());

		// Create Odds menu
		MenuItem showOddsButton = new MenuItem("Show Odds of winning");
		showOddsButton.setOnAction(e -> oddsStage().show());

		// Create Exit menu
		MenuItem exitButton = new MenuItem("Exit");
		exitButton.setOnAction(e -> primaryStage.close());

		MenuBar menubar = new MenuBar();
		menu.getItems().add(playButton);
		menu.getItems().add(showRulesButton);
		menu.getItems().add(showOddsButton);
		menu.getItems().add(exitButton);
		menubar.getMenus().add(menu);

		VBox vbox = new VBox(10);
		vbox.getChildren().add(menubar);


		Pane MainMenu = new Pane();
		MainMenu.getChildren().addAll(box, text, vbox);
		MainMenu.setStyle("-fx-background-color: " + backgroundGrey);

		Scene scene = new Scene(MainMenu, 700, 700, Color.BLUE);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// Create Title text and set it's dimensions and attributes
	private Text createText(){
		Text text = new Text("KENO");
		text.setX(250);
		text.setY(550);
		text.setFont(Font.font("Helvetica", 60));
		text.setStroke(Color.BLACK);
		text.setFill(Color.INDIANRED);

		return text;
	}

	// Create a 3D box and make it rotate / move
	private Box createCube(){
		Duration d1 = Duration.millis(5000);

		// Creating 3D box
		Box box = new Box();
		box.setWidth(200);
		box.setHeight(100);
		box.setDepth(100);

		box.setTranslateX(0);
		box.setTranslateY(300);
		box.setTranslateZ(0);

		PhongMaterial mat = new PhongMaterial();
		mat.setSpecularColor(Color.BLACK);
		mat.setDiffuseColor(Color.LIGHTCORAL);
		box.setMaterial(mat);

		// Making the box move from one side to another
		TranslateTransition translate = new TranslateTransition(d1);
		translate.setToX(750f);
		translate.setCycleCount(-1);
		translate.setAutoReverse(true);

		SequentialTransition seqT = new SequentialTransition(box, translate);
		seqT.play();

		// Making the box rotate
		Rotate xRotation = new Rotate(25, Rotate.X_AXIS);
		Rotate yRotation = new Rotate(25, Rotate.Y_AXIS);
		box.getTransforms().addAll(xRotation, yRotation);
		RotateTransition rt = new RotateTransition(Duration.millis(4000), box);

		rt.setAxis(Rotate.X_AXIS);
		rt.setByAngle(360);
		rt.setCycleCount(-1);
		rt.play();
		return box;
	}

	// One method for each Spot button.
	private void oneSpotButtonPressed(Button oneSpot, Button fourSpot, Button eightSpot, Button tenSpot, TextField betText){
		oneSpot.setStyle("-fx-base: #90EE90;");
		fourSpot.setStyle("-fx-base: #FFCCCB;");
		eightSpot.setStyle("-fx-base: #FFCCCB;");
		tenSpot.setStyle("-fx-base: #FFCCCB;");

		betText.setDisable(false);
		bet.setNumSpots(1);
	}
	private void fourSpotButtonPressed(Button oneSpot, Button fourSpot, Button eightSpot, Button tenSpot, TextField betText){
		fourSpot.setStyle("-fx-base: #90EE90;");
		oneSpot.setStyle("-fx-base: #FFCCCB;");
		eightSpot.setStyle("-fx-base: #FFCCCB;");
		tenSpot.setStyle("-fx-base: #FFCCCB;");

		betText.setDisable(false);
		bet.setNumSpots(4);
	}
	private void eightSpotButtonPressed(Button oneSpot, Button fourSpot, Button eightSpot, Button tenSpot, TextField betText){
		eightSpot.setStyle("-fx-base: #90EE90;");
		fourSpot.setStyle("-fx-base: #FFCCCB;");
		oneSpot.setStyle("-fx-base: #FFCCCB;");
		tenSpot.setStyle("-fx-base: #FFCCCB;");

		betText.setDisable(false);
		bet.setNumSpots(8);
	}
	private void tenSpotButtonPressed(Button oneSpot, Button fourSpot, Button eightSpot, Button tenSpot, TextField betText){
		tenSpot.setStyle("-fx-base: #90EE90;");
		fourSpot.setStyle("-fx-base: #FFCCCB;");
		eightSpot.setStyle("-fx-base: #FFCCCB;");
		oneSpot.setStyle("-fx-base: #FFCCCB;");

		betText.setDisable(false);
		bet.setNumSpots(10);
	}

	// When the "Submit bet" button is pressed from Play Screen.
	private void _submitBetButton(Bet bet, Button submitButton, TextField betAmount, Button oneSpot, Button fourSpot, Button eightSpot, Button tenSpot, TextField numGames, Button numGamesButton, Label betError){
		if(Integer.parseInt(betAmount.getText()) >= 1) {
			// Set value of bet
			submitButton.setVisible(false);
			betError.setVisible(false);
			bet.setBetAmount(Integer.parseInt(betAmount.getText()));

			// Disables all spot buttons
			oneSpot.setDisable(true);
			fourSpot.setDisable(true);
			eightSpot.setDisable(true);
			tenSpot.setDisable(true);

			betAmount.setDisable(true);
			submitButton.setDisable(true);
			numGames.setDisable(false);
			numGamesButton.setDisable(false);
		}
		else{
			betError.setVisible(true);
		}
	}

	// When the grid buttons are pressed from Play screen.
	private void gridButtonPressed(Button button, int number) {
		// If it is LIGHT_RED then turn it LIGHT_GREEN
		if (button.getStyle() == "-fx-base: #FFCCCB;") {
			if (drawing.canAdd(number, bet.getNumSpots())) {
				drawing.add(number);
				button.setStyle(null);
				button.setStyle("-fx-base: #90EE90;");
				button.setOpacity(1);
			}
		} else {
			button.setStyle(null);
			button.setStyle("-fx-base: #FFCCCB;");
			button.setOpacity(1);
			drawing.remove(number);
		}
	}

	// When the player decides to play again after they have finished a game
	private Stage _playAgain(Stage primaryStage){
		drawing.clear();
		bet.clear();
		buttonGridList.clear();
		countGames = 0;
		return playStage(primaryStage);
	}

	// After the player has finished their number of games, this window will show up
	private Stage _endScreen(Stage primaryStage){
		double winnings = bet.winnings(drawing.numMatches);

		VBox vbox = new VBox(15);
		Stage endStage = new Stage();
		endStage.setTitle("END");

		// To close application
		Button close = new Button("Close");
		close.setOnAction(e -> System.exit(0));

		// To play again
		Button playAgain = new Button("Play Again");
		playAgain.setOnAction(e -> _playAgain(primaryStage).show());

		HBox buttonsHBox = new HBox(20);
		buttonsHBox.getChildren().addAll(close, playAgain);
		buttonsHBox.setAlignment(Pos.CENTER);

		Label initialBet = new Label("Initial Bet: $" + bet.getBetAmount());
		initialBet.setFont(Font.font(mainFont, 14));

		Label numDraws = new Label("Number of draws: " + bet.getNumDraws());
		numDraws.setFont(Font.font(mainFont, 14));

		HBox hbox = new HBox(20);
		hbox.setAlignment(Pos.CENTER);
		hbox.getChildren().addAll(initialBet, numDraws);

		Label totalNumMatches = new Label("Total Amount of Matches: " + drawing.numMatches);
		totalNumMatches.setFont(Font.font(mainFont, 18));

		Label winningsText = new Label("Total Winnings: $" + winnings);
		winningsText.setFont(Font.font(mainFont, 18));

		vbox.getChildren().addAll(hbox, winningsText, totalNumMatches, buttonsHBox);
		vbox.setAlignment(Pos.CENTER);
		BorderPane bp = new BorderPane();
		bp.setCenter(vbox);

		Scene scene = new Scene(bp, 700, 700);
		endStage.setScene(scene);
		return endStage;
	}

	// When the user selects next game, this window appears
	private void _nextGameButton(Button nextGame, Stage primaryStage, Stage playStage){
		countGames++;
		// Check if n number of games have been completed.
		if(countGames == bet.getNumDraws()){
			_endScreen(primaryStage).show();
		}
		else {
			playStage(primaryStage).close();
			buttonGridList.clear();
			drawing.nextGameClear();

			playStage(primaryStage).show();
		}
	}

	private void printMatch(int i){
		int index = drawing.selectedDrawing.get(i);
		Button button = buttonGridList.get(index - 1);
		button.setOpacity(1);
		if (drawing.matchedDrawing.contains(index)) {
			button.setStyle("-fx-base: #FFD700"); // Gold color
			System.out.println("Gold " + index);
		} else {
			button.setStyle("-fx-base: #add8e6"); // Light blue
		}
	}

	// Method for matching the user numbers and the selected numbers
	private void matchDrawings(VBox vbox, Button submitButton, Button randomizeButton, Label drawingError){
		// Checking if there are sufficient amount of numbers that the user picked
		if(drawing.getUserSize() == bet.numSpots) {
			drawingError.setVisible(false);
			submitButton.setDisable(true);
			submitButton.setVisible(false);
			randomizeButton.setDisable(true);
			randomizeButton.setVisible(false);

			// Matching the user drawings and the selected drawings
			drawing.selectGameDraw();
			drawing.matching();

			// Disabling the grid buttons
			for (int i = 0; i < buttonGridList.size(); i++) {
				Button button = buttonGridList.get((i));
				button.setDisable(true);
			}

			// Setting color for user selected numbers as green
			for(int i = 0; i<drawing.userSize; i++){
				int index = drawing.userDrawing.get(i);
				Button button = buttonGridList.get(index - 1);
				button.setStyle("-fx-base: #90EE90"); // Light green
				button.setOpacity(1);

			}

			// Setting color for matching numbers as gold and Bot selected numbers as blue
			for (int i = 0; i < drawing.selectedSize; i++) {
				PauseTransition pause = new PauseTransition(Duration.seconds(1));
				pause.playFromStart();
				int index = i;
				pause.setOnFinished(e -> printMatch(index));
			}

			// Sorting the matched numbers before presenting
			ArrayList<Integer> sortedMatched = new ArrayList<Integer>();
			sortedMatched = drawing.matchedDrawing;
			Collections.sort(sortedMatched);

			Label numMatches = new Label("Number of matches: " + Integer.toString(drawing.getNumMatches()));
			numMatches.setFont(Font.font(mainFont, 20));
			numMatches.setTextFill(Color.WHITE);
			Label whichMatches = new Label();
			whichMatches.setFont(Font.font(mainFont, 14));
			whichMatches.setTextFill(Color.WHITE);

			String numbersMatchedstr = "Which matched: " + sortedMatched.get(0);
			for(int i = 1; i<drawing.numMatches; i++){
				numbersMatchedstr = numbersMatchedstr + ", " + sortedMatched.get(i);
			}

			whichMatches.setText(numbersMatchedstr);
			vbox.getChildren().addAll(numMatches, whichMatches);
		}
		else{
			// Display error if there aren't enough user numbers
			drawingError.setVisible(true);
		}
	}

	// Method for when the user submits their number of draws
	private void _numGamesSubmit(GridPane gridpane, VBox vbox, VBox labelVbox, TextField numGames, Button numGamesSubmit, Button randomize, Button nextGame, Label numDrawsError){
		// Checking if number of draws is within range.
		if(Integer.parseInt(numGames.getText()) >= 1 && Integer.parseInt(numGames.getText()) <= 4) {
			numGames.setDisable(true);
			numGamesSubmit.setDisable(true);
			numGamesSubmit.setVisible(false);
			randomize.setDisable(false);
			randomize.setVisible(true);

			numDrawsError.setVisible(false);

			// Setting the number of draws
			bet.setNumDraws(Integer.parseInt(numGames.getText()));

			nextGame.setVisible(true);
			if (countGames == bet.getNumDraws()) {
				nextGame.setText("End");
			}

			Button submitGrid = new Button("Submit");
			Label drawingError = new Label("You need to select " + bet.getNumSpots() + " numbers");
			drawingError.setVisible(false);

			randomize.setOnAction(e -> _randomizeButton(gridpane, labelVbox, randomize, submitGrid));
			submitGrid.setOnAction(e -> matchDrawings(labelVbox, submitGrid, randomize, drawingError));

			vbox.getChildren().addAll(submitGrid, randomize, drawingError);
			// Creating 80 buttons in an 8x10 matrix formation. These buttons are added to their own ArrayList as well.
			printGrid(gridpane);
		}
		else{
			// Display error if number of draws is outside of range.
			numDrawsError.setVisible(true);
		}

	}

	// To print the grid
	private void printGrid(GridPane gridpane){
		buttonGridList.clear();
		drawing.nextGameClear();
		for (int r = 0; r < 10; r++) {
			for (int c = 1; c < 9; c++) {
				int number = 8 * r + c;
				Button button = new Button(String.valueOf(number));
				button.setShape(new Rectangle(40, 40));
				button.setMinSize(40, 40);
				button.setStyle(null);
				button.setStyle("-fx-base: #6a6a6a");
				button.setOpacity(1);
				button.setOnAction(e -> gridButtonPressed(button, number));
				buttonGridList.add(button);
				gridpane.add(button, c, r);
			}
		}

	}

	// When player decides to randomize their draw
	private void _randomizeButton(GridPane gridpane, VBox vbox, Button randomize, Button submitGrid){
		submitGrid.setDisable(true);
		submitGrid.setVisible(false);
		randomize.setDisable(true);
		randomize.setVisible(false);

		drawing.nextGameClear();
		drawing.randomize(bet);

		matchDrawings(vbox, submitGrid, randomize, new Label());
	}

	// When the first draw has been completed, it will change the UI for the remaining rounds
	private void afterFirstDraw(VBox vbox, GridPane gridpane, Label spotLabel, Button oneSpot, Button fourSpot, Button eightSpot, Button tenSpot, TextField numGamesText, Button numGamesSubmit, TextField betAmount, Button submitBet, Label betInputLabel, Button randomize, Button nextGame, Label numGamesLabel){
		if(countGames>0){
			spotLabel.setVisible(false);
			oneSpot.setDisable(true);
			oneSpot.setVisible(false);
			fourSpot.setDisable(true);
			fourSpot.setVisible(false);
			eightSpot.setDisable(true);
			eightSpot.setVisible(false);
			tenSpot.setDisable(true);
			tenSpot.setVisible(false);

			numGamesText.setDisable(true);
			numGamesText.setVisible(false);
			numGamesSubmit.setDisable(true);
			numGamesSubmit.setVisible(false);
			betAmount.setDisable(true);
			betAmount.setVisible(false);
			submitBet.setDisable(true);
			submitBet.setVisible(false);
			betInputLabel.setDisable(true);
			betInputLabel.setVisible(false);

			nextGame.setVisible(true);

			randomize.setDisable(false);
			submitBet.setDisable(false);

			// Create new objects for the new UI

			Label Winnings = new Label("Winnings since starting: $" + bet.winnings(drawing.getNumMatches()));
			Winnings.setFont(Font.font("Verdana", 14));

			Label betLabel = new Label("Bet Amount: $" + bet.getBetAmount());
			Label numMatchesLabel = new Label("Number of matches: " + drawing.getNumMatches());

			numGamesLabel.setText("Amount of Draws: " + bet.numDraws);

			betLabel.setFont(Font.font(mainFont, 14));
			betLabel.setTextFill(Color.WHITE);

			numGamesText.setFont(Font.font(mainFont, 14));
			numMatchesLabel.setTextFill(Color.WHITE);

			Label drawingError = new Label("You need to select " + bet.getNumSpots() + " numbers");
			drawingError.setVisible(false);

			Button submitGrid = new Button("Submit");
			randomize.setOnAction(e -> _randomizeButton(gridpane, vbox, randomize, submitGrid));
			submitGrid.setOnAction(e -> matchDrawings(vbox, submitGrid, randomize, drawingError));

			randomize.setOnAction(e->_randomizeButton(gridpane, vbox, randomize, submitGrid));

			gridpane.getChildren().clear();
			printGrid(gridpane);

			vbox.getChildren().addAll(Winnings, betLabel, numMatchesLabel, randomize, submitGrid, drawingError);

		}
	}

	// If user decides they want a new look
	private void _newLook(BorderPane borderPane){
		if(newLook){
			borderPane.setStyle("-fx-background-color: #949494");
			newLook = false;
		}
		else {
			borderPane.setStyle("-fx-background-color: #FFa80F");
			newLook = true;
		}

	}

	// The window for the main playing stage.
	private Stage playStage(Stage primaryStage){
		Stage playStage = new Stage();
		playStage.setTitle("KENO");
		// Border Pane for main window.
		BorderPane borderPane = new BorderPane();

		// Vbox for everything in left border.
		VBox vbox = new VBox(10);

		// Creating a menu bar and it's components
		MenuItem newLookButton = new MenuItem("New Look");
		newLookButton.setOnAction(e -> _newLook(borderPane));

		// Create Rules menu
		MenuItem showRulesButton = new Menu("Rules");
		showRulesButton.setOnAction(e -> rulesStage().show());

		// Create Odds menu
		MenuItem showOddsButton = new MenuItem("Show Odds of winning");
		showOddsButton.setOnAction(e -> oddsStage().show());

		Menu menu = new Menu("Options");
		MenuItem exitButton = new MenuItem("Exit");
		exitButton.setOnAction(e -> System.exit(0));


		MenuBar menubar = new MenuBar();
		menu.getItems().add(newLookButton);
		menu.getItems().add(showRulesButton);
		menu.getItems().add(showOddsButton);
		menu.getItems().add(exitButton);
		menubar.getMenus().add(menu);

		borderPane.setTop(menubar);

		GridPane gridpane = new GridPane();
		gridpane.setAlignment(Pos.CENTER);

		// Create betting text and submit button.
		Label betInputLabel = new Label("Bet ($):");
		TextField betAmount = new TextField();
		betAmount.setPromptText("Enter Bet Amount");
		betAmount.setDisable(true);

		betInputLabel.setFont(Font.font(mainFont, 14));
		betInputLabel.setTextFill(Color.WHITE);

		// Creating spot buttons and it's attributes.
		Label spotLabel = new Label("Select the number of spots");
		spotLabel.setFont(Font.font("Verdana", 14));
		spotLabel.setTextFill(Color.WHITE);

		// Creating buttons for number of spots
		Button oneSpot = new Button("1"); oneSpot.setStyle("-fx-base: #FFCCCB;");
		Button fourSpot = new Button("4"); fourSpot.setStyle("-fx-base: #FFCCCB;");
		Button eightSpot = new Button("8"); eightSpot.setStyle("-fx-base: #FFCCCB;");
		Button tenSpot = new Button("10"); tenSpot.setStyle("-fx-base: #FFCCCB;");

		oneSpot.setOnAction(e -> oneSpotButtonPressed(oneSpot, fourSpot, eightSpot, tenSpot, betAmount));
		fourSpot.setOnAction(e -> fourSpotButtonPressed(oneSpot, fourSpot, eightSpot, tenSpot, betAmount));
		eightSpot.setOnAction(e -> eightSpotButtonPressed(oneSpot, fourSpot, eightSpot, tenSpot, betAmount));
		tenSpot.setOnAction(e -> tenSpotButtonPressed(oneSpot, fourSpot, eightSpot, tenSpot, betAmount));

		Label numGamesLabel = new Label();
		numGamesLabel.setText("Number of Draws:");
		numGamesLabel.setFont(Font.font(mainFont, 14));
		numGamesLabel.setTextFill(Color.WHITE);

		TextField numGamesText = new TextField();
		numGamesText.setPromptText("Enter number of draws");
		numGamesText.setDisable(true);

		Button numGamesSubmit = new Button("Submit");
		numGamesSubmit.setDisable(true);

		Button randomize = new Button("Randomize");
		randomize.setDisable(true);

		Button submitBet = new Button("Submit");

		Button nextGame = new Button("Next Game");
		nextGame.setVisible(false);

		Label gameNumber = new Label("Game " + (countGames+1));
		gameNumber.setFont(Font.font(mainFont, 14));
		gameNumber.setTextFill(Color.WHITE);

		Label betError = new Label("Bet Amount has to be at least $1");
		betError.setFont(Font.font(mainFont, 9));
		betError.setVisible(false);

		Label numDrawsError = new Label("Number of draws has to be between 1-4");
		numDrawsError.setFont(Font.font(mainFont, 9));
		numDrawsError.setVisible(false);

		afterFirstDraw(vbox,gridpane, spotLabel, oneSpot, fourSpot, eightSpot, tenSpot, numGamesText, numGamesSubmit, betAmount, submitBet, betInputLabel, randomize, nextGame, numGamesLabel);

		nextGame.setOnAction(e -> _nextGameButton(nextGame, primaryStage, playStage));

		submitBet.setOnAction(e ->
				_submitBetButton(bet, submitBet, betAmount, oneSpot, fourSpot, eightSpot, tenSpot, numGamesText, numGamesSubmit, betError)
		);
		VBox labelVbox = new VBox();
		numGamesSubmit.setOnAction(e -> _numGamesSubmit(gridpane, vbox, labelVbox, numGamesText, numGamesSubmit, randomize, nextGame, numDrawsError));

		HBox betHbox = new HBox(10);
		betHbox.getChildren().addAll(betAmount, betError);

		HBox numDrawsHBox = new HBox (10);
		numDrawsHBox.getChildren().addAll(numGamesText, numDrawsError);

		labelVbox.setAlignment(Pos.TOP_CENTER);

		HBox nextGameHbox = new HBox(15);
		nextGameHbox.getChildren().addAll(labelVbox, nextGame);
		nextGameHbox.setAlignment(Pos.CENTER_RIGHT);
		borderPane.setBottom(nextGameHbox);

		// Put the spot buttons in hBox
		HBox hbox = new HBox(5);
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.getChildren().addAll(oneSpot, fourSpot, eightSpot, tenSpot);
		if(!newLook){
			borderPane.setStyle("-fx-background-color: #949494");
		}
		else{
			borderPane.setStyle("-fx-background-color: #FFa80F");

		}

		vbox.setAlignment(Pos.CENTER_LEFT);
		vbox.getChildren().addAll(spotLabel, hbox, betInputLabel, betHbox, submitBet, numGamesLabel, numDrawsHBox, numGamesSubmit, gameNumber);

		borderPane.setLeft(vbox);
		borderPane.setCenter(gridpane);

		Scene scene = new Scene(borderPane, 700, 700);
		playStage.setScene(scene);

		return playStage;
	}

	// Window for when you press the rules button
	private Stage rulesStage(){
		Stage rulesStage = new Stage();
		VBox vbox = new VBox(20);
		vbox.setAlignment(Pos.CENTER);
		BorderPane rulesBorderPane = new BorderPane();
		rulesBorderPane.setStyle("-fx-background-color: #949494");
		rulesStage.setTitle("Rules");
		Button back = new Button("Back");
		rulesBorderPane.setTop(back);

		Label l1 = new Label("1. Pick number of spots (number of numbers to select) and hit submit");
		l1.setTextFill(Color.WHITE);
		l1.setFont(Font.font("Verdana", 14));

		Label l2 = new Label("2. Pick the amount you would like to bet.");
		l2.setTextFill(Color.WHITE);
		l2.setFont(Font.font("Verdana", 14));

		Label l3 = new Label("2. Pick the number of draws (number of time you want to play) and hit submit");
		l3.setTextFill(Color.WHITE);
		l3.setFont(Font.font("Verdana", 14));

		Label l4 = new Label("3. Select the numbers on the grid from 1-80, this has to be the same amount as number of spots");
		l4.setTextFill(Color.WHITE);
		l4.setFont(Font.font("Verdana", 14));

		Label l5 = new Label("	You can even decide to hit the randomize button which will select the numbers for you");
		l5.setTextFill(Color.WHITE);
		l5.setFont(Font.font("Verdana", 14));

		Label l6 = new Label("4. After you have selected your numbers, 20 numbers will be randomly drawn");
		l6.setTextFill(Color.WHITE);
		l6.setFont(Font.font("Verdana", 14));

		Label l7 = new Label("5. Your winnings depend on the amount of money you had bet, the number of spots, ");
		l7.setTextFill(Color.WHITE);
		l7.setFont(Font.font("Verdana", 14));

		Label l8 = new Label("number of draws and the number of matched numbers");
		l8.setTextFill(Color.WHITE);
		l8.setFont(Font.font("Verdana", 14));

		vbox.getChildren().addAll(l1, l2, l3, l4, l5, l6, l7, l8);

		back.setOnAction(e -> rulesStage.close());
		rulesBorderPane.setCenter(vbox);
		Scene scene = new Scene(rulesBorderPane, 700, 700);
		rulesStage.setScene(scene);

		return rulesStage;
	}

	// Window for when you press the "Odds for winning" button.
	private Stage oddsStage(){
		Stage oddsStage = new Stage();
		VBox vbox = new VBox(20);
		vbox.setAlignment(Pos.CENTER);
		BorderPane oddsBorderPane = new BorderPane();
		oddsStage.setTitle("Odds of Winning");
		Button back = new Button("Back");
		oddsBorderPane.setTop(back);

		Label l0 = new Label("Odds of Winning for each number of spots if you bet $1");
		l0.setTextFill(Color.WHITE);
		l0.setFont(Font.font("Verdana", 14));

		Label l1 = new Label("For 1 Spot: 1 in 4");
		l1.setTextFill(Color.WHITE);
		l1.setFont(Font.font("Verdana", 14));

		Label l2 = new Label("For 4 Spots: 1 in 3.86");
		l2.setTextFill(Color.WHITE);
		l2.setFont(Font.font("Verdana", 14));

		Label l3 = new Label("For 8 Spots: 1 in 9.77");
		l3.setTextFill(Color.WHITE);
		l3.setFont(Font.font("Verdana", 14));

		Label l4 = new Label("For 10 Spots: 1 in 9.05");
		l4.setTextFill(Color.WHITE);
		l4.setFont(Font.font("Verdana", 14));

		vbox.getChildren().addAll(l0, l1, l2, l3, l4);

		if(!newLook){
			oddsBorderPane.setStyle("-fx-background-color: #949494");
		}
		else{
			oddsBorderPane.setStyle("-fx-background-color: #FFa80F");
		}

		back.setOnAction(e -> oddsStage.close());
		oddsBorderPane.setCenter(vbox);
		Scene scene = new Scene(oddsBorderPane, 700, 700);
		oddsStage.setScene(scene);

		return oddsStage;
	}

	@Override
	public void handle(ActionEvent event){
	}
}
