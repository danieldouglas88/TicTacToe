package com.daniel.tictactoe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    //declare 3d array for the game board and other widgets
    private Button gameGrid[][] = new Button[3][3];
    private Button newGameButton;
    private TextView messageTextView;

    //set up prefs
    private SharedPreferences prefs;

    private int turn;
    private String message;
    private boolean gameOver;
    private String gameString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get references to widgets
        //first row
        gameGrid[0][0] = (Button) findViewById(R.id.square1);
        gameGrid[0][1] = (Button) findViewById(R.id.square2);
        gameGrid[0][2] = (Button) findViewById(R.id.square3);

        //second row
        gameGrid[1][0] = (Button) findViewById(R.id.square4);
        gameGrid[1][1] = (Button) findViewById(R.id.square5);
        gameGrid[1][2] = (Button) findViewById(R.id.square6);

        //third row
        gameGrid[2][0] = (Button) findViewById(R.id.square7);
        gameGrid[2][1] = (Button) findViewById(R.id.square8);
        gameGrid[2][2] = (Button) findViewById(R.id.square9);

        newGameButton = (Button) findViewById(R.id.newGameButton);
        messageTextView = (TextView) findViewById(R.id.messageTextView);

        //get default shared prefs
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //set event handlers
        for (int i = 0; i < gameGrid.length; i++){

            for (int y = 0; y < gameGrid.length; y++){
                gameGrid[i][y].setOnClickListener(this);
            }
            }
            newGameButton.setOnClickListener(this);
        setStartingValues();
    }

    @Override
    public void onPause() {
        //create game string
        gameString = "";
        String square = "";

        for(int x=0; x<gameGrid.length; x++){

            for(int y=0; y<gameGrid.length; y++){
                square = gameGrid[x][y].getText().toString();
                if(square.equals("")) {
                    square = ""; //use space for empty square
                }
                gameString +=square;

            }
            }

            //save instance variables
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("turn", turn);
            editor.putString("message", message);
            editor.putBoolean("gameOver", gameOver);
            editor.putString("gameString", gameString);
            editor.commit();

            super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();

        //restore instance variables
        turn = prefs.getInt("turn", 1);
        gameOver = prefs.getBoolean("gameOver", false);
        message = prefs.getString("message", "Player X Turn");
        gameString = prefs.getString("gameString", "         "); //nine spaces

        //set the message to restore the squares
        messageTextView.setText(message);

        //use the game strings to restore values
        int i = 0;
        for(int x=0; x<gameGrid.length; x++){

            for(int y=0; y<gameGrid[x].length; y++) {
                String square = gameString.substring(i, i+1);
                gameGrid[x][y].setText(square);
            }
            }
    }

    public void setStartingValues() {
        turn=1;
        gameOver = false;
        message = "Player X Turn";
        messageTextView.setText(message);
        gameString = "         "; //nine space

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.newGameButton:
                startNewGame();
                break;
            default:
                if(!gameOver){
                    Button b = (Button) view;

                    if(b.getText().equals("")) {
                        if (turn % 2 != 0) {
                            b.setText("X");
                            message = "Player O Turn";
                        } else {
                            b.setText("O");
                            message = "Player X Turn";
                        }
                        turn++;
                        checkForGameOver;

                    }else{
                        message = "This square is taken. Try again.";
                        }
                    }

                    messageTextView.setText(message);

                }
        }
                    private void checkForGameOver() {
                    //in progress
                    }


    private void clearGrid(){
        for (int x = 0; x<gameGrid[x].length; x++){
            for (int y = 0; y<gameGrid[x].length; y++){
                gameGrid[x][y].setText(" "); //use a space for each square
            }
        }
    }

    public void startNewGame(){
        clearGrid();
        setStartingValues();
    }
}
