
public enum Register {
	REGISTER_01(0),REGISTER_02(1),REGISTER_03(2),REGISTER_04(3), MAR(4), MDR(5), TempReg(6),ACC(7),IR(8), PC(9),CC(10);
	
    private int value;

   private Register(int value) 
   {
	   this.value = value;
   }
}
