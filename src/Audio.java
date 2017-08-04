import javax.sound.sampled.*;

public class Audio {
	public SourceDataLine sdl;
	public int[] toPlay=null;
	public boolean startPlaying=false;
	public boolean stopPlaying=false;
	public static Audio[] waves=new Audio[WaveSynthGUI.maxWaves];
	public double pause=0.5;
	public double maxAmplitude=1;
	
	public Audio(){
		Thread t=new Thread(new Runnable()
	    {
	        public void run()
	        {
	        	try{
	    			AudioFormat audioF=new AudioFormat(44100,16,1,true,false);
	    			sdl=AudioSystem.getSourceDataLine(audioF);
	    			sdl.open(audioF,8820);
	    		}catch(Exception e){
	    			System.out.println("Couldn't create/open line.");
	    		}
	        	while(true){
	        		try{
	        			Thread.sleep(5);
	        		}catch(Exception e){
	        		}
	        		if(startPlaying){
	        			sdl.start();
	        			while(!stopPlaying){
	        				for(int i=0;i<toPlay.length+pause*44100;i++){
		        				if(stopPlaying){
		        					sdl.flush();
		        					break;
		        				}
		        				if(i<toPlay.length){
		        					sdl.write(intToByteArray((int)(maxAmplitude*toPlay[i])), 0, 2);
		        				}else{
		        					sdl.write(intToByteArray(0),0,2);
		        				}
		        			}
	        			}
	        			sdl.drain();
	        			sdl.stop();
	        			stopPlaying=false;
    					startPlaying=false;
	        		}
	        	}
	        }
	    });
		t.setDaemon(true);
		t.start();
	}
	
	public static byte[] intToByteArray(int number){
		byte[] ba= new byte[2];
		ba[0]=(byte)number;
		ba[1]=(byte)(number>>8);
		return ba;
	}
}
