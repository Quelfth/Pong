package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Pong {
	static int P1Pos = 0;
	static int ballY = 30;
	static int ballX = 30;
	static boolean ballDirX = true;
	static boolean ballDirY = true;
	static int balls = 3;
	static int score = 0;
	static int speed = 0;
	public static void main(String[] args) {
		final JFrame frame = new JFrame("Pong");
		
		JPanel panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				frame.setSize(1000,700);
				super.paintComponent(g);
				P1Pos = MouseInfo.getPointerInfo().getLocation().x;
				
				if(ballY > 700){
					if(balls == 0){
						frame.dispose();
					}
					balls--;
					ballX = 30;
					ballY = 30;
				}
				speed = (score+3)-(5*(3-balls));
				if(speed < 3){speed = 3;}
				if(ballDirY && ballY >= 630 && (ballX <= P1Pos + 200 && ballX >= P1Pos - 200)){
					ballDirY = false;
					score++;
				}
				if(ballDirY == false && ballY <= 20){
					ballDirY = true;
				}
				if(ballDirX == false && ballX <= 20){
					ballDirX = true;
				}
				if(ballDirX && ballX >= 980){
					ballDirX = false;
				}
				if(ballDirX){
					ballX=ballX+speed;
				}else{
					ballX=ballX-speed;
				}
				if(ballDirY){
					ballY=ballY+speed;
				}else{
					ballY=ballY-speed;
				}
				String Score = score+"";
				String Balls = balls+"";
				String Speed = speed+"";
				g.setColor(Color.black);
				g.fillRect(0, 0, 1000, 700);
				g.setColor(Color.white);
				g.setFont(g.getFont().deriveFont(40f));
				g.drawString(Score, 950, 50);
				g.drawString(Balls, 25, 50);
				g.drawString(Speed, 487, 50);
				g.fillRect(P1Pos - 200, 650, 400, 10);
				g.fillRect(ballX-16,ballY-16,32,32);
				g.fillRect(ballX-20,ballY-10,40,20);
				g.fillRect(ballX-10,ballY-20,20,40);
			}
		};
		frame.setVisible(true);
		frame.add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		while(true){
			panel.repaint();
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
