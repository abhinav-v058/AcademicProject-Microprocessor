import java.util.LinkedList;

import javax.swing.JCheckBox;
import javax.swing.JLayeredPane;

public class RegisterGUI {
	
	public LinkedList<JCheckBox> register;
	
	public RegisterGUI()
	{
		register = new LinkedList<JCheckBox>();
		for(int i =0;i<16; i++)
		{
			register.add(new JCheckBox(""));
		}
	}
	
	public void AddToLayeredPane(int x, int y, JLayeredPane layeredPane)
	{
		int temp;
		JCheckBox tempCheckB;
		for(int i =0;i<16; i++)
		{
			temp = x + (20*i);
			tempCheckB = register.get(i);
			tempCheckB.setBounds(temp, y, 20, 20);
			layeredPane.add(tempCheckB);
		}
	}
	
	public void setText(String value)
	{
		for(int i = 0; i<16;i++)
		{
			this.register.get(i).setSelected(value.charAt(i)=='1'?true:false);			
		}
	}

}
