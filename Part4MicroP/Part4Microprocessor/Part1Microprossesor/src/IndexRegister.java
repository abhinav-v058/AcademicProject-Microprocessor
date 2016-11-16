public enum IndexRegister {
	NoIndexing(0),REGISTER_01(1),REGISTER_02(2),REGISTER_03(3);
	
    private int value;

   private IndexRegister(int value) 
   {
	   this.value = value;
   }
}