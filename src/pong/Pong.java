package pong;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Pong {
	static boolean addSpace = true;
	static int P1Pos = 0;
	static int ballY = 30;
	static int ballX = 30;
	static boolean ballDirX = true;
	static boolean ballDirY = true;
	static int balls = 3;
	static int score = 0;
	static int speed = 3;
	static int width = 0;
	static int rad = 0;
	static boolean doNotStart = false;
	static int auto = 0;
	static int SpInc = 1;
	static int SpInc10 = 0;
	static int SpInc100 = 0;
	static int offset = 0;
	static int mouseX = 0;
	static boolean paddleLimits = true;
	static boolean thickPaddle = false;
	static AudioClip ACWallBlip = JApplet.newAudioClip(Pong.class.getResource("Wall Blip.wav"));
	static AudioClip ACPaddleBlip = JApplet.newAudioClip(Pong.class.getResource("Paddle Blip.wav"));
	public static void main(String[] args) {
		String Diff = JOptionPane.showInputDialog("Easy / Medium / Hard / Custom");
		if (Diff.equalsIgnoreCase("easy") || Diff.equalsIgnoreCase("e")) {
			width = 400;
		} else {
			if (Diff.equalsIgnoreCase("medium") || Diff.equalsIgnoreCase("m")) {
				width = 200;
				SpInc10 = 4;
			} else {
				if (Diff.equalsIgnoreCase("hard") || Diff.equalsIgnoreCase("h")) {
					width = 100;
					SpInc10 = 4;
					SpInc100 = 45;
				} else {
					if (Diff.equalsIgnoreCase("custom") || Diff.equalsIgnoreCase("c")) {
						String Width = JOptionPane.showInputDialog("Width in thicknesses of platform:");
						width = Integer.parseInt(Width);
						width = width * 10;
						String Rate = JOptionPane.showInputDialog("Speed increase per score gotten:");
						SpInc = Integer.parseInt(Rate);
						Rate = JOptionPane.showInputDialog("Speed increase obtained per ten score gotten:");
						SpInc10 = Integer.parseInt(Rate);
						Rate = JOptionPane.showInputDialog("Speed increase obtained per one hundred score gotten:");
						SpInc100 = Integer.parseInt(Rate);
					} else {
						if (Diff.equals("Administrator")) {
							String Auto = JOptionPane.showInputDialog("auto");
							auto = Integer.parseInt(Auto);
							String Width = JOptionPane.showInputDialog("width");
							width = Integer.parseInt(Width);
							String Balls = JOptionPane.showInputDialog("balls");
							balls = Integer.parseInt(Balls);
							String Speed = JOptionPane.showInputDialog("speed");
							speed = Integer.parseInt(Speed);
							String Rate = JOptionPane.showInputDialog("speed rate per score");
							SpInc = Integer.parseInt(Rate);
							Rate = JOptionPane.showInputDialog("speed rate per 10 score");
							SpInc10 = Integer.parseInt(Rate);
							Rate = JOptionPane.showInputDialog("speed rate per 100 score");
							SpInc100 = Integer.parseInt(Rate);
							String ThickPaddle = JOptionPane.showInputDialog("thick paddle (boolean)");
							thickPaddle = Boolean.parseBoolean(ThickPaddle);
						} else {
							if (Diff.equals("auto")) {
								String Auto = JOptionPane.showInputDialog("auto");
								auto = Integer.parseInt(Auto);
								String Width = JOptionPane.showInputDialog("width");
								width = Integer.parseInt(Width);
							} else {
								width = 0;
							}
						}
					}
				}
			}
		}

		rad = width / 2;
		//Graphics
		final JFrame frame = new JFrame("Pong");
		JPanel panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				offset = frame.getX();
				if (addSpace) {
					frame.setSize(1016, 737);
				} else {
					frame.setSize(1000, 700);
				}
				super.paintComponent(g);
				if (doNotStart) {
					frame.dispose();
				}

				mouseX = MouseInfo.getPointerInfo().getLocation().x;
				P1Pos = mouseX - offset;
				if (auto == 1) {
					P1Pos = ballX;
				}
				if (auto == 2) {
					if (ballDirX) {
						P1Pos = ballX + (650 - ballY);
					} else {
						P1Pos = ballX - (650 - ballY);
					}
				}
				if (auto == 3) {
					if (ballY >= 640) {
						P1Pos = ballX;
						paddleLimits = true;
					} else {
						P1Pos = -(rad);
						paddleLimits = false;
					}
				}
				if (auto == -1) {
					if (ballY >= 640) {
						P1Pos = mouseX - offset;
						paddleLimits = true;
					} else {
						P1Pos = -(rad);
						paddleLimits = false;
					}
				}
				if (paddleLimits) {
					if (P1Pos > 1000 - rad) {
						P1Pos = 1000 - rad;
					}
					if (P1Pos < rad) {
						P1Pos = rad;
					}
				}
				//Death
				if (ballY > 700
						&& (thickPaddle == false || (ballX <= P1Pos + (rad + 20) && ballX >= P1Pos - (rad + 20)))) {
					if (balls == 0) {
						frame.dispose();
					}
					balls--;
					speed = speed/2;
					if(speed < 1){
						speed = 1;
					}
					ballX = 30;
					ballY = 30;
				}
				//Score
				if (ballDirY && ballY >= 630 && (ballX <= P1Pos + (rad + 20) && ballX >= P1Pos - (rad + 20))) {
					ballDirY = false;
					score++;
					speed += SpInc;
					if (score % 10 == 0) {
						speed += SpInc10;
					}
					if (score % 100 == 0) {
						speed += SpInc100;
					}
					ACPaddleBlip.play();
				}
				//Walls
				if (ballDirY == false && ballY <= 20) {
					ballDirY = true;
					ACWallBlip.play();
					
				}
				if (ballDirX == false && ballX <= 20) {
					ballDirX = true;
					ACWallBlip.play();
				}
				if (ballDirX && ballX >= 980) {
					ballDirX = false;
					ACWallBlip.play();
				}
				//Motion
				if (ballDirX) {
					ballX = ballX + speed;
				} else {
					ballX = ballX - speed;
				}
				if (ballDirY) {
					ballY = ballY + speed;
				} else {
					ballY = ballY - speed;
				}
				String Score = score + "";
				String Balls = balls + "";
				String Speed = speed + "";
				g.setColor(Color.black);
				g.fillRect(0, 0, 1000, 700);
				g.setColor(Color.white);
				g.setFont(g.getFont().deriveFont(40f));
				g.drawString(Score, 950, 50);
				g.drawString(Balls, 25, 50);
				g.drawString(Speed, 487, 50);
				g.fillRect(P1Pos - rad, 650, width, 10);
				g.fillRect(ballX - 16, ballY - 16, 32, 32);
				g.fillRect(ballX - 20, ballY - 10, 40, 20);
				g.fillRect(ballX - 10, ballY - 20, 20, 40);
			}
		};
		frame.setVisible(true);
		frame.add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		while (true) {
			panel.repaint();
			try {
				Thread.sleep(1000 / 60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
