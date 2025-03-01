package application;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Pong extends Application {
	
	//variable
	private static final int width = 800;
	private static final int height = 600;
	private static final int PLAYER_HEIGHT = 100;
	private static final int PLAYER_WIDTH = 15;
	private static final double BALL_R = 15;
	private int ballYSpeed = 1;
	private int ballXSpeed = 1;
	private double playerOneYPos = height / 2;
	private double playerTwoYPos = height / 2;
	private double ballXPos = width / 2;
	private double ballYPos = height / 2;
	private int scoreP1 = 0;
	private int scoreP2 = 0;
	private boolean gameStarted;
	private int playerOneXPos = 0;
	private double playerTwoXPos = width - PLAYER_WIDTH;
		
	public void start(Stage stage) throws Exception {
		stage.setTitle("P O N G");
		
        //tamaño de fondo
		Canvas canvas = new Canvas(width, height);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		//JavaFX Timeline = animación de forma libre definida por KeyFrames y su duración 
		Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
		//número de ciclos en la animación INDEFINIDO = repetir indefinidamente
		tl.setCycleCount(Timeline.INDEFINITE);
		
		//control del mouse (mover y hacer clic)
		canvas.setOnMouseMoved(e ->  playerOneYPos  = e.getY());
		canvas.setOnMouseClicked(e ->  gameStarted = true);
		stage.setScene(new Scene(new StackPane(canvas)));
		stage.show();
		tl.play();
	}

	private void run(GraphicsContext gc) {
		//establecer gráficos

		
        //establecer color de fondo
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, width, height);
		
		//establecer texto
		gc.setFill(Color.WHITE);
		gc.setFont(Font.font(25));
		
		if(gameStarted) {

			//establecer el movimiento de la pelota
			ballXPos+=ballXSpeed;
			ballYPos+=ballYSpeed;
			
			// oponente simple de la computadora que sigue la pelota
			if(ballXPos < width - width  / 4) {
				playerTwoYPos = ballYPos - PLAYER_HEIGHT / 2;
			}  else {
				playerTwoYPos =  ballYPos > playerTwoYPos + PLAYER_HEIGHT / 2 ?playerTwoYPos += 1: playerTwoYPos - 1;
			}
			
            //sacar la pelota
			gc.fillOval(ballXPos, ballYPos, BALL_R, BALL_R);
			
		} else {
			
            //establecer el texto de inicio
			gc.setStroke(Color.WHITE);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.strokeText("Click", width / 2, height / 2);
			
			
            //restablecer la posición inicial de la pelota
			ballXPos = width / 2;
			ballYPos = height / 2;
			
			//restablecer la velocidad de la pelota y la dirección
			ballXSpeed = new Random().nextInt(2) == 0 ? 1: -1;
			ballYSpeed = new Random().nextInt(2) == 0 ? 1: -1;
		}
		
		
         //se asegura de que la pelota permanezca en la lona
		if(ballYPos > height || ballYPos < 0) ballYSpeed *=-1;
		
		
        //si fallas el balón, la computadora obtiene un punto
		if(ballXPos < playerOneXPos - PLAYER_WIDTH) {
			scoreP2++;
			gameStarted = false;
		}
		
		//si la computadora falla el balón, obtienes un punto
		if(ballXPos > playerTwoXPos + PLAYER_WIDTH) {  
			scoreP1++;
			gameStarted = false;
		}
	
		// Aumenta la velocidad después de que la pelota golpea al jugador
		if( ((ballXPos + BALL_R > playerTwoXPos) && ballYPos >= playerTwoYPos && ballYPos <= playerTwoYPos + PLAYER_HEIGHT) || 
			((ballXPos < playerOneXPos + PLAYER_WIDTH) && ballYPos >= playerOneYPos && ballYPos <= playerOneYPos + PLAYER_HEIGHT)) {
			ballYSpeed += 1 * Math.signum(ballYSpeed);
			ballXSpeed += 1 * Math.signum(ballXSpeed);
			ballXSpeed *= -1;
			ballYSpeed *= -1;
		}
		
		//empate puntuación

		gc.fillText(scoreP1 + "\t\t\t\t\t\t\t\t" + scoreP2, width / 2, 100);
		// Dibuja al jugador 1 y 2
		gc.fillRect(playerTwoXPos, playerTwoYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
		gc.fillRect(playerOneXPos, playerOneYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
	}
	
		
        // inicia la aplicación
		public static void main(String[] args) {
		launch(args);
		}
}