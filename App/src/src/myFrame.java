package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import javax.sound.sampled.*;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class myFrame extends JFrame
{

	public static int index = 0;
	JButton rightButton;
	JButton leftButton;
	JButton addButton;
	JButton delete;
	JButton indexWindow;
	
	static Clip clip;

	static JLabel label;
	JLabel selectLabel;
	JLabel pokeLabel;	
	JFrame frame;
	
	JPanel bottomPanel;
	JPanel rightPanel;
	JPanel topPanel;
	JPanel leftPanel;
	JPanel midPanel;
	
	//ARRAY GOES FROM 0 TO 419, 0 BEING BULBASAUR AND 419 BEING DEOXYS SPEED
	//Currently there are dupes of Unown, Castform, and Deoxys
	ArrayList<Pokemon> list;
	
	static ImageIcon[] spriteSheet2;
	static Clip[] sounds2;
	ImageIcon[] sprite2;
	
	
	
	
	 
	//main window
	myFrame() throws LineUnavailableException, UnsupportedAudioFileException, IOException
	{
		spriteSheet2 = new ImageIcon[420];
		sounds2 = new Clip[420];
		sprite2 = new ImageIcon[420];
		setupFrame();
		setupListen();
	}
	
	
	//FRAME SETUP
	private void setupFrame() throws LineUnavailableException, UnsupportedAudioFileException, IOException
	{
		
		
		
		
		
		//ArrayList and array of all sprites since getResource cant just do it easily
		
		//MAIN SPRITES
		for (int i = 1; i <= 420; i++) {
		    String fileName = String.format("/pokemon-3/main-sprites/emerald/%03d.png", i);
		    URL url = getClass().getResource(fileName);
		    
		    if (url != null) {
			    spriteSheet2[i - 1] = new ImageIcon(url);
		    } else {
		        System.err.println("Missing: " + fileName);
		    }
		}
		
		
		
		
		//SECOND FRAME OF ANIMATION
		for (int i = 1; i <= 420; i++) {
		    String fileName = String.format("/pokemon-3/main-sprites/frame2/%03d.png", i);
		    URL url = getClass().getResource(fileName);
		    
		    if (url != null) {
			    sprite2[i - 1] = new ImageIcon(url);
		    } else {
		        System.err.println("Missing: " + fileName);
		    }
		}
		
		//Array of sounds
		for (int i = 1; i <= 420; i++) {
		    String fileName = String.format("/cries/%03d.wav", i);
		    InputStream current = getClass().getResourceAsStream(fileName);
		    
		    if (current != null) {
		    	InputStream currBuffer = new BufferedInputStream(current);
		    	AudioInputStream audStream = AudioSystem.getAudioInputStream(currBuffer);
		    	Clip c = AudioSystem.getClip();
		    	c.open(audStream);
		        sounds2[i - 1] = c;
		    } 
		    else 
		    {
		        System.err.println("Missing: " + fileName);
		    }
		}
		
		
		
		
		
		//MAIN WINDOW SETTINGS
		frame = new JFrame("DeskTopPokemon");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(600,500));
		frame.setMaximumSize(new Dimension(500,500));
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);
		URL logoUrl = getClass().getResource("/poke.png");
		if (logoUrl != null) {
		    ImageIcon logo = new ImageIcon(logoUrl);
		    frame.setIconImage(logo.getImage());
		} else {
		    System.err.println("Could not load logo icon!");
		}		
		frame.setLocationRelativeTo(null);
		
		
		
		
		
		
		
		
		
		
		//TOP PANEL
		topPanel = new JPanel();
		topPanel.setBackground(new Color(255, 128, 0));
		topPanel.setPreferredSize(new Dimension(0, 70));
		topPanel.setVisible(true);
		
		
		
		//bottom PANEL HOLDS BUTTONS ON BOTTOM
		bottomPanel = new JPanel();
		bottomPanel.setBackground(new Color(255, 128, 0));
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 45, 0));
		bottomPanel.setVisible(true);
		bottomPanel.setPreferredSize(new Dimension(0, 70));
		
		
		//RIGHTSIDE PANEL
		rightPanel = new JPanel();
		rightPanel.setBackground(Color.BLACK);
		rightPanel.setLayout(null);
		rightPanel.setVisible(true);
		rightPanel.setPreferredSize(new Dimension(90, 0));
		
		
		//LEFT PANEL
		leftPanel = new JPanel();
		leftPanel.setBackground(Color.BLACK);
		leftPanel.setLayout(null);
		leftPanel.setVisible(true);
		leftPanel.setPreferredSize(new Dimension(90, 0));
		
		//MIDDLE PANEL IS LIKE SCREEN
		midPanel = new JPanel();
		midPanel.setBackground(Color.LIGHT_GRAY);
		midPanel.setLayout(null);
		midPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
		midPanel.setVisible(true);
		
		
		//LABEL SHOWS CURRENT CHOICE
		label = new JLabel();
		label.setVisible(true);
		ImageIcon image = spriteSheet2[0];
		label.setIcon(spriteSheet2[0]);
		label.setBounds(170, 90, image.getIconWidth(), image.getIconHeight());
		
		
		//Select Text
		selectLabel = new JLabel();
		selectLabel.setVisible(true);
		ImageIcon selectText = null;
		logoUrl = getClass().getResource("/selectT.gif");
		if (logoUrl != null) {
		    selectText = new ImageIcon(logoUrl);
		} else {
		    System.err.println("Could not load logo icon!");
		}	
		selectLabel.setIcon(selectText);
		selectLabel.setBounds(75, 250, selectText.getIconWidth(), selectText.getIconHeight());
		
		
		//Pokeball animation
		pokeLabel = new JLabel();
		pokeLabel.setVisible(true);
		ImageIcon pokeBall = null;
		logoUrl = getClass().getResource("/pokeball.gif");
		if (logoUrl != null) {
		    pokeBall = new ImageIcon(logoUrl);
		} else {
		    System.err.println("Could not load logo icon!");
		}	
		pokeLabel.setIcon(pokeBall);
		pokeLabel.setBounds(0, 0, pokeBall.getIconWidth(), pokeBall.getIconHeight());
		
		
		//RIGHT GOES RIGHT
		URL rightURL = getClass().getResource("/rightButton.png");
		ImageIcon rightBut = null;

		if (rightURL != null) {
		    rightBut = new ImageIcon(rightURL);
		} else {
		    System.err.println("Could not load right button image!");
		}
		rightButton = new JButton(rightBut);
		rightButton.setBorderPainted(false);
		rightButton.setContentAreaFilled(false);
		rightButton.setFocusPainted(false);
		rightButton.setOpaque(false);
		rightButton.setFocusable(false);
		rightButton.setVisible(true);
		rightButton.setBackground(Color.gray);
		rightButton.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));
		
		
		//LEFT GOES LEFT
		URL leftUrl = getClass().getResource("/leftButton.png");
		ImageIcon leftBut = null;

		if (leftUrl != null) {
		    leftBut = new ImageIcon(leftUrl);
		} else {
		    System.err.println("Could not load right button image!");
		}
		leftButton = new JButton(leftBut);
		leftButton.setBorderPainted(false);
		leftButton.setContentAreaFilled(false);
		leftButton.setFocusPainted(false);
		leftButton.setOpaque(false);
		leftButton.setFocusable(false);
		leftButton.setPreferredSize(new Dimension(100, 50));
		leftButton.setVisible(true);
		leftButton.setBackground(Color.gray);
		
		
		//ADD BUTTON
		URL addURL = getClass().getResource("/addButton.png");
		ImageIcon addIcon = null;

		if (addURL != null) {
		    addIcon = new ImageIcon(addURL);
		} else {
		    System.err.println("Could not load add button image!");
		}

		addButton = new JButton(addIcon);
		addButton.setBorderPainted(false);
		addButton.setContentAreaFilled(false);
		addButton.setFocusPainted(false);
		addButton.setOpaque(false);
		addButton.setFocusable(false);
		addButton.setPreferredSize(new Dimension(100, 60));
		addButton.setVisible(true);
		addButton.setBackground(Color.gray);
		
		//REMOVE BUTTON
		URL deleteURL = getClass().getResource("/deleteButton.png");
		ImageIcon deleteIcon = null;

		if (deleteURL != null) {
		    deleteIcon = new ImageIcon(deleteURL);
		} else {
		    System.err.println("Could not load delete button image!");
		}
		delete = new JButton(deleteIcon);
		delete.setBorderPainted(false);
		delete.setContentAreaFilled(false);
		delete.setFocusPainted(false);
		delete.setOpaque(false);
		delete.setFocusable(false);
		delete.setPreferredSize(new Dimension(100, 60));
		delete.setVisible(true);
		delete.setBackground(Color.gray);
		
		//SPECIFIC INDEX BUTTON
		//X, Y, WIDTH, THEN HEIGHT
		URL ind = getClass().getResource("/indexButton.png");
		ImageIcon i = null;

		if (ind != null) {
		    i = new ImageIcon(ind);
		} else {
		    System.err.println("Could not load delete button image!");
		}

		indexWindow = new JButton(i);
		indexWindow.setBorderPainted(false);
		indexWindow.setContentAreaFilled(false);
		indexWindow.setFocusPainted(false);
		indexWindow.setFocusable(false);
		indexWindow.setBackground(Color.black);
		indexWindow.setVisible(true);
		indexWindow.setPreferredSize(new Dimension(50, 50));
		indexWindow.setBounds(30, 20, 40, 40);
		
		//ADDING EVERYTHING TO FRAME
		midPanel.add(pokeLabel);
		midPanel.add(label);
		midPanel.add(selectLabel);
		
		bottomPanel.add(delete);
		bottomPanel.add(leftButton);
		bottomPanel.add(rightButton);
		bottomPanel.add(addButton);
		
		leftPanel.add(indexWindow);
		
		
		frame.add(bottomPanel, BorderLayout.SOUTH);
		frame.add(rightPanel, BorderLayout.EAST);
		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(leftPanel, BorderLayout.WEST);
		frame.add(midPanel);
		
		
		
		list = new ArrayList<Pokemon>();
		
		playSound(index);
		frame.pack();
		
		Rectangle bounds = midPanel.getBounds();
		System.out.println("X: " + bounds.width);
		System.out.println("Y: " + bounds.height);
		
	}


	
	
	
	//LISTENING SETUP
	private void setupListen()
	{
		
		rightButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				rightPress();
			}
		});
		
		
		leftButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					leftPress();
				}
			});
		
		addButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					addPress();
				}
			});
		
		delete.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					deletePress();
				}
			});
		
		indexWindow.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				indexPress();
			}
		});
	}
	
	
	
	
	
	public void indexPress()
	{
		try {
			new indexSelect(spriteSheet2);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setIndex(int n)
	{
		
		System.out.println("BUTTON PRESSED: " + n);
		index = n;
		ImageIcon i = spriteSheet2[index];
		label.setIcon(i);
		
		if(clip.isRunning())
		{
			clip.stop();
		}
		
		playSound(index);
	}
	
	//LISTENERS
	public void rightPress() 
	{
		index++;
		if(index > 419)
		{
			System.out.println("END OF SPRITES");
			index = 419;
		}
		else
		{
			
			ImageIcon n = spriteSheet2[index];
			label.setIcon(n);
			
			if(clip.isRunning())
			{
				clip.stop();
			}
			
			playSound(index);
		}
			
			
		
	}
	
	public void leftPress()
	{
		
		if(index <= 0)
		{
			System.out.println("END OF SPRITES");
			index = 0;
		}
		else
		{
			index--;
			ImageIcon n = spriteSheet2[index];
			label.setIcon(n);
			
			if(clip.isRunning())
			{
				clip.stop();
			}
			
			playSound(index);
			
		}
		
		
		
	}
	

	public static void playSound(int ind)
	{
		clip = sounds2[0];
		if (sounds2[index] != null) {

		    if (clip != null && clip.isRunning()) {
		        clip.stop();
		    }

		    clip = sounds2[index];

		    if (!clip.isOpen()) {
		        return;
		    }

		    clip.setFramePosition(0);
		    clip.start();
		} else {
		    System.err.println("Clip is null for index: " + index);
		}
		
	}
	
	
	public void addPress()
	{
		if(index > 419)
		{
			index = 419;
		}
		if(index < 0)
		{
			index = 0;
		}
		ImageIcon n = (ImageIcon) label.getIcon();
		ImageIcon n2 = null;
		
		
		switch(index)
		{
		case 200:
		    System.out.println("FRAME200 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 201:
		    System.out.println("FRAME201 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 202:
		    System.out.println("FRAME202 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 203:
		    System.out.println("FRAME203 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 204:
		    System.out.println("FRAME204 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 205:
		    System.out.println("FRAME205 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 206:
		    System.out.println("FRAME206 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 207:
		    System.out.println("FRAME207 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 208:
		    System.out.println("FRAME208 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 209:
		    System.out.println("FRAME209 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 210:
		    System.out.println("FRAME210 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 211:
		    System.out.println("FRAME211 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 212:
		    System.out.println("FRAME212 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 213:
		    System.out.println("FRAME213 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 214:
		    System.out.println("FRAME214 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 215:
		    System.out.println("FRAME215 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 216:
		    System.out.println("FRAME216 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 217:
		    System.out.println("FRAME217 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 218:
		    System.out.println("FRAME218 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 219:
		    System.out.println("FRAME219 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 220:
		    System.out.println("FRAME220 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 221:
		    System.out.println("FRAME221 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 222:
		    System.out.println("FRAME222 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 223:
		    System.out.println("FRAME223 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 224:
		    System.out.println("FRAME224 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 225:
		    System.out.println("FRAME225 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 226:
		    System.out.println("FRAME226 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 227:
		    System.out.println("FRAME227 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 228:
		    System.out.println("FRAME228 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 354:
			System.out.println("FRAME 354 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 378:
			System.out.println("FRAME 378 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 379:
			System.out.println("FRAME 379 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 380:
			System.out.println("FRAME 380 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 381:
			System.out.println("FRAME 381 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 416:
			System.out.println("FRAME 416 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 417:
			System.out.println("FRAME 417 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 418:
			System.out.println("FRAME 418 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		case 419:
			System.out.println("FRAME 419 EXCEPTION SELECTED");
		    n2 = n;
		    break;
		default:
			n2 = sprite2[index];
		}
		
		
		
		
		list.add(new Pokemon(n, n2, clip));
		
		
		
	}
	
	public void deletePress()
	{
		if(!list.isEmpty())
		{
			list.get(0).close();
			list.remove(0);
		}
		
		
	}
	
	
	
	
	
	
}
