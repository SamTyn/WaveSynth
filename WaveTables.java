public class WaveTables {
	
	static int[] sine=new int[131072];
	static int[] square=new int[131072];
	static int[] saw=new int[131072];
	static int[] triangle=new int[131072];
	
	public static void loadAllWaveforms(){
		loadSine();
		loadSquare();
		loadSaw();
		loadTriangle();
	}
	
	public static void loadSine(){
		for(int i=0;i<131068;i++){
			sine[i]=(int)(Math.round(32767*Math.sin(2*Math.PI*i/131068)));
		}
	}
	
	public static void loadSquare(){
		for(int i=0;i<131068;i++){
			square[i]=(int)(32767-(i/65535)*65534);
		}
	}
	
	public static void loadSaw(){
		double pos=0;
		for(int i=0;i<131068;i++){
			saw[i]=(int)pos;
			if(pos==32767){
				pos=-32767.5;
			}
			pos+=0.5;
			}
	}
	
	public static void loadTriangle(){
		int pos=0;
		boolean increasing=true;
		for(int i=0;i<131068;i++){
			triangle[i]=pos;
			if(pos==32767){
				increasing=false;
			}
			if(pos==-32767){
				increasing=true;
			}
			if(increasing){
				pos+=1;
			}else{
				pos-=1;
			}
		}
	}
}
