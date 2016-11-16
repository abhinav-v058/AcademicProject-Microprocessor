
public class Accumulator 
{
	public int operand1;
	public int operand2;
	public int result;
	
	public void Add()
	{
		result = operand1 + operand2;
	}
	
	public void Multiply()
	{
		result = operand1 * operand2; 
	}
	
	public void Divide()
	{		
		result = operand1/operand2;
	}
	
	public void clear()
	{
		operand1 = 0;
		operand2 = 0;
		result = 0;
	}
}
