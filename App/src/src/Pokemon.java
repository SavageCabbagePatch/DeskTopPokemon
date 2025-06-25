package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import java.util.*;
import java.awt.Graphics2D;
import java.awt.Toolkit;
//k
public class Pokemon extends JWindow implements MouseListener
{
	Clip clip;
	JWindow window;
	JLabel i;
	ImageIcon sprite, frame2, mirror, mirror2;
	int xPos, yPos;
	Random rand;
	Timer moveTimer, cryTimer;
	int face;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = screenSize.width;
    int height = screenSize.height;
    int minWidth;
    int minHeight;
    int step = 2;
    boolean faceLeft = true;
	
	public Pokemon(ImageIcon s, ImageIcon s2, Clip clip)
	{
		rand = new Random();
		
		sprite = s;
		frame2 = s2;
		this.clip = clip;
		window = this;
		setSize(s.getIconWidth(), s.getIconHeight());
		window.setLocation(rand.nextInt(10, 1500), rand.nextInt(10, 900));
		setAlwaysOnTop(true);
		setVisible(true);
		setBackground(new Color(0, 0, 0, 0));
		i = new JLabel(s);
		this.add(i);
		this.addMouseListener(this);
		face = 1;
		
		
		
		makeMirror();
		moveTimer = new Timer();
		moveTimer.scheduleAtFixedRate(task, rand.nextInt(3000, 5000), rand.nextInt(4000, 10000));
		cryTimer = new Timer();
		cryTimer.scheduleAtFixedRate(cry, 0, rand.nextInt(20000, 60000));
		
		width = width - s.getIconWidth();
		height = height - s.getIconHeight();
		minWidth = 0 + sprite.getIconWidth();
		minHeight = 0 + sprite.getIconHeight();
		
		pack();
	}
	
	public void close()
	{
		task.cancel();
		moveTimer.cancel();
		cryTimer.cancel();
		cry.cancel();
		this.dispose();
		
	}
	
	
	//MOVEMENT. 0 IS UP, 1 IS RIGHT, 2 IS DOWN, 3 IS LEFT, 4 IS STAY
	private void move()
	{
		Timer movement = new Timer();
		int dir = rand.nextInt(5);
		xPos = window.getX();
		yPos = window.getY();
		
		//SETTING IMAGE TO FACE CORRECT WAY
		if(dir == 1)
		{
			faceLeft = false;
			i.setIcon(mirror);
		}
		if(dir == 3)
		{
			faceLeft = true;
			i.setIcon(sprite);
		}
		
		switch(dir)
		{
			case 0:
				TimerTask moveUp = new TimerTask() {
					int fPos= yPos - rand.nextInt(30, 101);
					
					@Override
					public void run()
					{
						//MOVE RIGHT
						
						int currentX = window.getX();
						int currentY = window.getY();
						
						if(fPos < minHeight )
							fPos = 0;
						
						if(currentY > fPos)
						{
							window.setLocation(currentX, currentY - step);
						}
						else
						{
							window.setLocation(currentX, currentY);
							this.cancel();
							movement.cancel();
						}
						
						
						
					}
				};
				
				movement.scheduleAtFixedRate(moveUp, 0, 20);
				break;
			case 1:
				TimerTask moveRight = new TimerTask() {
					@Override
					public void run()
					{
						int fPos= xPos + rand.nextInt(50, 101);
						int currentX = window.getX();
						int currentY = window.getY();
						
						if(fPos > width)
							fPos = width;
						
						if(currentX < fPos)
						{
							window.setLocation(currentX + step, currentY);
						}
						else
						{
							this.cancel();
							window.setLocation(currentX, currentY);
						}
						
						
						
					}
				};
				movement.scheduleAtFixedRate(moveRight, 0, 20);
				
				break;
			case 2:
				TimerTask moveDown = new TimerTask() {
					int fPos= yPos + rand.nextInt(30, 90);
					
					@Override
					public void run()
					{
						//MOVE RIGHT
						
						int currentX = window.getX();
						int currentY = window.getY();
						
						if(fPos > height)
							fPos = minHeight;
						
						if(currentY < fPos)
						{
							window.setLocation(currentX, currentY + step);
						}
						else
						{
							window.setLocation(currentX, currentY);
							this.cancel();
							movement.cancel();
							
						}
						
						
						
					}
				};
				movement.scheduleAtFixedRate(moveDown, 0, 20);
				break;
			case 3:
				//LEFT
				TimerTask moveLeft = new TimerTask() {
					int fPos = xPos - rand.nextInt(30, 90);
					@Override
					public void run()
					{
						
						int currentX = window.getX();
						int currentY = window.getY();
						
						if(fPos < 0)
							fPos = 0;
						
						if(currentX > fPos)
						{
							window.setLocation(currentX - step, currentY);
						}
						else
						{
							window.setLocation(currentX, currentY);
							this.cancel();
							movement.cancel();
						}
						
						
						
					}
				};
				movement.scheduleAtFixedRate(moveLeft, 0, 20);
				break;
		}
		
		
	}
	
	
	
	
	private void playSound() 
	{
		
		
		if (clip != null) {

		    if (clip != null && clip.isRunning()) {
		        clip.stop();
		    }


		    if (!clip.isOpen()) {
		        return;
		    }

		    clip.setFramePosition(0);
		    clip.start();
		} else {
		    System.err.println("Clip is null for index");
		}

	}
	
	
	
	
	
	


	public void makeMirror()
	{
		int width = sprite.getIconWidth();
        int height = sprite.getIconHeight();
        BufferedImage mirroredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = mirroredImage.createGraphics();
        graphics.drawImage(sprite.getImage(), 0, 0, width, height, width, 0, 0, height, null);
        graphics.dispose();
        
        mirror = new ImageIcon(mirroredImage);
        
        int width2 = frame2.getIconWidth();
        int height2 = frame2.getIconHeight();
        BufferedImage mirroredImage2 = new BufferedImage(width2, height2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2 = mirroredImage2.createGraphics();
        graphics2.drawImage(frame2.getImage(), 0, 0, width2, height2, width2, 0, 0, height2, null);
        graphics2.dispose();
        
        mirror2 =  new ImageIcon(mirroredImage2);
        
	}


	//play sound on click
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		playSound();
		
	}




	@Override
	public void mousePressed(MouseEvent e) 
	{
		
	}




	@Override
	public void mouseReleased(MouseEvent e) 
	{
		
	}




	@Override
	public void mouseEntered(MouseEvent e) 
	{
		if(faceLeft)
			i.setIcon(frame2);
		else
			i.setIcon(mirror2);
	}




	@Override
	public void mouseExited(MouseEvent e) 
	{
		if(faceLeft)
			i.setIcon(sprite);
		else
			i.setIcon(mirror);
	}
	
	
	TimerTask task = new TimerTask() {
		@Override
		public void run()
		{
			move();
		}
	};
	
	TimerTask cry = new TimerTask() {
		@Override
		public void run()
		{
			//playSound();
		}
	};
	
	
	
}
