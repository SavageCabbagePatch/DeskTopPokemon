package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;




public class indexSelect 
{
	
	
	JScrollPane scroll;
	
	JFrame indexFrame;
	JPanel mid;
	JLabel mainLabel;
	ImageIcon[] sprites;

	indexSelect(ImageIcon[] s) throws LineUnavailableException, UnsupportedAudioFileException, IOException 
	{
		//FRAME SETUP
		indexFrame = new JFrame("Index Selector");
		indexFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		indexFrame.setMinimumSize(new Dimension(600,500));
		indexFrame.setMaximumSize(new Dimension(500,500));
		indexFrame.setLayout(new BorderLayout());
		indexFrame.setResizable(false);
		
		sprites = new ImageIcon[s.length];
		for(int i = 0; i < s.length; i++)
		{
			sprites[i] = s[i];
		}
		
		
		//MAIN LABEL HOLDS ALL SELECTIONS
		mainLabel = new JLabel();
		mainLabel.setVisible(true);
		mainLabel.setBackground(Color.black);
		mainLabel.setOpaque(false);
		mainLabel.setIcon(sprites[sprites.length - 1]);
		
		
		//midPanel will hold all sprites
		mid = new JPanel();
		mid.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		mid.setBackground(Color.DARK_GRAY);
		mid.setMaximumSize(new Dimension(500, 500));

		mid.setVisible(true);		
		for(int x = 0; x < sprites.length; x++)
		{
			String n = Integer.toString(x);
			JButton button = new JButton(sprites[x]);
			button.setName(n);
			
			button.setVisible(true);
			button.setPreferredSize(new Dimension(sprites[x].getIconWidth(), sprites[x].getIconHeight()));
			
			button.setFocusable(false);
			
			setupListen(button);
			
			mid.add(button);
			
		}
		
		//mid.setAlignmentY(Component.TOP_ALIGNMENT);
		mid.setPreferredSize(new Dimension(500, 4500));
		
		scroll = new JScrollPane(mid);
		scroll.setPreferredSize(new Dimension(500, 200));
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.getVerticalScrollBar().setUnitIncrement(16); 

		
		indexFrame.add(scroll, BorderLayout.CENTER);
		indexFrame.setSize(500, 500);
		indexFrame.setResizable(false);
		indexFrame.setVisible(true);
		
		
		
		
	}
	
	
	private void setupListen(JButton b)
	{
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				buttonPress(b);
			}
		});
		
		
	}
	
	
	private void buttonPress(JButton b)
	{
		String n = b.getName();
		int x = Integer.parseInt(n);
		myFrame.setIndex(x);
		
	}
	
	
	
}
